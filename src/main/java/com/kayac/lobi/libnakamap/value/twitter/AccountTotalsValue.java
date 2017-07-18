package com.kayac.lobi.libnakamap.value.twitter;

import org.json.JSONObject;

public class AccountTotalsValue {
    public final int favorites;
    public final int followers;
    public final int friends;
    public final int updates;

    public AccountTotalsValue(JSONObject jsonObject) {
        this.friends = readInt(jsonObject, "friends");
        this.updates = readInt(jsonObject, "updates");
        this.followers = readInt(jsonObject, "followers");
        this.favorites = readInt(jsonObject, "favorites");
    }

    private int readInt(JSONObject jsonObject, String key) {
        int value = 0;
        try {
            value = Integer.parseInt(jsonObject.optString(key, "0"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }
}
