package com.rekoo.libs.platform.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.rekoo.libs.callback.RKLoginCallback;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.cons.FindUserCons;
import com.rekoo.libs.cons.LoginCons;
import com.rekoo.libs.cons.TouristCons;
import com.rekoo.libs.database.DBManager;
import com.rekoo.libs.entity.RKUser;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.platform.ui.floating.FloatManager;
import com.rekoo.libs.utils.CommonUtils;
import com.rekoo.libs.utils.JsonUtils;
import com.rekoo.libs.utils.JsonUtils.Content;
import com.rekoo.libs.utils.ResUtils;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginManager {
    public static LoginManager manager;
    private Context context;
    private FloatManager floatManager;
    private RKLoginCallback loginCallback = null;
    RKUser rkUser = null;
    private User user;
    List<User> users = null;

    class FindUserAsyncTask extends AsyncTask<String, Void, String> {
        FindUserAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            return NetRequest.getRequest().post(LoginManager.this.context, params[0], FindUserCons.getCons().getFindUserRequestBody(LoginManager.this.context));
        }

        protected void onPostExecute(String result) {
            Log.d("TAG", "finduser_result" + result);
            if (result == null) {
                LoginManager.this.loginCallback.onFail("-10");
                CommonUtils.showToast(LoginManager.this.context, ResUtils.getString("server_exception", LoginManager.this.context));
            } else if (JsonUtils.getRC(result)) {
                if (JsonUtils.getRC(result)) {
                    LoginManager.this.handleLoginSuccess(result);
                    BIConfig.getBiConfig().loginSuccessUninstalReinstall(LoginManager.this.context);
                }
                super.onPostExecute(result);
            } else {
                LoginManager.this.loginCallback.onFail("-11");
                CommonUtils.showToast(LoginManager.this.context, JsonUtils.getMsg(result));
                BIConfig.getBiConfig().loginFailUninstalReinstall(LoginManager.this.context);
            }
        }
    }

    class LoginAsyncTask extends AsyncTask<String, Void, String> {
        LoginAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String loginUrl = params[0];
            LoginManager.this.user = LoginManager.this.setUserParames(params[1], params[2]);
            String response = NetRequest.getRequest().post(LoginManager.this.context, loginUrl, LoginCons.getCons().getLoginRequestBody(LoginManager.this.context, LoginManager.this.user));
            Log.d("TAG", "老用户response：" + response);
            return response;
        }

        protected void onPostExecute(String result) {
            Log.i("TAG", "LoginResult==" + result);
            if (TextUtils.isEmpty(result)) {
                LoginManager.this.loginCallback.onFail(Config.LOGIN_FAIL_TIMEOUT);
                CommonUtils.showToast(LoginManager.this.context, ResUtils.getString("server_exception", LoginManager.this.context));
            } else if (JsonUtils.getRC(result)) {
                if (JsonUtils.getRC(result)) {
                    LoginManager.this.user = LoginManager.this.user.setUserContent(LoginManager.this.user, result);
                    LoginManager.this.rkUser = LoginManager.this.user.setRKUser(result);
                    LoginManager.this.handleLogin();
                    BIConfig.getBiConfig().loginSuccessOldaAccount(LoginManager.this.context);
                }
                super.onPostExecute(result);
            } else {
                Log.d("TAG", "response===fail");
                LoginManager.this.loginCallback.onFail(Config.LOGIN_OLD_ACCOUNT_FAIL);
                CommonUtils.showToast(LoginManager.this.context, JsonUtils.getMsg(result));
                BIConfig.getBiConfig().loginFailOldAccount(LoginManager.this.context);
            }
        }
    }

    class TouristAsyncTask extends AsyncTask<String, Void, String> {
        TouristAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String response = NetRequest.getRequest().post(LoginManager.this.context, params[0], TouristCons.getCons().getTouristLoginRequestBody(LoginManager.this.context));
            Log.d("TAG", "新用户response：" + response);
            return response;
        }

        protected void onPostExecute(String result) {
            Log.d("TAG", "新用户" + result);
            if (TextUtils.isEmpty(result)) {
                LoginManager.this.loginCallback.onFail(Config.LOGIN_FAIL_TIMEOUT);
                CommonUtils.showToast(LoginManager.this.context, ResUtils.getString("server_exception", LoginManager.this.context));
                return;
            }
            try {
                JSONObject json = new JSONObject(result);
                int s = json.getInt("rc");
                if (s == Cons.FD_SUCCESS) {
                    Log.d("TAG", "result是" + result + "我已经申请过了,去找回账号");
                    JSONObject jsonObject = json.getJSONObject(URLCons.CONTENT);
                    String name = jsonObject.getString("rkaccount");
                    String pwd = jsonObject.getString("rkpwd");
                    new LoginAsyncTask().execute(new String[]{URLCons.URL_COMMON_LOGIN, name, pwd});
                    BIConfig.getBiConfig().getAccountUninstalreinatall(LoginManager.this.context);
                } else if (s == 0) {
                    LoginManager.this.handleLoginSuccess(result);
                    BIConfig.getBiConfig().loginSuccessNewAccount(LoginManager.this.context);
                } else {
                    LoginManager.this.loginCallback.onFail(Config.LOGIN_RC_EXCEPTION);
                    BIConfig.getBiConfig().loginFailNewAccount(LoginManager.this.context);
                }
            } catch (JSONException e) {
                LoginManager.this.loginCallback.onFail(Config.LOGIN_JSON_EXCEPTION);
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    private LoginManager() {
    }

    public static LoginManager getManager() {
        if (manager == null) {
            synchronized (LoginManager.class) {
                if (manager == null) {
                    manager = new LoginManager();
                }
            }
        }
        return manager;
    }

    public void login(Context act) {
        Log.d("TAG", "loginManager=========");
        this.context = act;
        this.loginCallback = Config.loginCallback;
        this.users = DBManager.getManager(this.context).getAllUsers(this.context);
        boolean hasUser = DBManager.getManager(this.context).hasUser();
        Log.d("TAG", "hasUser==" + hasUser);
        if (hasUser) {
            try {
                this.user = (User) this.users.get(0);
                String name = this.user.getUserName();
                String pwd = this.user.getPassword();
                new LoginAsyncTask().execute(new String[]{URLCons.URL_COMMON_LOGIN, name, pwd});
                return;
            } catch (Exception e) {
                this.loginCallback.onFail(Config.LOGIN_NULL_POINT);
                return;
            }
        }
        new TouristAsyncTask().execute(new String[]{URLCons.URL_GUEST_LOGIN});
    }

    private User setUserParames(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return user;
    }

    private void handleLoginSuccess(String result) {
        try {
            Log.d("TAG", "找回账号时" + result);
            Config.isLogin = true;
            Content content = JsonUtils.getContent(result);
            Config.uid = content.uid;
            this.user = new User();
            this.user.setUserName(content.userName);
            this.user.setUserContent(this.user, result);
            this.user.setUid(content.uid);
            this.user.setToken(content.token);
            this.user.setPassword(content.password);
            DBManager.getManager(this.context).saveUser(this.user, this.context);
            this.rkUser = this.user.setRKUser(result);
            Log.d("TAG", "response=handleLoginSuccess==SUCCESS");
            this.loginCallback.onSuccess(this.rkUser);
        } catch (Exception e) {
            this.loginCallback.onFail(Config.LOGIN_BACK_ACCOUNT_EXCEPTION);
        }
    }

    private void handleLogin() {
        try {
            Config.isLogin = true;
            Config.uid = this.user.getUid();
            Log.d("TAG", "response==handleLogin=SUCCESS");
            DBManager.getManager(this.context).saveUser(this.user, this.context);
            this.loginCallback.onSuccess(this.rkUser);
        } catch (Exception e) {
            this.loginCallback.onFail(Config.LOGIN_FAIL_EXCEPTION);
        }
    }
}
