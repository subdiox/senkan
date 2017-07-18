package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzm implements Creator<zzl> {
    static void zza(zzl com_google_android_gms_wallet_firstparty_zzl, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzl.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zzl.zzbPI, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjO(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzof(i);
    }

    public zzl zzjO(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        int[] iArr = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    iArr = zzb.zzw(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzl(i, iArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzl[] zzof(int i) {
        return new zzl[i];
    }
}
