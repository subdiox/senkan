package com.rekoo.libs.callback;

public interface ResultCallback {
    void onFail();

    void onSuccess(String str);
}
