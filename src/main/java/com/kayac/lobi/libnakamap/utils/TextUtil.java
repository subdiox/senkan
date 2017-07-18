package com.kayac.lobi.libnakamap.utils;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.ClipboardManager;
import android.widget.Toast;
import com.kayac.lobi.sdk.R;

public class TextUtil {
    @TargetApi(11)
    public static void copyText(Context context, String text) {
        if (VERSION.SDK_INT < 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setText(text);
        } else {
            ((android.content.ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(IntentUtils.SCHEME_LOBI, text));
        }
        Toast.makeText(context, R.string.lobi_copied_to, 0).show();
    }
}
