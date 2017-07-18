package com.kayac.lobi.libnakamap.rec.d;

import android.os.Build;
import com.kayac.lobi.libnakamap.rec.a.b;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class a extends Build {
    private static Boolean a = null;

    public static synchronized boolean a() {
        boolean booleanValue;
        synchronized (a.class) {
            if (a != null) {
                booleanValue = a.booleanValue();
            } else {
                a = Boolean.valueOf(b());
                booleanValue = a.booleanValue();
            }
        }
        return booleanValue;
    }

    private static boolean b() {
        BufferedReader bufferedReader;
        Throwable e;
        File file = new File("/proc/cpuinfo");
        if (file.exists() && file.isFile()) {
            BufferedReader bufferedReader2 = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String readLine;
                do {
                    try {
                        readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                    e = e2;
                                    b.a(e);
                                    return false;
                                }
                            }
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                    } catch (IOException e4) {
                        e = e4;
                        bufferedReader2 = bufferedReader;
                    }
                } while (!readLine.contains("neon"));
                if (bufferedReader == null) {
                    return true;
                }
                try {
                    bufferedReader.close();
                    return true;
                } catch (Throwable e5) {
                    b.a(e5);
                    return true;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                bufferedReader = null;
                try {
                    b.a(e);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e7) {
                            e = e7;
                            b.a(e);
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    e = th;
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (Throwable e52) {
                            b.a(e52);
                        }
                    }
                    throw e;
                }
            } catch (IOException e8) {
                e = e8;
                try {
                    b.a(e);
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e9) {
                            e = e9;
                            b.a(e);
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    e = th2;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    throw e;
                }
            }
        }
        return false;
    }
}
