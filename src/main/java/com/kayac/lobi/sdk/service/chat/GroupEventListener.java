package com.kayac.lobi.sdk.service.chat;

import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import java.util.EventListener;

public interface GroupEventListener extends EventListener {
    void onMessage(GroupStreamValue groupStreamValue);
}
