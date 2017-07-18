package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.ViewUtils;

public class ListPopupWindow extends PopupWindow {
    private BaseAdapter mAdapter;
    private final int[] mAnchorPos = new int[2];
    private View mContentView;
    private final Context mContext;
    private final Rect mDisplayFrame = new Rect();
    private ListView mListView;
    private final OnClickListener mOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            ListPopupWindow.this.dismiss();
        }
    };
    private final OnKeyListener mOnKeyListener = new OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getKeyCode() == 4 && event.getAction() == 1) {
                ListPopupWindow.this.dismiss();
            }
            return false;
        }
    };
    private boolean mShouldWrapContent = false;

    public ListPopupWindow(Context context) {
        super(context, null, 0);
        this.mContext = context;
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setInputMethodMode(2);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void setLayoutId(int layoutId) {
        View view = LayoutInflater.from(this.mContext).inflate(layoutId, null);
        setContentView(view);
        if (this.mListView != null) {
            this.mListView.setOnKeyListener(null);
        }
        if (this.mContentView != null) {
            this.mContentView.setOnClickListener(null);
        }
        this.mListView = (ListView) view.findViewById(16908298);
        if (this.mListView == null) {
            throw new IllegalArgumentException("layout must contain a list with id '@android:id/list'");
        }
        ViewUtils.hideOverscrollEdge(this.mListView);
        this.mListView.setOnKeyListener(this.mOnKeyListener);
        if (this.mAdapter != null) {
            this.mListView.setAdapter(this.mAdapter);
        }
        this.mContentView = view;
        this.mContentView.setOnClickListener(this.mOnClickListener);
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void addFooter(View v) {
        DebugAssert.assertNotNull(v);
        DebugAssert.assertNotNull(this.mListView);
        this.mListView.addFooterView(v);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        if (this.mListView != null) {
            this.mListView.setAdapter(adapter);
        }
    }

    public BaseAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mListView.setOnItemClickListener(clickListener);
    }

    public ListView getListView() {
        return this.mListView;
    }

    public void forceWrapContent() {
        this.mShouldWrapContent = true;
    }

    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        if (this.mShouldWrapContent) {
            this.mContentView.measure(0, 0);
            update(this.mContentView.getMeasuredWidth(), this.mContentView.getMeasuredHeight());
            return;
        }
        DisplayMetrics display = this.mContext.getResources().getDisplayMetrics();
        anchor.getWindowVisibleDisplayFrame(this.mDisplayFrame);
        anchor.getLocationOnScreen(this.mAnchorPos);
        int i = this.mDisplayFrame.bottom;
        update(display.widthPixels, Math.max(display.heightPixels - (this.mAnchorPos[1] + anchor.getHeight()), this.mAnchorPos[1] - this.mDisplayFrame.top));
    }
}
