package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzo implements Creator<zzn> {
    static void zza(zzn com_google_android_gms_wallet_firstparty_zzn, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_firstparty_zzn.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_firstparty_zzn.zzbPJ, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_firstparty_zzn.zzbPK, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjP(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzog(i);
    }

    public zzn zzjP(Parcel parcel) {
        byte[][] bArr = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String[] strArr = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    strArr = zzb.zzC(parcel, zzaT);
                    break;
                case 3:
                    bArr = zzb.zzu(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zzn(i, strArr, bArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zzn[] zzog(int i) {
        return new zzn[i];
    }
}
