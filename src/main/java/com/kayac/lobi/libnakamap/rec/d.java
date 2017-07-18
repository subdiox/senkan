package com.kayac.lobi.libnakamap.rec;

import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI.FilePathCallback;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI.PostCallback;
import com.kayac.lobi.libnakamap.rec.c.f;
import java.util.Map;

final class d implements FilePathCallback {
    final /* synthetic */ PostCallback a;
    final /* synthetic */ Map b;

    d(PostCallback postCallback, Map map) {
        this.a = postCallback;
        this.b = map;
    }

    public void onResult(String str) {
        if (str == null) {
            this.a.onResult(2, null, null, null);
            return;
        }
        String str2 = str;
        f.a(str2, (String) this.b.get(LoginEntranceDialog.ARGUMENTS_TITLE), (String) this.b.get("description"), 0, "", "", false, false, false, false, false, false, (String) this.b.get("upload_callback_url"), false, true, new e(this, str));
    }
}
