package com.rekoo.libs.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.rekoo.libs.utils.JsonUtils;

public class RKUser implements Parcelable {
    public static final Creator<RKUser> CREATOR = new Creator<RKUser>() {
        public RKUser createFromParcel(Parcel source) {
            RKUser user = new RKUser();
            user.setToken(source.readString());
            user.setUserName(source.readString());
            user.setUid(source.readString());
            return user;
        }

        public RKUser[] newArray(int size) {
            return new RKUser[size];
        }
    };
    private String token;
    private String uid;
    private String userName;

    public RKUser getUserInfo(String response) {
        return JsonUtils.getUserInfo(response);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.userName);
        dest.writeString(this.uid);
    }

    public String toString() {
        return "RKUser [token=" + this.token + ", userName=" + this.userName + ", uid=" + this.uid + "]";
    }

    public int describeContents() {
        return 0;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
