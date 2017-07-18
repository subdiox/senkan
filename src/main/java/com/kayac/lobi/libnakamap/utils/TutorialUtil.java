package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.HelpPopup;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Hint;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.sdk.R;

public class TutorialUtil {

    public interface CommonTutorial {

        public static class Tutorial {
            private final boolean DEBUG = false;
            private int mBackgroundId;
            private Context mContext;
            private boolean mIsShowing = false;
            private String mKey;
            private int mLayoutId;
            private int mLayoutMessageId;
            private int mMessageId;
            private OnDismissListener mOnDismissListener;

            public interface OnDismissListener {
                void onDismiss();
            }

            public Tutorial(Context context, String key, int messageId, int layoutId, int LayoutMessageId, int BackgroundId) {
                this.mKey = key;
                this.mContext = context;
                this.mLayoutId = layoutId;
                this.mLayoutMessageId = LayoutMessageId;
                this.mMessageId = messageId;
                this.mBackgroundId = BackgroundId;
            }

            protected void debug() {
                if (this.mKey != null) {
                    TransactionDatastore.setKKValue(Hint.KEY1, this.mKey, Boolean.FALSE);
                }
            }

            public boolean isShown() {
                if (this.mKey == null) {
                    return false;
                }
                if (TutorialUtil.getIsShown(this.mKey) && ((Boolean) AccountDatastore.getValue(Key.HAS_ACCEPTED_TERMS_OF_USE, Boolean.FALSE)).booleanValue()) {
                    return true;
                }
                return false;
            }

            public void display() {
                showDialog();
            }

            protected void showDialog() {
                this.mIsShowing = true;
                if (this.mKey != null) {
                    TutorialUtil.setIsShown(this.mKey);
                }
                Resources res = this.mContext.getResources();
                int topPadding = res.getDimensionPixelSize(R.dimen.lobi_hint_groups_balloon_triangle_padding);
                int sidePadding = res.getDimensionPixelSize(R.dimen.lobi_hint_groups_balloon_padding);
                int bottomPadding = sidePadding;
                if (this.mBackgroundId == R.drawable.lobi_balloon_top_center_tutorial || this.mBackgroundId == R.drawable.lobi_balloon_top_left_tutorial || this.mBackgroundId == R.drawable.lobi_balloon_top_right_tutorial) {
                    int tmp = topPadding;
                    topPadding = bottomPadding;
                    bottomPadding = tmp;
                }
                HelpPopup help = new HelpPopup(this.mContext);
                if (help.getWindow() != null) {
                    help.getWindow().getAttributes().windowAnimations = R.style.Animations_Fading;
                }
                help.setContentView(this.mLayoutId);
                help.setAuto(false);
                TextView textView = (TextView) help.findViewById(this.mLayoutMessageId);
                textView.setText(this.mMessageId);
                textView.setBackgroundResource(this.mBackgroundId);
                textView.setPadding(sidePadding, topPadding, sidePadding, bottomPadding);
                help.show();
                if (this.mOnDismissListener != null) {
                    help.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            Tutorial.this.mIsShowing = false;
                            Tutorial.this.mOnDismissListener.onDismiss();
                        }
                    });
                }
            }

            public void setOnDismissListener(OnDismissListener listener) {
                this.mOnDismissListener = listener;
            }

            public boolean isShowing() {
                return this.mIsShowing;
            }
        }
    }

    public static class TutorialAccountSwitch implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_bottom_left_tutorial;
        protected static final String mKey = null;
        protected static final int mLayoutMessageResId = R.id.lobi_hint_account_switch_here_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_account_switch_here;
        protected static final int mMessageResId = R.string.lobi_tutorial_account_switch;

        public static Tutorial build(Context context) {
            return new Tutorial(context, mKey, mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialCommunityButton implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_top_right_tutorial;
        protected static final String mKey = "COMMUNITY_BUTTON_HINT_SHOWN";
        protected static final int mLayoutMessageResId = R.id.lobi_hint_community_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_community_button;
        protected static final int mMessageResId = R.string.lobi_community_button_tutorial;

        public static Tutorial build(Context context) {
            return new Tutorial(context, "COMMUNITY_BUTTON_HINT_SHOWN", mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialContactListFacebook implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_center_tutorial;
        protected static final String mKey = "CONTACT_HINT_SHOW";
        protected static final int mLayoutMessageResId = R.id.lobi_hint_groups_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_contacts_facebook;
        protected static final int mMessageResId = R.string.lobi_contact_tutorial;

        public static Tutorial build(Context context) {
            return new Tutorial(context, "CONTACT_HINT_SHOW", mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialContactListMail implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_right_tutorial;
        protected static final String mKey = "CONTACT_HINT_SHOW";
        protected static final int mLayoutMessageResId = R.id.lobi_hint_groups_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_contacts_mail;
        protected static final int mMessageResId = R.string.lobi_contact_tutorial;

        public static Tutorial build(Context context) {
            return new Tutorial(context, "CONTACT_HINT_SHOW", mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialContactListTwitter implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_left_tutorial;
        protected static final String mKey = "CONTACT_HINT_SHOW";
        protected static final int mLayoutMessageResId = R.id.lobi_hint_groups_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_contacts_twitter;
        protected static final int mMessageResId = R.string.lobi_contact_tutorial;

        public static Tutorial build(Context context) {
            return new Tutorial(context, "CONTACT_HINT_SHOW", mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialContactService implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_bottom_right_tutorial;
        protected static final String mKey = "CONTACT_INVITE_HINT_SHOWN";
        protected static final int mLayoutMessageResId = R.id.lobi_hint_groups_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_contacts_service;
        protected static final int mMessageResId = R.string.lobi_contact_service_tutorial;

        public static Tutorial build(Context context) {
            return new Tutorial(context, "CONTACT_INVITE_HINT_SHOWN", mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialPrivateGroup implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_bottom_left_tutorial;
        protected static final String mKey = null;
        protected static final int mLayoutMessageResId = R.id.lobi_hint_private_group_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_private_group;
        protected static final int mMessageResId = R.string.lobi_this_is_a_closed;

        public static Tutorial build(Context context) {
            return new Tutorial(context, mKey, mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialPublicGroup implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_bottom_right_tutorial;
        protected static final String mKey = null;
        protected static final int mLayoutMessageResId = R.id.lobi_hint_public_group_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_public_group;
        protected static final int mMessageResId = R.string.lobi_this_is_a_open;

        public static Tutorial build(Context context) {
            return new Tutorial(context, mKey, mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static class TutorialStamp implements CommonTutorial {
        protected static final int mBackgroundResId = R.drawable.lobi_balloon_bottom_right_tutorial;
        protected static final String mKey = "STAMP_HINT_SHOWN";
        protected static final int mLayoutMessageResId = R.id.lobi_hint_groups_message;
        protected static final int mLayoutResId = R.layout.lobi_hint_stamps;
        protected static final int mMessageResId = R.string.lobi_get_new;

        public static Tutorial build(Context context) {
            return new Tutorial(context, "STAMP_HINT_SHOWN", mMessageResId, mLayoutResId, mLayoutMessageResId, mBackgroundResId);
        }
    }

    public static void setIsShown(String key) {
        TransactionDatastore.setKKValue(Hint.KEY1, key, Boolean.TRUE);
    }

    public static void setIsShown(String key, boolean isShown) {
        TransactionDatastore.setKKValue(Hint.KEY1, key, Boolean.valueOf(isShown));
    }

    public static boolean getIsShown(String key) {
        return ((Boolean) TransactionDatastore.getKKValue(Hint.KEY1, key, Boolean.FALSE)).booleanValue();
    }

    public static boolean getIsShownForNewUser(String key) {
        return ((Boolean) TransactionDatastore.getKKValue(Hint.KEY1, key, Boolean.TRUE)).booleanValue();
    }

    public static void resetIsShownForNewUser() {
        TransactionDatastore.setKKValue(Hint.KEY1, Hint.JOINED_GROUP_HINT_SHOWN, Boolean.FALSE);
        TransactionDatastore.setKKValue(Hint.KEY1, Hint.JOINED_PUBLIC_GROUP, Boolean.FALSE);
    }
}
