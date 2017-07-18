package com.kayac.lobi.libnakamap.datastore;

import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupDetail.Order;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UserContact;
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
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionDatastoreAsync {
    private static final ExecutorService sExecutorService = Executors.newCachedThreadPool();

    private TransactionDatastoreAsync() {
    }

    public static final void setValue(final String key, final Serializable value, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setValue(key, value);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setValues(final List<Pair<String, Serializable>> keyValues, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setValues(keyValues);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final <T> void getValue(final String key, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = TransactionDatastore.getValue(key);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getValue(final String key, final T fallback, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = TransactionDatastore.getValue(key, fallback);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getValues(final List<String> keys, final T fallback, final DatastoreAsyncCallback<List<Pair<String, T>>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<Pair<String, T>> values = TransactionDatastore.getValues(keys, fallback);
                if (callback != null) {
                    callback.onResponse(values);
                }
            }
        });
    }

    public static final void deleteValue(final String key, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteValue(key);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setKKValue(final String key1, final String key2, final Serializable value, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setKKValue(key1, key2, value);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setKKValues(final String key1, final List<Pair<String, Serializable>> key2Values, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setKKValues(key1, key2Values);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final <T> void getKKValue(final String key1, final String key2, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = TransactionDatastore.getKKValue(key1, key2);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getKKValue(final String key1, final String key2, final T fallback, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = TransactionDatastore.getKKValue(key1, key2, fallback);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getKKValues(final String key1, final List<String> key2s, final T fallback, final DatastoreAsyncCallback<List<Pair<String, T>>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<Pair<String, T>> values = TransactionDatastore.getKKValues(key1, key2s, fallback);
                if (callback != null) {
                    callback.onResponse(values);
                }
            }
        });
    }

    public static final void deleteKKValue(final String key1, final String key2, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteKKValue(key1, key2);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setWidget(int appWidgetId, String token, String userUid, String groupUid, DatastoreAsyncCallback<Void> callback) {
        final int i = appWidgetId;
        final String str = token;
        final String str2 = userUid;
        final String str3 = groupUid;
        final DatastoreAsyncCallback<Void> datastoreAsyncCallback = callback;
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setWidget(i, str, str2, str3);
                if (datastoreAsyncCallback != null) {
                    datastoreAsyncCallback.onResponse(null);
                }
            }
        });
    }

    public static final void getWidget(final int appWidgetId, final DatastoreAsyncCallback<WidgetMetaDataValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                WidgetMetaDataValue widget = TransactionDatastore.getWidget(appWidgetId);
                if (callback != null) {
                    callback.onResponse(widget);
                }
            }
        });
    }

    public static final void getWidgetList(final String groupUid, final DatastoreAsyncCallback<List<WidgetMetaDataValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<WidgetMetaDataValue> widgetList = TransactionDatastore.getWidgetList(groupUid);
                if (callback != null) {
                    callback.onResponse(widgetList);
                }
            }
        });
    }

    public static final void deleteWidget(final int appWidgetId, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteWidget(appWidgetId);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setCategory(final CategoryValue category, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setCategory(category, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getCategory(final String id, final String userUid, final DatastoreAsyncCallback<CategoryValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                CategoryValue category = TransactionDatastore.getCategory(id, userUid);
                if (callback != null) {
                    callback.onResponse(category);
                }
            }
        });
    }

    @Deprecated
    public static final void getCategories(final String userUid, final DatastoreAsyncCallback<List<CategoryValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<CategoryValue> categories = TransactionDatastore.getCategories(userUid);
                if (callback != null) {
                    callback.onResponse(categories);
                }
            }
        });
    }

    @Deprecated
    public static final void getCategories(final String userUid, final Order order, final DatastoreAsyncCallback<List<CategoryValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<CategoryValue> categories = TransactionDatastore.getCategories(userUid, order);
                if (callback != null) {
                    callback.onResponse(categories);
                }
            }
        });
    }

    public static final void setGroupDetail(final GroupDetailValue groupDetail, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setGroupDetail(groupDetail, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setGroupDetails(final List<GroupDetailValue> groupDetails, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setGroupDetails(groupDetails, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getGroupDetail(final String groupUid, final String userUid, final DatastoreAsyncCallback<GroupDetailValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                GroupDetailValue groupDetailValue = TransactionDatastore.getGroupDetail(groupUid, userUid);
                if (callback != null) {
                    callback.onResponse(groupDetailValue);
                }
            }
        });
    }

    public static final void needGeoLocation(final String userUid, final DatastoreAsyncCallback<Boolean> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                boolean needGeoLocation = TransactionDatastore.needGeoLocation(userUid);
                if (callback != null) {
                    callback.onResponse(Boolean.valueOf(needGeoLocation));
                }
            }
        });
    }

    public static final void needGeoLocation(final DatastoreAsyncCallback<Boolean> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                boolean needGeoLocation = TransactionDatastore.needGeoLocation();
                if (callback != null) {
                    callback.onResponse(Boolean.valueOf(needGeoLocation));
                }
            }
        });
    }

    public static final void needPushNotification(final String userUid, final DatastoreAsyncCallback<Boolean> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                boolean needPushNotification = TransactionDatastore.needPushNotification(userUid);
                if (callback != null) {
                    callback.onResponse(Boolean.valueOf(needPushNotification));
                }
            }
        });
    }

    public static final void needPushNotification(final DatastoreAsyncCallback<Boolean> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                boolean needPushNotification = TransactionDatastore.needPushNotification();
                if (callback != null) {
                    callback.onResponse(Boolean.valueOf(needPushNotification));
                }
            }
        });
    }

    public static final void deleteGroupDetail(final String groupUid, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteGroupDetail(groupUid, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void deleteCategories(final String type, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteCategories(type, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setGroup(final GroupValue group, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setGroup(group, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getGroup(final String groupUid, final String userUid, final DatastoreAsyncCallback<GroupValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                GroupValue group = TransactionDatastore.getGroup(groupUid, userUid);
                if (callback != null) {
                    callback.onResponse(group);
                }
            }
        });
    }

    public static final void deleteGroup(final String groupUid, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteGroup(groupUid, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getChats(final String groupUid, final DatastoreAsyncCallback<List<ChatValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<ChatValue> chats = TransactionDatastore.getChats(groupUid);
                if (callback != null) {
                    callback.onResponse(chats);
                }
            }
        });
    }

    public static final void deleteChat(final String chatId, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteChat(chatId);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void deleteChatInGroup(final String groupUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteChatInGroup(groupUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUser(final UserValue user, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setUser(user);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getUser(final String targetUser, final DatastoreAsyncCallback<UserValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                UserValue user = TransactionDatastore.getUser(targetUser);
                if (callback != null) {
                    callback.onResponse(user);
                }
            }
        });
    }

    public static final void deleteUser(final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteUser(userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setFileCache(final FileCacheValue fileCache, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setFileCache(fileCache);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setFileCache(final String path, final String type, final int fileSize, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setFileCache(path, type, fileSize);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getFileCache(final String path, final DatastoreAsyncCallback<FileCacheValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                FileCacheValue fileCache = TransactionDatastore.getFileCache(path);
                if (callback != null) {
                    callback.onResponse(fileCache);
                }
            }
        });
    }

    public static final void getFileCaches(final int limit, final DatastoreAsyncCallback<List<FileCacheValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<FileCacheValue> caches = TransactionDatastore.getFileCaches(limit);
                if (callback != null) {
                    callback.onResponse(caches);
                }
            }
        });
    }

    public static final void getFileCaches(final String type, final int limit, final DatastoreAsyncCallback<List<FileCacheValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<FileCacheValue> caches = TransactionDatastore.getFileCaches(type, limit);
                if (callback != null) {
                    callback.onResponse(caches);
                }
            }
        });
    }

    public static final void getFileCaches(final int limit, final long fromDate, final DatastoreAsyncCallback<List<FileCacheValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<FileCacheValue> caches = TransactionDatastore.getFileCaches(limit, fromDate);
                if (callback != null) {
                    callback.onResponse(caches);
                }
            }
        });
    }

    public static final void getFileCaches(String type, int limit, long fromDate, DatastoreAsyncCallback<List<FileCacheValue>> callback) {
        final String str = type;
        final int i = limit;
        final long j = fromDate;
        final DatastoreAsyncCallback<List<FileCacheValue>> datastoreAsyncCallback = callback;
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<FileCacheValue> caches = TransactionDatastore.getFileCaches(str, i, j);
                if (datastoreAsyncCallback != null) {
                    datastoreAsyncCallback.onResponse(caches);
                }
            }
        });
    }

    public static final void getFileCacheEntryCount(final DatastoreAsyncCallback<Integer> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                int count = TransactionDatastore.getFileCacheEntryCount();
                if (callback != null) {
                    callback.onResponse(Integer.valueOf(count));
                }
            }
        });
    }

    public static final void getFileCacheEntryCount(final String type, final DatastoreAsyncCallback<Integer> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                int count = TransactionDatastore.getFileCacheEntryCount(type);
                if (callback != null) {
                    callback.onResponse(Integer.valueOf(count));
                }
            }
        });
    }

    public static final void getFileCacheFileSizeSum(final DatastoreAsyncCallback<Long> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                long fileSize = TransactionDatastore.getFileCacheFileSizeSum();
                if (callback != null) {
                    callback.onResponse(Long.valueOf(fileSize));
                }
            }
        });
    }

    public static final void getFileCacheFileSizeSum(final String type, final DatastoreAsyncCallback<Long> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                long fileSize = TransactionDatastore.getFileCacheFileSizeSum(type);
                if (callback != null) {
                    callback.onResponse(Long.valueOf(fileSize));
                }
            }
        });
    }

    public static final void deleteFileCache(final String path, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteFileCache(path);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setStamp(final StampValue stamp, final int categoryOrder, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setStamp(stamp, categoryOrder);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getStamps(final DatastoreAsyncCallback<List<StampValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<StampValue> stamps = TransactionDatastore.getStamps();
                if (callback != null) {
                    callback.onResponse(stamps);
                }
            }
        });
    }

    public static final void deleteAllStamp(final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteAllStamp();
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void deleteStamp(final String uid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteStamp(uid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void deleteStamps(final String category, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteStamps(category);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void addStampHistory(final String uid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.addStampHistory(uid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getStampHistory(final DatastoreAsyncCallback<List<Item>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<Item> history = TransactionDatastore.getStampHistory();
                if (callback != null) {
                    callback.onResponse(history);
                }
            }
        });
    }

    public static final void setDownload(final int total, final DatastoreAsyncCallback<Long> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                Long l = TransactionDatastore.setDownload(total);
                if (callback != null) {
                    callback.onResponse(l);
                }
            }
        });
    }

    public static final void getDownload(final DatastoreAsyncCallback<List<DownloadValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<DownloadValue> list = TransactionDatastore.getDownload();
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void getDownload(final int downloadUid, final DatastoreAsyncCallback<DownloadValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                DownloadValue download = TransactionDatastore.getDownload(downloadUid);
                if (callback != null) {
                    callback.onResponse(download);
                }
            }
        });
    }

    public static final void deleteDownload(final int id, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteDownload(id);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setDownloadItem(final List<DownloadValue.Item> items, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setDownloadItem(items);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getDownloadItem(final int downloadUid, final DatastoreAsyncCallback<List<DownloadValue.Item>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<DownloadValue.Item> items = TransactionDatastore.getDownloadItem(downloadUid);
                if (callback != null) {
                    callback.onResponse(items);
                }
            }
        });
    }

    public static final void deleteDownloadItem(final int downloadUid, final String assetUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteDownloadItem(downloadUid, assetUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUpload(String groupUid, String replyTo, int total, String message, int shout, DatastoreAsyncCallback<Long> callback) {
        final String str = groupUid;
        final String str2 = replyTo;
        final int i = total;
        final String str3 = message;
        final int i2 = shout;
        final DatastoreAsyncCallback<Long> datastoreAsyncCallback = callback;
        sExecutorService.execute(new Runnable() {
            public void run() {
                Long l = TransactionDatastore.setUpload(str, str2, i, str3, i2);
                if (datastoreAsyncCallback != null) {
                    datastoreAsyncCallback.onResponse(l);
                }
            }
        });
    }

    public static final void getUpload(final DatastoreAsyncCallback<List<UploadValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<UploadValue> list = TransactionDatastore.getUpload();
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void getUpload(final int uploadUid, final DatastoreAsyncCallback<List<UploadValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<UploadValue> list = TransactionDatastore.getUpload(uploadUid);
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void deleteUpload(final int id, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteUpload(id);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUploadItem(final UploadValue.Item item, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setUploadItem(item);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUploadItem(final List<UploadValue.Item> items, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setUploadItem(items);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getUploadItemCount(final int uploadUid, final DatastoreAsyncCallback<Integer> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                int i = TransactionDatastore.getUploadItemCount(uploadUid);
                if (callback != null) {
                    callback.onResponse(Integer.valueOf(i));
                }
            }
        });
    }

    public static final void getUploadItem(final int uploadUid, final DatastoreAsyncCallback<List<UploadValue.Item>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<UploadValue.Item> list = TransactionDatastore.getUploadItem(uploadUid);
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void deleteUploadItem(final String uid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteUploadItem(uid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setAsset(int uploadUid, String id, String type, String url, DatastoreAsyncCallback<Void> callback) {
        final int i = uploadUid;
        final String str = id;
        final String str2 = type;
        final String str3 = url;
        final DatastoreAsyncCallback<Void> datastoreAsyncCallback = callback;
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setAsset(i, str, str2, str3);
                if (datastoreAsyncCallback != null) {
                    datastoreAsyncCallback.onResponse(null);
                }
            }
        });
    }

    public static final void getAsset(final int uploadUid, final DatastoreAsyncCallback<List<AssetValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<AssetValue> list = TransactionDatastore.getAsset(uploadUid);
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void deleteAsset(final String id, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteAsset(id);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUserContact(final UserContactValue contact, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setUserContact(contact, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUserContacts(final List<UserContactValue> contacts, final String userUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.setUserContacts(contacts, userUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getUserContacts(final String userUid, final UserContact.Order order, final DatastoreAsyncCallback<List<UserContactValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<UserContactValue> list = TransactionDatastore.getUserContacts(userUid, order);
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void getUserContacts(final String userUid, final UserContact.Order order, final String query, final DatastoreAsyncCallback<List<UserContactValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<UserContactValue> list = TransactionDatastore.getUserContacts(userUid, order, query);
                if (callback != null) {
                    callback.onResponse(list);
                }
            }
        });
    }

    public static final void deleteUserContact(final String myUid, final String userContactUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                TransactionDatastore.deleteUserContact(myUid, userContactUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }
}
