package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.ActionBar.Button;
import com.kayac.lobi.libnakamap.components.MegamenuFragment.OnMenuAnimationListener;
import com.kayac.lobi.libnakamap.components.MegamenuItem.MenuType;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPush;
import com.kayac.lobi.libnakamap.net.APISync;
import com.kayac.lobi.libnakamap.net.APISync.APISyncException;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.GroupValue.Builder;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.group.ContactListActivity;
import com.kayac.lobi.sdk.chat.activity.ChatGroupInfoActivity;
import com.kayac.lobi.sdk.chat.activity.ChatGroupInfoSettingsActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatMegamenuFragment extends MegamenuFragment {
    public static final String TAG = "megamenu:chat";
    protected GroupValue mGroupValue;
    private UserValue mUserValue;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mUserValue = AccountDatastore.getCurrentUser();
    }

    public void show() {
        super.show();
        updateGroupValueAsync();
    }

    public void setMegamenuToActionBar(Context context, ActionBar actionBar) {
        if (context == null || actionBar == null) {
            throw new IllegalArgumentException("context and actionBar require param.");
        }
        final Button actionButton = new Button(context);
        actionButton.setIconImage(R.drawable.lobi_action_bar_button_megamenu_bottom_selector);
        final ChatMegamenuFragment fragment = this;
        actionButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                fragment.show();
            }
        });
        actionBar.addActionBarButton(actionButton);
        setAnimationListener(new OnMenuAnimationListener() {
            public void onShowAnimationFinish() {
                actionButton.setIconImage(R.drawable.lobi_icn_actionbar_arrowtop);
            }

            public void onHideAnimationFinish() {
                actionButton.setIconImage(R.drawable.lobi_action_bar_button_megamenu_bottom_selector);
            }
        });
    }

    protected MegamenuAdapter getAdapter() {
        return new MegamenuAdapter(getActivity(), getMenuItemList());
    }

    protected void displayUIWithGroupValue(GroupValue group) {
        if (group.isPublic()) {
            this.mAdapter.setEnableItemAtPosition(MenuType.MEMBER_LOCATION.ordinal(), false);
        }
        int position = MenuType.SHOUT_SETTING.ordinal();
        MegamenuItem item = this.mAdapter.getItem(position);
        if (group.isPushEnabled() != item.getIcon1().equals(item.getCurrentIcon())) {
            this.mAdapter.changeIconAtPosition(position);
        }
    }

    protected ArrayList<MegamenuItem> getMenuItemList() {
        TypedArray icon1Array = getResources().obtainTypedArray(R.array.lobi_chat_megamenu_icon_images_1);
        TypedArray icon2Array = getResources().obtainTypedArray(R.array.lobi_chat_megamenu_icon_images_2);
        String[] titles1 = getResources().getStringArray(R.array.lobi_chat_megamenu_label_1);
        String[] titles2 = getResources().getStringArray(R.array.lobi_chat_megamenu_label_2);
        ArrayList<MegamenuItem> items = new ArrayList();
        items.add(new MegamenuItem(MenuType.GROUP_DETAIL, icon1Array.getDrawable(0), icon2Array.getDrawable(0), titles1[0], titles2[0]));
        items.add(new MegamenuItem(MenuType.LIKES_CHAT, icon1Array.getDrawable(1), icon2Array.getDrawable(1), titles1[1], titles2[1]));
        items.add(new MegamenuItem(MenuType.INVITE_FRIEND, icon1Array.getDrawable(2), icon2Array.getDrawable(2), titles1[2], titles2[2]));
        items.add(new MegamenuItem(MenuType.MEMBER_LOCATION, icon1Array.getDrawable(3), icon2Array.getDrawable(3), titles1[3], titles2[3]));
        items.add(new MegamenuItem(MenuType.SHOUT_SETTING, icon1Array.getDrawable(4), icon2Array.getDrawable(4), titles1[4], titles2[4]));
        items.add(new MegamenuItem(MenuType.GROUP_SETTING, icon1Array.getDrawable(5), icon2Array.getDrawable(5), titles1[5], titles2[5]));
        icon1Array.recycle();
        icon2Array.recycle();
        return items;
    }

    protected void handleMenuAction(MegamenuItem item, int itemPosition) {
        switch (item.getType()) {
            case GROUP_DETAIL:
                Log.d(TAG, "tap GROUP_DETAIL.");
                startChatGroupInfoActivity();
                return;
            case INVITE_FRIEND:
                Log.d(TAG, "tap INVITE_FRIEND.");
                handleInviteButtonAction();
                return;
            case SHOUT_SETTING:
                Log.d(TAG, "tap SHOUT_SETTING.");
                setPushEnableSetting(itemPosition);
                return;
            case GROUP_SETTING:
                Log.d(TAG, "tap GROUP_SETTING.");
                startChatGroupInfoSettingsActivity();
                return;
            default:
                Log.w(TAG, "menu type not found.");
                return;
        }
    }

    private void updateGroupValueAsync() {
        final String gid = this.mGid;
        TransactionDatastoreAsync.getGroup(this.mGid, this.mUserValue.getUid(), new DatastoreAsyncCallback<GroupValue>() {
            public void onResponse(final GroupValue groupValue) {
                if (groupValue != null) {
                    ChatMegamenuFragment.this.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ChatMegamenuFragment.this.mGroupValue = groupValue;
                            ChatMegamenuFragment.this.displayUIWithGroupValue(ChatMegamenuFragment.this.mGroupValue);
                        }
                    });
                    return;
                }
                Log.v("lobi-sdk", "[groupInfo] no group cache!");
                Map<String, String> params = new HashMap();
                params.put("count", "0");
                params.put("members_count", "1");
                params.put("uid", gid);
                params.put("token", AccountDatastore.getCurrentUser().getToken());
                try {
                    final GetGroup getGroup = APISync.getGroup(params);
                    ChatMegamenuFragment.this.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ChatMegamenuFragment.this.mGroupValue = getGroup.group;
                            ChatMegamenuFragment.this.displayUIWithGroupValue(ChatMegamenuFragment.this.mGroupValue);
                        }
                    });
                } catch (APISyncException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleInviteButtonAction() {
        if (this.mGroupValue == null) {
            Log.w(TAG, "group value not set.");
            return;
        }
        GroupPermissionValue permission = this.mGroupValue.getPermission();
        if (!this.mGroupValue.isPublic() && permission.addMembers) {
            ContactListActivity.startContactsListFromChatGroupInfo(this.mUserValue.getUid(), this.mGroupValue.getUid());
        }
    }

    private void setPushEnableSetting(int itemPosition) {
        if (this.mGroupValue == null) {
            Log.w(TAG, "Dialog is already displayed.");
            return;
        }
        final CustomProgressDialog progress = new CustomProgressDialog(getActivity());
        progress.setMessage(getString(R.string.lobi_loading_loading));
        progress.show();
        final boolean changePushEnabled = !this.mGroupValue.isPushEnabled();
        Map<String, String> params = new HashMap();
        params.put("token", this.mUserValue.getToken());
        params.put("uid", this.mGroupValue.getUid());
        params.put("on", changePushEnabled ? "1" : "0");
        final int i = itemPosition;
        DefaultAPICallback<PostGroupPush> callback = new DefaultAPICallback<PostGroupPush>(getActivity()) {
            public void onResponse(PostGroupPush t) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        progress.dismiss();
                        ChatMegamenuFragment.this.mAdapter.changeIconAtPosition(i);
                        Builder groupBuilder = new Builder(TransactionDatastore.getGroup(ChatMegamenuFragment.this.mGroupValue.getUid(), ChatMegamenuFragment.this.mUserValue.getUid()));
                        groupBuilder.setPushEnabled(changePushEnabled);
                        GroupValueUtils.setGroupValueAndGroupDetailValue(groupBuilder.build(), TransactionDatastore.getGroupDetail(ChatMegamenuFragment.this.mGroupValue.getUid(), ChatMegamenuFragment.this.mUserValue.getUid()), ChatMegamenuFragment.this.mUserValue.getUid(), false);
                    }
                });
            }
        };
        callback.setProgress(progress);
        CoreAPI.postGroupPush(params, callback);
    }

    private void startChatGroupInfoSettingsActivity() {
        if (this.mGroupValue == null) {
            Log.w(TAG, "group value not set.");
        } else if (GroupValue.NOT_JOINED.equals(this.mGroupValue.getType())) {
            Toast.makeText(getActivity(), getString(R.string.lobi_need_to_join), 0).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(PathRouter.PATH, ChatGroupInfoSettingsActivity.PATH_GROUP_SETTINGS_FROM_CHAT);
            bundle.putParcelable("EXTRAS_GROUP_VALUE", this.mGroupValue);
            PathRouter.startPath(bundle);
        }
    }

    private void startChatGroupInfoActivity() {
        DebugAssert.assertNotNull(this.mGid);
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, ChatGroupInfoActivity.PATH_CHAT_INFO);
        bundle.putString("gid", this.mGid);
        PathRouter.startPath(bundle);
    }
}
