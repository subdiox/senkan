package com.kayac.lobi.libnakamap.utils;

import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;

public class GroupValueUtils {
    public static GroupDetailValue convertToGroupDetailValue(GroupValue group) {
        return new GroupDetailValue(group.getUid(), group.getName(), group.getDescription(), group.getCreatedDate(), group.getIcon(), group.getStreamHost(), group.getMembersCount(), 0, group.isOnline(), group.isPublic(), group.isOfficial(), group.isAuthorized(), group.getType(), System.currentTimeMillis(), group.isPushEnabled(), group.isNotice(), group.getPermission(), group.getExId());
    }

    public static GroupDetailValue updateGroupDetailValue(GroupDetailValue groupDetail, GroupValue group) {
        if (groupDetail == null) {
            return convertToGroupDetailValue(group);
        }
        return new GroupDetailValue(group.getUid(), group.getName(), group.getDescription(), group.getCreatedDate(), group.getIcon(), group.getStreamHost(), group.getMembersCount(), groupDetail.getOnlineUsers(), group.isOnline(), group.isPublic(), group.isOfficial(), group.isAuthorized(), group.getType(), groupDetail.getLastChatAt(), group.isPushEnabled(), group.isNotice(), group.getPermission(), group.getExId());
    }

    public static void setGroupValueAndGroupDetailValue(GroupValue group, GroupDetailValue oldGroupDetailValue, String userUid, boolean updatePublicCategory) {
        TransactionDatastore.setGroup(group, userUid);
        GroupDetailValue newGroupDetailValue = updateGroupDetailValue(oldGroupDetailValue, group);
        TransactionDatastore.setGroupDetail(newGroupDetailValue, userUid);
        if (updatePublicCategory) {
            CategoryValue category = TransactionDatastore.getCategory("public", userUid);
            if (category != null) {
                GroupDetailValue deleteTarget = null;
                for (GroupDetailValue categoryGroupDetail : category.getGroupDetails()) {
                    if (categoryGroupDetail.getUid().equals(newGroupDetailValue.getUid())) {
                        deleteTarget = categoryGroupDetail;
                        break;
                    }
                }
                if (deleteTarget != null) {
                    category.getGroupDetails().remove(deleteTarget);
                }
                category.getGroupDetails().add(newGroupDetailValue);
                TransactionDatastore.setCategory(category, userUid);
            }
        }
    }
}
