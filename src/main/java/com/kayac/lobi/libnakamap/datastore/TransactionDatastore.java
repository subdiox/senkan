package com.kayac.lobi.libnakamap.datastore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.datastore.CommonDDL.App;
import com.kayac.lobi.libnakamap.datastore.CommonDDL.User;
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
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.StampCategory;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.StampHistory;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.StampItem;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Upload;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UploadItem;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UserContact;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Widget;
import com.kayac.lobi.libnakamap.value.AdWaitingAppValue;
import com.kayac.lobi.libnakamap.value.AssetValue;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.DownloadValue;
import com.kayac.lobi.libnakamap.value.FileCacheValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.StampValue.Item;
import com.kayac.lobi.libnakamap.value.UploadValue;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.WidgetMetaDataValue;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;

public class TransactionDatastore implements CommonDatastoreImpl {
    private static final Class<TransactionDataHelper> MUTEX = TransactionDataHelper.class;
    private static Context sContext = null;
    private static TransactionDataHelper sHelper = null;

    private static final class TransactionDataHelper extends SQLiteOpenHelper {
        private static final String FILE = "nakamap_transaction.sqlite";
        private static final int VERSION = 22;

        public static final TransactionDataHelper newInstance(Context context) {
            return newInstance(context, null);
        }

        public static final TransactionDataHelper newInstance(Context context, String rootDir) {
            if (rootDir == null || rootDir.length() == 0) {
                return new TransactionDataHelper(context);
            }
            return new TransactionDataHelper(context, new File(rootDir, FILE).getPath());
        }

        public TransactionDataHelper(Context context) {
            super(context, FILE, null, 22);
        }

        public TransactionDataHelper(Context context, String filePath) {
            super(context, filePath, null, 22);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE key_value_table (c_key TEXT  PRIMARY KEY  ,c_value BLOB  NOT NULL );");
            db.execSQL("CREATE TABLE key_key_value_table (c_key_1 TEXT ,c_key_2 TEXT ,c_value BLOB  NOT NULL , PRIMARY KEY  (c_key_1,c_key_2));");
            db.execSQL(User.CREATE_SQL);
            db.execSQL(App.CREATE_SQL);
            db.execSQL(Widget.CREATE_SQL);
            db.execSQL(GroupDetail.CREATE_SQL);
            db.execSQL(GroupMember.CREATE_SQL);
            db.execSQL(Group.CREATE_SQL);
            db.execSQL(Chat.CREATE_SQL);
            db.execSQL(Category.CREATE_SQL);
            db.execSQL(FileCache.CREATE_SQL);
            db.execSQL(StampCategory.CREATE_SQL);
            db.execSQL(StampItem.CREATE_SQL);
            db.execSQL(UserContact.CREATE_SQL);
            db.execSQL(UserContact.CREATE_INDEX_SQL);
            db.execSQL(GroupPermission.CREATE_SQL);
            db.execSQL(ChatRefer.CREATE_SQL);
            db.execSQL(StampHistory.CREATE_SQL);
            db.execSQL(Upload.CREATE_SQL);
            db.execSQL(UploadItem.CREATE_SQL);
            db.execSQL(Download.CREATE_SQL);
            db.execSQL(DownloadItem.CREATE_SQL);
            db.execSQL(Asset.CREATE_SQL);
            db.execSQL(AdWaitingApp.CREATE_SQL);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onUpgrade(android.database.sqlite.SQLiteDatabase r6, int r7, int r8) {
            /*
            r5 = this;
            r2 = "[libnakamap]";
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "TransactionDataHelper.onUpgrade: ";
            r3 = r3.append(r4);
            r3 = r3.append(r7);
            r4 = " -> ";
            r3 = r3.append(r4);
            r3 = r3.append(r8);
            r3 = r3.toString();
            com.kayac.lobi.libnakamap.utils.Log.v(r2, r3);
            if (r7 != r8) goto L_0x0025;
        L_0x0024:
            return;
        L_0x0025:
            switch(r7) {
                case 1: goto L_0x0029;
                case 2: goto L_0x0074;
                case 3: goto L_0x0079;
                case 4: goto L_0x00a1;
                case 5: goto L_0x00ec;
                case 6: goto L_0x00f6;
                case 7: goto L_0x00fb;
                case 8: goto L_0x0100;
                case 9: goto L_0x011e;
                case 10: goto L_0x0132;
                case 11: goto L_0x0137;
                case 12: goto L_0x0146;
                case 13: goto L_0x015a;
                case 14: goto L_0x0178;
                case 15: goto L_0x017d;
                case 16: goto L_0x0187;
                case 17: goto L_0x01af;
                case 18: goto L_0x01b4;
                case 19: goto L_0x01be;
                case 20: goto L_0x01d2;
                case 21: goto L_0x01dc;
                default: goto L_0x0028;
            };
        L_0x0028:
            goto L_0x0024;
        L_0x0029:
            r2 = "DROP TABLE key_value_table";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE group_info_table";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE group_member_table";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE chat_table";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE user_table";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE key_value_table (c_key text PRIMARY KEY, c_value BLOB NOT NULL);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE key_key_value_table (c_key_1 text, c_key_2 text, c_value BLOB NOT NULL, PRIMARY KEY(c_key_1, c_key_2))";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE user_table (c_uid text PRIMARY KEY, c_default INTEGER, c_token TEXT, c_name TEXT, c_description TEXT, c_icon TEXT, c_contacts_count INTEGER, c_is_nan_location INTEGER, c_lng REAL, c_lat REAL, c_located_date INTEGER, c_app_name TEXT, c_update_at INTEGER)";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE app_table (c_name TEXT PRIMARY KEY, c_icon TEXT)";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_detail_table ( c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_total_users INTEGER, c_online_users INTEGER, c_is_online INTEGER, c_type TEXT, c_last_chat_at INTEGER, c_push_enabled INTEGER, c_user_uid TEXT NOT NULL, PRIMARY KEY (c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE chat_table (c_group_uid TEXT, c_user_uid TEXT, c_order INTEGER, PRIMARY KEY (c_group_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_table (c_uid TEXT PRIMARY KEY, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_push_enabled INTEGER, c_owner TEXT, c_is_online INTEGER, c_members_count INTEGER);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE chat_table  (c_id TEXT PRIMARY KEY, c_group_uid TEXT, c_type TEXT, c_message TEXT, c_created_date INTEGER, c_image TEXT, c_reply_to TEXT, c_user_uid TEXT);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE category_table (c_id TEXT, c_title TEXT, c_description TEXT, c_user_uid TEXT, c_group_uid TEXT, PRIMARY KEY(c_id, c_user_uid, c_group_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE widget_table ADD COLUMN c_token TEXT NOT NULL DEFAULT '';";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0074:
            r2 = "CREATE TABLE file_cache_table (c_path TEXT PRIMARY KEY, c_type TEXT, c_file_size INTEGER, c_created_at INTEGER);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0079:
            r2 = "CREATE TABLE stamp_category_table (c_category TEXT, c_name TEXT, c_icon TEXT, c_uid TEXT, PRIMARY KEY(c_category, c_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE stamp_item_table (c_uid TEXT PRIMARY KEY, c_image TEXT, c_thumb TEXT, c_width INTEGER, c_height INTEGER, c_state INTEGER, c_order INTEGER);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE user_contact_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_icon TEXT, c_contacts_count INTEGER, c_my_groups_count INTEGER, c_contacted_date INTEGER, c_user_uid TEXT, PRIMARY KEY (c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_image_type TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_image_width INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_image_height INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE user_table ADD COLUMN c_cover TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE user_table ADD COLUMN c_contacted_date INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x00a1:
            r2 = "DROP TABLE group_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE group_detail_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE category_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE widget_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE app_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_permission_table (c_user_uid TEXT ,c_group_uid TEXT ,c_permission TEXT ,PRIMARY KEY (c_user_uid ,c_group_uid ,c_permission));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_push_enabled INTEGER, c_owner TEXT, c_is_online INTEGER, c_is_public INTEGER, c_members_count INTEGER, c_type TEXT, c_user_uid TEXT, PRIMARY KEY (c_uid , c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_detail_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_total_users INTEGER, c_online_users INTEGER, c_is_online INTEGER, c_is_public INTEGER, c_type TEXT, c_last_chat_at INTEGER, c_push_enabled INTEGER, c_user_uid TEXT NOT NULL, PRIMARY KEY(c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE category_table (c_type TEXT, c_title TEXT, c_user_uid TEXT, c_group_uid TEXT, PRIMARY KEY (c_type ,c_user_uid ,c_group_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE widget_table (c_app_widget_id INTEGER, c_token TEXT NOT NULL DEFAULT '', c_user_uid TEXT NOT NULL, c_group_uid TEXT, c_updated_at INTEGER NOT NULL, PRIMARY KEY (c_app_widget_id));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE app_table (c_name TEXT, c_icon TEXT, c_appstore_uri TEXT, c_playstore_uri TEXT, c_uid TEXT, PRIMARY KEY(c_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE user_table_tmp (c_uid TEXT  PRIMARY KEY  ,c_default INTEGER ,c_token TEXT ,c_name TEXT ,c_description TEXT ,c_icon TEXT ,c_cover TEXT ,c_contacts_count INTEGER ,c_contacted_date INTEGER ,c_is_nan_location INTEGER ,c_lng REAL ,c_lat REAL ,c_located_date INTEGER ,c_update_at INTEGER ,c_app_uid TEXT );";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "INSERT INTO user_table_tmp SELECT c_uid, c_default, c_token, c_name, c_description, c_icon TEXT, c_cover, c_contacts_count, c_contacted_date, c_is_nan_location, c_lng, c_lat, c_located_date, c_update_at, '' FROM user_table";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE user_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE user_table_tmp RENAME TO user_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x00ec:
            r2 = "CREATE TABLE chat_refer_table (c_chat_id T_TEXT, c_ref_id T_TEXT, c_type T_TEXT, c_title T_TEXT, c_image T_TEXT, c_action_title T_TEXT, c_link T_TEXT, PRIMARY KEY(c_chat_id, c_ref_id));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE stamp_category_table ADD COLUMN c_order INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x00f6:
            r2 = "CREATE TABLE stamp_history_table (c_uid TEXT, c_last_used_at INTEGER, PRIMARY KEY(c_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x00fb:
            r2 = "ALTER TABLE group_table ADD COLUMN c_wallpaper TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0100:
            r2 = "DROP TABLE group_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE group_detail_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_stamp_uid TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_on_store TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_push_enabled INTEGER, c_owner TEXT, c_is_online INTEGER, c_is_public INTEGER, c_is_official INTEGER, c_type TEXT, c_members_count INTEGER, c_user_uid TEXT, c_wallpaper TEXT, PRIMARY KEY(c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_detail_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_total_users INTEGER, c_online_users INTEGER, c_is_online INTEGER, c_is_public INTEGER, c_is_official INTEGER, c_type TEXT, c_last_chat_at INTEGER, c_push_enabled INTEGER, c_user_uid TEXT NOT NULL, PRIMARY KEY(c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x011e:
            r2 = "DROP TABLE group_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE group_detail_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_detail_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_total_users INTEGER, c_online_users INTEGER, c_is_online INTEGER, c_is_public INTEGER, c_is_official INTEGER, c_type TEXT, c_last_chat_at INTEGER, c_push_enabled INTEGER, c_is_notice INTEGER, c_user_uid TEXT NOT NULL, PRIMARY KEY(c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE group_table (c_uid TEXT, c_name TEXT, c_description TEXT, c_created_date INTEGER, c_icon TEXT, c_stream_host TEXT, c_push_enabled INTEGER, c_owner TEXT, c_is_online INTEGER, c_is_public INTEGER, c_is_official INTEGER, c_type TEXT, c_members_count INTEGER, c_user_uid TEXT, c_wallpaper TEXT, c_is_notice INTEGER, PRIMARY KEY(c_uid, c_user_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0132:
            r2 = "ALTER TABLE user_table ADD COLUMN c_unread_counts INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0137:
            r2 = "DELETE FROM group_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DELETE FROM group_detail_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DELETE FROM category_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0146:
            r2 = "ALTER TABLE chat_table ADD COLUMN c_replies_count INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DELETE FROM group_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DELETE FROM group_detail_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DELETE FROM category_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x015a:
            r2 = "CREATE TABLE upload_table (c_uid INTEGER PRIMARY KEY AUTOINCREMENT, c_group_uid TEXT, c_reply_to TEXT, c_total INTEGER, c_message TEXT, c_shout INTEGER);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE upload_item_table (c_uid TEXT, c_upload_uid INTEGER, c_type TEXT, c_url TEXT, c_complete INTEGER, PRIMARY KEY(c_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE asset_table (c_id TEXT, c_upload_uid TEXT, c_type TEXT, c_url TEXT, PRIMARY KEY(c_id));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE download_table (c_uid INTEGER PRIMARY KEY AUTOINCREMENT, c_total INTEGER);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE download_item_table (c_asset_uid TEXT, c_download_uid INTEGER, c_type TEXT, c_url TEXT, c_complete INTEGER, PRIMARY KEY(c_asset_uid, c_download_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_assets TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0178:
            r2 = "CREATE TABLE notification_table (c_user_icon TEXT, c_title TEXT, c_message TEXT, c_icon TEXT, c_type TEXT, c_link TEXT, c_date INTEGER);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x017d:
            r2 = "DROP TABLE notification_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE notification_table (c_id INTEGER, c_user_icon TEXT, c_title TEXT, c_message TEXT, c_icon TEXT, c_type TEXT, c_link TEXT, c_date INTEGER, PRIMARY KEY(c_id));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x0187:
            r2 = "DROP TABLE notification_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE group_table ADD COLUMN c_ex_id TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE group_detail_table ADD COLUMN c_ex_id TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE user_table ADD COLUMN c_ex_id TEXT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE user_table_tmp (c_uid TEXT, c_default INTEGER, c_token TEXT, c_name TEXT, c_description TEXT, c_icon TEXT, c_cover TEXT, c_contacts_count INTEGER, c_contacted_date INTEGER, c_is_nan_location INTEGER, c_lng REAL, c_lat REAL, c_located_date INTEGER, c_unread_counts INTEGER, c_app_uid TEXT, c_ex_id TEXT, PRIMARY KEY(c_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "INSERT INTO user_table_tmp SELECT c_uid, c_default, c_token, c_name, c_description, c_icon, c_cover, c_contacts_count, c_contacted_date, c_is_nan_location, c_lng, c_lat, c_located_date, c_unread_counts, c_app_uid, c_ex_id FROM user_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "DROP TABLE user_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE user_table_tmp RENAME TO user_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x01af:
            r2 = "CREATE TABLE ad_waiting_app_table (c_ad_id INTEGER, c_package TEXT, c_client_id TEXT, c_date INTEGER, PRIMARY KEY (c_ad_id) ON CONFLICT REPLACE);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x01b4:
            r2 = "ALTER TABLE group_table ADD COLUMN c_is_authorized INT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE group_detail_table ADD COLUMN c_is_authorized INT;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x01be:
            r2 = "ALTER TABLE chat_table ADD COLUMN c_likes_count INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_boos_count INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_liked INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "ALTER TABLE chat_table ADD COLUMN c_booed INTEGER;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x01d2:
            r2 = "DROP TABLE ad_waiting_app_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE ad_waiting_app_table (c_ad_id TEXT, c_package TEXT, c_client_id TEXT, c_date INTEGER, c_count_conversion INTEGER, PRIMARY KEY (c_ad_id) ON CONFLICT REPLACE);";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
        L_0x01dc:
            r2 = "DROP TABLE upload_item_table;";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            r2 = "CREATE TABLE upload_item_table (c_uid TEXT, c_upload_uid INTEGER, c_type TEXT, c_url TEXT, c_complete INTEGER, PRIMARY KEY (c_uid));";
            r6.execSQL(r2);	 Catch:{ SQLException -> 0x01e8 }
            goto L_0x0024;
        L_0x01e8:
            r0 = move-exception;
            r2 = com.kayac.lobi.libnakamap.datastore.TransactionDatastore.sContext;
            r3 = "nakamap_transaction.sqlite";
            r1 = r2.getDatabasePath(r3);
            r1.delete();
            r2 = android.os.Process.myPid();
            android.os.Process.killProcess(r2);
            goto L_0x0024;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.datastore.TransactionDatastore.TransactionDataHelper.onUpgrade(android.database.sqlite.SQLiteDatabase, int, int):void");
        }
    }

    private TransactionDatastore() {
    }

    public static final void init(Context context) {
        sContext = context;
        synchronized (MUTEX) {
            if (sHelper == null) {
                sHelper = TransactionDataHelper.newInstance(context);
            }
        }
    }

    public static final void init(Context context, String rootDir) {
        sContext = context;
        synchronized (MUTEX) {
            if (sHelper == null) {
                sHelper = TransactionDataHelper.newInstance(context, rootDir);
            }
        }
    }

    public static final void deleteAll() {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL("DELETE FROM key_value_table");
                db.execSQL("DELETE FROM key_key_value_table");
                db.execSQL("DELETE FROM widget_table");
                db.execSQL("DELETE FROM group_detail_table");
                db.execSQL("DELETE FROM group_member_table");
                db.execSQL("DELETE FROM group_table");
                db.execSQL("DELETE FROM chat_table");
                db.execSQL("DELETE FROM user_table");
                db.execSQL("DELETE FROM category_table");
                db.execSQL("DELETE FROM file_cache_table");
                db.execSQL("DELETE FROM stamp_category_table");
                db.execSQL("DELETE FROM stamp_item_table");
                db.execSQL("DELETE FROM user_contact_table");
                db.execSQL("DELETE FROM group_permission_table");
                db.execSQL("DELETE FROM chat_refer_table");
                db.execSQL("DELETE FROM stamp_history_table");
                db.execSQL("DELETE FROM ad_waiting_app_table");
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setValue(String key, Serializable value) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setValue(db, key, value);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setValues(List<Pair<String, Serializable>> keyValues) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                for (Pair<String, Serializable> keyValue : keyValues) {
                    Function.setValue(db, (String) keyValue.first, (Serializable) keyValue.second);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final <T> T getValue(String key) {
        T value;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            value = null;
            try {
                db = sHelper.getReadableDatabase();
                value = Function.getValue(db, key);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return value;
    }

    public static final <T> T getValue(String key, T fallback) {
        T t = getValue(key);
        return t != null ? t : fallback;
    }

    public static final <T> List<Pair<String, T>> getValues(List<String> keys, T fallback) {
        List<Pair<String, T>> values;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            values = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                db.beginTransaction();
                for (String key : keys) {
                    T value = Function.getValue(db, key);
                    if (value == null) {
                        value = fallback;
                    }
                    values.add(new Pair(key, value));
                }
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return values;
    }

    public static final void deleteValue(String key) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteValue(db, key);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setKKValue(String key1, String key2, Serializable value) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setKKValue(db, key1, key2, value);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setKKValues(String key1, List<Pair<String, Serializable>> key2Values) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                for (Pair<String, Serializable> key2Value : key2Values) {
                    Function.setKKValue(db, key1, (String) key2Value.first, (Serializable) key2Value.second);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final <T> T getKKValue(String key1, String key2) {
        T value;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            value = null;
            try {
                db = sHelper.getReadableDatabase();
                value = Function.getKKValue(db, key1, key2);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return value;
    }

    public static final <T> T getKKValue(String key1, String key2, T fallback) {
        T t = getKKValue(key1, key2);
        return t != null ? t : fallback;
    }

    public static final <T> List<Pair<String, T>> getKKValues(String key1, List<String> key2s, T fallback) {
        List<Pair<String, T>> values;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            values = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                db.beginTransaction();
                for (String key2 : key2s) {
                    T value = Function.getKKValue(db, key1, key2);
                    if (value == null) {
                        value = fallback;
                    }
                    values.add(new Pair(key2, value));
                }
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return values;
    }

    public static final Map<String, String> getKKValues(String key1) {
        Map<String, String> values;
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            values = null;
            try {
                db = sHelper.getReadableDatabase();
                values = getKKValuesImpl(db, key1);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return values;
    }

    private static final <T> Map<String, T> getKKValuesImpl(SQLiteDatabase db, String key1) {
        Cursor c = null;
        Map<String, T> values = new HashMap();
        try {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables("key_key_value_table");
            SQLiteDatabase sQLiteDatabase = db;
            c = queryBuilder.query(sQLiteDatabase, new String[]{"c_key_2", "c_value"}, "c_key_1 = ?", new String[]{key1}, null, null, null);
            if (c.moveToFirst()) {
                do {
                    values.put(c.getString(c.getColumnIndex("c_key_2")), new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject());
                } while (c.moveToNext());
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
        return values;
    }

    public static final void deleteKKValue(String key1, String key2) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteKKValue(db, key1, key2);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setWidget(int appWidgetId, String token, String userUid, String groupUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setWidget(db, appWidgetId, token, userUid, groupUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final WidgetMetaDataValue getWidget(int appWidgetId) {
        WidgetMetaDataValue widgetMetaData;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            widgetMetaData = null;
            try {
                db = sHelper.getReadableDatabase();
                widgetMetaData = TransactionDatastoreImpl.getWidget(db, appWidgetId);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return widgetMetaData;
    }

    public static final ArrayList<WidgetMetaDataValue> getWidgetList(String groupUid) {
        ArrayList<WidgetMetaDataValue> ary;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            ary = null;
            try {
                db = sHelper.getReadableDatabase();
                ary = TransactionDatastoreImpl.getWidgetList(db, groupUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return ary;
    }

    public static final ArrayList<WidgetMetaDataValue> getWidgetList() {
        ArrayList<WidgetMetaDataValue> ary;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            ary = null;
            try {
                db = sHelper.getReadableDatabase();
                ary = TransactionDatastoreImpl.getWidgetList(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return ary;
    }

    public static final void deleteWidget(int appWidgetId) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteWidget(db, appWidgetId);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setCategory(CategoryValue category, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setCategory(db, category, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final CategoryValue getCategory(String id, String userUid) {
        CategoryValue category;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            category = null;
            try {
                db = sHelper.getReadableDatabase();
                category = TransactionDatastoreImpl.getCategory(db, id, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return category;
    }

    @Deprecated
    public static final List<CategoryValue> getCategories(String userUid) {
        List<CategoryValue> categories;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            categories = null;
            try {
                db = sHelper.getReadableDatabase();
                categories = TransactionDatastoreImpl.getCategories(db, userUid, null);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return categories;
    }

    @Deprecated
    public static final List<CategoryValue> getCategories(String userUid, Order order) {
        List<CategoryValue> categories;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            categories = null;
            try {
                db = sHelper.getReadableDatabase();
                categories = TransactionDatastoreImpl.getCategories(db, userUid, order);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return categories;
    }

    public static final void setGroupDetail(GroupDetailValue groupDetail, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setGroupDetail(db, groupDetail, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setGroupDetails(List<GroupDetailValue> groupDetails, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteGroupDetailForUser(db, userUid);
                Function.setKKValue(db, KKey.GROUP_UPDATE_AT, userUid, Long.valueOf(System.currentTimeMillis()));
                for (GroupDetailValue groupDetail : groupDetails) {
                    TransactionDatastoreImpl.setGroupDetail(db, groupDetail, userUid);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final GroupDetailValue getGroupDetail(String groupUid, String userUid) {
        GroupDetailValue groupDetail;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            groupDetail = null;
            try {
                db = sHelper.getReadableDatabase();
                groupDetail = TransactionDatastoreImpl.getGroupDetail(db, groupUid, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return groupDetail;
    }

    public static final boolean needGeoLocation(String userUid) {
        boolean need;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            need = false;
            try {
                db = sHelper.getReadableDatabase();
                need = TransactionDatastoreImpl.needGeoLocation(db, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return need;
    }

    public static final boolean needGeoLocation() {
        boolean need;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            need = false;
            try {
                db = sHelper.getReadableDatabase();
                need = TransactionDatastoreImpl.needGeoLocation(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return need;
    }

    public static final boolean needPushNotification(String userUid) {
        boolean need;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            need = false;
            try {
                db = sHelper.getReadableDatabase();
                need = TransactionDatastoreImpl.needPushNotification(db, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return need;
    }

    public static final boolean needPushNotification() {
        boolean need;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            need = false;
            try {
                db = sHelper.getReadableDatabase();
                need = TransactionDatastoreImpl.needPushNotification(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return need;
    }

    public static final void deleteGroupDetail(String groupUid, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteCategory(db, groupUid, userUid);
                TransactionDatastoreImpl.deleteGroupDetail(db, groupUid, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void deleteCategories(String type, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteCategories(db, userUid, type);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setGroup(GroupValue group, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setGroup(db, group, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final GroupValue getGroup(String groupUid, String userUid) {
        GroupValue group;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            group = null;
            try {
                db = sHelper.getReadableDatabase();
                group = TransactionDatastoreImpl.getGroup(db, groupUid, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return group;
    }

    public static final void deleteGroup(String groupUid, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteGroup(db, groupUid, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final ArrayList<ChatValue> getChats(String groupUid) {
        ArrayList<ChatValue> chats;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            chats = null;
            try {
                db = sHelper.getReadableDatabase();
                chats = TransactionDatastoreImpl.getChats(db, groupUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return chats;
    }

    public static final void deleteChat(String chatId) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteChat(db, chatId);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void deleteChatInGroup(String groupUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteChatInGroup(db, groupUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setUser(UserValue user) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setUser(db, user);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final UserValue getUser(String targetUser) {
        UserValue user;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            user = null;
            try {
                db = sHelper.getReadableDatabase();
                user = Function.getUser(db, targetUser);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return user;
    }

    public static final void deleteUser(String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteUser(db, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setFileCache(FileCacheValue fileCache) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setFileCache(db, fileCache);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setFileCache(String path, String type, int fileSize) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setFileCache(db, path, type, fileSize);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final FileCacheValue getFileCache(String path) {
        FileCacheValue fileCeche;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCeche = null;
            try {
                db = sHelper.getReadableDatabase();
                fileCeche = TransactionDatastoreImpl.getFileCache(db, path);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCeche;
    }

    public static final List<FileCacheValue> getFileCaches(int limit) {
        List<FileCacheValue> fileCeches;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCeches = null;
            try {
                db = sHelper.getReadableDatabase();
                fileCeches = TransactionDatastoreImpl.getFileCaches(db, limit);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCeches;
    }

    public static final List<FileCacheValue> getFileCaches(String type, int limit) {
        List<FileCacheValue> fileCeches;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCeches = null;
            try {
                db = sHelper.getReadableDatabase();
                fileCeches = TransactionDatastoreImpl.getFileCaches(db, type, limit);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCeches;
    }

    public static final List<FileCacheValue> getFileCaches(int limit, long fromDate) {
        List<FileCacheValue> fileCeches;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCeches = null;
            try {
                db = sHelper.getReadableDatabase();
                fileCeches = TransactionDatastoreImpl.getFileCaches(db, limit, fromDate);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCeches;
    }

    public static final List<FileCacheValue> getFileCaches(String type, int limit, long fromDate) {
        List<FileCacheValue> fileCeches;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCeches = null;
            try {
                db = sHelper.getReadableDatabase();
                fileCeches = TransactionDatastoreImpl.getFileCaches(db, type, limit, fromDate);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCeches;
    }

    public static final int getFileCacheEntryCount() {
        int entry;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            entry = 0;
            try {
                db = sHelper.getReadableDatabase();
                entry = TransactionDatastoreImpl.getFileCacheEntry(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return entry;
    }

    public static final int getFileCacheEntryCount(String type) {
        int entry;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            entry = 0;
            try {
                db = sHelper.getReadableDatabase();
                entry = TransactionDatastoreImpl.getFileCacheEntry(db, type);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return entry;
    }

    public static final long getFileCacheFileSizeSum() {
        long fileCacheSize;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCacheSize = 0;
            try {
                db = sHelper.getReadableDatabase();
                fileCacheSize = TransactionDatastoreImpl.getFileCacheFileSizeSum(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCacheSize;
    }

    public static final long getFileCacheFileSizeSum(String type) {
        long fileCacheSize;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            fileCacheSize = 0;
            try {
                db = sHelper.getReadableDatabase();
                fileCacheSize = TransactionDatastoreImpl.getFileCacheFileSizeSum(db, type);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return fileCacheSize;
    }

    public static final void deleteFileCache(String path) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteFileCache(db, path);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setStamp(StampValue stamp, int categoryOrder) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setStamp(db, stamp, categoryOrder);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final List<StampValue> getStamps() {
        List<StampValue> stamps;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            stamps = null;
            try {
                db = sHelper.getReadableDatabase();
                stamps = TransactionDatastoreImpl.getStamps(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return stamps;
    }

    public static final void deleteAllStamp() {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteAllStamp(db);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void deleteStamp(String uid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteStamp(db, uid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void deleteStamps(String category) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteStamps(db, category);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void addStampHistory(String uid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.addStampHistory(db, uid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final List<Item> getStampHistory() {
        List<Item> stamps;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            stamps = null;
            try {
                db = sHelper.getReadableDatabase();
                stamps = TransactionDatastoreImpl.getStampHistory(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return stamps;
    }

    public static final Long setDownload(int total) {
        Long l;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            l = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                l = Long.valueOf(TransactionDatastoreImpl.setDownload(db, total));
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return l;
    }

    public static final List<DownloadValue> getDownload() {
        List<DownloadValue> downloads;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            downloads = null;
            try {
                db = sHelper.getReadableDatabase();
                downloads = TransactionDatastoreImpl.getDownload(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return downloads;
    }

    public static final DownloadValue getDownload(int downloadUid) {
        DownloadValue downloads;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            downloads = null;
            try {
                db = sHelper.getReadableDatabase();
                downloads = TransactionDatastoreImpl.getDownload(db, downloadUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return downloads;
    }

    public static final void deleteDownload(int id) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteDownload(db, id);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setDownloadItem(List<DownloadValue.Item> items) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                for (DownloadValue.Item item : items) {
                    TransactionDatastoreImpl.setDownloadItem(db, item.downloadUid, item.assetUid, item.type, item.url);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final List<DownloadValue.Item> getDownloadItem(int downloadUid) {
        List<DownloadValue.Item> items;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            items = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                items = TransactionDatastoreImpl.getDownloadItem(db, downloadUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return items;
    }

    public static final void deleteDownloadItem(int downloadUid, String assetUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteDownloadItem(db, downloadUid, assetUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final Long setUpload(String groupUid, String replyTo, int total, String message, int shout) {
        Long l;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            l = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                l = Long.valueOf(TransactionDatastoreImpl.setUpload(db, groupUid, replyTo, total, message, shout));
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return l;
    }

    public static final List<UploadValue> getUpload() {
        List<UploadValue> uploads;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            uploads = null;
            try {
                db = sHelper.getReadableDatabase();
                uploads = TransactionDatastoreImpl.getUpload(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return uploads;
    }

    public static final List<UploadValue> getUpload(int uploadUid) {
        List<UploadValue> uploads;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            uploads = null;
            try {
                db = sHelper.getReadableDatabase();
                uploads = TransactionDatastoreImpl.getUpload(db, uploadUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return uploads;
    }

    public static final void deleteUpload(int id) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteUpload(db, id);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setUploadItem(UploadValue.Item item) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setUploadItem(db, item);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setUploadItem(List<UploadValue.Item> items) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                for (UploadValue.Item item : items) {
                    TransactionDatastoreImpl.setUploadItem(db, item);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final int getUploadItemCount(int uploadUid) {
        int count;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            count = 0;
            try {
                db = sHelper.getReadableDatabase();
                count = TransactionDatastoreImpl.getUploadItemCount(db, uploadUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return count;
    }

    public static final List<UploadValue.Item> getUploadItem(int uploadUid) {
        List<UploadValue.Item> items;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            items = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                items = TransactionDatastoreImpl.getUploadItem(db, uploadUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return items;
    }

    public static final void deleteUploadItem(String uid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteUploadItem(db, uid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setAsset(int uploadUid, String id, String type, String url) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setAssets(db, uploadUid, id, type, url);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final List<AssetValue> getAsset(int uploadUid) {
        List<AssetValue> assets;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            assets = null;
            try {
                db = sHelper.getReadableDatabase();
                assets = TransactionDatastoreImpl.getAsset(db, uploadUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return assets;
    }

    public static final void deleteAsset(String id) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteAsset(db, id);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setUserContact(UserContactValue contact, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setUserContact(db, contact, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setUserContacts(List<UserContactValue> contacts, String userUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setUserContacts(db, contacts, userUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final List<UserContactValue> getUserContacts(String userUid, UserContact.Order order) {
        List<UserContactValue> contacts;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            contacts = null;
            try {
                db = sHelper.getReadableDatabase();
                contacts = TransactionDatastoreImpl.getUserContacts(db, userUid, order);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return contacts;
    }

    public static final List<UserContactValue> getUserContacts(String userUid, UserContact.Order order, String query) {
        List<UserContactValue> contacts;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            contacts = null;
            try {
                db = sHelper.getReadableDatabase();
                contacts = TransactionDatastoreImpl.getUserContacts(db, userUid, order, query);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return contacts;
    }

    public static final void deleteUserContact(String myUid, String userContactUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteUserContact(db, myUid, userContactUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setAdWaitingApp(String adId, String packageName, String clientId, boolean countConversion) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.setAdWaitingApp(db, adId, packageName, clientId, countConversion);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final ArrayList<AdWaitingAppValue> getAdWaitingApp(String packageName) {
        ArrayList<AdWaitingAppValue> ads;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            ads = null;
            try {
                db = sHelper.getReadableDatabase();
                ads = TransactionDatastoreImpl.getAdWaitingApp(db, packageName);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return ads;
    }

    public static final void deleteAdWaitingApp(String adId) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                TransactionDatastoreImpl.deleteAdWaitingApp(db, adId);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }
}
