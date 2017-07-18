package com.yunva.im.sdk.lib.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import java.util.List;

public class a {
    public static a a;
    private Context b;
    private LocationManager c;
    private b d;
    private double e = 0.0d;
    private double f = 0.0d;
    private double g = 0.0d;
    private double h = 0.0d;

    public static a a(Context context, b bVar) {
        if (a != null) {
            return a;
        }
        a = new a(context, bVar);
        return a;
    }

    public a(Context context, b bVar) {
        this.b = context;
        this.c = (LocationManager) this.b.getSystemService(GroupStreamValue.LOCATION);
        this.d = bVar;
    }

    public boolean a() {
        this.g = 0.0d;
        this.h = 0.0d;
        if (this.c.isProviderEnabled("gps")) {
            Location lastKnownLocation = this.c.getLastKnownLocation("gps");
            if (lastKnownLocation != null) {
                this.g = lastKnownLocation.getLatitude();
                this.h = lastKnownLocation.getLongitude();
            }
            LocationListener h = h();
            Location lastKnownLocation2 = this.c.getLastKnownLocation("gps");
            if (lastKnownLocation2 != null) {
                this.g = lastKnownLocation2.getLatitude();
                this.h = lastKnownLocation2.getLongitude();
                this.d.a(1, this.h + "_" + this.g);
                this.c.removeUpdates(h);
                return true;
            } else if (this.g == 0.0d || this.h == 0.0d) {
                this.d.a(1949, 1);
                return false;
            } else {
                this.d.a(1, this.h + "_" + this.g);
                return true;
            }
        }
        this.d.a(1943, 1);
        return false;
    }

    public void b() {
        this.d.a(1946, 8);
    }

    public void c() {
        if (this.c.isProviderEnabled("network")) {
            LocationListener i = i();
            Location lastKnownLocation = this.c.getLastKnownLocation("network");
            if (lastKnownLocation != null) {
                this.e = lastKnownLocation.getLatitude();
                this.f = lastKnownLocation.getLongitude();
                this.d.a(32, this.f + "_" + this.e);
                this.c.removeUpdates(i);
                return;
            }
            return;
        }
        this.d.a(1946, 32);
    }

    public void d() {
        LocationListener h;
        Location lastKnownLocation;
        this.e = 0.0d;
        this.f = 0.0d;
        if (this.c.isProviderEnabled("gps")) {
            Location lastKnownLocation2 = this.c.getLastKnownLocation("gps");
            if (lastKnownLocation2 != null) {
                this.e = lastKnownLocation2.getLatitude();
                this.f = lastKnownLocation2.getLongitude();
            }
            h = h();
            lastKnownLocation = this.c.getLastKnownLocation("gps");
            if (lastKnownLocation != null) {
                this.e = lastKnownLocation.getLatitude();
                this.f = lastKnownLocation.getLongitude();
                this.d.a(64, this.f + "_" + this.e);
                this.c.removeUpdates(h);
                return;
            }
        }
        if (this.c.isProviderEnabled("network")) {
            h = i();
            lastKnownLocation = this.c.getLastKnownLocation("network");
            if (lastKnownLocation != null) {
                this.e = lastKnownLocation.getLatitude();
                this.f = lastKnownLocation.getLongitude();
                this.d.a(64, this.f + "_" + this.e);
                this.c.removeUpdates(h);
                return;
            }
        }
        if (this.e == 0.0d || this.f == 0.0d) {
            this.d.a(1946, 64);
        } else {
            this.d.a(64, this.f + "_" + this.e);
        }
    }

    private LocationListener h() {
        LocationListener anonymousClass1 = new LocationListener(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                com.yunva.im.sdk.lib.utils.a.d("LBS", "onProviderEnabled : provider=" + provider);
            }

            public void onProviderDisabled(String provider) {
                com.yunva.im.sdk.lib.utils.a.d("LBS", "onProviderDisabled : provider=" + provider);
            }

            public void onLocationChanged(Location location) {
            }
        };
        this.c.requestLocationUpdates("gps", 1000, 10.0f, anonymousClass1);
        return anonymousClass1;
    }

    private LocationListener i() {
        LocationListener anonymousClass2 = new LocationListener(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                com.yunva.im.sdk.lib.utils.a.d("LBS", "onProviderDisabled : provider=" + provider);
            }

            public void onLocationChanged(Location location) {
            }
        };
        this.c.requestLocationUpdates("network", 1000, 10.0f, anonymousClass2);
        return anonymousClass2;
    }

    public void e() {
        int phoneType = ((TelephonyManager) this.b.getSystemService("phone")).getPhoneType();
        List scanResults = ((WifiManager) this.b.getSystemService("wifi")).getScanResults();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanResult = (ScanResult) scanResults.get(i);
            if (!"".equals(scanResult.BSSID)) {
                stringBuffer.append("{");
                stringBuffer.append(scanResult.BSSID);
                stringBuffer.append("|");
                stringBuffer.append(WifiManager.calculateSignalLevel(scanResult.level, 5));
                stringBuffer.append("|");
                stringBuffer.append("}");
            }
            if (i >= 30) {
                break;
            }
        }
        stringBuffer.append("|").append(phoneType);
        if (stringBuffer.toString().length() == 1) {
            this.d.a(1940, 2);
        } else {
            this.d.a(2, stringBuffer.toString());
        }
    }

    public void f() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.b.getSystemService("phone");
            String networkOperator = telephonyManager.getNetworkOperator();
            int phoneType = telephonyManager.getPhoneType();
            if ("".equals(networkOperator) || networkOperator.length() <= 3) {
                this.d.a(1946, 4);
                return;
            }
            int parseInt = Integer.parseInt(networkOperator.substring(0, 3));
            int parseInt2 = Integer.parseInt(networkOperator.substring(3));
            List<NeighboringCellInfo> neighboringCellInfo = telephonyManager.getNeighboringCellInfo();
            StringBuffer stringBuffer = new StringBuffer();
            for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                stringBuffer.append("{");
                stringBuffer.append(parseInt);
                stringBuffer.append("|");
                stringBuffer.append(parseInt2);
                stringBuffer.append("|");
                stringBuffer.append(neighboringCellInfo2.getCid());
                stringBuffer.append("|");
                stringBuffer.append(neighboringCellInfo2.getLac());
                stringBuffer.append("|");
                stringBuffer.append((neighboringCellInfo2.getRssi() * 2) - 113);
                stringBuffer.append("|");
                stringBuffer.append("|");
                stringBuffer.append("}");
            }
            stringBuffer.append("|").append(phoneType);
            this.d.a(4, stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            this.d.a(1946, 4);
        }
    }

    public void g() {
        this.d.a(1946, 16);
    }
}
