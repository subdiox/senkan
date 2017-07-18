package com.rekoo.libs.callback;

import com.rekoo.libs.entity.Transform;

public interface TransformCallback {
    void onFail(int i);

    void onSuccess(Transform transform);
}
