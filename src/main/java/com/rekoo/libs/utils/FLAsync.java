package com.rekoo.libs.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.kayac.lobi.libnakamap.utils.ChatListUtil;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.FeedbackListCons;
import com.rekoo.libs.entity.Feedback;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FLAsync {
    private static FLAsync flAsync;
    private Context act;
    private Feedback feedback;
    private List<Feedback> list;
    private User user;

    class FLAsyncTask extends AsyncTask<String, Void, String> {
        FLAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String url = params[0];
            String uid = params[1];
            Log.i("TAG", "url+uid" + url + uid);
            String response = NetRequest.getRequest().post(FLAsync.this.act, url, FeedbackListCons.getCons(FLAsync.this.act).getFeedbackListBody(FLAsync.this.act, uid));
            Log.d("TAG", "FLAsync_response:" + response);
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("TAG", "result" + result);
            FLAsync.this.list = new ArrayList();
            if (result != null && JsonUtils.getRC(result)) {
                try {
                    FLAsync.this.user = new User();
                    JSONObject json = new JSONObject(result);
                    Log.e("TAG", "================");
                    JSONArray arr = json.getJSONArray(URLCons.CONTENT);
                    SPUtils.put(FLAsync.this.act, "feedbacklist", result);
                    int a = ((Integer) SPUtils.get(FLAsync.this.act, "lastrefreshtime", Integer.valueOf(0))).intValue();
                    Log.i("TAG", new StringBuilder(ChatListUtil.SUFFIX_ADS).append(a).toString());
                    if (a != arr.length()) {
                        Config.isSolve = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static FLAsync getManager() {
        if (flAsync == null) {
            synchronized (FLAsync.class) {
                if (flAsync == null) {
                    flAsync = new FLAsync();
                }
            }
        }
        return flAsync;
    }

    public void initFLAsync(Context context) {
        this.act = context;
        String uid = Config.currentLoginUser.getUid();
        SPUtils.setFileName("last_refresh");
        try {
            new FLAsyncTask().execute(new String[]{URLCons.URL_FEEDBACK_LIST, uid});
        } catch (Exception e) {
        }
    }
}
