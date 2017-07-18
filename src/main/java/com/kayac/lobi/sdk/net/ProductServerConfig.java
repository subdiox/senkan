package com.kayac.lobi.sdk.net;

public final class ProductServerConfig implements IServerConfig {
    public String apiDomain() {
        return "thanks.nakamap.com";
    }

    public String nakamapappsDomain() {
        return "nakamapapps.com";
    }

    public String nakamapappsURL() {
        return "https://nakamapapps.com";
    }

    public String webDomain() {
        return "nakamap.com";
    }

    public static IServerConfig getDefault() {
        return null;
    }
}
