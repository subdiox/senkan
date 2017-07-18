package com.kayac.lobi.sdk.net;

import org.json.JSONObject;

public interface ApiCallback<T> {
    void onReulst(int i, T t, JSONObject jSONObject);
}
