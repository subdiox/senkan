package cn.sharesdk.framework;

import android.graphics.Bitmap;
import com.mob.tools.utils.Hashon;
import java.util.HashMap;

public abstract class d {
    protected static final String ADDRESS = "address";
    protected static final String AUTHOR = "author";
    protected static final String COMMENT = "comment";
    protected static final String CONTENT_TYPE = "contentType";
    protected static final String CUSTOM_FLAG = "customFlag";
    protected static final String EXECUTE_URL = "executeUrl";
    protected static final String EXT_INFO = "extInfo";
    protected static final String FILE_PATH = "filePath";
    protected static final String GROPU_ID = "groupID";
    protected static final String HIDDEN = "hidden";
    protected static final String IMAGE_ARRAY = "imageArray";
    protected static final String IMAGE_DATA = "imageData";
    protected static final String IMAGE_PATH = "imagePath";
    protected static final String IMAGE_URL = "imageUrl";
    protected static final String INSTALL_URL = "installUrl";
    protected static final String IS_FAMILY = "isFamily";
    protected static final String IS_FRIEND = "isFriend";
    protected static final String IS_PUBLIC = "isPublic";
    protected static final String IS_SHARE_TENCENT_WEIBO = "isShareTencentWeibo";
    protected static final String LATITUDE = "latitude";
    protected static final String LONGITUDE = "longitude";
    protected static final String MUSIC_URL = "musicUrl";
    protected static final String NOTEBOOK = "notebook";
    protected static final String SAFETY_LEVEL = "safetyLevel";
    protected static final String SCENCE = "scene";
    protected static final String SHARE_TYPE = "shareType";
    protected static final String SITE = "site";
    protected static final String SITE_URL = "siteUrl";
    protected static final String STACK = "stack";
    protected static final String TAGS = "tags";
    protected static final String TEXT = "text";
    protected static final String TITLE = "title";
    protected static final String TITLE_URL = "titleUrl";
    protected static final String URL = "url";
    protected static final String VENUE_DESCRIPTION = "venueDescription";
    protected static final String VENUE_NAME = "venueName";
    private HashMap<String, Object> params;

    public d() {
        this.params = new HashMap();
    }

    public d(String str) {
        this(new Hashon().fromJson(str));
    }

    public d(HashMap<String, Object> hashMap) {
        this();
        if (hashMap != null) {
            this.params.putAll(hashMap);
        }
    }

    public <T> T get(String key, Class<T> type) {
        Object obj = this.params.get(key);
        if (obj != null) {
            return type.cast(obj);
        }
        if (Byte.class.equals(type) || Byte.TYPE.equals(type)) {
            return type.cast(new Byte((byte) 0));
        }
        if (Short.class.equals(type) || Short.TYPE.equals(type)) {
            return type.cast(new Short((short) 0));
        }
        if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
            return type.cast(new Integer(0));
        }
        if (Long.class.equals(type) || Long.TYPE.equals(type)) {
            return type.cast(new Long(0));
        }
        if (Float.class.equals(type) || Float.TYPE.equals(type)) {
            return type.cast(new Float(0.0f));
        }
        if (Double.class.equals(type) || Double.TYPE.equals(type)) {
            return type.cast(new Double(0.0d));
        }
        return (Boolean.class.equals(type) || Boolean.TYPE.equals(type)) ? type.cast(Boolean.valueOf(false)) : null;
    }

    public String getAddress() {
        return (String) get(ADDRESS, String.class);
    }

    public String getAuthor() {
        return (String) get(AUTHOR, String.class);
    }

    public String getComment() {
        return (String) get(COMMENT, String.class);
    }

    public int getContentType() {
        return ((Integer) get(CONTENT_TYPE, Integer.class)).intValue();
    }

    public String[] getCustomFlag() {
        return (String[]) get(CUSTOM_FLAG, String[].class);
    }

    public String getExecuteUrl() {
        return (String) get(EXECUTE_URL, String.class);
    }

    public String getExtInfo() {
        return (String) get(EXT_INFO, String.class);
    }

    public String getFilePath() {
        return (String) get(FILE_PATH, String.class);
    }

    public String getGroupId() {
        return (String) get(GROPU_ID, String.class);
    }

    public int getHidden() {
        return ((Integer) get(HIDDEN, Integer.class)).intValue();
    }

    public String[] getImageArray() {
        return (String[]) get(IMAGE_ARRAY, String[].class);
    }

    public Bitmap getImageData() {
        return (Bitmap) get(IMAGE_DATA, Bitmap.class);
    }

    public String getImagePath() {
        return (String) get(IMAGE_PATH, String.class);
    }

    public String getImageUrl() {
        return (String) get(IMAGE_URL, String.class);
    }

    public String getInstallUrl() {
        return (String) get(INSTALL_URL, String.class);
    }

    public float getLatitude() {
        return ((Float) get(LATITUDE, Float.class)).floatValue();
    }

    public float getLongitude() {
        return ((Float) get(LONGITUDE, Float.class)).floatValue();
    }

    public String getMusicUrl() {
        return (String) get(MUSIC_URL, String.class);
    }

    public String getNotebook() {
        return (String) get(NOTEBOOK, String.class);
    }

    public int getSafetyLevel() {
        return ((Integer) get(SAFETY_LEVEL, Integer.class)).intValue();
    }

    public int getScence() {
        return ((Integer) get(SCENCE, Integer.class)).intValue();
    }

    public int getShareType() {
        return ((Integer) get(SHARE_TYPE, Integer.class)).intValue();
    }

    public String getSite() {
        return (String) get(SITE, String.class);
    }

    public String getSiteUrl() {
        return (String) get(SITE_URL, String.class);
    }

    public String getStack() {
        return (String) get(STACK, String.class);
    }

    public String[] getTags() {
        return (String[]) get(TAGS, String[].class);
    }

    public String getText() {
        return (String) get("text", String.class);
    }

    public String getTitle() {
        return (String) get("title", String.class);
    }

    public String getTitleUrl() {
        return (String) get(TITLE_URL, String.class);
    }

    public String getUrl() {
        return (String) get(URL, String.class);
    }

    public String getVenueDescription() {
        return (String) get(VENUE_DESCRIPTION, String.class);
    }

    public String getVenueName() {
        return (String) get(VENUE_NAME, String.class);
    }

    public boolean isFamily() {
        return ((Boolean) get(IS_FAMILY, Boolean.class)).booleanValue();
    }

    public boolean isFriend() {
        return ((Boolean) get(IS_FRIEND, Boolean.class)).booleanValue();
    }

    public boolean isPublic() {
        return ((Boolean) get(IS_PUBLIC, Boolean.class)).booleanValue();
    }

    public boolean isShareTencentWeibo() {
        return ((Boolean) get(IS_SHARE_TENCENT_WEIBO, Boolean.class)).booleanValue();
    }

    public void set(String key, Object value) {
        this.params.put(key, value);
    }

    public void setAddress(String address) {
        set(ADDRESS, address);
    }

    public void setAuthor(String author) {
        set(AUTHOR, author);
    }

    public void setComment(String comment) {
        set(COMMENT, comment);
    }

    public void setContentType(int contentType) {
        set(CONTENT_TYPE, Integer.valueOf(contentType));
    }

    public void setCustomFlag(String[] customFlag) {
        set(CUSTOM_FLAG, customFlag);
    }

    public void setExecuteUrl() {
        set(EXECUTE_URL, String.class);
    }

    public void setExtInfo(String extInfo) {
        set(EXT_INFO, extInfo);
    }

    public void setFamily(boolean isFamily) {
        set(IS_FAMILY, Boolean.valueOf(isFamily));
    }

    public void setFilePath(String filePath) {
        set(FILE_PATH, filePath);
    }

    public void setFriend(boolean isFriend) {
        set(IS_FRIEND, Boolean.valueOf(isFriend));
    }

    public void setGroupId(String groupId) {
        set(GROPU_ID, groupId);
    }

    public void setHidden(int hidden) {
        set(HIDDEN, Integer.valueOf(hidden));
    }

    public void setImageArray(String[] imagePathArray) {
        set(IMAGE_ARRAY, imagePathArray);
    }

    public void setImageData(Bitmap imageData) {
        set(IMAGE_DATA, imageData);
    }

    public void setImagePath(String imagePath) {
        set(IMAGE_PATH, imagePath);
    }

    public void setImageUrl(String imageUrl) {
        set(IMAGE_URL, imageUrl);
    }

    public void setInstallUrl() {
        set(INSTALL_URL, String.class);
    }

    public void setLatitude(float latitude) {
        set(LATITUDE, Float.valueOf(latitude));
    }

    public void setLongitude(float longitude) {
        set(LONGITUDE, Float.valueOf(longitude));
    }

    public void setMusicUrl(String musicUrl) {
        set(MUSIC_URL, musicUrl);
    }

    public void setNotebook(String notebook) {
        set(NOTEBOOK, notebook);
    }

    public void setPublic(boolean isPublic) {
        set(IS_PUBLIC, Boolean.valueOf(isPublic));
    }

    public void setSafetyLevel(int safetyLevel) {
        set(SAFETY_LEVEL, Integer.valueOf(safetyLevel));
    }

    public void setScence(int scence) {
        set(SCENCE, Integer.valueOf(scence));
    }

    public void setShareTencentWeibo(boolean isShareTencentWeibo) {
        set(IS_SHARE_TENCENT_WEIBO, Boolean.valueOf(isShareTencentWeibo));
    }

    public void setShareType(int shareType) {
        set(SHARE_TYPE, Integer.valueOf(shareType));
    }

    public void setSite(String site) {
        set(SITE, site);
    }

    public void setSiteUrl(String siteUrl) {
        set(SITE_URL, siteUrl);
    }

    public void setStack(String stack) {
        set(STACK, stack);
    }

    public void setTags(String[] tags) {
        set(TAGS, tags);
    }

    public void setText(String text) {
        set("text", text);
    }

    public void setTitle(String title) {
        set("title", title);
    }

    public void setTitleUrl(String titleUrl) {
        set(TITLE_URL, titleUrl);
    }

    public void setUrl(String url) {
        set(URL, url);
    }

    public void setVenueDescription(String venueDescription) {
        set(VENUE_DESCRIPTION, venueDescription);
    }

    public void setVenueName(String venueName) {
        set(VENUE_NAME, venueName);
    }

    public HashMap<String, Object> toMap() {
        return this.params == null ? new HashMap() : this.params;
    }

    public String toString() {
        try {
            return new Hashon().fromHashMap(this.params);
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            return null;
        }
    }
}
