package com.mob.commons.authorize;

import android.content.Context;
import com.mob.commons.MobProduct;
import com.mob.commons.a;
import com.mob.commons.f;

public final class DeviceAuthorizer {
    public static String authorize(Context context, MobProduct mobProduct) {
        f.a(context).a(mobProduct);
        a aVar = new a();
        return (mobProduct == null || !a.g(context)) ? aVar.a(context) : aVar.a(context, mobProduct);
    }

    public static String authorize(Context context, MobProduct mobProduct) {
        return authorize(context, new c(mobProduct));
    }
}
