package com.rekoo.libs.platform.ui.floating;

import android.content.Context;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.rekoo.libs.utils.ResUtils;

public class SecondFloatView extends ImageView {
    private Context context;
    private int defaultResource;
    private boolean isRight = false;
    private PreferebceManager mPreferenceManager = null;
    private WindowManager windowManager;
    private LayoutParams windowManagerParams;

    public SecondFloatView(Context context, LayoutParams windowManagerParams, WindowManager windowManager) {
        super(context);
        this.context = context;
        this.defaultResource = ResUtils.getDrawable("float_image", context);
        this.windowManager = windowManager;
        this.windowManagerParams = windowManagerParams;
        this.mPreferenceManager = new PreferebceManager(context);
        windowManagerParams.type = 99;
        windowManagerParams.format = 1;
        windowManagerParams.flags = ((windowManagerParams.flags | 8) | 32) | 1024;
        windowManagerParams.gravity = 51;
        windowManagerParams.x = (int) this.mPreferenceManager.getFloatX();
        windowManagerParams.y = (int) this.mPreferenceManager.getFloatY();
        windowManagerParams.width = -2;
        windowManagerParams.height = -2;
        this.isRight = this.mPreferenceManager.isDisplayRight();
        setImageResource(this.defaultResource);
    }
}
