package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzp implements Creator<NotifyTransactionStatusRequest> {
    static void zza(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, notifyTransactionStatusRequest.mVersionCode);
        zzc.zza(parcel, 2, notifyTransactionStatusRequest.zzbNX, false);
        zzc.zzc(parcel, 3, notifyTransactionStatusRequest.status);
        zzc.zza(parcel, 4, notifyTransactionStatusRequest.zzbPj, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjE(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznV(i);
    }

    public NotifyTransactionStatusRequest zzjE(Parcel parcel) {
        String str = null;
        int i = 0;
        int zzaU = zzb.zzaU(parcel);
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 4:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new NotifyTransactionStatusRequest(i2, str2, i, str);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public NotifyTransactionStatusRequest[] zznV(int i) {
        return new NotifyTransactionStatusRequest[i];
    }
}
