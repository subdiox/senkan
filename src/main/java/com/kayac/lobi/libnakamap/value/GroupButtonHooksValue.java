package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupPermission;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class GroupButtonHooksValue implements Parcelable {
    public static final Creator<GroupButtonHooksValue> CREATOR = new Creator<GroupButtonHooksValue>() {
        public GroupButtonHooksValue createFromParcel(Parcel in) {
            return new GroupButtonHooksValue(in);
        }

        public GroupButtonHooksValue[] newArray(int size) {
            return new GroupButtonHooksValue[size];
        }
    };
    private final Hooks mDownloadButtonHooks;
    private final Hooks mJoinButtonHooks;

    public static class Hooks implements Parcelable {
        public static final Creator<Hooks> CREATOR = new Creator<Hooks>() {
            public Hooks createFromParcel(Parcel in) {
                return new Hooks(in);
            }

            public Hooks[] newArray(int size) {
                return new Hooks[size];
            }
        };
        private final List<HookActionValue> mClickHooks;
        private final List<HookActionValue> mDisplayHooks;

        public Hooks(JSONObject json, String key) {
            this.mClickHooks = new ArrayList();
            loadHookArray(json, this.mClickHooks, "click_hook_" + key);
            this.mDisplayHooks = new ArrayList();
            loadHookArray(json, this.mDisplayHooks, "display_hook_" + key);
        }

        private void loadHookArray(JSONObject json, List<HookActionValue> list, String key) {
            JSONArray ary = json.optJSONArray(key);
            if (ary != null) {
                for (int i = 0; i < ary.length(); i++) {
                    list.add(new HookActionValue(ary.optJSONObject(i)));
                }
            }
        }

        public Hooks(List<HookActionValue> clickHooks, List<HookActionValue> displayHooks) {
            this.mClickHooks = clickHooks;
            this.mDisplayHooks = displayHooks;
        }

        public List<HookActionValue> getClickHooks() {
            return this.mClickHooks;
        }

        public List<HookActionValue> getDisplayHooks() {
            return this.mDisplayHooks;
        }

        public Hooks(Parcel source) {
            this.mClickHooks = source.createTypedArrayList(HookActionValue.CREATOR);
            this.mDisplayHooks = source.createTypedArrayList(HookActionValue.CREATOR);
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.mClickHooks);
            dest.writeTypedList(this.mDisplayHooks);
        }

        public int describeContents() {
            return 0;
        }
    }

    public GroupButtonHooksValue(JSONObject json) {
        this.mJoinButtonHooks = new Hooks(json, GroupPermission.JOIN);
        this.mDownloadButtonHooks = new Hooks(json, "download");
    }

    public GroupButtonHooksValue(Hooks joinButtonHooks, Hooks downloadButtonHooks) {
        this.mJoinButtonHooks = joinButtonHooks;
        this.mDownloadButtonHooks = downloadButtonHooks;
    }

    public Hooks getJoinButtonHooks() {
        return this.mJoinButtonHooks;
    }

    public Hooks getDownloadButtonHooks() {
        return this.mDownloadButtonHooks;
    }

    public GroupButtonHooksValue(Parcel source) {
        this.mJoinButtonHooks = (Hooks) source.readParcelable(Hooks.class.getClassLoader());
        this.mDownloadButtonHooks = (Hooks) source.readParcelable(Hooks.class.getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mJoinButtonHooks, 0);
        dest.writeParcelable(this.mDownloadButtonHooks, 0);
    }

    public int describeContents() {
        return 0;
    }
}
