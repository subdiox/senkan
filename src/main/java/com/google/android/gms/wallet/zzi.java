package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

public class zzi implements Creator<GiftCardWalletObject> {
    static void zza(GiftCardWalletObject giftCardWalletObject, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, giftCardWalletObject.getVersionCode());
        zzc.zza(parcel, 2, giftCardWalletObject.zzbOk, i, false);
        zzc.zza(parcel, 3, giftCardWalletObject.zzbOl, false);
        zzc.zza(parcel, 4, giftCardWalletObject.pin, false);
        zzc.zza(parcel, 5, giftCardWalletObject.zzbOm, false);
        zzc.zza(parcel, 6, giftCardWalletObject.zzbOn);
        zzc.zza(parcel, 7, giftCardWalletObject.zzbOo, false);
        zzc.zza(parcel, 8, giftCardWalletObject.zzbOp);
        zzc.zza(parcel, 9, giftCardWalletObject.zzbOq, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjx(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznO(i);
    }

    public GiftCardWalletObject zzjx(Parcel parcel) {
        long j = 0;
        String str = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str2 = null;
        long j2 = 0;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        CommonWalletObject commonWalletObject = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    commonWalletObject = (CommonWalletObject) zzb.zza(parcel, zzaT, CommonWalletObject.CREATOR);
                    break;
                case 3:
                    str5 = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    str4 = zzb.zzq(parcel, zzaT);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, zzaT);
                    break;
                case 6:
                    j2 = zzb.zzi(parcel, zzaT);
                    break;
                case 7:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 8:
                    j = zzb.zzi(parcel, zzaT);
                    break;
                case 9:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new GiftCardWalletObject(i, commonWalletObject, str5, str4, str3, j2, str2, j, str);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public GiftCardWalletObject[] zznO(int i) {
        return new GiftCardWalletObject[i];
    }
}
