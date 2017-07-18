package com.rekoo.libs.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.rekoo.libs.utils.ResUtils;
import java.util.ArrayList;
import java.util.List;

public class MtitlePopupWindow extends PopupWindow {
    private ArrayAdapter adapter;
    private List<String> list = new ArrayList();
    private OnPopupWindowClickListener listener;
    private Context mContext;
    private int width = 0;

    public interface OnPopupWindowClickListener {
        void onPopupWindowItemClick(int i);
    }

    public MtitlePopupWindow(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View popupView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(ResUtils.getLayout("float_list", this.mContext), null);
        setContentView(popupView);
        setWidth(450);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        ListView listView = (ListView) popupView.findViewById(ResUtils.getId("lv_float", this.mContext));
        this.adapter = new ArrayAdapter(this.mContext, ResUtils.getLayout("popupwindow_item", this.mContext), ResUtils.getId("popup_item", this.mContext), this.list);
        listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MtitlePopupWindow.this.dismiss();
                if (MtitlePopupWindow.this.listener != null) {
                    MtitlePopupWindow.this.listener.onPopupWindowItemClick(position);
                }
            }
        });
    }

    public void setOnPopupWindowClickListener(OnPopupWindowClickListener listener) {
        this.listener = listener;
    }

    public void changeData(List<String> mList) {
        this.list.addAll(mList);
        this.adapter.notifyDataSetChanged();
    }
}
