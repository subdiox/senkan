package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.identity.intents.model.UserAddress;

public class zzn implements Creator<MaskedWallet> {
    static void zza(MaskedWallet maskedWallet, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, maskedWallet.getVersionCode());
        zzc.zza(parcel, 2, maskedWallet.zzbNX, false);
        zzc.zza(parcel, 3, maskedWallet.zzbNY, false);
        zzc.zza(parcel, 4, maskedWallet.zzbOd, false);
        zzc.zza(parcel, 5, maskedWallet.zzbOa, false);
        zzc.zza(parcel, 6, maskedWallet.zzbOb, i, false);
        zzc.zza(parcel, 7, maskedWallet.zzbOc, i, false);
        zzc.zza(parcel, 8, maskedWallet.zzbOS, i, false);
        zzc.zza(parcel, 9, maskedWallet.zzbOT, i, false);
        zzc.zza(parcel, 10, maskedWallet.zzbOe, i, false);
        zzc.zza(parcel, 11, maskedWallet.zzbOf, i, false);
        zzc.zza(parcel, 12, maskedWallet.zzbOg, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjC(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznT(i);
    }

    public MaskedWallet zzjC(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String[] strArr = null;
        String str3 = null;
        zza com_google_android_gms_wallet_zza = null;
        zza com_google_android_gms_wallet_zza2 = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
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
                    strArr = zzb.zzC(parcel, zzaT);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, zzaT);
                    break;
                case 6:
                    com_google_android_gms_wallet_zza = (zza) zzb.zza(parcel, zzaT, zza.CREATOR);
                    break;
                case 7:
                    com_google_android_gms_wallet_zza2 = (zza) zzb.zza(parcel, zzaT, zza.CREATOR);
                    break;
                case 8:
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) zzb.zzb(parcel, zzaT, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) zzb.zzb(parcel, zzaT, OfferWalletObject.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) zzb.zza(parcel, zzaT, UserAddress.CREATOR);
                    break;
                case 11:
                    userAddress2 = (UserAddress) zzb.zza(parcel, zzaT, UserAddress.CREATOR);
                    break;
                case 12:
                    instrumentInfoArr = (InstrumentInfo[]) zzb.zzb(parcel, zzaT, InstrumentInfo.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new MaskedWallet(i, str, str2, strArr, str3, com_google_android_gms_wallet_zza, com_google_android_gms_wallet_zza2, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress, userAddress2, instrumentInfoArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public MaskedWallet[] zznT(int i) {
        return new MaskedWallet[i];
    }
}
