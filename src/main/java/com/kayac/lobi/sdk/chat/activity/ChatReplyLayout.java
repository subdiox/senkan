package com.kayac.lobi.sdk.chat.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore.Images.Media;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.CustomTextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.LightBox;
import com.kayac.lobi.libnakamap.components.LightBox.OnContentLongClickListener;
import com.kayac.lobi.libnakamap.components.LightBox.OnContentShowListener;
import com.kayac.lobi.libnakamap.components.LightBox.OnDismissListener;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatItemNormalHolder.ChatReplyLayoutHolder;
import com.kayac.lobi.libnakamap.utils.CustomTextViewUtil;
import com.kayac.lobi.libnakamap.utils.PictureUtil;
import com.kayac.lobi.libnakamap.utils.TimeUtil;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import com.kayac.lobi.sdk.utils.ManifestUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;

public class ChatReplyLayout extends LinearLayout {
    public static final int LEFT = R.layout.lobi_chat_reply_layout_left;
    public static final int RIGHT = R.layout.lobi_chat_reply_layout_right;
    private final Context mContext;
    private final TextView mDate;
    private final String mGid;
    private final ImageLoaderView mIcon;
    private final boolean mIsPublic;
    private final TextView mName;
    private final OnLongClickListener mOnBalloonLongClick = new OnLongClickListener() {
        public boolean onLongClick(View v) {
            CharSequence message = ((CustomTextView) ChatReplyLayout.this.findViewById(R.id.lobi_chat_reply_message)).getText();
            if (TextUtils.isEmpty(message)) {
                return false;
            }
            ((ClipboardManager) ChatReplyLayout.this.mContext.getSystemService("clipboard")).setText(message);
            Toast.makeText(ChatReplyLayout.this.mContext, ChatReplyLayout.this.mContext.getString(R.string.lobi_chat_copy_done), 0).show();
            return true;
        }
    };
    private ImageLoaderView mPicture;
    private FrameLayout mPictureContainer;
    private TextView mPictureCount;
    private View mPictureDescription;
    private CustomTextView mReplyMessage;

    public ChatReplyLayout(Context context, ChatReplyLayoutHolder replyLayoutHolder, boolean isPublic, String gid, ChatValue data) {
        super(context);
        this.mContext = context;
        this.mName = replyLayoutHolder.name;
        this.mDate = replyLayoutHolder.date;
        this.mIcon = replyLayoutHolder.icon;
        this.mPictureContainer = replyLayoutHolder.pictureContainer;
        this.mPicture = replyLayoutHolder.picture;
        this.mPictureDescription = replyLayoutHolder.pictureDescription;
        this.mReplyMessage = replyLayoutHolder.replyMessage;
        this.mPictureCount = replyLayoutHolder.pictureCount;
        this.mIcon.setTag(data);
        this.mIsPublic = isPublic;
        this.mGid = gid;
    }

    public void initView() {
        this.mReplyMessage.setVisibility(8);
        this.mPictureContainer.setVisibility(8);
        this.mPicture.setVisibility(8);
    }

    public void setChatReply(ChatValue reply, boolean isShortTime) {
        UserValue user = reply.getUser();
        this.mName.setText(setEmojiName(reply.getUser()));
        if (isShortTime) {
            this.mDate.setText(TimeUtil.getTimeSpan(this.mContext, reply.getCreatedDate()));
        } else {
            this.mDate.setText(TimeUtil.getLongTime(reply.getCreatedDate()));
        }
        this.mIcon.loadImage(user.getIcon());
        final ChatValue chatValue = reply;
        this.mIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatSDKModuleBridge.startChatProfileFromGroup(chatValue.getUser(), ChatReplyLayout.this.mGid);
            }
        });
        CharSequence message = setMessage(reply.getMessage());
        String imagetype = reply.getImageType();
        boolean isStamp = imagetype == null ? false : imagetype.equals("stamp");
        if (isStamp) {
            setOnLongClickListener(null);
        } else if (TextUtils.isEmpty(message)) {
            setOnLongClickListener(null);
        } else {
            setOnLongClickListener(this.mOnBalloonLongClick);
            this.mReplyMessage.setText(message);
            this.mReplyMessage.setOnTextLinkClickedListener(CustomTextViewUtil.getOnTextLinkClickedListener(InvitationWebViewActivity.PATH_INVITATION, " "));
            this.mReplyMessage.setVisibility(0);
        }
        if (reply.getImage() != null) {
            Resources resources;
            this.mPictureContainer.setVisibility(0);
            this.mPictureContainer.setBackgroundColor(0);
            this.mPicture.setVisibility(0);
            int assetsSize = reply.getAssets().size();
            if (isStamp) {
                setStamp(this.mPicture, reply.getImage(), reply.getStampUid());
                this.mPictureContainer.setBackgroundColor(0);
                resources = this.mContext.getResources();
                setLayoutParams(this.mPictureContainer, resources.getDimensionPixelSize(R.dimen.lobi_stamp_thumb_height), resources.getDimensionPixelSize(R.dimen.lobi_stamp_thumb_height));
                this.mPictureContainer.setBackgroundResource(17170445);
            } else {
                resources = this.mContext.getResources();
                MarginLayoutParams params = (MarginLayoutParams) this.mPicture.getLayoutParams();
                int imageSizeWidth = resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_width);
                int imageSizeHeight = resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_height);
                params.width = imageSizeWidth;
                params.height = imageSizeHeight;
                this.mPicture.setLayoutParams(params);
                final LightBox lightBox = new LightBox(this.mContext);
                setLightBox(lightBox);
                chatValue = reply;
                this.mPicture.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ImageView imageView = (ImageView) v;
                        if (chatValue.getAssets() != null) {
                            lightBox.show(imageView, ((Activity) v.getContext()).getWindow());
                        } else {
                            lightBox.show(imageView, ((Activity) v.getContext()).getWindow());
                        }
                    }
                });
            }
            resources = this.mContext.getResources();
            if (assetsSize > 1) {
                if (assetsSize > 50) {
                    this.mPictureContainer.setBackgroundResource(R.drawable.lobi_chat_bg_media_03);
                } else if (assetsSize > 10) {
                    this.mPictureContainer.setBackgroundResource(R.drawable.lobi_chat_bg_media_02);
                } else {
                    this.mPictureContainer.setBackgroundResource(R.drawable.lobi_chat_bg_media_01);
                }
                this.mPictureDescription.setVisibility(0);
                this.mPictureCount.setText(Integer.toString(assetsSize));
                setPicture(this.mPicture, reply.getImage());
                this.mPicture.setScaleType(ScaleType.CENTER_CROP);
                setLayoutParams(this.mPicture, resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_width), resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_height));
                setLayoutParams(this.mPictureContainer, resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_frame_width), resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_frame_height));
                return;
            }
            this.mPictureDescription.setVisibility(8);
            DisplayMetrics metrics = resources.getDisplayMetrics();
            int maxWidth = metrics.widthPixels;
            int maxHeight = metrics.heightPixels;
            this.mPicture.setBaseWidth(maxWidth);
            this.mPicture.setBaseHeigth(maxHeight);
            if (!isStamp) {
                this.mPicture.setScaleType(ScaleType.CENTER_CROP);
                setLayoutParams(this.mPicture, resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_width), resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_height));
                setLayoutParams(this.mPictureContainer, resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_frame_width), resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_frame_height));
                this.mPictureContainer.setBackgroundResource(R.drawable.lobi_chat_bg_media_00);
                this.mPicture.setAdjustViewBounds(false);
                setPicture(this.mPicture, reply.getImage());
            }
        }
    }

    private void setLayoutParams(View view, int width, int height) {
        LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private CharSequence setEmojiName(UserValue user) {
        String name = user.getName();
        if (this.mIsPublic) {
            name = name + (" (" + user.getUid().substring(0, 5) + ")");
        }
        return EmoticonUtil.getEmoticonSpannedText(this.mContext, name);
    }

    private CharSequence setTime(long date) {
        return TimeUtil.getTimeSpan(this.mContext, date);
    }

    private CharSequence setMessage(String message) {
        return EmoticonUtil.getEmoticonSpannedText(this.mContext, message);
    }

    private void setPicture(ImageLoaderView imageView, String image) {
        imageView.loadImage(image.replace("_100.", "_raw."));
    }

    private void setStamp(ImageLoaderView imageView, String image, String stampUid) {
        MarginLayoutParams params = (MarginLayoutParams) imageView.getLayoutParams();
        params.width = -1;
        params.height = -1;
        imageView.setLayoutParams(params);
        imageView.setScaleType(ScaleType.CENTER_INSIDE);
        imageView.loadImage(image);
    }

    private void setLightBox(LightBox lightBox) {
        lightBox.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(ImageView v) {
            }
        });
        if (ManifestUtil.hasWriteExternalStoragePermission(this.mContext)) {
            lightBox.setOnContentShowListener(new OnContentShowListener() {
                public void onContentShow(View content) {
                    Toast.makeText(content.getContext(), ChatReplyLayout.this.mContext.getString(R.string.lobi_save_image_to_gallery), 0).show();
                }
            });
            lightBox.setOnContentLongClickListener(new OnContentLongClickListener() {
                public void onContentLongClick(final View content) {
                    if (!PictureUtil.requestWritePermissionIfNotGranted((Activity) content.getContext())) {
                        final ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.submit(new Runnable() {
                            public void run() {
                                FileNotFoundException e;
                                Throwable th;
                                Bitmap bitmap = ((BitmapDrawable) content.getDrawable()).getBitmap();
                                File file = new File(PictureUtil.getPreferablePlaceForSavingPictures(ChatReplyLayout.this.mContext), "nakamap-" + new Date().getTime() + ".jpg");
                                OutputStream outputStream = null;
                                try {
                                    OutputStream outputStream2 = new FileOutputStream(file);
                                    try {
                                        if (bitmap.isRecycled()) {
                                            IOUtils.closeQuietly(outputStream2);
                                            outputStream = outputStream2;
                                            return;
                                        }
                                        if (bitmap.compress(CompressFormat.JPEG, 80, outputStream2)) {
                                            ContentValues values = new ContentValues();
                                            values.put("_data", file.getAbsolutePath());
                                            values.put("mime_type", "image/jpeg");
                                            ChatReplyLayout.this.mContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
                                        }
                                        IOUtils.closeQuietly(outputStream2);
                                        outputStream = outputStream2;
                                        ((Activity) ChatReplyLayout.this.mContext).runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(content.getContext(), ChatReplyLayout.this.mContext.getString(R.string.lobi_saved_to), 0).show();
                                            }
                                        });
                                        executor.shutdown();
                                    } catch (FileNotFoundException e2) {
                                        e = e2;
                                        outputStream = outputStream2;
                                        try {
                                            e.printStackTrace();
                                            IOUtils.closeQuietly(outputStream);
                                            ((Activity) ChatReplyLayout.this.mContext).runOnUiThread(/* anonymous class already generated */);
                                            executor.shutdown();
                                        } catch (Throwable th2) {
                                            th = th2;
                                            IOUtils.closeQuietly(outputStream);
                                            throw th;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        outputStream = outputStream2;
                                        IOUtils.closeQuietly(outputStream);
                                        throw th;
                                    }
                                } catch (FileNotFoundException e3) {
                                    e = e3;
                                    e.printStackTrace();
                                    IOUtils.closeQuietly(outputStream);
                                    ((Activity) ChatReplyLayout.this.mContext).runOnUiThread(/* anonymous class already generated */);
                                    executor.shutdown();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
