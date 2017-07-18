package cn.sharesdk.onekeyshare.themes.classic;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.CustomerLogo;
import cn.sharesdk.onekeyshare.OnekeySharePage;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import com.mob.tools.gui.MobViewPager;
import com.mob.tools.utils.R;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlatformPage extends OnekeySharePage {
    private Animation animHide;
    private Animation animShow;
    private Runnable beforeFinish;
    private boolean finished;
    private ClassicTheme impl;
    private LinearLayout llPanel;

    protected abstract PlatformPageAdapter newAdapter(ArrayList<Object> arrayList);

    public PlatformPage(OnekeyShareThemeImpl impl) {
        super(impl);
        this.impl = (ClassicTheme) R.forceCast(impl);
    }

    public void onCreate() {
        this.activity.getWindow().setBackgroundDrawable(new ColorDrawable(1275068416));
        initAnims();
        LinearLayout llPage = new LinearLayout(this.activity);
        llPage.setOrientation(1);
        this.activity.setContentView(llPage);
        TextView vTop = new TextView(this.activity);
        LayoutParams lp = new LayoutParams(-1, -2);
        lp.weight = 1.0f;
        vTop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PlatformPage.this.finish();
            }
        });
        llPage.addView(vTop, lp);
        this.llPanel = new LinearLayout(this.activity);
        this.llPanel.setOrientation(1);
        lp = new LayoutParams(-1, -2);
        this.llPanel.setAnimation(this.animShow);
        llPage.addView(this.llPanel, lp);
        MobViewPager mvp = new MobViewPager(this.activity);
        PlatformPageAdapter adapter = newAdapter(collectCells());
        this.llPanel.addView(mvp, new LayoutParams(-1, adapter.getPanelHeight()));
        IndicatorView vInd = new IndicatorView(this.activity);
        this.llPanel.addView(vInd, new LayoutParams(-1, adapter.getBottomHeight()));
        vInd.setScreenCount(adapter.getCount());
        vInd.onScreenChange(0, 0);
        adapter.setIndicator(vInd);
        mvp.setAdapter(adapter);
    }

    protected ArrayList<Object> collectCells() {
        int i = 0;
        ArrayList<Object> cells = new ArrayList();
        Platform[] platforms = ShareSDK.getPlatformList();
        if (platforms == null) {
            platforms = new Platform[0];
        }
        HashMap<String, String> hides = getHiddenPlatforms();
        if (hides == null) {
            hides = new HashMap();
        }
        int length = platforms.length;
        while (i < length) {
            Platform p = platforms[i];
            if (!hides.containsKey(p.getName())) {
                cells.add(p);
            }
            i++;
        }
        ArrayList<CustomerLogo> customers = getCustomerLogos();
        if (customers != null && customers.size() > 0) {
            cells.addAll(customers);
        }
        return cells;
    }

    public final void showEditPage(final Platform platform) {
        this.beforeFinish = new Runnable() {
            public void run() {
                boolean isSilent = PlatformPage.this.isSilent();
                boolean isCustomPlatform = platform instanceof CustomPlatform;
                boolean isUseClientToShare = PlatformPage.this.isUseClientToShare(platform);
                if (isSilent || isCustomPlatform || isUseClientToShare) {
                    PlatformPage.this.shareSilently(platform);
                    return;
                }
                ShareParams sp = PlatformPage.this.formateShareData(platform);
                if (sp != null) {
                    ShareSDK.logDemoEvent(3, null);
                    if (PlatformPage.this.getCustomizeCallback() != null) {
                        PlatformPage.this.getCustomizeCallback().onShare(platform, sp);
                    }
                    PlatformPage.this.impl.showEditPage(PlatformPage.this.activity, platform, sp);
                }
            }
        };
        finish();
    }

    public final void performCustomLogoClick(final View v, final CustomerLogo logo) {
        this.beforeFinish = new Runnable() {
            public void run() {
                logo.listener.onClick(v);
            }
        };
        finish();
    }

    private void initAnims() {
        this.animShow = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        this.animShow.setDuration(300);
        this.animHide = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        this.animHide.setDuration(300);
    }

    public boolean onFinish() {
        if (this.finished) {
            this.finished = false;
            return false;
        }
        this.animHide.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (PlatformPage.this.beforeFinish == null) {
                    ShareSDK.logDemoEvent(2, null);
                } else {
                    PlatformPage.this.beforeFinish.run();
                    PlatformPage.this.beforeFinish = null;
                }
                PlatformPage.this.finished = true;
                PlatformPage.this.finish();
            }
        });
        this.llPanel.clearAnimation();
        this.llPanel.setAnimation(this.animHide);
        this.llPanel.setVisibility(8);
        return true;
    }
}
