package com.rekoo.libs.platform.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rekoo.libs.BaseActivity;
import com.rekoo.libs.adapter.MtitlePopupWindow;
import com.rekoo.libs.adapter.MtitlePopupWindow.OnPopupWindowClickListener;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.FeedbackCons;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.entity.UserFeedback;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.NetUtils;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.CommonUtils;
import com.rekoo.libs.utils.JsonUtils;
import com.rekoo.libs.utils.ResUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserFeedbackActivity extends BaseActivity {
    private OnClickListener backClick = new OnClickListener() {
        public void onClick(View v) {
            BIConfig.getBiConfig().feedbackInterfaceAbandon(UserFeedbackActivity.this.context);
            UserFeedbackActivity.this.finish();
        }
    };
    private Button btn_float_feedback_back;
    private int category = 0;
    public OnClickListener categoryClick = new OnClickListener() {
        public void onClick(View v) {
            UserFeedbackActivity.this.mtitlePopupWindow.showAsDropDown(v);
        }
    };
    private String content;
    Context context;
    private EditText et_feedback;
    private UserFeedback feedback;
    private String[] items;
    private List<String> list = new ArrayList();
    private List<String> listSubmit = new ArrayList();
    LinearLayout llUserList;
    private LinearLayout ll_category;
    private MtitlePopupWindow mtitlePopupWindow;
    private Button submit;
    private OnClickListener submitClick = new OnClickListener() {
        public void onClick(View v) {
            Log.e("TAG", "checkConnected" + NetUtils.checkConnected(UserFeedbackActivity.this.context));
            String content = UserFeedbackActivity.this.et_feedback.getText().toString();
            Log.i("", new StringBuilder(URLCons.CONTENT).append(content).toString());
            if (TextUtils.isEmpty(content)) {
                Log.e("TAG", "content==" + content);
                return;
            }
            String uid = Config.currentLoginUser.getUid();
            String token = Config.currentLoginUser.getToken();
            Log.i("TAG", "uid" + uid);
            UserFeedbackActivity.this.submitInfo(UserFeedbackActivity.this.category, content, uid, token);
            BIConfig.getBiConfig().feedbackInterfaceSubmit(UserFeedbackActivity.this.context);
            UserFeedbackActivity.this.finish();
        }
    };
    private TextView tv_category;
    private User user;

    class SumitAsyncTask extends AsyncTask<String, Void, String> {
        SumitAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String url = params[0];
            String submitCategory = params[1];
            String submitContext = params[2];
            String uid = params[3];
            String token = params[4];
            Log.i("TAG", "submitCategory" + submitCategory + "submitContext是" + submitContext + "url是" + url);
            String response = NetRequest.getRequest().post(UserFeedbackActivity.this.context, url, FeedbackCons.getCons().getSubmitInfoRequestBody(UserFeedbackActivity.this.context, submitCategory, submitContext, uid, token));
            Log.d("TAG", "UserFeedbackActivity:" + response);
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("TAG", "返回的信息是" + result);
            if (!TextUtils.isEmpty(result)) {
                if (JsonUtils.getRC(result)) {
                    BIConfig.getBiConfig().getFeedbackSubmitSuccess(UserFeedbackActivity.this.context);
                } else {
                    BIConfig.getBiConfig().getFeedbackSubmitFail(UserFeedbackActivity.this.context);
                }
            }
        }
    }

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        int viewId = ResUtils.getLayout("activity_float_user_feedback", this.context);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(viewId);
        BIConfig.getBiConfig().feedbackInterfaceShow(this.context);
        init();
    }

    private void init() {
        this.items = new String[]{ResUtils.getString("game_bug", this.context), ResUtils.getString("game_suggest", this.context), ResUtils.getString("pay_question", this.context)};
        this.ll_category = (LinearLayout) findViewById(ResUtils.getId("ll_category", this.context));
        this.ll_category.setOnClickListener(this.categoryClick);
        this.tv_category = (TextView) findViewById(ResUtils.getId("tv_category", this.context));
        this.btn_float_feedback_back = (Button) findViewById(ResUtils.getId("btn_float_feedback_back", this.context));
        this.btn_float_feedback_back.setOnClickListener(this.backClick);
        this.et_feedback = (EditText) findViewById(ResUtils.getId("et_feedback", this.context));
        this.submit = (Button) findViewById(ResUtils.getId("submit", this.context));
        if (NetUtils.checkConnected(this.context)) {
            Log.e("TAG", "checkConnected   nomal");
            this.submit.setOnClickListener(this.submitClick);
        } else {
            Log.e("TAG", "checkConnected   fail");
            this.submit.setEnabled(true);
            CommonUtils.showToast(this.context, ResUtils.getString("no_network", this.context));
        }
        this.mtitlePopupWindow = new MtitlePopupWindow(this.context);
        this.mtitlePopupWindow.changeData(Arrays.asList(this.items));
        this.mtitlePopupWindow.setOnPopupWindowClickListener(new OnPopupWindowClickListener() {
            public void onPopupWindowItemClick(int position) {
                UserFeedbackActivity.this.tv_category.setText(UserFeedbackActivity.this.items[position]);
                UserFeedbackActivity.this.category = position;
            }
        });
    }

    private void submitInfo(String category, String context, String uid, String token) {
        try {
            Log.e("TAG", "uid+token" + uid + token);
            new SumitAsyncTask().execute(new String[]{"http://sdk.jp.rekoo.net/user/" + uid + "/feedback", category, context, uid, token});
        } catch (Exception e) {
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }
}
