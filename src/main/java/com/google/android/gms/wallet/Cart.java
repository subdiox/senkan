package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;
import java.util.List;

public final class Cart extends zza implements ReflectedParcelable {
    public static final Creator<Cart> CREATOR = new zzc();
    private final int mVersionCode;
    String zzbNQ;
    String zzbNR;
    ArrayList<LineItem> zzbNS;

    public final class Builder {
        final /* synthetic */ Cart zzbNT;

        private Builder(Cart cart) {
            this.zzbNT = cart;
        }

        public Builder addLineItem(LineItem lineItem) {
            this.zzbNT.zzbNS.add(lineItem);
            return this;
        }

        public Cart build() {
            return this.zzbNT;
        }

        public Builder setCurrencyCode(String str) {
            this.zzbNT.zzbNR = str;
            return this;
        }

        public Builder setLineItems(List<LineItem> list) {
            this.zzbNT.zzbNS.clear();
            this.zzbNT.zzbNS.addAll(list);
            return this;
        }

        public Builder setTotalPrice(String str) {
            this.zzbNT.zzbNQ = str;
            return this;
        }
    }

    Cart() {
        this.mVersionCode = 1;
        this.zzbNS = new ArrayList();
    }

    Cart(int i, String str, String str2, ArrayList<LineItem> arrayList) {
        this.mVersionCode = i;
        this.zzbNQ = str;
        this.zzbNR = str2;
        this.zzbNS = arrayList;
    }

    public static Builder newBuilder() {
        Cart cart = new Cart();
        cart.getClass();
        return new Builder();
    }

    public String getCurrencyCode() {
        return this.zzbNR;
    }

    public ArrayList<LineItem> getLineItems() {
        return this.zzbNS;
    }

    public String getTotalPrice() {
        return this.zzbNQ;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
