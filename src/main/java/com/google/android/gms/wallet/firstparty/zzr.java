package com.google.android.gms.wallet.firstparty;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzr implements Creator<zzq> {
    static void zza(zzq com_google_android_gms_wallet_firstparty_zzq, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzq.mVersionCode);
        zzc.zzc(parcel, 2, com_google_android_gms_wallet_firstparty_zzq.zzbPM);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_firstparty_zzq.zzbPN, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_firstparty_zzq.zzbPO, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjR(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzoi(i);
    }

    public zzq zzjR(Parcel parcel) {
        String str = null;
        int i = 0;
        int zzaU = zzb.zzaU(parcel);
        Bundle bundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 3:
                    bundle = zzb.zzs(parcel, zzaT);
                    break;
                case 4:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzq(i2, i, bundle, str);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzq[] zzoi(int i) {
        return new zzq[i];
    }
}
