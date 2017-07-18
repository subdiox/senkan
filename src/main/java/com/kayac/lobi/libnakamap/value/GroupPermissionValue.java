package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.GroupPermission;
import org.json.JSONObject;

public class GroupPermissionValue implements Parcelable {
    public static final Creator<GroupPermissionValue> CREATOR = new Creator<GroupPermissionValue>() {
        public GroupPermissionValue createFromParcel(Parcel source) {
            return new GroupPermissionValue(source);
        }

        public GroupPermissionValue[] newArray(int size) {
            return new GroupPermissionValue[size];
        }
    };
    public final boolean addMembers;
    public final boolean canUpdateDescription;
    public final boolean canUpdateIcon;
    public final boolean canUpdateName;
    public final boolean canUpdateWallpaper;
    public final boolean invite;
    public final boolean join;
    public final boolean kick;
    public final boolean part;
    public final boolean peek;
    public final boolean remove;
    public final boolean shout;

    public static final class Builder {
        private boolean mAddMembers;
        private boolean mCanUpdateDescription;
        private boolean mCanUpdateIcon;
        private boolean mCanUpdateName;
        private boolean mCanUpdateWallpaper;
        private boolean mInvite;
        private boolean mJoin;
        private boolean mKick;
        private boolean mPart;
        private boolean mPeek;
        private boolean mRemove;
        private boolean mShout;

        public Builder(GroupPermissionValue value) {
            this.mCanUpdateIcon = value.canUpdateIcon;
            this.mCanUpdateDescription = value.canUpdateDescription;
            this.mCanUpdateWallpaper = value.canUpdateWallpaper;
            this.mInvite = value.invite;
            this.mAddMembers = value.addMembers;
            this.mJoin = value.join;
            this.mRemove = value.remove;
            this.mPart = value.part;
            this.mKick = value.kick;
            this.mPeek = value.peek;
            this.mShout = value.shout;
        }

        public void setCanUpdateIcon(boolean canUpdateIcon) {
            this.mCanUpdateIcon = canUpdateIcon;
        }

        public void setCanUpdateDescription(boolean canUpdateDescription) {
            this.mCanUpdateDescription = canUpdateDescription;
        }

        public void setCanUpdateWallpaper(boolean canUpdateWallpaper) {
            this.mCanUpdateWallpaper = canUpdateWallpaper;
        }

        public void setCanUpdateName(boolean canUpdateName) {
            this.mCanUpdateName = canUpdateName;
        }

        public void setInvite(boolean invite) {
            this.mInvite = invite;
        }

        public void setAddMembers(boolean addMembers) {
            this.mAddMembers = addMembers;
        }

        public void setJoin(boolean join) {
            this.mJoin = join;
        }

        public void setRemove(boolean remove) {
            this.mRemove = remove;
        }

        public void setPart(boolean part) {
            this.mPart = part;
        }

        public void setKick(boolean kick) {
            this.mKick = kick;
        }

        public void setShout(boolean shout) {
            this.mShout = shout;
        }

        public GroupPermissionValue build() {
            return new GroupPermissionValue(this.mCanUpdateIcon, this.mCanUpdateName, this.mCanUpdateDescription, this.mCanUpdateWallpaper, this.mInvite, this.mAddMembers, this.mJoin, this.mRemove, this.mPart, this.mKick, this.mPeek, this.mShout);
        }
    }

    public GroupPermissionValue(JSONObject jsonObject) {
        this.canUpdateIcon = parseFlag(jsonObject, GroupPermission.UPDATE_ICON);
        this.canUpdateName = parseFlag(jsonObject, GroupPermission.UPDATE_NAME);
        this.canUpdateDescription = parseFlag(jsonObject, GroupPermission.UPDATE_DESCRIPTION);
        this.canUpdateWallpaper = parseFlag(jsonObject, GroupPermission.UPDATE_WALLPAPER);
        this.invite = parseFlag(jsonObject, GroupPermission.INVITE);
        this.addMembers = parseFlag(jsonObject, GroupPermission.ADD_MEMBERS);
        this.join = parseFlag(jsonObject, GroupPermission.JOIN);
        this.remove = parseFlag(jsonObject, "remove");
        this.part = parseFlag(jsonObject, "part");
        this.kick = parseFlag(jsonObject, GroupPermission.KICK);
        this.peek = parseFlag(jsonObject, GroupPermission.PEEK);
        this.shout = parseFlag(jsonObject, "shout");
    }

    boolean parseFlag(JSONObject jsonObject, String key) {
        return "1".equals(jsonObject.optString(key, ""));
    }

    public GroupPermissionValue(boolean updateIcon, boolean updateName, boolean updateDescription, boolean updateWallpaper, boolean invite, boolean addMembers, boolean join, boolean remove, boolean part, boolean kick, boolean peek, boolean shout) {
        this.canUpdateIcon = updateIcon;
        this.canUpdateName = updateName;
        this.canUpdateDescription = updateDescription;
        this.canUpdateWallpaper = updateWallpaper;
        this.invite = invite;
        this.addMembers = addMembers;
        this.join = join;
        this.remove = remove;
        this.part = part;
        this.kick = kick;
        this.peek = peek;
        this.shout = shout;
    }

    public int describeContents() {
        return 0;
    }

    private GroupPermissionValue(Parcel source) {
        boolean[] permission = new boolean[12];
        source.readBooleanArray(permission);
        this.canUpdateIcon = permission[0];
        this.canUpdateName = permission[1];
        this.canUpdateDescription = permission[2];
        this.canUpdateWallpaper = permission[3];
        this.invite = permission[4];
        this.addMembers = permission[5];
        this.join = permission[6];
        this.remove = permission[7];
        this.part = permission[8];
        this.kick = permission[9];
        this.peek = permission[10];
        this.shout = permission[11];
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[]{this.canUpdateIcon, this.canUpdateName, this.canUpdateDescription, this.canUpdateWallpaper, this.invite, this.addMembers, this.join, this.remove, this.part, this.kick, this.peek, this.shout});
    }
}
