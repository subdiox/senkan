package com.kayac.lobi.libnakamap.components;

public interface ToggleInterface {

    public interface OnToggleStateChangedListener {
        void onToggle(ToggleInterface toggleInterface, boolean z);
    }

    void setOff();

    void setOn();
}
