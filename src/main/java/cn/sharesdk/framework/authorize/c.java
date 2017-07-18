package cn.sharesdk.framework.authorize;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.R;

class c implements OnClickListener {
    final /* synthetic */ RegisterView a;

    c(RegisterView registerView) {
        this.a = registerView;
    }

    public void onClick(View v) {
        try {
            int stringRes = R.getStringRes(v.getContext(), "ssdk_website");
            Object obj = null;
            if (stringRes > 0) {
                obj = v.getResources().getString(stringRes);
            }
            if (!TextUtils.isEmpty(obj)) {
                v.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj)));
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }
}
