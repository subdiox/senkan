package com.kayac.lobi.libnakamap.value;

public class DownloadValue {
    private final int mTotal;
    private final int mUid;

    public static final class Item {
        public static final String TYPE_IMAGE = "image";
        public static final String TYPE_VIDEO = "video";
        public final String assetUid;
        public final int downloadUid;
        public final boolean isComplete;
        public final String type;
        public final String url;

        public static final class Builder {
            public String mAssetUid;
            public int mDownloadUid;
            public boolean mIsComplete;
            public String mType;
            public String mUrl;

            public Builder(Item src) {
                this.mAssetUid = src.assetUid;
                this.mDownloadUid = src.downloadUid;
                this.mType = src.type;
                this.mUrl = src.url;
                this.mIsComplete = src.isComplete;
            }

            public void setAssrtUid(String uid) {
                this.mAssetUid = uid;
            }

            public void setUploadUid(int downloadUid) {
                this.mDownloadUid = downloadUid;
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
                return new Item(this.mAssetUid, this.mDownloadUid, this.mType, this.mUrl, this.mIsComplete);
            }
        }

        public Item(String uid, int downloadUid, String type, String url, boolean isComplete) {
            this.assetUid = uid;
            this.downloadUid = downloadUid;
            this.type = type;
            this.url = url;
            this.isComplete = isComplete;
        }
    }

    public DownloadValue(int uid, int total) {
        this.mUid = uid;
        this.mTotal = total;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getTotal() {
        return this.mTotal;
    }
}
