package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class PaymentMethodToken extends zza {
    public static final Creator<PaymentMethodToken> CREATOR = new zzr();
    private final int mVersionCode;
    int zzbPm;
    String zzbwP;

    private PaymentMethodToken() {
        this.mVersionCode = 1;
    }

    PaymentMethodToken(int i, int i2, String str) {
        this.mVersionCode = i;
        this.zzbPm = i2;
        this.zzbwP = str;
    }

    public int getPaymentMethodTokenizationType() {
        return this.zzbPm;
    }

    public String getToken() {
        return this.zzbwP;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzr.zza(this, parcel, i);
    }
}
