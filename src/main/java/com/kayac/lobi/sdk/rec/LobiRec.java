package com.kayac.lobi.sdk.rec;

import com.kayac.lobi.libnakamap.rec.LobiRecAPI;

public class LobiRec {
    public static void showPlaylist() {
        showPlaylist(null, null, false, null);
    }

    public static void showPlaylist(String str, String str2, boolean z, String str3) {
        LobiRecAPI.openLobiPlayActivity(str, str2, z, str3);
    }
}
