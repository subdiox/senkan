package com.rekoo.libs.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.rekoo.libs.utils.JsonUtils;
import com.rekoo.libs.utils.JsonUtils.Content;

public class User implements Parcelable {
    public static final int ALL_USER = -1;
    public static final int BIND = 1;
    public static final int COMMON = 2;
    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            boolean z = true;
            User user = new User();
            user.setToken(source.readString());
            user.setUserName(source.readString());
            user.setPassword(source.readString());
            user.setUid(source.readString());
            user.setSign(source.readString());
            user.setLoginTime(source.readString());
            user.setType(source.readInt());
            user.setIcon(source.readString());
            user.setGender(source.readInt());
            user.setLevel(source.readInt());
            user.setMoblie(source.readString());
            user.setRegisgerMode(source.readInt());
            user.setEmail(source.readString());
            user.setRefreshToken(source.readString());
            user.setSkipBindMobile(source.readByte() != (byte) 0);
            user.setMemberNum(source.readString());
            user.setlastRefreshTime(source.readString());
            user.setRegisterTime(source.readString());
            user.setBindMobileTime(source.readString());
            user.setBindMobileNum(source.readString());
            user.setBargainTime(source.readString());
            user.setBargainMoney(source.readDouble());
            user.setRekooCoin(source.readInt());
            user.setIfChangedPayPwd(source.readByte() != (byte) 0);
            user.setPayPwd(source.readString());
            user.setBirthYear(source.readString());
            user.setBirthMonth(source.readString());
            user.setBirthDay(source.readString());
            user.setSignature(source.readString());
            if (source.readByte() == (byte) 0) {
                z = false;
            }
            user.setifBindmobile(z);
            return user;
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public static final int FACEBOOK = 3;
    public static final int GUEST = 1;
    public static final int MODE_PHONE_NUM = 2;
    public static final int MODE_USER_NAME = 1;
    public static final int NOT_BIND = 0;
    protected double bargainMoney;
    protected String bargainTime;
    protected String bindMobileNum;
    protected String bindMobileTime;
    protected String birthDay;
    protected String birthMonth;
    protected String birthYear;
    protected String email;
    protected int gender;
    protected String icon;
    protected boolean ifBindmobile;
    protected boolean ifChangedPayPwd;
    protected boolean isSkipBindMobile;
    protected String lastRefreshTime;
    protected int level;
    protected String loginTime;
    protected String memberNum;
    protected String moblie;
    protected String password;
    protected String payPwd;
    protected String refreshToken;
    protected int regisgerMode;
    protected String registerTime;
    protected int rekooCoin;
    protected String sign;
    protected String signature;
    protected String token;
    protected int type;
    protected String uid;
    protected String userName;

    public String toString() {
        return "User [token=" + this.token + ", userName=" + this.userName + ", password=" + this.password + ", uid=" + this.uid + ", sign=" + this.sign + ", loginTime=" + this.loginTime + ", type=" + this.type + ", icon=" + this.icon + ", gender=" + this.gender + ", level=" + this.level + ", moblie=" + this.moblie + ", regisgerMode=" + this.regisgerMode + ", bindMobileTime=" + this.bindMobileTime + ", bindMobileNum=" + this.bindMobileNum + ", isSkipBindMobile=" + this.isSkipBindMobile + ", email=" + this.email + ", refreshToken=" + this.refreshToken + ", memberNum=" + this.memberNum + ", lastRefreshTime=" + this.lastRefreshTime + ", registerTime=" + this.registerTime + ", bargainTime=" + this.bargainTime + ", bargainMoney=" + this.bargainMoney + ", rekooCoin=" + this.rekooCoin + ", ifChangedPayPwd=" + this.ifChangedPayPwd + ", payPwd=" + this.payPwd + ", birthYear=" + this.birthYear + ", birthMonth=" + this.birthMonth + ", birthDay=" + this.birthDay + ", signature=" + this.signature + ",ifBindmobile=" + this.ifBindmobile + "]";
    }

    public User setUserContent(User user, String response) {
        Content content = JsonUtils.getContent(response);
        user.setUid(content.uid);
        user.setToken(content.token);
        user.setType(content.userType);
        user.setRefreshToken(content.refreshToken);
        return user;
    }

    public User setUserContent(User user, Content content) {
        user.setUid(content.uid);
        user.setToken(content.token);
        user.setType(content.userType);
        user.setRefreshToken(content.refreshToken);
        return user;
    }

    public RKUser setRKUser(String response) {
        Content content = JsonUtils.getContent(response);
        RKUser user = new RKUser();
        user.setUid(content.uid);
        user.setToken(content.token);
        user.setUserName(content.userName);
        return user;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeString(this.token);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.uid);
        dest.writeString(this.sign);
        dest.writeString(this.loginTime);
        dest.writeInt(this.type);
        dest.writeString(this.icon);
        dest.writeInt(this.gender);
        dest.writeInt(this.level);
        dest.writeString(this.moblie);
        dest.writeInt(this.regisgerMode);
        dest.writeString(this.email);
        dest.writeString(this.refreshToken);
        dest.writeByte((byte) (this.isSkipBindMobile ? 1 : 0));
        dest.writeString(this.memberNum);
        dest.writeString(this.lastRefreshTime);
        dest.writeString(this.registerTime);
        dest.writeString(this.bindMobileTime);
        dest.writeString(this.bindMobileNum);
        dest.writeString(this.bargainTime);
        dest.writeDouble(this.bargainMoney);
        dest.writeInt(this.rekooCoin);
        if (this.ifChangedPayPwd) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeString(this.payPwd);
        dest.writeString(this.birthYear);
        dest.writeString(this.birthMonth);
        dest.writeString(this.birthDay);
        dest.writeString(this.signature);
        if (!this.ifBindmobile) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMoblie() {
        return this.moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public int getRegisgerMode() {
        return this.regisgerMode;
    }

    public void setRegisgerMode(int regisgerMode) {
        this.regisgerMode = regisgerMode;
    }

    public String getBindMobileTime() {
        return this.bindMobileTime;
    }

    public void setBindMobileTime(String bindMobileTime) {
        this.bindMobileTime = bindMobileTime;
    }

    public String getBindMobileNum() {
        return this.bindMobileNum;
    }

    public void setBindMobileNum(String bindMobileNum) {
        this.bindMobileNum = bindMobileNum;
    }

    public boolean isSkipBindMobile() {
        return this.isSkipBindMobile;
    }

    public void setSkipBindMobile(boolean isSkipBindMobile) {
        this.isSkipBindMobile = isSkipBindMobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getMemberNum() {
        return this.memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getlastRefreshTime() {
        return this.lastRefreshTime;
    }

    public void setlastRefreshTime(String lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public String getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getBargainTime() {
        return this.bargainTime;
    }

    public void setBargainTime(String bargainTime) {
        this.bargainTime = bargainTime;
    }

    public double getBargainMoney() {
        return this.bargainMoney;
    }

    public void setBargainMoney(double bargainMoney) {
        this.bargainMoney = bargainMoney;
    }

    public int getRekooCoin() {
        return this.rekooCoin;
    }

    public void setRekooCoin(int rekooCoin) {
        this.rekooCoin = rekooCoin;
    }

    public boolean isIfChangedPayPwd() {
        return this.ifChangedPayPwd;
    }

    public void setIfChangedPayPwd(boolean ifChangedPayPwd) {
        this.ifChangedPayPwd = ifChangedPayPwd;
    }

    public String getPayPwd() {
        return this.payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getBirthYear() {
        return this.birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthMonth() {
        return this.birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthDay() {
        return this.birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean getifBindmobile() {
        return this.ifBindmobile;
    }

    public void setifBindmobile(boolean ifBindmobile) {
        this.ifBindmobile = ifBindmobile;
    }
}
