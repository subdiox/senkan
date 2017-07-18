package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.util.zzb;
import java.util.ArrayList;

public final class zzd extends zza {
    public static final Creator<zzd> CREATOR = new zze();
    private final int mVersionCode;
    String zzbQx;
    String zzbQy;
    ArrayList<zzb> zzbQz;

    zzd() {
        this.mVersionCode = 1;
        this.zzbQz = zzb.zzys();
    }

    zzd(int i, String str, String str2, ArrayList<zzb> arrayList) {
        this.mVersionCode = i;
        this.zzbQx = str;
        this.zzbQy = str2;
        this.zzbQz = arrayList;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }
}
