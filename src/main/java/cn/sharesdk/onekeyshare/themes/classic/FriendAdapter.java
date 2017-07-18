package cn.sharesdk.onekeyshare.themes.classic;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.mob.tools.gui.PullToRequestListAdapter;
import com.mob.tools.gui.PullToRequestView;
import com.mob.tools.utils.UIHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FriendAdapter extends PullToRequestListAdapter implements PlatformActionListener {
    private FriendListPage activity;
    private int curPage;
    private ArrayList<Following> follows;
    private boolean hasNext;
    private PRTHeader llHeader;
    private HashMap<String, Boolean> map;
    private final int pageCount = 15;
    private Platform platform;
    private float ratio;

    private static class FollowersResult {
        public boolean hasNextPage;
        public ArrayList<Following> list;

        private FollowersResult() {
            this.hasNextPage = false;
        }
    }

    public static class Following {
        public String atName;
        public boolean checked;
        public String description;
        public String icon;
        public String screenName;
        public String uid;
    }

    public FriendAdapter(FriendListPage activity, PullToRequestView view) {
        super(view);
        this.activity = activity;
        this.curPage = -1;
        this.hasNext = true;
        this.map = new HashMap();
        this.follows = new ArrayList();
        getListView().setDivider(new ColorDrawable(-1381654));
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        ListView listView = getListView();
        if (ratio < 1.0f) {
            ratio = 1.0f;
        }
        listView.setDividerHeight((int) ratio);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        getListView().setOnItemClickListener(listener);
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
        platform.setPlatformActionListener(this);
    }

    private void next() {
        if (this.hasNext) {
            this.platform.listFriend(15, this.curPage + 1, null);
        }
    }

    public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
        final FollowersResult followersResult = parseFollowers(this.platform.getName(), res, this.map);
        if (followersResult == null) {
            UIHandler.sendEmptyMessage(0, new Callback() {
                public boolean handleMessage(Message msg) {
                    FriendAdapter.this.notifyDataSetChanged();
                    return false;
                }
            });
            return;
        }
        this.hasNext = followersResult.hasNextPage;
        if (followersResult.list != null && followersResult.list.size() > 0) {
            this.curPage++;
            Message msg = new Message();
            msg.what = 1;
            msg.obj = followersResult.list;
            UIHandler.sendMessage(msg, new Callback() {
                public boolean handleMessage(Message msg) {
                    if (FriendAdapter.this.curPage <= 0) {
                        FriendAdapter.this.follows.clear();
                    }
                    FriendAdapter.this.follows.addAll(followersResult.list);
                    FriendAdapter.this.notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    private FollowersResult parseFollowers(String platform, HashMap<String, Object> res, HashMap<String, Boolean> uidMap) {
        if (res == null || res.size() <= 0) {
            return null;
        }
        boolean hasNext = false;
        ArrayList<Following> data = new ArrayList();
        Iterator it;
        HashMap<String, Object> user;
        String uid;
        Following following;
        if ("SinaWeibo".equals(platform)) {
            it = ((ArrayList) res.get("users")).iterator();
            while (it.hasNext()) {
                user = (HashMap) it.next();
                uid = String.valueOf(user.get("id"));
                if (!uidMap.containsKey(uid)) {
                    following = new Following();
                    following.uid = uid;
                    following.screenName = String.valueOf(user.get("name"));
                    following.description = String.valueOf(user.get("description"));
                    following.icon = String.valueOf(user.get("profile_image_url"));
                    following.atName = following.screenName;
                    uidMap.put(following.uid, Boolean.valueOf(true));
                    data.add(following);
                }
            }
            hasNext = ((Integer) res.get("total_number")).intValue() > uidMap.size();
        } else if ("TencentWeibo".equals(platform)) {
            hasNext = ((Integer) res.get("hasnext")).intValue() == 0;
            it = ((ArrayList) res.get("info")).iterator();
            while (it.hasNext()) {
                HashMap<String, Object> info = (HashMap) it.next();
                uid = String.valueOf(info.get("name"));
                if (!uidMap.containsKey(uid)) {
                    following = new Following();
                    following.screenName = String.valueOf(info.get("nick"));
                    following.uid = uid;
                    following.atName = uid;
                    Iterator it2 = ((ArrayList) info.get("tweet")).iterator();
                    if (it2.hasNext()) {
                        following.description = String.valueOf(((HashMap) it2.next()).get("text"));
                    }
                    following.icon = new StringBuilder(String.valueOf(String.valueOf(info.get("head")))).append("/100").toString();
                    uidMap.put(following.uid, Boolean.valueOf(true));
                    data.add(following);
                }
            }
        } else if ("Facebook".equals(platform)) {
            it = ((ArrayList) res.get(RequestKey.DATA)).iterator();
            while (it.hasNext()) {
                HashMap<String, Object> d = (HashMap) it.next();
                uid = String.valueOf(d.get("id"));
                if (!uidMap.containsKey(uid)) {
                    following = new Following();
                    following.uid = uid;
                    following.atName = "[" + uid + "]";
                    following.screenName = String.valueOf(d.get("name"));
                    HashMap<String, Object> picture = (HashMap) d.get("picture");
                    if (picture != null) {
                        following.icon = String.valueOf(((HashMap) picture.get(RequestKey.DATA)).get("url"));
                    }
                    uidMap.put(following.uid, Boolean.valueOf(true));
                    data.add(following);
                }
            }
            hasNext = ((HashMap) res.get("paging")).containsKey("next");
        } else if ("Twitter".equals(platform)) {
            it = ((ArrayList) res.get("users")).iterator();
            while (it.hasNext()) {
                user = (HashMap) it.next();
                uid = String.valueOf(user.get("screen_name"));
                if (!uidMap.containsKey(uid)) {
                    following = new Following();
                    following.uid = uid;
                    following.atName = uid;
                    following.screenName = String.valueOf(user.get("name"));
                    following.description = String.valueOf(user.get("description"));
                    following.icon = String.valueOf(user.get("profile_image_url"));
                    uidMap.put(following.uid, Boolean.valueOf(true));
                    data.add(following);
                }
            }
        }
        FollowersResult ret = new FollowersResult();
        ret.list = data;
        ret.hasNextPage = hasNext;
        return ret;
    }

    public void onError(Platform plat, int action, Throwable t) {
        t.printStackTrace();
    }

    public void onCancel(Platform plat, int action) {
        UIHandler.sendEmptyMessage(0, new Callback() {
            public boolean handleMessage(Message msg) {
                FriendAdapter.this.activity.finish();
                return false;
            }
        });
    }

    public Following getItem(int position) {
        return (Following) this.follows.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getCount() {
        return this.follows == null ? 0 : this.follows.size();
    }

    public View getHeaderView() {
        if (this.llHeader == null) {
            this.llHeader = new PRTHeader(getContext());
        }
        return this.llHeader;
    }

    public void onPullDown(int percent) {
        this.llHeader.onPullDown(percent);
    }

    public void onRefresh() {
        this.llHeader.onRequest();
        this.curPage = -1;
        this.hasNext = true;
        this.map.clear();
        next();
    }

    public void onReversed() {
        this.llHeader.reverse();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new FriendListItem(parent.getContext(), this.ratio);
        }
        ((FriendListItem) convertView).update(getItem(position), isFling());
        if (position == getCount() - 1) {
            next();
        }
        return convertView;
    }

    public View getFooterView() {
        LinearLayout footerView = new LinearLayout(getContext());
        footerView.setMinimumHeight(10);
        return footerView;
    }
}
