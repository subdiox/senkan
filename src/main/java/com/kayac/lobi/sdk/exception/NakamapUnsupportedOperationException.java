package com.kayac.lobi.sdk.exception;

public final class NakamapUnsupportedOperationException extends Exception {
    private static final long serialVersionUID = -9217694509740327858L;

    public NakamapUnsupportedOperationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NakamapUnsupportedOperationException(String detailMessage) {
        super(detailMessage);
    }

    public NakamapUnsupportedOperationException(Throwable throwable) {
        super(throwable);
    }
}
