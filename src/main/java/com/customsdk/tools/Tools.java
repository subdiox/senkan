package com.customsdk.tools;

import java.util.Locale;

public class Tools {
    public static final String GetLocalLanguages() {
        return Locale.getDefault().getLanguage();
    }

    public static final String GetLocalCountry() {
        return Locale.getDefault().getCountry();
    }
}
