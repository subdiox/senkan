package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzg implements Creator<zzf> {
    static void zza(zzf com_google_android_gms_wallet_firstparty_zzf, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzf.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zzf.zzbPE, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjL(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzoc(i);
    }

    public zzf zzjL(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        byte[] bArr = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    bArr = zzb.zzt(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzf(i, bArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzf[] zzoc(int i) {
        return new zzf[i];
    }
}
