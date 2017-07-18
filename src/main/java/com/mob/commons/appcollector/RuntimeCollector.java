package com.mob.commons.appcollector;

import android.content.Context;
import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.net.APIDef.GetRanking;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.mob.commons.a;
import com.mob.commons.g;
import com.mob.tools.MobLog;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;

public class RuntimeCollector {
    private static final String a = (VERSION.SDK_INT >= 16 ? "^u\\d+_a\\d+" : "^app_\\d+");
    private static RuntimeCollector b;
    private Context c;
    private DeviceHelper d;

    private RuntimeCollector(Context context) {
        this.c = context.getApplicationContext();
        this.d = DeviceHelper.getInstance(context);
    }

    private ArrayList<HashMap<String, Object>> a(HashMap<String, String>[][] hashMapArr, ArrayList<long[]> arrayList) {
        ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList(hashMapArr.length);
        for (HashMap<String, String>[] hashMapArr2 : hashMapArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("runtimes", Long.valueOf(0));
            hashMap.put("fg", Integer.valueOf(0));
            hashMap.put("bg", Integer.valueOf(0));
            hashMap.put("empty", Integer.valueOf(0));
            arrayList2.add(hashMap);
            int length = hashMapArr2.length - 1;
            while (length >= 0) {
                if (hashMapArr2[length] != null) {
                    hashMap.put("runtimes", Long.valueOf((length == 0 ? 0 : ((long[]) arrayList.get(length))[1]) + ((Long) R.forceCast(hashMap.get("runtimes"), Long.valueOf(0))).longValue()));
                    if ("fg".equals(hashMapArr2[length].get("pcy"))) {
                        hashMap.put("fg", Integer.valueOf(((Integer) R.forceCast(hashMap.get("fg"), Integer.valueOf(0))).intValue() + 1));
                    } else if ("bg".equals(hashMapArr2[length].get("pcy"))) {
                        hashMap.put("bg", Integer.valueOf(((Integer) R.forceCast(hashMap.get("bg"), Integer.valueOf(0))).intValue() + 1));
                    } else {
                        hashMap.put("empty", Integer.valueOf(((Integer) R.forceCast(hashMap.get("empty"), Integer.valueOf(0))).intValue() + 1));
                    }
                    hashMap.put("pkg", hashMapArr2[length].get("pkg"));
                    hashMap.put("name", hashMapArr2[length].get("name"));
                    hashMap.put("version", hashMapArr2[length].get("version"));
                }
                length--;
            }
        }
        return arrayList2;
    }

    private HashMap<String, Integer> a(ArrayList<ArrayList<HashMap<String, String>>> arrayList) {
        HashMap<String, Integer> hashMap = new HashMap();
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Iterator it2 = ((ArrayList) it.next()).iterator();
            int i2 = i;
            while (it2.hasNext()) {
                HashMap hashMap2 = (HashMap) it2.next();
                String str = ((String) hashMap2.get("pkg")) + ":" + ((String) hashMap2.get("version"));
                if (!hashMap.containsKey(str)) {
                    hashMap.put(str, Integer.valueOf(i2));
                    i2++;
                }
            }
            i = i2;
        }
        return hashMap;
    }

    private void a() {
        Thread eVar = new e(this);
        eVar.setPriority(1);
        eVar.start();
    }

    private void a(String str, ArrayList<ArrayList<HashMap<String, String>>> arrayList, ArrayList<long[]> arrayList2) {
        try {
            HashMap e = e();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), "utf-8"));
            String readLine = bufferedReader.readLine();
            for (int i = 0; i < 7; i++) {
                readLine = bufferedReader.readLine();
            }
            ArrayList arrayList3 = new ArrayList();
            for (readLine = 
/*
Method generation error in method: com.mob.commons.appcollector.RuntimeCollector.a(java.lang.String, java.util.ArrayList, java.util.ArrayList):void
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r1_2 'readLine' java.lang.String) = (r1_1 'readLine' java.lang.String), (r1_3 'readLine' java.lang.String) binds: {(r1_3 'readLine' java.lang.String)=B:4:0x001e, (r1_1 'readLine' java.lang.String)=B:2:?} in method: com.mob.commons.appcollector.RuntimeCollector.a(java.lang.String, java.util.ArrayList, java.util.ArrayList):void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:277)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 19 more

*/

            private void a(String str, HashMap<String, String[]> hashMap, ArrayList<HashMap<String, String>> arrayList) {
                String[] split = str.replaceAll(" +", " ").split(" ");
                if (split != null && split.length >= 10) {
                    Object obj = split[split.length - 1];
                    if (split[split.length - 2].matches(a) && !GetRanking.ORIGIN_TOP.equals(obj)) {
                        String[] strArr = (String[]) hashMap.get(obj);
                        if (strArr != null) {
                            HashMap hashMap2 = new HashMap();
                            hashMap2.put("pkg", obj);
                            hashMap2.put("name", strArr[0]);
                            hashMap2.put("version", strArr[1]);
                            hashMap2.put("pcy", split[split.length - 3]);
                            arrayList.add(hashMap2);
                        }
                    }
                }
            }

            private void a(ArrayList<HashMap<String, Object>> arrayList, ArrayList<long[]> arrayList2) {
                HashMap hashMap = new HashMap();
                hashMap.put("type", "APP_RUNTIMES");
                hashMap.put("list", arrayList);
                hashMap.put("datatime", Long.valueOf(a.b(this.c)));
                hashMap.put("recordat", Long.valueOf(((long[]) arrayList2.get(0))[0]));
                long j = 0;
                for (int i = 1; i < arrayList2.size(); i++) {
                    j += ((long[]) arrayList2.get(i))[1];
                }
                hashMap.put("sdk_runtime_len", Long.valueOf(j));
                hashMap.put("top_count", Integer.valueOf(arrayList2.size()));
                g.a(this.c).a(hashMap);
            }

            private boolean a(String str) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                a(str, arrayList, arrayList2);
                a(a(a(a(arrayList), arrayList), arrayList2), arrayList2);
                return b(str);
            }

            private HashMap<String, String>[][] a(HashMap<String, Integer> hashMap, ArrayList<ArrayList<HashMap<String, String>>> arrayList) {
                HashMap[][] hashMapArr = (HashMap[][]) Array.newInstance(HashMap.class, new int[]{hashMap.size(), arrayList.size()});
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ArrayList arrayList2 = (ArrayList) arrayList.get(i);
                    int size2 = arrayList2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        HashMap hashMap2 = (HashMap) arrayList2.get(i2);
                        hashMapArr[((Integer) hashMap.get(((String) hashMap2.get("pkg")) + ":" + ((String) hashMap2.get("version")))).intValue()][i] = hashMap2;
                    }
                }
                return hashMapArr;
            }

            private void b() {
                OutputStream outputStream;
                OutputStream outputStream2;
                Throwable th;
                try {
                    File file = new File(R.getCacheRoot(this.c), "comm/dbs/.plst");
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    String absolutePath = file.getAbsolutePath();
                    long c = c();
                    Process exec = Runtime.getRuntime().exec("sh");
                    Object obj = 1;
                    long j = c;
                    OutputStream outputStream3 = exec.getOutputStream();
                    Process process = exec;
                    while (true) {
                        try {
                            if (a.c(this.c)) {
                                String str;
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                long b = a.b(this.c);
                                outputStream3.write(("top -d 0 -n 1 | grep -E -v 'root|shell|system' >> " + absolutePath + " && echo \"" + "======================" + "\" >> " + absolutePath + IOUtils.LINE_SEPARATOR_UNIX).getBytes("ascii"));
                                if (obj != null) {
                                    str = "echo \"" + b + "_0\" >> " + absolutePath + IOUtils.LINE_SEPARATOR_UNIX;
                                    obj = null;
                                } else {
                                    str = "echo \"" + b + "_" + a.d(this.c) + "\" >> " + absolutePath + IOUtils.LINE_SEPARATOR_UNIX;
                                }
                                outputStream3.write(str.getBytes("ascii"));
                                outputStream3.flush();
                                if (b >= j) {
                                    outputStream3.write("exit\n".getBytes("ascii"));
                                    outputStream3.flush();
                                    outputStream3.close();
                                    process.waitFor();
                                    process.destroy();
                                    if (a(absolutePath)) {
                                        long d = d();
                                        if (d <= 0) {
                                            d = j;
                                        }
                                        b = d;
                                        obj = 1;
                                    } else {
                                        b = j;
                                    }
                                    try {
                                        Process exec2 = Runtime.getRuntime().exec("sh");
                                        try {
                                            exec = exec2;
                                            outputStream = exec2.getOutputStream();
                                            c = b;
                                            Thread.sleep((long) (a.d(this.c) * 1000));
                                            outputStream2 = outputStream;
                                            j = c;
                                            outputStream3 = outputStream2;
                                            process = exec;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            exec = exec2;
                                            outputStream = outputStream3;
                                            c = b;
                                            MobLog.getInstance().d(th);
                                            outputStream2 = outputStream;
                                            j = c;
                                            outputStream3 = outputStream2;
                                            process = exec;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        outputStream = outputStream3;
                                        exec = process;
                                        c = b;
                                        MobLog.getInstance().d(th);
                                        outputStream2 = outputStream;
                                        j = c;
                                        outputStream3 = outputStream2;
                                        process = exec;
                                    }
                                }
                            }
                            outputStream2 = outputStream3;
                            c = j;
                            outputStream = outputStream2;
                            exec = process;
                            try {
                                Thread.sleep((long) (a.d(this.c) * 1000));
                                outputStream2 = outputStream;
                                j = c;
                                outputStream3 = outputStream2;
                                process = exec;
                            } catch (Throwable th4) {
                                th = th4;
                                MobLog.getInstance().d(th);
                                outputStream2 = outputStream;
                                j = c;
                                outputStream3 = outputStream2;
                                process = exec;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            outputStream2 = outputStream3;
                            c = j;
                            outputStream = outputStream2;
                            exec = process;
                            MobLog.getInstance().d(th);
                            outputStream2 = outputStream;
                            j = c;
                            outputStream3 = outputStream2;
                            process = exec;
                        }
                    }
                } catch (Throwable th6) {
                }
            }

            private boolean b(String str) {
                try {
                    File file = new File(str);
                    file.delete();
                    file.createNewFile();
                    return true;
                } catch (Throwable th) {
                    MobLog.getInstance().d(th);
                    return false;
                }
            }

            private long c() {
                long b = a.b(this.c);
                try {
                    File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulplt");
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (file.exists()) {
                        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                        long readLong = dataInputStream.readLong();
                        dataInputStream.close();
                        return readLong;
                    }
                    file.createNewFile();
                    d();
                    return b + NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL;
                } catch (Throwable th) {
                    MobLog.getInstance().d(th);
                    return b + NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL;
                }
            }

            private long d() {
                long b = a.b(this.c) + NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL;
                try {
                    File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulplt");
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
                    dataOutputStream.writeLong(b);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    return b;
                } catch (Throwable th) {
                    MobLog.getInstance().d(th);
                    return 0;
                }
            }

            private HashMap<String, String[]> e() {
                ArrayList installedApp = this.d.getInstalledApp(false);
                HashMap<String, String[]> hashMap = new HashMap();
                Iterator it = installedApp.iterator();
                while (it.hasNext()) {
                    hashMap.put((String) ((HashMap) it.next()).get("pkg"), new String[]{(String) r0.get("name"), (String) ((HashMap) it.next()).get("version")});
                }
                return hashMap;
            }

            public static synchronized void startCollector(Context context) {
                synchronized (RuntimeCollector.class) {
                    if (b == null) {
                        b = new RuntimeCollector(context);
                        b.a();
                    }
                }
            }

            public static synchronized void startCollector(Context context, String str) {
                synchronized (RuntimeCollector.class) {
                    startCollector(context);
                }
            }
        }
