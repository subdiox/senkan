package cn.sharesdk.framework.authorize;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.R;
import java.lang.reflect.Method;

public class RegisterView extends ResizeLayout {
    private TitleLayout a;
    private RelativeLayout b;
    private WebView c;
    private TextView d;

    public RegisterView(Context context) {
        super(context);
        a(context);
    }

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    private void a(Context context) {
        int bitmapRes;
        setBackgroundColor(-1);
        setOrientation(1);
        int b = b(context);
        this.a = new TitleLayout(context);
        try {
            bitmapRes = R.getBitmapRes(context, "ssdk_auth_title_back");
            if (bitmapRes > 0) {
                this.a.setBackgroundResource(bitmapRes);
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
        this.a.getBtnRight().setVisibility(8);
        bitmapRes = R.getStringRes(getContext(), "ssdk_weibo_oauth_regiseter");
        if (bitmapRes > 0) {
            this.a.getTvTitle().setText(bitmapRes);
        }
        addView(this.a);
        View imageView = new ImageView(context);
        int bitmapRes2 = R.getBitmapRes(context, "ssdk_logo");
        if (bitmapRes2 > 0) {
            imageView.setImageResource(bitmapRes2);
        }
        imageView.setScaleType(ScaleType.CENTER_INSIDE);
        imageView.setPadding(0, 0, R.dipToPx(context, 10), 0);
        imageView.setLayoutParams(new LayoutParams(-2, -1));
        imageView.setOnClickListener(new c(this));
        this.a.addView(imageView);
        this.b = new RelativeLayout(context);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, 0);
        layoutParams.weight = 1.0f;
        this.b.setLayoutParams(layoutParams);
        addView(this.b);
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        this.b.addView(linearLayout);
        this.d = new TextView(context);
        this.d.setLayoutParams(new LayoutParams(-1, 5));
        this.d.setBackgroundColor(-12929302);
        linearLayout.addView(this.d);
        this.d.setVisibility(8);
        this.c = new WebView(context);
        layoutParams = new LayoutParams(-1, -1);
        layoutParams.weight = 1.0f;
        this.c.setLayoutParams(layoutParams);
        WebChromeClient dVar = new d(this, b);
        if (VERSION.SDK_INT > 10 && VERSION.SDK_INT < 17) {
            try {
                Method method = this.c.getClass().getMethod("removeJavascriptInterface", new Class[]{String.class});
                method.setAccessible(true);
                method.invoke(this.c, new Object[]{"searchBoxJavaBridge_"});
            } catch (Throwable th2) {
                d.a().d(th2);
            }
        }
        this.c.setWebChromeClient(dVar);
        linearLayout.addView(this.c);
        this.c.requestFocus();
    }

    private int b(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (!(context instanceof Activity)) {
            return 0;
        }
        WindowManager windowManager = ((Activity) context).getWindowManager();
        if (windowManager == null) {
            return 0;
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public View a() {
        return this.a.getBtnBack();
    }

    public void a(boolean z) {
        this.a.setVisibility(z ? 8 : 0);
    }

    public WebView b() {
        return this.c;
    }

    public TitleLayout c() {
        return this.a;
    }

    public RelativeLayout d() {
        return this.b;
    }
}
