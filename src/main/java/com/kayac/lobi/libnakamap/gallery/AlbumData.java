package com.kayac.lobi.libnakamap.gallery;

public class AlbumData {
    private int count;
    private final String cover;
    private final String name;
    private final String path;

    public AlbumData(String name, String path, String cover, int count) {
        this.name = name;
        this.count = count;
        this.cover = cover;
        this.path = path;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPath() {
        return this.path;
    }

    public String getCover() {
        return this.cover;
    }

    public String getName() {
        return this.name;
    }
}
