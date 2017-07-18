package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

public final class GiftCardWalletObject extends zza {
    public static final Creator<GiftCardWalletObject> CREATOR = new zzi();
    final int mVersionCode;
    String pin;
    CommonWalletObject zzbOk;
    String zzbOl;
    String zzbOm;
    long zzbOn;
    String zzbOo;
    long zzbOp;
    String zzbOq;

    GiftCardWalletObject() {
        this.zzbOk = CommonWalletObject.zzSA().zzSB();
        this.mVersionCode = 1;
    }

    GiftCardWalletObject(int i, CommonWalletObject commonWalletObject, String str, String str2, String str3, long j, String str4, long j2, String str5) {
        this.zzbOk = CommonWalletObject.zzSA().zzSB();
        this.mVersionCode = i;
        this.zzbOk = commonWalletObject;
        this.zzbOl = str;
        this.pin = str2;
        this.zzbOn = j;
        this.zzbOo = str4;
        this.zzbOp = j2;
        this.zzbOq = str5;
        this.zzbOm = str3;
    }

    public String getId() {
        return this.zzbOk.getId();
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
