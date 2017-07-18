package cn.sharesdk.onekeyshare.themes.classic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeySharePage;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.land.FriendListPageLand;
import cn.sharesdk.onekeyshare.themes.classic.port.FriendListPagePort;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EditPage extends OnekeySharePage implements OnClickListener, TextWatcher, Runnable {
    protected AsyncImageView aivThumb;
    protected EditText etContent;
    private OnekeyShareThemeImpl impl;
    protected LinearLayout llBottom;
    protected LinearLayout llPage;
    protected int maxBodyHeight;
    protected Platform platform;
    protected RelativeLayout rlThumb;
    protected RelativeLayout rlTitle;
    protected ShareParams sp;
    protected ScrollView svContent;
    protected Bitmap thumb;
    protected TextView tvAt;
    protected TextView tvCancel;
    protected TextView tvShare;
    protected TextView tvTextCouter;
    protected XView xvRemove;

    public EditPage(OnekeyShareThemeImpl impl) {
        super(impl);
        this.impl = impl;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setShareParams(ShareParams sp) {
        this.sp = sp;
    }

    public void setActivity(Activity activity) {
        super.setActivity(activity);
        if (isDialogMode()) {
            System.err.println("Theme classic does not support dialog mode!");
        }
        activity.getWindow().setSoftInputMode(37);
    }

    public void onCreate() {
        this.activity.getWindow().setBackgroundDrawable(new ColorDrawable(-789517));
    }

    private void cancelAndFinish() {
        ShareSDK.logDemoEvent(5, this.platform);
        finish();
    }

    private void shareAndFinish() {
        int resId = R.getStringRes(this.activity, "ssdk_oks_sharing");
        if (resId > 0) {
            Toast.makeText(this.activity, resId, 0).show();
        }
        if (isDisableSSO()) {
            this.platform.SSOSetting(true);
        }
        this.platform.setPlatformActionListener(getCallback());
        this.platform.share(this.sp);
        finish();
    }

    private void showThumb(Bitmap pic) {
        PicViewerPage page = new PicViewerPage(this.impl);
        page.setImageBitmap(pic);
        page.show(this.activity, null);
    }

    private void removeThumb() {
        this.sp.setImageArray(null);
        this.sp.setImageData(null);
        this.sp.setImagePath(null);
        this.sp.setImageUrl(null);
    }

    private void showFriendList() {
        FriendListPage page;
        if (this.activity.getResources().getConfiguration().orientation == 1) {
            page = new FriendListPagePort(this.impl);
        } else {
            page = new FriendListPageLand(this.impl);
        }
        page.setPlatform(this.platform);
        page.showForResult(this.platform.getContext(), null, this);
    }

    public void onResult(HashMap<String, Object> data) {
        String atText = getJoinSelectedUser(data);
        if (!TextUtils.isEmpty(atText)) {
            this.etContent.append(atText);
        }
    }

    private String getJoinSelectedUser(HashMap<String, Object> data) {
        if (data == null || !data.containsKey("selected")) {
            return null;
        }
        ArrayList<String> selected = (ArrayList) data.get("selected");
        if ("FacebookMessenger".equals(((Platform) data.get("platform")).getName())) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = selected.iterator();
        while (it.hasNext()) {
            sb.append('@').append((String) it.next()).append(' ');
        }
        return sb.toString();
    }

    protected boolean isShowAtUserLayout(String platformName) {
        return "SinaWeibo".equals(platformName) || "TencentWeibo".equals(platformName) || "Facebook".equals(platformName) || "Twitter".equals(platformName);
    }

    public void onClick(View v) {
        if (v.equals(this.tvCancel)) {
            cancelAndFinish();
        } else if (v.equals(this.tvShare)) {
            this.sp.setText(this.etContent.getText().toString().trim());
            shareAndFinish();
        } else if (v.equals(this.aivThumb)) {
            showThumb(this.thumb);
        } else if (v.equals(this.xvRemove)) {
            this.maxBodyHeight = 0;
            this.rlThumb.setVisibility(8);
            this.llPage.measure(0, 0);
            onTextChanged(this.etContent.getText(), 0, 0, 0);
            removeThumb();
        } else if (v.equals(this.tvAt)) {
            showFriendList();
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.tvTextCouter.setText(String.valueOf(s.length()));
        if (this.maxBodyHeight == 0) {
            this.maxBodyHeight = (this.llPage.getHeight() - this.rlTitle.getHeight()) - this.llBottom.getHeight();
        }
        if (this.maxBodyHeight > 0) {
            this.svContent.post(this);
        }
    }

    public void run() {
        int height = this.svContent.getChildAt(0).getHeight();
        LayoutParams lp = (LayoutParams) R.forceCast(this.svContent.getLayoutParams());
        if (height > this.maxBodyHeight && lp.height != this.maxBodyHeight) {
            lp.height = this.maxBodyHeight;
            this.svContent.setLayoutParams(lp);
        } else if (height < this.maxBodyHeight && lp.height == this.maxBodyHeight) {
            lp.height = -2;
            this.svContent.setLayoutParams(lp);
        }
    }

    public void afterTextChanged(Editable s) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onPause() {
        DeviceHelper.getInstance(this.activity).hideSoftInput(getContentView());
        super.onPause();
    }
}
