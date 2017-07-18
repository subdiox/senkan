package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzh extends zza {
    public static final Creator<zzh> CREATOR = new zzi();
    private final int mVersionCode;
    zzq zzbPF;
    boolean zzbPG;

    zzh() {
        this.mVersionCode = 1;
    }

    zzh(int i, zzq com_google_android_gms_wallet_firstparty_zzq, boolean z) {
        this.mVersionCode = i;
        this.zzbPF = com_google_android_gms_wallet_firstparty_zzq;
        this.zzbPG = z;
        if (com_google_android_gms_wallet_firstparty_zzq == null) {
            throw new NullPointerException("WalletCustomTheme is required");
        }
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
