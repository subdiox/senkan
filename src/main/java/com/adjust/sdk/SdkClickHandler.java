package com.adjust.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SdkClickHandler implements ISdkClickHandler {
    private BackoffStrategy backoffStrategy = AdjustFactory.getSdkClickBackoffStrategy();
    private ILogger logger = AdjustFactory.getLogger();
    private List<ActivityPackage> packageQueue;
    private boolean paused;
    private CustomScheduledExecutor scheduledExecutor = new CustomScheduledExecutor("SdkClickHandler", false);

    public void teardown() {
        this.logger.verbose("SdkClickHandler teardown", new Object[0]);
        if (this.scheduledExecutor != null) {
            try {
                this.scheduledExecutor.shutdownNow();
            } catch (SecurityException e) {
            }
        }
        if (this.packageQueue != null) {
            this.packageQueue.clear();
        }
        this.scheduledExecutor = null;
        this.logger = null;
        this.packageQueue = null;
        this.backoffStrategy = null;
    }

    public SdkClickHandler(boolean startsSending) {
        init(startsSending);
    }

    public void init(boolean startsSending) {
        this.paused = !startsSending;
        this.packageQueue = new ArrayList();
    }

    public void pauseSending() {
        this.paused = true;
    }

    public void resumeSending() {
        this.paused = false;
        sendNextSdkClick();
    }

    public void sendSdkClick(final ActivityPackage sdkClick) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                SdkClickHandler.this.packageQueue.add(sdkClick);
                SdkClickHandler.this.logger.debug("Added sdk_click %d", Integer.valueOf(SdkClickHandler.this.packageQueue.size()));
                SdkClickHandler.this.logger.verbose("%s", sdkClick.getExtendedString());
                SdkClickHandler.this.sendNextSdkClick();
            }
        });
    }

    private void sendNextSdkClick() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                SdkClickHandler.this.sendNextSdkClickI();
            }
        });
    }

    private void sendNextSdkClickI() {
        if (!this.paused && !this.packageQueue.isEmpty()) {
            final ActivityPackage sdkClickPackage = (ActivityPackage) this.packageQueue.remove(0);
            int retries = sdkClickPackage.getRetries();
            Runnable runnable = new Runnable() {
                public void run() {
                    SdkClickHandler.this.sendSdkClickI(sdkClickPackage);
                    SdkClickHandler.this.sendNextSdkClick();
                }
            };
            if (retries <= 0) {
                runnable.run();
                return;
            }
            long waitTimeMilliSeconds = Util.getWaitingTime(retries, this.backoffStrategy);
            String secondsString = Util.SecondsDisplayFormat.format(((double) waitTimeMilliSeconds) / 1000.0d);
            this.logger.verbose("Waiting for %s seconds before retrying sdk_click for the %d time", secondsString, Integer.valueOf(retries));
            this.scheduledExecutor.schedule(runnable, waitTimeMilliSeconds, TimeUnit.MILLISECONDS);
        }
    }

    private void sendSdkClickI(ActivityPackage sdkClickPackage) {
        try {
            if (UtilNetworking.createPOSTHttpsURLConnection(Constants.BASE_URL + sdkClickPackage.getPath(), sdkClickPackage, this.packageQueue.size() - 1).jsonResponse == null) {
                retrySendingI(sdkClickPackage);
            }
        } catch (UnsupportedEncodingException e) {
            logErrorMessageI(sdkClickPackage, "Sdk_click failed to encode parameters", e);
        } catch (SocketTimeoutException e2) {
            logErrorMessageI(sdkClickPackage, "Sdk_click request timed out. Will retry later", e2);
            retrySendingI(sdkClickPackage);
        } catch (IOException e3) {
            logErrorMessageI(sdkClickPackage, "Sdk_click request failed. Will retry later", e3);
            retrySendingI(sdkClickPackage);
        } catch (Throwable e4) {
            logErrorMessageI(sdkClickPackage, "Sdk_click runtime exception", e4);
        }
    }

    private void retrySendingI(ActivityPackage sdkClickPackage) {
        int retries = sdkClickPackage.increaseRetries();
        this.logger.error("Retrying sdk_click package for the %d time", Integer.valueOf(retries));
        sendSdkClick(sdkClickPackage);
    }

    private void logErrorMessageI(ActivityPackage sdkClickPackage, String message, Throwable throwable) {
        String packageMessage = sdkClickPackage.getFailureMessage();
        String reasonString = Util.getReasonString(message, throwable);
        this.logger.error(String.format("%s. (%s)", new Object[]{packageMessage, reasonString}), new Object[0]);
    }
}
