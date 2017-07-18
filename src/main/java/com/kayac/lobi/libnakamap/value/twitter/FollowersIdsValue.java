package com.kayac.lobi.libnakamap.value.twitter;

import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FollowersIdsValue {
    public final List<String> ids = new ArrayList();
    public final String nextCursor;
    public final String previousCursor;

    public FollowersIdsValue(JSONObject jsonObject) {
        this.previousCursor = JSONUtil.getString(jsonObject, "previous_cursor_str", null);
        this.nextCursor = JSONUtil.getString(jsonObject, "next_cursor_str", null);
        JSONArray idsJsonArray = jsonObject.optJSONArray("ids");
        if (idsJsonArray != null) {
            int len = idsJsonArray.length();
            for (int i = 0; i < len; i++) {
                this.ids.add(idsJsonArray.optString(i));
            }
        }
    }
}
