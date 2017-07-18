package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;

public final class PaymentMethodTokenizationParameters extends zza {
    public static final Creator<PaymentMethodTokenizationParameters> CREATOR = new zzs();
    private final int mVersionCode;
    int zzbPm;
    Bundle zzbPn;

    public final class Builder {
        final /* synthetic */ PaymentMethodTokenizationParameters zzbPo;

        private Builder(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzbPo = paymentMethodTokenizationParameters;
        }

        public Builder addParameter(String str, String str2) {
            zzac.zzh(str, "Tokenization parameter name must not be empty");
            zzac.zzh(str2, "Tokenization parameter value must not be empty");
            this.zzbPo.zzbPn.putString(str, str2);
            return this;
        }

        public PaymentMethodTokenizationParameters build() {
            return this.zzbPo;
        }

        public Builder setPaymentMethodTokenizationType(int i) {
            this.zzbPo.zzbPm = i;
            return this;
        }
    }

    private PaymentMethodTokenizationParameters() {
        this.zzbPn = new Bundle();
        this.mVersionCode = 1;
    }

    PaymentMethodTokenizationParameters(int i, int i2, Bundle bundle) {
        this.zzbPn = new Bundle();
        this.mVersionCode = i;
        this.zzbPm = i2;
        this.zzbPn = bundle;
    }

    public static Builder newBuilder() {
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = new PaymentMethodTokenizationParameters();
        paymentMethodTokenizationParameters.getClass();
        return new Builder();
    }

    public Bundle getParameters() {
        return new Bundle(this.zzbPn);
    }

    public int getPaymentMethodTokenizationType() {
        return this.zzbPm;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzs.zza(this, parcel, i);
    }
}
