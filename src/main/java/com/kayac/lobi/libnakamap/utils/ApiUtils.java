package com.kayac.lobi.libnakamap.utils;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UserContact.Order;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import java.util.List;
import java.util.Map;

public class ApiUtils {
    public static final String REQUEST_KEY_SERVICE_EMAIL = "email";
    public static final String REQUEST_KEY_SERVICE_FACEBOOK = "facebook";
    public static final String REQUEST_KEY_SERVICE_TWITTER = "twitter";

    public static UserValue checkNullValue(UserValue userValue) {
        return userValue != null ? userValue : AccountDatastore.getCurrentUser();
    }

    public static UserValue checkNullDefaultUser(UserValue userValue) {
        return userValue != null ? userValue : AccountDatastore.getDefaultUser();
    }

    public static GroupDetailValue checkNullValue(GroupDetailValue groupDetailValue, String gid, String userUid) {
        return groupDetailValue != null ? groupDetailValue : TransactionDatastore.getGroupDetail(gid, userUid);
    }

    public static List<UserContactValue> checkNullValue(List<UserContactValue> userContacts, String userUid, Order order) {
        return userContacts != null ? userContacts : TransactionDatastore.getUserContacts(userUid, order);
    }

    public static Map<String, String> completeParamsWithPreparedExIdIfNecessary(Map<String, String> params) {
        LobiCore.sharedInstance();
        if (LobiCore.isStrictExidEnabled()) {
            String paramExid = (String) params.get("encrypted_ex_id");
            String paramIv = (String) params.get("iv");
            String preparedExid = LobiCore.getPreparedExternalId();
            String preparedIv = LobiCore.getPreparedIv();
            if (!(TextUtils.isEmpty(preparedExid) || TextUtils.isEmpty(preparedIv) || (!TextUtils.isEmpty(paramExid) && !TextUtils.isEmpty(paramIv)))) {
                params.put("encrypted_ex_id", preparedExid);
                params.put("iv", preparedIv);
            }
        }
        return params;
    }
}
