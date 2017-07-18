package com.kayac.lobi.sdk.chat.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.TwoLine;
import com.kayac.lobi.libnakamap.utils.TimeUtil;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChatMemberListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<UserValue> mData = new ArrayList();
    private final LayoutInflater mInflater;
    private boolean mIsPublicGroup;
    private final Comparator<UserValue> mUserComparator = new Comparator<UserValue>() {
        public int compare(UserValue lhs, UserValue rhs) {
            return lhs.getUid().compareTo(rhs.getUid());
        }
    };

    static final class ItemHolder {
        final TextView description;
        final ImageLoaderCircleView icon;
        final ImageView locationIcon;
        final TextView locationStatusText;
        final TextView name;

        static final class Builder {
            TextView mDescription;
            ImageLoaderCircleView mIcon;
            ImageView mLocationIcon;
            TextView mLocationStatusText;
            TextView mName;

            Builder() {
            }

            void setIcon(ImageLoaderCircleView imageLoaderView) {
                this.mIcon = imageLoaderView;
            }

            void setName(TextView textView) {
                this.mName = textView;
            }

            void setDescription(TextView textView) {
                this.mDescription = textView;
            }

            void setLocationStatusText(TextView textView) {
                this.mLocationStatusText = textView;
            }

            void setLocationIcon(ImageView imageView) {
                this.mLocationIcon = imageView;
            }

            public ItemHolder build() {
                return new ItemHolder(this.mIcon, this.mName, this.mDescription, this.mLocationStatusText, this.mLocationIcon);
            }
        }

        ItemHolder(ImageLoaderCircleView icon, TextView name, TextView description, TextView locationStatus, ImageView locationIcon) {
            this.icon = icon;
            this.name = name;
            this.description = description;
            this.locationStatusText = locationStatus;
            this.locationIcon = locationIcon;
        }
    }

    public ChatMemberListAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setIsPublicGroup(boolean isPublicGroup) {
        this.mIsPublicGroup = isPublicGroup;
    }

    public int getCount() {
        return this.mData.size();
    }

    public UserValue getItem(int location) {
        if (location >= this.mData.size()) {
            return null;
        }
        return (UserValue) this.mData.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup container) {
        ListRow view;
        if (convertView == null) {
            view = createListRow();
        } else {
            view = (ListRow) convertView;
        }
        if (position == 0) {
            view.setIndexVisibility(0, this.mContext.getResources().getString(R.string.lobi_group_member_leader));
        } else if (position == 1) {
            view.setIndexVisibility(0, this.mContext.getResources().getString(R.string.lobi_group_member_members));
        } else {
            view.setIndexVisibility(8);
        }
        bindView(view, getItem(position));
        return view;
    }

    private void bindView(ListRow view, UserValue item) {
        if (item != null) {
            boolean isOnline;
            int i;
            ItemHolder holder = (ItemHolder) view.getTag();
            holder.icon.loadImage(item.getIcon());
            holder.name.setText(item.getName());
            if (Float.isNaN(item.getLat())) {
                isOnline = false;
            } else {
                isOnline = true;
            }
            ImageView imageView = holder.locationIcon;
            if (isOnline) {
                i = 0;
            } else {
                i = 8;
            }
            imageView.setVisibility(i);
            holder.description.setText(item.getDescription());
            if (Float.isNaN(item.getLat()) || Float.isNaN(item.getLng())) {
                holder.locationStatusText.setVisibility(8);
                holder.locationIcon.setVisibility(8);
                return;
            }
            holder.locationIcon.setImageResource(R.drawable.lobi_icn_map_on);
            holder.locationStatusText.setVisibility(0);
            holder.locationStatusText.setText(TimeUtil.getTimeSpan(this.mContext, item.getLocatedDate()));
        }
    }

    private ListRow createListRow() {
        ListRow view = (ListRow) this.mInflater.inflate(R.layout.lobi_chat_member_list_item, null);
        view.setRowBackgraound(R.drawable.lobi_bg_light_repeat);
        Builder builder = new Builder();
        builder.setIcon((ImageLoaderCircleView) view.getContent(0));
        TwoLine line = (TwoLine) view.getContent(1);
        new LayoutParams(-1, -2).gravity = 16;
        builder.setName((TextView) line.findViewById(R.id.lobi_line_0));
        builder.setDescription((TextView) line.findViewById(R.id.lobi_line_1));
        View right = view.getContent(2);
        builder.setLocationIcon((ImageView) right.findViewById(R.id.lobi_chat_member_isonline));
        builder.setLocationStatusText((TextView) right.findViewById(R.id.lobi_chat_member_location_status_text));
        right.setVisibility(8);
        view.setTag(builder.build());
        return view;
    }

    public void addAll(Collection<UserValue> set) {
        this.mData.addAll(set);
        notifyDataSetChanged();
    }

    public void updateSharedLocation(UserValue userValue) {
        int pos = Collections.binarySearch(this.mData, userValue, this.mUserComparator);
        if (pos > -1) {
            this.mData.set(pos, userValue);
        }
        notifyDataSetChanged();
    }

    public boolean removeUser(UserValue userValue) {
        boolean removed = false;
        int pos = Collections.binarySearch(this.mData, userValue, this.mUserComparator);
        if (pos > -1) {
            removed = true;
            this.mData.remove(pos);
        }
        notifyDataSetChanged();
        return removed;
    }

    public void setLeader(UserValue leader) {
        this.mData.add(0, leader);
        notifyDataSetChanged();
    }
}
