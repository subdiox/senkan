package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

@KeepName
public class CommonWalletObject extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Creator<CommonWalletObject> CREATOR = new zza();
    private final int mVersionCode;
    String name;
    int state;
    String zzbOA;
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
    String zzko;

    public final class zza {
        final /* synthetic */ CommonWalletObject zzbQw;

        private zza(CommonWalletObject commonWalletObject) {
            this.zzbQw = commonWalletObject;
        }

        public CommonWalletObject zzSB() {
            return this.zzbQw;
        }

        public zza zzip(String str) {
            this.zzbQw.zzko = str;
            return this;
        }
    }

    CommonWalletObject() {
        this.mVersionCode = 1;
        this.zzbOH = zzb.zzys();
        this.zzbOJ = zzb.zzys();
        this.zzbOM = zzb.zzys();
        this.zzbOO = zzb.zzys();
        this.zzbOP = zzb.zzys();
        this.zzbOQ = zzb.zzys();
    }

    CommonWalletObject(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2, ArrayList<zzp> arrayList, zzl com_google_android_gms_wallet_wobs_zzl, ArrayList<LatLng> arrayList2, String str9, String str10, ArrayList<zzd> arrayList3, boolean z, ArrayList<zzn> arrayList4, ArrayList<zzj> arrayList5, ArrayList<zzn> arrayList6) {
        this.mVersionCode = i;
        this.zzko = str;
        this.zzbOG = str2;
        this.name = str3;
        this.zzbOA = str4;
        this.zzbOC = str5;
        this.zzbOD = str6;
        this.zzbOE = str7;
        this.zzbOF = str8;
        this.state = i2;
        this.zzbOH = arrayList;
        this.zzbOI = com_google_android_gms_wallet_wobs_zzl;
        this.zzbOJ = arrayList2;
        this.zzbOK = str9;
        this.zzbOL = str10;
        this.zzbOM = arrayList3;
        this.zzbON = z;
        this.zzbOO = arrayList4;
        this.zzbOP = arrayList5;
        this.zzbOQ = arrayList6;
    }

    public static zza zzSA() {
        CommonWalletObject commonWalletObject = new CommonWalletObject();
        commonWalletObject.getClass();
        return new zza();
    }

    public String getId() {
        return this.zzko;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }
}
