package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.kayac.lobi.libnakamap.utils.ImageIntentManager;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.List;

public class SelectPictureMenuDialog extends DialogFragment {
    public static final String ACTION_SELECTED = (SelectPictureMenuDialog.class.getCanonicalName() + ".ACTION_SELECTED");
    private static final String ARGS_DETACH_LABEL = "ARGS_DETACH_LABEL";
    private static final String ARGS_IS_SET = "ARGS_IS_SET";
    private static final String ARGS_PICTURE_KEY = "ARGS_PICTURE_KEY";
    private static final String ARGS_TITLE = "ARGS_TITLE";
    public static final String EXTRA_PICTURE_KEY = "EXTRA_PICTURE_KEY";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    private CustomDialog mDialog = null;

    public static SelectPictureMenuDialog newInstance(String pictureKey, int titleResId, boolean isSet, int detachLabelResId) {
        SelectPictureMenuDialog frag = new SelectPictureMenuDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_PICTURE_KEY, pictureKey);
        args.putInt(ARGS_TITLE, titleResId);
        args.putBoolean(ARGS_IS_SET, isSet);
        args.putInt(ARGS_DETACH_LABEL, detachLabelResId);
        frag.setArguments(args);
        return frag;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<String> items = new ArrayList();
        final List<Integer> ids = new ArrayList();
        final Activity activity = getActivity();
        Bundle arguments = getArguments();
        if (ImageIntentManager.isCameraAvailable()) {
            items.add(activity.getString(R.string.lobi_take));
            ids.add(Integer.valueOf(R.id.lobi_select_picture_menu_take_photo));
        }
        items.add(activity.getString(R.string.lobi_choose_from_library));
        ids.add(Integer.valueOf(R.id.lobi_select_picture_menu_select_photo));
        if (arguments.getBoolean(ARGS_IS_SET)) {
            items.add(activity.getString(arguments.getInt(ARGS_DETACH_LABEL)));
            ids.add(Integer.valueOf(R.id.lobi_select_picture_menu_detach_photo));
        }
        final String pictureKey = arguments.getString(ARGS_PICTURE_KEY);
        this.mDialog = CustomDialog.createMultiLineDialog(activity, items, new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                NakamapBroadcastManager lbm = NakamapBroadcastManager.getInstance(activity);
                Intent intent = new Intent(SelectPictureMenuDialog.ACTION_SELECTED);
                intent.putExtra(SelectPictureMenuDialog.EXTRA_PICTURE_KEY, pictureKey);
                intent.putExtra(SelectPictureMenuDialog.EXTRA_TYPE, ((Integer) ids.get(position)).intValue());
                lbm.sendBroadcast(intent);
                SelectPictureMenuDialog.this.mDialog.dismiss();
                SelectPictureMenuDialog.this.mDialog = null;
            }
        });
        this.mDialog.setTitle(activity.getString(arguments.getInt(ARGS_TITLE)));
        return this.mDialog;
    }

    public void onCancel(DialogInterface arg0) {
        this.mDialog.dismiss();
        this.mDialog = null;
        super.onCancel(arg0);
    }

    public void onDestroy() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
        super.onDestroy();
    }
}
