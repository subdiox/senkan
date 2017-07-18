package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzm implements Creator<zzl> {
    static void zza(zzl com_google_android_gms_wallet_wobs_zzl, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_wobs_zzl.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_wobs_zzl.zzbQH);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_wobs_zzl.zzbQI);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzkb(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzov(i);
    }

    public zzl zzkb(Parcel parcel) {
        long j = 0;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        long j2 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    j2 = zzb.zzi(parcel, zzaT);
                    break;
                case 3:
                    j = zzb.zzi(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzl(i, j2, j);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzl[] zzov(int i) {
        return new zzl[i];
    }
}
