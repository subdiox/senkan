package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzb implements Creator<WalletFragmentOptions> {
    static void zza(WalletFragmentOptions walletFragmentOptions, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, walletFragmentOptions.mVersionCode);
        zzc.zzc(parcel, 2, walletFragmentOptions.getEnvironment());
        zzc.zzc(parcel, 3, walletFragmentOptions.getTheme());
        zzc.zza(parcel, 4, walletFragmentOptions.getFragmentStyle(), i, false);
        zzc.zzc(parcel, 5, walletFragmentOptions.getMode());
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjT(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzok(i);
    }

    public WalletFragmentOptions zzjT(Parcel parcel) {
        int i = 1;
        int i2 = 0;
        int zzaU = com.google.android.gms.common.internal.safeparcel.zzb.zzaU(parcel);
        WalletFragmentStyle walletFragmentStyle = null;
        int i3 = 1;
        int i4 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = com.google.android.gms.common.internal.safeparcel.zzb.zzaT(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzcW(zzaT)) {
                case 1:
                    i4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    i3 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaT);
                    break;
                case 3:
                    i2 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaT);
                    break;
                case 4:
                    walletFragmentStyle = (WalletFragmentStyle) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, zzaT, WalletFragmentStyle.CREATOR);
                    break;
                case 5:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaT);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new WalletFragmentOptions(i4, i3, i2, walletFragmentStyle, i);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public WalletFragmentOptions[] zzok(int i) {
        return new WalletFragmentOptions[i];
    }
}
