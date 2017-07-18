package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONObject;

public class HookActionValue implements Parcelable {
    public static final Creator<HookActionValue> CREATOR = new Creator<HookActionValue>() {
        public HookActionValue createFromParcel(Parcel in) {
            return new HookActionValue(in);
        }

        public HookActionValue[] newArray(int size) {
            return new HookActionValue[size];
        }
    };
    private final Params mParams;
    private final String mType;

    public static abstract class Params implements Parcelable {
        public int describeContents() {
            return 0;
        }
    }

    public static class APIRequestParams extends Params {
        public static final Creator<APIRequestParams> CREATOR = new Creator<APIRequestParams>() {
            public APIRequestParams createFromParcel(Parcel in) {
                return new APIRequestParams(in);
            }

            public APIRequestParams[] newArray(int size) {
                return new APIRequestParams[size];
            }
        };
        public static final String TYPE = "api_request";
        private final String mApiUrl;

        public APIRequestParams(JSONObject json) {
            this.mApiUrl = json.optString("api_url");
        }

        public String getApiUrl() {
            return this.mApiUrl;
        }

        public APIRequestParams(Parcel source) {
            this.mApiUrl = source.readString();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mApiUrl);
        }
    }

    public static class OpenAppStoreParams extends Params {
        public static final Creator<OpenAppStoreParams> CREATOR = new Creator<OpenAppStoreParams>() {
            public OpenAppStoreParams createFromParcel(Parcel in) {
                return new OpenAppStoreParams(in);
            }

            public OpenAppStoreParams[] newArray(int size) {
                return new OpenAppStoreParams[size];
            }
        };
        public static final String TYPE = "open_app_store";
        private final String mAdId;
        private final String mPackageName;

        public OpenAppStoreParams(JSONObject json) {
            this.mAdId = json.optString("ad_id", "");
            this.mPackageName = json.optString(AuthorizedAppValue.JSON_KEY_PACKAGE);
        }

        public String getAdId() {
            return this.mAdId;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public OpenAppStoreParams(Parcel source) {
            this.mAdId = source.readString();
            this.mPackageName = source.readString();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mAdId);
            dest.writeString(this.mPackageName);
        }
    }

    public HookActionValue(JSONObject json) {
        this.mType = json.optString("type");
        JSONObject params = json.optJSONObject("params");
        if (this.mType.equals(APIRequestParams.TYPE)) {
            this.mParams = new APIRequestParams(params);
        } else if (this.mType.equals(OpenAppStoreParams.TYPE)) {
            this.mParams = new OpenAppStoreParams(params);
        } else {
            this.mParams = null;
        }
    }

    public String getType() {
        return this.mType;
    }

    public Params getParams() {
        return this.mParams;
    }

    public HookActionValue(Parcel source) {
        this.mType = source.readString();
        if (this.mType.equals(APIRequestParams.TYPE)) {
            this.mParams = (Params) source.readParcelable(APIRequestParams.class.getClassLoader());
        } else if (this.mType.equals(OpenAppStoreParams.TYPE)) {
            this.mParams = (Params) source.readParcelable(OpenAppStoreParams.class.getClassLoader());
        } else {
            this.mParams = null;
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
        dest.writeParcelable(this.mParams, 0);
    }

    public int describeContents() {
        return 0;
    }
}
