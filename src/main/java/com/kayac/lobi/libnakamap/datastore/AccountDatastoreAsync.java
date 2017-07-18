package com.kayac.lobi.libnakamap.datastore;

import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountDatastoreAsync {
    private static final ExecutorService sExecutorService = Executors.newCachedThreadPool();

    private AccountDatastoreAsync() {
    }

    public static final void setValue(final String key, final Serializable value, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setValue(key, value);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setValues(final List<Pair<String, Serializable>> keyValues, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setValues(keyValues);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final <T> void getValue(final String key, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = AccountDatastore.getValue(key);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getValue(final String key, final T fallback, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = AccountDatastore.getValue(key, fallback);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getValues(final List<String> keys, final T fallback, final DatastoreAsyncCallback<List<Pair<String, T>>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<Pair<String, T>> values = AccountDatastore.getValues(keys, fallback);
                if (callback != null) {
                    callback.onResponse(values);
                }
            }
        });
    }

    public static final void deleteValue(final String key, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.deleteValue(key);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setKKValue(final String key1, final String key2, final Serializable value, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setKKValue(key1, key2, value);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setKKValues(final String key1, final List<Pair<String, Serializable>> key2Values, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setKKValues(key1, key2Values);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final <T> void getKKValue(final String key1, final String key2, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = AccountDatastore.getKKValue(key1, key2);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getKKValue(final String key1, final String key2, final T fallback, final DatastoreAsyncCallback<T> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                T t = AccountDatastore.getKKValue(key1, key2, fallback);
                if (callback != null) {
                    callback.onResponse(t);
                }
            }
        });
    }

    public static final <T> void getKKValues(final String key1, final List<String> key2s, final T fallback, final DatastoreAsyncCallback<List<Pair<String, T>>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<Pair<String, T>> values = AccountDatastore.getKKValues(key1, key2s, fallback);
                if (callback != null) {
                    callback.onResponse(values);
                }
            }
        });
    }

    public static final <T> void getKKValues(final String key1, final DatastoreAsyncCallback<List<T>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<T> values = AccountDatastore.getKKValues(key1);
                if (callback != null) {
                    callback.onResponse(values);
                }
            }
        });
    }

    public static final void deleteKKValue(final String key1, final String key2, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.deleteKKValue(key1, key2);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void deleteKKValues(final String key1, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.deleteKKValues(key1);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getNotificationId(final String userUid, final DatastoreAsyncCallback<Integer> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                int id = AccountDatastore.getNotificationId(userUid);
                if (callback != null) {
                    callback.onResponse(Integer.valueOf(id));
                }
            }
        });
    }

    public static final void setUser(final UserValue user, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setUser(user);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setUsers(final List<UserValue> users, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setUsers(users);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void setCurrentUser(final UserValue user, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.setCurrentUser(user);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }

    public static final void getUser(final String userUid, final DatastoreAsyncCallback<UserValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                UserValue user = AccountDatastore.getUser(userUid);
                if (callback != null) {
                    callback.onResponse(user);
                }
            }
        });
    }

    public static final void getUserFromAppUid(final String appUid, final DatastoreAsyncCallback<UserValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                UserValue user = AccountDatastore.getUserFromAppUid(appUid);
                if (callback != null) {
                    callback.onResponse(user);
                }
            }
        });
    }

    public static final void getDefaultUser(final DatastoreAsyncCallback<UserValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                UserValue user = AccountDatastore.getDefaultUser();
                if (callback != null) {
                    callback.onResponse(user);
                }
            }
        });
    }

    public static final void getCurrentUser(final DatastoreAsyncCallback<UserValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                UserValue user = AccountDatastore.getCurrentUser();
                if (callback != null) {
                    callback.onResponse(user);
                }
            }
        });
    }

    public static final void optCurrentUser(final DatastoreAsyncCallback<UserValue> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                UserValue user = AccountDatastore.optCurrentUser();
                if (callback != null) {
                    callback.onResponse(user);
                }
            }
        });
    }

    public static final void getUsers(final DatastoreAsyncCallback<List<UserValue>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                List<UserValue> users = AccountDatastore.getUsers();
                if (callback != null) {
                    callback.onResponse(users);
                }
            }
        });
    }

    public static final void deleteUserFromAppUid(final String appUid, final DatastoreAsyncCallback<Void> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                AccountDatastore.deleteUserFromAppUid(appUid);
                if (callback != null) {
                    callback.onResponse(null);
                }
            }
        });
    }
}
