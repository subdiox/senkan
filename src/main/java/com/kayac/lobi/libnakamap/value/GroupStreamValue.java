package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class GroupStreamValue {
    public static final String CHAT = "chat";
    public static final String CHAT_DELETED = "chat_deleted";
    public static final String LOCATION = "location";
    public static final String PART = "part";
    public static final String REMOVE = "remove";
    private final ChatValue mChat;
    private final String mEvent;
    private final String mId;
    private final UserValue mUser;

    public static final class Builder {
        private ChatValue mChat;
        private String mEvent;
        private String mId;
        private UserValue mUser;

        public Builder(GroupStreamValue groupStreamValue) {
            this.mEvent = groupStreamValue.getEvent();
            this.mChat = groupStreamValue.getChat();
            this.mId = groupStreamValue.getId();
            this.mUser = groupStreamValue.getUser();
        }

        public void setEvent(String event) {
            this.mEvent = event;
        }

        public void setChat(ChatValue chat) {
            this.mChat = chat;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public void setUser(UserValue user) {
            this.mUser = user;
        }

        public GroupStreamValue build() {
            return new GroupStreamValue(this.mEvent, this.mChat, this.mId, this.mUser);
        }
    }

    public GroupStreamValue(String event, ChatValue chatValue, String id, UserValue user) {
        this.mEvent = event;
        this.mChat = chatValue;
        this.mId = id;
        this.mUser = user;
    }

    public GroupStreamValue(JSONObject jsonObject) {
        this.mEvent = JSONUtil.getString(jsonObject, "event", null);
        if ("chat".equals(getEvent())) {
            this.mChat = new ChatValue(jsonObject.optJSONObject("chat"));
            this.mId = null;
            this.mUser = null;
        } else if (CHAT_DELETED.equals(getEvent())) {
            this.mChat = null;
            this.mId = JSONUtil.getString(jsonObject, "id", null);
            this.mUser = null;
        } else if (LOCATION.equals(getEvent())) {
            this.mChat = null;
            this.mId = null;
            this.mUser = new UserValue(jsonObject.optJSONObject("user"));
        } else if ("part".equals(getEvent())) {
            this.mChat = null;
            this.mId = null;
            this.mUser = new UserValue(jsonObject.optJSONObject("user"));
        } else if ("remove".equals(getEvent())) {
            this.mChat = null;
            this.mId = null;
            this.mUser = null;
        } else {
            this.mChat = null;
            this.mId = null;
            this.mUser = null;
        }
    }

    public String getEvent() {
        return this.mEvent;
    }

    public ChatValue getChat() {
        return this.mChat;
    }

    public String getId() {
        return this.mId;
    }

    public UserValue getUser() {
        return this.mUser;
    }
}
