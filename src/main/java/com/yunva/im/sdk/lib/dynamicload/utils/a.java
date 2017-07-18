package com.yunva.im.sdk.lib.dynamicload.utils;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class a {
    private static String a = "AssetsUtil";

    public static boolean a(Context context, String str) {
        try {
            String[] list = context.getAssets().list("");
            if (list == null || list.length <= 0) {
                return false;
            }
            for (String contains : list) {
                if (contains.contains(str)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2, boolean z) {
        Exception e;
        Throwable th;
        OutputStream outputStream = null;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream2 = null;
        try {
            File file2 = new File(file, str);
            if (file2.exists()) {
                if (z) {
                    file2.delete();
                } else {
                    if (null != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e2) {
                            com.yunva.im.sdk.lib.utils.a.c(a, "e:" + e2.toString());
                        }
                    }
                    if (null != null) {
                        outputStream2.close();
                    }
                    return true;
                }
            }
            InputStream open = context.getAssets().open(str);
            try {
                OutputStream fileOutputStream = new FileOutputStream(file2);
                if (open != null) {
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = open.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                    } catch (Exception e3) {
                        e2 = e3;
                        outputStream = fileOutputStream;
                        inputStream = open;
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = fileOutputStream;
                        inputStream = open;
                    }
                }
                if (open != null) {
                    try {
                        open.close();
                    } catch (Exception e22) {
                        com.yunva.im.sdk.lib.utils.a.c(a, "e:" + e22.toString());
                    }
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return true;
            } catch (Exception e4) {
                e22 = e4;
                inputStream = open;
                try {
                    com.yunva.im.sdk.lib.utils.a.c(a, "e:" + e22.toString());
                    throw new RuntimeException("yayavoice_for_assets_2017010001.jar is not found ...");
                } catch (Throwable th3) {
                    th = th3;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e5) {
                            com.yunva.im.sdk.lib.utils.a.c(a, "e:" + e5.toString());
                            throw th;
                        }
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                inputStream = open;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
        } catch (Exception e6) {
            e22 = e6;
            inputStream = null;
            com.yunva.im.sdk.lib.utils.a.c(a, "e:" + e22.toString());
            throw new RuntimeException("yayavoice_for_assets_2017010001.jar is not found ...");
        } catch (Throwable th5) {
            th = th5;
            inputStream = null;
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            throw th;
        }
    }

    public static boolean b(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            FileOutputStream openFileOutput = context.openFileOutput(str, 3);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read <= 0) {
                    open.close();
                    openFileOutput.close();
                    return true;
                }
                openFileOutput.write(bArr, 0, read);
            }
        } catch (Exception e) {
            com.yunva.im.sdk.lib.utils.a.c(a, "e:" + e.toString());
            return false;
        }
    }
}
