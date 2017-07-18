package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzq implements Creator<zzp> {
    static void zza(zzp com_google_android_gms_wallet_wobs_zzp, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_wobs_zzp.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_wobs_zzp.zzbQG, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_wobs_zzp.body, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_wobs_zzp.zzbQK, i, false);
        zzc.zza(parcel, 5, com_google_android_gms_wallet_wobs_zzp.zzbQL, i, false);
        zzc.zza(parcel, 6, com_google_android_gms_wallet_wobs_zzp.zzbQM, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzkd(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzox(i);
    }

    public zzp zzkd(Parcel parcel) {
        zzn com_google_android_gms_wallet_wobs_zzn = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        zzn com_google_android_gms_wallet_wobs_zzn2 = null;
        zzl com_google_android_gms_wallet_wobs_zzl = null;
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
                    com_google_android_gms_wallet_wobs_zzl = (zzl) zzb.zza(parcel, zzaT, zzl.CREATOR);
                    break;
                case 5:
                    com_google_android_gms_wallet_wobs_zzn2 = (zzn) zzb.zza(parcel, zzaT, zzn.CREATOR);
                    break;
                case 6:
                    com_google_android_gms_wallet_wobs_zzn = (zzn) zzb.zza(parcel, zzaT, zzn.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzp(i, str2, str, com_google_android_gms_wallet_wobs_zzl, com_google_android_gms_wallet_wobs_zzn2, com_google_android_gms_wallet_wobs_zzn);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzp[] zzox(int i) {
        return new zzp[i];
    }
}
