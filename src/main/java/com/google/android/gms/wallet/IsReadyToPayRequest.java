package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

public final class IsReadyToPayRequest extends zza {
    public static final Creator<IsReadyToPayRequest> CREATOR = new zzk();
    final int mVersionCode;
    ArrayList<Integer> zzbOt;

    public final class Builder {
        final /* synthetic */ IsReadyToPayRequest zzbOu;

        private Builder(IsReadyToPayRequest isReadyToPayRequest) {
            this.zzbOu = isReadyToPayRequest;
        }

        public Builder addAllowedCardNetwork(int i) {
            if (this.zzbOu.zzbOt == null) {
                this.zzbOu.zzbOt = new ArrayList();
            }
            this.zzbOu.zzbOt.add(Integer.valueOf(i));
            return this;
        }

        public IsReadyToPayRequest build() {
            return this.zzbOu;
        }
    }

    IsReadyToPayRequest() {
        this.mVersionCode = 1;
    }

    IsReadyToPayRequest(int i, ArrayList<Integer> arrayList) {
        this.mVersionCode = i;
        this.zzbOt = arrayList;
    }

    public static Builder newBuilder() {
        IsReadyToPayRequest isReadyToPayRequest = new IsReadyToPayRequest();
        isReadyToPayRequest.getClass();
        return new Builder();
    }

    public ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzbOt;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzk.zza(this, parcel, i);
    }
}
