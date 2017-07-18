package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzi implements Creator<zzf> {
    static void zza(zzf com_google_android_gms_wallet_wobs_zzf, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_wobs_zzf.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_wobs_zzf.label, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_wobs_zzf.zzbQA, i, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_wobs_zzf.type, false);
        zzc.zza(parcel, 5, com_google_android_gms_wallet_wobs_zzf.zzbOI, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjZ(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzot(i);
    }

    public zzf zzjZ(Parcel parcel) {
        zzl com_google_android_gms_wallet_wobs_zzl = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        zzg com_google_android_gms_wallet_wobs_zzg = null;
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
                    com_google_android_gms_wallet_wobs_zzg = (zzg) zzb.zza(parcel, zzaT, zzg.CREATOR);
                    break;
                case 4:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 5:
                    com_google_android_gms_wallet_wobs_zzl = (zzl) zzb.zza(parcel, zzaT, zzl.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzf(i, str2, com_google_android_gms_wallet_wobs_zzg, str, com_google_android_gms_wallet_wobs_zzl);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzf[] zzot(int i) {
        return new zzf[i];
    }
}
