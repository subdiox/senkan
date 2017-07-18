package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.identity.intents.model.UserAddress;

public class zzg implements Creator<FullWallet> {
    static void zza(FullWallet fullWallet, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, fullWallet.getVersionCode());
        zzc.zza(parcel, 2, fullWallet.zzbNX, false);
        zzc.zza(parcel, 3, fullWallet.zzbNY, false);
        zzc.zza(parcel, 4, fullWallet.zzbNZ, i, false);
        zzc.zza(parcel, 5, fullWallet.zzbOa, false);
        zzc.zza(parcel, 6, fullWallet.zzbOb, i, false);
        zzc.zza(parcel, 7, fullWallet.zzbOc, i, false);
        zzc.zza(parcel, 8, fullWallet.zzbOd, false);
        zzc.zza(parcel, 9, fullWallet.zzbOe, i, false);
        zzc.zza(parcel, 10, fullWallet.zzbOf, i, false);
        zzc.zza(parcel, 11, fullWallet.zzbOg, i, false);
        zzc.zza(parcel, 12, fullWallet.zzbOh, i, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjv(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznM(i);
    }

    public FullWallet zzjv(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        ProxyCard proxyCard = null;
        String str3 = null;
        zza com_google_android_gms_wallet_zza = null;
        zza com_google_android_gms_wallet_zza2 = null;
        String[] strArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
        PaymentMethodToken paymentMethodToken = null;
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
                    proxyCard = (ProxyCard) zzb.zza(parcel, zzaT, ProxyCard.CREATOR);
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
                    strArr = zzb.zzC(parcel, zzaT);
                    break;
                case 9:
                    userAddress = (UserAddress) zzb.zza(parcel, zzaT, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress2 = (UserAddress) zzb.zza(parcel, zzaT, UserAddress.CREATOR);
                    break;
                case 11:
                    instrumentInfoArr = (InstrumentInfo[]) zzb.zzb(parcel, zzaT, InstrumentInfo.CREATOR);
                    break;
                case 12:
                    paymentMethodToken = (PaymentMethodToken) zzb.zza(parcel, zzaT, PaymentMethodToken.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new FullWallet(i, str, str2, proxyCard, str3, com_google_android_gms_wallet_zza, com_google_android_gms_wallet_zza2, strArr, userAddress, userAddress2, instrumentInfoArr, paymentMethodToken);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public FullWallet[] zznM(int i) {
        return new FullWallet[i];
    }
}
