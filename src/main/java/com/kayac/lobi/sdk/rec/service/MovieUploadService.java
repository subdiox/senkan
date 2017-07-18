package com.kayac.lobi.sdk.rec.service;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.b.a;
import com.kayac.lobi.libnakamap.rec.c.f;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.migration.datastore.NakamapDatastore.Key;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class MovieUploadService extends IntentService {
    public MovieUploadService() {
        super("MovieUploadService");
    }

    private final void a(String str, boolean z) {
        if (z) {
            Intent intent = new Intent(LobiRec.ACTION_RANKERS_MOVIE_UPLOADED_ERROR);
            intent.putExtra(Key.FILE, new File(str).getName());
            sendBroadcast(intent);
        } else {
            sendBroadcast(new Intent(LobiRec.ACTION_MOVIE_UPLOADED_ERROR));
        }
        a.a().a(new File(str));
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("EXTRA_FILE_PATH")) {
                if (intent.hasExtra("EXTRA_UPLOAD_URL")) {
                    CharSequence stringExtra = intent.getStringExtra("EXTRA_FILE_PATH");
                    Object stringExtra2 = intent.getStringExtra("EXTRA_UPLOAD_URL");
                    if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
                        boolean z = !TextUtils.isEmpty((String) TransactionDatastore.getKKValue(Rec.UPLOAD_RANKERS_MOVIES_KEY1, stringExtra, ""));
                        File file = new File(stringExtra);
                        if (file.exists()) {
                            int i = 0;
                            int i2 = 1;
                            int i3 = 0;
                            long j = 0;
                            while (j < file.length()) {
                                int i4;
                                int i5;
                                int i6;
                                long j2;
                                long length = file.length() - (5242880 * ((long) i3));
                                if (length >= 5242880) {
                                    length = 5242880;
                                }
                                if (z) {
                                    Intent intent2 = new Intent(LobiRec.ACTION_RANKERS_MOVIE_UPLOAD_PROGRESS);
                                    intent2.putExtra(Key.FILE, file.getName());
                                    intent2.putExtra("maxIndex", file.length() / 5242880);
                                    intent2.putExtra("currentIndex", i3);
                                    sendBroadcast(intent2);
                                }
                                int[] iArr = new int[1];
                                f.a(file, stringExtra2, file.length(), length, ((long) i3) * 5242880, new a(this, iArr, new JSONObject[1]));
                                switch (iArr[0]) {
                                    case 0:
                                        j += length;
                                        i4 = 0;
                                        i5 = i2;
                                        i6 = i3 + 1;
                                        j2 = j;
                                        break;
                                    case 100:
                                    case APICallback.RESPONSE_ERROR /*101*/:
                                        if (i <= 10) {
                                            i5 = i + 1;
                                            i4 = i2 * 2;
                                            if (300 < i4) {
                                                i4 = 300;
                                            }
                                            int i7;
                                            try {
                                                TimeUnit.SECONDS.sleep((long) i4);
                                                i6 = i3;
                                                j2 = j;
                                                i7 = i5;
                                                i5 = i4;
                                                i4 = i7;
                                                break;
                                            } catch (Throwable e) {
                                                b.a(e);
                                                i6 = i3;
                                                j2 = j;
                                                i7 = i5;
                                                i5 = i4;
                                                i4 = i7;
                                                break;
                                            }
                                        }
                                        a(stringExtra, z);
                                        return;
                                    default:
                                        a(stringExtra, z);
                                        return;
                                }
                                i = i4;
                                i2 = i5;
                                i3 = i6;
                                j = j2;
                            }
                            TransactionDatastore.setKKValue(Rec.MOVIE_STATUS_KEY1, stringExtra, Rec.MOVIE_STATUS_UPLOAD_COMPLETE);
                            if (z) {
                                Intent intent3 = new Intent(LobiRec.ACTION_RANKERS_MOVIE_UPLOADED);
                                intent3.putExtra(Key.FILE, new File(stringExtra).getName());
                                sendBroadcast(intent3);
                                return;
                            }
                            sendBroadcast(new Intent(LobiRec.ACTION_MOVIE_UPLOADED));
                            return;
                        }
                        a.a().b(file);
                    }
                }
            }
        }
    }
}
