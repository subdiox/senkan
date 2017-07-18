package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.kayac.lobi.libnakamap.utils.ChatListUtil;
import org.json.JSONObject;

public final class PushNotificationValue {
    public static final String AUTOMATICALLY_ADDED = "aa";
    public static final String INVITED = "i";
    public static final String JOIN = "j";
    public static final String NORMAL = "cn";
    public static final String SHOUT = "cs";
    public final int badge;
    public final String chatMessage;
    public final String chatOwner;
    public final String groupChatId;
    public final String groupName;
    public final String groupUid;
    public final String image;
    public final String message;
    public final String sound;
    public final String type;
    public final String uid;

    public static final class Builder {
        private int mBadge;
        private String mChatMessage;
        private String mChatOwner;
        private String mGroupChatId;
        private String mGroupName;
        private String mGroupUid;
        private String mImage;
        private String mMessage;
        private String mSound;
        private String mType;
        private String mUid;

        public Builder(PushNotificationValue value) {
            this.mUid = value.uid;
            this.mType = value.type;
            this.mMessage = value.message;
            this.mGroupUid = value.groupUid;
            this.mBadge = value.badge;
            this.mGroupChatId = value.groupChatId;
            this.mSound = value.sound;
            this.mChatOwner = value.chatOwner;
            this.mGroupName = value.groupName;
            this.mChatMessage = value.chatMessage;
            this.mImage = value.image;
        }

        public void setUid(String uid) {
            this.mUid = uid;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setMessage(String message) {
            this.mMessage = message;
        }

        public void setGroupUid(String groupUid) {
            this.mGroupUid = groupUid;
        }

        public void setBadge(int badge) {
            this.mBadge = badge;
        }

        public void setGroupChatId(String groupChatId) {
            this.mGroupChatId = groupChatId;
        }

        public void setSound(String sound) {
            this.mSound = sound;
        }

        public void setChatOwner(String chatOwner) {
            this.mChatOwner = chatOwner;
        }

        public void setGroupName(String groupName) {
            this.mGroupName = groupName;
        }

        public void setChatMessage(String chatMessage) {
            this.mChatMessage = chatMessage;
        }

        public void setImage(String image) {
            this.mImage = image;
        }

        public PushNotificationValue build() {
            return new PushNotificationValue(this.mUid, this.mType, this.mMessage, this.mGroupUid, this.mBadge, this.mGroupChatId, this.mSound, this.mChatOwner, this.mGroupName, this.mChatMessage, this.mImage);
        }
    }

    public PushNotificationValue(String uid, String type, String message, String gid, int badge, String groupChatId, String sound, String chatOwner, String groupName, String chatMessage, String image) {
        this.uid = uid;
        this.type = type;
        this.message = message;
        this.groupUid = gid;
        this.badge = badge;
        this.groupChatId = groupChatId;
        this.sound = sound;
        this.chatOwner = chatOwner;
        this.groupName = groupName;
        this.chatMessage = chatMessage;
        this.image = image;
    }

    public PushNotificationValue(JSONObject jsonObject) {
        int badgeValue;
        JSONObject data = jsonObject.optJSONObject(RequestKey.DATA);
        if (data == null) {
            data = new JSONObject();
        }
        this.uid = data.optString("u");
        this.type = data.optString("t");
        this.message = data.optString("m");
        this.groupUid = data.optString("g");
        try {
            badgeValue = Integer.parseInt(data.optString(ChatListUtil.SUFFIX_CHAT));
        } catch (NumberFormatException e) {
            badgeValue = 0;
        }
        this.badge = badgeValue;
        this.groupChatId = data.optString("gc");
        this.sound = data.optString("s");
        this.chatOwner = data.optString("co");
        this.groupName = data.optString("gn");
        this.chatMessage = data.optString("cm", this.message);
        this.image = data.optString(INVITED);
    }
}
