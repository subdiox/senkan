package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zze implements Creator<zzd> {
    static void zza(zzd com_google_android_gms_wallet_firstparty_zzd, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzd.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zzd.zzbPC, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_firstparty_zzd.zzbPD, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjK(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzob(i);
    }

    public zzd zzjK(Parcel parcel) {
        byte[] bArr = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        byte[] bArr2 = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    bArr2 = zzb.zzt(parcel, zzaT);
                    break;
                case 3:
                    bArr = zzb.zzt(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzd(i, bArr2, bArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzd[] zzob(int i) {
        return new zzd[i];
    }
}
