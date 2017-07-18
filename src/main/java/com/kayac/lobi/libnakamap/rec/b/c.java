package com.kayac.lobi.libnakamap.rec.b;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.rec.a.b;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class c {
    private static final String a = c.class.getSimpleName();
    private static final b b = new b(a);
    private static final SimpleDateFormat c = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS", Locale.getDefault());
    private List<File> d = new ArrayList();
    private File e;
    private String f;
    private int g;

    public c() {
        a();
    }

    private boolean a(boolean[] zArr, String str) {
        return zArr[0] || !str.equals(this.f);
    }

    private File c(File file) {
        this.g++;
        File file2 = new File(file, this.f + "-" + this.g);
        b.b("add " + file2.getAbsolutePath() + " at " + this.d.size());
        this.d.add(file2);
        return file2;
    }

    public File a(File file) {
        if (this.d.size() == 0) {
            this.f = c.format(new Date());
            this.g = 0;
        }
        return c(file);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.File a(boolean[] r20) {
        /*
        r19 = this;
        r0 = r19;
        r2 = r0.d;
        r2 = r2.size();
        if (r2 != 0) goto L_0x000c;
    L_0x000a:
        r2 = 0;
    L_0x000b:
        return r2;
    L_0x000c:
        r0 = r19;
        r2 = r0.d;
        r2 = r2.size();
        r3 = 1;
        if (r2 != r3) goto L_0x0023;
    L_0x0017:
        r0 = r19;
        r2 = r0.d;
        r3 = 0;
        r2 = r2.get(r3);
        r2 = (java.io.File) r2;
        goto L_0x000b;
    L_0x0023:
        r0 = r19;
        r2 = r0.d;
        r7 = r2.size();
        r8 = java.lang.System.currentTimeMillis();
        r2 = b;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "concat ";
        r3 = r3.append(r4);
        r3 = r3.append(r7);
        r4 = " files";
        r3 = r3.append(r4);
        r3 = r3.toString();
        r2.a(r3);
        r0 = r19;
        r10 = r0.f;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r0 = r19;
        r2 = r0.d;	 Catch:{ IOException -> 0x009d }
        r4 = r2.iterator();	 Catch:{ IOException -> 0x009d }
    L_0x005e:
        r2 = r4.hasNext();	 Catch:{ IOException -> 0x009d }
        if (r2 == 0) goto L_0x00a4;
    L_0x0064:
        r2 = r4.next();	 Catch:{ IOException -> 0x009d }
        r2 = (java.io.File) r2;	 Catch:{ IOException -> 0x009d }
        r5 = r2.exists();	 Catch:{ IOException -> 0x009d }
        if (r5 != 0) goto L_0x0079;
    L_0x0070:
        r2 = b;	 Catch:{ IOException -> 0x009d }
        r3 = "A movie file is already removed.";
        r2.c(r3);	 Catch:{ IOException -> 0x009d }
        r2 = 0;
        goto L_0x000b;
    L_0x0079:
        r2 = r2.getAbsolutePath();	 Catch:{ NullPointerException -> 0x0091 }
        r2 = com.googlecode.mp4parser.authoring.container.mp4.MovieCreator.build(r2);	 Catch:{ NullPointerException -> 0x0091 }
        r3.add(r2);	 Catch:{ NullPointerException -> 0x0091 }
    L_0x0084:
        r0 = r19;
        r1 = r20;
        r2 = r0.a(r1, r10);	 Catch:{ IOException -> 0x009d }
        if (r2 == 0) goto L_0x005e;
    L_0x008e:
        r2 = 0;
        goto L_0x000b;
    L_0x0091:
        r2 = move-exception;
        r5 = b;	 Catch:{ IOException -> 0x009d }
        r6 = "failed to load a movie file";
        r5.c(r6);	 Catch:{ IOException -> 0x009d }
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);	 Catch:{ IOException -> 0x009d }
        goto L_0x0084;
    L_0x009d:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        r2 = 0;
        goto L_0x000b;
    L_0x00a4:
        r5 = new java.util.LinkedList;
        r5.<init>();
        r6 = new java.util.LinkedList;
        r6.<init>();
        r11 = r3.iterator();
    L_0x00b2:
        r2 = r11.hasNext();
        if (r2 == 0) goto L_0x01c4;
    L_0x00b8:
        r2 = r11.next();
        r2 = (com.googlecode.mp4parser.authoring.Movie) r2;
        r3 = 1;
        r4 = r2.getTracks();
        r12 = r4.iterator();
        r4 = r3;
    L_0x00c8:
        r3 = r12.hasNext();
        if (r3 == 0) goto L_0x011d;
    L_0x00ce:
        r3 = r12.next();
        r3 = (com.googlecode.mp4parser.authoring.Track) r3;
        r13 = r3.getHandler();
        r14 = "vide";
        r13 = r13.equals(r14);
        if (r13 == 0) goto L_0x047c;
    L_0x00e0:
        r13 = r3.getSyncSamples();
        if (r13 == 0) goto L_0x00e9;
    L_0x00e6:
        r13 = r13.length;
        if (r13 != 0) goto L_0x047c;
    L_0x00e9:
        r4 = b;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "concat ";
        r13 = r13.append(r14);
        r14 = r3.getHandler();
        r13 = r13.append(r14);
        r14 = " duration: ";
        r13 = r13.append(r14);
        r14 = r3.getDuration();
        r3 = r13.append(r14);
        r13 = " #DISPOSED# (no sync frames)";
        r3 = r3.append(r13);
        r3 = r3.toString();
        r4.a(r3);
        r4 = 0;
        r3 = r4;
    L_0x011b:
        r4 = r3;
        goto L_0x00c8;
    L_0x011d:
        if (r4 != 0) goto L_0x0127;
    L_0x011f:
        r2 = b;
        r3 = "too short movie file";
        r2.c(r3);
        goto L_0x00b2;
    L_0x0127:
        r2 = r2.getTracks();
        r3 = r2.iterator();
    L_0x012f:
        r2 = r3.hasNext();
        if (r2 == 0) goto L_0x00b2;
    L_0x0135:
        r2 = r3.next();
        r2 = (com.googlecode.mp4parser.authoring.Track) r2;
        r4 = r2.getHandler();
        r12 = "soun";
        r4 = r4.equals(r12);
        if (r4 == 0) goto L_0x014a;
    L_0x0147:
        r6.add(r2);
    L_0x014a:
        r4 = r2.getHandler();
        r12 = "vide";
        r4 = r4.equals(r12);
        if (r4 == 0) goto L_0x0159;
    L_0x0156:
        r5.add(r2);
    L_0x0159:
        r4 = r2.getTrackMetaData();
        r12 = b;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = "concat ";
        r13 = r13.append(r14);
        r14 = r2.getHandler();
        r13 = r13.append(r14);
        r14 = " duration: ";
        r13 = r13.append(r14);
        r14 = r2.getDuration();
        r2 = r13.append(r14);
        r13 = " timescale: ";
        r2 = r2.append(r13);
        r14 = r4.getTimescale();
        r2 = r2.append(r14);
        r13 = " size ";
        r2 = r2.append(r13);
        r14 = r4.getWidth();
        r2 = r2.append(r14);
        r13 = " x ";
        r2 = r2.append(r13);
        r14 = r4.getHeight();
        r2 = r2.append(r14);
        r4 = "";
        r2 = r2.append(r4);
        r2 = r2.toString();
        r12.a(r2);
        r0 = r19;
        r1 = r20;
        r2 = r0.a(r1, r10);
        if (r2 == 0) goto L_0x012f;
    L_0x01c1:
        r2 = 0;
        goto L_0x000b;
    L_0x01c4:
        r2 = 0;
        r4 = r2;
    L_0x01c6:
        r2 = r5.size();
        if (r4 >= r2) goto L_0x0282;
    L_0x01cc:
        r2 = r6.get(r4);
        r2 = (com.googlecode.mp4parser.authoring.Track) r2;
        r3 = r5.get(r4);
        r3 = (com.googlecode.mp4parser.authoring.Track) r3;
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r14 = r2.getDuration();
        r14 = (double) r14;
        r12 = r12 * r14;
        r2 = r2.getTrackMetaData();
        r14 = r2.getTimescale();
        r14 = (double) r14;
        r12 = r12 / r14;
        r14 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r16 = r3.getDuration();
        r0 = r16;
        r0 = (double) r0;
        r16 = r0;
        r14 = r14 * r16;
        r2 = r3.getTrackMetaData();
        r16 = r2.getTimescale();
        r0 = r16;
        r0 = (double) r0;
        r16 = r0;
        r14 = r14 / r16;
        r12 = r12 - r14;
        r14 = 0;
        r2 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r2 > 0) goto L_0x0229;
    L_0x020d:
        r2 = b;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r11 = "audio track is shorter than video track ";
        r3 = r3.append(r11);
        r3 = r3.append(r12);
        r3 = r3.toString();
        r2.a(r3);
    L_0x0225:
        r2 = r4 + 1;
        r4 = r2;
        goto L_0x01c6;
    L_0x0229:
        r2 = b;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r14 = "File ";
        r11 = r11.append(r14);
        r11 = r11.append(r4);
        r14 = ": add blank screen frames for ";
        r11 = r11.append(r14);
        r11 = r11.append(r12);
        r14 = "ms";
        r11 = r11.append(r14);
        r11 = r11.toString();
        r2.a(r11);
        r2 = r3.getSamples();
        r2 = r2.size();
        r11 = r3.getSampleDurations();
        r2 = r2 + -1;
        r14 = r11[r2];
        r14 = (double) r14;
        r3 = r3.getTrackMetaData();
        r16 = r3.getTimescale();
        r0 = r16;
        r0 = (double) r0;
        r16 = r0;
        r12 = r12 * r16;
        r12 = r12 + r14;
        r12 = (long) r12;
        r11[r2] = r12;
        r0 = r19;
        r1 = r20;
        r2 = r0.a(r1, r10);
        if (r2 == 0) goto L_0x0225;
    L_0x027f:
        r2 = 0;
        goto L_0x000b;
    L_0x0282:
        r11 = new com.googlecode.mp4parser.authoring.Movie;
        r11.<init>();
        r2 = r6.size();	 Catch:{ IOException -> 0x02c8 }
        if (r2 <= 0) goto L_0x02a1;
    L_0x028d:
        r3 = new com.googlecode.mp4parser.authoring.tracks.AppendTrack;	 Catch:{ IOException -> 0x02c8 }
        r2 = r6.size();	 Catch:{ IOException -> 0x02c8 }
        r2 = new com.googlecode.mp4parser.authoring.Track[r2];	 Catch:{ IOException -> 0x02c8 }
        r2 = r6.toArray(r2);	 Catch:{ IOException -> 0x02c8 }
        r2 = (com.googlecode.mp4parser.authoring.Track[]) r2;	 Catch:{ IOException -> 0x02c8 }
        r3.<init>(r2);	 Catch:{ IOException -> 0x02c8 }
        r11.addTrack(r3);	 Catch:{ IOException -> 0x02c8 }
    L_0x02a1:
        r2 = r5.size();	 Catch:{ IOException -> 0x02c8 }
        if (r2 <= 0) goto L_0x02bb;
    L_0x02a7:
        r3 = new com.googlecode.mp4parser.authoring.tracks.AppendTrack;	 Catch:{ IOException -> 0x02c8 }
        r2 = r5.size();	 Catch:{ IOException -> 0x02c8 }
        r2 = new com.googlecode.mp4parser.authoring.Track[r2];	 Catch:{ IOException -> 0x02c8 }
        r2 = r5.toArray(r2);	 Catch:{ IOException -> 0x02c8 }
        r2 = (com.googlecode.mp4parser.authoring.Track[]) r2;	 Catch:{ IOException -> 0x02c8 }
        r3.<init>(r2);	 Catch:{ IOException -> 0x02c8 }
        r11.addTrack(r3);	 Catch:{ IOException -> 0x02c8 }
    L_0x02bb:
        r0 = r19;
        r1 = r20;
        r2 = r0.a(r1, r10);
        if (r2 == 0) goto L_0x02cf;
    L_0x02c5:
        r2 = 0;
        goto L_0x000b;
    L_0x02c8:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        r2 = 0;
        goto L_0x000b;
    L_0x02cf:
        r3 = 0;
        r6 = 0;
        r4 = 0;
        r0 = r19;
        r1 = r20;
        r2 = r0.a(r1, r10);	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        if (r2 == 0) goto L_0x02f4;
    L_0x02dc:
        r2 = 0;
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x02e7;
    L_0x02e4:
        r4.close();	 Catch:{ IOException -> 0x02ee }
    L_0x02e7:
        if (r6 == 0) goto L_0x000b;
    L_0x02e9:
        r6.close();	 Catch:{ IOException -> 0x02ee }
        goto L_0x000b;
    L_0x02ee:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x000b;
    L_0x02f4:
        r2 = com.kayac.lobi.libnakamap.rec.b.a.a();	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r2 = r2.b();	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r0 = r19;
        r0.e = r2;	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r5 = new java.io.RandomAccessFile;	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r0 = r19;
        r2 = r0.e;	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r2 = r2.getAbsolutePath();	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r12 = "rw";
        r5.<init>(r2, r12);	 Catch:{ a -> 0x046b, FileNotFoundException -> 0x03f2, IOException -> 0x0412 }
        r4 = r5.getChannel();	 Catch:{ a -> 0x036b, FileNotFoundException -> 0x045b, IOException -> 0x044b }
        monitor-enter(r19);	 Catch:{ a -> 0x036b, FileNotFoundException -> 0x045b, IOException -> 0x044b }
        r2 = new com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;	 Catch:{ all -> 0x0368 }
        r2.<init>();	 Catch:{ all -> 0x0368 }
        r2 = r2.build(r11);	 Catch:{ all -> 0x0368 }
        r0 = r19;
        r1 = r20;
        r6 = r0.a(r1, r10);	 Catch:{ all -> 0x0368 }
        if (r6 == 0) goto L_0x0340;
    L_0x0327:
        r2 = 0;
        monitor-exit(r19);	 Catch:{ all -> 0x0368 }
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x0333;
    L_0x0330:
        r4.close();	 Catch:{ IOException -> 0x033a }
    L_0x0333:
        if (r5 == 0) goto L_0x000b;
    L_0x0335:
        r5.close();	 Catch:{ IOException -> 0x033a }
        goto L_0x000b;
    L_0x033a:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x000b;
    L_0x0340:
        r2.writeContainer(r4);	 Catch:{ all -> 0x0368 }
        monitor-exit(r19);	 Catch:{ all -> 0x0368 }
        monitor-enter(r19);	 Catch:{ a -> 0x036b, FileNotFoundException -> 0x045b, IOException -> 0x044b }
        r0 = r19;
        r1 = r20;
        r2 = r0.a(r1, r10);	 Catch:{ all -> 0x03e3 }
        if (r2 == 0) goto L_0x03b2;
    L_0x034f:
        r2 = 0;
        monitor-exit(r19);	 Catch:{ all -> 0x03e3 }
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x035b;
    L_0x0358:
        r4.close();	 Catch:{ IOException -> 0x0362 }
    L_0x035b:
        if (r5 == 0) goto L_0x000b;
    L_0x035d:
        r5.close();	 Catch:{ IOException -> 0x0362 }
        goto L_0x000b;
    L_0x0362:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x000b;
    L_0x0368:
        r2 = move-exception;
        monitor-exit(r19);	 Catch:{ all -> 0x0368 }
        throw r2;	 Catch:{ a -> 0x036b, FileNotFoundException -> 0x045b, IOException -> 0x044b }
    L_0x036b:
        r2 = move-exception;
        r18 = r2;
        r2 = r3;
        r3 = r18;
    L_0x0371:
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);	 Catch:{ all -> 0x0448 }
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x037e;
    L_0x037b:
        r4.close();	 Catch:{ IOException -> 0x03ed }
    L_0x037e:
        if (r5 == 0) goto L_0x0383;
    L_0x0380:
        r5.close();	 Catch:{ IOException -> 0x03ed }
    L_0x0383:
        r3 = b;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "END concat ";
        r4 = r4.append(r5);
        r4 = r4.append(r7);
        r5 = " files -- ";
        r4 = r4.append(r5);
        r6 = java.lang.System.currentTimeMillis();
        r6 = r6 - r8;
        r4 = r4.append(r6);
        r5 = "ms";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r3.a(r4);
        goto L_0x000b;
    L_0x03b2:
        r19.a();	 Catch:{ all -> 0x03e3 }
        r2 = com.kayac.lobi.libnakamap.rec.b.a.a();	 Catch:{ all -> 0x03e3 }
        r2 = r2.b();	 Catch:{ all -> 0x03e3 }
        r0 = r19;
        r6 = r0.e;	 Catch:{ all -> 0x03e3 }
        r6.renameTo(r2);	 Catch:{ all -> 0x03e3 }
        r6 = com.kayac.lobi.libnakamap.rec.b.a.a();	 Catch:{ all -> 0x03e3 }
        r10 = "MOVIE_STATUS_END_CAPTURING";
        r6.b(r10);	 Catch:{ all -> 0x03e3 }
        monitor-exit(r19);	 Catch:{ all -> 0x0474 }
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x03d8;
    L_0x03d5:
        r4.close();	 Catch:{ IOException -> 0x03de }
    L_0x03d8:
        if (r5 == 0) goto L_0x0383;
    L_0x03da:
        r5.close();	 Catch:{ IOException -> 0x03de }
        goto L_0x0383;
    L_0x03de:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x0383;
    L_0x03e3:
        r2 = move-exception;
    L_0x03e4:
        monitor-exit(r19);	 Catch:{ all -> 0x03e3 }
        throw r2;	 Catch:{ a -> 0x03e6, FileNotFoundException -> 0x0463, IOException -> 0x0453 }
    L_0x03e6:
        r2 = move-exception;
        r18 = r2;
        r2 = r3;
        r3 = r18;
        goto L_0x0371;
    L_0x03ed:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x0383;
    L_0x03f2:
        r2 = move-exception;
        r18 = r2;
        r2 = r3;
        r3 = r18;
    L_0x03f8:
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);	 Catch:{ all -> 0x0432 }
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x0405;
    L_0x0402:
        r4.close();	 Catch:{ IOException -> 0x040c }
    L_0x0405:
        if (r6 == 0) goto L_0x0383;
    L_0x0407:
        r6.close();	 Catch:{ IOException -> 0x040c }
        goto L_0x0383;
    L_0x040c:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x0383;
    L_0x0412:
        r2 = move-exception;
        r18 = r2;
        r2 = r3;
        r3 = r18;
    L_0x0418:
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);	 Catch:{ all -> 0x0432 }
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x0425;
    L_0x0422:
        r4.close();	 Catch:{ IOException -> 0x042c }
    L_0x0425:
        if (r6 == 0) goto L_0x0383;
    L_0x0427:
        r6.close();	 Catch:{ IOException -> 0x042c }
        goto L_0x0383;
    L_0x042c:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x0383;
    L_0x0432:
        r2 = move-exception;
    L_0x0433:
        r3 = 0;
        r0 = r19;
        r0.e = r3;
        if (r4 == 0) goto L_0x043d;
    L_0x043a:
        r4.close();	 Catch:{ IOException -> 0x0443 }
    L_0x043d:
        if (r6 == 0) goto L_0x0442;
    L_0x043f:
        r6.close();	 Catch:{ IOException -> 0x0443 }
    L_0x0442:
        throw r2;
    L_0x0443:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x0442;
    L_0x0448:
        r2 = move-exception;
        r6 = r5;
        goto L_0x0433;
    L_0x044b:
        r2 = move-exception;
        r6 = r5;
        r18 = r2;
        r2 = r3;
        r3 = r18;
        goto L_0x0418;
    L_0x0453:
        r2 = move-exception;
        r6 = r5;
        r18 = r2;
        r2 = r3;
        r3 = r18;
        goto L_0x0418;
    L_0x045b:
        r2 = move-exception;
        r6 = r5;
        r18 = r2;
        r2 = r3;
        r3 = r18;
        goto L_0x03f8;
    L_0x0463:
        r2 = move-exception;
        r6 = r5;
        r18 = r2;
        r2 = r3;
        r3 = r18;
        goto L_0x03f8;
    L_0x046b:
        r2 = move-exception;
        r5 = r6;
        r18 = r2;
        r2 = r3;
        r3 = r18;
        goto L_0x0371;
    L_0x0474:
        r3 = move-exception;
        r18 = r3;
        r3 = r2;
        r2 = r18;
        goto L_0x03e4;
    L_0x047c:
        r3 = r4;
        goto L_0x011b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.rec.b.c.a(boolean[]):java.io.File");
    }

    public void a(File file, String str) {
        int i;
        int i2 = 0;
        a();
        b.b("last capturing file: " + str);
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            String name = new File(str).getName();
            int lastIndexOf = name.lastIndexOf("-");
            this.f = name.substring(0, lastIndexOf);
            b.b("last capturing file: id " + this.f);
            try {
                i = Integer.parseInt(name.substring(lastIndexOf + 1));
                b.b("last capturing file: index " + i);
            } catch (Throwable e) {
                b.a(e);
                i = 0;
            }
        }
        if (!TextUtils.isEmpty(this.f) && i > 0) {
            b.a("last capturing file: loaded  id: " + this.f + " index: " + i);
            while (i2 < i) {
                if (c(file).exists()) {
                    i2++;
                } else {
                    a();
                    return;
                }
            }
        }
    }

    public boolean a() {
        boolean z = false;
        synchronized (this) {
            if (this.d.size() > 0) {
                z = true;
            }
            this.d.clear();
            this.f = null;
            this.g = 0;
        }
        return z;
    }

    public File b(File file, String str) {
        File file2 = new File(str);
        if (!file2.exists()) {
            return null;
        }
        if (this.d.size() == 0) {
            this.f = c.format(new Date());
            this.g = 0;
        }
        this.g++;
        File file3 = new File(file, this.f + "-" + this.g);
        file2.renameTo(file3);
        this.d.add(file3);
        file2.delete();
        return file3;
    }

    public boolean b(File file) {
        String absolutePath = file.getAbsolutePath();
        for (File absolutePath2 : this.d) {
            if (absolutePath2.getAbsolutePath().equals(absolutePath)) {
                return true;
            }
        }
        return this.e != null && this.e.getAbsolutePath().equals(absolutePath);
    }
}
