package com.google.android.gms.wallet.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.internal.zzbhv;
import com.google.android.gms.internal.zzbid;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

@TargetApi(12)
public final class WalletFragment extends Fragment {
    private boolean mCreated = false;
    private final Fragment zzaQn = this;
    private WalletFragmentOptions zzbPT;
    private WalletFragmentInitParams zzbPU;
    private MaskedWalletRequest zzbPV;
    private MaskedWallet zzbPW;
    private Boolean zzbPX;
    private zzb zzbQc;
    private final com.google.android.gms.dynamic.zzb zzbQd = com.google.android.gms.dynamic.zzb.zza(this);
    private final zzc zzbQe = new zzc();
    private zza zzbQf = new zza(this);

    public interface OnStateChangedListener {
        void onStateChanged(WalletFragment walletFragment, int i, int i2, Bundle bundle);
    }

    static class zza extends com.google.android.gms.internal.zzbhw.zza {
        private OnStateChangedListener zzbQg;
        private final WalletFragment zzbQh;

        zza(WalletFragment walletFragment) {
            this.zzbQh = walletFragment;
        }

        public void zza(int i, int i2, Bundle bundle) {
            if (this.zzbQg != null) {
                this.zzbQg.onStateChanged(this.zzbQh, i, i2, bundle);
            }
        }

        public void zza(OnStateChangedListener onStateChangedListener) {
            this.zzbQg = onStateChangedListener;
        }
    }

    private static class zzb implements LifecycleDelegate {
        private final zzbhv zzbQa;

        private zzb(zzbhv com_google_android_gms_internal_zzbhv) {
            this.zzbQa = com_google_android_gms_internal_zzbhv;
        }

        private int getState() {
            try {
                return this.zzbQa.getState();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private void initialize(WalletFragmentInitParams walletFragmentInitParams) {
            try {
                this.zzbQa.initialize(walletFragmentInitParams);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private void onActivityResult(int i, int i2, Intent intent) {
            try {
                this.zzbQa.onActivityResult(i, i2, intent);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private void setEnabled(boolean z) {
            try {
                this.zzbQa.setEnabled(z);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private void updateMaskedWallet(MaskedWallet maskedWallet) {
            try {
                this.zzbQa.updateMaskedWallet(maskedWallet);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            try {
                this.zzbQa.updateMaskedWalletRequest(maskedWalletRequest);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onCreate(Bundle bundle) {
            try {
                this.zzbQa.onCreate(bundle);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) zze.zzE(this.zzbQa.onCreateView(zze.zzA(layoutInflater), zze.zzA(viewGroup), bundle));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onDestroy() {
        }

        public void onDestroyView() {
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.zzbQa.zza(zze.zzA(activity), (WalletFragmentOptions) bundle.getParcelable("extraWalletFragmentOptions"), bundle2);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onLowMemory() {
        }

        public void onPause() {
            try {
                this.zzbQa.onPause();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onResume() {
            try {
                this.zzbQa.onResume();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.zzbQa.onSaveInstanceState(bundle);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onStart() {
            try {
                this.zzbQa.onStart();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void onStop() {
            try {
                this.zzbQa.onStop();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class zzc extends com.google.android.gms.dynamic.zza<zzb> implements OnClickListener {
        final /* synthetic */ WalletFragment zzbQi;

        private zzc(WalletFragment walletFragment) {
            this.zzbQi = walletFragment;
        }

        public void onClick(View view) {
            Context activity = this.zzbQi.zzaQn.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity), activity, -1);
        }

        protected void zza(FrameLayout frameLayout) {
            int i = -1;
            int i2 = -2;
            View button = new Button(this.zzbQi.zzaQn.getActivity());
            button.setText(R.string.wallet_buy_button_place_holder);
            if (this.zzbQi.zzbPT != null) {
                WalletFragmentStyle fragmentStyle = this.zzbQi.zzbPT.getFragmentStyle();
                if (fragmentStyle != null) {
                    DisplayMetrics displayMetrics = this.zzbQi.zzaQn.getResources().getDisplayMetrics();
                    i = fragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                    i2 = fragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new LayoutParams(i, i2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        protected void zza(zzf<zzb> com_google_android_gms_dynamic_zzf_com_google_android_gms_wallet_fragment_WalletFragment_zzb) {
            Activity activity = this.zzbQi.zzaQn.getActivity();
            if (this.zzbQi.zzbQc == null && this.zzbQi.mCreated && activity != null) {
                try {
                    this.zzbQi.zzbQc = new zzb(zzbid.zza(activity, this.zzbQi.zzbQd, this.zzbQi.zzbPT, this.zzbQi.zzbQf));
                    this.zzbQi.zzbPT = null;
                    com_google_android_gms_dynamic_zzf_com_google_android_gms_wallet_fragment_WalletFragment_zzb.zza(this.zzbQi.zzbQc);
                    if (this.zzbQi.zzbPU != null) {
                        this.zzbQi.zzbQc.initialize(this.zzbQi.zzbPU);
                        this.zzbQi.zzbPU = null;
                    }
                    if (this.zzbQi.zzbPV != null) {
                        this.zzbQi.zzbQc.updateMaskedWalletRequest(this.zzbQi.zzbPV);
                        this.zzbQi.zzbPV = null;
                    }
                    if (this.zzbQi.zzbPW != null) {
                        this.zzbQi.zzbQc.updateMaskedWallet(this.zzbQi.zzbPW);
                        this.zzbQi.zzbPW = null;
                    }
                    if (this.zzbQi.zzbPX != null) {
                        this.zzbQi.zzbQc.setEnabled(this.zzbQi.zzbPX.booleanValue());
                        this.zzbQi.zzbPX = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }
    }

    public static WalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        WalletFragment walletFragment = new WalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", walletFragmentOptions);
        walletFragment.zzaQn.setArguments(bundle);
        return walletFragment;
    }

    public int getState() {
        return this.zzbQc != null ? this.zzbQc.getState() : 0;
    }

    public void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzbQc != null) {
            this.zzbQc.initialize(walletFragmentInitParams);
            this.zzbPU = null;
        } else if (this.zzbPU == null) {
            this.zzbPU = walletFragmentInitParams;
            if (this.zzbPV != null) {
                Log.w("WalletFragment", "updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzbPW != null) {
                Log.w("WalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        } else {
            Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.zzbQc != null) {
            this.zzbQc.onActivityResult(i, i2, intent);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzbPU != null) {
                    Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.zzbPU = walletFragmentInitParams;
            }
            if (this.zzbPV == null) {
                this.zzbPV = (MaskedWalletRequest) bundle.getParcelable("maskedWalletRequest");
            }
            if (this.zzbPW == null) {
                this.zzbPW = (MaskedWallet) bundle.getParcelable("maskedWallet");
            }
            if (bundle.containsKey("walletFragmentOptions")) {
                this.zzbPT = (WalletFragmentOptions) bundle.getParcelable("walletFragmentOptions");
            }
            if (bundle.containsKey("enabled")) {
                this.zzbPX = Boolean.valueOf(bundle.getBoolean("enabled"));
            }
        } else if (this.zzaQn.getArguments() != null) {
            WalletFragmentOptions walletFragmentOptions = (WalletFragmentOptions) this.zzaQn.getArguments().getParcelable("extraWalletFragmentOptions");
            if (walletFragmentOptions != null) {
                walletFragmentOptions.zzbO(this.zzaQn.getActivity());
                this.zzbPT = walletFragmentOptions;
            }
        }
        this.mCreated = true;
        this.zzbQe.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzbQe.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.zzbPT == null) {
            this.zzbPT = WalletFragmentOptions.zzc((Context) activity, attributeSet);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("attrKeyWalletFragmentOptions", this.zzbPT);
        this.zzbQe.onInflate(activity, bundle2, bundle);
    }

    public void onPause() {
        super.onPause();
        this.zzbQe.onPause();
    }

    public void onResume() {
        super.onResume();
        this.zzbQe.onResume();
        FragmentManager fragmentManager = this.zzaQn.getActivity().getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (findFragmentByTag != null) {
            fragmentManager.beginTransaction().remove(findFragmentByTag).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzaQn.getActivity()), this.zzaQn.getActivity(), -1);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzbQe.onSaveInstanceState(bundle);
        if (this.zzbPU != null) {
            bundle.putParcelable("walletFragmentInitParams", this.zzbPU);
            this.zzbPU = null;
        }
        if (this.zzbPV != null) {
            bundle.putParcelable("maskedWalletRequest", this.zzbPV);
            this.zzbPV = null;
        }
        if (this.zzbPW != null) {
            bundle.putParcelable("maskedWallet", this.zzbPW);
            this.zzbPW = null;
        }
        if (this.zzbPT != null) {
            bundle.putParcelable("walletFragmentOptions", this.zzbPT);
            this.zzbPT = null;
        }
        if (this.zzbPX != null) {
            bundle.putBoolean("enabled", this.zzbPX.booleanValue());
            this.zzbPX = null;
        }
    }

    public void onStart() {
        super.onStart();
        this.zzbQe.onStart();
    }

    public void onStop() {
        super.onStop();
        this.zzbQe.onStop();
    }

    public void setEnabled(boolean z) {
        if (this.zzbQc != null) {
            this.zzbQc.setEnabled(z);
            this.zzbPX = null;
            return;
        }
        this.zzbPX = Boolean.valueOf(z);
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.zzbQf.zza(onStateChangedListener);
    }

    public void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzbQc != null) {
            this.zzbQc.updateMaskedWallet(maskedWallet);
            this.zzbPW = null;
            return;
        }
        this.zzbPW = maskedWallet;
    }

    public void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.zzbQc != null) {
            this.zzbQc.updateMaskedWalletRequest(maskedWalletRequest);
            this.zzbPV = null;
            return;
        }
        this.zzbPV = maskedWalletRequest;
    }
}
