package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.PopupWindow.OnDismissListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Key;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIDef.GetNotifications.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetNotifications;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.notifications.NotificationListPopup;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.NotificationValue.Condition;
import com.kayac.lobi.libnakamap.value.NotificationValue.Condition.NotInstalledParams;
import com.kayac.lobi.libnakamap.value.NotificationValue.Condition.Params;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.auth.AccountUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotificationUtil {
    private static final int MAX_FETCH_NOTIFICATIONS_FOR_BADGE = 10;
    private static final int MIN_INTERVAL_NOTIFICATIONS_FOR_BADGE_MS = 60000;
    public static final long PROMOTION_NOTIFICATION_INTERVAL = 86400000;
    private static boolean sHasMainAccount = false;

    public interface OnGetNotification {
        void onGetNotification(int i, boolean z);
    }

    public static class BadgeCache {
        private static BadgeCache sChatCache = new BadgeCache(KKey.LAST_NOTIFICATION_AT_CHAT);
        private static BadgeCache sRecAndRankingCache = new BadgeCache(KKey.LAST_NOTIFICATION_AT_REC_AND_RANKING);
        public final String dbKeyLastNotificationAt;
        public long lastUpdate;
        public int newCount;

        public BadgeCache(String dbKeyLastNotificationAt) {
            this.dbKeyLastNotificationAt = dbKeyLastNotificationAt;
        }

        public static BadgeCache get(NotificationSDKType sdkType) {
            return sdkType == NotificationSDKType.ChatSDK ? sChatCache : sRecAndRankingCache;
        }
    }

    public enum NotificationSDKType {
        ChatSDK,
        RankingSDK,
        RecSDK,
        Unknown
    }

    public enum PromoteType {
        Open("open"),
        Upload("upload");
        
        private final String value;

        private PromoteType(String n) {
            this.value = n;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static void openPopup(final Context context, final int offset, final View parent, final OnDismissListener onDismissListener, NotificationSDKType sdkType) {
        if (context != null && (context instanceof Activity) && parent != null) {
            final Activity activity = (Activity) context;
            final NotificationListPopup notification = new NotificationListPopup(context, getPromotionNotificationType(sdkType), sHasMainAccount, sdkType);
            notification.setAnimationStyle(0);
            notification.showAtLocation(parent, 49, 0, DeviceUtil.getStatusBarHeight(activity) + offset);
            updatePopupSize(context, activity, notification, offset);
            final OnGlobalLayoutListener listener = new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    NotificationUtil.updatePopupSize(context, activity, notification, offset);
                }
            };
            parent.getViewTreeObserver().addOnGlobalLayoutListener(listener);
            notification.setOnDismissListener(new OnDismissListener() {
                public void onDismiss() {
                    if (VERSION.SDK_INT < 16) {
                        parent.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
                    } else {
                        parent.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                    }
                    notification.setOnDismissListener(null);
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss();
                    }
                }
            });
            TransactionDatastoreAsync.setValue(Key.VIDEO_UPLOAD_NOTIFICATION_AVAILABLE, Boolean.valueOf(false), null);
            setLastNotificationAt(sdkType, AccountDatastore.getCurrentUser().getUid());
            BadgeCache.get(sdkType).newCount = 0;
        }
    }

    private static void updatePopupSize(Context context, Activity activity, NotificationListPopup popup, int offset) {
        if (context != null && activity != null && popup != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            popup.update(displayMetrics.widthPixels, (displayMetrics.heightPixels - DeviceUtil.getStatusBarHeight(activity)) - offset);
        }
    }

    public static final void getNotifications(Context context, final NotificationSDKType sdkType, final OnGetNotification onGetNotificationListener) {
        String service;
        final DefaultAPICallback<GetNotifications> onGetNotifications = new DefaultAPICallback<GetNotifications>(context) {
            public void onResponse(GetNotifications t) {
                final List<NotificationValue> notifications = t.notificationValue;
                super.onResponse(t);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Long lastNotification = Long.valueOf(NotificationUtil.getLastNotificationAt(sdkType, AccountDatastore.getCurrentUser().getUid()));
                        NotificationUtil.filter(notifications);
                        int newsNotifications = 0;
                        for (NotificationValue notification : notifications) {
                            if (notification.getCreatedDate() > lastNotification.longValue()) {
                                newsNotifications++;
                            }
                        }
                        BadgeCache.get(sdkType).newCount = newsNotifications;
                        onGetNotificationListener.onGetNotification(newsNotifications, NotificationUtil.getPromotionNotificationType(sdkType) != null);
                    }
                });
            }
        };
        UserValue userValue = AccountDatastore.getCurrentUser();
        final Map<String, String> getNotificationParams = new HashMap();
        getNotificationParams.put("token", userValue.getToken());
        getNotificationParams.put("count", Integer.toString(10));
        if (NotificationSDKType.ChatSDK == sdkType) {
            service = "chat";
        } else {
            service = RequestKey.SERVICE_REC;
        }
        getNotificationParams.put("service", service);
        final NotificationSDKType notificationSDKType = sdkType;
        final OnGetNotification onGetNotification = onGetNotificationListener;
        AccountUtil.requestCurrentUserBindingState(new DefaultAPICallback<Boolean>(null) {
            public void onResponse(Boolean t) {
                super.onResponse(t);
                boolean z = t != null && t.booleanValue();
                NotificationUtil.sHasMainAccount = z;
                doFinally();
            }

            public void onError(int statusCode, String responseBody) {
                super.onError(statusCode, responseBody);
                doFinally();
            }

            public void onError(Throwable e) {
                super.onError(e);
                doFinally();
            }

            private void doFinally() {
                if (System.currentTimeMillis() < BadgeCache.get(notificationSDKType).lastUpdate + 60000) {
                    Log.d(NotificationUtil.class.getSimpleName(), "use cached count");
                    if (onGetNotification != null) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                onGetNotification.onGetNotification(BadgeCache.get(notificationSDKType).newCount, NotificationUtil.getPromotionNotificationType(notificationSDKType) != null);
                            }
                        });
                        return;
                    }
                    return;
                }
                BadgeCache.get(notificationSDKType).lastUpdate = System.currentTimeMillis();
                CoreAPI.getNotifications(getNotificationParams, onGetNotifications);
            }
        });
    }

    public static void filter(List<NotificationValue> notifications) {
        Iterator<NotificationValue> iter = notifications.iterator();
        while (iter.hasNext()) {
            boolean display = true;
            Iterator it = ((NotificationValue) iter.next()).getDisplay().iterator();
            while (it.hasNext()) {
                Condition condition = (Condition) it.next();
                Params conditionParams = condition.getParams();
                if (Condition.TYPE_APP_NOT_INSTALLED.equals(condition.getType())) {
                    Log.i("[notification]", "display condition type: \"" + condition.getType() + "\"");
                    if (AdUtil.isInstalled(((NotInstalledParams) conditionParams).getPackageName())) {
                        Log.i("[notification]", "DISPLAY OFF -- already installed");
                        display = false;
                        continue;
                    } else {
                        continue;
                    }
                } else {
                    Log.e("[notification]", "unknown display condition type: \"" + condition.getType() + "\"");
                    display = false;
                    continue;
                }
                if (!display) {
                    break;
                }
            }
            if (!display) {
                iter.remove();
            }
        }
    }

    private static PromoteType getPromotionNotificationType(NotificationSDKType sdkType) {
        boolean randomNotification = false;
        if (sdkType == NotificationSDKType.ChatSDK) {
            return null;
        }
        if (sHasMainAccount) {
            return null;
        }
        UserValue currentUser = AccountDatastore.optCurrentUser();
        if (currentUser == null) {
            return null;
        }
        if (((Boolean) TransactionDatastore.getValue(Key.VIDEO_UPLOAD_NOTIFICATION_AVAILABLE, Boolean.valueOf(false))).booleanValue()) {
            return PromoteType.Upload;
        }
        long lastNoticeAt = getLastNotificationAt(sdkType, currentUser.getUid());
        if (BadgeCache.get(sdkType).newCount == 0 && lastNoticeAt < System.currentTimeMillis() - PROMOTION_NOTIFICATION_INTERVAL) {
            randomNotification = true;
        }
        return randomNotification ? PromoteType.Open : null;
    }

    public static long getLastNotificationAt(NotificationSDKType sdkType, String userUid) {
        if (TextUtils.isEmpty(userUid)) {
            return 0;
        }
        return ((Long) TransactionDatastore.getKKValue(BadgeCache.get(sdkType).dbKeyLastNotificationAt, userUid, Long.valueOf(0))).longValue();
    }

    private static void setLastNotificationAt(NotificationSDKType sdkType, String userUid) {
        if (!TextUtils.isEmpty(userUid)) {
            TransactionDatastoreAsync.setKKValue(BadgeCache.get(sdkType).dbKeyLastNotificationAt, userUid, Long.valueOf(System.currentTimeMillis()), null);
        }
    }
}
