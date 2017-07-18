package com.kayac.lobi.sdk.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;

public class LobiGroupData implements Parcelable {
    public static final Creator<LobiGroupData> CREATOR = new Creator<LobiGroupData>() {
        public LobiGroupData createFromParcel(Parcel source) {
            return new LobiGroupData(source);
        }

        public LobiGroupData[] newArray(int size) {
            return new LobiGroupData[size];
        }
    };
    public final boolean canAddMembers;
    public final boolean canInvite;
    public final boolean canJoin;
    public final boolean canKick;
    public final boolean canPart;
    public final boolean canPeek;
    public final boolean canRemove;
    public final boolean canShout;
    public final boolean canUpdateDescription;
    public final boolean canUpdateIcon;
    public final boolean canUpdateName;
    public final boolean canUpdateWallpaper;
    public final long createdDate;
    public final String description;
    public final String exId;
    public final String iconURL;
    public final boolean isNotice;
    public final boolean isOfficial;
    public final boolean isOnline;
    public final boolean isPublic;
    public final long lastChatDate;
    public final String name;
    public final int onlineUsers;
    public final boolean pushEnabled;
    public final int totalUsers;
    public final String type;
    public final String uid;

    LobiGroupData(GroupDetailValue detailValue) {
        GroupPermissionValue permission = detailValue.getPermission();
        this.canAddMembers = permission.addMembers;
        this.canInvite = permission.invite;
        this.canJoin = permission.join;
        this.canKick = permission.kick;
        this.canPart = permission.part;
        this.canPeek = permission.peek;
        this.canRemove = permission.remove;
        this.canShout = permission.shout;
        this.canUpdateDescription = permission.canUpdateDescription;
        this.canUpdateIcon = permission.canUpdateIcon;
        this.canUpdateName = permission.canUpdateName;
        this.canUpdateWallpaper = permission.canUpdateWallpaper;
        this.createdDate = detailValue.getCreatedDate();
        this.description = detailValue.getDescription();
        this.iconURL = detailValue.getIcon();
        this.isNotice = detailValue.isNotice();
        this.isOfficial = detailValue.isOfficial();
        this.isOnline = detailValue.isOnline();
        this.isPublic = detailValue.isPublic();
        this.lastChatDate = detailValue.getLastChatAt();
        this.name = detailValue.getName();
        this.onlineUsers = detailValue.getOnlineUsers();
        this.pushEnabled = detailValue.isPushEnabled();
        this.totalUsers = detailValue.getTotalUsers();
        this.type = detailValue.getType();
        this.uid = detailValue.getUid();
        this.exId = detailValue.getExId();
    }

    public int describeContents() {
        return 0;
    }

    private LobiGroupData(Parcel source) {
        boolean z;
        boolean z2 = true;
        this.canAddMembers = source.readByte() > (byte) 0;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canInvite = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canJoin = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canKick = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canPart = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canPeek = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canRemove = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canShout = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canUpdateDescription = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canUpdateIcon = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canUpdateName = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.canUpdateWallpaper = z;
        this.createdDate = source.readLong();
        this.description = source.readString();
        this.iconURL = source.readString();
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isNotice = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isOfficial = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isOnline = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isPublic = z;
        this.lastChatDate = source.readLong();
        this.name = source.readString();
        this.onlineUsers = source.readInt();
        if (source.readByte() <= (byte) 0) {
            z2 = false;
        }
        this.pushEnabled = z2;
        this.totalUsers = source.readInt();
        this.type = source.readString();
        this.uid = source.readString();
        this.exId = source.readString();
    }

    public void writeToParcel(Parcel dest, int arg1) {
        int i;
        int i2 = 1;
        dest.writeByte((byte) (this.canAddMembers ? 1 : 0));
        if (this.canInvite) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canJoin) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canKick) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canPart) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canPeek) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canRemove) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canShout) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canUpdateDescription) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canUpdateIcon) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canUpdateName) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.canUpdateWallpaper) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeLong(this.createdDate);
        dest.writeString(this.description);
        dest.writeString(this.iconURL);
        if (this.isNotice) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.isOfficial) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.isOnline) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.isPublic) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeLong(this.lastChatDate);
        dest.writeString(this.name);
        dest.writeInt(this.onlineUsers);
        if (!this.pushEnabled) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        dest.writeInt(this.totalUsers);
        dest.writeString(this.type);
        dest.writeString(this.uid);
        dest.writeString(this.exId);
    }

    public String toString() {
        return String.format("[LobiGroupData: canAddMembers=%s; canInvite=%s; canJoin=%s;\n canKick=%s; canPart=%s; canPeek=%s; canRemove=%s;\n canShout=%s; canUpdateDescription=%s; canUpdateIcon=%s;\n canUpdateName=%s; canUpdateWallpaper=%s; createdDate=%s;\n description=%s; iconURL=%s; isNotice=%s; isOfficial=%s;\n isOnline=%s; isPublic=%s; lastChatDate=%s; name=%s;\n onlineUsers=%s; pushEnabled=%s; totalUsers=%s; type=%s;\n uid=%s; exId=%s;]", new Object[]{Boolean.valueOf(this.canAddMembers), Boolean.valueOf(this.canInvite), Boolean.valueOf(this.canJoin), Boolean.valueOf(this.canKick), Boolean.valueOf(this.canPart), Boolean.valueOf(this.canPeek), Boolean.valueOf(this.canRemove), Boolean.valueOf(this.canShout), Boolean.valueOf(this.canUpdateDescription), Boolean.valueOf(this.canUpdateIcon), Boolean.valueOf(this.canUpdateName), Boolean.valueOf(this.canUpdateWallpaper), Long.valueOf(this.createdDate), this.description, this.iconURL, Boolean.valueOf(this.isNotice), Boolean.valueOf(this.isOfficial), Boolean.valueOf(this.isOnline), Boolean.valueOf(this.isPublic), Long.valueOf(this.lastChatDate), this.name, Integer.valueOf(this.onlineUsers), Boolean.valueOf(this.pushEnabled), Integer.valueOf(this.totalUsers), this.type, this.uid, this.exId});
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof LobiGroupData)) {
            return false;
        }
        return TextUtils.equals(this.uid, ((LobiGroupData) o).uid);
    }

    public int hashCode() {
        return this.uid.hashCode();
    }
}
