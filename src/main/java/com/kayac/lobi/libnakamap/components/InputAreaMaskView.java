package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.net.APIUtil;
import com.kayac.lobi.libnakamap.utils.ChatListUtil;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.GroupButtonHooksValue;
import com.kayac.lobi.libnakamap.value.GroupButtonHooksValue.Hooks;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition.InstalledParams;
import com.kayac.lobi.libnakamap.value.HookActionValue;
import com.kayac.lobi.libnakamap.value.HookActionValue.APIRequestParams;
import com.kayac.lobi.libnakamap.value.HookActionValue.Params;
import com.kayac.lobi.sdk.R;
import java.util.List;

public class InputAreaMaskView extends FrameLayout {
    private static final String TAG = "inputareamask";
    private Button mButton;
    private OnClickListener mClickListener;
    private GroupButtonHooksValue mHooks;
    private boolean mIsDisplayed;
    private TextView mMessage;
    private MaskState mState;

    private abstract class MaskState {
        protected abstract Hooks getHooks();

        protected abstract void render();

        private MaskState() {
        }

        protected void onClick(View v) {
            Log.d(InputAreaMaskView.TAG, "onClick");
            InputAreaMaskView.this.mClickListener.onClick(v);
            if (InputAreaMaskView.this.mHooks != null) {
                Hooks hooks = getHooks();
                if (hooks != null) {
                    List<HookActionValue> actions = hooks.getClickHooks();
                    if (actions != null) {
                        doActions(actions);
                    }
                }
            }
        }

        protected void onDisplay() {
            Log.d(InputAreaMaskView.TAG, "onDisplay");
            if (InputAreaMaskView.this.mIsDisplayed) {
                Log.d(InputAreaMaskView.TAG, "aborted: already displayed");
            } else if (InputAreaMaskView.this.mHooks == null) {
                Log.d(InputAreaMaskView.TAG, "aborted: no hooks");
            } else {
                Hooks hooks = getHooks();
                if (hooks != null) {
                    List<HookActionValue> actions = hooks.getDisplayHooks();
                    if (actions != null) {
                        doActions(actions);
                        InputAreaMaskView.this.mIsDisplayed = true;
                    }
                }
            }
        }

        protected void doActions(List<HookActionValue> actions) {
            Log.i(InputAreaMaskView.TAG, "acts: " + actions.size());
            for (HookActionValue action : actions) {
                Log.i(InputAreaMaskView.TAG, "act: " + action.getType());
                String type = action.getType();
                Params hookParams = action.getParams();
                if (APIRequestParams.TYPE.equals(type)) {
                    String uriString = ((APIRequestParams) hookParams).getApiUrl();
                    Log.i(InputAreaMaskView.TAG, "hook: POST " + uriString);
                    APIUtil.post(uriString, null, null);
                }
            }
        }
    }

    private class DownloadMask extends MaskState {
        private DownloadMask() {
            super();
        }

        protected void render() {
            InputAreaMaskView.this.mMessage.setText(R.string.lobi_download_to_join);
            InputAreaMaskView.this.mButton.setBackgroundResource(R.drawable.lobi_button_s_green_selector);
            InputAreaMaskView.this.mButton.setTextColor(InputAreaMaskView.this.getResources().getColorStateList(R.drawable.lobi_button_white_text_selector));
            InputAreaMaskView.this.mButton.setText(R.string.lobi_dl);
        }

        protected Hooks getHooks() {
            return InputAreaMaskView.this.mHooks.getDownloadButtonHooks();
        }
    }

    private class JoinMask extends MaskState {
        private JoinMask() {
            super();
        }

        protected void render() {
            InputAreaMaskView.this.mMessage.setText(R.string.lobi_join_to_see_reply);
            InputAreaMaskView.this.mButton.setText(R.string.lobi_join_short);
        }

        protected Hooks getHooks() {
            return InputAreaMaskView.this.mHooks.getJoinButtonHooks();
        }
    }

    private class NotSupportedMask extends MaskState {
        private NotSupportedMask() {
            super();
        }

        protected void render() {
            InputAreaMaskView.this.mMessage.setText(R.string.lobi_download_to_join_not_supported);
            InputAreaMaskView.this.mButton.setBackgroundResource(R.drawable.lobi_button_s_white_selector);
            InputAreaMaskView.this.mButton.setTextColor(InputAreaMaskView.this.getResources().getColor(R.color.lobi_pearl_gray));
            InputAreaMaskView.this.mButton.setText(R.string.lobi_dl);
        }

        protected Hooks getHooks() {
            return null;
        }
    }

    public InputAreaMaskView(Context context) {
        this(context, null);
    }

    public InputAreaMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsDisplayed = false;
        ViewGroup parent = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.lobi_chat_input_area_mask, null);
        this.mMessage = (TextView) parent.findViewById(R.id.lobi_chat_public_join_message);
        this.mButton = (Button) parent.findViewById(R.id.lobi_chat_public_join_button);
        this.mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (InputAreaMaskView.this.mState != null) {
                    InputAreaMaskView.this.mState.onClick(v);
                }
            }
        });
        Log.i(TAG, "attached");
        addView(parent);
    }

    private void onDisplay() {
        if (getVisibility() == 0) {
            this.mState.onDisplay();
        }
    }

    public void updateState(List<JoinCondition> joinConditions, GroupButtonHooksValue hooks) {
        if (joinConditions != null) {
            boolean canJoin;
            MaskState state;
            List<InstalledParams> neededPackages = ChatListUtil.getNotInstalledApps(joinConditions);
            if (neededPackages == null || neededPackages.size() == 0) {
                canJoin = true;
            } else {
                canJoin = false;
            }
            if (canJoin) {
                state = new JoinMask();
            } else if (TextUtils.isEmpty(((InstalledParams) neededPackages.get(0)).getPackageName())) {
                state = new NotSupportedMask();
            } else {
                state = new DownloadMask();
            }
            if (this.mState == null || this.mState.getClass() != state.getClass()) {
                this.mState = state;
                this.mIsDisplayed = false;
            }
        }
        if (hooks != null) {
            this.mHooks = hooks;
        }
    }

    public void render() {
        Log.i(TAG, "render");
        if (this.mState != null) {
            this.mState.render();
            onDisplay();
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }
}
