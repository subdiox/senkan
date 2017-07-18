package com.kayac.lobi.sdk.activity.ad;

import android.os.Bundle;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.AdVersion;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.sdk.R;
import java.util.HashMap;
import java.util.Map;

public class AdPrizeGroupsActivity extends AdBaseActivity {
    public static final String EXTRAS_AD_ID = "EXTRAS_AD_ID";
    public static final String PATH_AD_PRIZE_GROUPS = "/ad_prize_groups";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getData() != null) {
            finish();
            return;
        }
        String adId = getIntent().getStringExtra(EXTRAS_AD_ID);
        Map<String, String> params = new HashMap();
        params.put("token", AdUtil.getUserToken());
        if (!TextUtils.isEmpty(adId)) {
            params.put("ad_id", adId);
        }
        params.put("install_id", "");
        init(getResources().getString(R.string.lobi_prize_groups), "/ad/communities", params, R.id.lobi_popup_menu_prize_groups);
        TransactionDatastore.setKKValue(AdVersion.KEY1, AdVersion.NEW_PRIZE_GROUPS_AVAILABLE, Boolean.valueOf(false));
    }
}
