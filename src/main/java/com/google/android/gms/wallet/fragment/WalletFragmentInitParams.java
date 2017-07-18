package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class WalletFragmentInitParams extends zza implements ReflectedParcelable {
    public static final Creator<WalletFragmentInitParams> CREATOR = new zza();
    final int mVersionCode;
    private String zzaht;
    private MaskedWalletRequest zzbPV;
    private MaskedWallet zzbPW;
    private int zzbQj;

    public final class Builder {
        final /* synthetic */ WalletFragmentInitParams zzbQk;

        private Builder(WalletFragmentInitParams walletFragmentInitParams) {
            this.zzbQk = walletFragmentInitParams;
        }

        public WalletFragmentInitParams build() {
            boolean z = true;
            boolean z2 = (this.zzbQk.zzbPW != null && this.zzbQk.zzbPV == null) || (this.zzbQk.zzbPW == null && this.zzbQk.zzbPV != null);
            zzac.zza(z2, (Object) "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            if (this.zzbQk.zzbQj < 0) {
                z = false;
            }
            zzac.zza(z, (Object) "masked wallet request code is required and must be non-negative");
            return this.zzbQk;
        }

        public Builder setAccountName(String str) {
            this.zzbQk.zzaht = str;
            return this;
        }

        public Builder setMaskedWallet(MaskedWallet maskedWallet) {
            this.zzbQk.zzbPW = maskedWallet;
            return this;
        }

        public Builder setMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            this.zzbQk.zzbPV = maskedWalletRequest;
            return this;
        }

        public Builder setMaskedWalletRequestCode(int i) {
            this.zzbQk.zzbQj = i;
            return this;
        }
    }

    private WalletFragmentInitParams() {
        this.mVersionCode = 1;
        this.zzbQj = -1;
    }

    WalletFragmentInitParams(int i, String str, MaskedWalletRequest maskedWalletRequest, int i2, MaskedWallet maskedWallet) {
        this.mVersionCode = i;
        this.zzaht = str;
        this.zzbPV = maskedWalletRequest;
        this.zzbQj = i2;
        this.zzbPW = maskedWallet;
    }

    public static Builder newBuilder() {
        WalletFragmentInitParams walletFragmentInitParams = new WalletFragmentInitParams();
        walletFragmentInitParams.getClass();
        return new Builder();
    }

    public String getAccountName() {
        return this.zzaht;
    }

    public MaskedWallet getMaskedWallet() {
        return this.zzbPW;
    }

    public MaskedWalletRequest getMaskedWalletRequest() {
        return this.zzbPV;
    }

    public int getMaskedWalletRequestCode() {
        return this.zzbQj;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }
}
