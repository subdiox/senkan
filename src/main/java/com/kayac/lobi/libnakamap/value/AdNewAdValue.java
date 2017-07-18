package com.kayac.lobi.libnakamap.value;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdNewAdValue {
    private final ArrayList<AdItemList> mEntries;
    private final long mEpoch;

    public static class AdItemList {
        public static final String TYPE_PERK = "perk";
        public static final String TYPE_PRIZE_GROUPS = "community";
        public static final String TYPE_RECOMMEND = "recommend";
        public static final String TYPE_RESERVATION = "reservation";
        private final ArrayList<AdItem> mEntries;
        private final String mType;

        public static class AdItem {
            private int mAdId;
            private String mPackageName;

            public AdItem(JSONObject json) {
                this.mAdId = json.optInt("ad_id", 0);
                this.mPackageName = json.optString(AuthorizedAppValue.JSON_KEY_PACKAGE);
            }

            public int getAdId() {
                return this.mAdId;
            }

            public String getPackageName() {
                return this.mPackageName;
            }
        }

        public AdItemList(String type, ArrayList<AdItem> entries) {
            this.mType = type;
            this.mEntries = entries;
        }

        public AdItemList(JSONObject json) {
            this.mType = json.optString("type", "");
            JSONArray array = json.optJSONArray("items");
            this.mEntries = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                this.mEntries.add(new AdItem(array.optJSONObject(i)));
            }
        }

        public String getType() {
            return this.mType;
        }

        public ArrayList<AdItem> getEntries() {
            return this.mEntries;
        }
    }

    public AdNewAdValue(ArrayList<AdItemList> entries, long epoch) {
        this.mEntries = entries;
        this.mEpoch = epoch;
    }

    public AdNewAdValue(JSONObject json) {
        this.mEpoch = json.optLong("epoch", 0);
        this.mEntries = new ArrayList();
        JSONArray array = json.optJSONArray("items");
        for (int i = 0; i < array.length(); i++) {
            this.mEntries.add(new AdItemList(array.optJSONObject(i)));
        }
    }

    public ArrayList<AdItemList> getEntries() {
        return this.mEntries;
    }

    public long getEpoch() {
        return this.mEpoch;
    }
}
