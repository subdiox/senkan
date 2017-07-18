package com.kayac.lobi.libnakamap.value;

public class UploadValue {
    public static final int SHOUT_FALSE = 0;
    public static final int SHOUT_TRUE = 1;
    private final String mGroupName;
    private final String mGroupUid;
    private final String mMessage;
    private final String mReplyTo;
    private final int mShout;
    private final int mTotal;
    private final int mUid;

    public static final class Item {
        public static final String TYPE_IMAGE = "image";
        public static final String TYPE_VIDEO = "video";
        public final boolean isComplete;
        public final String type;
        public final String uid;
        public final int uploadUid;
        public final String url;

        public static final class Builder {
            public boolean mIsComplete;
            public String mType;
            public String mUid;
            public int mUploadUid;
            public String mUrl;

            public Builder(Item src) {
                this.mUid = src.uid;
                this.mUploadUid = src.uploadUid;
                this.mType = src.type;
                this.mUrl = src.url;
                this.mIsComplete = src.isComplete;
            }

            public void setUid(String uid) {
                this.mUid = uid;
            }

            public void setUploadUid(int uploadUid) {
                this.mUploadUid = uploadUid;
            }

            public void setType(String type) {
                this.mType = type;
            }

            public void setUrl(String url) {
                this.mUrl = url;
            }

            public void setIsComplete(boolean isComplete) {
                this.mIsComplete = isComplete;
            }

            public Item build() {
                return new Item(this.mUid, this.mUploadUid, this.mType, this.mUrl, this.mIsComplete);
            }
        }

        public Item(String uid, int uploadUid, String type, String url, boolean isComplete) {
            this.uid = uid;
            this.uploadUid = uploadUid;
            this.type = type;
            this.url = url;
            this.isComplete = isComplete;
        }
    }

    public UploadValue(int uid, String groupUid, String groupName, String replyTo, int total, String message, int shout) {
        this.mUid = uid;
        this.mGroupUid = groupUid;
        this.mGroupName = groupName;
        this.mReplyTo = replyTo;
        this.mTotal = total;
        this.mMessage = message;
        this.mShout = shout;
    }

    public int getUid() {
        return this.mUid;
    }

    public String getGroupUid() {
        return this.mGroupUid;
    }

    public String getGroupName() {
        return this.mGroupName;
    }

    public String getReplyTo() {
        return this.mReplyTo;
    }

    public int getTotal() {
        return this.mTotal;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getShout() {
        return this.mShout;
    }
}
