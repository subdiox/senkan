package cn.sharesdk.framework.utils;

import android.text.TextUtils;
import android.util.Xml;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class i {

    private static class a extends DefaultHandler {
        private HashMap<String, Object> a = new HashMap();
        private HashMap<String, Object> b;

        public HashMap<String, Object> a() {
            return this.a;
        }

        public void characters(char[] ch, int start, int length) {
            CharSequence trim = String.valueOf(ch, start, length).trim();
            if (!TextUtils.isEmpty(trim) && this.b != null) {
                this.b.put("value", trim);
            }
        }

        public void endElement(String uri, String localName, String qName) {
            this.b = null;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (this.b != null) {
                HashMap hashMap = new HashMap();
                this.b.put(localName, hashMap);
                this.b = hashMap;
            } else {
                this.b = new HashMap();
                this.a.put(localName, this.b);
            }
            int length = attributes.getLength();
            for (int i = 0; i < length; i++) {
                this.b.put(attributes.getLocalName(i), attributes.getValue(i));
            }
        }
    }

    public HashMap<String, Object> a(String str) {
        Object aVar = new a();
        Xml.parse(str, aVar);
        return aVar.a();
    }
}
