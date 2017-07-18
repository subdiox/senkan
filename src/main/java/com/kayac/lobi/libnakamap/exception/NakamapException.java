package com.kayac.lobi.libnakamap.exception;

public class NakamapException {

    public static class CurrentUserNotSet extends RuntimeException {
        public CurrentUserNotSet(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public CurrentUserNotSet(String detailMessage) {
            super(detailMessage);
        }

        public CurrentUserNotSet(Throwable throwable) {
            super(throwable);
        }
    }

    public static class Fatal extends RuntimeException {
        public Fatal(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public Fatal(String detailMessage) {
            super(detailMessage);
        }

        public Fatal(Throwable throwable) {
            super(throwable);
        }
    }
}
