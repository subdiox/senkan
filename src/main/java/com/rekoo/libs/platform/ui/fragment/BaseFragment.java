package com.rekoo.libs.platform.ui.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.rekoo.libs.utils.ResUtils;

public class BaseFragment extends Fragment {
    public final int MSG_RESPONSE_NULL = 9;
    protected Dialog loadingDialog;

    protected void showLoadingDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(ResUtils.getLayout("loading_dialog", getActivity()), null);
        this.loadingDialog = new Dialog(getActivity(), ResUtils.getStyle("MyDisclaimerDialogStyle", getActivity()));
        this.loadingDialog.setCancelable(false);
        this.loadingDialog.setContentView(view, new LayoutParams(-1, -1));
        this.loadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (this.loadingDialog == null) {
            throw new NullPointerException("you did not call show showLoadingDialog(Context context ,Dialog loadingDialog)");
        }
        this.loadingDialog.dismiss();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
}
