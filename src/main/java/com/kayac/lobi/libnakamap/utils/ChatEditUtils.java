package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.internal.view.SupportMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupChat.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChat;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.GalleryUtil.ImageData;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.stamp.StampActivity;
import com.kayac.lobi.sdk.chat.activity.ChatEditPictureButton;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ChatEditUtils {
    public static final int PICK_PICTURE = 20002;
    public static final int TAKE_PHOTO = 20001;

    public interface FileHolder {
        File getFile();
    }

    public static void setSelectedPicture(Context context, ChatEditPictureButton pictureButton) {
        if (!SelectionManager.getManager().getIsToSend()) {
            SelectionManager.getManager().deleteSelection();
        }
        ImageData imageSelected = SelectionManager.getManager().getFirstSelection();
        if (imageSelected != null && "image".equals(imageSelected.getType())) {
            pictureButton.setImageBitmap(ExifUtil.rotateBitmap(imageSelected.getUrl(), ImageUtils.scaleImage(imageSelected.getUrl(), 32, 32)));
            pictureButton.showThumbnail();
        }
    }

    public static void setCountPicture(TextView countPicture, String counterText) {
        int selected = SelectionManager.getManager().getSelectionSize();
        if (selected > 1) {
            countPicture.setText(selected + counterText);
        } else {
            countPicture.setText("");
        }
    }

    public static void clearSelection() {
        SelectionManager.getManager().deleteSelection();
    }

    public static int getCountSelectedPictures() {
        return SelectionManager.getManager().getSelectionSize();
    }

    public static File getSelectedFile(Context context) {
        ImageData selection = SelectionManager.getManager().getFirstSelection();
        if (selection != null) {
            return ImageUtils.createThumbnailFromUri(context, Uri.parse(selection.getUrl()), 960.0f, 80);
        }
        return null;
    }

    public static void postMultiples(Context context, GroupDetailValue groupDetail, String message, String replyTo) {
        SDKBridge.storeSelectedPictures(context, groupDetail, replyTo, message);
        Toast.makeText(context, R.string.lobi_uploading_pictures, 0).show();
    }

    public static void counter(final TextView viewCounter, UIEditText editText) {
        editText.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                int left = 255 - text.length();
                viewCounter.setText(left + "");
                viewCounter.setTextColor(left < 0 ? SupportMenu.CATEGORY_MASK : -12303292);
            }
        });
    }

    public static void counter(UIEditText editText) {
        editText.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                int left = 255 - text.length();
            }
        });
    }

    public static void stampButton(View view, final GroupDetailValue groupDetail) {
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StampActivity.startStamp(groupDetail);
            }
        });
    }

    public static void stampButton(View view, final GroupDetailValue groupDetail, final String chatUid) {
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StampActivity.startStamp(groupDetail, chatUid);
            }
        });
    }

    public static void postChat(DefaultAPICallback<PostGroupChat> callback, String message, String gid, String chatUid, File file) {
        Map<String, String> params = new HashMap();
        params.put("token", AccountDatastore.getCurrentUser().getToken());
        params.put("uid", gid);
        params.put("type", "normal");
        params.put("message", message);
        params.put(RequestKey.OPTION_REPLY_TO, chatUid);
        if (file != null) {
            params.put("image", file.getAbsolutePath());
        }
        CoreAPI.postGroupChat(params, callback);
    }
}
