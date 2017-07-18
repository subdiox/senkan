package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import org.json.JSONObject;

public class ChatReferValue implements Parcelable {
    public static final Creator<ChatReferValue> CREATOR = new Creator<ChatReferValue>() {
        public ChatReferValue createFromParcel(Parcel source) {
            return new ChatReferValue(source);
        }

        public ChatReferValue[] newArray(int size) {
            return new ChatReferValue[size];
        }
    };
    public final String actionTitle;
    public final String image;
    public final String link;
    public final String title;
    public final String type;

    public static final class Builder {
        private String mActionTitle;
        private String mImage;
        private String mLink;
        private String mTitle;
        private String mType;

        public Builder(ChatReferValue src) {
            this.mType = src.type;
            this.mTitle = src.title;
            this.mImage = src.image;
            this.mActionTitle = src.actionTitle;
            this.mLink = src.link;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public void setImage(String image) {
            this.mImage = image;
        }

        public void setActionTitle(String actionTitle) {
            this.mActionTitle = actionTitle;
        }

        public void setLink(String link) {
            this.mLink = link;
        }

        public ChatReferValue build() {
            return new ChatReferValue(this.mType, this.mTitle, this.mImage, this.mActionTitle, this.mLink);
        }
    }

    public ChatReferValue(String type, String title, String image, String actionTitle, String link) {
        this.type = type;
        this.title = title;
        this.image = image;
        this.actionTitle = actionTitle;
        this.link = link;
    }

    public ChatReferValue(JSONObject jsonObject) {
        this.type = jsonObject.optString("type");
        this.title = jsonObject.optString(LoginEntranceDialog.ARGUMENTS_TITLE);
        this.image = jsonObject.optString("image");
        this.actionTitle = jsonObject.optString("action_title");
        this.link = jsonObject.optString("link");
    }

    public int describeContents() {
        return 0;
    }

    private ChatReferValue(Parcel in) {
        this.type = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.actionTitle = in.readString();
        this.link = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.actionTitle);
        dest.writeString(this.link);
    }
}
