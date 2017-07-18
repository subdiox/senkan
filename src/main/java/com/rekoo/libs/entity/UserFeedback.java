package com.rekoo.libs.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.rekoo.libs.utils.JsonUtils.Content;

public class UserFeedback implements Parcelable {
    public static final Creator<UserFeedback> CREATOR = new Creator<UserFeedback>() {
        public UserFeedback[] newArray(int size) {
            return new UserFeedback[size];
        }

        public UserFeedback createFromParcel(Parcel source) {
            UserFeedback feedback = new UserFeedback();
            feedback.setCategory(source.readString());
            feedback.setContent(source.readString());
            return feedback;
        }
    };
    protected String category;
    protected String content;

    public UserFeedback setFeedContent(UserFeedback feedback, Content content) {
        feedback.setCategory(this.category);
        return feedback;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.content);
    }
}
