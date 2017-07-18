package com.kayac.lobi.libnakamap.rec.recorder.a;

import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public final class e {
    private static final String a = e.class.getSimpleName();
    private static final b b = new b(a);
    private final String c;
    private int d = 0;
    private MediaCodecInfo e;
    private int f = 0;
    private int g = 0;
    private boolean h = false;
    private boolean i = false;

    public e(String str) {
        this.c = str;
        a(3);
        int i = 21;
        if (b().indexOf("Tegra") != -1) {
            i = 19;
        }
        this.d = i;
        this.e = b("video/avc");
        if (this.e == null) {
            b.c("no encoder found for video/avc");
            LobiRec.setLastErrorCode(LobiRec.ERROR_BAD_ENCODER_CONNECTION);
            return;
        }
        Executors.newSingleThreadExecutor().execute(new f(this));
    }

    public static int a(String str) {
        return (VERSION.SDK_INT > 17 || str.indexOf("Adreno") == -1 || (str.indexOf("220") == -1 && str.indexOf("225") == -1 && str.indexOf("230") == -1 && str.indexOf("302") == -1 && str.indexOf("305") == -1 && str.indexOf("320") == -1)) ? 0 : 2048;
    }

    private static int b(MediaCodecInfo mediaCodecInfo, String str) {
        int i = 0;
        if (mediaCodecInfo == null) {
            throw new IllegalArgumentException("codecInfo is null");
        }
        try {
            int i2;
            CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
            for (int i3 : capabilitiesForType.colorFormats) {
                b.b("Color format: " + i3);
            }
            for (int i32 : capabilitiesForType.colorFormats) {
                if (i32 == 21) {
                    return i32;
                }
            }
            while (i < capabilitiesForType.colorFormats.length) {
                i2 = capabilitiesForType.colorFormats[i];
                switch (i2) {
                    case 19:
                    case 20:
                    case 21:
                    case 39:
                    case 2130706688:
                        return i2;
                    default:
                        i++;
                }
            }
            return 21;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    private static MediaCodecInfo b(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            b.b("MediaCodecInfo " + i + ": " + MediaCodecList.getCodecInfoAt(i).getName());
        }
        List<MediaCodecInfo> arrayList = new ArrayList();
        for (int i2 = 0; i2 < codecCount; i2++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (String equals : supportedTypes) {
                    if (equals.equals(str)) {
                        arrayList.add(codecInfoAt);
                        break;
                    }
                }
            }
        }
        if (arrayList.size() > 1) {
            List arrayList2 = new ArrayList();
            for (MediaCodecInfo name : arrayList) {
                arrayList2.add(name.getName());
            }
            String str2 = "OMX.SEC.avc.enc";
            str2 = "OMX.SEC.AVC.Encoder";
            if (arrayList2.contains("OMX.SEC.AVC.Encoder") && arrayList2.contains("OMX.SEC.avc.enc")) {
                for (MediaCodecInfo name2 : arrayList) {
                    if ("OMX.SEC.AVC.Encoder".equals(name2.getName())) {
                        return name2;
                    }
                }
            }
        }
        return arrayList.size() > 0 ? (MediaCodecInfo) arrayList.get(0) : null;
    }

    public int a() {
        return this.f;
    }

    public void a(int i) {
        if (i < 1) {
            b.c("not supported CaptureFrames value: " + i + " <" + 1);
            i = 1;
        }
        this.f = i;
        this.g = 60 / i;
    }

    public String b() {
        return this.c;
    }

    public MediaCodecInfo c() {
        return this.e;
    }

    public int d() {
        return this.d;
    }

    public int e() {
        return this.f;
    }

    public int f() {
        return this.g;
    }
}
