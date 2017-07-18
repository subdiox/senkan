package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzb implements Creator<zza> {
    static void zza(zza com_google_android_gms_wallet_zza, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_zza.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_zza.name, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_zza.zzbgO, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_zza.zzbgP, false);
        zzc.zza(parcel, 5, com_google_android_gms_wallet_zza.zzbgQ, false);
        zzc.zza(parcel, 6, com_google_android_gms_wallet_zza.zzUe, false);
        zzc.zza(parcel, 7, com_google_android_gms_wallet_zza.zzbNO, false);
        zzc.zza(parcel, 8, com_google_android_gms_wallet_zza.zzbNP, false);
        zzc.zza(parcel, 9, com_google_android_gms_wallet_zza.zzbgV, false);
        zzc.zza(parcel, 10, com_google_android_gms_wallet_zza.phoneNumber, false);
        zzc.zza(parcel, 11, com_google_android_gms_wallet_zza.zzbgX);
        zzc.zza(parcel, 12, com_google_android_gms_wallet_zza.zzbgY, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjr(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznI(i);
    }

    public zza zzjr(Parcel parcel) {
        int zzaU = com.google.android.gms.common.internal.safeparcel.zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        boolean z = false;
        String str10 = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = com.google.android.gms.common.internal.safeparcel.zzb.zzaT(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzcW(zzaT)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str2 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    str3 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 5:
                    str4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 6:
                    str5 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 7:
                    str6 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 8:
                    str7 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 9:
                    str8 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 10:
                    str9 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                case 11:
                    z = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, zzaT);
                    break;
                case 12:
                    str10 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, zzaT);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new zza(i, str, str2, str3, str4, str5, str6, str7, str8, str9, z, str10);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zza[] zznI(int i) {
        return new zza[i];
    }
}
