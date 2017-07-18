package com.kayac.lobi.sdk.activity.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.collection.MutablePair;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.components.CustomCheckbox;
import com.kayac.lobi.libnakamap.components.FramedImageLoader;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.TwoLine;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.util.ArrayList;
import java.util.List;

public final class ContactListAdapter extends BaseAdapter implements RecyclerListener {
    private final Context mContext;
    private final boolean mIsCheckable;
    private List<MutablePair<Pair<String, UserContactValue>, Boolean>> mItems = new ArrayList();

    static final class Holder {
        public final CustomCheckbox checkbox;
        public final TextView discription;
        public final FramedImageLoader image;
        public final TextView name;
        public final ListRow row;

        public Holder(ListRow row, FramedImageLoader image, TextView name, TextView discription, CustomCheckbox checkbox) {
            this.row = row;
            this.image = image;
            this.name = name;
            this.discription = discription;
            this.checkbox = checkbox;
        }

        public void bind(Value value) {
            this.image.loadImage(value.url);
            this.name.setText(value.name);
            this.discription.setText(value.description);
            this.checkbox.setChecked(value.checked);
        }
    }

    public static final class Value {
        public final boolean checked;
        public final CharSequence description;
        public final String index;
        public final CharSequence name;
        public final String url;

        public Value(Context context, String index, String url, String name, String description, boolean checked) {
            this.index = index;
            this.url = url;
            this.name = EmoticonUtil.getEmoticonSpannedText(context, name);
            this.description = EmoticonUtil.getEmoticonSpannedText(context, description);
            this.checked = checked;
        }
    }

    public ContactListAdapter(Context context, boolean canCheck) {
        this.mContext = context;
        this.mIsCheckable = canCheck;
    }

    public void setItems(List<MutablePair<Pair<String, UserContactValue>, Boolean>> items) {
        this.mItems = items;
    }

    public List<MutablePair<Pair<String, UserContactValue>, Boolean>> getItems() {
        return this.mItems;
    }

    public int getCount() {
        return this.mItems.size();
    }

    public Object getItem(int i) {
        return this.mItems.get(i);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int i, View convertView, ViewGroup container) {
        ListRow view;
        if (convertView == null) {
            view = listRowDeleteBuilder(this.mContext);
            Button b = (Button) view.getContent(2);
            if (this.mIsCheckable) {
                b.setVisibility(0);
            } else {
                b.setVisibility(8);
            }
        } else {
            view = (ListRow) convertView;
        }
        MutablePair<Pair<String, UserContactValue>, Boolean> item = (MutablePair) getItem(i);
        ((Holder) view.getTag()).bind(new Value(this.mContext, (String) ((Pair) item.first).first, ((UserContactValue) ((Pair) item.first).second).getIcon(), ((UserContactValue) ((Pair) item.first).second).getName(), ((UserContactValue) ((Pair) item.first).second).getDescription(), ((Boolean) item.second).booleanValue()));
        return view;
    }

    private static final ListRow listRowBuilder(Context context) {
        ListRow row = (ListRow) LayoutInflater.from(context).inflate(R.layout.lobi_group_contact_list_item, null);
        TwoLine line = (TwoLine) row.getContent(1);
        Holder holder = new Holder(row, (FramedImageLoader) row.getContent(0), (TextView) line.findViewById(R.id.lobi_line_0), (TextView) line.findViewById(R.id.lobi_line_1), null);
        holder.image.getImageLoaderView().setMemoryCacheEnable(false);
        row.setTag(holder);
        return row;
    }

    private static final ListRow listRowDeleteBuilder(Context context) {
        ListRow row = (ListRow) LayoutInflater.from(context).inflate(R.layout.lobi_group_contact_list_item, null);
        row.setContent(2, R.layout.lobi_list_row_content_checkbox);
        Button b = (Button) row.getContent(2);
        b.setFocusable(false);
        b.setClickable(false);
        b.setVisibility(8);
        TwoLine line = (TwoLine) row.getContent(1);
        Holder holder = new Holder(row, (FramedImageLoader) row.getContent(0), (TextView) line.findViewById(R.id.lobi_line_0), (TextView) line.findViewById(R.id.lobi_line_1), (CustomCheckbox) row.getContent(2));
        holder.image.getImageLoaderView().setMemoryCacheEnable(false);
        row.setTag(holder);
        return row;
    }

    public void onMovedToScrapHeap(View view) {
        ((Holder) view.getTag()).image.getImageLoaderView().cancelLoadImage();
    }
}
