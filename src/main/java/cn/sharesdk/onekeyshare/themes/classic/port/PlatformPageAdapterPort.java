package cn.sharesdk.onekeyshare.themes.classic.port;

import android.content.Context;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPage;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPageAdapter;
import com.mob.tools.utils.R;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlatformPageAdapterPort extends PlatformPageAdapter {
    private static final int DESIGN_LOGO_HEIGHT = 76;
    private static final int DESIGN_PADDING_TOP = 20;
    private static final int DESIGN_SCREEN_WIDTH_P = 720;
    private static final int DESIGN_SEP_LINE_WIDTH = 1;
    private static final int LINE_SIZE_P = 4;
    private static final int PAGE_SIZE_P = 12;

    public PlatformPageAdapterPort(PlatformPage page, ArrayList<Object> cells) {
        super(page, cells);
    }

    protected void calculateSize(Context context, ArrayList<Object> plats) {
        int i = 1;
        int screenWidth = R.getScreenWidth(context);
        this.lineSize = 4;
        float ratio = ((float) screenWidth) / 720.0f;
        this.sepLineWidth = (int) (1.0f * ratio);
        if (this.sepLineWidth >= 1) {
            i = this.sepLineWidth;
        }
        this.sepLineWidth = i;
        this.logoHeight = (int) (76.0f * ratio);
        this.paddingTop = (int) (20.0f * ratio);
        this.bottomHeight = (int) (52.0f * ratio);
        this.cellHeight = (screenWidth - (this.sepLineWidth * 3)) / 4;
        if (plats.size() <= this.lineSize) {
            this.panelHeight = this.cellHeight + this.sepLineWidth;
        } else if (plats.size() <= 12 - this.lineSize) {
            this.panelHeight = (this.cellHeight + this.sepLineWidth) * 2;
        } else {
            this.panelHeight = (this.cellHeight + this.sepLineWidth) * 3;
        }
    }

    protected void collectCells(ArrayList<Object> plats) {
        int count = plats.size();
        if (count < 12) {
            int lineCount = count / this.lineSize;
            if (count % this.lineSize != 0) {
                lineCount++;
            }
            this.cells = (Object[][]) Array.newInstance(Object.class, new int[]{1, this.lineSize * lineCount});
        } else {
            int pageCount = count / 12;
            if (count % 12 != 0) {
                pageCount++;
            }
            this.cells = (Object[][]) Array.newInstance(Object.class, new int[]{pageCount, 12});
        }
        for (int i = 0; i < count; i++) {
            int p = i / 12;
            this.cells[p][i - (p * 12)] = plats.get(i);
        }
    }
}
