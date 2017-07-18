package com.kayac.lobi.libnakamap.utils;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
    private JSONUtil() {
    }

    public static final String getString(JSONObject obj, String key, String fallback) {
        return (obj == null || obj.isNull(key)) ? fallback : obj.optString(key, fallback);
    }

    public static final String getJSONStringWithXPath(JSONObject jsonObject, String xpath, String fallback) {
        List<String> paths = new ArrayList(Arrays.asList(TextUtils.split(xpath, "\\.")));
        String lastPath = (String) paths.remove(paths.size() - 1);
        for (String path : paths) {
            if (jsonObject == null) {
                return fallback;
            }
            jsonObject = jsonObject.optJSONObject(path);
        }
        return jsonObject != null ? getString(jsonObject, lastPath, fallback) : fallback;
    }

    public static final void replaceValue(JSONObject obj, String key, String newValue) {
        obj.remove(key);
        try {
            obj.put(key, newValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final List<String> toStringList(JSONArray arr) {
        List<String> list = new ArrayList();
        for (int i = 0; i < arr.length(); i++) {
            String value = arr.optString(i);
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }
}
