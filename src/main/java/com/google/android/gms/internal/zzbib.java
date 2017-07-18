package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.Wallet.zza;
import com.google.android.gms.wallet.Wallet.zzb;

@SuppressLint({"MissingRemoteException"})
public class zzbib implements Payments {
    public void changeMaskedWallet(GoogleApiClient googleApiClient, String str, String str2, int i) {
        final String str3 = str;
        final String str4 = str2;
        final int i2 = i;
        googleApiClient.zza(new zzb(this, googleApiClient) {
            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zzf(str3, str4, i2);
                zzb(Status.zzayh);
            }
        });
    }

    public void checkForPreAuthorization(GoogleApiClient googleApiClient, final int i) {
        googleApiClient.zza(new zzb(this, googleApiClient) {
            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zzon(i);
                zzb(Status.zzayh);
            }
        });
    }

    public void isNewUser(GoogleApiClient googleApiClient, final int i) {
        googleApiClient.zza(new zzb(this, googleApiClient) {
            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zzoo(i);
                zzb(Status.zzayh);
            }
        });
    }

    public PendingResult<BooleanResult> isReadyToPay(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zza<BooleanResult>(this, googleApiClient) {
            protected BooleanResult zzL(Status status) {
                return new BooleanResult(status, false);
            }

            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zza(IsReadyToPayRequest.newBuilder().build(), (zzzv.zzb) this);
            }

            protected /* synthetic */ Result zzc(Status status) {
                return zzL(status);
            }
        });
    }

    public PendingResult<BooleanResult> isReadyToPay(GoogleApiClient googleApiClient, final IsReadyToPayRequest isReadyToPayRequest) {
        return googleApiClient.zza(new zza<BooleanResult>(this, googleApiClient) {
            protected BooleanResult zzL(Status status) {
                return new BooleanResult(status, false);
            }

            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zza(isReadyToPayRequest, (zzzv.zzb) this);
            }

            protected /* synthetic */ Result zzc(Status status) {
                return zzL(status);
            }
        });
    }

    public void loadFullWallet(GoogleApiClient googleApiClient, final FullWalletRequest fullWalletRequest, final int i) {
        googleApiClient.zza(new zzb(this, googleApiClient) {
            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zza(fullWalletRequest, i);
                zzb(Status.zzayh);
            }
        });
    }

    public void loadMaskedWallet(GoogleApiClient googleApiClient, final MaskedWalletRequest maskedWalletRequest, final int i) {
        googleApiClient.zza(new zzb(this, googleApiClient) {
            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zza(maskedWalletRequest, i);
                zzb(Status.zzayh);
            }
        });
    }

    public void notifyTransactionStatus(GoogleApiClient googleApiClient, final NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        googleApiClient.zza(new zzb(this, googleApiClient) {
            protected void zza(zzbic com_google_android_gms_internal_zzbic) {
                com_google_android_gms_internal_zzbic.zza(notifyTransactionStatusRequest);
                zzb(Status.zzayh);
            }
        });
    }
}
