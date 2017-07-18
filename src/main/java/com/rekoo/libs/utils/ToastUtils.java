package com.rekoo.libs.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void showToast(String text, Context context, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void showToastCenter(Activity act, String text) {
        Toast toast = Toast.makeText(act, text, 1);
        toast.setGravity(17, 0, 2);
        toast.show();
    }
}
