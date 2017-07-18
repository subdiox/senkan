package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;

public abstract class MegamenuFragment extends LobiFragment {
    private static final int DEFAULT_COLUMN_COUNT = 3;
    private static final String EXTRA_GID = "gid";
    public static final String TAG = MegamenuFragment.class.getName();
    protected MegamenuAdapter mAdapter;
    private OnMenuAnimationListener mAnimationListener;
    private View mBackgroundView;
    private View mDropDownView;
    protected String mGid;
    private OnItemClickListener mGridItemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            MegamenuItem item = MegamenuFragment.this.mAdapter.getItem(position);
            if (item == null || !(item instanceof MegamenuItem)) {
                Log.e(MegamenuFragment.TAG, "item not get.");
            } else if (!view.isEnabled()) {
                Log.i(MegamenuFragment.TAG, "disable view tap.");
            } else if (MegamenuFragment.this.mIsAnimating) {
                Log.w(MegamenuFragment.TAG, "fragment mIsAnimating.");
            } else {
                MegamenuFragment.this.handleMenuAction(item, position);
            }
        }
    };
    protected GridView mGridView;
    private boolean mIsAnimating;
    private boolean mIsShowing;
    private ViewGroup mLayout;
    private FragmentActivity mParentActivity;

    public interface OnMenuAnimationListener {
        void onHideAnimationFinish();

        void onShowAnimationFinish();
    }

    protected abstract MegamenuAdapter getAdapter();

    protected abstract void handleMenuAction(MegamenuItem megamenuItem, int i);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGid = getArguments().getString("gid");
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mParentActivity = getFragmentActivity();
        if (this.mParentActivity == null) {
            throw new RuntimeException("FragmentActivity attach fail.");
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mLayout = (FrameLayout) inflater.inflate(R.layout.lobi_chat_megamenu_popup, container, false);
        this.mBackgroundView = this.mLayout.findViewById(R.id.lobi_chat_megamenu_bgview);
        this.mGridView = (GridView) this.mLayout.findViewById(R.id.lobi_chat_megamenu_grid);
        this.mGridView.setNumColumns(3);
        this.mAdapter = getAdapter();
        this.mGridView.setAdapter(this.mAdapter);
        this.mGridView.setOnItemClickListener(this.mGridItemClickListener);
        this.mLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!MegamenuFragment.this.mIsAnimating) {
                    MegamenuFragment.this.hide(true);
                }
            }
        });
        return this.mLayout;
    }

    public static MegamenuFragment newInstance(Class<? extends MegamenuFragment> clazz, String gid) {
        if (gid == null) {
            throw new IllegalArgumentException("gid required param.");
        }
        MegamenuFragment fragment = null;
        try {
            fragment = (MegamenuFragment) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putString("gid", gid);
        fragment.setArguments(args);
        return fragment;
    }

    public static MegamenuFragment addMegamenuFragment(Class<? extends MegamenuFragment> clazz, FragmentActivity activity, String gid, View dropDownView) {
        MegamenuFragment megamenuFragment = null;
        if (activity == null) {
            Log.e(TAG, "activity is null.");
        } else if (clazz == null) {
            Log.e(TAG, "class is null.");
        } else {
            FragmentManager manager = activity.getSupportFragmentManager();
            Fragment foundFragment = manager.findFragmentByTag(ChatMegamenuFragment.TAG);
            FragmentTransaction transaction = manager.beginTransaction();
            if (foundFragment == null) {
                megamenuFragment = newInstance(clazz, gid);
                transaction.add(R.id.content_holder, megamenuFragment, ChatMegamenuFragment.TAG);
            } else {
                Log.w(TAG, "Fragment is Added already.");
                ChatMegamenuFragment menuFragment = (ChatMegamenuFragment) foundFragment;
            }
            megamenuFragment.setDropDownView(dropDownView);
            transaction.hide(megamenuFragment);
            transaction.commit();
        }
        return megamenuFragment;
    }

    public static void removeMegamenuFragment(FragmentActivity activity) {
        if (activity == null) {
            Log.e(TAG, "activity is null.");
            return;
        }
        Fragment addFragment = activity.getSupportFragmentManager().findFragmentByTag(ChatMegamenuFragment.TAG);
        if (addFragment == null) {
            Log.w(TAG, "Fragment is deleted already.");
        } else {
            activity.getSupportFragmentManager().beginTransaction().remove(addFragment).commitAllowingStateLoss();
        }
    }

    public void show() {
        if (this.mIsAnimating || this.mIsShowing) {
            Log.v(TAG, "Fragments already displayed or animation.");
        } else if (this.mParentActivity != null) {
            this.mParentActivity.getSupportFragmentManager().beginTransaction().show(this).commitAllowingStateLoss();
            if (this.mDropDownView != null) {
                this.mLayout.setPadding(0, this.mDropDownView.getTop() + this.mDropDownView.getHeight(), 0, 0);
            }
            Animation showAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.lobi_chat_megamenu_show);
            showAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    MegamenuFragment.this.mIsAnimating = true;
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    MegamenuFragment.this.mIsAnimating = false;
                    MegamenuFragment.this.mIsShowing = true;
                    if (MegamenuFragment.this.mAnimationListener != null) {
                        MegamenuFragment.this.mAnimationListener.onShowAnimationFinish();
                    }
                }
            });
            this.mGridView.startAnimation(showAnimation);
            Animation fadeAnimation = AnimationUtils.loadAnimation(getActivity(), 17432576);
            fadeAnimation.setDuration((long) getResources().getInteger(R.integer.lobi_chat_megamenu_animation_duration));
            this.mBackgroundView.startAnimation(fadeAnimation);
        }
    }

    public void setDropDownView(View view) {
        this.mDropDownView = view;
    }

    public OnMenuAnimationListener getAnimationListener() {
        return this.mAnimationListener;
    }

    public void setAnimationListener(OnMenuAnimationListener listener) {
        this.mAnimationListener = listener;
    }

    public void hide(boolean animated) {
        if (this.mIsAnimating || !this.mIsShowing) {
            Log.v(TAG, "Fragments, already hidden or animation.");
        } else if (animated) {
            Animation hideAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.lobi_chat_megamenu_hide);
            hideAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    MegamenuFragment.this.mIsAnimating = true;
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    MegamenuFragment.this.onHide();
                }
            });
            this.mGridView.startAnimation(hideAnimation);
            Animation fadeAnimation = AnimationUtils.loadAnimation(getActivity(), 17432577);
            fadeAnimation.setDuration((long) getResources().getInteger(R.integer.lobi_chat_megamenu_animation_duration));
            this.mBackgroundView.startAnimation(fadeAnimation);
        } else {
            onHide();
        }
    }

    private void onHide() {
        this.mIsAnimating = false;
        this.mIsShowing = false;
        hideFragment();
        if (this.mAnimationListener != null) {
            this.mAnimationListener.onHideAnimationFinish();
        }
    }

    private void hideFragment() {
        if (this.mParentActivity != null) {
            this.mParentActivity.getSupportFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
        }
    }

    private FragmentActivity getFragmentActivity() {
        FragmentActivity activity = getActivity();
        if (activity == null || !(activity instanceof FragmentActivity)) {
            Log.w(TAG, "getActivity() invalid.");
            return null;
        } else if (activity.getSupportFragmentManager().findFragmentByTag(ChatMegamenuFragment.TAG) != null) {
            return activity;
        } else {
            Log.w(TAG, "Fragment is not found.");
            return null;
        }
    }
}
