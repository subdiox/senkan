package com.yaya.sdk.utils;

import android.content.Context;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;

public class b {
    public static String a(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (IOException e) {
            Toast.makeText(context, "读取出错", 0).show();
            return null;
        }
    }
}
