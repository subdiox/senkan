package com.kayac.lobi.sdk.activity.group;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.components.LobiFollowButton;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.value.LobiAccountContactValue;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.net.NakamapApi;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class LobiAccountContactListAdapter extends BaseAdapter implements RecyclerListener, Filterable {
    private List<LobiAccountContactValue> mAllItems = new ArrayList();
    protected final Context mContext;
    private ItemFilter mFilter = new ItemFilter();
    private List<LobiAccountContactValue> mItems;

    private class Holder {
        private final TextView description;
        private final LobiFollowButton followBtn;
        private final ImageLoaderCircleView image;
        private final List<UserValue> mUsers = AccountDatastore.getUsers();
        private final TextView name;

        public Holder(Context context, LinearLayout row) {
            this.image = (ImageLoaderCircleView) row.findViewById(R.id.lobi_follow_list_item_icon);
            this.image.setMemoryCacheEnable(false);
            this.name = (TextView) row.findViewById(R.id.lobi_follow_list_item_lobi_name);
            this.description = (TextView) row.findViewById(R.id.lobi_follow_list_item_name);
            this.followBtn = (LobiFollowButton) row.findViewById(R.id.lobi_follow_list_item_follow);
        }

        public void bind(Context context, final int index, final LobiAccountContactValue value) {
            this.image.loadImage(value.getIcon());
            this.name.setText(EmoticonUtil.getEmoticonSpannedText(context, value.getName()));
            this.description.setText(context.getString(R.string.lobisdk_player_name_prefix) + EmoticonUtil.getEmoticonSpannedText(context, value.getAppUser().getName()));
            if (this.followBtn == null) {
                return;
            }
            if (isMe(value)) {
                this.followBtn.setVisibility(8);
                return;
            }
            this.followBtn.setVisibility(0);
            this.followBtn.setFollowingStatus(value.isFollowing());
            this.followBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    LobiAccountContactListAdapter.this.doFollowRequest(v.getContext(), index, value);
                }
            });
        }

        private boolean isMe(LobiAccountContactValue contact) {
            for (UserValue userValue : this.mUsers) {
                if (userValue.getUid().equals(contact.getUid())) {
                    return true;
                }
            }
            return false;
        }
    }

    private class ItemFilter extends Filter {
        private ItemFilter() {
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            List<LobiAccountContactValue> filtered;
            String queryString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(queryString)) {
                filtered = LobiAccountContactListAdapter.this.mAllItems;
            } else {
                filtered = new ArrayList();
                for (LobiAccountContactValue item : LobiAccountContactListAdapter.this.mAllItems) {
                    String appUserName = null;
                    if (item.getAppUser() != null) {
                        appUserName = item.getAppUser().getName().toLowerCase();
                    }
                    String lobiUserName = item.getName().toLowerCase();
                    if ((appUserName != null && appUserName.contains(queryString)) || lobiUserName.contains(queryString)) {
                        filtered.add(item);
                    }
                }
            }
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<LobiAccountContactValue> filtered = results.values;
            boolean changed = false;
            if (filtered.size() == LobiAccountContactListAdapter.this.mItems.size()) {
                for (int i = 0; i < LobiAccountContactListAdapter.this.mItems.size(); i++) {
                    if (!((LobiAccountContactValue) LobiAccountContactListAdapter.this.mItems.get(i)).getUid().equals(((LobiAccountContactValue) filtered.get(i)).getUid())) {
                        changed = true;
                        break;
                    }
                }
            } else {
                changed = true;
            }
            if (changed) {
                LobiAccountContactListAdapter.this.mItems = filtered;
                LobiAccountContactListAdapter.this.notifyDataSetChanged();
            }
        }
    }

    public LobiAccountContactListAdapter(Context context) {
        this.mContext = context;
        this.mItems = this.mAllItems;
    }

    public void setItems(List<LobiAccountContactValue> items) {
        this.mAllItems.clear();
        this.mAllItems.addAll(items);
        this.mItems = this.mAllItems;
    }

    public void addItems(List<LobiAccountContactValue> items) {
        this.mAllItems.addAll(items);
        this.mItems = this.mAllItems;
    }

    public void updateItemFollowStatus(int index, boolean followed) {
        if (index >= 0 && index < this.mItems.size()) {
            LobiAccountContactValue item = getItem(index);
            item.setFollowingDate(followed ? System.currentTimeMillis() : -1);
            this.mItems.set(index, item);
            notifyDataSetChanged();
        }
    }

    public List<LobiAccountContactValue> getItems() {
        return this.mItems;
    }

    public int getCount() {
        return this.mItems.size();
    }

    public LobiAccountContactValue getItem(int i) {
        return (LobiAccountContactValue) this.mItems.get(i);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int index, View convertView, ViewGroup container) {
        LinearLayout view;
        if (convertView == null) {
            view = listRowBuilder(this.mContext);
        } else {
            view = (LinearLayout) convertView;
        }
        ((Holder) view.getTag()).bind(this.mContext, index, getItem(index));
        return view;
    }

    private LinearLayout listRowBuilder(Context context) {
        LinearLayout row = (LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.lobi_follow_list_item, (ViewGroup) null);
        row.setTag(new Holder(this.mContext, row));
        return row;
    }

    public void onMovedToScrapHeap(View view) {
        ((Holder) view.getTag()).image.cancelLoadImage();
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    protected void doFollowRequest(Context context, int index, LobiAccountContactValue lobiUser) {
        final Context context2 = context;
        final LobiAccountContactValue lobiAccountContactValue = lobiUser;
        final int i = index;
        AccountUtil.requestCurrentUserBindingState(new DefaultAPICallback<Boolean>(null) {
            public void onResponse(Boolean binded) {
                if (binded == null || !binded.booleanValue()) {
                    LoginEntranceDialog.start((PathRoutedActivity) context2, NotificationValue.SHEME_AUTHORITY_LOGIN, 1, 1);
                } else if (lobiAccountContactValue.isFollowing()) {
                    NakamapApi.unfollowLobiAccount(lobiAccountContactValue.getUid(), new APICallback() {
                        public void onResult(int code, JSONObject response) {
                            if (code == 0) {
                                LobiAccountContactListAdapter.this.updateItemFollowStatus(i, false);
                            } else if (context2 instanceof PathRoutedActivity) {
                                ((PathRoutedActivity) context2).showResponseError(response);
                            }
                        }
                    });
                } else {
                    NakamapApi.followLobiAccount(lobiAccountContactValue.getUid(), new APICallback() {
                        public void onResult(int code, JSONObject response) {
                            if (code == 0) {
                                LobiAccountContactListAdapter.this.updateItemFollowStatus(i, true);
                            } else if (context2 instanceof PathRoutedActivity) {
                                ((PathRoutedActivity) context2).showResponseError(response);
                            }
                        }
                    });
                }
            }

            public void onError(int statusCode, final String responseBody) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AnonymousClass1.this.getContext(), responseBody, 0).show();
                    }
                });
            }

            public void onError(final Throwable e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(AnonymousClass1.this.getContext(), e.getMessage(), 0).show();
                    }
                });
            }
        });
    }
}
