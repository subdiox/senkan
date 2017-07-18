package com.kayac.lobi.libnakamap.value;

import org.json.JSONObject;

public class EasyAddSimpleUserValue {
    public String description;
    public String icon;
    public String name;
    public String uid;

    public EasyAddSimpleUserValue(JSONObject jsonObject) {
        this.description = jsonObject.optString("description");
        this.uid = jsonObject.optString("uid");
        this.name = jsonObject.optString("name");
        this.icon = jsonObject.optString("icon");
    }

    public String toString() {
        return "[SimpleUser: " + this.uid + "]";
    }

    public boolean equals(Object o) {
        if (this.uid != null && (o instanceof EasyAddSimpleUserValue)) {
            return this.uid.equals(((EasyAddSimpleUserValue) o).uid);
        }
        return false;
    }

    public int hashCode() {
        if (this.uid != null) {
            return this.uid.hashCode();
        }
        return super.hashCode();
    }
}
