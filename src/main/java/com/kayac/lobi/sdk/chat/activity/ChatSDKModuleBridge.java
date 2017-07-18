package com.kayac.lobi.sdk.chat.activity;

import android.os.Bundle;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.UserValue;

public class ChatSDKModuleBridge {

    public interface ChatProfileActivityBridge {
        public static final String EXTRAS_CAN_KICK = "EXTRAS_CAN_KICK";
        public static final String EXTRAS_GROUP_UID = "EXTRAS_GROUP_UID";
        public static final String EXTRAS_TARGET_USER = "EXTRAS_TARGET_USER";
        public static final String PATH_CHAT_PROFILE = "/chat_profile";
    }

    public static void startChatProfile(UserValue targetUser) {
        if (targetUser != null) {
            startPathChatProfile(targetUser, null);
        }
    }

    public static void startChatProfileFromGroup(UserValue targetUser, String groupUid) {
        if (targetUser != null && groupUid != null) {
            GroupValue group = TransactionDatastore.getGroup(groupUid, AccountDatastore.getCurrentUser().getUid());
            if (group != null) {
                Bundle extras = new Bundle();
                extras.putString(ChatProfileActivityBridge.EXTRAS_GROUP_UID, groupUid);
                GroupPermissionValue permission = group.getPermission();
                extras.putBoolean(ChatProfileActivityBridge.EXTRAS_CAN_KICK, permission != null ? permission.kick : false);
                startPathChatProfile(targetUser, extras);
            }
        }
    }

    private static void startPathChatProfile(UserValue targetUser, Bundle extras) {
        if (ModuleUtil.chatIsAvailable() && !targetUser.equals(AccountDatastore.getCurrentUser())) {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putString(PathRouter.PATH, ChatProfileActivityBridge.PATH_CHAT_PROFILE);
            extras.putParcelable("EXTRAS_TARGET_USER", targetUser);
            PathRouter.startPath(extras, 65536);
        }
    }
}
