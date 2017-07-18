package com.kayac.lobi.sdk.utils;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.Version;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class ReferrerUtil {
    private static final String SDK_CLIENT = "sdk_client";
    private static final String SDK_SESSION = "sdk_session";
    private static final String SDK_VERSION = "sdk_version";

    public static final String createMarketUri() {
        JSONObject jsonObject = new JSONObject();
        String referrer = "";
        String session = UUID.randomUUID().toString();
        AccountDatastore.setValue(Key.NAKAMAP_AUTH_SESSION, session);
        try {
            jsonObject.put("sdk_version", Version.versionName);
            jsonObject.put("sdk_client", LobiCore.sharedInstance().getClientId());
            jsonObject.put("sdk_session", session);
            referrer = URLEncoder.encode(jsonObject.toString(), "UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        String marketUri = String.format("https://lobi.co/sp/store?from=chatsdk&referrer=%s", new Object[]{referrer});
        Log.v("nakamap-sdk", "marketUri: " + marketUri);
        return marketUri;
    }
}
