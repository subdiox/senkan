package com.mob.commons.authorize;

import com.mob.commons.MobProduct;

final class c implements MobProduct {
    final /* synthetic */ MobProduct a;

    c(MobProduct mobProduct) {
        this.a = mobProduct;
    }

    public String getProductAppkey() {
        return this.a.getProductAppkey();
    }

    public String getProductTag() {
        return this.a.getProductTag();
    }

    public int getSdkver() {
        return this.a.getSdkver();
    }
}
