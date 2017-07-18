package com.kayac.lobi.libnakamap.rec;

import com.kayac.lobi.libnakamap.rec.LobiRecAPI.FilePathCallback;
import java.io.File;

final class c implements com.kayac.lobi.libnakamap.rec.b.a.c {
    final /* synthetic */ FilePathCallback a;

    c(FilePathCallback filePathCallback) {
        this.a = filePathCallback;
    }

    public void onLoadVideo(File file) {
        if (file == null) {
            this.a.onResult(null);
        } else {
            this.a.onResult(file.getAbsolutePath());
        }
    }
}
