package com.kayac.lobi.sdk.activity.invitation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.utils.URLUtils;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.SDKBridge;

public class InvitationActivity extends Activity {
    public static final String INVITED = "invited";
    protected static final String TAG = "[invitation]";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_invitation_activity);
        getWindow().setFormat(1);
        if (SDKBridge.shouldShowInvitationDialog()) {
            Uri data = getIntent().getData();
            if ("invited".equals(data.getAuthority())) {
                InvitationHandler.handleInvitation(this, URLUtils.parseQuery(data));
                return;
            } else {
                finish();
                return;
            }
        }
        final CustomDialog dialog = CustomDialog.createTextDialog(this, getString(R.string.lobi_to_use));
        dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                SDKBridge.showLoginForInvitationIfUserNotLoggedIn(InvitationActivity.this);
                InvitationActivity.this.finish();
            }
        });
        dialog.show();
    }
}
