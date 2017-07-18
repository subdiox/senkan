package com.kayac.lobi.libnakamap.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.datastore.CommonDatastoreImpl.Function;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.AdWaitingApp;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Asset;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Category;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Chat;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.ChatRefer;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Download;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.DownloadItem;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.FileCache;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Group;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupDetail;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupDetail.Order;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupMember;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupPermission;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.StampCategory;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.StampHistory;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.StampItem;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Upload;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UploadItem;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UserContact;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Widget;
import com.kayac.lobi.libnakamap.exception.NakamapException.Fatal;
import com.kayac.lobi.libnakamap.value.AdWaitingAppValue;
import com.kayac.lobi.libnakamap.value.AssetValue;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.ChatReferValue;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.ChatValue.Replies;
import com.kayac.lobi.libnakamap.value.DownloadValue;
import com.kayac.lobi.libnakamap.value.FileCacheValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.StampValue.Item;
import com.kayac.lobi.libnakamap.value.UploadValue;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.WidgetMetaDataValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionDatastoreImpl implements CommonDatastoreImpl {
    public static final void setWidget(SQLiteDatabase db, int appWidgetId, String token, String userUid, String groupUid) {
        try {
            ContentValues values = new ContentValues();
            values.put(Widget.C_APP_WIDGET_ID, Integer.valueOf(appWidgetId));
            values.put("c_token", token);
            values.put("c_user_uid", userUid);
            values.put("c_group_uid", groupUid);
            values.put(Widget.C_UPDATED_AT, Long.valueOf(System.currentTimeMillis()));
            if (db.replaceOrThrow(Widget.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final WidgetMetaDataValue getWidget(SQLiteDatabase db, int appWidgetId) {
        Cursor c = null;
        try {
            WidgetMetaDataValue widgetMetaData;
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Widget.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_group_uid", "c_token", "c_user_uid", Widget.C_UPDATED_AT}, "c_app_widget_id = ?", new String[]{String.valueOf(appWidgetId)}, null, null, null);
            if (c.moveToFirst()) {
                int i = appWidgetId;
                widgetMetaData = new WidgetMetaDataValue(i, c.getString(c.getColumnIndex("c_token")), c.getString(c.getColumnIndex("c_user_uid")), c.getString(c.getColumnIndex("c_group_uid")), c.getLong(c.getColumnIndex(Widget.C_UPDATED_AT)));
            } else {
                widgetMetaData = null;
            }
            if (c != null) {
                c.close();
            }
            return widgetMetaData;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final ArrayList<WidgetMetaDataValue> getWidgetList(SQLiteDatabase db, String groupUid) {
        Cursor c = null;
        ArrayList<WidgetMetaDataValue> ary = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Widget.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{Widget.C_APP_WIDGET_ID, "c_token", "c_user_uid", Widget.C_UPDATED_AT}, "c_group_uid = ?", new String[]{groupUid}, null, null, Widget.C_APP_WIDGET_ID);
            if (c.moveToFirst()) {
                do {
                    ary.add(new WidgetMetaDataValue(c.getInt(c.getColumnIndex(Widget.C_APP_WIDGET_ID)), c.getString(c.getColumnIndex("c_token")), c.getString(c.getColumnIndex("c_user_uid")), groupUid, c.getLong(c.getColumnIndex(Widget.C_UPDATED_AT))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return ary;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final ArrayList<WidgetMetaDataValue> getWidgetList(SQLiteDatabase db) {
        Cursor c = null;
        ArrayList<WidgetMetaDataValue> ary = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Widget.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{Widget.C_APP_WIDGET_ID, "c_token", "c_user_uid", "c_group_uid", Widget.C_UPDATED_AT}, null, null, null, null, Widget.C_APP_WIDGET_ID);
            if (c.moveToFirst()) {
                do {
                    ary.add(new WidgetMetaDataValue(c.getInt(c.getColumnIndex(Widget.C_APP_WIDGET_ID)), c.getString(c.getColumnIndex("c_token")), c.getString(c.getColumnIndex("c_user_uid")), c.getString(c.getColumnIndex("c_group_uid")), c.getLong(c.getColumnIndex(Widget.C_UPDATED_AT))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return ary;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void deleteWidget(SQLiteDatabase db, int appWidgetId) {
        try {
            db.delete(Widget.TABLE, "c_app_widget_id = ? ", new String[]{String.valueOf(appWidgetId)});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setCategory(SQLiteDatabase db, CategoryValue category, String userUid) {
        try {
            String title = category.getTitle();
            for (GroupDetailValue groupDetail : category.getGroupDetails()) {
                ContentValues values = new ContentValues();
                values.put("c_type", category.getType());
                values.put("c_title", title);
                values.put("c_user_uid", userUid);
                values.put("c_group_uid", groupDetail.getUid());
                if (db.replaceOrThrow(Category.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
                setGroupDetail(db, groupDetail, userUid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final CategoryValue getCategory(SQLiteDatabase db, String t, String userUid) {
        Cursor c = null;
        CategoryValue category = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("category_table LEFT OUTER JOIN group_detail_table ON category_table.c_group_uid = group_detail_table.c_uid AND category_table.c_user_uid = group_detail_table.c_user_uid");
            c = queryBuilder.query(db, new String[]{"category_table.c_type AS category_type", "category_table.c_title AS title", "group_detail_table.c_uid AS uid", "group_detail_table.c_name AS name", "group_detail_table.c_description AS group_detail_description", "group_detail_table.c_created_date AS created_date", "group_detail_table.c_icon AS icon", "group_detail_table.c_stream_host AS stream_host", "group_detail_table.c_total_users AS total_users", "group_detail_table.c_online_users AS online_users", "group_detail_table.c_is_online AS is_online", "group_detail_table.c_is_public AS is_public", "group_detail_table.c_is_official AS is_official", "group_detail_table.c_is_authorized AS is_authorized", "group_detail_table.c_type AS type", "group_detail_table.c_last_chat_at AS last_chat_at", "group_detail_table.c_push_enabled AS push_enabled", "group_detail_table.c_is_notice AS is_notice", "group_detail_table.c_ex_id AS ex_id"}, "category_table.c_type = ? AND category_table.c_user_uid = ?", new String[]{t, userUid}, null, null, "c_last_chat_at DESC ");
            if (c.moveToFirst()) {
                String categoryType = c.getString(c.getColumnIndex("category_type"));
                String title = c.getString(c.getColumnIndex(LoginEntranceDialog.ARGUMENTS_TITLE));
                List<GroupDetailValue> groupDetails = new ArrayList();
                do {
                    String uid = c.getString(c.getColumnIndex("uid"));
                    String name = c.getString(c.getColumnIndex("name"));
                    String groupDescription = c.getString(c.getColumnIndex("group_detail_description"));
                    long createdDate = c.getLong(c.getColumnIndex("created_date"));
                    String icon = c.getString(c.getColumnIndex("icon"));
                    String streamHost = c.getString(c.getColumnIndex("stream_host"));
                    int totalUsers = c.getInt(c.getColumnIndex("total_users"));
                    int onlineUsers = c.getInt(c.getColumnIndex("online_users"));
                    boolean isOnline = c.getInt(c.getColumnIndex("is_online")) == 1;
                    boolean isPublic = c.getInt(c.getColumnIndex("is_public")) == 1;
                    boolean isOfficial = c.getInt(c.getColumnIndex("is_official")) == 1;
                    boolean isAuthorized = c.getInt(c.getColumnIndex("is_authorized")) == 1;
                    String type = c.getString(c.getColumnIndex("type"));
                    long lastChatAt = c.getLong(c.getColumnIndex("last_chat_at"));
                    boolean pushEnabled = c.getInt(c.getColumnIndex("push_enabled")) == 1;
                    boolean isNotice = c.getInt(c.getColumnIndex("is_notice")) == 1;
                    String exId = c.getString(c.getColumnIndex("ex_id"));
                    if (!TextUtils.isEmpty(uid)) {
                        groupDetails.add(new GroupDetailValue(uid, name, groupDescription, createdDate, icon, streamHost, totalUsers, onlineUsers, isOnline, isPublic, isOfficial, isAuthorized, type, lastChatAt, pushEnabled, isNotice, getGroupPermissioin(db, uid, userUid), exId));
                    }
                } while (c.moveToNext());
                CategoryValue categoryValue = new CategoryValue(categoryType, title, groupDetails);
            }
            if (c != null) {
                c.close();
            }
            if (category != null) {
                return category;
            }
            return new CategoryValue(t, "", new ArrayList());
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    @Deprecated
    public static final List<CategoryValue> getCategories(SQLiteDatabase db, String userUid, Order order) {
        return null;
    }

    public static final void deleteCategory(SQLiteDatabase db, String groupUid, String userUid) {
        try {
            db.delete(Category.TABLE, "c_group_uid = ? AND c_user_uid = ?", new String[]{groupUid, userUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void deleteCategories(SQLiteDatabase db, String userUid, String type) {
        try {
            db.delete(Category.TABLE, "c_type = ? AND c_user_uid = ?", new String[]{type, userUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setGroupDetail(SQLiteDatabase db, GroupDetailValue groupDetail, String userUid) {
        int i = 1;
        try {
            int i2;
            ContentValues values = new ContentValues();
            values.put("c_uid", groupDetail.getUid());
            values.put("c_name", groupDetail.getName());
            values.put("c_description", groupDetail.getDescription());
            values.put("c_created_date", Long.valueOf(groupDetail.getCreatedDate()));
            values.put("c_icon", groupDetail.getIcon());
            values.put("c_stream_host", groupDetail.getStreamHost());
            values.put(GroupDetail.C_TOTAL_USERS, Integer.valueOf(groupDetail.getTotalUsers()));
            values.put(GroupDetail.C_ONLINE_USERS, Integer.valueOf(groupDetail.getOnlineUsers()));
            String str = "c_is_online";
            if (groupDetail.isOnline()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            str = "c_is_public";
            if (groupDetail.isPublic()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            str = "c_is_official";
            if (groupDetail.isOfficial()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            str = "c_is_authorized";
            if (groupDetail.isAuthorized()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            values.put("c_type", groupDetail.getType());
            values.put(GroupDetail.C_LAST_CHAT_AT, Long.valueOf(groupDetail.getLastChatAt()));
            str = "c_push_enabled";
            if (groupDetail.isPushEnabled()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            String str2 = "c_is_notice";
            if (!groupDetail.isNotice()) {
                i = 0;
            }
            values.put(str2, Integer.valueOf(i));
            values.put("c_ex_id", groupDetail.getExId());
            values.put("c_user_uid", userUid);
            setGroupPermission(db, groupDetail.getUid(), userUid, groupDetail.getPermission());
            if (db.replaceOrThrow(GroupDetail.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final GroupDetailValue getGroupDetail(SQLiteDatabase db, String groupUid, String userUid) {
        Cursor c = null;
        GroupDetailValue groupDetail = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupDetail.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", "c_name", "c_description", "c_created_date", "c_icon", "c_stream_host", GroupDetail.C_TOTAL_USERS, GroupDetail.C_ONLINE_USERS, "c_is_online", "c_is_public", "c_is_official", "c_is_authorized", "c_type", GroupDetail.C_LAST_CHAT_AT, "c_push_enabled", "c_is_notice", "c_ex_id"}, "c_uid = ? AND c_user_uid = ?", new String[]{groupUid, userUid}, null, null, null);
            if (c.moveToFirst()) {
                groupDetail = toGroupDetail(db, c, getGroupPermissioin(db, groupUid, userUid));
            }
            if (c != null) {
                c.close();
            }
            return groupDetail;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final boolean needGeoLocation(SQLiteDatabase db, String userUid) {
        Cursor c = null;
        boolean need = false;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupDetail.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid"}, "c_user_uid = ? AND c_is_online = 1", new String[]{userUid}, null, null, null, "1");
            if (c.moveToFirst()) {
                need = true;
            }
            if (c != null) {
                c.close();
            }
            return need;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final boolean needGeoLocation(SQLiteDatabase db) {
        Cursor c = null;
        boolean need = false;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupDetail.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid"}, "c_is_online = 1", null, null, null, null, "1");
            if (c.moveToFirst()) {
                need = true;
            }
            if (c != null) {
                c.close();
            }
            return need;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final boolean needPushNotification(SQLiteDatabase db, String userUid) {
        Cursor c = null;
        boolean need = false;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupDetail.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid"}, "c_user_uid = ? AND c_push_enabled = 1", new String[]{userUid}, null, null, null, "1");
            if (c.moveToFirst()) {
                need = true;
            }
            if (c != null) {
                c.close();
            }
            return need;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final boolean needPushNotification(SQLiteDatabase db) {
        Cursor c = null;
        boolean need = false;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupDetail.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid"}, "c_push_enabled = 1", null, null, null, null, "1");
            if (c.moveToFirst()) {
                need = true;
            }
            if (c != null) {
                c.close();
            }
            return need;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    private static final GroupDetailValue toGroupDetail(SQLiteDatabase db, Cursor c, GroupPermissionValue permission) {
        return new GroupDetailValue(c.getString(c.getColumnIndex("c_uid")), c.getString(c.getColumnIndex("c_name")), c.getString(c.getColumnIndex("c_description")), c.getLong(c.getColumnIndex("c_created_date")), c.getString(c.getColumnIndex("c_icon")), c.getString(c.getColumnIndex("c_stream_host")), c.getInt(c.getColumnIndex(GroupDetail.C_TOTAL_USERS)), c.getInt(c.getColumnIndex(GroupDetail.C_ONLINE_USERS)), c.getInt(c.getColumnIndex("c_is_online")) == 1, c.getInt(c.getColumnIndex("c_is_public")) == 1, c.getInt(c.getColumnIndex("c_is_official")) == 1, c.getInt(c.getColumnIndex("c_is_authorized")) == 1, c.getString(c.getColumnIndex("c_type")), c.getLong(c.getColumnIndex(GroupDetail.C_LAST_CHAT_AT)), c.getInt(c.getColumnIndex("c_push_enabled")) == 1, c.getInt(c.getColumnIndex("c_is_notice")) == 1, permission, c.getString(c.getColumnIndex("c_ex_id")));
    }

    public static final void deleteGroupDetail(SQLiteDatabase db, String groupUid, String userUid) {
        try {
            db.delete(GroupDetail.TABLE, "c_uid = ? AND c_user_uid = ?", new String[]{groupUid, userUid});
            deleteGroupPermission(db, groupUid, userUid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void deleteGroupDetailForUser(SQLiteDatabase db, String userUid) {
        try {
            db.delete(GroupDetail.TABLE, "c_user_uid = ? ", new String[]{userUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setGroup(SQLiteDatabase db, GroupValue group, String userUid) {
        int i = 1;
        try {
            int i2;
            ContentValues values = new ContentValues();
            values.put("c_uid", group.getUid());
            values.put("c_name", group.getName());
            values.put("c_description", group.getDescription());
            values.put("c_created_date", Long.valueOf(group.getCreatedDate()));
            values.put("c_icon", group.getIcon());
            values.put("c_stream_host", group.getStreamHost());
            String str = "c_push_enabled";
            if (group.isPushEnabled()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            values.put(Group.C_OWNER, group.getOwner().getUid());
            str = "c_is_online";
            if (group.isOnline()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            str = "c_is_public";
            if (group.isPublic()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            str = "c_is_official";
            if (group.isOfficial()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            values.put(str, Integer.valueOf(i2));
            String str2 = "c_is_authorized";
            if (!group.isAuthorized()) {
                i = 0;
            }
            values.put(str2, Integer.valueOf(i));
            values.put(Group.C_MEMBERS_COUNT, Integer.valueOf(group.getMembersCount()));
            values.put("c_type", group.getType());
            values.put("c_user_uid", userUid);
            values.put(Group.C_WALLPAPER, group.getWallpaper());
            values.put("c_is_notice", Boolean.valueOf(group.isNotice()));
            values.put("c_ex_id", group.getExId());
            if (db.replaceOrThrow(Group.TABLE, null, values) == -1) {
                throw new Fatal();
            }
            Function.setUser(db, group.getOwner());
            setGroupMember(db, group.getUid(), group.getMembers());
            for (UserValue user : group.getMembers()) {
                Function.setUser(db, user);
            }
            setChat(db, group.getUid(), group.getChats());
            setGroupPermission(db, group.getUid(), userUid, group.getPermission());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final GroupValue getGroup(SQLiteDatabase db, String groupUid, String userUid) {
        Cursor c = null;
        GroupValue group = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Group.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", "c_name", "c_description", "c_created_date", "c_icon", "c_stream_host", "c_push_enabled", Group.C_OWNER, "c_is_online", "c_is_public", "c_is_official", "c_is_authorized", Group.C_MEMBERS_COUNT, "c_type", Group.C_WALLPAPER, "c_is_notice", "c_ex_id"}, "c_uid = ? AND c_user_uid = ?", new String[]{groupUid, userUid}, null, null, null);
            if (c.moveToFirst()) {
                group = toGroup(db, c, userUid);
            }
            if (c != null) {
                c.close();
            }
            return group;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    private static final GroupValue toGroup(SQLiteDatabase db, Cursor c, String userUid) {
        String uid = c.getString(c.getColumnIndex("c_uid"));
        String name = c.getString(c.getColumnIndex("c_name"));
        String description = c.getString(c.getColumnIndex("c_description"));
        long createdDate = c.getLong(c.getColumnIndex("c_created_date"));
        String icon = c.getString(c.getColumnIndex("c_icon"));
        String streamHost = c.getString(c.getColumnIndex("c_stream_host"));
        boolean pushEnabled = c.getInt(c.getColumnIndex("c_push_enabled")) == 1;
        boolean isOnline = c.getInt(c.getColumnIndex("c_is_online")) == 1;
        boolean isPublic = c.getInt(c.getColumnIndex("c_is_public")) == 1;
        boolean isOfficial = c.getInt(c.getColumnIndex("c_is_official")) == 1;
        boolean isAuthorized = c.getInt(c.getColumnIndex("c_is_authorized")) == 1;
        int membersCount = c.getInt(c.getColumnIndex(Group.C_MEMBERS_COUNT));
        String type = c.getString(c.getColumnIndex("c_type"));
        UserValue owner = Function.getUser(db, c.getString(c.getColumnIndex(Group.C_OWNER)));
        List<UserValue> members = new ArrayList();
        Iterator it = getGroupMember(db, uid).iterator();
        while (it.hasNext()) {
            members.add(Function.getUser(db, (String) it.next()));
        }
        return new GroupValue(uid, name, description, createdDate, icon, streamHost, pushEnabled, members, getChats(db, uid), owner, isOnline, isPublic, isOfficial, isAuthorized, membersCount, null, getGroupPermissioin(db, uid, userUid), type, c.getString(c.getColumnIndex(Group.C_WALLPAPER)), c.getInt(c.getColumnIndex("c_is_notice")) == 1, c.getString(c.getColumnIndex("c_ex_id")), null, null);
    }

    public static final void deleteGroup(SQLiteDatabase db, String groupUid, String userUid) {
        try {
            deleteGroupMember(db, groupUid);
            deleteChatInGroup(db, groupUid);
            deleteGroupPermission(db, groupUid, userUid);
            db.delete(Group.TABLE, "c_uid = ? AND c_user_uid = ?", new String[]{groupUid, userUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setGroupMember(SQLiteDatabase db, String groupUid, List<UserValue> member) {
        try {
            deleteGroupMember(db, groupUid);
            int count = 0;
            for (UserValue u : member) {
                ContentValues values = new ContentValues();
                values.put("c_group_uid", groupUid);
                values.put("c_user_uid", u.getUid());
                int count2 = count + 1;
                values.put("c_order", Integer.valueOf(count));
                if (db.replaceOrThrow(GroupMember.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
                count = count2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<String> getGroupMember(SQLiteDatabase db, String groupUid) {
        Cursor c = null;
        ArrayList<String> userUids = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupMember.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_user_uid"}, "c_group_uid = ?", new String[]{groupUid}, null, null, "c_order");
            if (c.moveToFirst()) {
                do {
                    userUids.add(c.getString(c.getColumnIndex("c_user_uid")));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return userUids;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void deleteGroupMember(SQLiteDatabase db, String groupUid) {
        try {
            db.delete(GroupMember.TABLE, "c_group_uid = ? ", new String[]{groupUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    static final void setGroupPermission(SQLiteDatabase db, String groupUid, String userUid, GroupPermissionValue permission) {
        try {
            deleteGroupPermission(db, groupUid, userUid);
            if (permission.canUpdateIcon && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.UPDATE_ICON)) == -1) {
                throw new Fatal();
            } else if (permission.canUpdateName && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.UPDATE_NAME)) == -1) {
                throw new Fatal();
            } else if (permission.canUpdateDescription && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.UPDATE_DESCRIPTION)) == -1) {
                throw new Fatal();
            } else if (permission.canUpdateWallpaper && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.UPDATE_WALLPAPER)) == -1) {
                throw new Fatal();
            } else if (permission.invite && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.INVITE)) == -1) {
                throw new Fatal();
            } else if (permission.addMembers && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.ADD_MEMBERS)) == -1) {
                throw new Fatal();
            } else if (permission.join && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.JOIN)) == -1) {
                throw new Fatal();
            } else if (permission.remove && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, "remove")) == -1) {
                throw new Fatal();
            } else if (permission.part && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, "part")) == -1) {
                throw new Fatal();
            } else if (permission.kick && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.KICK)) == -1) {
                throw new Fatal();
            } else if (permission.peek && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, GroupPermission.PEEK)) == -1) {
                throw new Fatal();
            } else if (permission.shout && db.replaceOrThrow(GroupPermission.TABLE, null, getContantValuesForGroupPermission(groupUid, userUid, "shout")) == -1) {
                throw new Fatal();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Fatal(e);
        }
    }

    private static ContentValues getContantValuesForGroupPermission(String groupUid, String userUid, String permission) {
        ContentValues values = new ContentValues();
        values.put("c_group_uid", groupUid);
        values.put("c_user_uid", userUid);
        values.put(GroupPermission.C_PERMISSION, permission);
        return values;
    }

    static final GroupPermissionValue getGroupPermissioin(SQLiteDatabase db, String groupUid, String userUid) {
        GroupPermissionValue groupPermissionValue = null;
        Cursor c = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(GroupPermission.TABLE);
            groupPermissionValue = "c_group_uid = ? AND c_user_uid = ? ";
            c = queryBuilder.query(db, new String[]{GroupPermission.C_PERMISSION}, groupPermissionValue, new String[]{groupUid, userUid}, null, null, null);
            boolean updateIcon = false;
            boolean updateName = false;
            boolean updateDescription = false;
            boolean updateWallpaper = false;
            boolean invite = false;
            boolean addMembers = false;
            boolean join = false;
            boolean remove = false;
            boolean part = false;
            boolean kick = false;
            boolean peek = false;
            boolean shout = false;
            if (c.moveToFirst()) {
                while (true) {
                    String p = c.getString(c.getColumnIndex(GroupPermission.C_PERMISSION));
                    if (GroupPermission.UPDATE_ICON.equals(p)) {
                        updateIcon = true;
                    } else if (GroupPermission.UPDATE_NAME.equals(p)) {
                        updateName = true;
                    } else if (GroupPermission.UPDATE_DESCRIPTION.equals(p)) {
                        updateDescription = true;
                    } else if (GroupPermission.UPDATE_WALLPAPER.equals(p)) {
                        updateWallpaper = true;
                    } else if (GroupPermission.INVITE.equals(p)) {
                        invite = true;
                    } else if (GroupPermission.ADD_MEMBERS.equals(p)) {
                        addMembers = true;
                    } else if (GroupPermission.JOIN.equals(p)) {
                        join = true;
                    } else if ("remove".equals(p)) {
                        remove = true;
                    } else if ("part".equals(p)) {
                        part = true;
                    } else if (GroupPermission.KICK.equals(p)) {
                        kick = true;
                    } else if (GroupPermission.PEEK.equals(p)) {
                        peek = true;
                    } else if ("shout".equals(p)) {
                        shout = true;
                    }
                    if (!c.moveToNext()) {
                        break;
                    }
                }
            }
            groupPermissionValue = new GroupPermissionValue(updateIcon, updateName, updateDescription, updateWallpaper, invite, addMembers, join, remove, part, kick, peek, shout);
            return groupPermissionValue;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    static void deleteGroupPermission(SQLiteDatabase db, String groupUid, String userUid) {
        try {
            db.delete(GroupPermission.TABLE, "c_group_uid = ? AND c_user_uid = ?", new String[]{groupUid, userUid});
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Fatal(e);
        }
    }

    public static final void setChat(SQLiteDatabase db, String groupUid, List<ChatValue> chats) {
        try {
            deleteChatInGroup(db, groupUid);
            for (ChatValue chat : chats) {
                ContentValues values = new ContentValues();
                values.put("c_id", chat.getId());
                values.put("c_group_uid", groupUid);
                values.put("c_type", chat.getType());
                values.put("c_message", chat.getMessage());
                values.put("c_created_date", Long.valueOf(chat.getCreatedDate()));
                values.put("c_image", chat.getImage());
                values.put(Chat.C_IMAGE_TYPE, chat.getImageType());
                values.put(Chat.C_IMAGE_HEIGHT, Integer.valueOf(chat.getImageHeight()));
                values.put(Chat.C_IMAGE_WIDTH, Integer.valueOf(chat.getImageWidth()));
                values.put("c_reply_to", chat.getReplyTo());
                values.put(Chat.C_STAMP_UID, chat.getStampUid());
                values.put(Chat.C_ON_STORE, Integer.valueOf(chat.getOnStore()));
                values.put("c_user_uid", chat.getUser().getUid());
                Replies replies = chat.getReplies();
                if (replies != null) {
                    values.put(Chat.C_REPLIES_COUNT, Integer.valueOf(replies.getCount()));
                    if (replies.getChats().size() > 0) {
                        setChat(db, groupUid, replies.getChats());
                    }
                } else {
                    values.put(Chat.C_REPLIES_COUNT, Integer.valueOf(0));
                }
                values.put(Chat.C_LIKES_COUNT, Integer.valueOf(chat.getLikesCount()));
                values.put(Chat.C_BOOS_COUNT, Integer.valueOf(chat.getBoosCount()));
                values.put(Chat.C_LIKED, Integer.valueOf(chat.getLiked() ? 1 : 0));
                values.put(Chat.C_BOOED, Integer.valueOf(chat.getBooed() ? 1 : 0));
                if (db.replaceOrThrow(Chat.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
                Function.setUser(db, chat.getUser());
                setChatRefers(db, chat);
                if (chat.getAssets() != null && chat.getAssets().size() > 0) {
                    String assetString = null;
                    for (AssetValue asset : chat.getAssets()) {
                        assetString = assetString + asset.getUid() + ":" + asset.getType() + ":" + asset.getUrl();
                    }
                    values.put(Chat.C_ASSETS, assetString);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<ChatValue> getChats(SQLiteDatabase db, String groupUid) {
        ArrayList<ChatValue> replies = getReplayChats(db, groupUid);
        Cursor c = null;
        ArrayList<ChatValue> chats = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Chat.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_id", "c_type", "c_message", "c_created_date", "c_image", Chat.C_IMAGE_TYPE, Chat.C_IMAGE_HEIGHT, Chat.C_IMAGE_WIDTH, "c_reply_to", Chat.C_STAMP_UID, Chat.C_ON_STORE, "c_user_uid", Chat.C_REPLIES_COUNT, Chat.C_ASSETS, Chat.C_LIKES_COUNT, Chat.C_BOOS_COUNT, Chat.C_LIKED, Chat.C_BOOED}, "c_group_uid = ? AND c_reply_to IS NULL", new String[]{String.valueOf(groupUid)}, null, null, "c_created_date DESC ");
            if (c.moveToFirst()) {
                do {
                    chats.add(toChat(db, c, replies));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return chats;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final ArrayList<ChatValue> getReplayChats(SQLiteDatabase db, String groupUid) {
        Cursor c = null;
        ArrayList<ChatValue> chats = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Chat.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_id", "c_type", "c_message", "c_created_date", "c_image", Chat.C_IMAGE_TYPE, Chat.C_IMAGE_HEIGHT, Chat.C_IMAGE_WIDTH, "c_reply_to", Chat.C_STAMP_UID, Chat.C_ON_STORE, "c_user_uid", Chat.C_ASSETS, Chat.C_LIKES_COUNT, Chat.C_BOOS_COUNT, Chat.C_LIKED, Chat.C_BOOED}, "c_group_uid = ? AND c_reply_to IS NOT NULL", new String[]{String.valueOf(groupUid)}, null, null, "c_created_date DESC ");
            if (c.moveToFirst()) {
                do {
                    chats.add(toChat(db, c, null));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return chats;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    private static final ChatValue toChat(SQLiteDatabase db, Cursor c, ArrayList<ChatValue> replies) {
        String id = c.getString(c.getColumnIndex("c_id"));
        String type = c.getString(c.getColumnIndex("c_type"));
        String message = c.getString(c.getColumnIndex("c_message"));
        long createdDate = c.getLong(c.getColumnIndex("c_created_date"));
        String image = c.getString(c.getColumnIndex("c_image"));
        String imageType = c.getString(c.getColumnIndex(Chat.C_IMAGE_TYPE));
        int imageHeight = c.getInt(c.getColumnIndex(Chat.C_IMAGE_HEIGHT));
        int imageWidth = c.getInt(c.getColumnIndex(Chat.C_IMAGE_WIDTH));
        String replyTo = c.getString(c.getColumnIndex("c_reply_to"));
        String stampUid = c.getString(c.getColumnIndex(Chat.C_STAMP_UID));
        int onStore = c.getInt(c.getColumnIndex(Chat.C_ON_STORE));
        UserValue user = Function.getUser(db, c.getString(c.getColumnIndex("c_user_uid")));
        List<ChatReferValue> refers = getChatRefers(db, id);
        String assetsString = c.getString(c.getColumnIndex(Chat.C_ASSETS));
        int likesCount = c.getInt(c.getColumnIndex(Chat.C_LIKES_COUNT));
        int boosCount = c.getInt(c.getColumnIndex(Chat.C_BOOS_COUNT));
        boolean liked = c.getInt(c.getColumnIndex(Chat.C_LIKED)) == 1;
        boolean booed = c.getInt(c.getColumnIndex(Chat.C_BOOED)) == 1;
        ArrayList<ChatValue> chatReplies = new ArrayList();
        if (replies != null) {
            for (int i = 0; i < replies.size(); i++) {
                if (((ChatValue) replies.get(i)).getReplyTo().equals(id)) {
                    chatReplies.add(replies.remove(i));
                }
            }
        }
        ArrayList<AssetValue> assets = new ArrayList();
        if (assetsString != null) {
            for (String asset : assetsString.split(",")) {
                assets.add(new AssetValue(asset));
            }
        }
        return new ChatValue(id, type, message, createdDate, image, replyTo, user, imageType, imageWidth, imageHeight, stampUid, onStore, refers, new Replies((List) chatReplies, 0), assets, likesCount, boosCount, liked, booed);
    }

    public static final void deleteChat(SQLiteDatabase db, String chatId) {
        try {
            db.delete(Chat.TABLE, "c_id = ? ", new String[]{chatId});
            deleteChatRefs(db, chatId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    static final void deleteChatInGroup(SQLiteDatabase db, String groupUid) {
        try {
            List<ChatValue> chats = getChats(db, groupUid);
            if (chats != null) {
                for (ChatValue chat : chats) {
                    deleteChatRefs(db, chat.getId());
                }
            }
            db.delete(Chat.TABLE, "c_group_uid = ? ", new String[]{groupUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    static final List<ChatReferValue> getChatRefers(SQLiteDatabase db, String chatId) {
        Throwable th;
        Cursor c = null;
        List<ChatReferValue> refers = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(ChatRefer.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{ChatRefer.C_ACTION_TITLE, ChatRefer.C_CHAT_ID, "c_image", ChatRefer.C_LINK, "c_title", "c_type"}, "c_chat_id= ?", new String[]{chatId}, null, null, ChatRefer.C_REF_ID);
            if (c.moveToFirst()) {
                List<ChatReferValue> refers2 = new ArrayList();
                do {
                    try {
                        refers2.add(toChatReferValue(db, c));
                    } catch (Throwable th2) {
                        th = th2;
                        refers = refers2;
                    }
                } while (c.moveToNext());
                refers = refers2;
            }
            if (c != null) {
                c.close();
            }
            return refers;
        } catch (Throwable th3) {
            th = th3;
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    private static final ChatReferValue toChatReferValue(SQLiteDatabase db, Cursor c) {
        return new ChatReferValue(c.getString(c.getColumnIndex("c_type")), c.getString(c.getColumnIndex("c_title")), c.getString(c.getColumnIndex("c_image")), c.getString(c.getColumnIndex(ChatRefer.C_ACTION_TITLE)), c.getString(c.getColumnIndex(ChatRefer.C_LINK)));
    }

    static final void setChatRefers(SQLiteDatabase db, ChatValue chat) {
        SQLException e;
        List<ChatReferValue> refers = chat.getRefers();
        if (refers != null) {
            try {
                int i = 0;
                for (ChatReferValue refer : refers) {
                    int i2;
                    try {
                        ContentValues values = new ContentValues();
                        values.put(ChatRefer.C_ACTION_TITLE, refer.actionTitle);
                        values.put(ChatRefer.C_CHAT_ID, chat.getId());
                        values.put("c_image", refer.image);
                        values.put(ChatRefer.C_LINK, refer.link);
                        i2 = i + 1;
                        values.put(ChatRefer.C_REF_ID, Integer.valueOf(i));
                        values.put("c_title", refer.title);
                        values.put("c_type", refer.type);
                        if (db.replaceOrThrow(ChatRefer.TABLE, null, values) == -1) {
                            throw new Fatal();
                        }
                        i = i2;
                    } catch (SQLException e2) {
                        e = e2;
                        i2 = i;
                    }
                }
                return;
            } catch (SQLException e3) {
                e = e3;
            }
        } else {
            return;
        }
        e.printStackTrace();
        throw new Fatal();
    }

    static final void deleteChatRefs(SQLiteDatabase db, String chatId) {
        try {
            db.delete(ChatRefer.TABLE, "c_chat_id = ?", new String[]{chatId});
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Fatal(e);
        }
    }

    public static final void setFileCache(SQLiteDatabase db, FileCacheValue fileCache) {
        try {
            ContentValues values = new ContentValues();
            values.put(FileCache.C_PATH, fileCache.getPath());
            values.put("c_type", fileCache.getType());
            values.put(FileCache.C_FILE_SIZE, Integer.valueOf(fileCache.getFileSize()));
            values.put(FileCache.C_CREATED_AT, Long.valueOf(fileCache.getCreatedAt()));
            if (db.replaceOrThrow(FileCache.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setFileCache(SQLiteDatabase db, String path, String type, int fileSize) {
        try {
            ContentValues values = new ContentValues();
            values.put(FileCache.C_PATH, path);
            values.put("c_type", type);
            values.put(FileCache.C_FILE_SIZE, Integer.valueOf(fileSize));
            values.put(FileCache.C_CREATED_AT, Long.valueOf(System.currentTimeMillis()));
            if (db.replaceOrThrow(FileCache.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final FileCacheValue getFileCache(SQLiteDatabase db, String path) {
        Cursor c = null;
        FileCacheValue fileCache = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{FileCache.C_PATH, "c_type", FileCache.C_FILE_SIZE, FileCache.C_CREATED_AT}, "c_path = ?", new String[]{path}, null, null, null);
            if (c.moveToFirst()) {
                fileCache = toFileCache(db, c);
            }
            if (c != null) {
                c.close();
            }
            return fileCache;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final List<FileCacheValue> getFileCaches(SQLiteDatabase db, int limit) {
        Cursor c = null;
        List<FileCacheValue> fileCaches = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{FileCache.C_PATH, "c_type", FileCache.C_FILE_SIZE, FileCache.C_CREATED_AT}, null, null, null, null, "c_created_at ASC ", String.valueOf(limit));
            if (c.moveToFirst()) {
                do {
                    fileCaches.add(toFileCache(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return fileCaches;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final List<FileCacheValue> getFileCaches(SQLiteDatabase db, String type, int limit) {
        Cursor c = null;
        List<FileCacheValue> fileCaches = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{FileCache.C_PATH, "c_type", FileCache.C_FILE_SIZE, FileCache.C_CREATED_AT}, "c_type = ?", new String[]{type}, null, null, "c_created_at ASC ", String.valueOf(limit));
            if (c.moveToFirst()) {
                do {
                    fileCaches.add(toFileCache(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return fileCaches;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final List<FileCacheValue> getFileCaches(SQLiteDatabase db, int limit, long fromDate) {
        Cursor c = null;
        List<FileCacheValue> fileCaches = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{FileCache.C_PATH, "c_type", FileCache.C_FILE_SIZE, FileCache.C_CREATED_AT}, "c_created_at >= ? ", new String[]{String.valueOf(fromDate)}, null, null, "c_created_at ASC ", String.valueOf(limit));
            if (c.moveToFirst()) {
                do {
                    fileCaches.add(toFileCache(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return fileCaches;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final List<FileCacheValue> getFileCaches(SQLiteDatabase db, String type, int limit, long fromDate) {
        Cursor c = null;
        List<FileCacheValue> fileCaches = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{FileCache.C_PATH, "c_type", FileCache.C_FILE_SIZE, FileCache.C_CREATED_AT}, "c_type = ? AND c_created_at >= ?", new String[]{type, String.valueOf(fromDate)}, null, null, "c_created_at ASC ", String.valueOf(limit));
            if (c.moveToFirst()) {
                do {
                    fileCaches.add(toFileCache(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return fileCaches;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    private static final FileCacheValue toFileCache(SQLiteDatabase db, Cursor c) {
        return new FileCacheValue(c.getString(c.getColumnIndex(FileCache.C_PATH)), c.getString(c.getColumnIndex("c_type")), c.getInt(c.getColumnIndex(FileCache.C_FILE_SIZE)), c.getLong(c.getColumnIndex(FileCache.C_CREATED_AT)));
    }

    public static final int getFileCacheEntry(SQLiteDatabase db) {
        Cursor c = null;
        int entry = 0;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            c = queryBuilder.query(db, new String[]{"COUNT(*)"}, null, null, null, null, null);
            if (c.moveToFirst()) {
                entry = c.getInt(0);
            }
            if (c != null) {
                c.close();
            }
            return entry;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final int getFileCacheEntry(SQLiteDatabase db, String type) {
        Cursor c = null;
        int entry = 0;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"COUNT(*)"}, "c_type = ?", new String[]{type}, null, null, null);
            if (c.moveToFirst()) {
                entry = c.getInt(0);
            }
            if (c != null) {
                c.close();
            }
            return entry;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final long getFileCacheFileSizeSum(SQLiteDatabase db) {
        Cursor c = null;
        long fileSizeSum = 0;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            String[] projectionIn = new String[1];
            projectionIn[0] = String.format("SUM(%s)", new Object[]{FileCache.C_FILE_SIZE});
            c = queryBuilder.query(db, projectionIn, null, null, null, null, null);
            if (c.moveToFirst()) {
                fileSizeSum = c.getLong(0);
            }
            if (c != null) {
                c.close();
            }
            return fileSizeSum;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final long getFileCacheFileSizeSum(SQLiteDatabase db, String type) {
        Cursor c = null;
        long fileSizeSum = 0;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(FileCache.TABLE);
            String[] projectionIn = new String[1];
            projectionIn[0] = String.format("SUM(%s)", new Object[]{FileCache.C_FILE_SIZE});
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, projectionIn, "c_type = ?", new String[]{type}, null, null, null);
            if (c.moveToFirst()) {
                fileSizeSum = c.getLong(0);
            }
            if (c != null) {
                c.close();
            }
            return fileSizeSum;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    static final void deleteFileCache(SQLiteDatabase db, String path) {
        try {
            db.delete(FileCache.TABLE, "c_path = ? ", new String[]{path});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setStamp(SQLiteDatabase db, StampValue stamp, int categoryOrder) {
        int order = 0;
        try {
            for (Item item : stamp.getItems()) {
                order++;
                ContentValues values = new ContentValues();
                values.put(StampCategory.C_CATEGORY, stamp.getCategory());
                values.put("c_name", stamp.getName());
                values.put("c_icon", stamp.getIcon());
                values.put("c_uid", item.getUid());
                values.put("c_order", Integer.valueOf(categoryOrder));
                if (db.replaceOrThrow(StampCategory.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
                values = new ContentValues();
                values.put("c_uid", item.getUid());
                values.put("c_image", item.getImage());
                values.put(StampItem.C_THUMB, item.getThumb());
                values.put(StampItem.C_WIDTH, Integer.valueOf(item.getWidth()));
                values.put(StampItem.C_HEIGHT, Integer.valueOf(item.getHeight()));
                values.put(StampItem.C_STATE, Integer.valueOf(item.getState()));
                values.put("c_order", Integer.valueOf(order));
                if (db.replaceOrThrow(StampItem.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final List<StampValue> getStamps(SQLiteDatabase db) {
        Cursor c = null;
        List<StampValue> stamps = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("stamp_category_table LEFT OUTER JOIN stamp_item_table ON stamp_category_table.c_uid = stamp_item_table.c_uid");
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"stamp_category_table.c_category AS category", "stamp_category_table.c_name AS name", "stamp_category_table.c_icon AS icon", "stamp_item_table.c_uid AS uid", "stamp_item_table.c_image AS image", "stamp_item_table.c_thumb AS thumb", "stamp_item_table.c_width AS width", "stamp_item_table.c_height AS height", "stamp_item_table.c_state AS state"}, null, null, null, null, "stamp_category_table.c_order,stamp_category_table.c_category,stamp_item_table.c_order");
            if (c.moveToFirst()) {
                String lastCategory = null;
                Object lastStamp = null;
                do {
                    String category = c.getString(c.getColumnIndex("category"));
                    if (!category.equals(lastCategory)) {
                        if (lastStamp != null) {
                            stamps.add(lastStamp);
                        }
                        lastStamp = new StampValue(category, c.getString(c.getColumnIndex("icon")), c.getString(c.getColumnIndex("name")), new ArrayList());
                    }
                    lastCategory = category;
                    lastStamp.getItems().add(new Item(c.getString(c.getColumnIndex("uid")), c.getString(c.getColumnIndex("image")), c.getString(c.getColumnIndex("thumb")), c.getInt(c.getColumnIndex("width")), c.getInt(c.getColumnIndex("height")), c.getInt(c.getColumnIndex("state"))));
                } while (c.moveToNext());
                if (lastStamp != null) {
                    stamps.add(lastStamp);
                }
            }
            if (c != null) {
                c.close();
            }
            return stamps;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    static final void deleteAllStamp(SQLiteDatabase db) {
        try {
            db.delete(StampCategory.TABLE, null, null);
            db.delete(StampItem.TABLE, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    static final void deleteStamp(SQLiteDatabase db, String uid) {
        try {
            db.delete(StampCategory.TABLE, "c_uid = ? ", new String[]{uid});
            db.delete(StampItem.TABLE, "c_uid = ? ", new String[]{uid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    static final void deleteStamps(SQLiteDatabase db, String category) {
        Cursor c = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(StampCategory.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid"}, "c_category = ?", new String[]{category}, null, null, null);
            if (c.moveToFirst()) {
                do {
                    String uid = c.getString(c.getColumnIndex("c_uid"));
                    db.delete(StampItem.TABLE, "c_uid = ? ", new String[]{uid});
                } while (c.moveToNext());
            }
            db.delete(StampCategory.TABLE, "c_category = ? ", new String[]{category});
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final ArrayList<Item> getStampHistory(SQLiteDatabase db) {
        Cursor c = null;
        ArrayList<Item> stamps = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("stamp_history_table LEFT JOIN stamp_item_table ON stamp_history_table.c_uid = stamp_item_table.c_uid");
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"stamp_history_table.c_uid", "stamp_history_table.c_last_used_at", "stamp_item_table.c_image", "stamp_item_table.c_thumb", "stamp_item_table.c_width", "stamp_item_table.c_height", "stamp_item_table.c_state"}, null, null, null, null, "c_last_used_at DESC ");
            if (c.moveToFirst()) {
                do {
                    stamps.add(toStamp(c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return stamps;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    private static final Item toStamp(Cursor c) {
        return new Item(c.getString(c.getColumnIndex("c_uid")), c.getString(c.getColumnIndex("c_image")), c.getString(c.getColumnIndex(StampItem.C_THUMB)), c.getInt(c.getColumnIndex(StampItem.C_WIDTH)), c.getInt(c.getColumnIndex(StampItem.C_HEIGHT)), c.getInt(c.getColumnIndex(StampItem.C_STATE)));
    }

    public static final void addStampHistory(SQLiteDatabase db, String stampUid) {
        try {
            ContentValues values = new ContentValues();
            values.put("c_uid", stampUid);
            values.put(StampHistory.C_LAST_USED_AT, Long.valueOf(System.currentTimeMillis()));
            if (db.replaceOrThrow(StampHistory.TABLE, null, values) == -1) {
                throw new Fatal();
            }
            deleteOldHistory(db);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    private static final void deleteOldHistory(SQLiteDatabase db) {
        db.execSQL("DELETE FROM stamp_history_table WHERE c_uid in (SELECT c_uid FROM stamp_history_table ORDER BY c_last_used_at DESC LIMIT 32,-1 )");
    }

    public static final long setDownload(SQLiteDatabase db, int total) {
        try {
            ContentValues values = new ContentValues();
            values.put("c_total", Integer.valueOf(total));
            long l = db.replaceOrThrow(Upload.TABLE, null, values);
            if (l != -1) {
                return l;
            }
            throw new Fatal();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void deleteDownload(SQLiteDatabase db, int uid) {
        try {
            db.delete(Download.TABLE, "c_uid = ? ", new String[]{Integer.toString(uid)});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<DownloadValue> getDownload(SQLiteDatabase db) {
        Cursor c = null;
        ArrayList<DownloadValue> items = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Download.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"download_table.c_uid", "download_table.c_total"}, null, null, null, null, "c_uid DESC ");
            if (c.moveToFirst()) {
                do {
                    items.add(new DownloadValue(c.getInt(c.getColumnIndex("c_uid")), c.getInt(c.getColumnIndex("c_total"))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return items;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final DownloadValue getDownload(SQLiteDatabase db, int downloadUid) {
        Cursor c = null;
        DownloadValue download = null;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("download_table WHERE download_table.c_uid = " + downloadUid);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"download_table.c_uid", "download_table.c_total"}, null, null, null, null, "c_uid DESC ");
            if (c.moveToFirst()) {
                download = new DownloadValue(c.getInt(c.getColumnIndex("c_uid")), c.getInt(c.getColumnIndex("c_total")));
            }
            if (c != null) {
                c.close();
            }
            return download;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void setDownloadItem(SQLiteDatabase db, int downloadUid, String assetUid, String type, String url) {
        try {
            ContentValues values = new ContentValues();
            values.put(DownloadItem.C_ASSET_UID, assetUid);
            values.put(DownloadItem.C_DOWNLOAD_UID, Integer.valueOf(downloadUid));
            values.put("c_type", type);
            values.put("c_url", url);
            values.put("c_complete", "0");
            if (db.replaceOrThrow(DownloadItem.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void deleteDownloadItem(SQLiteDatabase db, int downloadUid, String assetUid) {
        try {
            db.delete(DownloadItem.TABLE, "c_asset_uid = ?  AND c_download_uid = ? ", new String[]{assetUid, Integer.toString(downloadUid)});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<DownloadValue.Item> getDownloadItem(SQLiteDatabase db, int downloadUid) {
        Cursor c = null;
        ArrayList<DownloadValue.Item> items = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("download_item_table WHERE download_item_table.c_download_uid = " + downloadUid);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"download_item_table.c_asset_uid", "download_item_table.c_download_uid", "download_item_table.c_type", "download_item_table.c_url", "download_item_table.c_complete"}, null, null, null, null, "c_download_uid DESC ");
            if (c.moveToFirst()) {
                do {
                    items.add(new DownloadValue.Item(c.getString(c.getColumnIndex(DownloadItem.C_ASSET_UID)), c.getInt(c.getColumnIndex(DownloadItem.C_DOWNLOAD_UID)), c.getString(c.getColumnIndex("c_type")), c.getString(c.getColumnIndex("c_url")), "1".equals(c.getString(c.getColumnIndex("c_complete")))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return items;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final long setUpload(SQLiteDatabase db, String groupUid, String replyTo, int total, String message, int shout) {
        try {
            ContentValues values = new ContentValues();
            values.put("c_group_uid", groupUid);
            values.put("c_reply_to", replyTo);
            values.put("c_total", Integer.valueOf(total));
            values.put("c_message", message);
            values.put(Upload.C_SHOUT, Integer.valueOf(shout));
            long l = db.replaceOrThrow(Upload.TABLE, null, values);
            if (l != -1) {
                return l;
            }
            throw new Fatal();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void deleteUpload(SQLiteDatabase db, int uid) {
        try {
            db.delete(Upload.TABLE, "c_uid = ? ", new String[]{Integer.toString(uid)});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<UploadValue> getUpload(SQLiteDatabase db) {
        return uploadQuery(db, "upload_table LEFT JOIN group_table ON upload_table.c_group_uid = group_table.c_uid");
    }

    public static final ArrayList<UploadValue> getUpload(SQLiteDatabase db, int uploadUid) {
        return uploadQuery(db, "upload_table LEFT JOIN group_table ON upload_table.c_group_uid = group_table.c_uid WHERE upload_table.c_uid = " + uploadUid);
    }

    protected static final ArrayList<UploadValue> uploadQuery(SQLiteDatabase db, String query) {
        Cursor c = null;
        ArrayList<UploadValue> uploadList = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(query);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"upload_table.c_uid", "upload_table.c_group_uid", "group_table.c_name", "upload_table.c_reply_to", "upload_table.c_total", "upload_table.c_message", "upload_table.c_shout"}, null, null, null, null, "upload_table.c_uid DESC ");
            if (c.moveToFirst()) {
                do {
                    uploadList.add(new UploadValue(c.getInt(c.getColumnIndex("c_uid")), c.getString(c.getColumnIndex("c_group_uid")), c.getString(c.getColumnIndex("c_name")), c.getString(c.getColumnIndex("c_reply_to")), c.getInt(c.getColumnIndex("c_total")), c.getString(c.getColumnIndex("c_message")), c.getInt(c.getColumnIndex(Upload.C_SHOUT))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return uploadList;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void setUploadItem(SQLiteDatabase db, UploadValue.Item item) {
        try {
            ContentValues values = new ContentValues();
            values.put("c_uid", item.uid);
            values.put("c_upload_uid", Integer.valueOf(item.uploadUid));
            values.put("c_type", item.type);
            values.put("c_url", item.url);
            values.put("c_complete", item.isComplete ? "1" : "0");
            if (db.replaceOrThrow(UploadItem.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final int getUploadItemCount(SQLiteDatabase db, int uploadUid) {
        String query = "upload_item_table WHERE upload_item_table.c_upload_uid = " + uploadUid;
        Cursor c = null;
        int entry = 0;
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(query);
            c = queryBuilder.query(db, new String[]{"COUNT(*)"}, null, null, null, null, null);
            if (c.moveToFirst()) {
                entry = c.getInt(0);
            }
            if (c != null) {
                c.close();
            }
            return entry;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final ArrayList<UploadValue.Item> getUploadItem(SQLiteDatabase db, int uploadUid) {
        Cursor c = null;
        ArrayList<UploadValue.Item> items = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("upload_item_table WHERE upload_item_table.c_upload_uid = " + uploadUid);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"upload_item_table.c_uid", "upload_item_table.c_upload_uid", "upload_item_table.c_type", "upload_item_table.c_url", "upload_item_table.c_complete"}, null, null, null, null, "ROWID");
            if (c.moveToFirst()) {
                do {
                    items.add(new UploadValue.Item(c.getString(c.getColumnIndex("c_uid")), c.getInt(c.getColumnIndex("c_upload_uid")), c.getString(c.getColumnIndex("c_type")), c.getString(c.getColumnIndex("c_url")), "1".equals(c.getString(c.getColumnIndex("c_complete")))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return items;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void deleteUploadItem(SQLiteDatabase db, String uid) {
        try {
            db.delete(UploadItem.TABLE, "c_uid = ? ", new String[]{uid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setAssets(SQLiteDatabase db, int uploadUid, String id, String type, String url) {
        try {
            ContentValues values = new ContentValues();
            values.put("c_upload_uid", Integer.valueOf(uploadUid));
            values.put("c_id", id);
            values.put("c_type", type);
            values.put("c_url", url);
            if (db.replaceOrThrow(Asset.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<AssetValue> getAsset(SQLiteDatabase db, int uploadUid) {
        Cursor c = null;
        ArrayList<AssetValue> assets = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("asset_table WHERE asset_table.c_upload_uid = " + uploadUid);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"asset_table.c_id", "asset_table.c_type", "asset_table.c_url"}, null, null, null, null, "ROWID");
            if (c.moveToFirst()) {
                do {
                    assets.add(new AssetValue(c.getString(c.getColumnIndex("c_id")), c.getString(c.getColumnIndex("c_type")), c.getString(c.getColumnIndex("c_url"))));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return assets;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void deleteAsset(SQLiteDatabase db, String uid) {
        try {
            db.delete(Asset.TABLE, "c_id = ? ", new String[]{uid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setAdWaitingApp(SQLiteDatabase db, String adId, String packageName, String clientId, boolean countConversion) {
        try {
            ContentValues values = new ContentValues();
            values.put(AdWaitingApp.C_AD_ID, adId);
            values.put("c_client_id", clientId);
            values.put(AdWaitingApp.C_PACKAGE, packageName);
            values.put(AdWaitingApp.C_DATE, Long.valueOf(System.currentTimeMillis()));
            values.put(AdWaitingApp.C_COUNT_CONVERSION, Integer.valueOf(countConversion ? 1 : 0));
            if (db.replaceOrThrow(AdWaitingApp.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final ArrayList<AdWaitingAppValue> getAdWaitingApp(SQLiteDatabase db, String packageName) {
        Cursor c = null;
        ArrayList<AdWaitingAppValue> ads = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(AdWaitingApp.TABLE);
            String[] projectionIn = new String[]{AdWaitingApp.C_AD_ID, AdWaitingApp.C_PACKAGE, "c_client_id", AdWaitingApp.C_DATE, AdWaitingApp.C_COUNT_CONVERSION};
            if (packageName != null) {
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, projectionIn, "c_package = ? ", new String[]{packageName}, null, null, null);
            } else {
                c = queryBuilder.query(db, projectionIn, null, null, null, null, null);
            }
            if (c.moveToFirst()) {
                do {
                    ads.add(toAdWaitingAppValue(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return ads;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final void deleteAdWaitingApp(SQLiteDatabase db, String adId) {
        try {
            db.delete(AdWaitingApp.TABLE, "c_ad_id = ? ", new String[]{adId});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    private static final AdWaitingAppValue toAdWaitingAppValue(SQLiteDatabase db, Cursor c) {
        return new AdWaitingAppValue(c.getString(c.getColumnIndex(AdWaitingApp.C_AD_ID)), c.getString(c.getColumnIndex(AdWaitingApp.C_PACKAGE)), c.getString(c.getColumnIndex("c_client_id")), c.getLong(c.getColumnIndex(AdWaitingApp.C_DATE)), c.getInt(c.getColumnIndex(AdWaitingApp.C_COUNT_CONVERSION)) != 0);
    }

    public static final void setUserContact(SQLiteDatabase db, UserContactValue contact, String userUid) {
        try {
            ContentValues values = new ContentValues();
            values.put("c_uid", contact.getUid());
            values.put("c_name", contact.getName());
            values.put("c_description", contact.getDescription());
            values.put("c_icon", contact.getIcon());
            values.put("c_contacts_count", Integer.valueOf(contact.getContactsCount()));
            values.put(UserContact.C_MY_GROUPS_COUNT, Integer.valueOf(contact.getMyGroupsCount()));
            values.put("c_contacted_date", Long.valueOf(contact.getContactedDate()));
            values.put("c_user_uid", userUid);
            if (db.replaceOrThrow(UserContact.TABLE, null, values) == -1) {
                throw new Fatal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final void setUserContacts(SQLiteDatabase db, List<UserContactValue> contacts, String userUid) {
        try {
            db.delete(UserContact.TABLE, null, null);
            for (UserContactValue contact : contacts) {
                ContentValues values = new ContentValues();
                values.put("c_uid", contact.getUid());
                values.put("c_name", contact.getName());
                values.put("c_description", contact.getDescription());
                values.put("c_icon", contact.getIcon());
                values.put("c_contacts_count", Integer.valueOf(contact.getContactsCount()));
                values.put(UserContact.C_MY_GROUPS_COUNT, Integer.valueOf(contact.getMyGroupsCount()));
                values.put("c_contacted_date", Long.valueOf(contact.getContactedDate()));
                values.put("c_user_uid", userUid);
                if (db.replaceOrThrow(UserContact.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }

    public static final List<UserContactValue> getUserContacts(SQLiteDatabase db, String userUid, UserContact.Order order) {
        Cursor c = null;
        List<UserContactValue> contacts = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(UserContact.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", "c_name", "c_description", "c_icon", "c_contacts_count", UserContact.C_MY_GROUPS_COUNT, "c_contacted_date"}, "c_user_uid = ?", new String[]{userUid}, null, null, order.getValue());
            if (c.moveToFirst()) {
                do {
                    contacts.add(toUserContact(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return contacts;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static final List<UserContactValue> getUserContacts(SQLiteDatabase db, String userUid, UserContact.Order order, String query) {
        Cursor c = null;
        List<UserContactValue> contacts = new ArrayList();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(UserContact.TABLE);
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", "c_name", "c_description", "c_icon", "c_contacts_count", UserContact.C_MY_GROUPS_COUNT, "c_contacted_date"}, "c_user_uid = ? AND c_name LIKE '%' || ? || '%' ", new String[]{userUid, query}, null, null, order.getValue());
            if (c.moveToFirst()) {
                do {
                    contacts.add(toUserContact(db, c));
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
            return contacts;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    private static final UserContactValue toUserContact(SQLiteDatabase db, Cursor c) {
        return new UserContactValue(c.getString(c.getColumnIndex("c_uid")), c.getString(c.getColumnIndex("c_name")), c.getString(c.getColumnIndex("c_description")), c.getString(c.getColumnIndex("c_icon")), c.getInt(c.getColumnIndex("c_contacts_count")), c.getInt(c.getColumnIndex(UserContact.C_MY_GROUPS_COUNT)), (long) c.getInt(c.getColumnIndex("c_contacted_date")));
    }

    static final void deleteUserContact(SQLiteDatabase db, String myUid, String userContactUid) {
        try {
            db.delete(UserContact.TABLE, "c_user_uid = ?  AND c_uid = ?", new String[]{myUid, userContactUid});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Fatal();
        }
    }
}
