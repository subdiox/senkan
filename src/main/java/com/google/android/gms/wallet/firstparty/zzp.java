package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzb.zza;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzp implements Creator<InitializeBuyFlowRequest> {
    static void zza(InitializeBuyFlowRequest initializeBuyFlowRequest, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, initializeBuyFlowRequest.getVersionCode());
        zzc.zza(parcel, 2, initializeBuyFlowRequest.zzbPL, false);
        zzc.zzJ(parcel, zzaV);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzjQ(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzoh(i);
    }

    public InitializeBuyFlowRequest zzjQ(Parcel parcel) {
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        byte[][] bArr = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    bArr = zzb.zzu(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new InitializeBuyFlowRequest(i, bArr);
        }
        throw new zza("Overread allowed size end=" + zzaU, parcel);
    }

    public InitializeBuyFlowRequest[] zzoh(int i) {
        return new InitializeBuyFlowRequest[i];
    }
}
