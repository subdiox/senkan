package com.kayac.lobi.sdk.utils;

import android.net.Uri;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UrlMatcher {
    private List<Pattern> mPatternList = new ArrayList();

    private class Pattern {
        private String mHost;
        private String mPath;
        private Runnable mRunnable;
        private String mScheme;

        public Pattern(String scheme, String host, String path, Runnable runnable) {
            this.mScheme = scheme;
            this.mHost = host;
            this.mPath = path;
            this.mRunnable = runnable;
        }

        public boolean matching(Uri uri) {
            if (uri == null) {
                return false;
            }
            if (this.mScheme != null && !uri.getScheme().equals(this.mScheme)) {
                return false;
            }
            if (this.mHost != null && !uri.getHost().equals(this.mHost)) {
                return false;
            }
            if (this.mPath == null || uri.getPath().equals(this.mPath)) {
                return true;
            }
            return false;
        }

        public Runnable getAction() {
            return this.mRunnable;
        }
    }

    public void addPattern(String host, Runnable runnable) {
        addPattern(null, host, null, runnable);
    }

    public void addPattern(String scheme, String host, Runnable runnable) {
        addPattern(scheme, host, null, runnable);
    }

    public void addPattern(String scheme, String host, String path, Runnable runnable) {
        this.mPatternList.add(new Pattern(scheme, host, path, runnable));
    }

    public Runnable matchingAction(URI uri) {
        return matchingAction(Uri.parse(uri.toString()));
    }

    public Runnable matchingAction(Uri uri) {
        for (Pattern pattern : this.mPatternList) {
            if (pattern.matching(uri)) {
                return pattern.getAction();
            }
        }
        return null;
    }
}
