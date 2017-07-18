package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import java.util.ArrayList;

public class zzc implements Creator<Cart> {
    static void zza(Cart cart, Parcel parcel, int i) {
        int zzaV = com.google.android.gms.common.internal.safeparcel.zzc.zzaV(parcel);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 1, cart.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 2, cart.zzbNQ, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 3, cart.zzbNR, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 4, cart.zzbNS, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjs(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zznJ(i);
    }

    public Cart zzjs(Parcel parcel) {
        String str = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        ArrayList arrayList = new ArrayList();
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
                    arrayList = zzb.zzc(parcel, zzaT, LineItem.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new Cart(i, str2, str, arrayList);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public Cart[] zznJ(int i) {
        return new Cart[i];
    }
}
