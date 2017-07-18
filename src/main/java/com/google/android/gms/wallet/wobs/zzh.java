package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzh implements Creator<zzg> {
    static void zza(zzg com_google_android_gms_wallet_wobs_zzg, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_wobs_zzg.getVersionCode());
        zzc.zzc(parcel, 2, com_google_android_gms_wallet_wobs_zzg.zzbQB);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_wobs_zzg.zzbQC, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_wobs_zzg.zzbQD);
        zzc.zza(parcel, 5, com_google_android_gms_wallet_wobs_zzg.zzbOo, false);
        zzc.zza(parcel, 6, com_google_android_gms_wallet_wobs_zzg.zzbQE);
        zzc.zzc(parcel, 7, com_google_android_gms_wallet_wobs_zzg.zzbQF);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjY(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzos(i);
    }

    public zzg zzjY(Parcel parcel) {
        String str = null;
        int i = 0;
        int zzaU = zzb.zzaU(parcel);
        double d = 0.0d;
        long j = 0;
        int i2 = -1;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i3 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    d = zzb.zzn(parcel, zzaT);
                    break;
                case 5:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 6:
                    j = zzb.zzi(parcel, zzaT);
                    break;
                case 7:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzg(i3, i, str2, d, str, j, i2);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzg[] zzos(int i) {
        return new zzg[i];
    }
}
