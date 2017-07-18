package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

public class zzq implements Creator<OfferWalletObject> {
    static void zza(OfferWalletObject offerWalletObject, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, offerWalletObject.getVersionCode());
        zzc.zza(parcel, 2, offerWalletObject.zzko, false);
        zzc.zza(parcel, 3, offerWalletObject.zzbPl, false);
        zzc.zza(parcel, 4, offerWalletObject.zzbOk, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjF(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznW(i);
    }

    public OfferWalletObject zzjF(Parcel parcel) {
        CommonWalletObject commonWalletObject = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    commonWalletObject = (CommonWalletObject) zzb.zza(parcel, zzaT, CommonWalletObject.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new OfferWalletObject(i, str2, str, commonWalletObject);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public OfferWalletObject[] zznW(int i) {
        return new OfferWalletObject[i];
    }
}
