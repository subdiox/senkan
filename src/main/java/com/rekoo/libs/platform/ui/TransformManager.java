package com.rekoo.libs.platform.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.rekoo.libs.callback.ProduceTransformCallback;
import com.rekoo.libs.callback.TransformCallback;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.TransformCons;
import com.rekoo.libs.entity.ProduceTransform;
import com.rekoo.libs.entity.Transform;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import org.json.JSONException;
import org.json.JSONObject;

public class TransformManager {
    public static TransformManager manager;
    private Context act;
    private ProduceTransformCallback callback;
    private Context context;
    private String mCode;
    private TransformCallback resultcallback;

    class GetTransform extends AsyncTask<String, Void, String> {
        GetTransform() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String transformUrl = params[0];
            String uid = params[1];
            Log.d("TAG", "获取迁移码URL:" + transformUrl);
            String response = NetRequest.getRequest().post(TransformManager.this.context, transformUrl, TransformCons.getCons().getTransformRequestBody(TransformManager.this.context, uid));
            Log.d("TAG", "获取迁移码response：" + response);
            return response;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("TAG", "生成迁移码：" + result);
            if (result == null) {
                TransformManager.this.callback.onFailed(2064);
                return;
            }
            try {
                JSONObject json = new JSONObject(result);
                if (json.getInt("rc") == 0) {
                    JSONObject content = json.getJSONObject(URLCons.CONTENT);
                    ProduceTransform ptf = new ProduceTransform();
                    ptf.setRktransid(content.getString("rktransid"));
                    ptf.setRkuid(content.getString("rkuid"));
                    ptf.setLasttime(content.getString("lasttime"));
                    TransformManager.this.callback.onSuccess(ptf);
                    return;
                }
                TransformManager.this.callback.onFailed(2064);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class SetTransform extends AsyncTask<String, Void, String> {
        SetTransform() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String transformUrl = params[0];
            String transformCode = params[1];
            Log.d("TAG", "验证迁移码URL：" + transformUrl);
            String response = NetRequest.getRequest().post(TransformManager.this.act, transformUrl, TransformCons.getCons().setTransformRequestBody(TransformManager.this.act, transformCode));
            Log.d("TAG", "验证迁移码response：" + response);
            return response;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("TAG", "验证迁移码：" + result);
            if (result == null) {
                TransformManager.this.resultcallback.onFail(2064);
                return;
            }
            try {
                JSONObject json = new JSONObject(result);
                int rc = json.getInt("rc");
                if (rc == 2064) {
                    TransformManager.this.resultcallback.onFail(rc);
                } else if (rc == 0) {
                    JSONObject jsonObject = json.getJSONObject(URLCons.CONTENT);
                    Transform transEntity = new Transform();
                    transEntity.setRkTrans(jsonObject.getString("rktransid"));
                    transEntity.setOldUid(jsonObject.getString("olduid"));
                    transEntity.setNewUid(jsonObject.getString("newuid"));
                    TransformManager.this.resultcallback.onSuccess(transEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private TransformManager() {
    }

    public static TransformManager getInstentce() {
        if (manager == null) {
            synchronized (TransformManager.class) {
                if (manager == null) {
                    manager = new TransformManager();
                }
            }
        }
        return manager;
    }

    public void getTransform(Context ctx, ProduceTransformCallback mResultCallback) {
        this.context = ctx;
        this.callback = mResultCallback;
        new GetTransform().execute(new String[]{URLCons.URL_TRANSFORM, Config.uid});
    }

    public void setTransform(Context ctx, String code, TransformCallback transformCallback) {
        this.act = ctx;
        this.mCode = code;
        this.resultcallback = transformCallback;
        new SetTransform().execute(new String[]{URLCons.URL_CHANGE_ACCOUNT, this.mCode});
    }
}
