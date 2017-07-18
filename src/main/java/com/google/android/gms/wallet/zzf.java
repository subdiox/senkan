package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzf implements Creator<zze> {
    static void zza(zze com_google_android_gms_wallet_zze, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, com_google_android_gms_wallet_zze.getVersionCode());
        zzc.zza(parcel, 2, com_google_android_gms_wallet_zze.zzbNU, i, false);
        zzc.zza(parcel, 3, com_google_android_gms_wallet_zze.zzbNV, i, false);
        zzc.zza(parcel, 4, com_google_android_gms_wallet_zze.zzbNW, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzju(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznL(i);
    }

    public zze zzju(Parcel parcel) {
        GiftCardWalletObject giftCardWalletObject = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        OfferWalletObject offerWalletObject = null;
        LoyaltyWalletObject loyaltyWalletObject = null;
        while (parcel.dataPosition() < zzaU) {
            OfferWalletObject offerWalletObject2;
            LoyaltyWalletObject loyaltyWalletObject2;
            int zzg;
            GiftCardWalletObject giftCardWalletObject2;
            int zzaT = zzb.zzaT(parcel);
            GiftCardWalletObject giftCardWalletObject3;
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    giftCardWalletObject3 = giftCardWalletObject;
                    offerWalletObject2 = offerWalletObject;
                    loyaltyWalletObject2 = loyaltyWalletObject;
                    zzg = zzb.zzg(parcel, zzaT);
                    giftCardWalletObject2 = giftCardWalletObject3;
                    break;
                case 2:
                    zzg = i;
                    OfferWalletObject offerWalletObject3 = offerWalletObject;
                    loyaltyWalletObject2 = (LoyaltyWalletObject) zzb.zza(parcel, zzaT, LoyaltyWalletObject.CREATOR);
                    giftCardWalletObject2 = giftCardWalletObject;
                    offerWalletObject2 = offerWalletObject3;
                    break;
                case 3:
                    loyaltyWalletObject2 = loyaltyWalletObject;
                    zzg = i;
                    giftCardWalletObject3 = giftCardWalletObject;
                    offerWalletObject2 = (OfferWalletObject) zzb.zza(parcel, zzaT, OfferWalletObject.CREATOR);
                    giftCardWalletObject2 = giftCardWalletObject3;
                    break;
                case 4:
                    giftCardWalletObject2 = (GiftCardWalletObject) zzb.zza(parcel, zzaT, GiftCardWalletObject.CREATOR);
                    offerWalletObject2 = offerWalletObject;
                    loyaltyWalletObject2 = loyaltyWalletObject;
                    zzg = i;
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    giftCardWalletObject2 = giftCardWalletObject;
                    offerWalletObject2 = offerWalletObject;
                    loyaltyWalletObject2 = loyaltyWalletObject;
                    zzg = i;
                    break;
            }
            i = zzg;
            loyaltyWalletObject = loyaltyWalletObject2;
            offerWalletObject = offerWalletObject2;
            giftCardWalletObject = giftCardWalletObject2;
        }
        if (parcel.dataPosition() == zzaU) {
            return new zze(i, loyaltyWalletObject, offerWalletObject, giftCardWalletObject);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public zze[] zznL(int i) {
        return new zze[i];
    }
}
