package com.kayac.lobi.sdk.rec.unity;

import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.sdk.utils.UnityUtils;

public class LobiRecBridge {
    private LobiRecBridge() {
    }

    public static void cameraCaptureAndRender() {
        LobiRecAPI.cameraCaptureAndRender();
    }

    public static void cameraPreRender() {
        LobiRecAPI.cameraPreRender();
    }

    public static void disableAudioRecording() {
        LobiRecUnity.disableAudioRecording();
    }

    public static int getCapturePerFrame() {
        return LobiRecAPI.getCapturePerFrame();
    }

    public static float getGameSoundVolume() {
        return LobiRecAPI.getGameSoundVolume();
    }

    public static int getLiveWipeStatus() {
        return LobiRecAPI.getLiveWipeStatus();
    }

    public static boolean getMicEnable() {
        return LobiRecAPI.getMicEnable();
    }

    public static float getMicVolume() {
        return LobiRecAPI.getMicVolume();
    }

    public static boolean getStickyRecording() {
        return LobiRecAPI.getStickyRecording();
    }

    public static float getWipePositionX() {
        return (float) LobiRecAPI.getWipePositionX();
    }

    public static float getWipePositionY() {
        return (float) LobiRecAPI.getWipePositionY();
    }

    public static float getWipeSquareSize() {
        return (float) LobiRecAPI.getWipeSquareSize();
    }

    public static boolean hasMovie() {
        return LobiRecAPI.hasMovie();
    }

    public static boolean isCapturing() {
        return LobiRecAPI.isCapturing();
    }

    public static boolean isFaceCaptureSupported() {
        return LobiRecAPI.isFaceCaptureSupported();
    }

    public static void isMicEnabled(String str, String str2) {
        UnityUtils.unitySendMessage(str, str2, "1");
    }

    public static boolean isPaused() {
        return LobiRecAPI.isPaused();
    }

    public static boolean isSupported() {
        return LobiRecAPI.isSupported();
    }

    public static void onEndOfFrame() {
        LobiRecAPI.onEndOfFrame();
    }

    public static void openLobiPlayActivity() {
        LobiRecAPI.openLobiPlayActivity();
    }

    public static void openLobiPlayActivity(String str) {
        LobiRecAPI.openLobiPlayActivity(str);
    }

    public static void openLobiPlayActivity(String str, String str2, boolean z, String str3) {
        LobiRecAPI.openLobiPlayActivity(str, str2, z, str3);
    }

    public static void openLobiPlayActivityWithEventFields(String str) {
        LobiRecAPI.openLobiPlayActivityWithEventFields(str);
    }

    public static void openPostVideoActivity(String str, String str2, long j, String str3) {
        LobiRecAPI.openPostVideoActivity(str, str2, j, str3);
    }

    public static void openPostVideoActivity(String str, String str2, long j, String str3, String str4) {
        LobiRecAPI.openPostVideoActivity(str, str2, j, str3, str4);
    }

    public static void pauseCapturing() {
        LobiRecAPI.pauseRecording();
    }

    public static void registerDismissingPostVideoViewNotification(String str, String str2) {
        LobiRecUnity.registerDismissingPostVideoViewNotification(str, str2);
    }

    public static void registerDryingUpInStorageObserver(String str, String str2) {
        LobiRecUnity.registerDryingUpInStorageObserver(str, str2);
    }

    public static void registerMicEnableErrorObserver(String str, String str2) {
        LobiRecUnity.registerMicEnableErrorObserver(str, str2);
    }

    public static void registerMovieCreatedErrorNotification(String str, String str2) {
        LobiRecUnity.registerMovieCreatedErrorNotification(str, str2);
    }

    public static void registerMovieCreatedNotification(String str, String str2) {
        LobiRecUnity.registerMovieCreatedNotification(str, str2);
    }

    public static void registerMovieUploadedErrorNotification(String str, String str2) {
        LobiRecUnity.registerMovieUploadedErrorNotification(str, str2);
    }

    public static void registerMovieUploadedNotification(String str, String str2) {
        LobiRecUnity.registerMovieUploadedNotification(str, str2);
    }

    public static boolean removeUnretainedVideo() {
        return LobiRecAPI.removeUnretainedVideo();
    }

    public static void resumeCapturing() {
        LobiRecAPI.resumeRecording();
    }

    public static void setCapturePerFrame(int i) {
        LobiRecAPI.setCapturePerFrame(i);
    }

    public static void setGameSoundVolume(float f) {
        LobiRecAPI.setGameSoundVolume((double) f);
    }

    public static void setLiveWipeStatus(int i) {
        LobiRecAPI.setLiveWipeStatus(i);
    }

    public static void setMicEnable(boolean z) {
        LobiRecAPI.setMicEnable(z);
    }

    public static void setMicVolume(float f) {
        LobiRecAPI.setMicVolume((double) f);
    }

    public static void setStickyRecording(boolean z) {
        LobiRecAPI.setStickyRecording(z);
    }

    public static void setWipePositionX(float f) {
        LobiRecAPI.setWipePositionX((int) f);
    }

    public static void setWipePositionY(float f) {
        LobiRecAPI.setWipePositionY((int) f);
    }

    public static void setWipeSquareSize(float f) {
        LobiRecAPI.setWipeSquareSize((int) f);
    }

    public static void startCapturing() {
        LobiRecUnity.startRecording();
    }

    public static void stopCapturing() {
        LobiRecAPI.stopRecording();
    }

    public static void unregisterDismissingPostVideoViewNotification() {
        LobiRecUnity.unregisterDismissingPostVideoViewNotification();
    }

    public static void unregisterDryingUpInStorageObserver() {
        LobiRecUnity.unregisterDryingUpInStorageObserver();
    }

    public static void unregisterMicEnableErrorObserver() {
        LobiRecUnity.unregisterMicEnableErrorObserver();
    }

    public static void unregisterMovieCreatedErrorNotification() {
        LobiRecUnity.unregisterMovieCreatedErrorNotification();
    }

    public static void unregisterMovieCreatedNotification() {
        LobiRecUnity.unregisterMovieCreatedNotification();
    }

    public static void unregisterMovieUploadedErrorNotification() {
        LobiRecUnity.unregisterMovieUploadedErrorNotification();
    }

    public static void unregisterMovieUploadedNotification() {
        LobiRecUnity.unregisterMovieUploadedNotification();
    }
}
