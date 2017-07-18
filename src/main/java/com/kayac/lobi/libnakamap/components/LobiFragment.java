package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.kayac.lobi.sdk.LobiCore;

public class LobiFragment extends Fragment {
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LobiCore.setup(activity);
    }

    public void runOnUiThreadIfAttached(Runnable runnable) {
        Activity activity = getActivity();
        if (activity != null && runnable != null) {
            activity.runOnUiThread(runnable);
        }
    }
}
