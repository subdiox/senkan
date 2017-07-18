package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.zzd;
import com.google.android.gms.wallet.wobs.zzf;
import com.google.android.gms.wallet.wobs.zzj;
import com.google.android.gms.wallet.wobs.zzl;
import com.google.android.gms.wallet.wobs.zzn;
import com.google.android.gms.wallet.wobs.zzp;
import java.util.ArrayList;

public final class LoyaltyWalletObject extends zza {
    public static final Creator<LoyaltyWalletObject> CREATOR = new zzm();
    private final int mVersionCode;
    int state;
    String zzaIv;
    String zzbOA;
    String zzbOB;
    String zzbOC;
    String zzbOD;
    String zzbOE;
    String zzbOF;
    String zzbOG;
    ArrayList<zzp> zzbOH;
    zzl zzbOI;
    ArrayList<LatLng> zzbOJ;
    String zzbOK;
    String zzbOL;
    ArrayList<zzd> zzbOM;
    boolean zzbON;
    ArrayList<zzn> zzbOO;
    ArrayList<zzj> zzbOP;
    ArrayList<zzn> zzbOQ;
    zzf zzbOR;
    String zzbOz;
    String zzko;

    LoyaltyWalletObject() {
        this.mVersionCode = 4;
        this.zzbOH = zzb.zzys();
        this.zzbOJ = zzb.zzys();
        this.zzbOM = zzb.zzys();
        this.zzbOO = zzb.zzys();
        this.zzbOP = zzb.zzys();
        this.zzbOQ = zzb.zzys();
    }

    LoyaltyWalletObject(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2, ArrayList<zzp> arrayList, zzl com_google_android_gms_wallet_wobs_zzl, ArrayList<LatLng> arrayList2, String str11, String str12, ArrayList<zzd> arrayList3, boolean z, ArrayList<zzn> arrayList4, ArrayList<zzj> arrayList5, ArrayList<zzn> arrayList6, zzf com_google_android_gms_wallet_wobs_zzf) {
        this.mVersionCode = i;
        this.zzko = str;
        this.zzbOz = str2;
        this.zzbOA = str3;
        this.zzbOB = str4;
        this.zzaIv = str5;
        this.zzbOC = str6;
        this.zzbOD = str7;
        this.zzbOE = str8;
        this.zzbOF = str9;
        this.zzbOG = str10;
        this.state = i2;
        this.zzbOH = arrayList;
        this.zzbOI = com_google_android_gms_wallet_wobs_zzl;
        this.zzbOJ = arrayList2;
        this.zzbOK = str11;
        this.zzbOL = str12;
        this.zzbOM = arrayList3;
        this.zzbON = z;
        this.zzbOO = arrayList4;
        this.zzbOP = arrayList5;
        this.zzbOQ = arrayList6;
        this.zzbOR = com_google_android_gms_wallet_wobs_zzf;
    }

    public String getAccountId() {
        return this.zzbOz;
    }

    public String getAccountName() {
        return this.zzaIv;
    }

    public String getBarcodeAlternateText() {
        return this.zzbOC;
    }

    public String getBarcodeType() {
        return this.zzbOD;
    }

    public String getBarcodeValue() {
        return this.zzbOE;
    }

    public String getId() {
        return this.zzko;
    }

    public String getIssuerName() {
        return this.zzbOA;
    }

    public String getProgramName() {
        return this.zzbOB;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }
}
