package com.google.android.gms.wallet;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzbib;
import com.google.android.gms.internal.zzbic;
import com.google.android.gms.internal.zzbie;
import com.google.android.gms.internal.zzbif;
import com.google.android.gms.wallet.firstparty.zzc;
import com.google.android.gms.wallet.wobs.zzr;
import java.util.Locale;

public final class Wallet {
    public static final Api<WalletOptions> API = new Api("Wallet.API", zzahd, zzahc);
    public static final Payments Payments = new zzbib();
    private static final zzf<zzbic> zzahc = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzbic, WalletOptions> zzahd = new com.google.android.gms.common.api.Api.zza<zzbic, WalletOptions>() {
        public zzbic zza(Context context, Looper looper, zzg com_google_android_gms_common_internal_zzg, WalletOptions walletOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            if (walletOptions == null) {
                walletOptions = new WalletOptions();
            }
            return new zzbic(context, looper, com_google_android_gms_common_internal_zzg, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, walletOptions.theme, walletOptions.zzbPv);
        }
    };
    public static final zzr zzbPt = new zzbif();
    public static final zzc zzbPu = new zzbie();

    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzzv.zza<R, zzbic> {
        public zza(GoogleApiClient googleApiClient) {
            super(Wallet.API, googleApiClient);
        }

        @VisibleForTesting
        protected abstract void zza(zzbic com_google_android_gms_internal_zzbic) throws RemoteException;
    }

    public static abstract class zzb extends zza<Status> {
        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected Status zzb(Status status) {
            return status;
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzb(status);
        }
    }

    public static final class WalletOptions implements HasOptions {
        public final int environment;
        public final int theme;
        @VisibleForTesting
        final boolean zzbPv;

        public static final class Builder {
            private int mTheme = 0;
            private int zzbPw = 3;
            private boolean zzbPx = true;

            public WalletOptions build() {
                return new WalletOptions();
            }

            public Builder setEnvironment(int i) {
                if (i == 0 || i == 2 || i == 1 || i == 3) {
                    this.zzbPw = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid environment value %d", new Object[]{Integer.valueOf(i)}));
            }

            public Builder setTheme(int i) {
                if (i == 0 || i == 1) {
                    this.mTheme = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid theme value %d", new Object[]{Integer.valueOf(i)}));
            }

            @Deprecated
            public Builder useGoogleWallet() {
                this.zzbPx = false;
                return this;
            }
        }

        private WalletOptions() {
            this(new Builder());
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.zzbPw;
            this.theme = builder.mTheme;
            this.zzbPv = builder.zzbPx;
        }
    }

    private Wallet() {
    }

    @Deprecated
    public static void changeMaskedWallet(GoogleApiClient googleApiClient, String str, String str2, int i) {
        Payments.changeMaskedWallet(googleApiClient, str, str2, i);
    }

    @Deprecated
    public static void checkForPreAuthorization(GoogleApiClient googleApiClient, int i) {
        Payments.checkForPreAuthorization(googleApiClient, i);
    }

    @Deprecated
    public static void loadFullWallet(GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int i) {
        Payments.loadFullWallet(googleApiClient, fullWalletRequest, i);
    }

    @Deprecated
    public static void loadMaskedWallet(GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int i) {
        Payments.loadMaskedWallet(googleApiClient, maskedWalletRequest, i);
    }

    @Deprecated
    public static void notifyTransactionStatus(GoogleApiClient googleApiClient, NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        Payments.notifyTransactionStatus(googleApiClient, notifyTransactionStatusRequest);
    }
}
