package com.kayac.lobi.sdk.chat.activity;

import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import java.util.Comparator;

public class ChatValueComparator implements Comparator<ChatListItemData> {
    public int compare(ChatListItemData lhs, ChatListItemData rhs) {
        String id0 = lhs.getOrder();
        String id1 = rhs.getOrder();
        int length0 = id0.length();
        int length1 = id1.length();
        if (length0 < length1) {
            return 1;
        }
        if (length1 < length0) {
            return -1;
        }
        return id1.compareTo(id0);
    }
}
