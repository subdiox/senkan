package cn.sharesdk.framework;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

class q extends TextView {
    final /* synthetic */ ImageView a;
    final /* synthetic */ TitleLayout b;

    q(TitleLayout titleLayout, Context context, ImageView imageView) {
        this.b = titleLayout;
        this.a = imageView;
        super(context);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.a.setVisibility(visibility);
    }
}
