package com.rekoo.libs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.rekoo.libs.utils.ResUtils;
import java.util.List;

public class SpinnerMixedPayAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;

    public final class ViewHolder {
        public ImageView ivImage;
        public TextView tvOtherType;
    }

    public SpinnerMixedPayAdapter(Context context, List<String> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(ResUtils.getLayout("rekoo_item_in_rekoopay_otherpay", this.context), parent, false);
            holder.tvOtherType = (TextView) convertView.findViewById(ResUtils.getId("tv_rekoopay_mixedpay_sptext", this.context));
            holder.ivImage = (ImageView) convertView.findViewById(ResUtils.getId("iv_rekoopay_mixedpay_image", this.context));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvOtherType.setText(((String) this.list.get(position)).toString());
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(ResUtils.getLayout("rekoo_item_in_rekoopay_otherpay_text", this.context), null);
        ((TextView) view.findViewById(ResUtils.getId("tv_rekoopay_mixedpay_sptext_drop", this.context))).setText((CharSequence) this.list.get(position));
        return view;
    }
}
