package com.rekoo.libs.entity;

public class Feedback {
    public int category;
    public String content;
    public String datetime;
    public int fid;
    public String status;
    public String submiter;

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFid() {
        return this.fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getSubmiter() {
        return this.submiter;
    }

    public void setSubmiter(String submiter) {
        this.submiter = submiter;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
