package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class zza implements Creator<CommonWalletObject> {
    static void zza(CommonWalletObject commonWalletObject, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, commonWalletObject.getVersionCode());
        zzc.zza(parcel, 2, commonWalletObject.zzko, false);
        zzc.zza(parcel, 3, commonWalletObject.zzbOG, false);
        zzc.zza(parcel, 4, commonWalletObject.name, false);
        zzc.zza(parcel, 5, commonWalletObject.zzbOA, false);
        zzc.zza(parcel, 6, commonWalletObject.zzbOC, false);
        zzc.zza(parcel, 7, commonWalletObject.zzbOD, false);
        zzc.zza(parcel, 8, commonWalletObject.zzbOE, false);
        zzc.zza(parcel, 9, commonWalletObject.zzbOF, false);
        zzc.zzc(parcel, 10, commonWalletObject.state);
        zzc.zzc(parcel, 11, commonWalletObject.zzbOH, false);
        zzc.zza(parcel, 12, commonWalletObject.zzbOI, i, false);
        zzc.zzc(parcel, 13, commonWalletObject.zzbOJ, false);
        zzc.zza(parcel, 14, commonWalletObject.zzbOK, false);
        zzc.zza(parcel, 15, commonWalletObject.zzbOL, false);
        zzc.zzc(parcel, 16, commonWalletObject.zzbOM, false);
        zzc.zza(parcel, 17, commonWalletObject.zzbON);
        zzc.zzc(parcel, 18, commonWalletObject.zzbOO, false);
        zzc.zzc(parcel, 19, commonWalletObject.zzbOP, false);
        zzc.zzc(parcel, 20, commonWalletObject.zzbOQ, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjV(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzop(i);
    }

    public CommonWalletObject zzjV(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        int i2 = 0;
        ArrayList zzys = com.google.android.gms.common.util.zzb.zzys();
        zzl com_google_android_gms_wallet_wobs_zzl = null;
        ArrayList zzys2 = com.google.android.gms.common.util.zzb.zzys();
        String str9 = null;
        String str10 = null;
        ArrayList zzys3 = com.google.android.gms.common.util.zzb.zzys();
        boolean z = false;
        ArrayList zzys4 = com.google.android.gms.common.util.zzb.zzys();
        ArrayList zzys5 = com.google.android.gms.common.util.zzb.zzys();
        ArrayList zzys6 = com.google.android.gms.common.util.zzb.zzys();
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    str3 = zzb.zzq(parcel, zzaT);
                    break;
                case 5:
                    str4 = zzb.zzq(parcel, zzaT);
                    break;
                case 6:
                    str5 = zzb.zzq(parcel, zzaT);
                    break;
                case 7:
                    str6 = zzb.zzq(parcel, zzaT);
                    break;
                case 8:
                    str7 = zzb.zzq(parcel, zzaT);
                    break;
                case 9:
                    str8 = zzb.zzq(parcel, zzaT);
                    break;
                case 10:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 11:
                    zzys = zzb.zzc(parcel, zzaT, zzp.CREATOR);
                    break;
                case 12:
                    com_google_android_gms_wallet_wobs_zzl = (zzl) zzb.zza(parcel, zzaT, zzl.CREATOR);
                    break;
                case 13:
                    zzys2 = zzb.zzc(parcel, zzaT, LatLng.CREATOR);
                    break;
                case 14:
                    str9 = zzb.zzq(parcel, zzaT);
                    break;
                case 15:
                    str10 = zzb.zzq(parcel, zzaT);
                    break;
                case 16:
                    zzys3 = zzb.zzc(parcel, zzaT, zzd.CREATOR);
                    break;
                case 17:
                    z = zzb.zzc(parcel, zzaT);
                    break;
                case 18:
                    zzys4 = zzb.zzc(parcel, zzaT, zzn.CREATOR);
                    break;
                case 19:
                    zzys5 = zzb.zzc(parcel, zzaT, zzj.CREATOR);
                    break;
                case 20:
                    zzys6 = zzb.zzc(parcel, zzaT, zzn.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new CommonWalletObject(i, str, str2, str3, str4, str5, str6, str7, str8, i2, zzys, com_google_android_gms_wallet_wobs_zzl, zzys2, str9, str10, zzys3, z, zzys4, zzys5, zzys6);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zzb.zza("Overread allowed size end=" + zzaU, parcel);
    }

    public CommonWalletObject[] zzop(int i) {
        return new CommonWalletObject[i];
    }
}
