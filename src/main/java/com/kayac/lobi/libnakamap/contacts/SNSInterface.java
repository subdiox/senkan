package com.kayac.lobi.libnakamap.contacts;

import android.content.Intent;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeExternalContacts;
import java.util.List;

public interface SNSInterface {
    public static final int START_ACTIVITY_REQUEST_CODE = 1258;
    public static final String SUCCESS = "success";

    public interface Callback<T> {
        void onAuthError();

        void onError(String str);

        void onSuccess(T t);
    }

    public enum PrivacySettings {
        EVERYONE("everyone"),
        FRIENDS("friends"),
        FRIENDS_OF_FRIENDS("friends_of_friends");
        
        private String mSettings;

        private PrivacySettings(String settings) {
            this.mSettings = settings;
        }

        public String settings() {
            return this.mSettings;
        }
    }

    public interface StartActivityForResultInterface {
        void startActivityForResult(Intent intent, int i);
    }

    void destroy();

    String getId(PostMeExternalContacts postMeExternalContacts);

    boolean isSignedIn();

    void loadFriends(Callback<List<ExternalContact>> callback);

    String postMeExternalContactsKey();

    String service();

    void startAuth(StartActivityForResultInterface startActivityForResultInterface);
}
