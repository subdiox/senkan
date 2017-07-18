package com.kayac.lobi.sdk.exception;

public final class NakamapResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -2293558621782287857L;

    public NakamapResourceNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NakamapResourceNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public NakamapResourceNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
