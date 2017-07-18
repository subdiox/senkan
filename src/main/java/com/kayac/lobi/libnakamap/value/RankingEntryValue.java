package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.net.APIDef.PostRankingScore.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class RankingEntryValue implements Parcelable {
    public static final Creator<RankingEntryValue> CREATOR = new Creator<RankingEntryValue>() {
        public RankingEntryValue createFromParcel(Parcel source) {
            return new RankingEntryValue(source);
        }

        public RankingEntryValue[] newArray(int size) {
            return new RankingEntryValue[size];
        }
    };
    private final String mDisplayScore;
    private final String mIcon;
    private final boolean mIsSelf;
    private final String mName;
    private final long mRank;
    private final long mScore;
    private final String mUid;

    public static final class Builder {
        private String mDisplayScore;
        private String mIcon;
        private boolean mIsSelf;
        private String mName;
        private long mRank;
        private long mScore;
        private String mUid;

        public Builder(RankingEntryValue src) {
            this.mRank = src.getRank();
            this.mUid = src.getUid();
            this.mIsSelf = src.isSelf();
            this.mName = src.getName();
            this.mScore = src.getScore();
            this.mDisplayScore = src.getDisplayScore();
            this.mIcon = src.getIcon();
        }

        public void setRank(long mRank) {
            this.mRank = mRank;
        }

        public void setUid(String mUid) {
            this.mUid = mUid;
        }

        public void setIsSelf(boolean mIsSelf) {
            this.mIsSelf = mIsSelf;
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public void setScore(long mScore) {
            this.mScore = mScore;
        }

        public void setDisplayScore(String mDisplayScore) {
            this.mDisplayScore = mDisplayScore;
        }

        public void setIcon(String mIcon) {
            this.mIcon = mIcon;
        }

        public RankingEntryValue build() {
            return new RankingEntryValue(this.mRank, this.mUid, this.mIsSelf, this.mName, this.mScore, this.mDisplayScore, this.mIcon);
        }
    }

    public RankingEntryValue(long mRank, String mUid, boolean mIsSelf, String mName, long mScore, String mDisplayScore, String mIcon) {
        this.mRank = mRank;
        this.mUid = mUid;
        this.mIsSelf = mIsSelf;
        this.mName = mName;
        this.mScore = mScore;
        this.mDisplayScore = mDisplayScore;
        this.mIcon = mIcon;
    }

    public RankingEntryValue(JSONObject json) {
        if (json != null) {
            this.mRank = Long.parseLong(JSONUtil.getString(json, "rank", "0"));
            this.mUid = JSONUtil.getString(json, "uid", null);
            this.mIsSelf = JSONUtil.getString(json, "is_self", "0").equals("1");
            this.mName = JSONUtil.getString(json, "name", null);
            this.mScore = Long.parseLong(JSONUtil.getString(json, RequestKey.SCORE, "0"));
            this.mDisplayScore = JSONUtil.getString(json, "display_score", null);
            this.mIcon = JSONUtil.getString(json, "icon", null);
            return;
        }
        this.mRank = 0;
        this.mUid = null;
        this.mIsSelf = false;
        this.mName = null;
        this.mScore = 0;
        this.mDisplayScore = null;
        this.mIcon = null;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        if (this.mRank != 0) {
            try {
                json.put("rank", Long.toString(this.mRank));
                json.put("uid", this.mUid);
                json.put("is_self", this.mIsSelf ? "1" : "0");
                json.put("name", this.mName);
                json.put(RequestKey.SCORE, Long.toString(this.mScore));
                json.put("display_score", this.mDisplayScore);
                json.put("icon", this.mIcon);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public long getRank() {
        return this.mRank;
    }

    public String getUid() {
        return this.mUid;
    }

    public boolean isSelf() {
        return this.mIsSelf;
    }

    public String getName() {
        return this.mName;
    }

    public long getScore() {
        return this.mScore;
    }

    public String getDisplayScore() {
        return this.mDisplayScore;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public int describeContents() {
        return 0;
    }

    private RankingEntryValue(Parcel source) {
        this.mRank = source.readLong();
        this.mUid = source.readString();
        this.mIsSelf = source.readByte() > (byte) 0;
        this.mName = source.readString();
        this.mScore = source.readLong();
        this.mDisplayScore = source.readString();
        this.mIcon = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mRank);
        dest.writeString(this.mUid);
        dest.writeByte((byte) (this.mIsSelf ? 1 : 0));
        dest.writeString(this.mName);
        dest.writeLong(this.mScore);
        dest.writeString(this.mDisplayScore);
        dest.writeString(this.mIcon);
    }
}
