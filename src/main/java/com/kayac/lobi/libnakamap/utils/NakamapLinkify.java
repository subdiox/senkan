package com.kayac.lobi.libnakamap.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.kayac.lobi.libnakamap.components.CustomTextView;
import com.kayac.lobi.libnakamap.components.CustomTextView.OnTextLinkClickedListener;
import com.rekoo.libs.net.URLCons;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NakamapLinkify {
    public static final int ALL = 15;
    public static final int EMAIL_ADDRESSES = 2;
    public static final int MAP_ADDRESSES = 8;
    public static final int PHONE_NUMBERS = 4;
    private static final int PHONE_NUMBER_MINIMUM_DIGITS = 5;
    public static final int WEB_URLS = 1;
    public static final MatchFilter sPhoneNumberMatchFilter = new MatchFilter() {
        public final boolean acceptMatch(CharSequence s, int start, int end) {
            int digitCount = 0;
            for (int i = start; i < end; i++) {
                if (Character.isDigit(s.charAt(i))) {
                    digitCount++;
                    if (digitCount >= 5) {
                        return true;
                    }
                }
            }
            return false;
        }
    };
    public static final TransformFilter sPhoneNumberTransformFilter = new TransformFilter() {
        public final String transformUrl(Matcher match, String url) {
            return Patterns.digitsAndPlusOnly(match);
        }
    };
    public static final MatchFilter sUrlMatchFilter = new MatchFilter() {
        public final boolean acceptMatch(CharSequence s, int start, int end) {
            if (start != 0 && s.charAt(start - 1) == '@') {
                return false;
            }
            return true;
        }
    };

    public interface MatchFilter {
        boolean acceptMatch(CharSequence charSequence, int i, int i2);
    }

    public interface TransformFilter {
        String transformUrl(Matcher matcher, String str);
    }

    public static final class ClickableURLSpan extends URLSpan {
        OnTextLinkClickedListener mOnClickListener;

        public ClickableURLSpan(String url, OnTextLinkClickedListener onClicListener) {
            super(url);
            this.mOnClickListener = onClicListener;
            Log.v("nakamap", "[link] ClickableURLSpan: " + onClicListener);
        }

        public void setOnClickListener(OnTextLinkClickedListener listener) {
            this.mOnClickListener = listener;
        }

        public void onClick(View widget) {
            if (this.mOnClickListener == null) {
                super.onClick(widget);
            } else {
                this.mOnClickListener.onTextLinkClicked((CustomTextView) widget, this, getURL());
            }
        }

        public void openInBrowser(View widget) {
            super.onClick(widget);
        }
    }

    public static final boolean addLinks(Spannable text, int mask, OnTextLinkClickedListener onClickListener) {
        if (mask == 0) {
            return false;
        }
        int i;
        ClickableURLSpan[] old = (ClickableURLSpan[]) text.getSpans(0, text.length(), ClickableURLSpan.class);
        for (i = old.length - 1; i >= 0; i--) {
            text.removeSpan(old[i]);
        }
        URLSpan[] old2 = (URLSpan[]) text.getSpans(0, text.length(), URLSpan.class);
        for (i = old2.length - 1; i >= 0; i--) {
            text.removeSpan(old2[i]);
        }
        ArrayList<LinkSpec> links = new ArrayList();
        if ((mask & 1) != 0) {
            Spannable spannable = text;
            gatherLinks(links, spannable, Patterns.WEB_URL, new String[]{URLCons.HTTP, "https://", "rtsp://"}, sUrlMatchFilter, null);
        }
        if ((mask & 2) != 0) {
            gatherLinks(links, text, Patterns.EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((mask & 4) != 0) {
            spannable = text;
            gatherLinks(links, spannable, Patterns.PHONE, new String[]{"tel:"}, sPhoneNumberMatchFilter, sPhoneNumberTransformFilter);
        }
        if ((mask & 8) != 0) {
            gatherMapLinks(links, text);
        }
        pruneOverlaps(links);
        if (links.size() == 0) {
            return false;
        }
        Iterator it = links.iterator();
        while (it.hasNext()) {
            LinkSpec link = (LinkSpec) it.next();
            applyLink(link.url, link.start, link.end, text, onClickListener);
        }
        return true;
    }

    public static final boolean addLinks(TextView text, int mask, OnTextLinkClickedListener onClickListener) {
        if (mask == 0) {
            return false;
        }
        CharSequence t = text.getText();
        if (!(t instanceof Spannable)) {
            Spannable s = SpannableString.valueOf(t);
            if (!addLinks(s, mask, onClickListener)) {
                return false;
            }
            addLinkMovementMethod(text);
            if (text instanceof CustomTextView) {
                ((CustomTextView) text).setSuperText(s, BufferType.SPANNABLE);
            } else {
                text.setText(s);
            }
            return true;
        } else if (!addLinks((Spannable) t, mask, onClickListener)) {
            return false;
        } else {
            addLinkMovementMethod(text);
            return true;
        }
    }

    private static final void addLinkMovementMethod(TextView t) {
        MovementMethod m = t.getMovementMethod();
        if ((m == null || !(m instanceof LinkMovementMethod)) && t.getLinksClickable()) {
            t.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static final void addLinks(TextView text, Pattern pattern, String scheme, OnTextLinkClickedListener onClickListener) {
        addLinks(text, pattern, scheme, null, null, onClickListener);
    }

    public static final void addLinks(TextView text, Pattern p, String scheme, MatchFilter matchFilter, TransformFilter transformFilter, OnTextLinkClickedListener onClickListener) {
        Spannable s = SpannableString.valueOf(text.getText());
        if (addLinks(s, p, scheme, matchFilter, transformFilter, onClickListener)) {
            text.setText(s);
            addLinkMovementMethod(text);
        }
    }

    public static final boolean addLinks(Spannable text, Pattern pattern, String scheme, OnTextLinkClickedListener onClickListener) {
        return addLinks(text, pattern, scheme, null, null, onClickListener);
    }

    public static final boolean addLinks(Spannable s, Pattern p, String scheme, MatchFilter matchFilter, TransformFilter transformFilter, OnTextLinkClickedListener onClickListener) {
        boolean hasMatches = false;
        String prefix = scheme == null ? "" : scheme.toLowerCase();
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            boolean allowed = true;
            if (matchFilter != null) {
                allowed = matchFilter.acceptMatch(s, start, end);
            }
            if (allowed) {
                applyLink(makeUrl(m.group(0), new String[]{prefix}, m, transformFilter), start, end, s, onClickListener);
                hasMatches = true;
            }
        }
        return hasMatches;
    }

    private static final void applyLink(String url, int start, int end, Spannable text, OnTextLinkClickedListener onClickListener) {
        text.setSpan(new ClickableURLSpan(url, onClickListener), start, end, 33);
    }

    private static final String makeUrl(String url, String[] prefixes, Matcher m, TransformFilter filter) {
        if (filter != null) {
            url = filter.transformUrl(m, url);
        }
        boolean hasPrefix = false;
        for (int i = 0; i < prefixes.length; i++) {
            if (url.regionMatches(true, 0, prefixes[i], 0, prefixes[i].length())) {
                hasPrefix = true;
                if (!url.regionMatches(false, 0, prefixes[i], 0, prefixes[i].length())) {
                    url = prefixes[i] + url.substring(prefixes[i].length());
                }
                if (hasPrefix) {
                    return prefixes[0] + url;
                }
                return url;
            }
        }
        if (hasPrefix) {
            return url;
        }
        return prefixes[0] + url;
    }

    private static final void gatherLinks(ArrayList<LinkSpec> links, Spannable s, Pattern pattern, String[] schemes, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            if (matchFilter == null || matchFilter.acceptMatch(s, start, end)) {
                LinkSpec spec = new LinkSpec();
                spec.url = makeUrl(m.group(0), schemes, m, transformFilter);
                spec.start = start;
                spec.end = end;
                links.add(spec);
            }
        }
    }

    private static final void gatherMapLinks(ArrayList<LinkSpec> links, Spannable s) {
        String string = s.toString();
        int base = 0;
        while (true) {
            String address = WebView.findAddress(string);
            if (address != null) {
                int start = string.indexOf(address);
                if (start >= 0) {
                    LinkSpec spec = new LinkSpec();
                    int end = start + address.length();
                    spec.start = base + start;
                    spec.end = base + end;
                    string = string.substring(end);
                    base += end;
                    try {
                        spec.url = "geo:0,0?q=" + URLEncoder.encode(address, "UTF-8");
                        links.add(spec);
                    } catch (UnsupportedEncodingException e) {
                    }
                } else {
                    return;
                }
            }
            return;
        }
    }

    private static final void pruneOverlaps(ArrayList<LinkSpec> links) {
        Collections.sort(links, new Comparator<LinkSpec>() {
            public final int compare(LinkSpec a, LinkSpec b) {
                if (a.start < b.start) {
                    return -1;
                }
                if (a.start > b.start) {
                    return 1;
                }
                if (a.end < b.end) {
                    return 1;
                }
                if (a.end <= b.end) {
                    return 0;
                }
                return -1;
            }

            public final boolean equals(Object o) {
                return false;
            }
        });
        int len = links.size();
        int i = 0;
        while (i < len - 1) {
            LinkSpec a = (LinkSpec) links.get(i);
            LinkSpec b = (LinkSpec) links.get(i + 1);
            int remove = -1;
            if (a.start <= b.start && a.end > b.start) {
                if (b.end <= a.end) {
                    remove = i + 1;
                } else if (a.end - a.start > b.end - b.start) {
                    remove = i + 1;
                } else if (a.end - a.start < b.end - b.start) {
                    remove = i;
                }
                if (remove != -1) {
                    links.remove(remove);
                    len--;
                }
            }
            i++;
        }
    }
}
