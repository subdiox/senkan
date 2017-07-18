package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zze implements Creator<zzd> {
    static void zza(zzd com_google_android_gms_wallet_wobs_zzd, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_wobs_zzd.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_wobs_zzd.zzbQx, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_wobs_zzd.zzbQy, false);
        zzc.zzc(parcel, 4, com_google_android_gms_wallet_wobs_zzd.zzbQz, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjX(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzor(i);
    }

    public zzd zzjX(Parcel parcel) {
        String str = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        ArrayList zzys = com.google.android.gms.common.util.zzb.zzys();
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
                    zzys = zzb.zzc(parcel, zzaT, zzb.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzd(i, str2, str, zzys);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzd[] zzor(int i) {
        return new zzd[i];
    }
}
