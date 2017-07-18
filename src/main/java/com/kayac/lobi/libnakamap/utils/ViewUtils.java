package com.kayac.lobi.libnakamap.utils;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class ViewUtils {
    public static <T> T findViewById(View view, int id) {
        return view.findViewById(id);
    }

    public static void hideOverscrollEdge(View view) {
        if (VERSION.SDK_INT >= 9) {
            hideOverscrollEdgeImpl(view);
        }
    }

    @TargetApi(9)
    private static void hideOverscrollEdgeImpl(View view) {
        view.setOverScrollMode(2);
    }

    public static void cleanAllDescendantViews(ViewGroup group) {
        if (group != null) {
            int len = group.getChildCount();
            for (int i = 0; i < len; i++) {
                View child = group.getChildAt(i);
                if (child instanceof AdapterView) {
                    ((AdapterView) child).setOnItemClickListener(null);
                } else {
                    child.setOnClickListener(null);
                }
                child.setOnTouchListener(null);
                child.setTag(null);
                if (child instanceof ViewGroup) {
                    cleanAllDescendantViews((ViewGroup) child);
                }
            }
            if (!(group instanceof AdapterView)) {
                group.removeAllViews();
            }
        }
    }
}
