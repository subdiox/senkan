package com.kayac.lobi.libnakamap.utils;

import android.os.Bundle;
import com.kayac.lobi.libnakamap.components.CustomTextView;
import com.kayac.lobi.libnakamap.components.CustomTextView.OnTextLinkClickedListener;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.utils.NakamapLinkify.ClickableURLSpan;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import java.util.regex.Pattern;

public class CustomTextViewUtil {
    private static final String LOBI_INVITATION = "https?://lobi.co/invite/\\w+";
    private static final String NAKAMAP_INVITATION = "https?://nakamap.com/invite/\\w+";

    public static OnTextLinkClickedListener getOnTextLinkClickedListener(final String path, final String title) {
        return new OnTextLinkClickedListener() {
            public void onTextLinkClicked(CustomTextView editText, ClickableURLSpan span, CharSequence link) {
                if (Pattern.matches(CustomTextViewUtil.LOBI_INVITATION, span.getURL())) {
                    String string = link.toString() + "?platfrom=android&is_sdk=1";
                    Bundle bundle = new Bundle();
                    bundle.putString(PathRouter.PATH, path);
                    bundle.putString(InvitationWebViewActivity.EXTRA_URL, string);
                    bundle.putString("EXTRA_TITLE", title);
                    PathRouter.startPath(bundle);
                    return;
                }
                span.openInBrowser(editText);
            }
        };
    }
}
