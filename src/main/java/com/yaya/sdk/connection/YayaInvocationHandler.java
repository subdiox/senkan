package com.yaya.sdk.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class YayaInvocationHandler implements InvocationHandler {
    private YayaObject obj;

    public YayaInvocationHandler(YayaObject obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws YayaException {
        Object obj = null;
        synchronized (this.obj.L) {
            YayaObject field = this.obj.getField(method.getName());
            if (field.isNil()) {
            } else {
                Class returnType = method.getReturnType();
                if (returnType.equals(Void.class) || returnType.equals(Void.TYPE)) {
                    field.call(args, 0);
                } else {
                    obj = field.call(args, 1)[0];
                    if (obj != null && (obj instanceof Double)) {
                        obj = YayaLib.convertYayaNumber((Double) obj, returnType);
                    }
                }
            }
        }
        return obj;
    }
}
