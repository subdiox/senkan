package com.rekoo.libs.callback;

import com.rekoo.libs.entity.ProduceTransform;

public interface ProduceTransformCallback {
    void onFailed(int i);

    void onSuccess(ProduceTransform produceTransform);
}
