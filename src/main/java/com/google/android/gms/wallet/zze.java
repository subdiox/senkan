package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zze extends zza {
    public static final Creator<zze> CREATOR = new zzf();
    private final int mVersionCode;
    LoyaltyWalletObject zzbNU;
    OfferWalletObject zzbNV;
    GiftCardWalletObject zzbNW;

    zze() {
        this.mVersionCode = 3;
    }

    zze(int i, LoyaltyWalletObject loyaltyWalletObject, OfferWalletObject offerWalletObject, GiftCardWalletObject giftCardWalletObject) {
        this.mVersionCode = i;
        this.zzbNU = loyaltyWalletObject;
        this.zzbNV = offerWalletObject;
        this.zzbNW = giftCardWalletObject;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzf.zza(this, parcel, i);
    }
}
