package com.kayac.lobi.libnakamap.rec.nougat;

import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.sdk.rec.unity.RecBroadcastReceiver;
import com.kayac.lobi.sdk.utils.UnityUtils;

public class LobiRecNougatUnity {
    private LobiRecNougatUnity() {
    }

    public static boolean getStickyRecording() {
        return c.n();
    }

    public static boolean hasMovie() {
        return c.l();
    }

    public static boolean isCapturing() {
        return c.j();
    }

    public static void isMicEnabled(String str, String str2) {
        UnityUtils.unitySendMessage(str, str2, "1");
    }

    public static boolean isPaused() {
        return c.i();
    }

    public static boolean isPrepareRecording() {
        return c.h();
    }

    public static boolean isSupported() {
        return c.k();
    }

    public static void openLobiPlayActivity() {
        c.o();
    }

    public static void openLobiPlayActivity(String str) {
        c.b(str);
    }

    public static void openLobiPlayActivity(String str, String str2, boolean z, String str3) {
        c.a(str, str2, z, str3);
    }

    public static void openLobiPlayActivityWithEventFields(String str) {
        c.a(str);
    }

    public static void openPostVideoActivity(String str, String str2, long j, String str3) {
        c.a(str, str2, j, str3);
    }

    public static void openPostVideoActivity(String str, String str2, long j, String str3, String str4) {
        c.a(str, str2, j, str3, str4);
    }

    public static void pauseCapturing() {
        c.d();
    }

    public static void prepare() {
        c.b();
    }

    public static void registerDismissingPostVideoViewNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY, str, str2);
    }

    public static void registerMovieCreatedErrorNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_CREATED_ERROR, str, str2);
    }

    public static void registerMovieCreatedNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_CREATED, str, str2);
    }

    public static void registerMovieUploadedErrorNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_UPLOADED_ERROR, str, str2);
    }

    public static void registerMovieUploadedNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_UPLOADED, str, str2);
    }

    public static boolean removeUnretainedVideo() {
        return c.m();
    }

    public static void reset() {
        c.g();
    }

    public static void resumeCapturing() {
        c.e();
    }

    public static void setStickyRecording(boolean z) {
        c.a(z);
    }

    public static void setup() {
        c.a(UnityUtils.getUnityCurrentActivity());
    }

    public static boolean shouldUseRecAfterNougat() {
        return c.a();
    }

    public static void showDownloadLobiDialog() {
        c.p();
    }

    public static void startCapturing() {
        c.c();
    }

    public static void stopCapturing() {
        c.f();
    }

    public static void unregisterDismissingPostVideoViewNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY);
    }

    public static void unregisterMovieCreatedErrorNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_CREATED_ERROR);
    }

    public static void unregisterMovieCreatedNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_CREATED);
    }

    public static void unregisterMovieUploadedErrorNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_UPLOADED_ERROR);
    }

    public static void unregisterMovieUploadedNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_UPLOADED);
    }
}
