package com.rekoo.libs.utils;

import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.entity.RKUser;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.URLCons;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public class Content {
        public String password;
        public String refreshToken;
        public String token;
        public String uid;
        public String userName;
        public int userType;
    }

    public static boolean getRC(String response) {
        JSONObject jSONObject;
        JSONException e;
        int result = -1;
        try {
            JSONObject jObject = new JSONObject(response);
            try {
                result = jObject.getInt("rc");
                jSONObject = jObject;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jObject;
                e.printStackTrace();
                return result != 0;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            if (result != 0) {
            }
        }
        if (result != 0) {
        }
    }

    public static boolean getFD(String response) {
        JSONObject jSONObject;
        JSONException e;
        int result = -1;
        try {
            JSONObject jObject = new JSONObject(response);
            try {
                result = jObject.getInt("rc");
                jSONObject = jObject;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jObject;
                e.printStackTrace();
                return result == Cons.FD_SUCCESS;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            if (result == Cons.FD_SUCCESS) {
            }
        }
        if (result == Cons.FD_SUCCESS) {
        }
    }

    public static int getRCValue(String response) {
        JSONException e;
        int result = -1;
        try {
            JSONObject jObject = new JSONObject(response);
            JSONObject jSONObject;
            try {
                result = jObject.getInt("rc");
                jSONObject = jObject;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jObject;
                e.printStackTrace();
                return result;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static String getMsg(String response) {
        JSONObject jSONObject;
        JSONException e;
        String msg = null;
        try {
            JSONObject jObject = new JSONObject(response);
            try {
                msg = jObject.getString("msg");
                jSONObject = jObject;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jObject;
                e.printStackTrace();
                return msg;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return msg;
        }
        return msg;
    }

    public static Content getContent(String response) {
        JSONObject jSONObject;
        JSONException e;
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.getClass();
        Content content = new Content();
        try {
            JSONObject jObject = new JSONObject(response);
            try {
                JSONObject jObj = jObject.getJSONObject(URLCons.CONTENT);
                content.userType = jObj.getInt("userType");
                content.password = jObj.getString("rkpwd");
                content.uid = jObj.getString("rkuid");
                content.userName = jObj.getString("rkaccount");
                content.token = jObj.getString("token");
                content.refreshToken = jObj.getString("refresh_token");
                jSONObject = jObject;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jObject;
                e.printStackTrace();
                return content;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return content;
        }
        return content;
    }

    public static User getUser(String response) {
        JSONException e;
        User user = null;
        try {
            JSONObject jObject = new JSONObject(response);
            JSONObject jSONObject;
            try {
                JSONObject jObj = jObject.getJSONObject(URLCons.CONTENT);
                user.setType(jObj.getInt("userType"));
                user.setMoblie(jObj.getString("mobile"));
                user.setUid(jObj.getString("rkuid"));
                user.setUserName(jObj.getString("rkaccount"));
                user.setPassword(jObj.getString("rkpwd"));
                user.setToken(jObj.getString("token"));
                jSONObject = jObject;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jObject;
                e.printStackTrace();
                return user;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return user;
        }
        return user;
    }

    public static RKUser getUserInfo(String response) {
        JSONException e;
        RKUser rkUser = null;
        try {
            RKUser rkUser2 = new RKUser();
            try {
                JSONObject jObject = new JSONObject(response);
                JSONObject jSONObject;
                try {
                    rkUser2.setUserName(jObject.getJSONObject(URLCons.CONTENT).getString("rkaccount"));
                    jSONObject = jObject;
                    return rkUser2;
                } catch (JSONException e2) {
                    e = e2;
                    jSONObject = jObject;
                    rkUser = rkUser2;
                    e.printStackTrace();
                    return rkUser;
                }
            } catch (JSONException e3) {
                e = e3;
                rkUser = rkUser2;
                e.printStackTrace();
                return rkUser;
            }
        } catch (JSONException e4) {
            e = e4;
            e.printStackTrace();
            return rkUser;
        }
    }
}
