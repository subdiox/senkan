package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import com.mob.tools.FakeActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class OnekeySharePage extends FakeActivity {
    private OnekeyShareThemeImpl impl;

    public OnekeySharePage(OnekeyShareThemeImpl impl) {
        this.impl = impl;
    }

    protected final boolean isDialogMode() {
        return this.impl.dialogMode;
    }

    protected final HashMap<String, Object> getShareParamsMap() {
        return this.impl.shareParamsMap;
    }

    protected final boolean isSilent() {
        return this.impl.silent;
    }

    protected final ArrayList<CustomerLogo> getCustomerLogos() {
        return this.impl.customerLogos;
    }

    protected final HashMap<String, String> getHiddenPlatforms() {
        return this.impl.hiddenPlatforms;
    }

    protected final PlatformActionListener getCallback() {
        return this.impl.callback;
    }

    protected final ShareContentCustomizeCallback getCustomizeCallback() {
        return this.impl.customizeCallback;
    }

    protected final boolean isDisableSSO() {
        return this.impl.disableSSO;
    }

    protected final void shareSilently(Platform platform) {
        this.impl.shareSilently(platform);
    }

    protected final ShareParams formateShareData(Platform platform) {
        if (this.impl.formateShareData(platform)) {
            return this.impl.shareDataToShareParams(platform);
        }
        return null;
    }

    protected final boolean isUseClientToShare(Platform platform) {
        return this.impl.isUseClientToShare(platform);
    }
}
