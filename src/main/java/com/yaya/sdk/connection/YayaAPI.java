package com.yaya.sdk.connection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class YayaAPI {
    private YayaAPI() {
    }

    public static int objectIndex(int codeState, Object obj, String methodName) throws YayaException {
        YayaLib existingState = YayaStateFactory.getExistingState(codeState);
        synchronized (existingState) {
            Class cls;
            Object obj2;
            Method method;
            int top = existingState.getTop();
            Object[] objArr = new Object[(top - 1)];
            if (obj instanceof Class) {
                cls = (Class) obj;
            } else {
                cls = obj.getClass();
            }
            Method[] methods = cls.getMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(methodName)) {
                    Class[] parameterTypes = methods[i].getParameterTypes();
                    if (parameterTypes.length == top - 1) {
                        int i2 = 0;
                        while (i2 < parameterTypes.length) {
                            try {
                                objArr[i2] = compareTypes(existingState, parameterTypes[i2], i2 + 2);
                                i2++;
                            } catch (Exception e) {
                                obj2 = null;
                            }
                        }
                        obj2 = 1;
                        if (obj2 != null) {
                            method = methods[i];
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            method = null;
            if (method == null) {
                throw new YayaException("Invalid method call. No such method.");
            }
            try {
                if (Modifier.isPublic(method.getModifiers())) {
                    method.setAccessible(true);
                }
                if (obj instanceof Class) {
                    obj2 = method.invoke(null, objArr);
                } else {
                    obj2 = method.invoke(obj, objArr);
                }
                if (obj2 == null) {
                    return 0;
                }
                existingState.pushObjectValue(obj2);
                return 1;
            } catch (Exception e2) {
                throw new YayaException(e2);
            }
        }
    }

    public static int classIndex(int codeState, Class clazz, String searchName) throws YayaException {
        synchronized (YayaStateFactory.getExistingState(codeState)) {
            if (checkField(codeState, clazz, searchName) != 0) {
                return 1;
            } else if (checkMethod(codeState, clazz, searchName) != 0) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    public static int javaNewInstance(int codeState, String className) throws YayaException {
        YayaLib existingState = YayaStateFactory.getExistingState(codeState);
        synchronized (existingState) {
            try {
                existingState.pushJavaObject(getObjInstance(existingState, Class.forName(className)));
            } catch (Exception e) {
                throw new YayaException(e);
            }
        }
        return 1;
    }

    public static int javaNew(int codeState, Class clazz) throws YayaException {
        YayaLib existingState = YayaStateFactory.getExistingState(codeState);
        synchronized (existingState) {
            existingState.pushJavaObject(getObjInstance(existingState, clazz));
        }
        return 1;
    }

    public static int javaLoadLib(int codeState, String className, String methodName) throws YayaException {
        synchronized (YayaStateFactory.getExistingState(codeState)) {
            try {
                Object invoke = Class.forName(className).getMethod(methodName, new Class[]{YayaLib.class}).invoke(null, new Object[]{r2});
                if (invoke == null || !(invoke instanceof Integer)) {
                    return 0;
                }
                int intValue = ((Integer) invoke).intValue();
                return intValue;
            } catch (Exception e) {
                throw new YayaException("Error on calling method. Library could not be loaded. " + e.getMessage());
            } catch (Exception e2) {
                throw new YayaException(e2);
            }
        }
    }

    private static Object getObjInstance(YayaLib L, Class clazz) throws YayaException {
        Object newInstance;
        synchronized (L) {
            int top = L.getTop();
            Object[] objArr = new Object[(top - 1)];
            Constructor[] constructors = clazz.getConstructors();
            Constructor constructor = null;
            for (int i = 0; i < constructors.length; i++) {
                Class[] parameterTypes = constructors[i].getParameterTypes();
                if (parameterTypes.length == top - 1) {
                    Object obj = 1;
                    int i2 = 0;
                    while (i2 < parameterTypes.length) {
                        try {
                            objArr[i2] = compareTypes(L, parameterTypes[i2], i2 + 2);
                            i2++;
                        } catch (Exception e) {
                            obj = null;
                        }
                    }
                    if (obj != null) {
                        constructor = constructors[i];
                        break;
                    }
                }
            }
            if (constructor == null) {
                throw new YayaException("Invalid method call. No such method.");
            }
            try {
                newInstance = constructor.newInstance(objArr);
                if (newInstance == null) {
                    throw new YayaException("Couldn't instantiate java Object");
                }
            } catch (Exception e2) {
                throw new YayaException(e2);
            }
        }
        return newInstance;
    }

    public static int checkField(int codeState, Object obj, String fieldName) throws YayaException {
        YayaLib existingState = YayaStateFactory.getExistingState(codeState);
        synchronized (existingState) {
            Class cls;
            if (obj instanceof Class) {
                cls = (Class) obj;
            } else {
                cls = obj.getClass();
            }
            try {
                Field field = cls.getField(fieldName);
                if (field == null) {
                    return 0;
                }
                try {
                    Object obj2 = field.get(obj);
                    if (obj == null) {
                        return 0;
                    }
                    existingState.pushObjectValue(obj2);
                    return 1;
                } catch (Exception e) {
                    return 0;
                }
            } catch (Exception e2) {
                return 0;
            }
        }
    }

    public static int checkMethod(int codeState, Object obj, String methodName) {
        synchronized (YayaStateFactory.getExistingState(codeState)) {
            if (obj instanceof Class) {
                obj = (Class) obj;
            } else {
                obj = obj.getClass();
            }
            Method[] methods = obj.getMethods();
            for (Method name : methods) {
                if (name.getName().equals(methodName)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    public static int createProxyObject(int codeState, String implem) throws YayaException {
        YayaLib existingState = YayaStateFactory.getExistingState(codeState);
        synchronized (existingState) {
            try {
                if (existingState.isTable(2)) {
                    existingState.pushJavaObject(existingState.getYayaObject(2).createProxy(implem));
                } else {
                    throw new YayaException("Parameter is not a table. Can't create proxy.");
                }
            } catch (Exception e) {
                throw new YayaException(e);
            }
        }
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object compareTypes(com.yaya.sdk.connection.YayaLib r7, java.lang.Class r8, int r9) throws com.yaya.sdk.connection.YayaException {
        /*
        r2 = 0;
        r0 = 0;
        r1 = 1;
        r3 = r7.isBoolean(r9);
        if (r3 == 0) goto L_0x0033;
    L_0x0009:
        r2 = r8.isPrimitive();
        if (r2 == 0) goto L_0x0029;
    L_0x000f:
        r2 = java.lang.Boolean.TYPE;
        if (r8 == r2) goto L_0x0031;
    L_0x0013:
        r1 = new java.lang.Boolean;
        r2 = r7.toBoolean(r9);
        r1.<init>(r2);
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x001f:
        if (r1 != 0) goto L_0x00d3;
    L_0x0021:
        r0 = new com.yaya.sdk.connection.YayaException;
        r1 = "Invalid Parameter.";
        r0.<init>(r1);
        throw r0;
    L_0x0029:
        r2 = java.lang.Boolean.class;
        r2 = r8.isAssignableFrom(r2);
        if (r2 == 0) goto L_0x0013;
    L_0x0031:
        r0 = r1;
        goto L_0x0013;
    L_0x0033:
        r3 = r7.type(r9);
        r4 = 4;
        if (r3 != r4) goto L_0x004a;
    L_0x003a:
        r3 = java.lang.String.class;
        r3 = r8.isAssignableFrom(r3);
        if (r3 != 0) goto L_0x0045;
    L_0x0042:
        r1 = r0;
        r0 = r2;
        goto L_0x001f;
    L_0x0045:
        r0 = r7.toString(r9);
        goto L_0x001f;
    L_0x004a:
        r3 = r7.isFunction(r9);
        if (r3 == 0) goto L_0x0060;
    L_0x0050:
        r3 = com.yaya.sdk.connection.YayaObject.class;
        r3 = r8.isAssignableFrom(r3);
        if (r3 != 0) goto L_0x005b;
    L_0x0058:
        r1 = r0;
        r0 = r2;
        goto L_0x001f;
    L_0x005b:
        r0 = r7.getYayaObject(r9);
        goto L_0x001f;
    L_0x0060:
        r3 = r7.isTable(r9);
        if (r3 == 0) goto L_0x0076;
    L_0x0066:
        r3 = com.yaya.sdk.connection.YayaObject.class;
        r3 = r8.isAssignableFrom(r3);
        if (r3 != 0) goto L_0x0071;
    L_0x006e:
        r1 = r0;
        r0 = r2;
        goto L_0x001f;
    L_0x0071:
        r0 = r7.getYayaObject(r9);
        goto L_0x001f;
    L_0x0076:
        r3 = r7.type(r9);
        r4 = 3;
        if (r3 != r4) goto L_0x008f;
    L_0x007d:
        r2 = new java.lang.Double;
        r4 = r7.toNumber(r9);
        r2.<init>(r4);
        r2 = com.yaya.sdk.connection.YayaLib.convertYayaNumber(r2, r8);
        if (r2 != 0) goto L_0x00d4;
    L_0x008c:
        r1 = r0;
        r0 = r2;
        goto L_0x001f;
    L_0x008f:
        r3 = r7.isUserdata(r9);
        if (r3 == 0) goto L_0x00c2;
    L_0x0095:
        r3 = r7.isObject(r9);
        if (r3 == 0) goto L_0x00b0;
    L_0x009b:
        r3 = r7.getObjectFromUserdata(r9);
        r4 = r3.getClass();
        r4 = r8.isAssignableFrom(r4);
        if (r4 != 0) goto L_0x00ad;
    L_0x00a9:
        r1 = r0;
        r0 = r2;
        goto L_0x001f;
    L_0x00ad:
        r0 = r3;
        goto L_0x001f;
    L_0x00b0:
        r3 = com.yaya.sdk.connection.YayaObject.class;
        r3 = r8.isAssignableFrom(r3);
        if (r3 != 0) goto L_0x00bc;
    L_0x00b8:
        r1 = r0;
        r0 = r2;
        goto L_0x001f;
    L_0x00bc:
        r0 = r7.getYayaObject(r9);
        goto L_0x001f;
    L_0x00c2:
        r0 = r7.isNil(r9);
        if (r0 == 0) goto L_0x00cb;
    L_0x00c8:
        r0 = r2;
        goto L_0x001f;
    L_0x00cb:
        r0 = new com.yaya.sdk.connection.YayaException;
        r1 = "Invalid Parameters.";
        r0.<init>(r1);
        throw r0;
    L_0x00d3:
        return r0;
    L_0x00d4:
        r0 = r2;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yaya.sdk.connection.YayaAPI.compareTypes(com.yaya.sdk.connection.YayaLib, java.lang.Class, int):java.lang.Object");
    }
}
