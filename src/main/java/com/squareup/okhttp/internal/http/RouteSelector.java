package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocketFactory;

public final class RouteSelector {
    private final Address address;
    private final OkHttpClient client;
    private List<ConnectionSpec> connectionSpecs = Collections.emptyList();
    private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private ConnectionSpec lastSpec;
    private final Network network;
    private int nextInetSocketAddressIndex;
    private int nextProxyIndex;
    private int nextSpecIndex;
    private final ConnectionPool pool;
    private final List<Route> postponedRoutes = new ArrayList();
    private List<Proxy> proxies = Collections.emptyList();
    private final ProxySelector proxySelector;
    private final Request request;
    private final RouteDatabase routeDatabase;
    private final URI uri;

    private RouteSelector(Address address, URI uri, OkHttpClient client, Request request) {
        this.address = address;
        this.uri = uri;
        this.client = client;
        this.proxySelector = client.getProxySelector();
        this.pool = client.getConnectionPool();
        this.routeDatabase = Internal.instance.routeDatabase(client);
        this.network = Internal.instance.network(client);
        this.request = request;
        resetNextProxy(uri, address.getProxy());
    }

    public static RouteSelector get(Request request, OkHttpClient client) throws IOException {
        String uriHost = request.url().getHost();
        if (uriHost == null || uriHost.length() == 0) {
            throw new UnknownHostException(request.url().toString());
        }
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (request.isHttps()) {
            sslSocketFactory = client.getSslSocketFactory();
            hostnameVerifier = client.getHostnameVerifier();
            certificatePinner = client.getCertificatePinner();
        }
        return new RouteSelector(new Address(uriHost, Util.getEffectivePort(request.url()), client.getSocketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, client.getAuthenticator(), client.getProxy(), client.getProtocols(), client.getConnectionSpecs()), request.uri(), client, request);
    }

    public boolean hasNext() {
        return hasNextConnectionSpec() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
    }

    public Connection next(HttpEngine owner) throws IOException {
        Connection connection = nextUnconnected();
        Internal.instance.connectAndSetOwner(this.client, connection, owner, this.request);
        return connection;
    }

    Connection nextUnconnected() throws IOException {
        while (true) {
            Connection pooled = this.pool.get(this.address);
            if (pooled == null) {
                break;
            } else if (this.request.method().equals("GET") || Internal.instance.isReadable(pooled)) {
                return pooled;
            } else {
                pooled.getSocket().close();
            }
        }
        if (!hasNextConnectionSpec()) {
            if (!hasNextInetSocketAddress()) {
                if (hasNextProxy()) {
                    this.lastProxy = nextProxy();
                } else if (hasNextPostponed()) {
                    return new Connection(this.pool, nextPostponed());
                } else {
                    throw new NoSuchElementException();
                }
            }
            this.lastInetSocketAddress = nextInetSocketAddress();
        }
        this.lastSpec = nextConnectionSpec();
        Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec);
        if (!this.routeDatabase.shouldPostpone(route)) {
            return new Connection(this.pool, route);
        }
        this.postponedRoutes.add(route);
        return nextUnconnected();
    }

    public void connectFailed(Connection connection, IOException failure) {
        if (Internal.instance.recycleCount(connection) <= 0) {
            Route failedRoute = connection.getRoute();
            if (!(failedRoute.getProxy().type() == Type.DIRECT || this.proxySelector == null)) {
                this.proxySelector.connectFailed(this.uri, failedRoute.getProxy().address(), failure);
            }
            this.routeDatabase.failed(failedRoute);
            if (!(failure instanceof SSLHandshakeException) && !(failure instanceof SSLProtocolException)) {
                while (this.nextSpecIndex < this.connectionSpecs.size()) {
                    Address address = this.address;
                    Proxy proxy = this.lastProxy;
                    InetSocketAddress inetSocketAddress = this.lastInetSocketAddress;
                    List list = this.connectionSpecs;
                    int i = this.nextSpecIndex;
                    this.nextSpecIndex = i + 1;
                    this.routeDatabase.failed(new Route(address, proxy, inetSocketAddress, (ConnectionSpec) list.get(i)));
                }
            }
        }
    }

    private void resetNextProxy(URI uri, Proxy proxy) {
        if (proxy != null) {
            this.proxies = Collections.singletonList(proxy);
        } else {
            this.proxies = new ArrayList();
            List<Proxy> selectedProxies = this.proxySelector.select(uri);
            if (selectedProxies != null) {
                this.proxies.addAll(selectedProxies);
            }
            this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.proxies.add(Proxy.NO_PROXY);
        }
        this.nextProxyIndex = 0;
    }

    private boolean hasNextProxy() {
        return this.nextProxyIndex < this.proxies.size();
    }

    private Proxy nextProxy() throws IOException {
        if (hasNextProxy()) {
            List list = this.proxies;
            int i = this.nextProxyIndex;
            this.nextProxyIndex = i + 1;
            Proxy result = (Proxy) list.get(i);
            resetNextInetSocketAddress(result);
            return result;
        }
        throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted proxy configurations: " + this.proxies);
    }

    private void resetNextInetSocketAddress(Proxy proxy) throws UnknownHostException {
        int socketPort;
        this.inetSocketAddresses = new ArrayList();
        String socketHost;
        if (proxy.type() == Type.DIRECT) {
            socketHost = this.address.getUriHost();
            socketPort = Util.getEffectivePort(this.uri);
        } else {
            SocketAddress proxyAddress = proxy.address();
            if (proxyAddress instanceof InetSocketAddress) {
                InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
                socketHost = proxySocketAddress.getHostName();
                socketPort = proxySocketAddress.getPort();
            } else {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
            }
        }
        for (InetAddress inetAddress : this.network.resolveInetAddresses(socketHost)) {
            this.inetSocketAddresses.add(new InetSocketAddress(inetAddress, socketPort));
        }
        this.nextInetSocketAddressIndex = 0;
    }

    private boolean hasNextInetSocketAddress() {
        return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
    }

    private InetSocketAddress nextInetSocketAddress() throws IOException {
        if (hasNextInetSocketAddress()) {
            List list = this.inetSocketAddresses;
            int i = this.nextInetSocketAddressIndex;
            this.nextInetSocketAddressIndex = i + 1;
            InetSocketAddress result = (InetSocketAddress) list.get(i);
            resetConnectionSpecs();
            return result;
        }
        throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
    }

    private void resetConnectionSpecs() {
        this.connectionSpecs = new ArrayList();
        for (ConnectionSpec spec : this.address.getConnectionSpecs()) {
            if (this.request.isHttps() == spec.isTls()) {
                this.connectionSpecs.add(spec);
            }
        }
        this.nextSpecIndex = 0;
    }

    private boolean hasNextConnectionSpec() {
        return this.nextSpecIndex < this.connectionSpecs.size();
    }

    private ConnectionSpec nextConnectionSpec() throws IOException {
        if (hasNextConnectionSpec()) {
            List list = this.connectionSpecs;
            int i = this.nextSpecIndex;
            this.nextSpecIndex = i + 1;
            return (ConnectionSpec) list.get(i);
        }
        throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted connection specs: " + this.connectionSpecs);
    }

    private boolean hasNextPostponed() {
        return !this.postponedRoutes.isEmpty();
    }

    private Route nextPostponed() {
        return (Route) this.postponedRoutes.remove(0);
    }
}
