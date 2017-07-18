package com.mob.tools.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import com.mob.tools.MobHandlerThread;

public class LocationHelper implements LocationListener, Callback {
    private int GPSTimeoutSec;
    private boolean gpsRequesting;
    private Handler handler;
    private LocationManager lm;
    private boolean networkRequesting;
    private int networkTimeoutSec;
    private Location res;

    public LocationHelper() {
        MobHandlerThread thread = new MobHandlerThread();
        thread.start();
        this.handler = new Handler(thread.getLooper(), this);
    }

    private void onGPSTimeout() {
        boolean preferNetwork = false;
        this.lm.removeUpdates(this);
        this.gpsRequesting = false;
        if (this.networkTimeoutSec != 0) {
            preferNetwork = true;
        }
        if (preferNetwork && this.lm.isProviderEnabled("network")) {
            this.networkRequesting = true;
            this.lm.requestLocationUpdates("network", 1000, 0.0f, this);
            if (this.networkTimeoutSec > 0) {
                this.handler.sendEmptyMessageDelayed(1, (long) (this.networkTimeoutSec * 1000));
                return;
            }
            return;
        }
        synchronized (this) {
            notifyAll();
        }
        this.handler.getLooper().quit();
    }

    private void onRequest() {
        boolean preferGPS = this.GPSTimeoutSec != 0;
        boolean preferNetwork = this.networkTimeoutSec != 0;
        if (preferGPS && this.lm.isProviderEnabled("gps")) {
            this.gpsRequesting = true;
            this.lm.requestLocationUpdates("gps", 1000, 0.0f, this);
            if (this.GPSTimeoutSec > 0) {
                this.handler.sendEmptyMessageDelayed(1, (long) (this.GPSTimeoutSec * 1000));
            }
        } else if (preferNetwork && this.lm.isProviderEnabled("network")) {
            this.networkRequesting = true;
            this.lm.requestLocationUpdates("network", 1000, 0.0f, this);
            if (this.networkTimeoutSec > 0) {
                this.handler.sendEmptyMessageDelayed(1, (long) (this.networkTimeoutSec * 1000));
            }
        } else {
            synchronized (this) {
                notifyAll();
            }
            this.handler.getLooper().quit();
        }
    }

    public Location getLocation(Context context) throws Throwable {
        return getLocation(context, 0);
    }

    public Location getLocation(Context context, int GPSTimeoutSec) throws Throwable {
        return getLocation(context, GPSTimeoutSec, 0);
    }

    public Location getLocation(Context context, int GPSTimeoutSec, int networkTimeoutSec) throws Throwable {
        return getLocation(context, GPSTimeoutSec, networkTimeoutSec, true);
    }

    public Location getLocation(Context context, int GPSTimeoutSec, int networkTimeoutSec, boolean useLastKnown) throws Throwable {
        boolean preferNetwork = true;
        this.GPSTimeoutSec = GPSTimeoutSec;
        this.networkTimeoutSec = networkTimeoutSec;
        this.lm = (LocationManager) context.getSystemService(GroupStreamValue.LOCATION);
        if (this.lm == null) {
            return null;
        }
        synchronized (this) {
            this.handler.sendEmptyMessageDelayed(0, 50);
            wait();
        }
        if (this.res == null && useLastKnown) {
            boolean preferGPS = GPSTimeoutSec != 0;
            if (networkTimeoutSec == 0) {
                preferNetwork = false;
            }
            if (preferGPS && this.lm.isProviderEnabled("gps")) {
                this.res = this.lm.getLastKnownLocation("gps");
            } else if (preferNetwork && this.lm.isProviderEnabled("network")) {
                this.res = this.lm.getLastKnownLocation("network");
            }
        }
        return this.res;
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == 0) {
            onRequest();
        } else if (this.gpsRequesting) {
            onGPSTimeout();
        } else if (this.networkRequesting) {
            this.lm.removeUpdates(this);
            synchronized (this) {
                notifyAll();
            }
            this.handler.getLooper().quit();
        }
        return false;
    }

    public void onLocationChanged(Location location) {
        synchronized (this) {
            this.lm.removeUpdates(this);
            this.res = location;
            notifyAll();
        }
        this.handler.getLooper().quit();
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
