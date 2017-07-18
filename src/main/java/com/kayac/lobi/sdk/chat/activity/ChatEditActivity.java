package com.kayac.lobi.sdk.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChat;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ChatEditUtils;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.GalleryUtil.ImageData;
import com.kayac.lobi.libnakamap.utils.ImageIntentManager;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.utils.PictureUtil;
import com.kayac.lobi.libnakamap.utils.PictureUtil.Res;
import com.kayac.lobi.libnakamap.utils.SelectionManager;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.stamp.StampActivity;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatEditActivity extends PathRoutedActivity {
    public static final String EXTRA_GROUP_DETAIL_VALUE = "EXTRA_GROUP_DETAIL_VALUE";
    public static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_STAMP_COUNT = "EXTRA_STAMP_COUNT";
    public static final String EXTRA_USER_UID = "EXTRA_USER_UID";
    public static final String PATH_CHAT_EDIT = "/grouplist/chat/edit";
    private static final int PICK_PICTURE = 20002;
    protected static final String TAG = "[/group/chat/edit]";
    private static final int TAKE_PHOTO = 20001;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (SelectPictureMenuDialog.ACTION_SELECTED.equals(action) && extras.getInt(SelectPictureMenuDialog.EXTRA_TYPE, -1) == R.id.lobi_select_picture_menu_detach_photo) {
                ChatEditActivity.this.mPictButton.showButton();
            }
            if (!ChatEditActivity.this.mIsPaused) {
                ChatEditActivity.this.mImageIntentManager.onReceive(context, intent);
            }
        }
    };
    private List<ImageData> mFilesToUpload = new ArrayList();
    private final ImageIntentManager mImageIntentManager = new ImageIntentManager(this);
    private boolean mIsPaused = false;
    protected boolean mIsSuicide;
    private TextView mPhotoCount;
    private ChatEditPictureButton mPictButton;
    private View mPostChatButton;
    private CustomProgressDialog mProgress;

    private static final class OnPostGroupChat extends DefaultAPICallback<PostGroupChat> {
        private ChatEditActivity mActivity;
        private GroupDetailValue mGroupDetailValue;
        private View mPostButton;
        private CustomProgressDialog mProgressDialog;

        public OnPostGroupChat(ChatEditActivity context) {
            super(context);
            this.mActivity = context;
        }

        public void setGroupDetail(GroupDetailValue groupDetailValue) {
            this.mGroupDetailValue = groupDetailValue;
        }

        public void setPostButton(View button) {
            this.mPostButton = button;
        }

        public void onResponse(PostGroupChat t) {
            SDKBridge.onPostGroupChat(this.mGroupDetailValue);
            runOnUiThread(new Runnable() {
                public void run() {
                    if (OnPostGroupChat.this.mProgressDialog != null) {
                        OnPostGroupChat.this.mProgressDialog.dismiss();
                    }
                    ((InputMethodManager) OnPostGroupChat.this.mActivity.getSystemService("input_method")).hideSoftInputFromWindow(((EditText) OnPostGroupChat.this.mActivity.findViewById(R.id.lobi_chat_edit)).getWindowToken(), 0);
                    OnPostGroupChat.this.mActivity.finish();
                }
            });
        }

        public void onError(Throwable e) {
            enablePostButton();
            super.onError(e);
        }

        public void onError(int statusCode, String responseBody) {
            enablePostButton();
            super.onError(statusCode, responseBody);
        }

        private void enablePostButton() {
            runOnUiThread(new Runnable() {
                public void run() {
                    OnPostGroupChat.this.mPostButton.setEnabled(true);
                }
            });
        }
    }

    public static void startChatFromShare(String userUid, GroupDetailValue groupDetail, String message, String imagePath) {
        PathRouter.removeAllThePaths();
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_CHAT_EDIT);
        bundle.putString("EXTRA_USER_UID", userUid);
        bundle.putString("gid", groupDetail.getUid());
        bundle.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
        if (!TextUtils.isEmpty(message)) {
            bundle.putString("EXTRA_MESSAGE", message);
        }
        if (!TextUtils.isEmpty(imagePath)) {
            bundle.putString(EXTRA_IMAGE_PATH, imagePath);
        }
        PathRouter.startPath(PathRouterConfig.PATH_CHAT_SDK_DEFAULT);
        PathRouter.startPath(bundle);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        if (extras.containsKey("EXTRA_USER_UID")) {
            AccountDatastore.setCurrentUser(AccountDatastore.getUser(extras.getString("EXTRA_USER_UID")));
        }
        final GroupDetailValue groupDetail = (GroupDetailValue) extras.getParcelable("EXTRA_GROUP_DETAIL_VALUE");
        if (groupDetail == null) {
            finish();
            return;
        }
        setContentView(R.layout.lobi_chat_chat_edit_activity);
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        BackableContent content = (BackableContent) actionBar.getContent();
        content.setText(groupDetail.getName());
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatEditActivity.this.finish();
            }
        });
        DebugAssert.assertNotNull(actionBar);
        View view = findViewById(R.id.lobi_chat_edit_post);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View editPost) {
                GroupDetailValue groupDetail = (GroupDetailValue) extras.getParcelable("EXTRA_GROUP_DETAIL_VALUE");
                if (!groupDetail.isPublic() || groupDetail.getType().equals(GroupValue.MINE) || groupDetail.getType().equals("invited")) {
                    if (ChatEditUtils.getCountSelectedPictures() > 1) {
                        EditText editText = (EditText) ChatEditActivity.this.findViewById(R.id.lobi_chat_edit);
                        ChatEditUtils.postMultiples(ChatEditActivity.this.getApplicationContext(), groupDetail, editText.getText().toString(), null);
                        ((InputMethodManager) ChatEditActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        ChatEditActivity.this.finish();
                    } else {
                        ChatEditActivity.this.postChat(groupDetail, AccountDatastore.getCurrentUser());
                    }
                    editPost.setEnabled(false);
                }
            }
        });
        this.mPostChatButton = view;
        Integer stampCount = Integer.valueOf(extras.getInt(EXTRA_STAMP_COUNT));
        if (stampCount == null) {
            TransactionDatastoreAsync.getStamps(new DatastoreAsyncCallback<List<StampValue>>() {
                public void onResponse(final List<StampValue> t) {
                    ChatEditActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ChatEditActivity.this.setStampButton(groupDetail, t.size());
                        }
                    });
                }
            });
        } else {
            setStampButton(groupDetail, stampCount.intValue());
        }
        this.mPhotoCount = (TextView) findViewById(R.id.lobi_chat_edit_photo_counter);
        this.mPictButton = (ChatEditPictureButton) findViewById(R.id.lobi_chat_edit_picture);
        this.mPictButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SDKBridge.startAlbumFromChatEditActivity(ChatEditActivity.this, groupDetail.getUid(), ChatEditActivity.this.mImageIntentManager.getFile() != null);
            }
        });
        if (extras.containsKey(EXTRA_IMAGE_PATH)) {
            Res res = PictureUtil.onActivityResultPictureTakenHelper(this, null, Uri.parse(extras.getString(EXTRA_IMAGE_PATH)), PictureUtil.CHAT_SIZE, 1, 640);
            if (res.getRaw() == null || res.getThumb() == null) {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            } else {
                this.mPictButton.setImageUri(Uri.fromFile(res.getThumb()));
                this.mPictButton.showThumbnail();
                this.mImageIntentManager.setFile(res.getRaw());
                this.mPhotoCount.setText("");
            }
        }
        UIEditText editText = (UIEditText) findViewById(R.id.lobi_chat_edit);
        final TextView textView = (TextView) findViewById(R.id.lobi_chat_edit_counter);
        editText.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                int left = 255 - text.length();
                textView.setText(left + "");
                textView.setTextColor(left < 0 ? SupportMenu.CATEGORY_MASK : -12303292);
                ChatEditActivity.this.mPostChatButton.setEnabled(left >= 0);
                ChatEditActivity.this.updateSendButton();
            }
        });
        if (extras.containsKey("EXTRA_MESSAGE")) {
            editText.setText(extras.getString("EXTRA_MESSAGE"));
        }
        NakamapBroadcastManager manager = NakamapBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SelectPictureMenuDialog.ACTION_SELECTED);
        manager.registerReceiver(this.mBroadcastReceiver, filter);
        this.mProgress = new CustomProgressDialog(this);
        this.mProgress.setMessage(getString(R.string.lobi_loading_loading));
    }

    protected void updateSendButton() {
        if (((UIEditText) findViewById(R.id.lobi_chat_edit)).getText().length() == 0 && ChatEditUtils.getCountSelectedPictures() == 0 && this.mImageIntentManager.getFile() == null) {
            this.mPostChatButton.setEnabled(false);
        } else {
            this.mPostChatButton.setEnabled(true);
        }
    }

    protected void onPause() {
        super.onPause();
        this.mIsPaused = true;
    }

    protected void onResume() {
        super.onResume();
        this.mIsPaused = false;
    }

    protected void setStampButton(final GroupDetailValue groupDetail, int size) {
        View view = findViewById(R.id.lobi_chat_edit_start_stamp);
        if (view != null) {
            if (size > 0) {
                view.setVisibility(0);
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        StampActivity.startStampEdit(groupDetail);
                    }
                });
                return;
            }
            view.setVisibility(8);
        }
    }

    protected int loadStampFromDisk() {
        return TransactionDatastore.getStamps().size();
    }

    protected void postChat(GroupDetailValue groupDetail, UserValue user) {
        this.mProgress.show();
        DebugAssert.assertNotNull(getIntent().getExtras());
        String gid = groupDetail.getUid();
        String message = ((EditText) findViewById(R.id.lobi_chat_edit)).getText().toString();
        Map<String, String> params = new HashMap();
        params.put("token", user.getToken());
        params.put("uid", gid);
        params.put("type", "normal");
        params.put("message", message);
        File file = this.mImageIntentManager.getFile();
        if (file != null) {
            params.put("image", file.getAbsolutePath());
        }
        OnPostGroupChat callback = new OnPostGroupChat(this);
        callback.setGroupDetail(groupDetail);
        callback.setProgress(this.mProgress);
        callback.setPostButton(this.mPostChatButton);
        CoreAPI.postGroupChat(params, callback);
    }

    protected void onDestroy() {
        SelectionManager.getManager().deleteSelection();
        NakamapBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mBroadcastReceiver);
        this.mImageIntentManager.onDestroy();
        if (this.mProgress != null) {
            this.mProgress.dismiss();
        }
        super.onDestroy();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (this.mImageIntentManager.getFile() == null) {
            ChatEditUtils.setSelectedPicture(getApplicationContext(), this.mPictButton);
            int selected = SelectionManager.getManager().getSelectionSize();
            String counterText = getString(R.string.lobi_chat_number_photos, new Object[]{Integer.valueOf(selected)});
            if (selected > 1) {
                this.mPhotoCount.setText(counterText);
            } else {
                this.mPhotoCount.setText("");
            }
            this.mPictButton.setImageUri(null);
            updateSendButton();
        }
    }

    public void finish() {
        super.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20001 || requestCode == 20002) {
            Res res = this.mImageIntentManager.onActivityResult(requestCode, resultCode, data);
            if (resultCode == -1) {
                if (res == null || res.getRaw() == null || res.getThumb() == null) {
                    Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
                } else {
                    this.mPictButton.setImageUri(Uri.fromFile(res.getThumb()));
                    this.mPictButton.showThumbnail();
                }
            } else if (resultCode == 0) {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            } else {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            }
        }
        updateSendButton();
    }
}
