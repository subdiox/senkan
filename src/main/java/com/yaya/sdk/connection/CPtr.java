package com.yaya.sdk.connection;

public class CPtr {
    private long peer;

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (CPtr.class == other.getClass() && this.peer == ((CPtr) other).peer) {
            return true;
        }
        return false;
    }

    protected long getPeer() {
        return this.peer;
    }

    CPtr() {
    }
}
