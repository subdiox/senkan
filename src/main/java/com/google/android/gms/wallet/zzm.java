package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.zzd;
import com.google.android.gms.wallet.wobs.zzf;
import com.google.android.gms.wallet.wobs.zzj;
import com.google.android.gms.wallet.wobs.zzl;
import com.google.android.gms.wallet.wobs.zzn;
import com.google.android.gms.wallet.wobs.zzp;
import java.util.ArrayList;

public class zzm implements Creator<LoyaltyWalletObject> {
    static void zza(LoyaltyWalletObject loyaltyWalletObject, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, loyaltyWalletObject.getVersionCode());
        zzc.zza(parcel, 2, loyaltyWalletObject.zzko, false);
        zzc.zza(parcel, 3, loyaltyWalletObject.zzbOz, false);
        zzc.zza(parcel, 4, loyaltyWalletObject.zzbOA, false);
        zzc.zza(parcel, 5, loyaltyWalletObject.zzbOB, false);
        zzc.zza(parcel, 6, loyaltyWalletObject.zzaIv, false);
        zzc.zza(parcel, 7, loyaltyWalletObject.zzbOC, false);
        zzc.zza(parcel, 8, loyaltyWalletObject.zzbOD, false);
        zzc.zza(parcel, 9, loyaltyWalletObject.zzbOE, false);
        zzc.zza(parcel, 10, loyaltyWalletObject.zzbOF, false);
        zzc.zza(parcel, 11, loyaltyWalletObject.zzbOG, false);
        zzc.zzc(parcel, 12, loyaltyWalletObject.state);
        zzc.zzc(parcel, 13, loyaltyWalletObject.zzbOH, false);
        zzc.zza(parcel, 14, loyaltyWalletObject.zzbOI, i, false);
        zzc.zzc(parcel, 15, loyaltyWalletObject.zzbOJ, false);
        zzc.zza(parcel, 16, loyaltyWalletObject.zzbOK, false);
        zzc.zza(parcel, 17, loyaltyWalletObject.zzbOL, false);
        zzc.zzc(parcel, 18, loyaltyWalletObject.zzbOM, false);
        zzc.zza(parcel, 19, loyaltyWalletObject.zzbON);
        zzc.zzc(parcel, 20, loyaltyWalletObject.zzbOO, false);
        zzc.zzc(parcel, 21, loyaltyWalletObject.zzbOP, false);
        zzc.zzc(parcel, 22, loyaltyWalletObject.zzbOQ, false);
        zzc.zza(parcel, 23, loyaltyWalletObject.zzbOR, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjB(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznS(i);
    }

    public LoyaltyWalletObject zzjB(Parcel parcel) {
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
        String str9 = null;
        String str10 = null;
        int i2 = 0;
        ArrayList zzys = com.google.android.gms.common.util.zzb.zzys();
        zzl com_google_android_gms_wallet_wobs_zzl = null;
        ArrayList zzys2 = com.google.android.gms.common.util.zzb.zzys();
        String str11 = null;
        String str12 = null;
        ArrayList zzys3 = com.google.android.gms.common.util.zzb.zzys();
        boolean z = false;
        ArrayList zzys4 = com.google.android.gms.common.util.zzb.zzys();
        ArrayList zzys5 = com.google.android.gms.common.util.zzb.zzys();
        ArrayList zzys6 = com.google.android.gms.common.util.zzb.zzys();
        zzf com_google_android_gms_wallet_wobs_zzf = null;
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
                    str9 = zzb.zzq(parcel, zzaT);
                    break;
                case 11:
                    str10 = zzb.zzq(parcel, zzaT);
                    break;
                case 12:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 13:
                    zzys = zzb.zzc(parcel, zzaT, zzp.CREATOR);
                    break;
                case 14:
                    com_google_android_gms_wallet_wobs_zzl = (zzl) zzb.zza(parcel, zzaT, zzl.CREATOR);
                    break;
                case 15:
                    zzys2 = zzb.zzc(parcel, zzaT, LatLng.CREATOR);
                    break;
                case 16:
                    str11 = zzb.zzq(parcel, zzaT);
                    break;
                case 17:
                    str12 = zzb.zzq(parcel, zzaT);
                    break;
                case 18:
                    zzys3 = zzb.zzc(parcel, zzaT, zzd.CREATOR);
                    break;
                case 19:
                    z = zzb.zzc(parcel, zzaT);
                    break;
                case 20:
                    zzys4 = zzb.zzc(parcel, zzaT, zzn.CREATOR);
                    break;
                case 21:
                    zzys5 = zzb.zzc(parcel, zzaT, zzj.CREATOR);
                    break;
                case 22:
                    zzys6 = zzb.zzc(parcel, zzaT, zzn.CREATOR);
                    break;
                case 23:
                    com_google_android_gms_wallet_wobs_zzf = (zzf) zzb.zza(parcel, zzaT, zzf.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new LoyaltyWalletObject(i, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, i2, zzys, com_google_android_gms_wallet_wobs_zzl, zzys2, str11, str12, zzys3, z, zzys4, zzys5, zzys6, com_google_android_gms_wallet_wobs_zzf);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public LoyaltyWalletObject[] zznS(int i) {
        return new LoyaltyWalletObject[i];
    }
}
