package com.kayac.lobi.sdk.chat.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.sdk.R;

public class ChatEditPictureButton extends FrameLayout {
    private View mButton = ((View) ViewUtils.findViewById(this, R.id.lobi_chat_edit_picture_button_button));
    private ImageView mThumbnail = ((ImageView) ViewUtils.findViewById(this, R.id.lobi_chat_edit_picture_thumbnail));

    public ChatEditPictureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.lobi_chat_edit_picture, this);
    }

    public void setImageUri(Uri uri) {
        Log.v("[picture]", "setImageUri: " + uri);
        this.mThumbnail.setImageURI(uri);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mThumbnail.setImageBitmap(bitmap);
    }

    public void showThumbnail() {
        Log.v("nakamap", "[share] pictButton showThumbnail");
        this.mThumbnail.setVisibility(0);
        this.mButton.setVisibility(8);
    }

    public void showButton() {
        Log.v("nakamap", "[share] pictButton showButton", new RuntimeException());
        this.mThumbnail.setVisibility(8);
        this.mButton.setVisibility(0);
    }
}
