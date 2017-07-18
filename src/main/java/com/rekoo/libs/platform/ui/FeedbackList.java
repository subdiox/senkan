package com.rekoo.libs.platform.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.rekoo.libs.adapter.FeedbackListAdapter;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.FeedbackListCons;
import com.rekoo.libs.entity.Feedback;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.JsonUtils;
import com.rekoo.libs.utils.ResUtils;
import com.rekoo.libs.utils.SPUtils;
import com.squareup.okhttp.RequestBody;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackList extends Activity {
    public static FeedbackList feedbackList;
    private Context act;
    private OnClickListener backClick = new OnClickListener() {
        public void onClick(View v) {
            FeedbackList.this.finish();
        }
    };
    private Button btn_feedback_list_back;
    private Feedback feedback;
    private List<Feedback> list;
    private ListView lv_feedback_list;

    class FeedbackListAsync extends AsyncTask<String, Void, String> {
        FeedbackListAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String url = params[0];
            String uid = params[1];
            Log.i("TAG", "url+uid" + url + uid);
            RequestBody body = FeedbackListCons.getCons(FeedbackList.this.act).getFeedbackListBody(FeedbackList.this.act, uid);
            Log.d("TAG", "feedbacklist");
            return NetRequest.getRequest().post(FeedbackList.this.act, url, body);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("TAG", "result" + result);
            FeedbackList.this.list = new ArrayList();
            if (result != null && JsonUtils.getRC(result)) {
                try {
                    JSONObject json = new JSONObject(result);
                    Log.i("TAG", "----------");
                    JSONArray arr = json.getJSONArray(URLCons.CONTENT);
                    SPUtils.put(FeedbackList.this.act, "lastrefreshtime", Integer.valueOf(arr.length()));
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json1 = arr.getJSONObject(i);
                        Log.i("TAG", "++++++++++");
                        int category = json1.getInt("category");
                        String status = json1.getString("status");
                        String submiter = json1.getString("submiter");
                        String datetime = json1.getString("datetime");
                        String content = json1.getString(URLCons.CONTENT);
                        int fid = json1.getInt("fid");
                        FeedbackList.this.feedback = new Feedback();
                        FeedbackList.this.feedback.setCategory(category);
                        FeedbackList.this.feedback.setContent(content);
                        FeedbackList.this.feedback.setDatetime(datetime);
                        FeedbackList.this.feedback.setStatus(status);
                        FeedbackList.this.feedback.setSubmiter(submiter);
                        FeedbackList.this.feedback.setFid(fid);
                        FeedbackList.this.list.add(FeedbackList.this.feedback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.act = this;
        super.onCreate(savedInstanceState);
        int ViewId = ResUtils.getLayout("feedback_list", this.act);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(ViewId);
        BIConfig.getBiConfig().backlistShow(this.act);
        init();
        initView();
        Config.isSolve = false;
    }

    private void init() {
        this.list = new ArrayList();
        String result = (String) SPUtils.get(this.act, "feedbacklist", "feedbacklist");
        if (result != null && JsonUtils.getRC(result)) {
            try {
                JSONObject json = new JSONObject(result);
                Log.i("TAG", "----------");
                JSONArray arr = json.getJSONArray(URLCons.CONTENT);
                SPUtils.put(this.act, "lastrefreshtime", Integer.valueOf(arr.length()));
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json1 = arr.getJSONObject(i);
                    Log.i("TAG", "++++++++++");
                    int category = json1.getInt("category");
                    String status = json1.getString("status");
                    String submiter = json1.getString("submiter");
                    String datetime = json1.getString("datetime");
                    String content = json1.getString(URLCons.CONTENT);
                    int fid = json1.getInt("fid");
                    this.feedback = new Feedback();
                    this.feedback.setCategory(category);
                    this.feedback.setContent(content);
                    this.feedback.setDatetime(datetime);
                    this.feedback.setStatus(status);
                    this.feedback.setSubmiter(submiter);
                    this.feedback.setFid(fid);
                    this.list.add(this.feedback);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        this.btn_feedback_list_back = (Button) findViewById(ResUtils.getId("btn_feedback_list_back", this.act));
        this.btn_feedback_list_back.setOnClickListener(this.backClick);
        this.lv_feedback_list = (ListView) findViewById(ResUtils.getId("lv_feedback_list", this.act));
        this.lv_feedback_list.setAdapter(new FeedbackListAdapter(this.act, this.list));
    }
}
