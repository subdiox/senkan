package com.rekoo.libs.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gcm.GCMConstants;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private GCMIntentService intentService;

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("action", "action" + action);
        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            Log.i("data1", new StringBuilder(String.valueOf(key)).append(" - ").append(bundle.get(key)).toString());
        }
        intent.getScheme();
        try {
            if (action.equals(GCMConstants.INTENT_FROM_GCM_REGISTRATION_CALLBACK)) {
                String regId = intent.getStringExtra(GCMConstants.EXTRA_REGISTRATION_ID);
                if (regId == null) {
                    BIConfig.getBiConfig().getPushTokenFail(context);
                    Config.regId = "0";
                } else {
                    Config.regId = regId;
                    Log.i("TAG", "Config.regId" + Config.regId);
                    Log.i("regId", regId);
                    BIConfig.getBiConfig().getPushTokenSuccess(context);
                    BIConfig.getBiConfig().initSuccess(context);
                }
                Log.e("RKSDK", "初始化：pushToken-Config.regId===" + Config.regId);
                Log.i("TAG", "初始化成功");
                Log.i("RKSDK", "pushToken:" + regId);
                Config.isInit = true;
                setResultCode(200);
            }
            if (action.equals(GCMConstants.INTENT_FROM_GCM_MESSAGE)) {
                Log.i("RKSDK", "收到推送消息");
                this.intentService = new GCMIntentService();
                this.intentService.onMessage(context, intent);
            }
            setResultCode(200);
        } catch (Exception e) {
            Log.i("rk_receive", "brocast exception");
        }
    }
}
