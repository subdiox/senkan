package cn.sharesdk.onekeyshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.R;
import java.util.ArrayList;
import java.util.HashMap;

public class OnekeyShare {
    private HashMap<String, Object> params = new HashMap();

    public OnekeyShare() {
        this.params.put("customers", new ArrayList());
        this.params.put("hiddenPlatforms", new HashMap());
    }

    public void setAddress(String address) {
        this.params.put("address", address);
    }

    public void setTitle(String title) {
        this.params.put(LoginEntranceDialog.ARGUMENTS_TITLE, title);
    }

    public void setTitleUrl(String titleUrl) {
        this.params.put("titleUrl", titleUrl);
    }

    public void setText(String text) {
        this.params.put("text", text);
    }

    public String getText() {
        return this.params.containsKey("text") ? String.valueOf(this.params.get("text")) : null;
    }

    public void setImagePath(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            this.params.put("imagePath", imagePath);
        }
    }

    public void setImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            this.params.put("imageUrl", imageUrl);
        }
    }

    public void setUrl(String url) {
        this.params.put("url", url);
    }

    public void setFilePath(String filePath) {
        this.params.put("filePath", filePath);
    }

    public void setComment(String comment) {
        this.params.put("comment", comment);
    }

    public void setSite(String site) {
        this.params.put("site", site);
    }

    public void setSiteUrl(String siteUrl) {
        this.params.put("siteUrl", siteUrl);
    }

    public void setVenueName(String venueName) {
        this.params.put("venueName", venueName);
    }

    public void setVenueDescription(String venueDescription) {
        this.params.put("venueDescription", venueDescription);
    }

    public void setLatitude(float latitude) {
        this.params.put("latitude", Float.valueOf(latitude));
    }

    public void setLongitude(float longitude) {
        this.params.put("longitude", Float.valueOf(longitude));
    }

    public void setSilent(boolean silent) {
        this.params.put("silent", Boolean.valueOf(silent));
    }

    public void setPlatform(String platform) {
        this.params.put("platform", platform);
    }

    public void setInstallUrl(String installurl) {
        this.params.put("installurl", installurl);
    }

    public void setExecuteUrl(String executeurl) {
        this.params.put("executeurl", executeurl);
    }

    public void setMusicUrl(String musicUrl) {
        this.params.put("musicUrl", musicUrl);
    }

    public void setCallback(PlatformActionListener callback) {
        this.params.put("callback", callback);
    }

    public PlatformActionListener getCallback() {
        return (PlatformActionListener) R.forceCast(this.params.get("callback"));
    }

    public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
        this.params.put("customizeCallback", callback);
    }

    public ShareContentCustomizeCallback getShareContentCustomizeCallback() {
        return (ShareContentCustomizeCallback) R.forceCast(this.params.get("customizeCallback"));
    }

    public void setCustomerLogo(Bitmap logo, String label, OnClickListener ocl) {
        CustomerLogo cl = new CustomerLogo();
        cl.logo = logo;
        cl.label = label;
        cl.listener = ocl;
        ((ArrayList) R.forceCast(this.params.get("customers"))).add(cl);
    }

    public void disableSSOWhenAuthorize() {
        this.params.put("disableSSO", Boolean.valueOf(true));
    }

    public void setVideoUrl(String url) {
        this.params.put("url", url);
        this.params.put("shareType", Integer.valueOf(6));
    }

    @Deprecated
    public void setDialogMode() {
        this.params.put("dialogMode", Boolean.valueOf(true));
    }

    public void addHiddenPlatform(String platform) {
        ((HashMap) R.forceCast(this.params.get("hiddenPlatforms"))).put(platform, platform);
    }

    public void setViewToShare(View viewToShare) {
        try {
            this.params.put("viewToShare", BitmapHelper.captureView(viewToShare, viewToShare.getWidth(), viewToShare.getHeight()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void setImageArray(String[] imageArray) {
        this.params.put("imageArray", imageArray);
    }

    public void setShareToTencentWeiboWhenPerformingQQOrQZoneSharing() {
        this.params.put("isShareTencentWeibo", Boolean.valueOf(true));
    }

    public void setTheme(OnekeyShareTheme theme) {
        this.params.put("theme", Integer.valueOf(theme.getValue()));
    }

    public void show(Context context) {
        boolean z = false;
        HashMap<String, Object> shareParamsMap = new HashMap();
        shareParamsMap.putAll(this.params);
        ShareSDK.initSDK(context);
        ShareSDK.logDemoEvent(1, null);
        int iTheme = 0;
        try {
            iTheme = R.parseInt(String.valueOf(shareParamsMap.remove("theme")));
        } catch (Throwable th) {
        }
        OnekeyShareThemeImpl themeImpl = OnekeyShareTheme.fromValue(iTheme).getImpl();
        themeImpl.setShareParamsMap(shareParamsMap);
        themeImpl.setDialogMode(shareParamsMap.containsKey("dialogMode") ? ((Boolean) shareParamsMap.remove("dialogMode")).booleanValue() : false);
        if (shareParamsMap.containsKey("silent")) {
            z = ((Boolean) shareParamsMap.remove("silent")).booleanValue();
        }
        themeImpl.setSilent(z);
        themeImpl.setCustomerLogos((ArrayList) shareParamsMap.remove("customers"));
        themeImpl.setHiddenPlatforms((HashMap) shareParamsMap.remove("hiddenPlatforms"));
        themeImpl.setPlatformActionListener((PlatformActionListener) shareParamsMap.remove("callback"));
        themeImpl.setShareContentCustomizeCallback((ShareContentCustomizeCallback) shareParamsMap.remove("customizeCallback"));
        if (shareParamsMap.containsKey("disableSSO") && ((Boolean) shareParamsMap.remove("disableSSO")).booleanValue()) {
            themeImpl.disableSSO();
        }
        themeImpl.show(context);
    }
}
