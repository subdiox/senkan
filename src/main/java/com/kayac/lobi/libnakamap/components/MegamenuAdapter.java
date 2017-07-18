package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;

public class MegamenuAdapter extends BaseAdapter {
    private static final String TAG = MegamenuAdapter.class.getName();
    private Context mContext;
    private ArrayList<MegamenuItem> mItems;

    public MegamenuAdapter(Context context, ArrayList<MegamenuItem> items) {
        if (context == null || items == null) {
            throw new IllegalArgumentException("context and items is required param.");
        }
        this.mContext = context;
        this.mItems = items;
    }

    public int getCount() {
        return this.mItems.size();
    }

    public MegamenuItem getItem(int position) {
        return (MegamenuItem) this.mItems.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View contentView;
        if (convertView == null) {
            contentView = (LinearLayout) ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.lobi_chat_megamenu_list_item, null);
        } else {
            contentView = convertView;
        }
        ImageView imageView = (ImageView) contentView.findViewById(R.id.lobi_chat_megamenu_image);
        TextView textView = (TextView) contentView.findViewById(R.id.lobi_chat_megamenu_title);
        MegamenuItem item = (MegamenuItem) this.mItems.get(position);
        String title = item.getCurrentTitle();
        if (title != null) {
            textView.setText(title);
        }
        imageView.setImageDrawable(item.getCurrentIcon());
        contentView.setEnabled(item.isEnable());
        return contentView;
    }

    public void changeIconAtPosition(int itemPosition) {
        MegamenuItem item = getItem(itemPosition);
        if (item == null) {
            Log.w(TAG, "item not found.");
            return;
        }
        item.changeIconState();
        notifyDataSetChanged();
    }

    public void setEnableItemAtPosition(int itemPosition, boolean enabled) {
        MegamenuItem item = getItem(itemPosition);
        if (item == null) {
            Log.w(TAG, "item not found.");
            return;
        }
        item.setEnable(enabled);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<MegamenuItem> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }
}
