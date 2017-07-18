package com.kayac.lobi.libnakamap.value;

public class FileCacheValue {
    private final long mCreatedAt;
    private final int mFileSize;
    private final String mPath;
    private final String mType;

    public static final class Builder {
        private long mCreatedAt;
        private int mFileSize;
        private String mPath;
        private String mType;

        public Builder(FileCacheValue src) {
            this.mPath = src.getPath();
            this.mType = src.getType();
            this.mFileSize = src.getFileSize();
            this.mCreatedAt = src.getCreatedAt();
        }

        public void setPath(String path) {
            this.mPath = path;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setFileSize(int fileSize) {
            this.mFileSize = fileSize;
        }

        public void setCreatedAt(long createdAt) {
            this.mCreatedAt = createdAt;
        }

        public FileCacheValue build() {
            return new FileCacheValue(this.mPath, this.mType, this.mFileSize, this.mCreatedAt);
        }
    }

    public FileCacheValue(String path, String type, int fileSize, long createdAt) {
        this.mPath = path;
        this.mType = type;
        this.mFileSize = fileSize;
        this.mCreatedAt = createdAt;
    }

    public String getPath() {
        return this.mPath;
    }

    public String getType() {
        return this.mType;
    }

    public int getFileSize() {
        return this.mFileSize;
    }

    public long getCreatedAt() {
        return this.mCreatedAt;
    }
}
