package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class WalletFragmentOptions extends zza implements ReflectedParcelable {
    public static final Creator<WalletFragmentOptions> CREATOR = new zzb();
    private int mTheme;
    final int mVersionCode;
    private int zzaJi;
    private int zzbPw;
    private WalletFragmentStyle zzbQl;

    public final class Builder {
        final /* synthetic */ WalletFragmentOptions zzbQm;

        private Builder(WalletFragmentOptions walletFragmentOptions) {
            this.zzbQm = walletFragmentOptions;
        }

        public WalletFragmentOptions build() {
            return this.zzbQm;
        }

        public Builder setEnvironment(int i) {
            this.zzbQm.zzbPw = i;
            return this;
        }

        public Builder setFragmentStyle(int i) {
            this.zzbQm.zzbQl = new WalletFragmentStyle().setStyleResourceId(i);
            return this;
        }

        public Builder setFragmentStyle(WalletFragmentStyle walletFragmentStyle) {
            this.zzbQm.zzbQl = walletFragmentStyle;
            return this;
        }

        public Builder setMode(int i) {
            this.zzbQm.zzaJi = i;
            return this;
        }

        public Builder setTheme(int i) {
            this.zzbQm.mTheme = i;
            return this;
        }
    }

    private WalletFragmentOptions() {
        this.mVersionCode = 1;
        this.zzbPw = 3;
        this.zzbQl = new WalletFragmentStyle();
    }

    WalletFragmentOptions(int i, int i2, int i3, WalletFragmentStyle walletFragmentStyle, int i4) {
        this.mVersionCode = i;
        this.zzbPw = i2;
        this.mTheme = i3;
        this.zzbQl = walletFragmentStyle;
        this.zzaJi = i4;
    }

    public static Builder newBuilder() {
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.getClass();
        return new Builder();
    }

    public static WalletFragmentOptions zzc(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.WalletFragmentOptions);
        int i = obtainStyledAttributes.getInt(R.styleable.WalletFragmentOptions_appTheme, 0);
        int i2 = obtainStyledAttributes.getInt(R.styleable.WalletFragmentOptions_environment, 1);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.WalletFragmentOptions_fragmentStyle, 0);
        int i3 = obtainStyledAttributes.getInt(R.styleable.WalletFragmentOptions_fragmentMode, 1);
        obtainStyledAttributes.recycle();
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.mTheme = i;
        walletFragmentOptions.zzbPw = i2;
        walletFragmentOptions.zzbQl = new WalletFragmentStyle().setStyleResourceId(resourceId);
        walletFragmentOptions.zzbQl.zzbO(context);
        walletFragmentOptions.zzaJi = i3;
        return walletFragmentOptions;
    }

    public int getEnvironment() {
        return this.zzbPw;
    }

    public WalletFragmentStyle getFragmentStyle() {
        return this.zzbQl;
    }

    public int getMode() {
        return this.zzaJi;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public void zzbO(Context context) {
        if (this.zzbQl != null) {
            this.zzbQl.zzbO(context);
        }
    }
}
