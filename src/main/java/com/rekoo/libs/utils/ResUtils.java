package com.rekoo.libs.utils;

import android.content.Context;

public class ResUtils {
    private static final String RESOURCE_COLOR = "color";
    private static final String RESOURCE_DRAWABLE = "drawable";
    private static final String RESOURCE_ID = "id";
    private static final String RESOURCE_LAYOUT = "layout";
    private static final String RESOURCE_MENU = "menu";
    private static final String RESOURCE_STRING = "string";
    private static final String RESOURCE_STYLE = "style";

    public static int getId(String name, Context activity) {
        return activity.getResources().getIdentifier(name, "id", activity.getPackageName());
    }

    public static int getLayout(String name, Context activity) {
        return activity.getResources().getIdentifier(name, RESOURCE_LAYOUT, activity.getPackageName());
    }

    public static int getStyle(String name, Context activity) {
        return activity.getResources().getIdentifier(name, RESOURCE_STYLE, activity.getPackageName());
    }

    public static int getDrawable(String name, Context activity) {
        return activity.getResources().getIdentifier(name, RESOURCE_DRAWABLE, activity.getPackageName());
    }

    public static int getStringId(String name, Context activity) {
        return activity.getResources().getIdentifier(name, RESOURCE_STRING, activity.getPackageName());
    }

    public static String getString(String name, Context activity) {
        return activity.getResources().getString(getStringId(name, activity));
    }

    public static int getColor(String name, Context activity) {
        return activity.getResources().getIdentifier(name, RESOURCE_COLOR, activity.getPackageName());
    }

    public static int getMenu(String name, Context activity) {
        return activity.getResources().getIdentifier(name, RESOURCE_MENU, activity.getPackageName());
    }
}
