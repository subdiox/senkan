package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;

@Deprecated
public final class NotifyTransactionStatusRequest extends zza {
    public static final Creator<NotifyTransactionStatusRequest> CREATOR = new zzp();
    final int mVersionCode;
    int status;
    String zzbNX;
    String zzbPj;

    public final class Builder {
        final /* synthetic */ NotifyTransactionStatusRequest zzbPk;

        private Builder(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
            this.zzbPk = notifyTransactionStatusRequest;
        }

        public NotifyTransactionStatusRequest build() {
            boolean z = true;
            zzac.zzb(!TextUtils.isEmpty(this.zzbPk.zzbNX), (Object) "googleTransactionId is required");
            if (this.zzbPk.status < 1 || this.zzbPk.status > 8) {
                z = false;
            }
            zzac.zzb(z, (Object) "status is an unrecognized value");
            return this.zzbPk;
        }

        public Builder setDetailedReason(String str) {
            this.zzbPk.zzbPj = str;
            return this;
        }

        public Builder setGoogleTransactionId(String str) {
            this.zzbPk.zzbNX = str;
            return this;
        }

        public Builder setStatus(int i) {
            this.zzbPk.status = i;
            return this;
        }
    }

    public interface Status {
        public static final int SUCCESS = 1;

        public interface Error {
            public static final int AVS_DECLINE = 7;
            public static final int BAD_CARD = 4;
            public static final int BAD_CVC = 3;
            public static final int DECLINED = 5;
            public static final int FRAUD_DECLINE = 8;
            public static final int OTHER = 6;
            public static final int UNKNOWN = 2;
        }
    }

    NotifyTransactionStatusRequest() {
        this.mVersionCode = 1;
    }

    NotifyTransactionStatusRequest(int i, String str, int i2, String str2) {
        this.mVersionCode = i;
        this.zzbNX = str;
        this.status = i2;
        this.zzbPj = str2;
    }

    public static Builder newBuilder() {
        NotifyTransactionStatusRequest notifyTransactionStatusRequest = new NotifyTransactionStatusRequest();
        notifyTransactionStatusRequest.getClass();
        return new Builder();
    }

    public String getDetailedReason() {
        return this.zzbPj;
    }

    public String getGoogleTransactionId() {
        return this.zzbNX;
    }

    public int getStatus() {
        return this.status;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzp.zza(this, parcel, i);
    }
}
