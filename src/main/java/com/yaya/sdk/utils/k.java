package com.yaya.sdk.utils;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class k {
    private static final String a = "/uuinfo";
    private static final String b = "phone_uuid.tmp";

    public static String a(Context context) {
        String str = b(context) + a;
        String a = a(str, b);
        if (a != null && a.length() != 0) {
            return a;
        }
        a = UUID.randomUUID().toString().replaceAll("-", "").trim();
        a(str, a, b);
        return a;
    }

    public static String a() {
        return UUID.randomUUID().toString().replaceAll("-", "").trim();
    }

    public static String b(Context context) {
        String str = "";
        if (Environment.getExternalStorageState().equals("mounted")) {
            return new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath().toString())).append(File.separator).append("yaya").toString();
        }
        return new StringBuilder(String.valueOf(context.getFilesDir().getAbsolutePath())).append(File.separator).append("yaya").toString();
    }

    private static void a(String str, String str2, String str3) {
        BufferedWriter bufferedWriter;
        Throwable th;
        BufferedWriter bufferedWriter2 = null;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(new StringBuilder(String.valueOf(str)).append(File.separator).append(str3).toString()));
            try {
                bufferedWriter.write(str2);
                bufferedWriter.flush();
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                    }
                }
            } catch (IOException e2) {
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            if (bufferedWriter2 != null) {
                bufferedWriter2.close();
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bufferedWriter = null;
            th = th4;
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            throw th;
        }
    }

    private static String a(String str, String str2) {
        BufferedReader bufferedReader;
        Throwable th;
        String str3 = "";
        BufferedReader bufferedReader2 = null;
        try {
            File file = new File(new StringBuilder(String.valueOf(str)).append(File.separator).append(str2).toString());
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                try {
                    str3 = bufferedReader.readLine();
                } catch (IOException e) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                        }
                    }
                    return str3;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException e3) {
                        }
                    }
                    throw th;
                }
            }
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                }
            }
        } catch (IOException e5) {
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            return str3;
        } catch (Throwable th3) {
            th = th3;
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            throw th;
        }
        return str3;
    }
}
