package com.kayac.lobi.sdk.path;

import android.app.Activity;
import com.kayac.lobi.libnakamap.components.PathRouter.Config;
import com.kayac.lobi.sdk.activity.RootActivity;
import com.kayac.lobi.sdk.activity.ad.AdRecommendActivity;
import com.kayac.lobi.sdk.activity.group.ContactListActivity;
import com.kayac.lobi.sdk.activity.group.CreateNewGroupActivity;
import com.kayac.lobi.sdk.activity.group.UserFollowerListActivity;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import com.kayac.lobi.sdk.activity.menu.MenuActivity;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.activity.profile.ProfileEditActivity;
import com.kayac.lobi.sdk.activity.setting.WebViewSetting;
import com.kayac.lobi.sdk.activity.stamp.StampActivity;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.chat.activity.ChatEditActivity;
import com.kayac.lobi.sdk.chat.activity.ChatGroupInfoActivity;
import com.kayac.lobi.sdk.chat.activity.ChatGroupInfoLeaderChange;
import com.kayac.lobi.sdk.chat.activity.ChatGroupInfoSettingsActivity;
import com.kayac.lobi.sdk.chat.activity.ChatMemberActivity;
import com.kayac.lobi.sdk.chat.activity.ChatReplyActivity;
import java.util.HashMap;
import java.util.Map;

public class PathRouterConfig implements Config {
    public static final String PATH_CHAT_SDK_DEFAULT = "/grouplist";
    public static final String PATH_RANKING_SDK_DEFAULT = "/ranking";
    public static final String PATH_REC_SDK_DEFAULT = "/rec_playlist";
    private static final Config sConfig = new PathRouterConfig();
    private final Map<String, Class<? extends Activity>> mPaths = new HashMap();

    private PathRouterConfig() {
        this.mPaths.put("/", RootActivity.class);
        this.mPaths.put(ProfileActivity.PATH_PROFILE, ProfileActivity.class);
        this.mPaths.put(ProfileActivity.PATH_MY_PROFILE, ProfileActivity.class);
        this.mPaths.put(ProfileEditActivity.PATH_PROFILE_EDIT, ProfileEditActivity.class);
        this.mPaths.put(ContactListActivity.PATH_CONTACTS, ContactListActivity.class);
        this.mPaths.put(UserFollowerListActivity.PATH_USER_FOLLOWS, UserFollowerListActivity.class);
        this.mPaths.put(UserFollowerListActivity.PATH_USER_FOLLOWERS, UserFollowerListActivity.class);
        this.mPaths.put(MenuActivity.PATH_MENU, MenuActivity.class);
        this.mPaths.put(WebViewSetting.PATH_WEBVIEW_SETTINGS, WebViewSetting.class);
        this.mPaths.put(WebViewSetting.PATH_COMMUNITY_WEBVIEW, WebViewSetting.class);
        this.mPaths.put(WebViewSetting.PATH_WEBVIEW, WebViewSetting.class);
        this.mPaths.put(InvitationWebViewActivity.PATH_INVITATION, InvitationWebViewActivity.class);
        this.mPaths.put(AdRecommendActivity.PATH_AD_RECOMMEND, AdRecommendActivity.class);
        this.mPaths.put(ChatActivity.PATH_CHAT, ChatActivity.class);
        this.mPaths.put(ChatReplyActivity.PATH_CHAT_REPLY, ChatReplyActivity.class);
        this.mPaths.put(ChatEditActivity.PATH_CHAT_EDIT, ChatEditActivity.class);
        this.mPaths.put(ChatGroupInfoActivity.PATH_CHAT_INFO, ChatGroupInfoActivity.class);
        this.mPaths.put(ChatMemberActivity.PATH_CHAT_INFO_MEMBERS, ChatMemberActivity.class);
        this.mPaths.put(ChatGroupInfoSettingsActivity.PATH_GROUP_SETTINGS_FROM_CHAT, ChatGroupInfoSettingsActivity.class);
        this.mPaths.put(ChatGroupInfoSettingsActivity.PATH_GROUP_SETTINGS_FROM_INFO, ChatGroupInfoSettingsActivity.class);
        this.mPaths.put(ChatGroupInfoLeaderChange.PATH_CHANGE_LEADER, ChatGroupInfoLeaderChange.class);
        this.mPaths.put(StampActivity.PATH_CHAT_STAMP, StampActivity.class);
        this.mPaths.put(StampActivity.PATH_CHAT_REPLY_STAMP, StampActivity.class);
        this.mPaths.put(StampActivity.PATH_CHAT_EDIT_STAMP, StampActivity.class);
        this.mPaths.put(CreateNewGroupActivity.PATH_NEW, CreateNewGroupActivity.class);
    }

    public static Config getConfig() {
        return sConfig;
    }

    public Class<? extends Activity> getClassForPath(String path) {
        return (Class) this.mPaths.get(path);
    }

    public void updateClassForPath(String path, Class<? extends Activity> classRef) {
        this.mPaths.remove(path);
        this.mPaths.put(path, classRef);
    }
}
