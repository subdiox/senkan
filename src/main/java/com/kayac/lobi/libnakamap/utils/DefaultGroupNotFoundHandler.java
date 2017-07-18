package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.gms.wallet.WalletConstants;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;

public class DefaultGroupNotFoundHandler {
    public static final void handleError(final Activity activity, int statusCode, String uid, String gid) {
        if (statusCode == WalletConstants.ERROR_CODE_INVALID_PARAMETERS && !TextUtils.isEmpty(gid)) {
            TransactionDatastore.deleteGroup(gid, uid);
            for (UserValue user : AccountDatastore.getUsers()) {
                TransactionDatastore.deleteGroupDetail(gid, user.getUid());
            }
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, R.string.lobi_this_group, 0).show();
                    PathRouter.removePathsGreaterThan("/");
                }
            });
        } else if (statusCode == 403) {
            TransactionDatastore.deleteGroup(gid, uid);
            TransactionDatastore.deleteGroupDetail(gid, uid);
            PathRouter.removePathsGreaterThan("/");
        }
    }
}
