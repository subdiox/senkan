package com.rekoo.libs.push;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.ReadCons;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;

public class Read {
    private static Read read;
    private Context act;

    class ReadAsynctask extends AsyncTask<String, Void, String> {
        ReadAsynctask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String response = NetRequest.getRequest().post(Read.this.act, params[0], ReadCons.getCons().getReadRequestBody(Read.this.act, params[1], params[2]));
            Log.d("TAG", "Read response:" + response);
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("TAG", "read+result" + result);
        }
    }

    public static Read getManager() {
        if (read == null) {
            synchronized (Read.class) {
                if (read == null) {
                    read = new Read();
                }
            }
        }
        return read;
    }

    public void initRead(Context context) {
        this.act = context;
        String sendno = new StringBuilder(String.valueOf(Config.sendno)).toString();
        String uid = "";
        try {
            uid = Config.currentLoginUser.getUid();
        } catch (Exception e) {
            Log.i("RKSDK", "发送推送已读-uid获取失败");
        }
        try {
            new ReadAsynctask().execute(new String[]{URLCons.URL_MESSAGE_READ, uid, sendno});
        } catch (Exception e2) {
        }
    }
}
