package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@zzmb
public class zzge {
    private final Map<String, zzgd> zzFq = new HashMap();
    @Nullable
    private final zzgf zzsr;

    public zzge(@Nullable zzgf com_google_android_gms_internal_zzgf) {
        this.zzsr = com_google_android_gms_internal_zzgf;
    }

    public void zza(String str, zzgd com_google_android_gms_internal_zzgd) {
        this.zzFq.put(str, com_google_android_gms_internal_zzgd);
    }

    public void zza(String str, String str2, long j) {
        zzgb.zza(this.zzsr, (zzgd) this.zzFq.get(str2), j, str);
        this.zzFq.put(str, zzgb.zza(this.zzsr, j));
    }

    @Nullable
    public zzgf zzfv() {
        return this.zzsr;
    }
}
