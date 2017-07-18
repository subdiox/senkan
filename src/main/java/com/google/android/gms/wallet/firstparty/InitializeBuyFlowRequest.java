package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public class InitializeBuyFlowRequest extends zza implements ReflectedParcelable {
    public static final Creator<InitializeBuyFlowRequest> CREATOR = new zzp();
    private final int mVersionCode;
    byte[][] zzbPL;

    InitializeBuyFlowRequest(int i, byte[][] bArr) {
        this.mVersionCode = i;
        this.zzbPL = bArr;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzp.zza(this, parcel, i);
    }
}
