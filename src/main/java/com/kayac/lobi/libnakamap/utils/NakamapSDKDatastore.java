package com.kayac.lobi.libnakamap.utils;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.UUID;

public class NakamapSDKDatastore {

    public static final class Key {
        public static final String APPLINK_DATA_CACHE = "applinkDataCache";
        public static final String CUSTOM_FRIEND_LIST_EXIDS = "customFriendListExids";
        public static final String ENCRYPTED_EX_ID = "encryptedExId";
        public static final String EVENT_FIELDS_CACHE = "eventFieldsCache";
        public static final String GAME_PROFILE_CALLBACK_ENABLED = "gameProfileCallbackEnabled";
        public static final String GAME_PROFILE_CALLBACK_PENDING_EXID = "gameProfileCallbackPendingExid";
        public static final String HAS_ACCEPTED_TERMS_OF_USE = "hasAcceptedTermsOfUse";
        public static final String HAS_CREATED_USER = "hasCreatedUser";
        public static final String INSTALL_ID = "installId";
        public static final String IS_REPORT_SENT = "isReportSent";
        public static final String IV = "iv";
        public static final String LAST_REFRECHED_DATE = "lastRefreshedDate";
        public static final String LAST_SEEN_AT = "lastSeenAt";
        public static final String NAKAMAP_AUTH_SESSION = "nakamapAuthSession";
        public static final String NATIVE_NAKAMAP_APP_INSTALLED = "nativeNakamapAppInstalled";
        public static final String NATIVE_NAKAMAP_APP_SSO_SUPPORTED = "nativeNakamapAppSsoSupported";
        public static final String PRIVATE_CHAT_ENABLED = "privateChatEnabled";
        public static final String PUBLIC_CHAT_ENABLED = "publicChatEnabled";
        public static final String SSO_BOUND = "ssoBound";
        public static final String STARTUP_CONFIG = "startupConfig";
    }

    private NakamapSDKDatastore() {
    }

    public static final String getInstallId() {
        String installId = (String) AccountDatastore.getValue("installId", "");
        if (!TextUtils.isEmpty(installId)) {
            return installId;
        }
        installId = UUID.randomUUID().toString();
        AccountDatastore.setValue("installId", installId);
        return installId;
    }

    public static void saveCurrentUser(UserValue user) {
        AccountDatastore.setCurrentUser(user);
        AccountDatastore.setValue(Key.HAS_CREATED_USER, Boolean.TRUE);
    }
}
