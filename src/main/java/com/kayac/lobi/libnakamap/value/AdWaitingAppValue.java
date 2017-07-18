package com.kayac.lobi.libnakamap.value;

public class AdWaitingAppValue {
    private final String mAdId;
    private final String mClientId;
    private final boolean mCountConversion;
    private final long mDate;
    private final String mPackage;

    public AdWaitingAppValue(String adId, String packageName, String clientId, long date, boolean countConversion) {
        this.mAdId = adId;
        this.mPackage = packageName;
        this.mClientId = clientId;
        this.mDate = date;
        this.mCountConversion = countConversion;
    }

    public String getAdId() {
        return this.mAdId;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public String getClientId() {
        return this.mClientId;
    }

    public long getDate() {
        return this.mDate;
    }

    public boolean getCountConversion() {
        return this.mCountConversion;
    }
}
