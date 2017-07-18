package cn.sharesdk.onekeyshare.themes.classic.port;

import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.FriendListPage;
import com.mob.tools.utils.R;

public class FriendListPagePort extends FriendListPage {
    private static final int DESIGN_SCREEN_WIDTH = 720;
    private static final int DESIGN_TITLE_HEIGHT = 96;

    public FriendListPagePort(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    protected float getRatio() {
        return ((float) R.getScreenWidth(this.activity)) / 720.0f;
    }

    protected int getDesignTitleHeight() {
        return DESIGN_TITLE_HEIGHT;
    }
}
