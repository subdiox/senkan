package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.CustomerLogo;
import com.mob.tools.gui.ViewPagerAdapter;
import com.mob.tools.utils.R;
import java.util.ArrayList;

public abstract class PlatformPageAdapter extends ViewPagerAdapter implements OnClickListener {
    public static final int DESIGN_BOTTOM_HEIGHT = 52;
    protected static final int MIN_CLICK_INTERVAL = 1000;
    protected int bottomHeight;
    protected int cellHeight;
    protected Object[][] cells;
    private long lastClickTime;
    protected int lineSize;
    protected int logoHeight;
    protected int paddingTop;
    private PlatformPage page;
    protected int panelHeight;
    protected int sepLineWidth;
    private IndicatorView vInd;

    protected abstract void calculateSize(Context context, ArrayList<Object> arrayList);

    protected abstract void collectCells(ArrayList<Object> arrayList);

    public PlatformPageAdapter(PlatformPage page, ArrayList<Object> cells) {
        this.page = page;
        if (cells != null && !cells.isEmpty()) {
            calculateSize(page.getContext(), cells);
            collectCells(cells);
        }
    }

    public int getBottomHeight() {
        return this.bottomHeight;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    public int getCount() {
        return this.cells == null ? 0 : this.cells.length;
    }

    public void setIndicator(IndicatorView view) {
        this.vInd = view;
    }

    public void onScreenChange(int currentScreen, int lastScreen) {
        if (this.vInd != null) {
            this.vInd.setScreenCount(getCount());
            this.vInd.onScreenChange(currentScreen, lastScreen);
        }
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createPanel(parent.getContext());
        }
        refreshPanel((LinearLayout[]) R.forceCast(((LinearLayout) R.forceCast(convertView)).getTag()), this.cells[index]);
        return convertView;
    }

    private View createPanel(Context context) {
        LinearLayout llPanel = new LinearLayout(context);
        llPanel.setOrientation(1);
        llPanel.setBackgroundColor(-855310);
        int lineCount = this.panelHeight / this.cellHeight;
        LinearLayout[] llCells = new LinearLayout[(this.lineSize * lineCount)];
        llPanel.setTag(llCells);
        int cellBack = R.getBitmapRes(context, "ssdk_oks_classic_platform_cell_back");
        for (int i = 0; i < lineCount; i++) {
            LinearLayout llLine = new LinearLayout(context);
            llPanel.addView(llLine, new LayoutParams(-1, this.cellHeight));
            for (int j = 0; j < this.lineSize; j++) {
                llCells[(this.lineSize * i) + j] = new LinearLayout(context);
                llCells[(this.lineSize * i) + j].setBackgroundResource(cellBack);
                llCells[(this.lineSize * i) + j].setOrientation(1);
                LayoutParams lp = new LayoutParams(-1, this.cellHeight);
                lp.weight = 1.0f;
                llLine.addView(llCells[(this.lineSize * i) + j], lp);
                if (j < this.lineSize - 1) {
                    llLine.addView(new View(context), new LayoutParams(this.sepLineWidth, -1));
                }
            }
            llPanel.addView(new View(context), new LayoutParams(-1, this.sepLineWidth));
        }
        for (LinearLayout llCell : llCells) {
            ImageView ivLogo = new ImageView(context);
            ivLogo.setScaleType(ScaleType.CENTER_INSIDE);
            lp = new LayoutParams(-1, this.logoHeight);
            lp.topMargin = this.paddingTop;
            llCell.addView(ivLogo, lp);
            TextView tvName = new TextView(context);
            tvName.setTextColor(-10197916);
            tvName.setTextSize(2, 14.0f);
            tvName.setGravity(17);
            lp = new LayoutParams(-1, -2);
            lp.weight = 1.0f;
            llCell.addView(tvName, lp);
        }
        return llPanel;
    }

    private void refreshPanel(LinearLayout[] llCells, Object[] logos) {
        int cellBack = R.getBitmapRes(this.page.getContext(), "ssdk_oks_classic_platform_cell_back");
        int disableBack = R.getBitmapRes(this.page.getContext(), "ssdk_oks_classic_platfrom_cell_back_nor");
        for (int i = 0; i < logos.length; i++) {
            ImageView ivLogo = (ImageView) R.forceCast(llCells[i].getChildAt(0));
            TextView tvName = (TextView) R.forceCast(llCells[i].getChildAt(1));
            if (logos[i] == null) {
                ivLogo.setVisibility(4);
                tvName.setVisibility(4);
                llCells[i].setBackgroundResource(disableBack);
                llCells[i].setOnClickListener(null);
            } else {
                ivLogo.setVisibility(0);
                tvName.setVisibility(0);
                llCells[i].setBackgroundResource(cellBack);
                llCells[i].setOnClickListener(this);
                llCells[i].setTag(logos[i]);
                if (logos[i] instanceof CustomerLogo) {
                    CustomerLogo logo = (CustomerLogo) R.forceCast(logos[i]);
                    if (logo.logo != null) {
                        ivLogo.setImageBitmap(logo.logo);
                    } else {
                        ivLogo.setImageBitmap(null);
                    }
                    if (logo.label != null) {
                        tvName.setText(logo.label);
                    } else {
                        tvName.setText("");
                    }
                } else {
                    String name = ((Platform) R.forceCast(logos[i])).getName().toLowerCase();
                    int resId = R.getBitmapRes(ivLogo.getContext(), "ssdk_oks_classic_" + name);
                    if (resId > 0) {
                        ivLogo.setImageResource(resId);
                    } else {
                        ivLogo.setImageBitmap(null);
                    }
                    resId = R.getStringRes(tvName.getContext(), "ssdk_" + name);
                    if (resId > 0) {
                        tvName.setText(resId);
                    } else {
                        tvName.setText("");
                    }
                }
            }
        }
    }

    public void onClick(View v) {
        long time = System.currentTimeMillis();
        if (time - this.lastClickTime >= 1000) {
            this.lastClickTime = time;
            if (v.getTag() instanceof CustomerLogo) {
                this.page.performCustomLogoClick(v, (CustomerLogo) R.forceCast(v.getTag()));
                return;
            }
            this.page.showEditPage((Platform) R.forceCast(v.getTag()));
        }
    }
}
