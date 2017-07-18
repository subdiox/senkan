package com.kayac.lobi.sdk.net;

public class ServerConfig {
    private static final IServerConfig sServerConfig = new ProductServerConfig();

    public static final IServerConfig getServerConfig() {
        return sServerConfig;
    }
}
