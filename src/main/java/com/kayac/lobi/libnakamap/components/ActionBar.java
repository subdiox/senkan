package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.Styleable.Function;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.kayac.lobi.libnakamap.utils.NotificationUtil.NotificationSDKType;
import com.kayac.lobi.libnakamap.utils.NotificationUtil.OnGetNotification;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.WeakHashMap;

public class ActionBar extends LinearLayout implements Styleable {
    private static final int CONTENT_TYPE_BACKABLE = 1;
    private static final int CONTENT_TYPE_CLOSE = 4;
    private static final int CONTENT_TYPE_MANUAL = 0;
    private static final int CONTENT_TYPE_MENU = 5;
    private static final int CONTENT_TYPE_SELECT_ACCOUNT = 2;
    private static final int CONTENT_TYPE_TITLE_ONLY = 3;
    public static final String NOTIFICATION_ACTION_BAR_BUTTON_TAG = "NOTIFICATION_ACTION_BAR_BUTTON_TAG";
    private static final int NOTIFICATION_BADGE_MAX = 9;
    private static Class<? extends View> sSeparator = Separator.class;
    private final WeakHashMap<String, View> mButtons;

    public static class AccountThumbnail extends FrameLayout {
        public AccountThumbnail(Context context, AttributeSet attrs) {
            super(context, attrs);
            LayoutInflater.from(context).inflate(R.layout.lobi_action_bar_account_thumbnail, this);
        }

        public void loadImage(String url) {
            ImageLoaderView image = (ImageLoaderView) findViewById(R.id.lobi_account_thumbnail_image_loader);
            if (image != null) {
                image.loadImage(url);
            }
        }
    }

    public static final class BackableContent extends LinearLayout {
        public BackableContent(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new LayoutParams(-1, -2));
            setOrientation(0);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_content_backable, this, true);
        }

        public BackableContent(Context context) {
            this(context, null);
        }

        public void setText(int resId) {
            setText(getContext().getString(resId));
        }

        public void setText(String text) {
            if (text != null && text.length() != 0) {
                TextView t = (TextView) findViewById(R.id.lobi_action_bar_title);
                DebugAssert.assertNotNull(t);
                t.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
            }
        }

        public void setOnBackButtonClickListener(OnClickListener listener) {
            ImageView back = (ImageView) findViewById(R.id.lobi_action_bar_back_button);
            DebugAssert.assertNotNull(back);
            back.setOnClickListener(listener);
        }

        public void setBackButtonImageResource(int resId) {
            ((ImageView) findViewById(R.id.lobi_action_bar_back_button)).setImageResource(resId);
        }
    }

    public static final class Button extends FrameLayout implements Styleable {
        private State mState;

        public enum State {
            STATE_DEFAULT,
            STATE_EDITABLE,
            STATE_EDITABLE_ENABLE
        }

        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public void setIconImage(int resource) {
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            DebugAssert.assertNotNull(image);
            image.setBackgroundResource(resource);
        }

        public void setOnClickListener(OnClickListener l) {
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            DebugAssert.assertNotNull(image);
            image.setOnClickListener(l);
        }

        public Button(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mState = State.STATE_DEFAULT;
            setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_button, this, true);
        }

        public Button(Context context) {
            this(context, null);
        }

        public void setState(State state) {
            this.mState = state;
        }

        public State getState() {
            return this.mState;
        }
    }

    public static final class CheckButton extends FrameLayout implements Styleable {
        private OnClickListener mOnClickListener;
        private State mState;

        public enum State {
            STATE_ENABLE,
            STATE_DISABLE
        }

        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public void setOnClickListener(OnClickListener l) {
            DebugAssert.assertNotNull((ImageButton) findViewById(R.id.lobi_action_bar_button_image));
            setEnabled(false);
            this.mOnClickListener = l;
        }

        public void setEnable() {
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            DebugAssert.assertNotNull(image);
            image.setBackgroundResource(R.drawable.lobi_action_bar_button_upload_enabled_selector);
            image.setOnClickListener(this.mOnClickListener);
            this.mState = State.STATE_ENABLE;
        }

        public void setDisable() {
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            DebugAssert.assertNotNull(image);
            image.setBackgroundResource(R.drawable.lobi_action_bar_button_upload_selector);
            this.mState = State.STATE_DISABLE;
            image.setOnClickListener(null);
        }

        public CheckButton(Context context) {
            this(context, null);
        }

        public State getState() {
            return this.mState;
        }

        public CheckButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mState = State.STATE_DISABLE;
            setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_button, this, true);
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            DebugAssert.assertNotNull(image);
            image.setBackgroundResource(R.drawable.lobi_action_bar_button_upload_selector);
            setDisable();
        }
    }

    public static final class CloseButton extends FrameLayout implements Styleable {
        private OnClickListener mOnClickListener;

        public CloseButton(Context context) {
            this(context, null);
        }

        public CloseButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_button, this, true);
            ((ImageButton) findViewById(R.id.lobi_action_bar_button_image)).setBackgroundResource(R.drawable.lobi_icn_actionbar_close);
        }

        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public void setOnClickListener(OnClickListener l) {
            ((ImageButton) findViewById(R.id.lobi_action_bar_button_image)).setOnClickListener(l);
        }
    }

    public static final class CloseContent extends LinearLayout {
        public CloseContent(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new LayoutParams(-1, -2));
            setOrientation(0);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_content_close, this, true);
        }

        public CloseContent(Context context) {
            this(context, null);
        }

        public void setOnBackButtonClickListener(OnClickListener listener) {
            ImageView back = (ImageView) findViewById(R.id.lobi_action_bar_back_button);
            DebugAssert.assertNotNull(back);
            back.setOnClickListener(listener);
        }

        public void setBackButtonImageResource(int resId) {
            ((ImageView) findViewById(R.id.lobi_action_bar_back_button)).setImageResource(resId);
        }
    }

    public static class MenuButtonContent extends LinearLayout implements Styleable {
        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public MenuButtonContent(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MenuButtonContent(Context context) {
            this(context, null);
        }

        public void setOnMenuButtonClickListener(OnClickListener listener) {
            ImageView menu = (ImageView) findViewById(R.id.lobi_action_bar_menu_button);
            DebugAssert.assertNotNull(menu);
            menu.setOnClickListener(listener);
        }
    }

    public static final class MenuContent extends MenuButtonContent {
        public MenuContent(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new LayoutParams(-1, -2));
            setOrientation(0);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_content_menu, this, true);
        }

        public MenuContent(Context context) {
            this(context, null);
        }

        public void setText(int resId) {
            setText(getContext().getString(resId));
        }

        public void setText(String text) {
            if (text != null && text.length() != 0) {
                TextView t = (TextView) findViewById(R.id.lobi_action_bar_title);
                DebugAssert.assertNotNull(t);
                t.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
            }
        }
    }

    public static final class SelectAccountContent extends MenuButtonContent {
        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public SelectAccountContent(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new LayoutParams(-1, -2));
            setOrientation(0);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_content_select_account, this, true);
        }

        public SelectAccountContent(Context context) {
            this(context, null);
        }

        public void setText(String text) {
            if (text != null && text.length() != 0) {
                TextView t = (TextView) findViewById(R.id.lobi_action_bar_account_name);
                DebugAssert.assertNotNull(t);
                t.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
            }
        }

        public void setDownIconVisibility(int visibility) {
            findViewById(R.id.lobi_action_bar_account_down_icon).setVisibility(visibility);
        }

        public void setNoticeVisibility(int visibility) {
            findViewById(R.id.lobi_action_bar_account_notice).setVisibility(visibility);
        }
    }

    public static final class Separator extends FrameLayout implements Styleable {
        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public Separator(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_separator, this, true);
        }

        public Separator(Context context) {
            this(context, null);
        }
    }

    public static final class TextButton extends FrameLayout implements Styleable {
        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public void setText(String text) {
            TextView btn = (TextView) findViewById(R.id.lobi_action_bar_new_text);
            if (btn != null) {
                btn.setVisibility(0);
                btn.setText(text);
            }
        }

        public String getText() {
            TextView btn = (TextView) findViewById(R.id.lobi_action_bar_new_text);
            if (btn != null) {
                return (String) btn.getText();
            }
            return "0";
        }

        public void removeText() {
            TextView btn = (TextView) findViewById(R.id.lobi_action_bar_new_text);
            if (btn != null) {
                btn.setText("");
                btn.setVisibility(8);
            }
        }

        public void setOnClickListener(OnClickListener l) {
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            if (image != null) {
                image.setOnClickListener(l);
            }
        }

        public TextButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_button, this, true);
        }

        public TextButton(Context context) {
            this(context, null);
        }

        public void setIconImage(int resource) {
            ImageButton image = (ImageButton) findViewById(R.id.lobi_action_bar_button_image);
            if (image != null) {
                image.setBackgroundResource(resource);
            }
        }
    }

    public static final class TitleOnlyContent extends LinearLayout implements Styleable {
        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public TitleOnlyContent(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayoutParams(new LayoutParams(-1, -2));
            setOrientation(0);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar_content_title_only, this, true);
        }

        public TitleOnlyContent(Context context) {
            this(context, null);
        }

        public void setText(String text) {
            if (text != null && text.length() != 0) {
                TextView t = (TextView) findViewById(R.id.lobi_action_bar_title);
                DebugAssert.assertNotNull(t);
                t.setText(text);
            }
        }
    }

    public static void setSeparator(Class<? extends View> separator) {
        sSeparator = separator;
    }

    public void setContent(View newContent) {
        View content = findViewById(R.id.lobi_action_bar_content_view);
        int index = getIndex(content);
        DebugAssert.assertTrue(index != -1);
        removeViewAt(index);
        newContent.setLayoutParams(content.getLayoutParams());
        newContent.setId(R.id.lobi_action_bar_content_view);
        addView(newContent, index);
    }

    public View getContent() {
        return findViewById(R.id.lobi_action_bar_content_view);
    }

    public void addActionBarButton(String tag, View actionBarButton) {
        this.mButtons.put(tag, actionBarButton);
        addActionBarButton(actionBarButton);
    }

    public void addActionBarButton(View actionBarButton) {
        if (sSeparator != null) {
            try {
                View separator = (View) sSeparator.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{getContext()});
                separator.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
                addView(separator);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
        addView(actionBarButton);
    }

    public void addActionBarButtonWithSeparator(String tag, View actionBarButton) {
        this.mButtons.put(tag, actionBarButton);
        addActionBarButtonWithSeparator(actionBarButton);
    }

    public void addActionBarButtonWithSeparator(View actionBarButton) {
        actionBarButton.findViewById(R.id.lobi_action_bar_separator_helper).setVisibility(0);
        addView(actionBarButton);
    }

    public View getActioinBarButton(String tag) {
        return (View) this.mButtons.get(tag);
    }

    public void removeAllActionBarButton() {
        this.mButtons.clear();
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
    }

    public void setStyle(Style style) {
        Function.setChildenStyleIter(style, this);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mButtons = new WeakHashMap();
        setLayoutParams(new LayoutParams(-1, -2));
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_action_bar, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_ActionBar);
        int contentType = typedArray.getInt(R.styleable.lobi_ActionBar_lobi_content_type, 0);
        String text = typedArray.getString(R.styleable.lobi_ActionBar_lobi_text);
        switch (contentType) {
            case 0:
                return;
            case 1:
                BackableContent content = new BackableContent(getContext());
                content.setText(text);
                setContent(content);
                return;
            case 2:
                SelectAccountContent content2 = new SelectAccountContent(getContext());
                content2.setText(text);
                setContent(content2);
                return;
            case 3:
                TitleOnlyContent content3 = new TitleOnlyContent(getContext());
                content3.setText(text);
                setContent(content3);
                return;
            case 4:
                setContent(new CloseContent(getContext()));
                return;
            case 5:
                MenuContent content4 = new MenuContent(getContext());
                content4.setText(text);
                setContent(content4);
                return;
            default:
                DebugAssert.fail();
                return;
        }
    }

    public ActionBar(Context context) {
        this(context, null);
    }

    private int getIndex(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            if (view == getChildAt(i)) {
                return i;
            }
        }
        return -1;
    }

    public final TextButton setupNotifications(final NotificationSDKType type) {
        TextButton notificationButton = new TextButton(getContext());
        addActionBarButtonWithSeparator(NOTIFICATION_ACTION_BAR_BUTTON_TAG, notificationButton);
        setBadgeCount(0);
        notificationButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ActionBar.this.setBadgeCount(0);
                ActionBar.this.openNotificationPopup(type);
            }
        });
        return notificationButton;
    }

    public final void getNotifications(NotificationSDKType sdkType, final OnGetNotification onGetNotificationListener) {
        Context context = getContext();
        if (((TextButton) getActioinBarButton(NOTIFICATION_ACTION_BAR_BUTTON_TAG)) != null) {
            NotificationUtil.getNotifications(context, sdkType, new OnGetNotification() {
                public void onGetNotification(int notificationCount, boolean hasPromotionNotification) {
                    int badgeCount = notificationCount;
                    if (hasPromotionNotification) {
                        badgeCount++;
                    }
                    ActionBar.this.setBadgeCount(badgeCount);
                    if (onGetNotificationListener != null) {
                        onGetNotificationListener.onGetNotification(notificationCount, hasPromotionNotification);
                    }
                }
            });
        }
    }

    private void setBadgeCount(int badgeCount) {
        TextButton notificationButton = (TextButton) getActioinBarButton(NOTIFICATION_ACTION_BAR_BUTTON_TAG);
        String badgeCountLabel = "";
        if (badgeCount <= 9) {
            badgeCountLabel = Integer.toString(badgeCount);
        } else {
            badgeCountLabel = "9+";
        }
        notificationButton.setText(badgeCountLabel);
        if (badgeCount > 0) {
            notificationButton.setIconImage(R.drawable.lobi_action_bar_button_notice_news_selector);
            return;
        }
        notificationButton.setIconImage(R.drawable.lobi_action_bar_button_notice_selector);
        notificationButton.removeText();
    }

    public final void openNotificationPopup(NotificationSDKType type) {
        NotificationUtil.openPopup(getContext(), getHeight(), (TextButton) getActioinBarButton(NOTIFICATION_ACTION_BAR_BUTTON_TAG), null, type);
    }
}
