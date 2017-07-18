package com.rekoo.libs.entity;

public class Transform {
    private String newUid;
    private String oldUid;
    private String rkTrans;

    public String getRkTrans() {
        return this.rkTrans;
    }

    public void setRkTrans(String rkTrans) {
        this.rkTrans = rkTrans;
    }

    public String getOldUid() {
        return this.oldUid;
    }

    public void setOldUid(String oldUid) {
        this.oldUid = oldUid;
    }

    public String getNewUid() {
        return this.newUid;
    }

    public void setNewUid(String newUid) {
        this.newUid = newUid;
    }
}
