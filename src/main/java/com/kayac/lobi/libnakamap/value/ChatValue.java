package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupChat.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatValue implements Parcelable {
    public static final String ADMIN_DELETED = "admin.deleted";
    public static final Creator<ChatValue> CREATOR = new Creator<ChatValue>() {
        public ChatValue createFromParcel(Parcel source) {
            return new ChatValue(source);
        }

        public ChatValue[] newArray(int size) {
            return new ChatValue[size];
        }
    };
    public static final String NORMAL = "normal";
    public static final int ONSTORE = 1;
    public static final String SHOUT = "shout";
    public static final String STAMP = "stamp";
    public static final String SYSTEM_CREATED = "system.created";
    public static final String SYSTEM_ICON_UPDATE = "system.icon_updated";
    public static final String SYSTEM_JOINED = "system.joined";
    public static final String SYSTEM_MEMO_UPDATE = "system.memo_updated";
    public static final String SYSTEM_NAME_UPDATE = "system.name_updated";
    public static final String SYSTEM_PARTED = "system.parted";
    public static final String SYSTEM_WALLPAPER_REMOVED = "system.wallpaper_removed";
    public static final String SYSTEM_WALLPAPER_UPDATE = "system.wallpaper_updated";
    public static final String USER_DELETED = "user.deleted";
    private final List<AssetValue> mAssets;
    private final boolean mBooed;
    private final int mBoosCount;
    private final long mCreatedDate;
    private final String mId;
    private final String mImage;
    private final int mImageHeight;
    private final String mImageType;
    private final int mImageWidth;
    private final boolean mLiked;
    private final int mLikesCount;
    private final String mMessage;
    private final int mOnStore;
    private final List<ChatReferValue> mRefers;
    private final Replies mReplies;
    private final String mReplyTo;
    private final String mStampId;
    private final String mType;
    private final UserValue mUser;

    public static final class Builder {
        private List<AssetValue> mAssets;
        private boolean mBooed;
        private int mBoosCount;
        private long mCreatedDate;
        private String mId;
        private String mImage;
        private int mImageHeight;
        private String mImageType;
        private int mImageWidth;
        private boolean mLiked;
        private int mLikesCount;
        private String mMessage;
        private int mOnStore;
        private List<ChatReferValue> mRefers;
        private Replies mReplies;
        private String mReplyTo;
        private String mStampId;
        private String mType;
        private UserValue mUser;

        public Builder(ChatValue src) {
            this.mId = src.getId();
            this.mType = src.getType();
            this.mMessage = src.getMessage();
            this.mCreatedDate = src.getCreatedDate();
            this.mImage = src.getImage();
            this.mImageType = src.getImageType();
            this.mImageWidth = src.getImageWidth();
            this.mImageHeight = src.getImageHeight();
            this.mReplyTo = src.getReplyTo();
            this.mUser = src.getUser();
            this.mStampId = src.getStampUid();
            this.mOnStore = src.getOnStore();
            this.mRefers = src.getRefers();
            this.mReplies = src.getReplies();
            this.mAssets = src.getAssets();
            this.mLikesCount = src.getLikesCount();
            this.mBoosCount = src.getBoosCount();
            this.mLiked = src.getLiked();
            this.mBooed = src.getBooed();
        }

        public void setId(String id) {
            this.mId = id;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setMessage(String message) {
            this.mMessage = message;
        }

        public void setCreatedDate(long createdDate) {
            this.mCreatedDate = createdDate;
        }

        public void setImage(String image) {
            this.mImage = image;
        }

        public void setImageType(String imageType) {
            this.mImageType = imageType;
        }

        public void setImageWidth(int imageWidth) {
            this.mImageWidth = imageWidth;
        }

        public void setImageHeight(int imageHeight) {
            this.mImageHeight = imageHeight;
        }

        public void setReplyTo(String replyTo) {
            this.mReplyTo = replyTo;
        }

        public void setUser(UserValue user) {
            this.mUser = user;
        }

        public void setStampId(String stampId) {
            this.mStampId = stampId;
        }

        public void setOnStore(int onStore) {
            this.mOnStore = onStore;
        }

        public void setRefers(List<ChatReferValue> refers) {
            this.mRefers = refers;
        }

        public void setReplies(Replies replies) {
            this.mReplies = replies;
        }

        public void setAssets(List<AssetValue> list) {
        }

        public void setLikesCount(int likes) {
            this.mLikesCount = likes;
        }

        public void setBoosCount(int boos) {
            this.mBoosCount = boos;
        }

        public void setLiked(boolean liked) {
            this.mLiked = liked;
        }

        public void setBooed(boolean booed) {
            this.mBooed = booed;
        }

        public ChatValue build() {
            return new ChatValue(this.mId, this.mType, this.mMessage, this.mCreatedDate, this.mImage, this.mReplyTo, this.mUser, this.mImageType, this.mImageWidth, this.mImageHeight, this.mStampId, this.mOnStore, this.mRefers, this.mReplies, this.mAssets, this.mLikesCount, this.mBoosCount, this.mLiked, this.mBooed);
        }
    }

    public static final class Replies implements Parcelable {
        public static final Creator<Replies> CREATOR = new Creator<Replies>() {
            public Replies createFromParcel(Parcel source) {
                return new Replies(source);
            }

            public Replies[] newArray(int size) {
                return new Replies[size];
            }
        };
        private final List<ChatValue> mChats;
        private final int mCount;

        public static final class Builder {
            private List<ChatValue> mChats;
            private int mCount;

            public Builder(Replies src) {
                this.mChats = src.getChats();
                this.mCount = src.getCount();
            }

            public void setChats(List<ChatValue> chats) {
                this.mChats = chats;
            }

            public void setCount(int count) {
                this.mCount = count;
            }

            public Replies build() {
                return new Replies(this.mChats, this.mCount);
            }
        }

        public Replies(List<ChatValue> chats, int count) {
            this.mChats = chats;
            this.mCount = count;
        }

        public Replies(JSONObject json) {
            this.mChats = new ArrayList();
            this.mCount = Integer.parseInt(JSONUtil.getString(json, "count", "0"));
            JSONArray chats = json.optJSONArray("chats");
            if (chats != null) {
                int len = chats.length();
                for (int i = 0; i < len; i++) {
                    JSONObject reply = chats.optJSONObject(i);
                    if (reply != null) {
                        this.mChats.add(new ChatValue(reply, true));
                    }
                }
                Collections.reverse(this.mChats);
            }
        }

        Replies() {
            this.mChats = new ArrayList();
            this.mCount = 0;
        }

        public List<ChatValue> getChats() {
            return this.mChats;
        }

        public int getCount() {
            return this.mCount;
        }

        public void addReplies(ChatValue reply) {
            this.mChats.add(reply);
        }

        public int describeContents() {
            return 0;
        }

        private Replies(Parcel source) {
            this.mChats = new ArrayList();
            source.readList(this.mChats, ChatValue.class.getClassLoader());
            this.mCount = source.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.mChats);
            dest.writeInt(this.mCount);
        }
    }

    public ChatValue(String id, String type, String message, long createdDate, String image, String replyTo, UserValue user, String imageType, int imageWidth, int imageHeight, String stampId, int onStore, List<ChatReferValue> refer, Replies replies, List<AssetValue> assets, int likes, int boos, boolean liked, boolean booed) {
        this.mId = id;
        this.mType = type;
        this.mMessage = message;
        this.mCreatedDate = createdDate;
        this.mImage = image;
        this.mReplyTo = replyTo;
        this.mUser = user;
        this.mImageType = imageType;
        this.mImageWidth = imageWidth;
        this.mImageHeight = imageHeight;
        this.mStampId = stampId;
        this.mOnStore = onStore;
        this.mRefers = refer;
        this.mReplies = replies;
        this.mAssets = assets;
        this.mLikesCount = likes;
        this.mBoosCount = boos;
        this.mLiked = liked;
        this.mBooed = booed;
    }

    public ChatValue(JSONObject json) {
        this(json, false);
    }

    public ChatValue(JSONObject json, boolean isReply) {
        int len;
        int i;
        this.mId = JSONUtil.getString(json, "id", null);
        this.mType = JSONUtil.getString(json, "type", null);
        this.mMessage = JSONUtil.getString(json, "message", null);
        this.mCreatedDate = Long.parseLong(JSONUtil.getString(json, "created_date", "0")) * 1000;
        this.mImage = JSONUtil.getString(json, "image", null);
        this.mImageType = JSONUtil.getString(json, RequestKey.OPTION_IMAGE_TYPE, null);
        this.mImageWidth = Integer.parseInt(JSONUtil.getString(json, "image_width", "0"));
        this.mImageHeight = Integer.parseInt(JSONUtil.getString(json, "image_height", "0"));
        this.mReplyTo = JSONUtil.getString(json, RequestKey.OPTION_REPLY_TO, null);
        this.mStampId = JSONUtil.getString(json, "stamp_id", null);
        this.mOnStore = Integer.parseInt(JSONUtil.getString(json, "on_store", "0"));
        JSONObject user = json.optJSONObject("user");
        if (user != null) {
            this.mUser = new UserValue(user);
        } else {
            this.mUser = null;
        }
        JSONArray refers = json.optJSONArray("refers");
        if (refers != null) {
            this.mRefers = new ArrayList();
            len = refers.length();
            for (i = 0; i < len; i++) {
                JSONObject refer = refers.optJSONObject(i);
                if (refer != null) {
                    this.mRefers.add(new ChatReferValue(refer));
                }
            }
        } else {
            this.mRefers = null;
        }
        JSONObject replies = json.optJSONObject("replies");
        if (replies != null) {
            this.mReplies = new Replies(replies);
        } else {
            this.mReplies = new Replies();
        }
        JSONArray assets = json.optJSONArray(RequestKey.OPTION_ASSETS);
        if (assets != null) {
            this.mAssets = new ArrayList();
            len = assets.length();
            for (i = 0; i < len; i++) {
                JSONObject asset = assets.optJSONObject(i);
                if (asset != null) {
                    this.mAssets.add(new AssetValue(asset));
                }
            }
        } else {
            this.mAssets = null;
        }
        this.mLikesCount = Integer.parseInt(JSONUtil.getString(json, "likes_count", "0"));
        this.mBoosCount = Integer.parseInt(JSONUtil.getString(json, "boos_count", "0"));
        this.mLiked = JSONUtil.getString(json, "liked", "0").equals("1");
        this.mBooed = JSONUtil.getString(json, "booed", "0").equals("1");
    }

    public String getId() {
        return this.mId;
    }

    public String getType() {
        return this.mType;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public long getCreatedDate() {
        return this.mCreatedDate;
    }

    public String getImage() {
        return this.mImage;
    }

    public int getImageWidth() {
        return this.mImageWidth;
    }

    public int getImageHeight() {
        return this.mImageHeight;
    }

    public String getImageType() {
        return this.mImageType;
    }

    public String getReplyTo() {
        return this.mReplyTo;
    }

    public UserValue getUser() {
        return this.mUser;
    }

    public String getStampUid() {
        return this.mStampId;
    }

    public int getOnStore() {
        return this.mOnStore;
    }

    public List<ChatReferValue> getRefers() {
        return this.mRefers;
    }

    public Replies getReplies() {
        return this.mReplies;
    }

    public List<AssetValue> getAssets() {
        return this.mAssets;
    }

    public int getLikesCount() {
        return this.mLikesCount;
    }

    public int getBoosCount() {
        return this.mBoosCount;
    }

    public boolean getLiked() {
        return this.mLiked;
    }

    public boolean getBooed() {
        return this.mBooed;
    }

    public int describeContents() {
        return 0;
    }

    private ChatValue(Parcel source) {
        boolean z;
        boolean z2 = true;
        this.mId = source.readString();
        this.mType = source.readString();
        this.mMessage = source.readString();
        this.mCreatedDate = source.readLong();
        this.mImage = source.readString();
        this.mReplyTo = source.readString();
        this.mUser = (UserValue) source.readParcelable(UserValue.class.getClassLoader());
        this.mImageType = source.readString();
        this.mImageWidth = source.readInt();
        this.mImageHeight = source.readInt();
        this.mStampId = source.readString();
        this.mOnStore = source.readInt();
        this.mReplies = (Replies) source.readParcelable(Replies.class.getClassLoader());
        this.mRefers = new ArrayList();
        this.mAssets = new ArrayList();
        source.readList(this.mRefers, ChatReferValue.class.getClassLoader());
        source.readList(this.mAssets, AssetValue.class.getClassLoader());
        this.mLikesCount = source.readInt();
        this.mBoosCount = source.readInt();
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mLiked = z;
        if (source.readByte() <= (byte) 0) {
            z2 = false;
        }
        this.mBooed = z2;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeString(this.mId);
        dest.writeString(this.mType);
        dest.writeString(this.mMessage);
        dest.writeLong(this.mCreatedDate);
        dest.writeString(this.mImage);
        dest.writeString(this.mReplyTo);
        dest.writeParcelable(this.mUser, 0);
        dest.writeString(this.mImageType);
        dest.writeInt(this.mImageWidth);
        dest.writeInt(this.mImageHeight);
        dest.writeString(this.mStampId);
        dest.writeInt(this.mOnStore);
        dest.writeParcelable(this.mReplies, 0);
        dest.writeList(this.mRefers);
        dest.writeList(this.mAssets);
        dest.writeInt(this.mLikesCount);
        dest.writeInt(this.mBoosCount);
        if (this.mLiked) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (!this.mBooed) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
    }
}
