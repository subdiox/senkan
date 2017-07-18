package cn.sharesdk.framework;

import java.util.Comparator;

class m implements Comparator<Platform> {
    final /* synthetic */ l a;

    m(l lVar) {
        this.a = lVar;
    }

    public int a(Platform platform, Platform platform2) {
        return platform.getSortId() != platform2.getSortId() ? platform.getSortId() - platform2.getSortId() : platform.getPlatformId() - platform2.getPlatformId();
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((Platform) obj, (Platform) obj2);
    }
}
