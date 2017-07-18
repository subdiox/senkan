package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzl implements Creator<LineItem> {
    static void zza(LineItem lineItem, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, lineItem.getVersionCode());
        zzc.zza(parcel, 2, lineItem.description, false);
        zzc.zza(parcel, 3, lineItem.zzbOv, false);
        zzc.zza(parcel, 4, lineItem.zzbOw, false);
        zzc.zza(parcel, 5, lineItem.zzbNQ, false);
        zzc.zzc(parcel, 6, lineItem.zzbOx);
        zzc.zza(parcel, 7, lineItem.zzbNR, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjA(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznR(i);
    }

    public LineItem zzjA(Parcel parcel) {
        int i = 0;
        String str = null;
        int zzaU = zzb.zzaU(parcel);
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str5 = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str4 = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    str3 = zzb.zzq(parcel, zzaT);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 6:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 7:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new LineItem(i2, str5, str4, str3, str2, i, str);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public LineItem[] zznR(int i) {
        return new LineItem[i];
    }
}
