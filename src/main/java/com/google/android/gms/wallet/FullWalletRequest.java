package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class FullWalletRequest extends zza implements ReflectedParcelable {
    public static final Creator<FullWalletRequest> CREATOR = new zzh();
    private final int mVersionCode;
    String zzbNX;
    String zzbNY;
    Cart zzbOi;

    public final class Builder {
        final /* synthetic */ FullWalletRequest zzbOj;

        private Builder(FullWalletRequest fullWalletRequest) {
            this.zzbOj = fullWalletRequest;
        }

        public FullWalletRequest build() {
            return this.zzbOj;
        }

        public Builder setCart(Cart cart) {
            this.zzbOj.zzbOi = cart;
            return this;
        }

        public Builder setGoogleTransactionId(String str) {
            this.zzbOj.zzbNX = str;
            return this;
        }

        public Builder setMerchantTransactionId(String str) {
            this.zzbOj.zzbNY = str;
            return this;
        }
    }

    FullWalletRequest() {
        this.mVersionCode = 1;
    }

    FullWalletRequest(int i, String str, String str2, Cart cart) {
        this.mVersionCode = i;
        this.zzbNX = str;
        this.zzbNY = str2;
        this.zzbOi = cart;
    }

    public static Builder newBuilder() {
        FullWalletRequest fullWalletRequest = new FullWalletRequest();
        fullWalletRequest.getClass();
        return new Builder();
    }

    public Cart getCart() {
        return this.zzbOi;
    }

    public String getGoogleTransactionId() {
        return this.zzbNX;
    }

    public String getMerchantTransactionId() {
        return this.zzbNY;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.zza(this, parcel, i);
    }
}
