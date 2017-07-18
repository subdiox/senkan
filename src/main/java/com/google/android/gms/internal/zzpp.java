package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.io.IOUtils;

@zzmb
public class zzpp {
    private static zzl zzXw;
    private static final Object zzXx = new Object();
    public static final zza<Void> zzXy = new zza<Void>() {
        public /* bridge */ /* synthetic */ Object zzh(InputStream inputStream) {
            return null;
        }

        public /* bridge */ /* synthetic */ Object zziT() {
            return null;
        }
    };

    public interface zza<T> {
        T zzh(InputStream inputStream);

        T zziT();
    }

    private static class zzb<T> extends zzk<InputStream> {
        private final zza<T> zzXC;
        private final com.google.android.gms.internal.zzm.zzb<T> zzaF;

        class AnonymousClass1 implements com.google.android.gms.internal.zzm.zza {
            final /* synthetic */ com.google.android.gms.internal.zzm.zzb zzXD;
            final /* synthetic */ zza zzXE;

            AnonymousClass1(com.google.android.gms.internal.zzm.zzb com_google_android_gms_internal_zzm_zzb, zza com_google_android_gms_internal_zzpp_zza) {
                this.zzXD = com_google_android_gms_internal_zzm_zzb;
                this.zzXE = com_google_android_gms_internal_zzpp_zza;
            }

            public void zze(zzr com_google_android_gms_internal_zzr) {
                this.zzXD.zzb(this.zzXE.zziT());
            }
        }

        public zzb(String str, zza<T> com_google_android_gms_internal_zzpp_zza_T, com.google.android.gms.internal.zzm.zzb<T> com_google_android_gms_internal_zzm_zzb_T) {
            super(0, str, new AnonymousClass1(com_google_android_gms_internal_zzm_zzb_T, com_google_android_gms_internal_zzpp_zza_T));
            this.zzXC = com_google_android_gms_internal_zzpp_zza_T;
            this.zzaF = com_google_android_gms_internal_zzm_zzb_T;
        }

        protected zzm<InputStream> zza(zzi com_google_android_gms_internal_zzi) {
            return zzm.zza(new ByteArrayInputStream(com_google_android_gms_internal_zzi.data), zzx.zzb(com_google_android_gms_internal_zzi));
        }

        protected /* synthetic */ void zza(Object obj) {
            zzj((InputStream) obj);
        }

        protected void zzj(InputStream inputStream) {
            this.zzaF.zzb(this.zzXC.zzh(inputStream));
        }
    }

    private class zzc<T> extends zzqc<T> implements com.google.android.gms.internal.zzm.zzb<T> {
        private zzc(zzpp com_google_android_gms_internal_zzpp) {
        }

        public void zzb(@Nullable T t) {
            super.zzh(t);
        }
    }

    public zzpp(Context context) {
        zzN(context);
    }

    private static zzl zzN(Context context) {
        zzl com_google_android_gms_internal_zzl;
        synchronized (zzXx) {
            if (zzXw == null) {
                zzXw = zzac.zza(context.getApplicationContext());
            }
            com_google_android_gms_internal_zzl = zzXw;
        }
        return com_google_android_gms_internal_zzl;
    }

    public zzqf<String> zza(int i, final String str, @Nullable Map<String, String> map, @Nullable byte[] bArr) {
        final Object com_google_android_gms_internal_zzpp_zzc = new zzc();
        final byte[] bArr2 = bArr;
        final Map<String, String> map2 = map;
        zzXw.zze(new zzab(this, i, str, com_google_android_gms_internal_zzpp_zzc, new com.google.android.gms.internal.zzm.zza(this) {
            public void zze(zzr com_google_android_gms_internal_zzr) {
                String str = str;
                String valueOf = String.valueOf(com_google_android_gms_internal_zzr.toString());
                zzpe.zzbe(new StringBuilder((String.valueOf(str).length() + 21) + String.valueOf(valueOf).length()).append("Failed to load URL: ").append(str).append(IOUtils.LINE_SEPARATOR_UNIX).append(valueOf).toString());
                com_google_android_gms_internal_zzpp_zzc.zzb(null);
            }
        }) {
            public Map<String, String> getHeaders() throws zza {
                return map2 == null ? super.getHeaders() : map2;
            }

            public byte[] zzm() throws zza {
                return bArr2 == null ? super.zzm() : bArr2;
            }
        });
        return com_google_android_gms_internal_zzpp_zzc;
    }

    public <T> zzqf<T> zza(String str, zza<T> com_google_android_gms_internal_zzpp_zza_T) {
        Object com_google_android_gms_internal_zzpp_zzc = new zzc();
        zzXw.zze(new zzb(str, com_google_android_gms_internal_zzpp_zza_T, com_google_android_gms_internal_zzpp_zzc));
        return com_google_android_gms_internal_zzpp_zzc;
    }

    public zzqf<String> zzc(String str, Map<String, String> map) {
        return zza(0, str, map, null);
    }
}
