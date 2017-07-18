package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000;
    private static final int MAX_CONNECTIONS_TO_CLEANUP = 2;
    private static final ConnectionPool systemDefault;
    private final LinkedList<Connection> connections = new LinkedList();
    private final Runnable connectionsCleanupRunnable = new Runnable() {
        public void run() {
            List<Connection> expiredConnections = new ArrayList(2);
            int idleConnectionCount = 0;
            synchronized (ConnectionPool.this) {
                ListIterator<Connection> i = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                while (i.hasPrevious()) {
                    Connection connection = (Connection) i.previous();
                    if (!connection.isAlive() || connection.isExpired(ConnectionPool.this.keepAliveDurationNs)) {
                        i.remove();
                        expiredConnections.add(connection);
                        if (expiredConnections.size() == 2) {
                            break;
                        }
                    } else if (connection.isIdle()) {
                        idleConnectionCount++;
                    }
                }
                i = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                while (i.hasPrevious() && idleConnectionCount > ConnectionPool.this.maxIdleConnections) {
                    connection = (Connection) i.previous();
                    if (connection.isIdle()) {
                        expiredConnections.add(connection);
                        i.remove();
                        idleConnectionCount--;
                    }
                }
            }
            for (Connection expiredConnection : expiredConnections) {
                Util.closeQuietly(expiredConnection.getSocket());
            }
        }
    };
    private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    static {
        String keepAlive = System.getProperty("http.keepAlive");
        String keepAliveDuration = System.getProperty("http.keepAliveDuration");
        String maxIdleConnections = System.getProperty("http.maxConnections");
        long keepAliveDurationMs = keepAliveDuration != null ? Long.parseLong(keepAliveDuration) : DEFAULT_KEEP_ALIVE_DURATION_MS;
        if (keepAlive != null && !Boolean.parseBoolean(keepAlive)) {
            systemDefault = new ConnectionPool(0, keepAliveDurationMs);
        } else if (maxIdleConnections != null) {
            systemDefault = new ConnectionPool(Integer.parseInt(maxIdleConnections), keepAliveDurationMs);
        } else {
            systemDefault = new ConnectionPool(5, keepAliveDurationMs);
        }
    }

    public ConnectionPool(int maxIdleConnections, long keepAliveDurationMs) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationNs = (keepAliveDurationMs * 1000) * 1000;
    }

    List<Connection> getConnections() {
        List arrayList;
        waitForCleanupCallableToRun();
        synchronized (this) {
            arrayList = new ArrayList(this.connections);
        }
        return arrayList;
    }

    private void waitForCleanupCallableToRun() {
        try {
            this.executorService.submit(new Runnable() {
                public void run() {
                }
            }).get();
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    public synchronized int getConnectionCount() {
        return this.connections.size();
    }

    public synchronized int getSpdyConnectionCount() {
        int total;
        total = 0;
        Iterator i$ = this.connections.iterator();
        while (i$.hasNext()) {
            if (((Connection) i$.next()).isSpdy()) {
                total++;
            }
        }
        return total;
    }

    public synchronized int getHttpConnectionCount() {
        int total;
        total = 0;
        Iterator i$ = this.connections.iterator();
        while (i$.hasNext()) {
            if (!((Connection) i$.next()).isSpdy()) {
                total++;
            }
        }
        return total;
    }

    public synchronized Connection get(Address address) {
        Connection foundConnection;
        foundConnection = null;
        ListIterator<Connection> i = this.connections.listIterator(this.connections.size());
        while (i.hasPrevious()) {
            Connection connection = (Connection) i.previous();
            if (connection.getRoute().getAddress().equals(address) && connection.isAlive() && System.nanoTime() - connection.getIdleStartTimeNs() < this.keepAliveDurationNs) {
                i.remove();
                if (!connection.isSpdy()) {
                    try {
                        Platform.get().tagSocket(connection.getSocket());
                    } catch (SocketException e) {
                        Util.closeQuietly(connection.getSocket());
                        Platform.get().logW("Unable to tagSocket(): " + e);
                    }
                }
                foundConnection = connection;
                break;
            }
        }
        if (foundConnection != null) {
            if (foundConnection.isSpdy()) {
                this.connections.addFirst(foundConnection);
            }
        }
        this.executorService.execute(this.connectionsCleanupRunnable);
        return foundConnection;
    }

    void recycle(Connection connection) {
        if (connection.isSpdy() || !connection.clearOwner()) {
            return;
        }
        if (connection.isAlive()) {
            try {
                Platform.get().untagSocket(connection.getSocket());
                synchronized (this) {
                    this.connections.addFirst(connection);
                    connection.incrementRecycleCount();
                    connection.resetIdleStartTime();
                }
                this.executorService.execute(this.connectionsCleanupRunnable);
                return;
            } catch (SocketException e) {
                Platform.get().logW("Unable to untagSocket(): " + e);
                Util.closeQuietly(connection.getSocket());
                return;
            }
        }
        Util.closeQuietly(connection.getSocket());
    }

    void share(Connection connection) {
        if (connection.isSpdy()) {
            this.executorService.execute(this.connectionsCleanupRunnable);
            if (connection.isAlive()) {
                synchronized (this) {
                    this.connections.addFirst(connection);
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void evictAll() {
        List<Connection> connections;
        synchronized (this) {
            connections = new ArrayList(this.connections);
            this.connections.clear();
        }
        int size = connections.size();
        for (int i = 0; i < size; i++) {
            Util.closeQuietly(((Connection) connections.get(i)).getSocket());
        }
    }
}
