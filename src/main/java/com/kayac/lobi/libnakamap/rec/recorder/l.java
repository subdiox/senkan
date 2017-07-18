package com.kayac.lobi.libnakamap.rec.recorder;

class l implements Runnable {
    final /* synthetic */ j a;

    l(j jVar) {
        this.a = jVar;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r6 = this;
        r4 = 0;
        r0 = com.kayac.lobi.libnakamap.rec.recorder.j.b;
        r1 = "AudioRecordTask begin";
        r0.a(r1);
        r0 = r6.a;	 Catch:{ RuntimeException -> 0x00bc }
        r1 = r0.m;	 Catch:{ RuntimeException -> 0x00bc }
        monitor-enter(r1);	 Catch:{ RuntimeException -> 0x00bc }
        r0 = r6.a;	 Catch:{ all -> 0x00e3 }
        r0 = r0.j;	 Catch:{ all -> 0x00e3 }
        if (r0 != 0) goto L_0x0023;
    L_0x001a:
        r0 = r6.a;	 Catch:{ InterruptedException -> 0x00dd }
        r0 = r0.m;	 Catch:{ InterruptedException -> 0x00dd }
        r0.wait();	 Catch:{ InterruptedException -> 0x00dd }
    L_0x0023:
        monitor-exit(r1);	 Catch:{ all -> 0x00e3 }
        r0 = r6.a;	 Catch:{ RuntimeException -> 0x00bc }
        r1 = r0.A;	 Catch:{ RuntimeException -> 0x00bc }
        monitor-enter(r1);	 Catch:{ RuntimeException -> 0x00bc }
        r0 = r6.a;	 Catch:{ all -> 0x00f1 }
        r0 = r0.e;	 Catch:{ all -> 0x00f1 }
        if (r0 == 0) goto L_0x0040;
    L_0x0033:
        r0 = r6.a;	 Catch:{ all -> 0x00f1 }
        r0 = r0.e;	 Catch:{ all -> 0x00f1 }
        r0 = r0.h();	 Catch:{ all -> 0x00f1 }
        r0.a();	 Catch:{ all -> 0x00f1 }
    L_0x0040:
        monitor-exit(r1);	 Catch:{ all -> 0x00f1 }
    L_0x0041:
        r0 = 1;
        android.os.Process.setThreadPriority(r0);	 Catch:{ RuntimeException -> 0x00bc }
        r0 = r6.a;	 Catch:{ RuntimeException -> 0x00bc }
        r1 = r0.A;	 Catch:{ RuntimeException -> 0x00bc }
        monitor-enter(r1);	 Catch:{ RuntimeException -> 0x00bc }
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r0 = r0.e;	 Catch:{ all -> 0x0125 }
        if (r0 == 0) goto L_0x00f4;
    L_0x0054:
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r0 = r0.e;	 Catch:{ all -> 0x0125 }
        r0.d();	 Catch:{ all -> 0x0125 }
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r0 = r0.h;	 Catch:{ all -> 0x0125 }
        if (r0 == 0) goto L_0x00ff;
    L_0x0065:
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r2 = r0.x;	 Catch:{ all -> 0x0125 }
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 <= 0) goto L_0x0089;
    L_0x006f:
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r0 = r0.e;	 Catch:{ all -> 0x0125 }
        r0 = r0.h();	 Catch:{ all -> 0x0125 }
        r2 = r6.a;	 Catch:{ all -> 0x0125 }
        r2 = r2.x;	 Catch:{ all -> 0x0125 }
        r0.b(r2);	 Catch:{ all -> 0x0125 }
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r2 = 0;
        r0.x = r2;	 Catch:{ all -> 0x0125 }
    L_0x0089:
        monitor-exit(r1);	 Catch:{ all -> 0x0125 }
        r0 = com.kayac.lobi.libnakamap.rec.recorder.j.b;	 Catch:{ RuntimeException -> 0x00bc }
        r1 = "audio loop wait";
        r0.b(r1);	 Catch:{ RuntimeException -> 0x00bc }
        r0 = r6.a;	 Catch:{ RuntimeException -> 0x00bc }
        r1 = r0.n;	 Catch:{ RuntimeException -> 0x00bc }
        monitor-enter(r1);	 Catch:{ RuntimeException -> 0x00bc }
        r0 = r6.a;	 Catch:{ all -> 0x012e }
        r0 = r0.k;	 Catch:{ all -> 0x012e }
        if (r0 != 0) goto L_0x00ab;
    L_0x00a2:
        r0 = r6.a;	 Catch:{ InterruptedException -> 0x0128 }
        r0 = r0.n;	 Catch:{ InterruptedException -> 0x0128 }
        r0.wait();	 Catch:{ InterruptedException -> 0x0128 }
    L_0x00ab:
        r0 = r6.a;	 Catch:{ all -> 0x012e }
        r2 = 0;
        r0.k = r2;	 Catch:{ all -> 0x012e }
        monitor-exit(r1);	 Catch:{ all -> 0x012e }
        r0 = com.kayac.lobi.libnakamap.rec.recorder.j.b;	 Catch:{ RuntimeException -> 0x00bc }
        r1 = "audio loop again";
        r0.b(r1);	 Catch:{ RuntimeException -> 0x00bc }
        goto L_0x0041;
    L_0x00bc:
        r0 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r0);	 Catch:{ all -> 0x00e6 }
        r0 = com.kayac.lobi.libnakamap.rec.recorder.j.b;	 Catch:{ all -> 0x00e6 }
        r1 = "recording is aborted. (audio thread)";
        r0.c(r1);	 Catch:{ all -> 0x00e6 }
        r0 = r6.a;	 Catch:{ all -> 0x00e6 }
        r0.C();	 Catch:{ all -> 0x00e6 }
        r0 = r6.a;	 Catch:{ all -> 0x00e6 }
        r0.g();	 Catch:{ all -> 0x00e6 }
        r0 = com.kayac.lobi.libnakamap.rec.recorder.j.b;
        r1 = "AudioRecordTask end";
        r0.a(r1);
    L_0x00dc:
        return;
    L_0x00dd:
        r0 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r0);	 Catch:{ all -> 0x00e3 }
        goto L_0x0023;
    L_0x00e3:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x00e3 }
        throw r0;	 Catch:{ RuntimeException -> 0x00bc }
    L_0x00e6:
        r0 = move-exception;
        r1 = com.kayac.lobi.libnakamap.rec.recorder.j.b;
        r2 = "AudioRecordTask end";
        r1.a(r2);
        throw r0;
    L_0x00f1:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x00f1 }
        throw r0;	 Catch:{ RuntimeException -> 0x00bc }
    L_0x00f4:
        monitor-exit(r1);	 Catch:{ all -> 0x0125 }
        r0 = com.kayac.lobi.libnakamap.rec.recorder.j.b;
        r1 = "AudioRecordTask end";
        r0.a(r1);
        goto L_0x00dc;
    L_0x00ff:
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r2 = r0.y;	 Catch:{ all -> 0x0125 }
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 <= 0) goto L_0x0089;
    L_0x0109:
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r0 = r0.e;	 Catch:{ all -> 0x0125 }
        r0 = r0.h();	 Catch:{ all -> 0x0125 }
        r2 = r6.a;	 Catch:{ all -> 0x0125 }
        r2 = r2.y;	 Catch:{ all -> 0x0125 }
        r0.a(r2);	 Catch:{ all -> 0x0125 }
        r0 = r6.a;	 Catch:{ all -> 0x0125 }
        r2 = 0;
        r0.y = r2;	 Catch:{ all -> 0x0125 }
        goto L_0x0089;
    L_0x0125:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0125 }
        throw r0;	 Catch:{ RuntimeException -> 0x00bc }
    L_0x0128:
        r0 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r0);	 Catch:{ all -> 0x012e }
        goto L_0x00ab;
    L_0x012e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x012e }
        throw r0;	 Catch:{ RuntimeException -> 0x00bc }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.rec.recorder.l.run():void");
    }
}
