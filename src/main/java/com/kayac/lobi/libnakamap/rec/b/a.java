package com.kayac.lobi.libnakamap.rec.b;

import android.text.TextUtils;
import android.util.Pair;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.sdk.LobiCore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class a {
    private static final String a = a.class.getSimpleName();
    private static final com.kayac.lobi.libnakamap.rec.a.b b = new com.kayac.lobi.libnakamap.rec.a.b(a);
    private static a c = null;
    private c d = new c();
    private c e;
    private boolean[] f = new boolean[]{false};
    private boolean g = false;
    private Runnable h = new b(this);

    public static class a extends Exception {
    }

    public static final class b extends a {
    }

    public interface c {
        void onLoadVideo(File file);
    }

    private a() {
        try {
            File j = j();
            String str = (String) TransactionDatastore.getValue(Rec.LAST_CAPTURE_MOVIE);
            if (str != null) {
                if (Rec.MOVIE_STATUS_END_CAPTURING.equals((String) TransactionDatastore.getKKValue(Rec.MOVIE_STATUS_KEY1, str))) {
                    this.d.a(j, str);
                }
            }
        } catch (Throwable e) {
            com.kayac.lobi.libnakamap.rec.a.b.a(e);
        }
    }

    public static final a a() {
        if (c == null) {
            c = new a();
        }
        return c;
    }

    private File a(File file, String[] strArr) throws b {
        File file2 = null;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            file2 = new File(file, strArr[i]);
            if (file2.exists()) {
                if (file2.isDirectory()) {
                    continue;
                } else {
                    file2.delete();
                    if (!file2.mkdir()) {
                        throw new b();
                    }
                }
            } else if (!file2.mkdir()) {
                throw new b();
            }
            i++;
            file = file2;
        }
        if (file2 != null) {
            return file2;
        }
        throw new RuntimeException();
    }

    private File j() throws b {
        return a(LobiCore.sharedInstance().getContext().getExternalFilesDir(null), new String[]{"lobisdk", "movies"});
    }

    public void a(File file) {
        b.b("delete " + file.getAbsolutePath());
        file.delete();
        TransactionDatastore.deleteKKValue(Rec.MOVIE_STATUS_KEY1, file.getAbsolutePath());
    }

    public void a(String str) {
        try {
            File b = this.d.b(j(), str);
            TransactionDatastore.setValue(Rec.LAST_CAPTURE_MOVIE, b.getAbsolutePath());
            TransactionDatastore.setKKValue(Rec.MOVIE_STATUS_KEY1, b.getAbsolutePath(), Rec.MOVIE_STATUS_END_CAPTURING);
        } catch (b e) {
            e.printStackTrace();
        }
    }

    public boolean a(c cVar) {
        if (this.g) {
            return false;
        }
        this.e = cVar;
        this.f[0] = false;
        Executors.newSingleThreadExecutor().execute(this.h);
        return true;
    }

    public File b() throws a {
        File a = this.d.a(j());
        if (a == null) {
            throw new a();
        }
        TransactionDatastore.setValue(Rec.LAST_CAPTURE_MOVIE, a.getAbsolutePath());
        TransactionDatastore.setKKValue(Rec.MOVIE_STATUS_KEY1, a.getAbsolutePath(), Rec.MOVIE_STATUS_START_CAPTURING);
        f();
        return a;
    }

    public void b(File file) {
        TransactionDatastore.deleteKKValue(Rec.UPLOAD_MOVIES_KEY1, file.getAbsolutePath());
        TransactionDatastore.deleteKKValue(Rec.UPLOAD_RANKERS_MOVIES_KEY1, file.getAbsolutePath());
    }

    public void b(String str) {
        String str2 = (String) TransactionDatastore.getValue(Rec.LAST_CAPTURE_MOVIE);
        if (!TextUtils.isEmpty(str2)) {
            TransactionDatastore.setKKValue(Rec.MOVIE_STATUS_KEY1, str2, str);
        }
    }

    public boolean c() {
        return this.d.a();
    }

    public boolean c(File file) {
        return file.getAbsolutePath().equals((String) TransactionDatastore.getValue(Rec.LAST_UPLOAD_MOVIE));
    }

    public boolean d() {
        String str = (String) TransactionDatastore.getValue(Rec.LAST_CAPTURE_MOVIE);
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return false;
        }
        str = (String) TransactionDatastore.getKKValue(Rec.MOVIE_STATUS_KEY1, str);
        boolean z = Rec.MOVIE_STATUS_END_CAPTURING.equals(str) || Rec.MOVIE_STATUS_UPLOADING.equals(str) || Rec.MOVIE_STATUS_UPLOAD_COMPLETE.equals(str);
        return z;
    }

    public File[] e() throws b {
        return j().listFiles();
    }

    public boolean f() throws b {
        boolean z = false;
        for (File file : j().listFiles()) {
            if (!this.d.b(file)) {
                if (Rec.MOVIE_STATUS_UPLOADING.equals((String) TransactionDatastore.getKKValue(Rec.MOVIE_STATUS_KEY1, file.getAbsolutePath()))) {
                    boolean z2;
                    if (TextUtils.isEmpty((String) TransactionDatastore.getKKValue(Rec.UPLOAD_MOVIES_KEY1, file.getAbsolutePath()))) {
                        b.b("deleted " + file.getAbsolutePath() + "(invalid)");
                        a(file);
                        z2 = true;
                    } else {
                        z2 = z;
                    }
                    z = z2;
                } else {
                    b.b("deleted " + file.getAbsolutePath() + "(disposed)");
                    a(file);
                    z = true;
                }
            }
        }
        return z;
    }

    public List<Pair<File, String>> g() throws b {
        List<Pair<File, String>> arrayList = new ArrayList();
        for (File file : e()) {
            if (Rec.MOVIE_STATUS_UPLOADING.equals((String) TransactionDatastore.getKKValue(Rec.MOVIE_STATUS_KEY1, file.getAbsolutePath()))) {
                String str = (String) TransactionDatastore.getKKValue(Rec.UPLOAD_MOVIES_KEY1, file.getAbsolutePath());
                if (!TextUtils.isEmpty(str)) {
                    arrayList.add(new Pair(file, str));
                    b.b("uploading " + file.getAbsolutePath() + " -> " + str);
                }
            }
        }
        return arrayList;
    }

    public void h() {
        this.e = null;
        this.f[0] = true;
    }
}
