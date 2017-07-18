package com.rekoo.libs.callback;

import com.rekoo.libs.entity.RKUser;

public interface GetUserInfoCallback {
    void onFail(String str);

    void onSuccess(RKUser rKUser);
}
