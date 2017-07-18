package cn.sharesdk.line;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.b.b.f.a;
import com.mob.tools.utils.BitmapHelper;
import java.io.File;
import java.util.HashMap;

public class Line extends Platform {
    public static final String NAME = Line.class.getSimpleName();

    public Line(Context context) {
        super(context);
    }

    protected boolean checkAuthorize(int action, Object extra) {
        if (isClientValid()) {
            return true;
        }
        if (this.listener != null) {
            this.listener.onError(this, action, new LineClientNotExistException());
        }
        return false;
    }

    protected void doAuthorize(String[] permissions) {
    }

    protected void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
        if (this.listener != null) {
            this.listener.onCancel(this, customerAction);
        }
    }

    protected void doShare(ShareParams params) {
        Object text = params.getText();
        HashMap hashMap;
        if (TextUtils.isEmpty(text)) {
            String imagePath = params.getImagePath();
            if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists()) {
                try {
                    imagePath = BitmapHelper.downloadBitmap(this.context, params.getImageUrl());
                    if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
                        try {
                            a.a((Platform) this).b(imagePath);
                            if (this.listener != null) {
                                hashMap = new HashMap();
                                hashMap.put("ShareParams", params);
                                this.listener.onComplete(this, 9, hashMap);
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            if (this.listener != null) {
                                this.listener.onError(this, 9, th);
                                return;
                            }
                            return;
                        }
                    } else if (this.listener != null) {
                        this.listener.onError(this, 9, new Throwable("both text and image are null"));
                        return;
                    } else {
                        return;
                    }
                } catch (Throwable th2) {
                    if (this.listener != null) {
                        this.listener.onError(this, 9, th2);
                        return;
                    }
                    return;
                }
            }
            try {
                a.a((Platform) this).b(imagePath);
                if (this.listener != null) {
                    hashMap = new HashMap();
                    hashMap.put("ShareParams", params);
                    this.listener.onComplete(this, 9, hashMap);
                    return;
                }
                return;
            } catch (Throwable th22) {
                if (this.listener != null) {
                    this.listener.onError(this, 9, th22);
                    return;
                }
                return;
            }
        }
        try {
            a.a((Platform) this).a(getShortLintk(text, false));
            if (this.listener != null) {
                hashMap = new HashMap();
                hashMap.put("ShareParams", params);
                this.listener.onComplete(this, 9, hashMap);
            }
        } catch (Throwable th222) {
            if (this.listener != null) {
                this.listener.onError(this, 9, th222);
            }
        }
    }

    protected HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> hashMap) {
        return null;
    }

    protected a filterShareContent(ShareParams params, HashMap<String, Object> hashMap) {
        a aVar = new a();
        aVar.b = params.getText();
        String imageUrl = params.getImageUrl();
        if (imageUrl != null) {
            aVar.d.add(imageUrl);
        } else {
            imageUrl = params.getImagePath();
            if (imageUrl != null) {
                aVar.e.add(imageUrl);
            }
        }
        return aVar;
    }

    protected void follow(String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 6);
        }
    }

    protected HashMap<String, Object> getBilaterals(int count, int cursor, String account) {
        return null;
    }

    protected HashMap<String, Object> getFollowers(int count, int cursor, String account) {
        return null;
    }

    protected HashMap<String, Object> getFollowings(int count, int page, String account) {
        return null;
    }

    protected void getFriendList(int count, int cursor, String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 2);
        }
    }

    public String getName() {
        return NAME;
    }

    protected int getPlatformId() {
        return 42;
    }

    public int getVersion() {
        return 1;
    }

    public boolean hasShareCallback() {
        return false;
    }

    protected void initDevInfo(String name) {
    }

    public boolean isClientValid() {
        return a.a((Platform) this).a();
    }

    @Deprecated
    public boolean isValid() {
        return a.a((Platform) this).a();
    }

    protected void setNetworkDevinfo() {
    }

    protected void timeline(int count, int page, String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 7);
        }
    }

    protected void userInfor(String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 8);
        }
    }
}
