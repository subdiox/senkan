package com.mob.tools.gui;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

public abstract class PullToRequestGridAdapter extends PullToRequestBaseListAdapter {
    private PullToRequestBaseAdapter adapter;
    private boolean fling;
    private ScrollableGridView gridView = onNewGridView(getContext());
    private OnListStopScrollListener osListener;
    private boolean pullUpReady;

    public PullToRequestGridAdapter(PullToRequestView view) {
        super(view);
        this.gridView.setOnScrollListener(new OnScrollListener() {
            private int firstVisibleItem;
            private int visibleItemCount;

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.firstVisibleItem = firstVisibleItem;
                this.visibleItemCount = visibleItemCount;
                View v = view.getChildAt(visibleItemCount - 1);
                PullToRequestGridAdapter pullToRequestGridAdapter = PullToRequestGridAdapter.this;
                boolean z = firstVisibleItem + visibleItemCount == totalItemCount && v != null && v.getBottom() <= view.getBottom();
                pullToRequestGridAdapter.pullUpReady = z;
                PullToRequestGridAdapter.this.onScroll(PullToRequestGridAdapter.this.gridView, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                PullToRequestGridAdapter.this.fling = scrollState == 2;
                if (scrollState != 0) {
                    return;
                }
                if (PullToRequestGridAdapter.this.osListener != null) {
                    PullToRequestGridAdapter.this.osListener.onListStopScrolling(this.firstVisibleItem, this.visibleItemCount);
                } else if (PullToRequestGridAdapter.this.adapter != null) {
                    PullToRequestGridAdapter.this.adapter.notifyDataSetChanged();
                }
            }
        });
        this.adapter = new PullToRequestBaseAdapter(this);
        this.gridView.setAdapter(this.adapter);
    }

    public Scrollable getBodyView() {
        return this.gridView;
    }

    public GridView getGridView() {
        return this.gridView;
    }

    public boolean isFling() {
        return this.fling;
    }

    public boolean isPullDownReady() {
        return this.gridView.isReadyToPull();
    }

    public boolean isPullUpReady() {
        return this.pullUpReady;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.adapter.notifyDataSetChanged();
    }

    protected ScrollableGridView onNewGridView(Context context) {
        return new ScrollableGridView(context);
    }

    public void onScroll(Scrollable scrollable, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void setColumnWidth(int columnWidth) {
        this.gridView.setColumnWidth(columnWidth);
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.gridView.setHorizontalSpacing(horizontalSpacing);
    }

    public void setNumColumns(int numColumns) {
        this.gridView.setNumColumns(numColumns);
    }

    public void setStretchMode(int stretchMode) {
        this.gridView.setStretchMode(stretchMode);
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.gridView.setVerticalSpacing(verticalSpacing);
    }
}
