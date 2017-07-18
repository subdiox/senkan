package com.kayac.lobi.sdk.chat.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomDialog.EditTextContent;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.CustomTextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.OneLine;
import com.kayac.lobi.libnakamap.components.ListRow.TwoLine;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.ToggleOnOffButton;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIDef.PostAccusations.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.PostAccusations;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPart;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupRemove;
import com.kayac.lobi.libnakamap.net.APISync;
import com.kayac.lobi.libnakamap.net.APISync.APISyncException;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ChatReadMarkUtils;
import com.kayac.lobi.libnakamap.utils.CustomTextViewUtil;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.DefaultGroupNotFoundHandler;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.CategoryValue.Builder;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatGroupInfoActivity extends PathRoutedActivity {
    public static final String PATH_CHAT_INFO = "/grouplist/chat/info";
    protected static final String TAG = "[groupinfo]";
    String mGid;
    GroupValue mGroup;
    private boolean mIsDestroyed;
    ToggleOnOffButton mPushtoggleOnOffButton;
    UserValue mUserValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_chat_group_info_activity);
        this.mUserValue = AccountDatastore.getCurrentUser();
        getExtras();
        setupActionBar();
    }

    protected void onResume() {
        super.onResume();
        getGroupValueAsync();
    }

    protected void onPause() {
        super.onPause();
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        this.mGid = extras.getString("gid");
    }

    private void setupActionBar() {
        ((BackableContent) ((ActionBar) findViewById(R.id.lobi_action_bar)).getContent()).setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatGroupInfoActivity.this.finish();
            }
        });
    }

    private void getGroupValueAsync() {
        final String gid = this.mGid;
        TransactionDatastoreAsync.getGroup(this.mGid, this.mUserValue.getUid(), new DatastoreAsyncCallback<GroupValue>() {
            public void onResponse(final GroupValue t) {
                if (t != null) {
                    ChatGroupInfoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ChatGroupInfoActivity.this.mGroup = t;
                            Log.v("TEST", "displayUIWithGroupValue " + t.isOnline());
                            ChatGroupInfoActivity.this.displayUIWithGroupValue(t);
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
                    ChatGroupInfoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ChatGroupInfoActivity.this.mGroup = getGroup.group;
                            ChatGroupInfoActivity.this.displayUIWithGroupValue(getGroup.group);
                        }
                    });
                } catch (APISyncException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayUIWithGroupValue(GroupValue group) {
        setContent(group);
    }

    private void setContent(GroupValue group) {
        setGroupInfo(group, this.mUserValue);
        setGroupHeader(group);
        setMoveToLikeRanking();
        setSettings(group);
        setupMember(group);
    }

    private void setGroupHeader(GroupValue groupValue) {
        String wallpaper = groupValue.getWallpaper();
        if (!TextUtils.isEmpty(wallpaper)) {
            ((ImageLoaderView) findViewById(R.id.lobi_chat_group_background)).loadImage(wallpaper);
        }
        ListRow listRow = (ListRow) findViewById(R.id.lobi_chat_group_settings);
        ((ImageLoaderView) ((FrameLayout) listRow.getContent(0)).findViewById(R.id.lobi_list_row_content_image_loader_big_image_view)).loadImage(groupValue.getIcon(), 128);
        OneLine oneLine = (OneLine) listRow.getContent(1);
        TextView oneLineTextView = oneLine.getTextView(0);
        oneLineTextView.setSingleLine(false);
        oneLineTextView.setTextColor(getResources().getColor(R.color.lobi_text_white));
        oneLine.setText(0, groupValue.getName());
        findViewById(R.id.lobi_chat_group_header).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatGroupInfoActivity.this.startChatGroupInfoSettingsActivity();
            }
        });
    }

    private void startChatGroupInfoSettingsActivity() {
        if (this.mGroup != null) {
            if (GroupValue.NOT_JOINED.equals(this.mGroup.getType())) {
                Toast.makeText(this, getString(R.string.lobi_need_to_join), 0).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(PathRouter.PATH, ChatGroupInfoSettingsActivity.PATH_GROUP_SETTINGS_FROM_INFO);
            bundle.putParcelable("EXTRAS_GROUP_VALUE", this.mGroup);
            PathRouter.startPath(bundle);
        }
    }

    public void setGroupValue(GroupValue groupValue) {
        this.mGroup = groupValue;
    }

    @SuppressLint({"NewApi"})
    public void setGroupInfo(final GroupValue group, UserValue currentUser) {
        if (!isFinishing() && !isDestroyed()) {
            CustomTextView description = (CustomTextView) findViewById(R.id.lobi_chat_group_info_description);
            description.setOnTextLinkClickedListener(CustomTextViewUtil.getOnTextLinkClickedListener(InvitationWebViewActivity.PATH_INVITATION, " "));
            TextView descriptionExplain = (TextView) findViewById(R.id.lobi_chat_group_info_description_explain);
            descriptionExplain.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PathRouter.PATH, ChatGroupInfoSettingsActivity.PATH_GROUP_SETTINGS_FROM_CHAT);
                    bundle.putParcelable("EXTRAS_GROUP_VALUE", group);
                    PathRouter.startPath(bundle);
                }
            });
            description.setVisibility(0);
            Spannable descriptionText = EmoticonUtil.getEmoticonSpannedText(this, group.getDescription());
            description.setText(descriptionText);
            if (descriptionText.length() == 0) {
                descriptionExplain.setVisibility(0);
                description.setVisibility(8);
                return;
            }
            descriptionExplain.setVisibility(8);
            description.setVisibility(0);
        }
    }

    private void setupMember(GroupValue group) {
        ListRow listRow = (ListRow) findViewById(R.id.lobi_chat_group_info_members);
        TwoLine twoLine = (TwoLine) listRow.getContent(1);
        twoLine.setText(0, getString(R.string.lobi_group_info_member_list));
        twoLine.setText(1, String.valueOf(group.getMembersCount()));
        ((ImageLoaderCircleView) listRow.getContent(0)).loadImage(group.getOwner().getIcon());
        listRow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString(PathRouter.PATH, ChatMemberActivity.PATH_CHAT_INFO_MEMBERS);
                extras.putString("gid", ChatGroupInfoActivity.this.mGid);
                PathRouter.startPath(extras);
            }
        });
    }

    private void setSettings(GroupValue groupValue) {
        GroupValue group = this.mGroup;
        if (group != null) {
            setDeleteGroup(group);
        }
        setAccuseAbuse(groupValue);
    }

    private void setDeleteGroup(final GroupValue group) {
        boolean canRemoveOrPart;
        String type = group.getType();
        GroupPermissionValue permission = group.getPermission();
        if (GroupValue.NOT_JOINED.equals(type) || permission == null || !(permission.remove || permission.part)) {
            canRemoveOrPart = false;
        } else {
            canRemoveOrPart = true;
        }
        if (canRemoveOrPart) {
            String title;
            final boolean isMine = permission.remove;
            if (group.isPublic() && !isMine) {
                title = getString(R.string.lobi_leave_public_group);
            } else if (isMine) {
                title = getString(R.string.lobi_delete_group);
            } else {
                title = getString(R.string.lobi_leave_group_dialog);
            }
            final ListRow container = (ListRow) findViewById(R.id.lobi_chat_group_info_delete_group);
            ((OneLine) container.getContent(1)).setText(0, title);
            ((ImageView) container.getContent(2)).setImageResource(R.drawable.lobi_icn_arrow_black);
            container.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (isMine) {
                        ChatGroupInfoActivity.this.removeGroup(container, group);
                    } else {
                        ChatGroupInfoActivity.this.partFromGroup(container, group);
                    }
                }
            });
            return;
        }
        findViewById(R.id.lobi_chat_group_info_delete_group_container).setVisibility(8);
    }

    protected void partFromGroup(final ListRow container, final GroupValue group) {
        String message;
        if (group.isPublic()) {
            message = getString(R.string.lobi_part_from_this_public_group_question);
        } else {
            message = getString(R.string.lobi_leave_group_make_sure_sdk);
        }
        final CustomDialog dialog = CustomDialog.createTextDialog(this, message);
        if (!group.isPublic()) {
            dialog.setTitle(R.string.lobi_leave_group_confirm);
        }
        dialog.setPositiveButton(getString(R.string.lobi_leave_group), new OnClickListener() {
            final DefaultAPICallback<PostGroupPart> mOnPostGroupPart = new DefaultAPICallback<PostGroupPart>(ChatGroupInfoActivity.this) {
                public void onResponse(PostGroupPart t) {
                    if (t.success) {
                        ChatGroupInfoActivity.this.deleteGroupFromTransactionDatastore(group, ChatGroupInfoActivity.this.mUserValue.getUid());
                        PathRouter.removePathsGreaterThan("/");
                        PathRouter.startPath(PathRouterConfig.PATH_CHAT_SDK_DEFAULT);
                    }
                    super.onResponse(t);
                }

                public void onError(int statusCode, String responseBody) {
                    DefaultGroupNotFoundHandler.handleError(ChatGroupInfoActivity.this, statusCode, ChatGroupInfoActivity.this.mUserValue.getUid(), ChatGroupInfoActivity.this.mGid);
                    super.onError(statusCode, responseBody);
                }
            };

            public void onClick(View v) {
                dialog.dismiss();
                CustomProgressDialog progress = new CustomProgressDialog(ChatGroupInfoActivity.this);
                progress.setMessage(ChatGroupInfoActivity.this.getString(R.string.lobi_loading_loading));
                progress.show();
                this.mOnPostGroupPart.setProgress(progress);
                container.setEnabled(true);
                Map<String, String> params = new HashMap();
                params.put("token", ChatGroupInfoActivity.this.mUserValue.getToken());
                params.put("uid", group.getUid());
                CoreAPI.postGroupPart(params, this.mOnPostGroupPart);
            }
        });
        dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                container.setEnabled(true);
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                container.setEnabled(true);
            }
        });
        dialog.show();
        container.setEnabled(false);
    }

    private void deleteGroupFromTransactionDatastore(GroupValue group, String userUid) {
        String type = group.isPublic() ? "public" : CategoryValue.TYPE_PRIVATE;
        CategoryValue category = TransactionDatastore.getCategory(type, userUid);
        List<GroupDetailValue> details = category.getGroupDetails();
        GroupDetailValue toDelete = null;
        for (GroupDetailValue detail : details) {
            if (group.getUid().equals(detail.getUid())) {
                toDelete = detail;
                break;
            }
        }
        if (toDelete != null) {
            details.remove(toDelete);
        }
        Log.v("nakamap [group]", "deleteGroupFromTransactionDatastore");
        Builder builder = new Builder(category);
        builder.setGroupDetails(details);
        TransactionDatastore.deleteCategories(type, userUid);
        TransactionDatastore.setCategory(builder.build(), userUid);
        TransactionDatastore.deleteGroupDetail(group.getUid(), userUid);
        ChatReadMarkUtils.deleteChatAt(group.getUid());
    }

    protected void removeGroup(final ListRow container, final GroupValue group) {
        final CustomDialog dialog = CustomDialog.createTextDialog(this, getString(R.string.lobi_delete_group_make_sure_sdk));
        dialog.setTitle(getString(R.string.lobi_delete_this_group));
        dialog.setPositiveButton(getString(R.string.lobi_delete), new OnClickListener() {
            final DefaultAPICallback<PostGroupRemove> mOnPostGroupRemove = new DefaultAPICallback<PostGroupRemove>(ChatGroupInfoActivity.this) {
                public void onResponse(PostGroupRemove t) {
                    if (t.success) {
                        ChatGroupInfoActivity.this.deleteGroupFromTransactionDatastore(group, ChatGroupInfoActivity.this.mUserValue.getUid());
                        PathRouter.removePathsGreaterThan("/");
                        PathRouter.startPath(PathRouterConfig.PATH_CHAT_SDK_DEFAULT);
                    }
                    super.onResponse(t);
                }

                public void onError(int statusCode, String responseBody) {
                    DefaultGroupNotFoundHandler.handleError(ChatGroupInfoActivity.this, statusCode, ChatGroupInfoActivity.this.mUserValue.getUid(), ChatGroupInfoActivity.this.mGid);
                    super.onError(statusCode, responseBody);
                }
            };

            public void onClick(View v) {
                dialog.dismiss();
                CustomProgressDialog progress = new CustomProgressDialog(ChatGroupInfoActivity.this);
                progress.setMessage(ChatGroupInfoActivity.this.getString(R.string.lobi_loading_loading));
                progress.show();
                this.mOnPostGroupRemove.setProgress(progress);
                container.setEnabled(true);
                Map<String, String> params = new HashMap();
                params.put("token", ChatGroupInfoActivity.this.mUserValue.getToken());
                params.put("uid", group.getUid());
                CoreAPI.postGroupRemove(params, this.mOnPostGroupRemove);
            }
        });
        dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                container.setEnabled(true);
            }
        });
        dialog.show();
        container.setEnabled(false);
    }

    private void setMoveToLikeRanking() {
        Log.v(TAG, "set setMoveToLikeRanking: ");
        findViewById(R.id.lobi_chat_group_info_move_like_ranking).setVisibility(8);
    }

    private void setAccuseAbuse(final GroupValue groupValue) {
        View container = findViewById(R.id.lobi_chat_group_info_accuse_abuse_container);
        Log.v(TAG, "set accuse abuse: " + groupValue.isPublic());
        ((OneLine) ((ListRow) container.findViewById(R.id.lobi_chat_group_info_accuse_abuse)).getContent(1)).setText(0, getString(R.string.lobi_accuse));
        container.setOnClickListener(new OnClickListener() {
            final DefaultAPICallback<PostAccusations> mCallback = new DefaultAPICallback<PostAccusations>(ChatGroupInfoActivity.this) {
                public void onResponse(PostAccusations t) {
                    if (t.success) {
                        showToast(ChatGroupInfoActivity.this.getString(R.string.lobi_reported));
                    }
                }

                private void showToast(final String message) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ChatGroupInfoActivity.this, message, 0).show();
                        }
                    });
                }
            };

            public void onClick(View v) {
                final View newContent = new EditTextContent(ChatGroupInfoActivity.this, ChatGroupInfoActivity.this.getString(R.string.lobi_please_enter_your_reason_of_accusing), null, false);
                final CustomDialog dialog = new CustomDialog(ChatGroupInfoActivity.this, newContent);
                dialog.setTitle(ChatGroupInfoActivity.this.getString(R.string.lobi_please_enter_your_reason_of_accusing));
                dialog.setNegativeButton(ChatGroupInfoActivity.this.getString(17039360), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton(ChatGroupInfoActivity.this.getString(17039370), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        Map<String, String> params = new HashMap();
                        params.put("token", ChatGroupInfoActivity.this.mUserValue.getToken());
                        params.put("group", groupValue.getUid());
                        params.put(RequestKey.REASON, newContent.getText().toString());
                        CoreAPI.postAccusations(params, AnonymousClass12.this.mCallback);
                    }
                });
                dialog.show();
            }
        });
    }

    protected void onDestroy() {
        this.mIsDestroyed = true;
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }

    @SuppressLint({"NewApi"})
    public boolean isDestroyed() {
        if (VERSION.SDK_INT >= 17) {
            return super.isDestroyed();
        }
        return this.mIsDestroyed;
    }
}
