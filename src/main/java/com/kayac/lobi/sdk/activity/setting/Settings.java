package com.kayac.lobi.sdk.activity.setting;

import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.Facebook;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.GlobalSettings;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.Mail;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.NotificationSound;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.ServerSettings;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.Twitter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeSettings;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.List;

public class Settings {
    private static final boolean GPS_ENABLED_DEFAULT = false;
    private static final boolean LOCATION_ENABLED_DEFAULT = false;
    private static final boolean RECEIVE_INVITATION_NOTICE_ENABLED_DEFAULT = true;
    private static final boolean SEARCH_ENABLED_DEFAULT = true;
    public static final int SERVER_COUNT = 7;
    public static final int SERVER_DEFAULT = 0;

    public static final boolean isBackgroundPushEnabled() {
        List<Integer> lists = AccountDatastore.getKKValues(KKey.SETTING_SHOUT_TYPE);
        if (lists == null || lists.size() == 0) {
            return true;
        }
        if (lists.contains(Integer.valueOf(0)) || lists.contains(Integer.valueOf(1))) {
            return true;
        }
        return false;
    }

    public static final void setBackgroundLocationEnabled(boolean enabled) {
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.LOCATION_ENABLED, Boolean.valueOf(enabled));
    }

    public static final boolean isBackgroundLocationEnabled() {
        return ((Boolean) AccountDatastore.getKKValue(GlobalSettings.KEY1, GlobalSettings.LOCATION_ENABLED, Boolean.valueOf(false))).booleanValue();
    }

    public static final void saveMeSettings(UserValue userValue, GetMeSettings settings) {
        setSearchableEnabled(settings.searchable);
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.AUTO_ADD_EMAIL_ENABLED, Boolean.valueOf(settings.autoAddEmail));
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.AUTO_ADD_TWITTER_ENABLED, Boolean.valueOf(settings.autoAddTwitter));
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.AUTO_ADD_FACEBOOK_ENABLED, Boolean.valueOf(settings.autoAddFacebook));
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.AUTO_ADD_MIXI_ENABLED, Boolean.valueOf(settings.autoAddMixi));
        AccountDatastore.setKKValue(NotificationSound.KEY1, "SOUND:" + userValue.getUid(), settings.push.sound);
    }

    public static final void setSearchableEnabled(boolean enabled) {
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.SEARCH_ENABLED, Boolean.valueOf(enabled));
    }

    public static final boolean isSearchableEnabled() {
        return ((Boolean) AccountDatastore.getKKValue(GlobalSettings.KEY1, GlobalSettings.SEARCH_ENABLED, Boolean.valueOf(true))).booleanValue();
    }

    public static final void setReceiveInvitationNoticeEnabled(boolean enabled, String userUid) {
        AccountDatastore.setKKValue(KKey.RECEIVE_INVITATION_NOTICE_ENABLED, userUid, Boolean.valueOf(enabled));
    }

    public static final boolean isReceiveInvitationNoticeEnabled(String userUid) {
        return ((Boolean) AccountDatastore.getKKValue(KKey.RECEIVE_INVITATION_NOTICE_ENABLED, userUid, Boolean.valueOf(true))).booleanValue();
    }

    public static final void setNotificationPushEnabled(boolean enabled, String userUid) {
        AccountDatastore.setKKValue(KKey.NOTIFICATION_PUSH_ENABLED, userUid, Boolean.valueOf(enabled));
    }

    public static final boolean isNotificationPushEnabled(String userUid) {
        return ((Boolean) AccountDatastore.getKKValue(KKey.NOTIFICATION_PUSH_ENABLED, userUid, Boolean.TRUE)).booleanValue();
    }

    public static final void setBackgroundGpsEnabled(boolean enabled) {
        AccountDatastore.setKKValue(GlobalSettings.KEY1, GlobalSettings.GPS_ENABLED, Boolean.valueOf(enabled));
    }

    public static final boolean isBackgroundGpsEnabled() {
        return ((Boolean) AccountDatastore.getKKValue(GlobalSettings.KEY1, GlobalSettings.GPS_ENABLED, Boolean.valueOf(false))).booleanValue();
    }

    public static final int isServerEnabled() {
        return ((Integer) AccountDatastore.getKKValue(ServerSettings.KEY1, ServerSettings.SERVER_ENABLED, Integer.valueOf(0))).intValue();
    }

    public static final void setServerEnabled(int server) {
        AccountDatastore.setKKValue(ServerSettings.KEY1, ServerSettings.SERVER_ENABLED, Integer.valueOf(server));
    }

    public static final void deleteAllSignupInfo() {
        AccountDatastore.deleteKKValues(Mail.KEY1);
        AccountDatastore.deleteKKValues(Twitter.KEY1);
        AccountDatastore.deleteKKValues(Facebook.KEY1);
    }
}
