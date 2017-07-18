package com.rekoo.libs.platform.ui.floating;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.platform.ui.FeedbackList;
import com.rekoo.libs.platform.ui.FloatWebView;
import com.rekoo.libs.platform.ui.UserFeedbackActivity;
import com.rekoo.libs.utils.CommonUtils;
import com.rekoo.libs.utils.FLAsync;
import com.rekoo.libs.utils.ResUtils;

public class FloatManager {
    private static FloatManager floatManager;
    private Context context;
    private OnClickListener feedbackClick = new OnClickListener() {
        public void onClick(View v) {
            FloatManager.this.removeView();
            if (!Config.isSolve) {
                CommonUtils.openActivity(FloatManager.this.context, UserFeedbackActivity.class);
            } else if (Config.isSolve) {
                CommonUtils.openActivity(FloatManager.this.context, FeedbackList.class);
            }
        }
    };
    private FloatView floatView = null;
    private NoDuplicateClickListener floatViewClick = new NoDuplicateClickListener() {
        public void onNoDulicateClick(View v) {
            FloatManager.this.isRight = FloatManager.this.mPreferenceManager.isDisplayRight();
            BIConfig.getBiConfig().dropzoneClick(FloatManager.this.context);
            FLAsync.getManager().initFLAsync(FloatManager.this.context);
            FloatManager.this.handler.postDelayed(new Runnable() {
                public void run() {
                    FloatManager.this.secondFloatView = new SecondFloatView(FloatManager.this.context, FloatManager.this.windowParams, FloatManager.this.windowManager);
                    FloatManager.this.secondFloatView.setOnClickListener(new NoDuplicateClickListener() {
                        public void onNoDulicateClick(View v) {
                            FloatManager.this.dismissPopupWindow();
                            FloatManager.this.handler.postDelayed(new Runnable() {
                                public void run() {
                                    if (FloatManager.this.secondFloatView != null) {
                                        FloatManager.this.windowManager.removeView(FloatManager.this.secondFloatView);
                                    }
                                    FloatManager.this.secondFloatView = null;
                                }
                            }, 200);
                        }
                    });
                    FloatManager.this.windowManager.addView(FloatManager.this.secondFloatView, FloatManager.this.windowParams);
                    int redPointId = ResUtils.getDrawable("redpoint", FloatManager.this.context);
                    int usercenter = ResUtils.getDrawable("usercenter", FloatManager.this.context);
                    if (FloatManager.this.isRight) {
                        FloatManager.this.popupView = View.inflate(FloatManager.this.context, ResUtils.getLayout("float_popup_window_right", FloatManager.this.context), null);
                        FloatManager.this.right_usercenter = (ImageView) FloatManager.this.popupView.findViewById(ResUtils.getId("right_usercenter", FloatManager.this.context));
                        if (Config.isSolve && FloatManager.this.isRight) {
                            FloatManager.this.right_usercenter.setImageResource(redPointId);
                        } else if (!Config.isSolve && FloatManager.this.isRight) {
                            FloatManager.this.right_usercenter.setImageResource(usercenter);
                        }
                    } else {
                        FloatManager.this.popupView = View.inflate(FloatManager.this.context, ResUtils.getLayout("float_popup_window_left", FloatManager.this.context), null);
                        FloatManager.this.iv_user_feedback = (ImageView) FloatManager.this.popupView.findViewById(ResUtils.getId("iv_user_feedback", FloatManager.this.context));
                        if (Config.isSolve && !FloatManager.this.isRight) {
                            FloatManager.this.iv_user_feedback.setImageResource(redPointId);
                        } else if (!(Config.isSolve || FloatManager.this.isRight)) {
                            FloatManager.this.iv_user_feedback.setImageResource(usercenter);
                        }
                    }
                    FloatManager.this.initView();
                    FloatManager.this.popupWindow = new PopupWindow(FloatManager.this.popupView, -2, -2);
                    FloatManager.this.popupWindow.setTouchable(true);
                    if (FloatManager.this.isRight) {
                        FloatManager.this.popupWindow.setAnimationStyle(ResUtils.getStyle("popupWindowRightAnimation", FloatManager.this.context));
                        FloatManager.this.popupWindow.showAtLocation(FloatManager.this.floatView, 5, 0, 0);
                        return;
                    }
                    FloatManager.this.popupWindow.setAnimationStyle(ResUtils.getStyle("popupWindowAnimation", FloatManager.this.context));
                    FloatManager.this.popupWindow.showAtLocation(FloatManager.this.floatView, 3, 0, 0);
                }
            }, (long) FloatManager.this.postDelayedTime);
        }
    };
    private OnClickListener forumClick = new OnClickListener() {
        public void onClick(View v) {
            if (Config.isRun) {
                BIConfig.getBiConfig().gamesForumClick(FloatManager.this.context);
                FloatManager.this.removeView();
                CommonUtils.openActivity(FloatManager.this.context, FloatWebView.class);
            }
        }
    };
    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
        }
    };
    private boolean isDisplay = false;
    private boolean isRight;
    private ImageView iv_user_feedback;
    private LinearLayout ll_user_feedback;
    private PreferebceManager mPreferenceManager;
    private View popupView;
    private PopupWindow popupWindow;
    private int postDelayedTime = 20;
    private ImageView right_usercenter;
    private SecondFloatView secondFloatView;
    private WindowManager windowManager;
    private LayoutParams windowParams = new LayoutParams();

    public static FloatManager getManager(Context context) {
        if (floatManager == null) {
            synchronized (FloatManager.class) {
                if (floatManager == null) {
                    floatManager = new FloatManager(context);
                }
            }
        }
        return floatManager;
    }

    public FloatManager(Context context) {
        this.context = context;
        this.mPreferenceManager = new PreferebceManager(context);
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.floatView = new FloatView(context, this.windowParams, this.windowManager);
        this.floatView.setNoDuplicateClickListener(this.floatViewClick);
    }

    public void createView() {
        Log.e("TAG", "response==createView" + Config.isLogin);
        if (!this.isDisplay && !((Activity) this.context).isFinishing() && Config.isShowbar) {
            this.windowManager.addView(this.floatView, this.windowParams);
            this.isDisplay = true;
        }
    }

    public void removeView() {
        Log.e("TAG", "response==removeView" + Config.isLogin);
        if (this.isDisplay && !((Activity) this.context).isFinishing()) {
            if (this.secondFloatView != null) {
                dismissPopupWindow();
                this.windowManager.removeView(this.secondFloatView);
                this.secondFloatView = null;
            }
            this.windowManager.removeView(this.floatView);
            this.isDisplay = false;
        }
    }

    private void dismissPopupWindow() {
        if (this.popupWindow != null) {
            this.popupWindow.dismiss();
            this.popupWindow = null;
        }
    }

    private void initView() {
        int ll_user_feedbackId = ResUtils.getId("ll_user_feedback", this.context);
        int ll_game_forumId = ResUtils.getId("ll_game_forum", this.context);
        this.ll_user_feedback = (LinearLayout) this.popupView.findViewById(ll_user_feedbackId);
        if (Config.isFeedback) {
            this.ll_user_feedback.setVisibility(0);
        } else {
            this.ll_user_feedback.setVisibility(8);
        }
        this.ll_user_feedback.setOnClickListener(this.feedbackClick);
        View v = this.popupView.findViewById(ll_game_forumId);
        if (!TextUtils.isEmpty(Config.forumUrl)) {
            v.setOnClickListener(this.forumClick);
        }
    }
}
