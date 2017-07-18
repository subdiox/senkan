package com.kayac.lobi.sdk.exception;

public final class NakamapInvalidInvitaionPrameterException extends RuntimeException {
    private static final long serialVersionUID = -8904001252814118077L;

    public NakamapInvalidInvitaionPrameterException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NakamapInvalidInvitaionPrameterException(String detailMessage) {
        super(detailMessage);
    }

    public NakamapInvalidInvitaionPrameterException(Throwable throwable) {
        super(throwable);
    }
}
