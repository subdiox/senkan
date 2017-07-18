package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzh implements Creator<FullWalletRequest> {
    static void zza(FullWalletRequest fullWalletRequest, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, fullWalletRequest.getVersionCode());
        zzc.zza(parcel, 2, fullWalletRequest.zzbNX, false);
        zzc.zza(parcel, 3, fullWalletRequest.zzbNY, false);
        zzc.zza(parcel, 4, fullWalletRequest.zzbOi, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjw(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznN(i);
    }

    public FullWalletRequest zzjw(Parcel parcel) {
        Cart cart = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    cart = (Cart) zzb.zza(parcel, zzaT, Cart.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new FullWalletRequest(i, str2, str, cart);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public FullWalletRequest[] zznN(int i) {
        return new FullWalletRequest[i];
    }
}
