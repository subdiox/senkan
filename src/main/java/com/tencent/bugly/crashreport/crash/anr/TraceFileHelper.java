package com.tencent.bugly.crashreport.crash.anr;

import com.tencent.bugly.proguard.w;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;

/* compiled from: BUGLY */
public class TraceFileHelper {

    /* compiled from: BUGLY */
    public interface b {
        boolean a(long j);

        boolean a(long j, long j2, String str);

        boolean a(String str, int i, String str2, String str3);
    }

    /* compiled from: BUGLY */
    public static class a {
        public long a;
        public String b;
        public long c;
        public Map<String, String[]> d;
    }

    public static a readTargetDumpInfo(String str, String str2, final boolean z) {
        if (str == null || str2 == null) {
            return null;
        }
        final a aVar = new a();
        readTraceFile(str2, new b() {
            public final boolean a(String str, int i, String str2, String str3) {
                w.c("new thread %s", str);
                if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
                    if (aVar.d == null) {
                        aVar.d = new HashMap();
                    }
                    aVar.d.put(str, new String[]{str2, str3, i});
                }
                return true;
            }

            public final boolean a(long j, long j2, String str) {
                w.c("new process %s", str);
                if (!str.equals(str)) {
                    return true;
                }
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                if (z) {
                    return true;
                }
                return false;
            }

            public final boolean a(long j) {
                w.c("process end %d", Long.valueOf(j));
                if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
                    return true;
                }
                return false;
            }
        });
        if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
            return null;
        }
        return aVar;
    }

    public static a readFirstDumpInfo(String str, final boolean z) {
        if (str == null) {
            w.e("path:%s", str);
            return null;
        }
        final a aVar = new a();
        readTraceFile(str, new b() {
            public final boolean a(String str, int i, String str2, String str3) {
                w.c("new thread %s", str);
                if (aVar.d == null) {
                    aVar.d = new HashMap();
                }
                aVar.d.put(str, new String[]{str2, str3, i});
                return true;
            }

            public final boolean a(long j, long j2, String str) {
                w.c("new process %s", str);
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                if (z) {
                    return true;
                }
                return false;
            }

            public final boolean a(long j) {
                w.c("process end %d", Long.valueOf(j));
                return false;
            }
        });
        if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
            return aVar;
        }
        w.e("first dump error %s", aVar.a + " " + aVar.c + " " + aVar.b);
        return null;
    }

    public static void readTraceFile(String str, b bVar) {
        Throwable e;
        if (str != null && bVar != null) {
            File file = new File(str);
            if (file.exists()) {
                file.lastModified();
                file.length();
                BufferedReader bufferedReader = null;
                BufferedReader bufferedReader2;
                try {
                    bufferedReader2 = new BufferedReader(new FileReader(file));
                    try {
                        Pattern compile = Pattern.compile("-{5}\\spid\\s\\d+\\sat\\s\\d+-\\d+-\\d+\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}");
                        Pattern compile2 = Pattern.compile("-{5}\\send\\s\\d+\\s-{5}");
                        Pattern compile3 = Pattern.compile("Cmd\\sline:\\s(\\S+)");
                        Pattern compile4 = Pattern.compile("\".+\"\\s(daemon\\s){0,1}prio=\\d+\\stid=\\d+\\s.*");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                        while (true) {
                            Object[] a = a(bufferedReader2, compile);
                            if (a != null) {
                                String[] split = a[1].toString().split("\\s");
                                long parseLong = Long.parseLong(split[2]);
                                long time = simpleDateFormat.parse(split[4] + " " + split[5]).getTime();
                                a = a(bufferedReader2, compile3);
                                if (a == null) {
                                    try {
                                        bufferedReader2.close();
                                        return;
                                    } catch (Throwable e2) {
                                        if (!w.a(e2)) {
                                            e2.printStackTrace();
                                            return;
                                        }
                                        return;
                                    }
                                }
                                Matcher matcher = compile3.matcher(a[1].toString());
                                matcher.find();
                                matcher.group(1);
                                if (bVar.a(parseLong, time, matcher.group(1))) {
                                    while (true) {
                                        a = a(bufferedReader2, compile4, compile2);
                                        if (a != null) {
                                            if (a[0] != compile4) {
                                                break;
                                            }
                                            CharSequence obj = a[1].toString();
                                            Matcher matcher2 = Pattern.compile("\".+\"").matcher(obj);
                                            matcher2.find();
                                            String group = matcher2.group();
                                            group = group.substring(1, group.length() - 1);
                                            obj.contains("NATIVE");
                                            matcher = Pattern.compile("tid=\\d+").matcher(obj);
                                            matcher.find();
                                            String group2 = matcher.group();
                                            bVar.a(group, Integer.parseInt(group2.substring(group2.indexOf("=") + 1)), a(bufferedReader2), b(bufferedReader2));
                                        } else {
                                            break;
                                        }
                                    }
                                    if (!bVar.a(Long.parseLong(a[1].toString().split("\\s")[2]))) {
                                        try {
                                            bufferedReader2.close();
                                            return;
                                        } catch (Throwable e22) {
                                            if (!w.a(e22)) {
                                                e22.printStackTrace();
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                } else {
                                    try {
                                        bufferedReader2.close();
                                        return;
                                    } catch (Throwable e222) {
                                        if (!w.a(e222)) {
                                            e222.printStackTrace();
                                            return;
                                        }
                                        return;
                                    }
                                }
                            }
                            try {
                                bufferedReader2.close();
                                return;
                            } catch (Throwable e2222) {
                                if (!w.a(e2222)) {
                                    e2222.printStackTrace();
                                    return;
                                }
                                return;
                            }
                        }
                    } catch (Exception e3) {
                        e2222 = e3;
                        bufferedReader = bufferedReader2;
                    } catch (Throwable th) {
                        e2222 = th;
                    }
                } catch (Exception e4) {
                    e2222 = e4;
                    try {
                        if (!w.a(e2222)) {
                            e2222.printStackTrace();
                        }
                        w.d("trace open fail:%s : %s", e2222.getClass().getName(), e2222.getMessage());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable e22222) {
                                if (!w.a(e22222)) {
                                    e22222.printStackTrace();
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        e22222 = th2;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (Throwable e5) {
                                if (!w.a(e5)) {
                                    e5.printStackTrace();
                                }
                            }
                        }
                        throw e22222;
                    }
                } catch (Throwable th3) {
                    e22222 = th3;
                    bufferedReader2 = null;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    throw e22222;
                }
            }
        }
    }

    private static Object[] a(BufferedReader bufferedReader, Pattern... patternArr) throws IOException {
        if (bufferedReader == null || patternArr == null) {
            return null;
        }
        while (true) {
            CharSequence readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            for (Pattern matcher : patternArr) {
                if (matcher.matcher(readLine).matches()) {
                    return new Object[]{patternArr[r1], readLine};
                }
            }
        }
    }

    private static String a(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            stringBuffer.append(readLine + IOUtils.LINE_SEPARATOR_UNIX);
        }
        return stringBuffer.toString();
    }

    private static String b(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null && readLine.trim().length() > 0) {
                stringBuffer.append(readLine + IOUtils.LINE_SEPARATOR_UNIX);
            }
        }
        return stringBuffer.toString();
    }
}
