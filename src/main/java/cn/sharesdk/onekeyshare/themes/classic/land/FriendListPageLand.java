package cn.sharesdk.onekeyshare.themes.classic.land;

import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.FriendListPage;
import com.mob.tools.utils.R;

public class FriendListPageLand extends FriendListPage {
    private static final int DESIGN_SCREEN_WIDTH = 1280;
    private static final int DESIGN_TITLE_HEIGHT = 70;

    public FriendListPageLand(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    protected float getRatio() {
        return ((float) R.getScreenWidth(this.activity)) / 1280.0f;
    }

    protected int getDesignTitleHeight() {
        return DESIGN_TITLE_HEIGHT;
    }
}
