package com.kayac.lobi.sdk.activity.stamp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.Button;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.HorizontalElementScroller;
import com.kayac.lobi.libnakamap.components.HorizontalElementScroller.OnSizeChangeListener;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.LobiFragment;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.Styleable;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.AccountDatastoreAsync;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Stamps;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupChat.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetStamps;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChat;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUid;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ChatEditUtils;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.StampValue.Item;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.chat.activity.ChatReplyActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StampActivity extends PathRoutedActivity {
    public static final String CATEGORY_TYPE_HISTORY = "0";
    private static final int COLUMNS = 4;
    public static final String EXTRA_GROUP_DETAIL_VALUE = "EXTRA_GROUP_DETAIL_VALUE";
    public static final String PATH_CHAT_EDIT_STAMP = "/grouplist/chat/edit/stamp";
    public static final String PATH_CHAT_REPLY_STAMP = "/grouplist/chat/reply/stamp";
    public static final String PATH_CHAT_STAMP = "/grouplist/chat/stamp";
    private static final String TAG = "[stamps]";
    private static String mChatUid;
    private static GroupDetailValue mGroupDetail;
    public static int mSeletectedIndex = 0;
    private static boolean mStartFromEdit = false;
    private static final List<StampValue> sStamps = new ArrayList();
    String category = "0";
    private BackableContent mActionBarContent;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        }
    };
    private final List<View> mCategories = new ArrayList();
    private Context mContext;
    private UserValue mCurrentUser;
    private StampFragmentPagerAdapter mPagerAdapter;
    HorizontalElementScroller mStampScroller;
    private ViewPager mViewPager;
    private final DefaultAPICallback<GetStamps> onGetStamps = new DefaultAPICallback<GetStamps>(this) {
        public void onResponse(final GetStamps r) {
            runOnUiThread(new Runnable() {
                public void run() {
                    StampActivity.this.onFetchedStamps(r.items);
                }
            });
            StampActivity.this.stampDiskStore(r.items);
        }
    };

    public static final class CategoryItem extends FrameLayout implements Styleable {
        public void setStyle(Style style) {
        }

        public CategoryItem(Context context) {
            super(context, null);
            setLayoutParams(new LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_horizontal_element_scroller_item, this, true);
        }
    }

    private static final class OnPostChat extends DefaultAPICallback<PostGroupChat> {
        private final Activity mActivity;
        private boolean mIsReply = false;

        public OnPostChat(Activity activity) {
            super(activity);
            this.mActivity = activity;
        }

        public void setDialog(CustomDialog dialog) {
            super.setProgress(dialog);
        }

        public void setIsReply(Boolean isReply) {
            this.mIsReply = isReply.booleanValue();
        }

        public void onResponse(PostGroupChat t) {
            super.onResponse(t);
            if (ChatEditUtils.getCountSelectedPictures() > 0) {
                ChatEditUtils.clearSelection();
            }
            if (StampActivity.mStartFromEdit) {
                StampActivity.mStartFromEdit = false;
                Bundle bundle = new Bundle();
                bundle.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                bundle.putParcelable("EXTRA_GROUP_DETAIL_VALUE", StampActivity.mGroupDetail);
                bundle.putString("gid", StampActivity.mGroupDetail.getUid());
                PathRouter.startPath(bundle);
                return;
            }
            this.mActivity.finish();
        }
    }

    private static class PostGroupJoin extends DefaultAPICallback<PostGroupJoinWithGroupUid> {
        private final StampActivity mActivity;
        private String mStampImage = null;
        private String mStampUid = null;
        private UserValue mUser = null;

        public PostGroupJoin(StampActivity activity) {
            super(activity);
            this.mActivity = activity;
        }

        public void setStampUid(String stampUid) {
            this.mStampUid = stampUid;
        }

        public void setStampImage(String stampImage) {
            this.mStampImage = stampImage;
        }

        public void setUser(UserValue user) {
            this.mUser = user;
        }

        public void onResponse(PostGroupJoinWithGroupUid t) {
            TransactionDatastore.setGroup(t.group, this.mUser.getUid());
            GroupDetailValue res = GroupValueUtils.convertToGroupDetailValue(t.group);
            CategoryValue category = TransactionDatastore.getCategory("public", this.mUser.getUid());
            if (category != null) {
                GroupDetailValue deleteTarget = null;
                for (GroupDetailValue groupDetail : category.getGroupDetails()) {
                    if (groupDetail.getUid().equals(res.getUid())) {
                        deleteTarget = groupDetail;
                        break;
                    }
                }
                if (deleteTarget != null) {
                    category.getGroupDetails().remove(deleteTarget);
                }
                category.getGroupDetails().add(res);
                TransactionDatastore.setCategory(category, this.mUser.getUid());
            }
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    PostGroupJoin.this.mActivity.onPostStamp(PostGroupJoin.this.mStampUid, PostGroupJoin.this.mStampImage);
                }
            });
        }
    }

    public static class StampAdapter extends BaseAdapter {
        private final List<Item> mGridData = new ArrayList();
        private LayoutInflater mInflater;
        private boolean mIsShown = true;

        public StampAdapter(Context context) {
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        public int getCount() {
            return this.mGridData.size();
        }

        public Item getItem(int location) {
            return (Item) this.mGridData.get(location);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public void setIsShown(boolean isShown) {
            this.mIsShown = isShown;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.lobi_stamp_item, parent, false);
                holder = new ViewHolder();
                holder.stamp = (ImageLoaderView) convertView.findViewById(R.id.lobi_stamp_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (this.mIsShown) {
                holder.stamp.loadImage(((Item) this.mGridData.get(position)).getThumb());
            }
            return convertView;
        }

        public void setData(List<Item> items) {
            this.mGridData.clear();
            this.mGridData.addAll(items);
            notifyDataSetChanged();
        }

        public void clearData() {
            this.mGridData.clear();
        }
    }

    private static class StampFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private List<StampValue> mData;
        private int mNumItems;

        public StampFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void clear() {
            this.mNumItems = 0;
            notifyDataSetChanged();
        }

        public void setNumitems(int items) {
            this.mNumItems = items;
        }

        public void setData(List<StampValue> data) {
            this.mData = data;
        }

        public StampPageFragment getItem(int position) {
            return StampPageFragment.newInstance(position, ((StampValue) this.mData.get(position)).getItems());
        }

        public int getCount() {
            return this.mNumItems;
        }

        public int getItemPosition(Object object) {
            return -2;
        }
    }

    public static class StampPageFragment extends LobiFragment {
        private static final String ARGS_NUMBER = "ARGS_NUMBER";
        private static final String ARGS_STAMP_VALUE_ITEM = "ARGS_STAMP_VALUE_ITEM";
        private GridView mGrid;

        public static StampPageFragment newInstance(int n, List<Item> list) {
            StampPageFragment frag = new StampPageFragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_NUMBER, n);
            frag.setArguments(args);
            return frag;
        }

        public void recycleGridViewImages() {
            if (this.mGrid != null) {
                int count = this.mGrid.getChildCount();
                for (int i = 0; i < count; i++) {
                    ImageLoaderView stamp = ((ViewHolder) this.mGrid.getChildAt(i).getTag()).stamp;
                    stamp.cancelLoadImage();
                    stamp.recycleImageSafely();
                }
            }
        }

        public GridView onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
            final StampActivity activity = (StampActivity) getActivity();
            this.mGrid = new GridView(activity);
            this.mGrid.setNumColumns(4);
            final StampAdapter stampAdapter = new StampAdapter(activity);
            if (activity != null) {
                int index = getArguments().getInt(ARGS_NUMBER);
                if (StampActivity.mSeletectedIndex == index) {
                    if (StampActivity.sStamps.size() <= index) {
                        return null;
                    }
                    stampAdapter.setData(((StampValue) StampActivity.sStamps.get(index)).getItems());
                    this.mGrid.setAdapter(stampAdapter);
                }
            }
            this.mGrid.setRecyclerListener(new RecyclerListener() {
                public void onMovedToScrapHeap(View view) {
                    recycleImageSafely(((ViewHolder) view.getTag()).stamp);
                }

                private void recycleImageSafely(ImageLoaderView imageView) {
                    Drawable drawable = imageView.getDrawable();
                    imageView.setImageBitmap(null);
                    if (drawable instanceof BitmapDrawable) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        if (bitmap != null && !bitmap.isRecycled()) {
                            Log.v(StampActivity.TAG, "recycled!");
                            bitmap.recycle();
                        }
                    }
                }
            });
            this.mGrid.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if (position >= 0) {
                        final String uid = stampAdapter.getItem(position).getUid();
                        int state = stampAdapter.getItem(position).getState();
                        final String img = stampAdapter.getItem(position).getImage();
                        final GroupDetailValue groupDetail = (GroupDetailValue) activity.getIntent().getExtras().getParcelable("EXTRA_GROUP_DETAIL_VALUE");
                        if (state != 1) {
                            activity.showStampNotActive(img);
                        } else if (!groupDetail.isPublic() || groupDetail.getType().equals(GroupValue.MINE) || groupDetail.getType().equals("invited")) {
                            activity.onPostStamp(uid, img);
                        } else {
                            final CustomDialog dialog = CustomDialog.createTextDialog(activity, StampPageFragment.this.getString(R.string.lobi_join_this));
                            dialog.setPositiveButton(StampPageFragment.this.getString(17039370), new OnClickListener() {
                                public void onClick(View v) {
                                    UserValue currentUser = AccountDatastore.getCurrentUser();
                                    Map<String, String> params = new HashMap();
                                    params.put("token", currentUser.getToken());
                                    params.put("uid", groupDetail.getUid());
                                    if (StampActivity.mChatUid != null) {
                                        params.put(RequestKey.OPTION_REPLY_TO, StampActivity.mChatUid);
                                    }
                                    PostGroupJoin callback = new PostGroupJoin(activity);
                                    callback.setStampUid(uid);
                                    callback.setStampImage(img);
                                    callback.setUser(currentUser);
                                    CoreAPI.postGroupJoinWithGroupUidV2(params, callback);
                                    dialog.dismiss();
                                }
                            });
                            dialog.setNegativeButton(StampPageFragment.this.getString(17039360), new OnClickListener() {
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                }
            });
            return this.mGrid;
        }

        public void onDestroyView() {
            recycleGridViewImages();
            super.onDestroyView();
        }
    }

    static class ViewHolder {
        ImageLoaderView stamp;

        ViewHolder() {
        }
    }

    public List<StampValue> getListStamp() {
        return sStamps;
    }

    public static void startStamp(GroupDetailValue groupDetail) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_CHAT_STAMP);
        bundle.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
        bundle.putString("gid", groupDetail.getUid());
        PathRouter.startPath(bundle);
    }

    public static void startStampEdit(GroupDetailValue groupDetail) {
        mStartFromEdit = true;
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_CHAT_EDIT_STAMP);
        bundle.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
        bundle.putString("gid", groupDetail.getUid());
        PathRouter.startPath(bundle);
    }

    public static void startStamp(GroupDetailValue groupDetail, String chatUid) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_CHAT_REPLY_STAMP);
        bundle.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
        bundle.putString("gid", groupDetail.getUid());
        bundle.putString(ChatReplyActivity.EXTRA_CHAT_REPLY_CHAT_UID, chatUid);
        PathRouter.startPath(bundle);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.lobi_stamp_activity);
        this.mStampScroller = (HorizontalElementScroller) findViewById(R.id.lobi_stamp_category_scroller);
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        this.mActionBarContent = (BackableContent) actionBar.getContent();
        this.mActionBarContent.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                StampActivity.this.finish();
            }
        });
        new Button(this).setIconImage(R.drawable.lobi_action_bar_button_groupnew_selector_01);
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        mGroupDetail = (GroupDetailValue) extras.getParcelable("EXTRA_GROUP_DETAIL_VALUE");
        mChatUid = extras.getString(ChatReplyActivity.EXTRA_CHAT_REPLY_CHAT_UID);
        this.mCurrentUser = AccountDatastore.getCurrentUser();
    }

    public void onResume() {
        if (this.mStampScroller != null) {
            this.mStampScroller = (HorizontalElementScroller) findViewById(R.id.lobi_stamp_category_scroller);
        }
        if (this.mStampScroller.getElementsNumberInPage() == 0) {
            this.mStampScroller.setOnSizeChangeListener(new OnSizeChangeListener() {
                public void onSizeChange(int w, int h, int oldw, int oldh) {
                    if (oldw == 0 && oldh == 0) {
                        StampActivity.this.getStamps();
                    }
                }
            });
        } else {
            getStamps();
        }
        super.onResume();
    }

    protected void setViewPager() {
        if (this.mViewPager != null) {
            this.mViewPager.removeAllViews();
        }
        this.mViewPager = (ViewPager) findViewById(R.id.lobi_stamp_view_pager);
    }

    protected void getStamps() {
        AccountDatastoreAsync.getKKValue(UpdateAt.KEY1, UpdateAt.GET_STAMPS, Long.valueOf(-1), new DatastoreAsyncCallback<Long>() {
            public void onResponse(Long t) {
                if (System.currentTimeMillis() < t.longValue() + 600000) {
                    final List<StampValue> stamps = StampActivity.this.loadFromDisk();
                    if (stamps.size() == 0) {
                        StampActivity.this.loadFromServer();
                        return;
                    } else {
                        StampActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                StampActivity.this.onFetchedStamps(stamps);
                            }
                        });
                        return;
                    }
                }
                StampActivity.this.loadFromServer();
            }
        });
    }

    protected void loadFromServer() {
        Map<String, String> params = new HashMap();
        params.put("token", this.mCurrentUser.getToken());
        CoreAPI.getStamps(params, this.onGetStamps);
    }

    protected List<StampValue> loadFromDisk() {
        return TransactionDatastore.getStamps();
    }

    protected void stampDiskStore(final List<StampValue> stamps) {
        TransactionDatastoreAsync.deleteAllStamp(new DatastoreAsyncCallback<Void>() {
            public void onResponse(Void t) {
                int len = stamps.size();
                for (int i = 0; i < len; i++) {
                    TransactionDatastore.setStamp((StampValue) stamps.get(i), i);
                }
                AccountDatastore.setKKValue(UpdateAt.KEY1, UpdateAt.GET_STAMPS, Long.valueOf(System.currentTimeMillis()));
            }
        });
    }

    protected void onFetchedStamps(List<StampValue> stamps) {
        StampValue history = loadStampHistoryValues();
        sStamps.clear();
        if (this.mStampScroller != null) {
            this.mStampScroller.clearView();
        }
        if (history.getItems().size() > 0) {
            sStamps.add(loadStampHistoryValues());
        }
        sStamps.addAll(stamps);
        createPagers();
        selectCurrentCategory();
    }

    protected StampValue loadStampHistoryValues() {
        String name = getString(R.string.lobi_history);
        List<Item> items = TransactionDatastore.getStampHistory();
        return new StampValue(this.category, "", name, items);
    }

    protected void createCategories() {
        for (int n = 0; n < sStamps.size(); n++) {
            final int index = n;
            CategoryItem item = new CategoryItem(this.mContext);
            ImageLoaderView iconOn = (ImageLoaderView) item.findViewById(R.id.lobi_horizontal_scroller_image_on);
            if ("0".equals(((StampValue) sStamps.get(n)).getCategory())) {
                iconOn.setImageResource(R.drawable.lobi_icn_recentstamp);
                iconOn.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.lobi_stamp_thumb_small_size_pixel);
                iconOn.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.lobi_stamp_thumb_small_size_pixel);
            } else {
                String imageOn = formatThumbnailOn(((StampValue) sStamps.get(n)).getIcon());
                iconOn.setClearBitmapOnDetach(false);
                iconOn.loadImage(imageOn);
            }
            ((RelativeLayout) item.findViewById(R.id.lobi_horizontal_scroll_element)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    StampActivity.this.selectCategory(index);
                    StampActivity.this.selectPage(index);
                    StampActivity.this.comparePagers(index);
                    StampActivity.this.saveCurrentPosition(index);
                }
            });
            this.mCategories.add(item);
        }
        this.mStampScroller.setData(this.mCategories);
        this.mStampScroller.render();
    }

    protected void selectCategory(int index) {
        changeActionBarText(((StampValue) sStamps.get(index)).getName());
        for (int n = 0; n < sStamps.size(); n++) {
            ((View) this.mCategories.get(n)).findViewById(R.id.lobi_horizontal_scroll_back).setVisibility(4);
        }
        ((View) this.mCategories.get(index)).findViewById(R.id.lobi_horizontal_scroll_back).setVisibility(0);
    }

    protected void selectCurrentCategory() {
        TransactionDatastoreAsync.getKKValue(Stamps.STAMP_CATEGORY, this.mCurrentUser.getUid(), new DatastoreAsyncCallback<Object>() {
            public void onResponse(final Object t) {
                StampActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        StampActivity.this.createCategories();
                        if (t != null) {
                            String currentStamp = t.toString();
                            for (int n = 0; n < StampActivity.sStamps.size(); n++) {
                                if (currentStamp.equals(((StampValue) StampActivity.sStamps.get(n)).getName())) {
                                    StampActivity.this.selectCategory(n);
                                    StampActivity.this.selectPage(n);
                                    StampActivity.this.comparePagers(n);
                                    return;
                                }
                            }
                            return;
                        }
                        StampActivity.this.selectCategory(0);
                        StampActivity.this.selectPage(0);
                    }
                });
            }
        });
    }

    public void createPagers() {
        this.mPagerAdapter = new StampFragmentPagerAdapter(getSupportFragmentManager());
        this.mPagerAdapter.clear();
        this.mPagerAdapter.setNumitems(sStamps.size());
        this.mPagerAdapter.setData(sStamps);
        setViewPager();
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int position) {
                if (StampActivity.this.mContext != null) {
                    StampActivity.mSeletectedIndex = position;
                    StampActivity.this.mPagerAdapter.notifyDataSetChanged();
                    StampActivity.this.selectCategory(position);
                    StampActivity.this.comparePagers(position);
                    StampActivity.this.saveCurrentPosition(position);
                    StampActivity.this.mPagerAdapter.getItem(position);
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    protected void saveCurrentPosition(int index) {
        TransactionDatastoreAsync.setKKValue(Stamps.STAMP_CATEGORY, this.mCurrentUser.getUid(), ((StampValue) sStamps.get(index)).getName(), null);
    }

    protected void selectPage(int index) {
        this.mViewPager.setCurrentItem(index);
        this.mPagerAdapter.notifyDataSetChanged();
    }

    public void onPostStamp(final String stampId, String img) {
        final CustomDialog dialog = CustomDialog.createImageDialog(this.mContext, img);
        dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View v) {
                Bundle extras = StampActivity.this.getIntent().getExtras();
                DebugAssert.assertNotNull(extras);
                GroupDetailValue groupDetail = (GroupDetailValue) extras.getParcelable("EXTRA_GROUP_DETAIL_VALUE");
                TransactionDatastore.addStampHistory(stampId);
                Map<String, String> params = new HashMap();
                params.put("token", StampActivity.this.mCurrentUser.getToken());
                params.put("uid", groupDetail.getUid());
                params.put(RequestKey.OPTION_IMAGE_TYPE, "stamp");
                params.put("type", "normal");
                params.put("image", stampId);
                params.put("message", "");
                if (StampActivity.mChatUid != null) {
                    params.put(RequestKey.OPTION_REPLY_TO, StampActivity.mChatUid);
                }
                OnPostChat callback = new OnPostChat(StampActivity.this);
                callback.setDialog(dialog);
                CoreAPI.postGroupChat(params, callback);
                dialog.hideFooter();
            }
        });
        dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showStampNotActive(String img) {
        final CustomDialog dialog = CustomDialog.createTextDialog(this.mContext, getString(R.string.lobi_this_stamp));
        dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    protected String formatThumbnailOn(String thumb) {
        return thumb.replace(".png", "_on.png");
    }

    protected void changeActionBarText(String txt) {
        this.mActionBarContent.setText(txt);
    }

    protected void comparePagers(int horizontalPagerIndex) {
        int intendedPage = horizontalPagerIndex / this.mStampScroller.getElementsNumberInPage();
        if (intendedPage != this.mStampScroller.getCurrentPage()) {
            this.mStampScroller.setCurrentPage(intendedPage);
        }
    }

    protected void onDestroy() {
        if (this.mStampScroller != null) {
            this.mStampScroller.clearView();
        }
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }
}
