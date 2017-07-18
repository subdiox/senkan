package com.kayac.lobi.sdk.exception;

public final class NakamapIllegalStateException extends RuntimeException {
    private static final long serialVersionUID = 1555376062230218693L;

    public NakamapIllegalStateException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NakamapIllegalStateException(String detailMessage) {
        super(detailMessage);
    }

    public NakamapIllegalStateException(Throwable throwable) {
        super(throwable);
    }
}
