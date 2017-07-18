package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;

public class zzo implements Creator<MaskedWalletRequest> {
    static void zza(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, maskedWalletRequest.getVersionCode());
        zzc.zza(parcel, 2, maskedWalletRequest.zzbNY, false);
        zzc.zza(parcel, 3, maskedWalletRequest.zzbOV);
        zzc.zza(parcel, 4, maskedWalletRequest.zzbOW);
        zzc.zza(parcel, 5, maskedWalletRequest.zzbOX);
        zzc.zza(parcel, 6, maskedWalletRequest.zzbOY, false);
        zzc.zza(parcel, 7, maskedWalletRequest.zzbNR, false);
        zzc.zza(parcel, 8, maskedWalletRequest.zzbOZ, false);
        zzc.zza(parcel, 9, maskedWalletRequest.zzbOi, i, false);
        zzc.zza(parcel, 10, maskedWalletRequest.zzbPa);
        zzc.zza(parcel, 11, maskedWalletRequest.zzbPb);
        zzc.zza(parcel, 12, maskedWalletRequest.zzbPc, i, false);
        zzc.zza(parcel, 13, maskedWalletRequest.zzbPd);
        zzc.zza(parcel, 14, maskedWalletRequest.zzbPe);
        zzc.zzc(parcel, 15, maskedWalletRequest.zzbPf, false);
        zzc.zza(parcel, 16, maskedWalletRequest.zzbPg, i, false);
        zzc.zza(parcel, 17, maskedWalletRequest.zzbPh, false);
        zzc.zza(parcel, 18, maskedWalletRequest.zzUe, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjD(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznU(i);
    }

    public MaskedWalletRequest zzjD(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Cart cart = null;
        boolean z4 = false;
        boolean z5 = false;
        CountrySpecification[] countrySpecificationArr = null;
        boolean z6 = true;
        boolean z7 = true;
        ArrayList arrayList = null;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        ArrayList arrayList2 = null;
        String str5 = null;
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
                    z = zzb.zzc(parcel, zzaT);
                    break;
                case 4:
                    z2 = zzb.zzc(parcel, zzaT);
                    break;
                case 5:
                    z3 = zzb.zzc(parcel, zzaT);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 7:
                    str3 = zzb.zzq(parcel, zzaT);
                    break;
                case 8:
                    str4 = zzb.zzq(parcel, zzaT);
                    break;
                case 9:
                    cart = (Cart) zzb.zza(parcel, zzaT, Cart.CREATOR);
                    break;
                case 10:
                    z4 = zzb.zzc(parcel, zzaT);
                    break;
                case 11:
                    z5 = zzb.zzc(parcel, zzaT);
                    break;
                case 12:
                    countrySpecificationArr = (CountrySpecification[]) zzb.zzb(parcel, zzaT, CountrySpecification.CREATOR);
                    break;
                case 13:
                    z6 = zzb.zzc(parcel, zzaT);
                    break;
                case 14:
                    z7 = zzb.zzc(parcel, zzaT);
                    break;
                case 15:
                    arrayList = zzb.zzc(parcel, zzaT, CountrySpecification.CREATOR);
                    break;
                case 16:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) zzb.zza(parcel, zzaT, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 17:
                    arrayList2 = zzb.zzD(parcel, zzaT);
                    break;
                case 18:
                    str5 = zzb.zzq(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new MaskedWalletRequest(i, str, z, z2, z3, str2, str3, str4, cart, z4, z5, countrySpecificationArr, z6, z7, arrayList, paymentMethodTokenizationParameters, arrayList2, str5);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public MaskedWalletRequest[] zznU(int i) {
        return new MaskedWalletRequest[i];
    }
}
