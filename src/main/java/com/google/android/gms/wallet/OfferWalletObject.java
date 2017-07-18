package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

public final class OfferWalletObject extends zza {
    public static final Creator<OfferWalletObject> CREATOR = new zzq();
    private final int mVersionCode;
    CommonWalletObject zzbOk;
    String zzbPl;
    String zzko;

    OfferWalletObject() {
        this.mVersionCode = 3;
    }

    OfferWalletObject(int i, String str, String str2, CommonWalletObject commonWalletObject) {
        this.mVersionCode = i;
        this.zzbPl = str2;
        if (i < 3) {
            this.zzbOk = CommonWalletObject.zzSA().zzip(str).zzSB();
        } else {
            this.zzbOk = commonWalletObject;
        }
    }

    public String getId() {
        return this.zzbOk.getId();
    }

    public String getRedemptionCode() {
        return this.zzbPl;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzq.zza(this, parcel, i);
    }
}
