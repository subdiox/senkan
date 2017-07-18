package com.yaya.sdk.utils;

import android.content.Context;
import android.widget.Toast;
import com.adjust.sdk.Constants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.security.MessageDigest;
import org.apache.commons.io.IOUtils;

public class e {
    private static MessageDigest a;

    public static String a(String str, Context context) {
        try {
            InputStream open = context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (Exception e) {
            Toast.makeText(context, "读取出错", 0).show();
            return "FAIL";
        }
    }

    static {
        a = null;
        try {
            a = MessageDigest.getInstance(Constants.MD5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String a(String str) {
        byte[] digest = a.digest(str.getBytes());
        StringBuilder stringBuilder = new StringBuilder(40);
        for (byte b : digest) {
            if (((b & 255) >> 4) == 0) {
                stringBuilder.append("0").append(Integer.toHexString(b & 255));
            } else {
                stringBuilder.append(Integer.toHexString(b & 255));
            }
        }
        return stringBuilder.toString();
    }

    public static boolean b(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        }
        try {
            File file = new File(str);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void c(String str) {
        BufferedWriter bufferedWriter;
        Throwable th;
        BufferedWriter bufferedWriter2 = null;
        try {
            File file = new File(com.yaya.sdk.constants.Constants.ROOT_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            Writer fileWriter = new FileWriter("yaya_jb/ddd.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            try {
                bufferedWriter.write(new StringBuilder(String.valueOf(str)).append(IOUtils.LINE_SEPARATOR_UNIX).toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.close();
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                }
            } catch (Exception e2) {
                bufferedWriter2 = bufferedWriter;
                try {
                    bufferedWriter2.close();
                } catch (IOException e3) {
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    bufferedWriter.close();
                } catch (IOException e4) {
                }
                throw th;
            }
        } catch (Exception e5) {
            bufferedWriter2.close();
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bufferedWriter = null;
            th = th4;
            bufferedWriter.close();
            throw th;
        }
    }
}
