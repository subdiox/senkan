package com.kayac.lobi.sdk.activity.ad;

import android.os.Bundle;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.AdVersion;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.sdk.R;
import java.util.HashMap;
import java.util.Map;

public class AdRecommendActivity extends AdBaseActivity {
    public static final String PATH_AD_RECOMMEND = "/ad_recommend";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Map<String, String> params = new HashMap();
        params.put("token", AdUtil.getUserToken());
        init(getResources().getString(R.string.lobi_game_ranking), NotificationValue.SHEME_PATH_AD_RECOMMENDS, params, R.id.lobi_popup_menu_game_ranking);
        TransactionDatastore.setKKValue(AdVersion.KEY1, AdVersion.NEW_GAME_RANKING_AVAILABLE, Boolean.valueOf(false));
    }

    protected void goBackOrFinish() {
        if (this.mWebview == null || !this.mWebview.canGoBack()) {
            finish();
        } else {
            this.mWebview.goBack();
        }
    }

    protected void onResume() {
        super.onResume();
        this.mWebview.reload();
    }
}
