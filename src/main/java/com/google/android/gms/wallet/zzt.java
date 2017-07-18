package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzt implements Creator<ProxyCard> {
    static void zza(ProxyCard proxyCard, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, proxyCard.getVersionCode());
        zzc.zza(parcel, 2, proxyCard.zzbPp, false);
        zzc.zza(parcel, 3, proxyCard.zzbPq, false);
        zzc.zzc(parcel, 4, proxyCard.zzbPr);
        zzc.zzc(parcel, 5, proxyCard.zzbPs);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjI(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznZ(i);
    }

    public ProxyCard zzjI(Parcel parcel) {
        String str = null;
        int i = 0;
        int zzaU = zzb.zzaU(parcel);
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i3 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 5:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new ProxyCard(i3, str2, str, i2, i);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public ProxyCard[] zznZ(int i) {
        return new ProxyCard[i];
    }
}
