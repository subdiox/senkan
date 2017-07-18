package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;

public class LobiRecCocos2dxTest {

    final class AnonymousClass1 implements Runnable {
        private LobiMusic mLobiMusic;
        private Button mPauseButton;
        private Button mResumeButton;
        private Button mStartButton;
        private Button mStartMusicButton;
        private Button mStopButton;
        private Button mStopExternalAudio;
        private Button mStopMusicButton;
        final /* synthetic */ Activity val$activity;

        AnonymousClass1(Activity activity) {
            this.val$activity = activity;
        }

        private LinearLayout createMenu() {
            LinearLayout linearLayout = new LinearLayout(this.val$activity);
            linearLayout.setLayoutParams(new LayoutParams(-2, -2));
            linearLayout.setOrientation(1);
            return linearLayout;
        }

        private void toggleVisibility(View view) {
            view.setVisibility(view.getVisibility() == 0 ? 8 : 0);
        }

        public void run() {
            this.mLobiMusic = new LobiMusic(this.val$activity);
            ViewGroup viewGroup = (ViewGroup) this.val$activity.getWindow().getDecorView().findViewById(16908290);
            View createMenu = createMenu();
            this.mStartMusicButton = LobiRecCocos2dxTest.addButton(createMenu, "Start Music", new OnClickListener() {
                public void onClick(View view) {
                    AnonymousClass1.this.mLobiMusic.playBackgroundMusic("track.mp3", true);
                    AnonymousClass1.this.mStartMusicButton.setVisibility(8);
                    AnonymousClass1.this.mStopMusicButton.setVisibility(0);
                }
            });
            this.mStopMusicButton = LobiRecCocos2dxTest.addButton(createMenu, "Stop Music", new OnClickListener() {
                public void onClick(View view) {
                    AnonymousClass1.this.mLobiMusic.pauseBackgroundMusic();
                    AnonymousClass1.this.mStartMusicButton.setVisibility(0);
                    AnonymousClass1.this.mStopMusicButton.setVisibility(8);
                }
            });
            this.mStartMusicButton.setVisibility(0);
            this.mStopMusicButton.setVisibility(8);
            this.mStartButton = LobiRecCocos2dxTest.addButton(createMenu, "Start capturing", new OnClickListener() {
                public void onClick(View view) {
                    LobiRecAPI.setStickyRecording(true);
                    LobiRecAPI.setMicEnable(true);
                    LobiRecAPI.setCapturePerFrame(3);
                    LobiRecAPI.setMicVolume(1.8d);
                    LobiRecAPI.setGameSoundVolume(0.1d);
                    LobiRecAPI.startRecording();
                    AnonymousClass1.this.toggleVisibility(AnonymousClass1.this.mStartButton);
                    AnonymousClass1.this.toggleVisibility(AnonymousClass1.this.mStopButton);
                    AnonymousClass1.this.mPauseButton.setVisibility(0);
                }
            });
            this.mStopButton = LobiRecCocos2dxTest.addButton(createMenu, "Stop capturing", new OnClickListener() {
                public void onClick(View view) {
                    LobiRecAPI.stopRecording();
                    AnonymousClass1.this.toggleVisibility(AnonymousClass1.this.mStartButton);
                    AnonymousClass1.this.toggleVisibility(AnonymousClass1.this.mStopButton);
                    AnonymousClass1.this.mResumeButton.setVisibility(8);
                    AnonymousClass1.this.mPauseButton.setVisibility(8);
                }
            });
            this.mStartButton.setVisibility(0);
            this.mStopButton.setVisibility(8);
            this.mResumeButton = LobiRecCocos2dxTest.addButton(createMenu, "Resume capturing", new OnClickListener() {
                public void onClick(View view) {
                    LobiRecAPI.resumeRecording();
                    AnonymousClass1.this.mResumeButton.setVisibility(8);
                    AnonymousClass1.this.mPauseButton.setVisibility(0);
                }
            });
            this.mPauseButton = LobiRecCocos2dxTest.addButton(createMenu, "Pause capturing", new OnClickListener() {
                public void onClick(View view) {
                    LobiRecAPI.pauseRecording();
                    AnonymousClass1.this.mResumeButton.setVisibility(0);
                    AnonymousClass1.this.mPauseButton.setVisibility(8);
                }
            });
            this.mResumeButton.setVisibility(8);
            this.mPauseButton.setVisibility(8);
            LobiRecCocos2dxTest.addButton(createMenu, "Open video List", new OnClickListener() {
                public void onClick(View view) {
                    LobiRecAPI.openLobiPlayActivity();
                }
            });
            LobiRecCocos2dxTest.addButton(createMenu, "Post last video", new OnClickListener() {
                public void onClick(View view) {
                    LobiRecAPI.openPostVideoActivity("Rec. SDK Test", "Hello Rec.", 0, "");
                }
            });
            viewGroup.addView(createMenu);
        }
    }

    private static Button addButton(LinearLayout linearLayout, String str, OnClickListener onClickListener) {
        View button = new Button(linearLayout.getContext());
        button.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        button.setText(str);
        button.setOnClickListener(onClickListener);
        linearLayout.addView(button);
        return button;
    }

    public static void addUI(Activity activity) {
        activity.runOnUiThread(new AnonymousClass1(activity));
    }
}
