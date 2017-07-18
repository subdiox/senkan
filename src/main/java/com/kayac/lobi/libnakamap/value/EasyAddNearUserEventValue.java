package com.kayac.lobi.libnakamap.value;

import org.json.JSONObject;

public class EasyAddNearUserEventValue {
    private static final String NEAR = "near";
    private static final String PART = "part";
    public final Event event;
    public final EasyAddSimpleUserValue user;

    public enum Event {
        NEAR,
        PART,
        OTHERWISE
    }

    public EasyAddNearUserEventValue(JSONObject jsonObject) {
        String strEvent = jsonObject.optString("ev");
        if (NEAR.equals(strEvent)) {
            this.event = Event.NEAR;
        } else if ("part".equals(strEvent)) {
            this.event = Event.PART;
        } else {
            this.event = Event.OTHERWISE;
        }
        this.user = new EasyAddSimpleUserValue(jsonObject.optJSONObject("user"));
    }
}
