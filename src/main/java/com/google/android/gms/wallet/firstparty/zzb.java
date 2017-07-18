package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzb implements Creator<zza> {
    static void zza(zza com_google_android_gms_wallet_firstparty_zza, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zza.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zza.zzbPz, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_firstparty_zza.zzbPA, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_firstparty_zza.zzbPB, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjJ(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzoa(i);
    }

    public zza zzjJ(Parcel parcel) {
        zzq com_google_android_gms_wallet_firstparty_zzq = null;
        int zzaU = com.google.android.gms.common.internal.safeparcel.zzb.zzaU(parcel);
        int i = 0;
        byte[] bArr = null;
        byte[] bArr2 = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = com.google.android.gms.common.internal.safeparcel.zzb.zzaT(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzcW(zzaT)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    bArr2 = com.google.android.gms.common.internal.safeparcel.zzb.zzt(parcel, zzaT);
                    break;
                case 3:
                    bArr = com.google.android.gms.common.internal.safeparcel.zzb.zzt(parcel, zzaT);
                    break;
                case 4:
                    com_google_android_gms_wallet_firstparty_zzq = (zzq) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, zzaT, zzq.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zza(i, bArr2, bArr, com_google_android_gms_wallet_firstparty_zzq);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zza[] zzoa(int i) {
        return new zza[i];
    }
}
