package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationValue {
    public static final String SCHEME = "nakamap";
    public static final String SHEME_AUTHORITY_APP = "app";
    public static final String SHEME_AUTHORITY_GROUP = "group";
    public static final String SHEME_AUTHORITY_INVITED = "invited";
    public static final String SHEME_AUTHORITY_LOGIN = "login";
    public static final String SHEME_AUTHORITY_PUBLIC_GROUPS_TREE = "public_groups_tree";
    public static final String SHEME_AUTHORITY_STORE = "store";
    public static final String SHEME_AUTHORITY_USER = "user";
    public static final String SHEME_PATH_AD_COUNTDOWN = "/ad/countdown";
    public static final String SHEME_PATH_AD_PERKS = "/ad/perks";
    public static final String SHEME_PATH_AD_RECOMMENDS = "/ad/recommends";
    public static final String SHEME_PATH_AD_RESERVATIONS = "/ad/reservations";
    public static final String SHEME_PATH_VIDEO = "/video/";
    private final ArrayList<HookActionValue> mClickHook;
    private final long mCreatedDate;
    private final ArrayList<Condition> mDisplay;
    private final ArrayList<HookActionValue> mDisplayHook;
    private final String mIcon;
    private final String mId;
    private final String mLink;
    private final String mMessage;
    private final String mTitle;
    private final String mType;
    private final UserValue mUser;

    public static final class Builder {
        private ArrayList<HookActionValue> mClickHook;
        private long mCreatedDate;
        private ArrayList<Condition> mDisplay;
        private ArrayList<HookActionValue> mDisplayHook;
        private String mIcon;
        private String mId;
        private String mLink;
        private String mMessage;
        private String mTitle;
        private String mType;
        private UserValue mUser;

        public Builder(NotificationValue src) {
            this.mId = src.getId();
            this.mUser = src.getUser();
            this.mTitle = src.getTitle();
            this.mMessage = src.getMessage();
            this.mIcon = src.getIcon();
            this.mType = src.getType();
            this.mCreatedDate = src.getCreatedDate();
            this.mLink = src.getLink();
            this.mDisplay = src.getDisplay();
            this.mDisplayHook = src.getDisplayHook();
            this.mClickHook = src.getClickHook();
        }

        public void setId(String id) {
            this.mId = id;
        }

        public void setUser(UserValue user) {
            this.mUser = user;
        }

        public void setUser(String title) {
            this.mTitle = title;
        }

        public void setMessage(String message) {
            this.mMessage = message;
        }

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setTCreatedDate(long date) {
            this.mCreatedDate = date;
        }

        public void setLink(String link) {
            this.mLink = link;
        }

        public void setDisplay(ArrayList<Condition> display) {
            this.mDisplay = display;
        }

        public void setDisplayHook(ArrayList<HookActionValue> displayHook) {
            this.mDisplayHook = displayHook;
        }

        public void setClickHook(ArrayList<HookActionValue> clickHook) {
            this.mClickHook = clickHook;
        }

        public NotificationValue build() {
            return new NotificationValue(this.mId, this.mUser, this.mTitle, this.mMessage, this.mIcon, this.mType, this.mLink, this.mCreatedDate, this.mDisplay, this.mDisplayHook, this.mClickHook);
        }
    }

    public static class Condition {
        public static final String TYPE_APP_NOT_INSTALLED = "app_not_installed";
        private final Params mParams;
        private final String mType;

        public static abstract class Params {
        }

        public static class NotInstalledParams extends Params {
            private final String mPackageName;

            public NotInstalledParams(JSONObject json) {
                this.mPackageName = json.optString(AuthorizedAppValue.JSON_KEY_PACKAGE);
            }

            public String getPackageName() {
                return this.mPackageName;
            }
        }

        public Condition(JSONObject json) {
            this.mType = json.optString("type");
            JSONObject params = json.optJSONObject("params");
            if (this.mType.equals(TYPE_APP_NOT_INSTALLED)) {
                this.mParams = new NotInstalledParams(params);
            } else {
                this.mParams = null;
            }
        }

        public String getType() {
            return this.mType;
        }

        public Params getParams() {
            return this.mParams;
        }
    }

    public NotificationValue(String id, UserValue user, String title, String message, String icon, String type, String link, long date, ArrayList<Condition> display, ArrayList<HookActionValue> displayHook, ArrayList<HookActionValue> clickHook) {
        this.mId = id;
        this.mUser = user;
        this.mTitle = title;
        this.mMessage = message;
        this.mIcon = icon;
        this.mType = type;
        this.mCreatedDate = date;
        this.mLink = link;
        this.mDisplay = display;
        this.mDisplayHook = displayHook;
        this.mClickHook = clickHook;
    }

    public NotificationValue(JSONObject json) {
        int i;
        JSONObject item;
        this.mId = JSONUtil.getString(json, "id", null);
        this.mUser = new UserValue(json.optJSONObject("user"));
        this.mTitle = JSONUtil.getString(json, LoginEntranceDialog.ARGUMENTS_TITLE, null);
        this.mMessage = JSONUtil.getString(json, "message", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mType = JSONUtil.getString(json, "type", null);
        this.mCreatedDate = Long.parseLong(JSONUtil.getString(json, "created_date", "0")) * 1000;
        this.mLink = JSONUtil.getString(json, "link", null);
        this.mDisplay = new ArrayList();
        JSONArray array = json.optJSONArray("display");
        if (array != null) {
            for (i = 0; i < array.length(); i++) {
                item = array.optJSONObject(i);
                if (item != null) {
                    this.mDisplay.add(new Condition(item));
                }
            }
        }
        this.mDisplayHook = new ArrayList();
        array = json.optJSONArray("display_hook");
        if (array != null) {
            for (i = 0; i < array.length(); i++) {
                item = array.optJSONObject(i);
                if (item != null) {
                    this.mDisplayHook.add(new HookActionValue(item));
                }
            }
        }
        this.mClickHook = new ArrayList();
        array = json.optJSONArray("click_hook");
        if (array != null) {
            for (i = 0; i < array.length(); i++) {
                item = array.optJSONObject(i);
                if (item != null) {
                    this.mClickHook.add(new HookActionValue(item));
                }
            }
        }
    }

    public String getId() {
        return this.mId;
    }

    public UserValue getUser() {
        return this.mUser;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public String getType() {
        return this.mType;
    }

    public long getCreatedDate() {
        return this.mCreatedDate;
    }

    public String getLink() {
        return this.mLink;
    }

    public ArrayList<Condition> getDisplay() {
        return this.mDisplay;
    }

    public ArrayList<HookActionValue> getDisplayHook() {
        return this.mDisplayHook;
    }

    public ArrayList<HookActionValue> getClickHook() {
        return this.mClickHook;
    }
}
