package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: BUGLY */
public final class x {
    public static boolean a = true;
    private static SimpleDateFormat b;
    private static int c = 5120;
    private static StringBuilder d;
    private static StringBuilder e;
    private static boolean f;
    private static a g;
    private static String h;
    private static String i;
    private static Context j;
    private static String k;
    private static boolean l;
    private static int m;
    private static Object n = new Object();

    /* compiled from: BUGLY */
    public static class a {
        private boolean a;
        private File b;
        private String c;
        private long d;
        private long e = 30720;

        public a(String str) {
            if (str != null && !str.equals("")) {
                this.c = str;
                this.a = a();
            }
        }

        private synchronized boolean a() {
            boolean z = false;
            synchronized (this) {
                try {
                    this.b = new File(this.c);
                    if (!this.b.exists() || this.b.delete()) {
                        if (!this.b.createNewFile()) {
                            this.a = false;
                        }
                        z = true;
                    } else {
                        this.a = false;
                    }
                } catch (Throwable th) {
                    this.a = false;
                }
            }
            return z;
        }

        public final synchronized boolean a(String str) {
            FileOutputStream fileOutputStream;
            Throwable th;
            boolean z = false;
            synchronized (this) {
                if (this.a) {
                    FileOutputStream fileOutputStream2;
                    try {
                        fileOutputStream2 = new FileOutputStream(this.b, true);
                        try {
                            byte[] bytes = str.getBytes("UTF-8");
                            fileOutputStream2.write(bytes);
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                            this.d += (long) bytes.length;
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e) {
                            }
                            z = true;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream2 = null;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        throw th;
                    }
                }
            }
            return z;
        }
    }

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    private static boolean b(String str, String str2, String str3) {
        try {
            com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
            if (!(b == null || b.C == null)) {
                return b.C.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static synchronized void a(Context context) {
        synchronized (x.class) {
            if (!(l || context == null || !a)) {
                try {
                    e = new StringBuilder(0);
                    d = new StringBuilder(0);
                    j = context;
                    com.tencent.bugly.crashreport.common.info.a a = com.tencent.bugly.crashreport.common.info.a.a(context);
                    h = a.d;
                    a.getClass();
                    i = "";
                    k = j.getFilesDir().getPath() + "/buglylog_" + h + "_" + i + ".txt";
                    m = Process.myPid();
                } catch (Throwable th) {
                }
                l = true;
            }
        }
    }

    public static void a(int i) {
        synchronized (n) {
            c = i;
            if (i < 0) {
                c = 0;
            } else if (i > 10240) {
                c = 10240;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            a(str, str2, message + '\n' + y.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (x.class) {
            if (l && a) {
                b(str, str2, str3);
                long myTid = (long) Process.myTid();
                d.setLength(0);
                if (str3.length() > 30720) {
                    str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
                }
                Date date = new Date();
                d.append(b != null ? b.format(date) : date.toString()).append(" ").append(m).append(" ").append(myTid).append(" ").append(str).append(" ").append(str2).append(": ").append(str3).append("\u0001\r\n");
                final String stringBuilder = d.toString();
                synchronized (n) {
                    e.append(stringBuilder);
                    if (e.length() <= c) {
                    } else if (f) {
                    } else {
                        f = true;
                        v.a().a(new Runnable() {
                            public final void run() {
                                synchronized (x.n) {
                                    try {
                                        if (x.g == null) {
                                            x.g = new a(x.k);
                                        } else if (x.g.b == null || x.g.b.length() + ((long) x.e.length()) > x.g.e) {
                                            x.g.a();
                                        }
                                        if (x.g.a) {
                                            x.g.a(x.e.toString());
                                            x.e.setLength(0);
                                        } else {
                                            x.e.setLength(0);
                                            x.e.append(stringBuilder);
                                        }
                                        x.f = false;
                                    } catch (Throwable th) {
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public static byte[] a(boolean z) {
        byte[] bArr = null;
        if (a) {
            synchronized (n) {
                File a;
                if (z) {
                    try {
                        if (g != null && g.a) {
                            a = g.b;
                            if (e.length() == 0 || a != null) {
                                bArr = y.a(a, e.toString());
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
                a = bArr;
                if (e.length() == 0) {
                }
                bArr = y.a(a, e.toString());
            }
        }
        return bArr;
    }
}
