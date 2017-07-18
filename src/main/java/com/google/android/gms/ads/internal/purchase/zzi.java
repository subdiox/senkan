package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.customsdk.rekoo.util.IabHelper;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.internal.zzmb;
import com.google.android.gms.internal.zzoy;
import com.google.android.gms.internal.zzpe;
import org.json.JSONException;
import org.json.JSONObject;

@zzmb
public class zzi {
    public void zza(Context context, boolean z, GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel) {
        Intent intent = new Intent();
        intent.setClassName(context, "com.google.android.gms.ads.purchase.InAppPurchaseActivity");
        intent.putExtra("com.google.android.gms.ads.internal.purchase.useClientJar", z);
        GInAppPurchaseManagerInfoParcel.zza(intent, gInAppPurchaseManagerInfoParcel);
        zzv.zzcJ().zzb(context, intent);
    }

    public String zzaD(String str) {
        String str2 = null;
        if (str != null) {
            try {
                str2 = new JSONObject(str).getString("developerPayload");
            } catch (JSONException e) {
                zzpe.zzbe("Fail to parse purchase data");
            }
        }
        return str2;
    }

    public String zzaE(String str) {
        String str2 = null;
        if (str != null) {
            try {
                str2 = new JSONObject(str).getString("purchaseToken");
            } catch (JSONException e) {
                zzpe.zzbe("Fail to parse purchase data");
            }
        }
        return str2;
    }

    public int zzd(Intent intent) {
        if (intent == null) {
            return 5;
        }
        Object obj = intent.getExtras().get(IabHelper.RESPONSE_CODE);
        if (obj == null) {
            zzpe.zzbe("Intent with no response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            String str = "Unexpected type for intent response code. ";
            String valueOf = String.valueOf(obj.getClass().getName());
            zzpe.zzbe(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return 5;
        }
    }

    public int zzd(Bundle bundle) {
        Object obj = bundle.get(IabHelper.RESPONSE_CODE);
        if (obj == null) {
            zzpe.zzbe("Bundle with null response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            String str = "Unexpected type for intent response code. ";
            String valueOf = String.valueOf(obj.getClass().getName());
            zzpe.zzbe(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return 5;
        }
    }

    public String zze(Intent intent) {
        return intent == null ? null : intent.getStringExtra(IabHelper.RESPONSE_INAPP_PURCHASE_DATA);
    }

    public String zzf(Intent intent) {
        return intent == null ? null : intent.getStringExtra(IabHelper.RESPONSE_INAPP_SIGNATURE);
    }

    public void zzr(final Context context) {
        ServiceConnection anonymousClass1 = new ServiceConnection(this) {
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                boolean z = false;
                zzb com_google_android_gms_ads_internal_purchase_zzb = new zzb(context.getApplicationContext(), false);
                com_google_android_gms_ads_internal_purchase_zzb.zzV(iBinder);
                int zzb = com_google_android_gms_ads_internal_purchase_zzb.zzb(3, context.getPackageName(), IabHelper.ITEM_TYPE_INAPP);
                zzoy zzcN = zzv.zzcN();
                if (zzb == 0) {
                    z = true;
                }
                zzcN.zzH(z);
                zza.zzyc().zza(context, this);
                com_google_android_gms_ads_internal_purchase_zzb.destroy();
            }

            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        zza.zzyc().zza(context, intent, anonymousClass1, 1);
    }
}
