package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzi implements Creator<zzh> {
    static void zza(zzh com_google_android_gms_wallet_firstparty_zzh, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzh.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zzh.zzbPF, i, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_firstparty_zzh.zzbPG);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjM(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzod(i);
    }

    public zzh zzjM(Parcel parcel) {
        boolean z = false;
        int zzaU = zzb.zzaU(parcel);
        zzq com_google_android_gms_wallet_firstparty_zzq = null;
        int i = 0;
        while (parcel.dataPosition() < zzaU) {
            zzq com_google_android_gms_wallet_firstparty_zzq2;
            int zzg;
            boolean z2;
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    boolean z3 = z;
                    com_google_android_gms_wallet_firstparty_zzq2 = com_google_android_gms_wallet_firstparty_zzq;
                    zzg = zzb.zzg(parcel, zzaT);
                    z2 = z3;
                    break;
                case 2:
                    zzg = i;
                    zzq com_google_android_gms_wallet_firstparty_zzq3 = (zzq) zzb.zza(parcel, zzaT, zzq.CREATOR);
                    z2 = z;
                    com_google_android_gms_wallet_firstparty_zzq2 = com_google_android_gms_wallet_firstparty_zzq3;
                    break;
                case 3:
                    z2 = zzb.zzc(parcel, zzaT);
                    com_google_android_gms_wallet_firstparty_zzq2 = com_google_android_gms_wallet_firstparty_zzq;
                    zzg = i;
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    z2 = z;
                    com_google_android_gms_wallet_firstparty_zzq2 = com_google_android_gms_wallet_firstparty_zzq;
                    zzg = i;
                    break;
            }
            i = zzg;
            com_google_android_gms_wallet_firstparty_zzq = com_google_android_gms_wallet_firstparty_zzq2;
            z = z2;
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzh(i, com_google_android_gms_wallet_firstparty_zzq, z);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzh[] zzod(int i) {
        return new zzh[i];
    }
}
