package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class FullWallet extends zza implements ReflectedParcelable {
    public static final Creator<FullWallet> CREATOR = new zzg();
    private final int mVersionCode;
    String zzbNX;
    String zzbNY;
    ProxyCard zzbNZ;
    String zzbOa;
    zza zzbOb;
    zza zzbOc;
    String[] zzbOd;
    UserAddress zzbOe;
    UserAddress zzbOf;
    InstrumentInfo[] zzbOg;
    PaymentMethodToken zzbOh;

    private FullWallet() {
        this.mVersionCode = 1;
    }

    FullWallet(int i, String str, String str2, ProxyCard proxyCard, String str3, zza com_google_android_gms_wallet_zza, zza com_google_android_gms_wallet_zza2, String[] strArr, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] instrumentInfoArr, PaymentMethodToken paymentMethodToken) {
        this.mVersionCode = i;
        this.zzbNX = str;
        this.zzbNY = str2;
        this.zzbNZ = proxyCard;
        this.zzbOa = str3;
        this.zzbOb = com_google_android_gms_wallet_zza;
        this.zzbOc = com_google_android_gms_wallet_zza2;
        this.zzbOd = strArr;
        this.zzbOe = userAddress;
        this.zzbOf = userAddress2;
        this.zzbOg = instrumentInfoArr;
        this.zzbOh = paymentMethodToken;
    }

    public UserAddress getBuyerBillingAddress() {
        return this.zzbOe;
    }

    public UserAddress getBuyerShippingAddress() {
        return this.zzbOf;
    }

    public String getEmail() {
        return this.zzbOa;
    }

    public String getGoogleTransactionId() {
        return this.zzbNX;
    }

    public InstrumentInfo[] getInstrumentInfos() {
        return this.zzbOg;
    }

    public String getMerchantTransactionId() {
        return this.zzbNY;
    }

    public String[] getPaymentDescriptions() {
        return this.zzbOd;
    }

    public PaymentMethodToken getPaymentMethodToken() {
        return this.zzbOh;
    }

    public ProxyCard getProxyCard() {
        return this.zzbNZ;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzg.zza(this, parcel, i);
    }
}
