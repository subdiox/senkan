package cn.sharesdk.unity3d;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.UIHandler;
import com.rekoo.libs.cons.Cons;
import com.unity3d.player.UnityPlayer;
import java.util.HashMap;
import java.util.Map.Entry;

public class ShareSDKUtils implements Callback {
    private static boolean DEBUG = true;
    private static final int MSG_AUTHORIZE = 2;
    private static final int MSG_FOLLOW_FRIEND = 7;
    private static final int MSG_GET_FRIENDLIST = 6;
    private static final int MSG_INITSDK = 1;
    private static final int MSG_ONEKEY_SAHRE = 5;
    private static final int MSG_SHARE = 4;
    private static final int MSG_SHOW_USER = 3;
    private static Context context;
    private static boolean disableSSO = false;
    private static String u3dCallback;
    private static String u3dGameObject;

    public ShareSDKUtils(String gameObject, String callback) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.prepare");
        }
        if (context == null) {
            context = UnityPlayer.currentActivity.getApplicationContext();
        }
        if (!TextUtils.isEmpty(gameObject)) {
            u3dGameObject = gameObject;
        }
        if (!TextUtils.isEmpty(callback)) {
            u3dCallback = callback;
        }
    }

    public void initSDK(String appKey) {
        if (DEBUG) {
            System.out.println("initSDK appkey ==>>" + appKey);
        }
        if (TextUtils.isEmpty(appKey)) {
            ShareSDK.initSDK(context);
        } else {
            ShareSDK.initSDK(context, appKey);
        }
    }

    public void setPlatformConfig(String configs) {
        if (DEBUG) {
            System.out.println("initSDK configs ==>>" + configs);
        }
        if (!TextUtils.isEmpty(configs)) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = configs;
            UIHandler.sendMessageDelayed(msg, 1000, this);
        }
    }

    public void authorize(int reqID, int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.authorize");
        }
        Message msg = new Message();
        msg.what = 2;
        msg.arg1 = platform;
        msg.arg2 = reqID;
        UIHandler.sendMessage(msg, this);
    }

    public void removeAccount(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.removeAccount");
        }
        ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform)).removeAccount(true);
    }

    public boolean isAuthValid(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.isAuthValid");
        }
        return ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform)).isAuthValid();
    }

    public boolean isClientValid(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.isClientValid");
        }
        return ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform)).isClientValid();
    }

    public void showUser(int reqID, int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.showUser");
        }
        Message msg = new Message();
        msg.what = 3;
        msg.arg1 = platform;
        msg.arg2 = reqID;
        UIHandler.sendMessage(msg, this);
    }

    public void shareContent(int reqID, int platform, String content) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.share");
        }
        Message msg = new Message();
        msg.what = 4;
        msg.arg1 = platform;
        msg.obj = content;
        msg.arg2 = reqID;
        UIHandler.sendMessage(msg, this);
    }

    public void onekeyShare(int reqID, int platform, String content) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.OnekeyShare");
        }
        Message msg = new Message();
        msg.what = 5;
        msg.arg1 = platform;
        msg.obj = content;
        msg.arg2 = reqID;
        UIHandler.sendMessage(msg, this);
    }

    public void getFriendList(int reqID, int platform, int count, int page) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.getFriendList");
        }
        Message msg = new Message();
        msg.what = 6;
        msg.arg1 = platform;
        msg.arg2 = reqID;
        Bundle data = new Bundle();
        data.putInt("page", page);
        data.putInt("count", count);
        msg.setData(data);
        UIHandler.sendMessage(msg, this);
    }

    public void followFriend(int reqID, int platform, String account) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.followFriend");
        }
        Message msg = new Message();
        msg.what = 7;
        msg.arg1 = platform;
        msg.obj = account;
        msg.arg2 = reqID;
        UIHandler.sendMessage(msg, this);
    }

    public String getAuthInfo(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.getAuthInfo");
        }
        Platform plat = ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform));
        Hashon hashon = new Hashon();
        HashMap<String, Object> map = new HashMap();
        if (plat.isValid()) {
            map.put("expiresIn", Long.valueOf(plat.getDb().getExpiresIn()));
            map.put("expiresTime", Long.valueOf(plat.getDb().getExpiresTime()));
            map.put("token", plat.getDb().getToken());
            map.put("tokenSecret", plat.getDb().getTokenSecret());
            map.put("userGender", plat.getDb().getUserGender());
            map.put("userID", plat.getDb().getUserId());
            map.put("openID", plat.getDb().get("openid"));
            map.put(Cons.USER_NAME, plat.getDb().getUserName());
            map.put("userIcon", plat.getDb().getUserIcon());
        }
        return hashon.fromHashMap(map);
    }

    public void disableSSOWhenAuthorize(boolean open) {
        disableSSO = open;
    }

    public boolean handleMessage(Message msg) {
        int platform;
        Unity3dPlatformActionListener unity3dPlatformActionListener;
        Platform plat;
        String content;
        Hashon hashon;
        HashMap<String, String> customizeSP;
        switch (msg.what) {
            case 1:
                if (DEBUG) {
                    System.out.println("ShareSDKUtils.setPlatformConfig");
                }
                for (Entry<String, Object> entry : new Hashon().fromJson(msg.obj).entrySet()) {
                    String p = ShareSDK.platformIdToName(Integer.parseInt((String) entry.getKey()));
                    if (p != null) {
                        if (DEBUG) {
                            System.out.println(new StringBuilder(String.valueOf(p)).append(" ==>>").append(new Hashon().fromHashMap((HashMap) entry.getValue())).toString());
                        }
                        ShareSDK.setPlatformDevInfo(p, (HashMap) entry.getValue());
                    }
                }
                break;
            case 2:
                platform = msg.arg1;
                unity3dPlatformActionListener = new Unity3dPlatformActionListener(u3dGameObject, u3dCallback);
                unity3dPlatformActionListener.setReqID(msg.arg2);
                plat = ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform));
                plat.setPlatformActionListener(unity3dPlatformActionListener);
                plat.SSOSetting(disableSSO);
                plat.authorize();
                break;
            case 3:
                platform = msg.arg1;
                unity3dPlatformActionListener = new Unity3dPlatformActionListener(u3dGameObject, u3dCallback);
                unity3dPlatformActionListener.setReqID(msg.arg2);
                plat = ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform));
                plat.setPlatformActionListener(unity3dPlatformActionListener);
                plat.SSOSetting(disableSSO);
                plat.showUser(null);
                break;
            case 4:
                int platformID = msg.arg1;
                unity3dPlatformActionListener = new Unity3dPlatformActionListener(u3dGameObject, u3dCallback);
                unity3dPlatformActionListener.setReqID(msg.arg2);
                content = msg.obj;
                plat = ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platformID));
                plat.setPlatformActionListener(unity3dPlatformActionListener);
                plat.SSOSetting(disableSSO);
                try {
                    hashon = new Hashon();
                    if (DEBUG) {
                        System.out.println("share content ==>>" + content);
                    }
                    HashMap data = hashon.fromJson(content);
                    ShareParams shareParams = new ShareParams(data);
                    if (data.containsKey("customizeShareParams")) {
                        customizeSP = (HashMap) data.get("customizeShareParams");
                        if (customizeSP.size() > 0) {
                            String pID = String.valueOf(platformID);
                            if (customizeSP.containsKey(pID)) {
                                String cSP = (String) customizeSP.get(pID);
                                if (DEBUG) {
                                    System.out.println("share content ==>>" + cSP);
                                }
                                HashMap<String, Object> data2 = hashon.fromJson(cSP);
                                for (String key : data2.keySet()) {
                                    shareParams.set(key, data2.get(key));
                                }
                            }
                        }
                    }
                    plat.share(shareParams);
                    break;
                } catch (Throwable t) {
                    unity3dPlatformActionListener.onError(plat, 9, t);
                    break;
                }
            case 5:
                platform = msg.arg1;
                unity3dPlatformActionListener = new Unity3dPlatformActionListener(u3dGameObject, u3dCallback);
                unity3dPlatformActionListener.setReqID(msg.arg2);
                content = (String) msg.obj;
                hashon = new Hashon();
                if (DEBUG) {
                    System.out.println("onekeyshare  ==>>" + content);
                }
                HashMap<String, Object> map = hashon.fromJson(content);
                OnekeyShare oks = new OnekeyShare();
                if (platform > 0) {
                    String name = ShareSDK.platformIdToName(platform);
                    if (DEBUG) {
                        System.out.println("ShareSDKUtils Onekeyshare shareView platform name ==>> " + name);
                    }
                    if (!TextUtils.isEmpty(name)) {
                        oks.setPlatform(name);
                        oks.setSilent(false);
                    }
                }
                if (map.containsKey("text")) {
                    oks.setText((String) map.get("text"));
                }
                if (map.containsKey("imagePath")) {
                    oks.setImagePath((String) map.get("imagePath"));
                }
                if (map.containsKey("imageUrl")) {
                    oks.setImageUrl((String) map.get("imageUrl"));
                }
                if (map.containsKey(LoginEntranceDialog.ARGUMENTS_TITLE)) {
                    oks.setTitle((String) map.get(LoginEntranceDialog.ARGUMENTS_TITLE));
                }
                if (map.containsKey("comment")) {
                    oks.setComment((String) map.get("comment"));
                }
                if (map.containsKey("url")) {
                    oks.setUrl((String) map.get("url"));
                    oks.setTitleUrl((String) map.get("url"));
                }
                if (map.containsKey("site")) {
                    oks.setSite((String) map.get("site"));
                }
                if (map.containsKey("siteUrl")) {
                    oks.setSiteUrl((String) map.get("siteUrl"));
                }
                if (map.containsKey("musicUrl")) {
                    oks.setSiteUrl((String) map.get("musicUrl"));
                }
                if (map.containsKey("shareType") && "6".equals(String.valueOf(map.get("shareType"))) && map.containsKey("url")) {
                    oks.setVideoUrl((String) map.get("url"));
                }
                if (map.containsKey("customizeShareParams")) {
                    customizeSP = (HashMap) map.get("customizeShareParams");
                    if (customizeSP.size() > 0) {
                        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                            public void onShare(Platform platform, ShareParams paramsToShare) {
                                String platformID = String.valueOf(ShareSDK.platformNameToId(platform.getName()));
                                if (customizeSP.containsKey(platformID)) {
                                    Hashon hashon = new Hashon();
                                    String content = (String) customizeSP.get(platformID);
                                    if (ShareSDKUtils.DEBUG) {
                                        System.out.println("share content ==>>" + content);
                                    }
                                    HashMap<String, Object> data = hashon.fromJson(content);
                                    for (String key : data.keySet()) {
                                        paramsToShare.set(key, data.get(key));
                                    }
                                }
                            }
                        });
                    }
                }
                if (disableSSO) {
                    oks.disableSSOWhenAuthorize();
                }
                oks.setCallback(unity3dPlatformActionListener);
                oks.show(context);
                break;
            case 6:
                platform = msg.arg1;
                unity3dPlatformActionListener = new Unity3dPlatformActionListener(u3dGameObject, u3dCallback);
                unity3dPlatformActionListener.setReqID(msg.arg2);
                int page = msg.getData().getInt("page");
                int count = msg.getData().getInt("count");
                plat = ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform));
                plat.setPlatformActionListener(unity3dPlatformActionListener);
                plat.SSOSetting(disableSSO);
                plat.listFriend(count, page, null);
                break;
            case 7:
                platform = msg.arg1;
                unity3dPlatformActionListener = new Unity3dPlatformActionListener(u3dGameObject, u3dCallback);
                unity3dPlatformActionListener.setReqID(msg.arg2);
                String account = msg.obj;
                plat = ShareSDK.getPlatform(context, ShareSDK.platformIdToName(platform));
                plat.setPlatformActionListener(unity3dPlatformActionListener);
                plat.SSOSetting(disableSSO);
                plat.followFriend(account);
                break;
        }
        return false;
    }
}
