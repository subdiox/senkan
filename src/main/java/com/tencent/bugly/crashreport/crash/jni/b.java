package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import android.support.v4.os.EnvironmentCompat;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.j;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.IOUtils;

/* compiled from: BUGLY */
public class b {
    private StringBuilder a;
    private int b = 0;

    private void d(String str) {
        for (int i = 0; i < this.b; i++) {
            this.a.append('\t');
        }
        if (str != null) {
            this.a.append(str).append(": ");
        }
    }

    public b(StringBuilder stringBuilder, int i) {
        this.a = stringBuilder;
        this.b = i;
    }

    public b a(boolean z, String str) {
        d(str);
        this.a.append(z ? 'T' : 'F').append('\n');
        return this;
    }

    private static Map<String, Integer> c(String str) {
        if (str == null) {
            return null;
        }
        try {
            Map<String, Integer> hashMap = new HashMap();
            for (String split : str.split(",")) {
                String[] split2 = split.split(":");
                if (split2.length != 2) {
                    w.e("error format at %s", split);
                    return null;
                }
                hashMap.put(split2[0], Integer.valueOf(Integer.parseInt(split2[1])));
            }
            return hashMap;
        } catch (Exception e) {
            w.e("error format intStateStr %s", str);
            e.printStackTrace();
            return null;
        }
    }

    public b a(byte b, String str) {
        d(str);
        this.a.append(b).append('\n');
        return this;
    }

    public b a(char c, String str) {
        d(str);
        this.a.append(c).append('\n');
        return this;
    }

    public b a(short s, String str) {
        d(str);
        this.a.append(s).append('\n');
        return this;
    }

    public b a(int i, String str) {
        d(str);
        this.a.append(i).append('\n');
        return this;
    }

    protected static String a(String str) {
        if (str == null) {
            return "";
        }
        String[] split = str.split(IOUtils.LINE_SEPARATOR_UNIX);
        if (split == null || split.length == 0) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : split) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                stringBuilder.append(str2).append(IOUtils.LINE_SEPARATOR_UNIX);
            }
        }
        return stringBuilder.toString();
    }

    public b a(long j, String str) {
        d(str);
        this.a.append(j).append('\n');
        return this;
    }

    public b a(float f, String str) {
        d(str);
        this.a.append(f).append('\n');
        return this;
    }

    public b a(double d, String str) {
        d(str);
        this.a.append(d).append('\n');
        return this;
    }

    private static CrashDetailBean a(Context context, Map<String, String> map, NativeExceptionHandler nativeExceptionHandler) {
        if (map == null) {
            return null;
        }
        if (a.a(context) == null) {
            w.e("abnormal com info not created", new Object[0]);
            return null;
        }
        String str = (String) map.get("intStateStr");
        if (str == null || str.trim().length() <= 0) {
            w.e("no intStateStr", new Object[0]);
            return null;
        }
        Map c = c(str);
        if (c == null) {
            w.e("parse intSateMap fail", Integer.valueOf(map.size()));
            return null;
        }
        try {
            ((Integer) c.get("sino")).intValue();
            ((Integer) c.get("sud")).intValue();
            String str2 = (String) map.get("soVersion");
            if (str2 == null) {
                w.e("error format at version", new Object[0]);
                return null;
            }
            String str3;
            String str4;
            String str5;
            String str6;
            String str7;
            String str8;
            String str9;
            str = (String) map.get("errorAddr");
            String str10 = str == null ? EnvironmentCompat.MEDIA_UNKNOWN : str;
            str = (String) map.get("codeMsg");
            if (str == null) {
                str3 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str3 = str;
            }
            str = (String) map.get("tombPath");
            if (str == null) {
                str4 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str4 = str;
            }
            str = (String) map.get("signalName");
            if (str == null) {
                str5 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str5 = str;
            }
            map.get("errnoMsg");
            str = (String) map.get("stack");
            if (str == null) {
                str6 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str6 = str;
            }
            str = (String) map.get("jstack");
            if (str != null) {
                str6 = str6 + "java:\n" + str;
            }
            Integer num = (Integer) c.get("sico");
            if (num != null && num.intValue() > 0) {
                str5 = str5 + "(" + str3 + ")";
                str3 = "KERNEL";
            }
            str = (String) map.get("nativeLog");
            byte[] bArr = null;
            if (!(str == null || str.isEmpty())) {
                bArr = y.a(null, str);
            }
            str = (String) map.get("sendingProcess");
            if (str == null) {
                str7 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str7 = str;
            }
            num = (Integer) c.get("spd");
            if (num != null) {
                str7 = str7 + "(" + num + ")";
            }
            str = (String) map.get("threadName");
            if (str == null) {
                str8 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str8 = str;
            }
            num = (Integer) c.get("et");
            if (num != null) {
                str8 = str8 + "(" + num + ")";
            }
            str = (String) map.get("processName");
            if (str == null) {
                str9 = EnvironmentCompat.MEDIA_UNKNOWN;
            } else {
                str9 = str;
            }
            num = (Integer) c.get("ep");
            if (num != null) {
                str9 = str9 + "(" + num + ")";
            }
            Map map2 = null;
            str = (String) map.get("key-value");
            if (str != null) {
                map2 = new HashMap();
                for (String split : str.split(IOUtils.LINE_SEPARATOR_UNIX)) {
                    String[] split2 = split.split("=");
                    if (split2.length == 2) {
                        map2.put(split2[0], split2[1]);
                    }
                }
            }
            CrashDetailBean packageCrashDatas = nativeExceptionHandler.packageCrashDatas(str9, str8, (((long) ((Integer) c.get("etms")).intValue()) / 1000) + (((long) ((Integer) c.get("ets")).intValue()) * 1000), str5, str10, a(str6), str3, str7, str4, str2, bArr, map2, false);
            if (packageCrashDatas != null) {
                str = (String) map.get("userId");
                if (str != null) {
                    w.c("[Native record info] userId: %s", str);
                    packageCrashDatas.m = str;
                }
                str = (String) map.get("sysLog");
                if (str != null) {
                    packageCrashDatas.w = str;
                }
                str = (String) map.get("appVersion");
                if (str != null) {
                    w.c("[Native record info] appVersion: %s", str);
                    packageCrashDatas.f = str;
                }
                str = (String) map.get("isAppForeground");
                if (str != null) {
                    w.c("[Native record info] isAppForeground: %s", str);
                    packageCrashDatas.M = str.equalsIgnoreCase("true");
                }
                str = (String) map.get("launchTime");
                if (str != null) {
                    w.c("[Native record info] launchTime: %s", str);
                    packageCrashDatas.L = Long.parseLong(str);
                }
                packageCrashDatas.y = null;
                packageCrashDatas.k = true;
            }
            return packageCrashDatas;
        } catch (Throwable e) {
            if (!w.a(e)) {
                e.printStackTrace();
            }
        } catch (Throwable e2) {
            w.e("error format", new Object[0]);
            e2.printStackTrace();
            return null;
        }
    }

    public b b(String str, String str2) {
        d(str2);
        if (str == null) {
            this.a.append("null\n");
        } else {
            this.a.append(str).append('\n');
        }
        return this;
    }

    public b a(byte[] bArr, String str) {
        d(str);
        if (bArr == null) {
            this.a.append("null\n");
        } else if (bArr.length == 0) {
            this.a.append(bArr.length).append(", []\n");
        } else {
            this.a.append(bArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (byte a : bArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public b a(short[] sArr, String str) {
        d(str);
        if (sArr == null) {
            this.a.append("null\n");
        } else if (sArr.length == 0) {
            this.a.append(sArr.length).append(", []\n");
        } else {
            this.a.append(sArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (short a : sArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public b a(int[] iArr, String str) {
        d(str);
        if (iArr == null) {
            this.a.append("null\n");
        } else if (iArr.length == 0) {
            this.a.append(iArr.length).append(", []\n");
        } else {
            this.a.append(iArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (int a : iArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public b a(long[] jArr, String str) {
        d(str);
        if (jArr == null) {
            this.a.append("null\n");
        } else if (jArr.length == 0) {
            this.a.append(jArr.length).append(", []\n");
        } else {
            this.a.append(jArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (long a : jArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public b a(float[] fArr, String str) {
        d(str);
        if (fArr == null) {
            this.a.append("null\n");
        } else if (fArr.length == 0) {
            this.a.append(fArr.length).append(", []\n");
        } else {
            this.a.append(fArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (float a : fArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public b a(double[] dArr, String str) {
        d(str);
        if (dArr == null) {
            this.a.append("null\n");
        } else if (dArr.length == 0) {
            this.a.append(dArr.length).append(", []\n");
        } else {
            this.a.append(dArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (double a : dArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public <K, V> b a(Map<K, V> map, String str) {
        d(str);
        if (map == null) {
            this.a.append("null\n");
        } else if (map.isEmpty()) {
            this.a.append(map.size()).append(", {}\n");
        } else {
            this.a.append(map.size()).append(", {\n");
            b bVar = new b(this.a, this.b + 1);
            b bVar2 = new b(this.a, this.b + 2);
            for (Entry entry : map.entrySet()) {
                bVar.a('(', null);
                bVar2.a(entry.getKey(), null);
                bVar2.a(entry.getValue(), null);
                bVar.a(')', null);
            }
            a('}', null);
        }
        return this;
    }

    private static String a(BufferedInputStream bufferedInputStream) throws IOException {
        if (bufferedInputStream == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int read = bufferedInputStream.read();
            if (read == -1) {
                return null;
            }
            if (read == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append((char) read);
        }
    }

    public <T> b a(T[] tArr, String str) {
        d(str);
        if (tArr == null) {
            this.a.append("null\n");
        } else if (tArr.length == 0) {
            this.a.append(tArr.length).append(", []\n");
        } else {
            this.a.append(tArr.length).append(", [\n");
            b bVar = new b(this.a, this.b + 1);
            for (Object a : tArr) {
                bVar.a(a, null);
            }
            a(']', null);
        }
        return this;
    }

    public <T> b a(Collection<T> collection, String str) {
        if (collection != null) {
            return a(collection.toArray(), str);
        }
        d(str);
        this.a.append("null\t");
        return this;
    }

    public static CrashDetailBean a(Context context, String str, NativeExceptionHandler nativeExceptionHandler) {
        BufferedInputStream bufferedInputStream;
        IOException e;
        Throwable th;
        CrashDetailBean crashDetailBean = null;
        if (context == null || str == null || nativeExceptionHandler == null) {
            w.e("get eup record file args error", new Object[0]);
        } else {
            File file = new File(str, "rqd_record.eup");
            if (file.exists() && file.canRead()) {
                try {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    try {
                        String a = a(bufferedInputStream);
                        if (a == null || !a.equals("NATIVE_RQD_REPORT")) {
                            w.e("record read fail! %s", a);
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        } else {
                            Map hashMap = new HashMap();
                            Object obj = crashDetailBean;
                            while (true) {
                                String a2 = a(bufferedInputStream);
                                if (a2 == null) {
                                    break;
                                } else if (obj == null) {
                                    obj = a2;
                                } else {
                                    hashMap.put(obj, a2);
                                    obj = crashDetailBean;
                                }
                            }
                            if (obj != null) {
                                w.e("record not pair! drop! %s", obj);
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e22) {
                                    e22.printStackTrace();
                                }
                            } else {
                                crashDetailBean = a(context, hashMap, nativeExceptionHandler);
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e222) {
                                    e222.printStackTrace();
                                }
                            }
                        }
                    } catch (IOException e3) {
                        e222 = e3;
                        try {
                            e222.printStackTrace();
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e2222) {
                                    e2222.printStackTrace();
                                }
                            }
                            return crashDetailBean;
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e22222) {
                                    e22222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                } catch (IOException e4) {
                    e22222 = e4;
                    bufferedInputStream = crashDetailBean;
                    e22222.printStackTrace();
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    return crashDetailBean;
                } catch (Throwable th3) {
                    bufferedInputStream = crashDetailBean;
                    th = th3;
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    throw th;
                }
            }
        }
        return crashDetailBean;
    }

    public <T> b a(T t, String str) {
        if (t == null) {
            this.a.append("null\n");
        } else if (t instanceof Byte) {
            a(((Byte) t).byteValue(), str);
        } else if (t instanceof Boolean) {
            a(((Boolean) t).booleanValue(), str);
        } else if (t instanceof Short) {
            a(((Short) t).shortValue(), str);
        } else if (t instanceof Integer) {
            a(((Integer) t).intValue(), str);
        } else if (t instanceof Long) {
            a(((Long) t).longValue(), str);
        } else if (t instanceof Float) {
            a(((Float) t).floatValue(), str);
        } else if (t instanceof Double) {
            a(((Double) t).doubleValue(), str);
        } else if (t instanceof String) {
            b((String) t, str);
        } else if (t instanceof Map) {
            a((Map) t, str);
        } else if (t instanceof List) {
            a((List) t, str);
        } else if (t instanceof j) {
            a((j) t, str);
        } else if (t instanceof byte[]) {
            a((byte[]) t, str);
        } else if (t instanceof boolean[]) {
            a((boolean[]) t, str);
        } else if (t instanceof short[]) {
            a((short[]) t, str);
        } else if (t instanceof int[]) {
            a((int[]) t, str);
        } else if (t instanceof long[]) {
            a((long[]) t, str);
        } else if (t instanceof float[]) {
            a((float[]) t, str);
        } else if (t instanceof double[]) {
            a((double[]) t, str);
        } else if (t.getClass().isArray()) {
            a((Object[]) t, str);
        } else {
            throw new com.tencent.bugly.proguard.b("write object error: unsupport type.");
        }
        return this;
    }

    private static String c(String str, String str2) {
        String str3 = null;
        BufferedReader a = y.a(str, "reg_record.txt");
        if (a != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String readLine = a.readLine();
                if (readLine != null && readLine.startsWith(str2)) {
                    String str4 = "                ";
                    int i = 0;
                    int i2 = 18;
                    int i3 = 0;
                    while (true) {
                        String readLine2 = a.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        if (i3 % 4 == 0) {
                            if (i3 > 0) {
                                stringBuilder.append(IOUtils.LINE_SEPARATOR_UNIX);
                            }
                            stringBuilder.append("  ");
                        } else {
                            if (readLine2.length() > 16) {
                                i2 = 28;
                            }
                            stringBuilder.append(str4.substring(0, i2 - i));
                        }
                        i = readLine2.length();
                        stringBuilder.append(readLine2);
                        i3++;
                    }
                    stringBuilder.append(IOUtils.LINE_SEPARATOR_UNIX);
                    str3 = stringBuilder.toString();
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable e) {
                            w.a(e);
                        }
                    }
                } else if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e2) {
                        w.a(e2);
                    }
                }
            } catch (Throwable th) {
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e22) {
                        w.a(e22);
                    }
                }
            }
        }
        return str3;
    }

    public b a(j jVar, String str) {
        a('{', str);
        if (jVar == null) {
            this.a.append('\t').append("null");
        } else {
            jVar.a(this.a, this.b + 1);
        }
        a('}', null);
        return this;
    }

    private static String d(String str, String str2) {
        String str3 = null;
        BufferedReader a = y.a(str, "map_record.txt");
        if (a != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String readLine = a.readLine();
                if (readLine != null && readLine.startsWith(str2)) {
                    while (true) {
                        readLine = a.readLine();
                        if (readLine == null) {
                            break;
                        }
                        stringBuilder.append("  ");
                        stringBuilder.append(readLine);
                        stringBuilder.append(IOUtils.LINE_SEPARATOR_UNIX);
                    }
                    str3 = stringBuilder.toString();
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable e) {
                            w.a(e);
                        }
                    }
                } else if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e2) {
                        w.a(e2);
                    }
                }
            } catch (Throwable th) {
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable e22) {
                        w.a(e22);
                    }
                }
            }
        }
        return str3;
    }

    public static String a(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String c = c(str, str2);
        if (!(c == null || c.isEmpty())) {
            stringBuilder.append("Register infos:\n");
            stringBuilder.append(c);
        }
        c = d(str, str2);
        if (!(c == null || c.isEmpty())) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(IOUtils.LINE_SEPARATOR_UNIX);
            }
            stringBuilder.append("System SO infos:\n");
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static void b(String str) {
        File file = new File(str, "rqd_record.eup");
        if (file.exists() && file.canWrite()) {
            file.delete();
            w.c("delete record file %s", file.getAbsoluteFile());
        }
        file = new File(str, "reg_record.txt");
        if (file.exists() && file.canWrite()) {
            file.delete();
            w.c("delete record file %s", file.getAbsoluteFile());
        }
        file = new File(str, "map_record.txt");
        if (file.exists() && file.canWrite()) {
            file.delete();
            w.c("delete record file %s", file.getAbsoluteFile());
        }
        file = new File(str, "backup_record.txt");
        if (file.exists() && file.canWrite()) {
            file.delete();
            w.c("delete record file %s", file.getAbsoluteFile());
        }
    }
}
