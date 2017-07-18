package cn.sharesdk.onekeyshare.themes.classic.port;

import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPage;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPageAdapter;
import java.util.ArrayList;

public class PlatformPagePort extends PlatformPage {
    public PlatformPagePort(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    public void onCreate() {
        requestPortraitOrientation();
        super.onCreate();
    }

    protected PlatformPageAdapter newAdapter(ArrayList<Object> cells) {
        return new PlatformPageAdapterPort(this, cells);
    }
}
