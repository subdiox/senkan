package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzs implements Creator<PaymentMethodTokenizationParameters> {
    static void zza(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, paymentMethodTokenizationParameters.getVersionCode());
        zzc.zzc(parcel, 2, paymentMethodTokenizationParameters.zzbPm);
        zzc.zza(parcel, 3, paymentMethodTokenizationParameters.zzbPn, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjH(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznY(i);
    }

    public PaymentMethodTokenizationParameters zzjH(Parcel parcel) {
        int i = 0;
        int zzaU = zzb.zzaU(parcel);
        Bundle bundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 3:
                    bundle = zzb.zzs(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new PaymentMethodTokenizationParameters(i2, i, bundle);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public PaymentMethodTokenizationParameters[] zznY(int i) {
        return new PaymentMethodTokenizationParameters[i];
    }
}
