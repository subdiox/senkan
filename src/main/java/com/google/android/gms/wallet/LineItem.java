package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class LineItem extends zza {
    public static final Creator<LineItem> CREATOR = new zzl();
    String description;
    private final int mVersionCode;
    String zzbNQ;
    String zzbNR;
    String zzbOv;
    String zzbOw;
    int zzbOx;

    public final class Builder {
        final /* synthetic */ LineItem zzbOy;

        private Builder(LineItem lineItem) {
            this.zzbOy = lineItem;
        }

        public LineItem build() {
            return this.zzbOy;
        }

        public Builder setCurrencyCode(String str) {
            this.zzbOy.zzbNR = str;
            return this;
        }

        public Builder setDescription(String str) {
            this.zzbOy.description = str;
            return this;
        }

        public Builder setQuantity(String str) {
            this.zzbOy.zzbOv = str;
            return this;
        }

        public Builder setRole(int i) {
            this.zzbOy.zzbOx = i;
            return this;
        }

        public Builder setTotalPrice(String str) {
            this.zzbOy.zzbNQ = str;
            return this;
        }

        public Builder setUnitPrice(String str) {
            this.zzbOy.zzbOw = str;
            return this;
        }
    }

    public interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

    LineItem() {
        this.mVersionCode = 1;
        this.zzbOx = 0;
    }

    LineItem(int i, String str, String str2, String str3, String str4, int i2, String str5) {
        this.mVersionCode = i;
        this.description = str;
        this.zzbOv = str2;
        this.zzbOw = str3;
        this.zzbNQ = str4;
        this.zzbOx = i2;
        this.zzbNR = str5;
    }

    public static Builder newBuilder() {
        LineItem lineItem = new LineItem();
        lineItem.getClass();
        return new Builder();
    }

    public String getCurrencyCode() {
        return this.zzbNR;
    }

    public String getDescription() {
        return this.description;
    }

    public String getQuantity() {
        return this.zzbOv;
    }

    public int getRole() {
        return this.zzbOx;
    }

    public String getTotalPrice() {
        return this.zzbNQ;
    }

    public String getUnitPrice() {
        return this.zzbOw;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzl.zza(this, parcel, i);
    }
}
