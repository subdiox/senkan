package cn.sharesdk.onekeyshare.themes.classic.land;

import android.content.Context;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPage;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPageAdapter;
import com.mob.tools.utils.R;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlatformPageAdapterLand extends PlatformPageAdapter {
    private static final int DESIGN_CELL_WIDTH_L = 160;
    private static final int DESIGN_LOGO_HEIGHT = 76;
    private static final int DESIGN_PADDING_TOP = 20;
    private static final int DESIGN_SCREEN_WIDTH_L = 1280;
    private static final int DESIGN_SEP_LINE_WIDTH = 1;

    public PlatformPageAdapterLand(PlatformPage page, ArrayList<Object> cells) {
        super(page, cells);
    }

    protected void calculateSize(Context context, ArrayList<Object> arrayList) {
        int i = 1;
        int screenWidth = R.getScreenWidth(context);
        float ratio = ((float) screenWidth) / 1280.0f;
        this.lineSize = screenWidth / ((int) (160.0f * ratio));
        this.sepLineWidth = (int) (1.0f * ratio);
        if (this.sepLineWidth >= 1) {
            i = this.sepLineWidth;
        }
        this.sepLineWidth = i;
        this.logoHeight = (int) (76.0f * ratio);
        this.paddingTop = (int) (20.0f * ratio);
        this.bottomHeight = (int) (52.0f * ratio);
        this.cellHeight = (screenWidth - (this.sepLineWidth * 3)) / (this.lineSize - 1);
        this.panelHeight = this.cellHeight + this.sepLineWidth;
    }

    protected void collectCells(ArrayList<Object> plats) {
        int count = plats.size();
        if (count < this.lineSize) {
            int lineCount = count / this.lineSize;
            if (count % this.lineSize != 0) {
                lineCount++;
            }
            this.cells = (Object[][]) Array.newInstance(Object.class, new int[]{1, this.lineSize * lineCount});
        } else {
            int pageCount = count / this.lineSize;
            if (count % this.lineSize != 0) {
                pageCount++;
            }
            this.cells = (Object[][]) Array.newInstance(Object.class, new int[]{pageCount, this.lineSize});
        }
        for (int i = 0; i < count; i++) {
            int p = i / this.lineSize;
            this.cells[p][i - (this.lineSize * p)] = plats.get(i);
        }
    }
}
