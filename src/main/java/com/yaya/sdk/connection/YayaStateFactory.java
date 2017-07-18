package com.yaya.sdk.connection;

import java.util.ArrayList;
import java.util.List;

public final class YayaStateFactory {
    private static final List states = new ArrayList();

    private YayaStateFactory() {
    }

    public static synchronized YayaLib newYayaState() {
        YayaLib yayaLib;
        synchronized (YayaStateFactory.class) {
            int nextStateIndex = getNextStateIndex();
            yayaLib = new YayaLib(nextStateIndex);
            states.add(nextStateIndex, yayaLib);
        }
        return yayaLib;
    }

    public static synchronized YayaLib getExistingState(int index) {
        YayaLib yayaLib;
        synchronized (YayaStateFactory.class) {
            yayaLib = (YayaLib) states.get(index);
        }
        return yayaLib;
    }

    public static synchronized int insertYayaState(YayaLib L) {
        int i;
        synchronized (YayaStateFactory.class) {
            for (int i2 = 0; i2 < states.size(); i2++) {
                YayaLib yayaLib = (YayaLib) states.get(i2);
                if (yayaLib != null && yayaLib.getCPtrPeer() == L.getCPtrPeer()) {
                    i = i2;
                    break;
                }
            }
            i = getNextStateIndex();
            states.set(i, L);
        }
        return i;
    }

    public static synchronized void removeYayaState(int idx) {
        synchronized (YayaStateFactory.class) {
            states.add(idx, null);
        }
    }

    private static synchronized int getNextStateIndex() {
        int i;
        synchronized (YayaStateFactory.class) {
            i = 0;
            while (i < states.size() && states.get(i) != null) {
                i++;
            }
        }
        return i;
    }
}
