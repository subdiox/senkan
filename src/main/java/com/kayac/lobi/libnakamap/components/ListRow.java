package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.collection.MutablePair;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.util.ArrayList;
import java.util.List;

public class ListRow extends LinearLayout implements Checkable {
    private static final int[] CHECKED_STATE = new int[]{16842912};
    public static final int POSITION_CENTER = 1;
    public static final int POSITION_LEFT = 0;
    public static final int POSITION_RIGHT = 2;
    private static final int[] PRESSED_STATE = new int[]{16842919};
    private boolean mIsChecked;

    public static final class Builder {
        private int mBackgroundResource;
        private View mCenterContent;
        private int mLeftContent;
        private int mRightContent;
        private int mRowBackground;

        public void setRowBackgraound(int background) {
            this.mRowBackground = background;
        }

        public void setBackgroundResource(int background) {
            this.mBackgroundResource = background;
        }

        public void setLeftContentLayout(int content) {
            this.mLeftContent = content;
        }

        public void setRightContentLayout(int content) {
            this.mRightContent = content;
        }

        public void setCenterContent(View content) {
            this.mCenterContent = content;
        }

        public ListRow build(Context context) {
            ListRow listRow = new ListRow(context);
            ((LinearLayout) listRow.findViewById(R.id.lobi_list_row_area)).setLayoutParams(new LayoutParams(-1, context.getResources().getDimensionPixelSize(R.dimen.lobi_custom_dialog_list_row_height_big)));
            if (this.mRowBackground != 0) {
                listRow.setRowBackgraound(this.mRowBackground);
            }
            if (this.mBackgroundResource != 0) {
                listRow.setBackgroundResource(this.mBackgroundResource);
            }
            if (this.mLeftContent != 0) {
                listRow.setContent(0, this.mLeftContent);
            } else {
                listRow.getContentArea(0).setVisibility(8);
            }
            if (this.mRightContent != 0) {
                listRow.setContent(2, this.mRightContent);
            } else {
                listRow.getContentArea(0).setVisibility(8);
            }
            if (this.mCenterContent != null) {
                FrameLayout area = (FrameLayout) listRow.findViewById(R.id.lobi_list_row_center_content_view_area);
                LayoutParams p = (LayoutParams) area.getLayoutParams();
                p.gravity = 16;
                area.setLayoutParams(p);
                listRow.setContent(1, this.mCenterContent);
                listRow.getContentArea(1).setVisibility(0);
            }
            return listRow;
        }
    }

    public interface IndexTarget<T> {
        String pickup(T t);
    }

    public static class OneLine extends LinearLayout {
        public static final int POSITION_0 = 0;

        public void setText(int position, String text) {
            TextView v = (TextView) findViewById(getResourceId(position));
            DebugAssert.assertNotNull(v);
            v.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
        }

        public OneLine(Context context, AttributeSet attrs) {
            super(context, attrs);
            initLayout();
        }

        public OneLine(Context context) {
            super(context);
            initLayout();
        }

        private void initLayout() {
            setOrientation(1);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_list_row_center_oneline, this, true);
        }

        public TextView getTextView(int position) {
            return (TextView) findViewById(getResourceId(position));
        }

        private int getResourceId(int position) {
            switch (position) {
                case 0:
                    return R.id.lobi_line_0;
                default:
                    DebugAssert.fail();
                    return 0;
            }
        }
    }

    public static class OneLineSmall extends LinearLayout {
        public static final int POSITION_0 = 0;

        public void setText(int position, String text) {
            TextView v = (TextView) findViewById(getResourceId(position));
            DebugAssert.assertNotNull(v);
            v.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
        }

        public OneLineSmall(Context context, AttributeSet attrs) {
            super(context, attrs);
            initLayout();
        }

        public OneLineSmall(Context context) {
            super(context);
            initLayout();
        }

        private void initLayout() {
            setOrientation(1);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_list_row_center_oneline_small, this, true);
        }

        public TextView getTextView(int position) {
            return (TextView) findViewById(getResourceId(position));
        }

        private int getResourceId(int position) {
            switch (position) {
                case 0:
                    return R.id.lobi_line_0;
                default:
                    DebugAssert.fail();
                    return 0;
            }
        }
    }

    public static final class ThreeLine extends LinearLayout {
        public static final int POSITION_0 = 0;
        public static final int POSITION_1 = 1;
        public static final int POSITION_2 = 2;

        public void setText(int position, String text) {
            TextView v = (TextView) findViewById(getResourceId(position));
            DebugAssert.assertNotNull(v);
            v.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
        }

        public ThreeLine(Context context, AttributeSet attrs) {
            super(context, attrs);
            initLayout();
        }

        public ThreeLine(Context context) {
            super(context);
            initLayout();
        }

        private void initLayout() {
            setOrientation(1);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_list_row_center_threeline, this, true);
        }

        public TextView getTextView(int position) {
            return (TextView) findViewById(getResourceId(position));
        }

        private int getResourceId(int position) {
            switch (position) {
                case 0:
                    return R.id.lobi_line_0;
                case 1:
                    return R.id.lobi_line_1;
                case 2:
                    return R.id.lobi_line_2;
                default:
                    DebugAssert.fail();
                    return 0;
            }
        }
    }

    public static final class TwoLine extends LinearLayout {
        public static final int POSITION_0 = 0;
        public static final int POSITION_1 = 1;

        public void setText(int position, String text) {
            TextView v = (TextView) findViewById(getResourceId(position));
            DebugAssert.assertNotNull(v);
            v.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), text));
        }

        public void setVisibility(int position, int visibility) {
            TextView v = (TextView) findViewById(getResourceId(position));
            DebugAssert.assertNotNull(v);
            v.setVisibility(visibility);
        }

        public void setStyle(int style) {
        }

        public TwoLine(Context context, AttributeSet attrs) {
            super(context, attrs);
            initLayout(context.obtainStyledAttributes(attrs, R.styleable.lobi_ListRow_TwoLine).getResourceId(R.styleable.lobi_ListRow_TwoLine_lobi_two_line_layout, R.layout.lobi_list_row_center_twoline));
        }

        public TwoLine(Context context) {
            super(context);
            initLayout(R.layout.lobi_list_row_center_twoline);
        }

        private void initLayout(int layout) {
            setOrientation(1);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(layout, this, true);
        }

        public TextView getTextView(int position) {
            return (TextView) findViewById(getResourceId(position));
        }

        private int getResourceId(int position) {
            switch (position) {
                case 0:
                    return R.id.lobi_line_0;
                case 1:
                    return R.id.lobi_line_1;
                default:
                    DebugAssert.fail();
                    return 0;
            }
        }
    }

    public static final <T> List<MutablePair<Pair<String, T>, Boolean>> appendIndexMapCheckable(List<T> items, IndexTarget<T> target) {
        List<MutablePair<Pair<String, T>, Boolean>> result = new ArrayList(items.size());
        String lastIndex = null;
        for (T t : items) {
            String s = target.pickup(t);
            if (TextUtils.isEmpty(s)) {
                result.add(new MutablePair(new Pair(null, t), Boolean.valueOf(false)));
            } else {
                s = s.substring(0, 1);
                if (s.equals(lastIndex)) {
                    result.add(new MutablePair(new Pair(null, t), Boolean.valueOf(false)));
                } else {
                    result.add(new MutablePair(new Pair(s, t), Boolean.valueOf(false)));
                    lastIndex = s;
                }
            }
        }
        return result;
    }

    public static final <T> List<Pair<String, T>> appendIndexMap(List<T> items, IndexTarget<T> target) {
        List<Pair<String, T>> result = new ArrayList(items.size());
        String lastIndex = null;
        for (T t : items) {
            String s = target.pickup(t);
            if (TextUtils.isEmpty(s)) {
                result.add(new Pair(null, t));
            } else {
                s = s.substring(0, 1);
                if (s.equals(lastIndex)) {
                    result.add(new Pair(null, t));
                } else {
                    result.add(new Pair(s, t));
                    lastIndex = s;
                }
            }
        }
        return result;
    }

    public void setIndexVisibility(int visibility) {
        ((SectionView) findViewById(R.id.lobi_list_row_index_text)).setVisibility(visibility);
    }

    public void setIndexVisibility(int visibility, String index) {
        setIndexVisibility(visibility);
        ((SectionView) findViewById(R.id.lobi_list_row_index_text)).setTitleText(index);
    }

    public void setIndexBackground(int resId) {
        ((SectionView) findViewById(R.id.lobi_list_row_index_text)).setBackgroundResource(resId);
    }

    public void setIndexBackground(Drawable drawable) {
        findViewById(R.id.lobi_list_row_index_text).setBackgroundDrawable(drawable);
    }

    public void setIndexBackgraoundColor(int color) {
        findViewById(R.id.lobi_list_row_index_text).setBackgroundColor(color);
    }

    public void setRowBackgraound(int resId) {
        ((ViewGroup) findViewById(R.id.lobi_list_row_area)).setBackgroundResource(resId);
    }

    public void setRowBackgraound(Drawable drawable) {
        ((ViewGroup) findViewById(R.id.lobi_list_row_area)).setBackgroundDrawable(drawable);
    }

    public void setRowBackgraoundColor(int color) {
        ((ViewGroup) findViewById(R.id.lobi_list_row_area)).setBackgroundColor(color);
    }

    public void setContent(int position, int layoutId) {
        View newContent = LayoutInflater.from(getContext()).inflate(layoutId, null, false);
        ViewGroup area = (ViewGroup) findViewById(getResourceId(position));
        area.removeViewAt(0);
        area.addView(newContent, 0);
        area.setVisibility(0);
    }

    public void setContent(int position, View newContent) {
        setContent(position, newContent, (FrameLayout.LayoutParams) ((ViewGroup) findViewById(getResourceId(position))).getChildAt(0).getLayoutParams());
    }

    public void setContent(int position, View newContent, FrameLayout.LayoutParams params) {
        ViewGroup area = (ViewGroup) findViewById(getResourceId(position));
        area.removeViewAt(0);
        newContent.setLayoutParams(params);
        area.addView(newContent, 0);
    }

    public TextView getIndexText() {
        return ((SectionView) findViewById(R.id.lobi_list_row_index_text)).getTitleTextView();
    }

    public ViewGroup getContentArea(int position) {
        return (ViewGroup) findViewById(getResourceId(position));
    }

    public View getContent(int position) {
        return ((ViewGroup) findViewById(getResourceId(position))).getChildAt(0);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            Drawable drawable = getChildAt(i).getBackground();
            if (drawable != null) {
                drawable.setState(state);
            }
        }
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState;
        if (isChecked()) {
            drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, CHECKED_STATE);
            return drawableState;
        } else if (!isPressed()) {
            return super.onCreateDrawableState(extraSpace);
        } else {
            drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, PRESSED_STATE);
            return drawableState;
        }
    }

    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        refreshDrawableState();
    }

    public boolean isChecked() {
        return this.mIsChecked;
    }

    public void setChecked(boolean checked) {
        this.mIsChecked = checked;
        setCheckedRecursively(this, checked);
        refreshDrawableState();
    }

    private void setCheckedRecursively(ViewGroup group, boolean checked) {
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof Checkable) {
                Log.v("View", "popup: setChecked " + view);
                ((Checkable) view).setChecked(checked);
                view.refreshDrawableState();
            }
            if (view instanceof ViewGroup) {
                setCheckedRecursively((ViewGroup) view, checked);
            }
        }
    }

    public void toggle() {
        setChecked(!this.mIsChecked);
    }

    public ListRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mIsChecked = false;
        initLayout(context, attrs, defStyle);
    }

    public ListRow(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.lobi_list_row);
    }

    public ListRow(Context context) {
        this(context, null);
    }

    private void initLayout(Context context, AttributeSet attrs, int defStyle) {
        setOrientation(1);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_list_row, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_ListRow, 0, defStyle);
        setIndexVisibility(a.getBoolean(R.styleable.lobi_ListRow_lobi_indexVisible, false) ? 0 : 8);
        int layoutId = a.getResourceId(R.styleable.lobi_ListRow_lobi_leftContentLayoutId, 0);
        if (layoutId != 0) {
            setContent(0, layoutId);
        }
        getContentArea(0).setVisibility(a.getBoolean(R.styleable.lobi_ListRow_lobi_leftContentVisible, true) ? 0 : 8);
        layoutId = a.getResourceId(R.styleable.lobi_ListRow_lobi_centerContentLayoutId, 0);
        if (layoutId != 0) {
            setContent(1, layoutId);
        }
        getContentArea(1).setVisibility(a.getBoolean(R.styleable.lobi_ListRow_lobi_centerContentVisible, true) ? 0 : 8);
        layoutId = a.getResourceId(R.styleable.lobi_ListRow_lobi_rightContentLayoutId, 0);
        if (layoutId != 0) {
            setContent(2, layoutId);
        }
        getContentArea(2).setVisibility(a.getBoolean(R.styleable.lobi_ListRow_lobi_rightContentVisible, true) ? 0 : 8);
        setIndexBackground(a.getDrawable(R.styleable.lobi_ListRow_lobi_indexBackground));
        setRowBackgraound(a.getDrawable(R.styleable.lobi_ListRow_lobi_contentBackground));
        if (a.getBoolean(R.styleable.lobi_ListRow_lobi_wrapContent, false)) {
            ((LinearLayout) findViewById(R.id.lobi_list_row_area)).setLayoutParams(new LayoutParams(-1, -2));
        }
        a.recycle();
    }

    private int getResourceId(int position) {
        switch (position) {
            case 0:
                return R.id.lobi_list_row_left_content_view_area;
            case 1:
                return R.id.lobi_list_row_center_content_view_area;
            case 2:
                return R.id.lobi_list_row_right_content_view_area;
            default:
                DebugAssert.fail();
                return 0;
        }
    }
}
