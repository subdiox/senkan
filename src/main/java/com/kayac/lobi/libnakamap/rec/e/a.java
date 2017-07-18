package com.kayac.lobi.libnakamap.rec.e;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.rec.R;
import com.kayac.lobi.sdk.rec.activity.RecPlayActivity;
import com.kayac.lobi.sdk.rec.activity.RecPostVideoActivity;
import java.util.ArrayList;
import java.util.List;
import org.apache.james.mime4j.field.ContentTypeField;

public class a extends DialogFragment implements OnItemClickListener {
    private static final int[] c = new int[]{R.string.lobirec_check_posted_video, R.string.lobirec_share, R.string.lobirec_close};
    private String a;
    private String b;

    public static a a(String str) {
        a aVar = new a();
        Bundle bundle = new Bundle();
        bundle.putString("UPLOAD_URL", str);
        aVar.setArguments(bundle);
        return aVar;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = getArguments().getString("UPLOAD_URL");
        String str = null;
        try {
            str = Uri.parse(this.a).getLastPathSegment();
        } catch (Throwable e) {
            b.a(e);
        }
        this.b = str;
        b.a(RecPostVideoActivity.class.getSimpleName(), "video uid: " + this.b);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Context activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(bundle);
        }
        setCancelable(false);
        List arrayList = new ArrayList();
        for (int string : c) {
            arrayList.add(getString(string));
        }
        Dialog createMultiLineDialog = CustomDialog.createMultiLineDialog(activity, getString(R.string.lobirec_accepted_to_post_video), arrayList, (OnItemClickListener) this);
        createMultiLineDialog.setCancelable(false);
        createMultiLineDialog.setTitle(R.string.lobirec_posted_successfully);
        return createMultiLineDialog;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Activity activity = getActivity();
        if (activity != null && i >= 0 && c.length > i) {
            int i2 = c[i];
            if (i2 == R.string.lobirec_check_posted_video) {
                Bundle bundle = new Bundle();
                bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT);
                bundle.putString(RecPlayActivity.EXTRA_VIDEO_UID, this.b);
                bundle.putBoolean(RecPlayActivity.EXTRA_CAN_GO_BACK_TO_ACTIVITY, true);
                PathRouter.startPath(bundle);
            } else if (i2 == R.string.lobirec_share) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(ContentTypeField.TYPE_TEXT_PLAIN);
                intent.putExtra("android.intent.extra.TEXT", this.a);
                startActivity(Intent.createChooser(intent, getString(R.string.lobirec_share)));
            } else {
                activity.finish();
            }
        }
    }
}
