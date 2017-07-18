package com.kayac.lobi.sdk.service.chat.sdk;

import com.kayac.lobi.libnakamap.net.APIDef.PostGroupExId;

public class GroupEventPollingTaskSetting {
    private static final Impl sImpl = new Impl() {
        public String getStreamHost(String streamHost) {
            return "thanks-stream.nakamap.com";
        }

        public String getApiPath() {
            return PostGroupExId.PATH;
        }
    };

    public interface Impl {
        String getApiPath();

        String getStreamHost(String str);
    }

    public static final String getApiPath() {
        return sImpl.getApiPath();
    }

    public static final String getStreamHost(String streamHost) {
        return sImpl.getStreamHost(streamHost);
    }
}
