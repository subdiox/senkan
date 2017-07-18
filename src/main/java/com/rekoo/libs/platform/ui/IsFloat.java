package com.rekoo.libs.platform.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.ForumCons;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import org.json.JSONException;
import org.json.JSONObject;

public class IsFloat {
    private static IsFloat floatManager;
    private Context act;
    private Boolean isShowFloat = Boolean.valueOf(false);
    private User user;

    private class FloatManagerAsycTask extends AsyncTask<String, Void, String> {
        private FloatManagerAsycTask() {
        }

        protected String doInBackground(String... params) {
            String url = params[0];
            if (url == null) {
                return null;
            }
            return NetRequest.getRequest().post(IsFloat.this.act, url, ForumCons.getCons().getForumReqyestBody(IsFloat.this.act));
        }

        protected void onPostExecute(String result) {
            Log.e("TAG", "---" + result);
            if (result != null) {
                try {
                    JSONObject json1 = new JSONObject(result).getJSONObject(URLCons.CONTENT);
                    Config.forumUrl = json1.getString("url");
                    int isRun = json1.getInt("isrun");
                    int isShowbar = json1.getInt("isShowbar");
                    int isFeedback = json1.getInt("isFeedback");
                    if (isRun == 1) {
                        Config.isRun = true;
                    }
                    if (isShowbar == 1) {
                        Config.isShowbar = true;
                    }
                    if (isFeedback == 1) {
                        Config.isFeedback = true;
                    } else if (isFeedback == 0) {
                        Config.isFeedback = false;
                    }
                    Config.isInit = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }

    private IsFloat() {
    }

    public static IsFloat getManager() {
        if (floatManager == null) {
            synchronized (IsFloat.class) {
                if (floatManager == null) {
                    floatManager = new IsFloat();
                }
            }
        }
        return floatManager;
    }

    public void initFloat(Context context) {
        this.act = context;
        try {
            new FloatManagerAsycTask().execute(new String[]{URLCons.URL_BBS_STATUS});
        } catch (Exception e) {
        }
    }
}
