package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.net.APIDef.GetNotifications.RequestKey;
import java.io.Serializable;
import org.json.JSONObject;

public class StartupConfigValue implements Serializable {
    private static final long serialVersionUID = 1;
    private final ChatConfig mChat;
    private final CoreConfig mCore;
    private final RankingConfig mRanking;
    private final RecConfig mRec;

    public static class ModuleConfig implements Serializable {
        private static final long serialVersionUID = 1;
        public final boolean enabled;

        public ModuleConfig(JSONObject json) {
            if (json == null) {
                this.enabled = false;
            } else {
                this.enabled = "1".equals(json.optString("enabled"));
            }
        }
    }

    public static class ChatConfig extends ModuleConfig {
        private static final long serialVersionUID = 1;

        public ChatConfig(JSONObject json) {
            super(json);
        }
    }

    public static class CoreConfig implements Serializable {
        private static final long serialVersionUID = 1;
        public final Banner contactBanner;
        public final Game game;
        public final Banner menuBanner;
        public final Banner profileBanner;

        public static class Banner implements Serializable {
            private static final long serialVersionUID = 1;
            public final String appUrl;
            public final String description;
            public final String webUrl;

            public Banner(JSONObject json) {
                if (json == null) {
                    this.webUrl = null;
                    this.appUrl = null;
                    this.description = null;
                    return;
                }
                this.webUrl = json.optString("web_url");
                this.appUrl = json.optString("app_url");
                this.description = json.optString("description");
            }
        }

        public static class Game implements Serializable {
            private static final long serialVersionUID = 1;
            public final String appName;
            public final String background;
            public final String icon;
            public final String uid;

            public Game(JSONObject json) {
                if (json == null) {
                    this.appName = null;
                    this.uid = null;
                    this.icon = null;
                    this.background = null;
                    return;
                }
                this.appName = json.optString("app_name");
                this.uid = json.optString("uid");
                this.icon = json.optString("icon");
                this.background = json.optString("background");
            }
        }

        public CoreConfig(JSONObject json) {
            if (json == null) {
                this.game = new Game(null);
                this.contactBanner = new Banner(null);
                this.menuBanner = new Banner(null);
                this.profileBanner = new Banner(null);
                return;
            }
            this.game = new Game(json.optJSONObject("game"));
            this.contactBanner = new Banner(json.optJSONObject("contact_banner"));
            this.menuBanner = new Banner(json.optJSONObject("menu_banner"));
            this.profileBanner = new Banner(json.optJSONObject("profile_banner"));
        }
    }

    public static class RankingConfig extends ModuleConfig {
        private static final long serialVersionUID = 1;

        public RankingConfig(JSONObject json) {
            super(json);
        }
    }

    public static class RecConfig extends ModuleConfig {
        private static final long serialVersionUID = 1;

        public RecConfig(JSONObject json) {
            super(json);
        }
    }

    public StartupConfigValue(JSONObject json) {
        this.mCore = new CoreConfig(json.optJSONObject("core"));
        this.mRec = new RecConfig(json.optJSONObject(RequestKey.SERVICE_REC));
        this.mRanking = new RankingConfig(json.optJSONObject("ranking"));
        this.mChat = new ChatConfig(json.optJSONObject("chat"));
    }

    public CoreConfig getCoreConfig() {
        return this.mCore;
    }

    public RecConfig getRecConfig() {
        return this.mRec;
    }

    public RankingConfig getRankingConfig() {
        return this.mRanking;
    }

    public ChatConfig getChatConfig() {
        return this.mChat;
    }
}
