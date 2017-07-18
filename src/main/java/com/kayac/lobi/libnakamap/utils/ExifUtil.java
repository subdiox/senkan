package com.kayac.lobi.libnakamap.utils;

import android.os.Build.VERSION;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class ExifUtil {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap rotateBitmap(java.lang.String r10, android.graphics.Bitmap r11) {
        /*
        if (r10 != 0) goto L_0x0003;
    L_0x0002:
        return r11;
    L_0x0003:
        if (r11 != 0) goto L_0x0007;
    L_0x0005:
        r11 = 0;
        goto L_0x0002;
    L_0x0007:
        r8 = getExifOrientation(r10);	 Catch:{ IOException -> 0x0039 }
        r0 = 1;
        if (r8 == r0) goto L_0x0002;
    L_0x000e:
        r5 = new android.graphics.Matrix;	 Catch:{ IOException -> 0x0039 }
        r5.<init>();	 Catch:{ IOException -> 0x0039 }
        switch(r8) {
            case 2: goto L_0x0017;
            case 3: goto L_0x0033;
            case 4: goto L_0x003e;
            case 5: goto L_0x004b;
            case 6: goto L_0x0058;
            case 7: goto L_0x005e;
            case 8: goto L_0x006b;
            default: goto L_0x0016;
        };	 Catch:{ IOException -> 0x0039 }
    L_0x0016:
        goto L_0x0002;
    L_0x0017:
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.setScale(r0, r1);	 Catch:{ IOException -> 0x0039 }
    L_0x001e:
        r1 = 0;
        r2 = 0;
        r3 = r11.getWidth();	 Catch:{ OutOfMemoryError -> 0x0071 }
        r4 = r11.getHeight();	 Catch:{ OutOfMemoryError -> 0x0071 }
        r6 = 1;
        r0 = r11;
        r9 = android.graphics.Bitmap.createBitmap(r0, r1, r2, r3, r4, r5, r6);	 Catch:{ OutOfMemoryError -> 0x0071 }
        r11.recycle();	 Catch:{ OutOfMemoryError -> 0x0071 }
        r11 = r9;
        goto L_0x0002;
    L_0x0033:
        r0 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r5.setRotate(r0);	 Catch:{ IOException -> 0x0039 }
        goto L_0x001e;
    L_0x0039:
        r7 = move-exception;
        r7.printStackTrace();
        goto L_0x0002;
    L_0x003e:
        r0 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r5.setRotate(r0);	 Catch:{ IOException -> 0x0039 }
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.postScale(r0, r1);	 Catch:{ IOException -> 0x0039 }
        goto L_0x001e;
    L_0x004b:
        r0 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r5.setRotate(r0);	 Catch:{ IOException -> 0x0039 }
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.postScale(r0, r1);	 Catch:{ IOException -> 0x0039 }
        goto L_0x001e;
    L_0x0058:
        r0 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r5.setRotate(r0);	 Catch:{ IOException -> 0x0039 }
        goto L_0x001e;
    L_0x005e:
        r0 = -1028390912; // 0xffffffffc2b40000 float:-90.0 double:NaN;
        r5.setRotate(r0);	 Catch:{ IOException -> 0x0039 }
        r0 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r1 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.postScale(r0, r1);	 Catch:{ IOException -> 0x0039 }
        goto L_0x001e;
    L_0x006b:
        r0 = -1028390912; // 0xffffffffc2b40000 float:-90.0 double:NaN;
        r5.setRotate(r0);	 Catch:{ IOException -> 0x0039 }
        goto L_0x001e;
    L_0x0071:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ IOException -> 0x0039 }
        goto L_0x0002;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.utils.ExifUtil.rotateBitmap(java.lang.String, android.graphics.Bitmap):android.graphics.Bitmap");
    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;
        try {
            if (VERSION.SDK_INT >= 5) {
                Class<?> exifClass = Class.forName("android.media.ExifInterface");
                Object exifInstance = exifClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{src});
                orientation = ((Integer) exifClass.getMethod("getAttributeInt", new Class[]{String.class, Integer.TYPE}).invoke(exifInstance, new Object[]{(String) exifClass.getField("TAG_ORIENTATION").get(null), Integer.valueOf(1)})).intValue();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
        } catch (InstantiationException e5) {
            e5.printStackTrace();
        } catch (IllegalAccessException e6) {
            e6.printStackTrace();
        } catch (InvocationTargetException e7) {
            e7.printStackTrace();
        } catch (NoSuchFieldException e8) {
            e8.printStackTrace();
        }
        return orientation;
    }
}
