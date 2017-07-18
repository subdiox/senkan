package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public class zza implements Creator<WalletFragmentInitParams> {
    static void zza(WalletFragmentInitParams walletFragmentInitParams, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, walletFragmentInitParams.mVersionCode);
        zzc.zza(parcel, 2, walletFragmentInitParams.getAccountName(), false);
        zzc.zza(parcel, 3, walletFragmentInitParams.getMaskedWalletRequest(), i, false);
        zzc.zzc(parcel, 4, walletFragmentInitParams.getMaskedWalletRequestCode());
        zzc.zza(parcel, 5, walletFragmentInitParams.getMaskedWallet(), i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjS(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzoj(i);
    }

    public WalletFragmentInitParams zzjS(Parcel parcel) {
        MaskedWallet maskedWallet = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        int i2 = -1;
        MaskedWalletRequest maskedWalletRequest = null;
        String str = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    maskedWalletRequest = (MaskedWalletRequest) zzb.zza(parcel, zzaT, MaskedWalletRequest.CREATOR);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 5:
                    maskedWallet = (MaskedWallet) zzb.zza(parcel, zzaT, MaskedWallet.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new WalletFragmentInitParams(i, str, maskedWalletRequest, i2, maskedWallet);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zzb.zza("Overread allowed size end=" + zzaU, parcel);
    }

    public WalletFragmentInitParams[] zzoj(int i) {
        return new WalletFragmentInitParams[i];
    }
}
