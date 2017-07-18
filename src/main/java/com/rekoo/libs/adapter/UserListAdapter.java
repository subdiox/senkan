package com.rekoo.libs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.utils.ResUtils;
import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> list;
    private LayoutInflater mInflater;
    ManageUsersInterface manageUsers;

    public interface ManageUsersInterface {
        void delete(User user);
    }

    public final class ViewHolder {
        public ImageView ivDelete;
        public TextView tvUserName;
    }

    public UserListAdapter(Context context, List<User> list) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(ResUtils.getLayout("rekoo_item_in_user_popwindow", this.context), parent, false);
            holder.tvUserName = (TextView) convertView.findViewById(ResUtils.getId("tvUserName", this.context));
            holder.ivDelete = (ImageView) convertView.findViewById(ResUtils.getId("ivDelete", this.context));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvUserName.setText(((User) this.list.get(position)).getUserName());
        holder.ivDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (UserListAdapter.this.manageUsers != null && UserListAdapter.this.list.size() > 0) {
                    UserListAdapter.this.manageUsers.delete((User) UserListAdapter.this.list.get(position));
                }
            }
        });
        return convertView;
    }

    public void setOnDeleteListener(ManageUsersInterface manageUsers) {
        this.manageUsers = manageUsers;
    }
}
