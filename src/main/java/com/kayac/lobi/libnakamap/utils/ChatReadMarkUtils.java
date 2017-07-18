package com.kayac.lobi.libnakamap.utils;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Chat;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;

public class ChatReadMarkUtils {
    private static final String TAG = "[readmark]";
    private static long sCacheChatAt = 0;
    private static String sCacheGroupId = null;

    public static void saveChatAtOnMemory(String groupId, long chatAt) {
        if (checkGroupId(groupId) && sCacheChatAt < chatAt) {
            Log.i(TAG, "set M group:" + groupId + " chat:" + chatAt);
            sCacheChatAt = chatAt;
        }
    }

    private static long getChatAtOnMemory(String groupId) {
        if (checkGroupId(groupId)) {
            return sCacheChatAt;
        }
        return 0;
    }

    private static boolean checkGroupId(String groupId) {
        if (TextUtils.isEmpty(groupId)) {
            Log.i(TAG, "group is empty");
            return false;
        }
        if (sCacheGroupId == null || !sCacheGroupId.equals(groupId)) {
            sCacheGroupId = groupId;
            sCacheChatAt = 0;
        }
        return true;
    }

    public static void saveChatAt(String groupId) {
        if (TextUtils.isEmpty(groupId) || !groupId.equals(sCacheGroupId)) {
            Log.i(TAG, "group is invalid");
        } else if (isJoined(groupId)) {
            long chatAt = getChatAtOnMemory(groupId);
            if (getChatAt(groupId) < chatAt) {
                Log.i(TAG, "set group:" + groupId + " chat:" + chatAt);
                TransactionDatastoreAsync.setKKValue(Chat.LATEST_READ_CHAT_AT, groupId, Long.valueOf(chatAt), null);
            }
        } else {
            Log.i(TAG, "not joined");
        }
    }

    public static long getChatAt(String groupId) {
        if (isJoined(groupId)) {
            long chatAt = ((Long) TransactionDatastore.getKKValue(Chat.LATEST_READ_CHAT_AT, groupId, Long.valueOf(0))).longValue();
            Log.i(TAG, "get group:" + groupId + " chat:" + chatAt);
            return chatAt;
        }
        Log.i(TAG, "not joined");
        return 0;
    }

    private static boolean isJoined(String groupId) {
        GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(groupId, AccountDatastore.getCurrentUser().getUid());
        if (groupDetail == null || GroupValue.NOT_JOINED.equals(groupDetail.getType())) {
            return false;
        }
        return true;
    }

    public static void deleteChatAt(String groupId) {
        TransactionDatastore.deleteKKValue(Chat.LATEST_READ_CHAT_AT, groupId);
    }
}
