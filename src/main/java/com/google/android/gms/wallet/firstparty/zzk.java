package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzk implements Creator<zzj> {
    static void zza(zzj com_google_android_gms_wallet_firstparty_zzj, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzj.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zzj.zzbPH, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjN(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzoe(i);
    }

    public zzj zzjN(Parcel parcel) {
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
            return new zzj(i, bArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzj[] zzoe(int i) {
        return new zzj[i];
    }
}
