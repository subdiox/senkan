package com.adjust.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.os.Handler;
import android.os.Process;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ActivityHandler implements IActivityHandler {
    private static final String ACTIVITY_STATE_NAME = "Activity state";
    private static final String ADJUST_PREFIX = "adjust_";
    private static final String ATTRIBUTION_NAME = "Attribution";
    private static long BACKGROUND_TIMER_INTERVAL = 0;
    private static final String BACKGROUND_TIMER_NAME = "Background timer";
    private static final String DELAY_START_TIMER_NAME = "Delay Start timer";
    private static long FOREGROUND_TIMER_INTERVAL = 0;
    private static final String FOREGROUND_TIMER_NAME = "Foreground timer";
    private static long FOREGROUND_TIMER_START = 0;
    private static final String SESSION_CALLBACK_PARAMETERS_NAME = "Session Callback parameters";
    private static long SESSION_INTERVAL = 0;
    private static final String SESSION_PARTNER_PARAMETERS_NAME = "Session Partner parameters";
    private static long SUBSESSION_INTERVAL = 0;
    private static final String TIME_TRAVEL = "Time travel!";
    private ActivityState activityState;
    private AdjustConfig adjustConfig;
    private AdjustAttribution attribution;
    private IAttributionHandler attributionHandler;
    private TimerOnce backgroundTimer;
    private TimerOnce delayStartTimer;
    private DeviceInfo deviceInfo;
    private TimerCycle foregroundTimer;
    private InternalState internalState;
    private ILogger logger = AdjustFactory.getLogger();
    private IPackageHandler packageHandler;
    private CustomScheduledExecutor scheduledExecutor;
    private ISdkClickHandler sdkClickHandler;
    private SessionParameters sessionParameters;

    public class InternalState {
        boolean background;
        boolean delayStart;
        boolean enabled;
        boolean firstLaunch;
        boolean offline;
        boolean sessionResponseProcessed;
        boolean updatePackages;

        public boolean isEnabled() {
            return this.enabled;
        }

        public boolean isDisabled() {
            return !this.enabled;
        }

        public boolean isOffline() {
            return this.offline;
        }

        public boolean isOnline() {
            return !this.offline;
        }

        public boolean isBackground() {
            return this.background;
        }

        public boolean isForeground() {
            return !this.background;
        }

        public boolean isDelayStart() {
            return this.delayStart;
        }

        public boolean isToStartNow() {
            return !this.delayStart;
        }

        public boolean isToUpdatePackages() {
            return this.updatePackages;
        }

        public boolean isFirstLaunch() {
            return this.firstLaunch;
        }

        public boolean isSessionResponseProcessed() {
            return this.sessionResponseProcessed;
        }
    }

    public void teardown(boolean deleteState) {
        if (this.backgroundTimer != null) {
            this.backgroundTimer.teardown();
        }
        if (this.foregroundTimer != null) {
            this.foregroundTimer.teardown();
        }
        if (this.delayStartTimer != null) {
            this.delayStartTimer.teardown();
        }
        if (this.scheduledExecutor != null) {
            try {
                this.scheduledExecutor.shutdownNow();
            } catch (SecurityException e) {
            }
        }
        if (this.packageHandler != null) {
            this.packageHandler.teardown(deleteState);
        }
        if (this.attributionHandler != null) {
            this.attributionHandler.teardown();
        }
        if (this.sdkClickHandler != null) {
            this.sdkClickHandler.teardown();
        }
        if (this.sessionParameters != null) {
            if (this.sessionParameters.callbackParameters != null) {
                this.sessionParameters.callbackParameters.clear();
            }
            if (this.sessionParameters.partnerParameters != null) {
                this.sessionParameters.partnerParameters.clear();
            }
        }
        teardownActivityStateS(deleteState);
        teardownAttributionS(deleteState);
        teardownAllSessionParametersS(deleteState);
        this.packageHandler = null;
        this.logger = null;
        this.foregroundTimer = null;
        this.scheduledExecutor = null;
        this.backgroundTimer = null;
        this.delayStartTimer = null;
        this.internalState = null;
        this.deviceInfo = null;
        this.adjustConfig = null;
        this.attributionHandler = null;
        this.sdkClickHandler = null;
        this.sessionParameters = null;
    }

    private ActivityHandler(AdjustConfig adjustConfig) {
        init(adjustConfig);
        this.logger.lockLogLevel();
        this.scheduledExecutor = new CustomScheduledExecutor("ActivityHandler", false);
        this.internalState = new InternalState();
        this.internalState.enabled = true;
        this.internalState.offline = false;
        this.internalState.background = true;
        this.internalState.delayStart = false;
        this.internalState.updatePackages = false;
        this.internalState.sessionResponseProcessed = false;
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.initI();
            }
        });
    }

    public void init(AdjustConfig adjustConfig) {
        this.adjustConfig = adjustConfig;
    }

    public static ActivityHandler getInstance(AdjustConfig adjustConfig) {
        if (adjustConfig == null) {
            AdjustFactory.getLogger().error("AdjustConfig missing", new Object[0]);
            return null;
        } else if (adjustConfig.isValid()) {
            if (adjustConfig.processName != null) {
                int currentPid = Process.myPid();
                ActivityManager manager = (ActivityManager) adjustConfig.context.getSystemService("activity");
                if (manager == null) {
                    return null;
                }
                for (RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                    if (processInfo.pid == currentPid) {
                        if (!processInfo.processName.equalsIgnoreCase(adjustConfig.processName)) {
                            AdjustFactory.getLogger().info("Skipping initialization in background process (%s)", processInfo.processName);
                            return null;
                        }
                    }
                }
            }
            return new ActivityHandler(adjustConfig);
        } else {
            AdjustFactory.getLogger().error("AdjustConfig not initialized correctly", new Object[0]);
            return null;
        }
    }

    public void onResume() {
        this.internalState.background = false;
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.delayStartI();
                ActivityHandler.this.stopBackgroundTimerI();
                ActivityHandler.this.startForegroundTimerI();
                ActivityHandler.this.logger.verbose("Subsession start", new Object[0]);
                ActivityHandler.this.startI();
            }
        });
    }

    public void onPause() {
        this.internalState.background = true;
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.stopForegroundTimerI();
                ActivityHandler.this.startBackgroundTimerI();
                ActivityHandler.this.logger.verbose("Subsession end", new Object[0]);
                ActivityHandler.this.endI();
            }
        });
    }

    public void trackEvent(final AdjustEvent event) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                if (ActivityHandler.this.activityState == null) {
                    ActivityHandler.this.logger.warn("Event tracked before first activity resumed.\nIf it was triggered in the Application class, it might timestamp or even send an install long before the user opens the app.\nPlease check https://github.com/adjust/android_sdk#can-i-trigger-an-event-at-application-launch for more information.", new Object[0]);
                    ActivityHandler.this.startI();
                }
                ActivityHandler.this.trackEventI(event);
            }
        });
    }

    public void finishedTrackingActivity(ResponseData responseData) {
        if (responseData instanceof SessionResponseData) {
            this.attributionHandler.checkSessionResponse((SessionResponseData) responseData);
        } else if (responseData instanceof EventResponseData) {
            launchEventResponseTasks((EventResponseData) responseData);
        }
    }

    public void setEnabled(final boolean enabled) {
        boolean z = true;
        if (hasChangedState(isEnabled(), enabled, "Adjust already enabled", "Adjust already disabled")) {
            this.internalState.enabled = enabled;
            if (this.activityState == null) {
                if (enabled) {
                    z = false;
                }
                updateStatus(z, "Handlers will start as paused due to the SDK being disabled", "Handlers will still start as paused", "Handlers will start as active due to the SDK being enabled");
                return;
            }
            writeActivityStateS(new Runnable() {
                public void run() {
                    ActivityHandler.this.activityState.enabled = enabled;
                }
            });
            if (enabled) {
                z = false;
            }
            updateStatus(z, "Pausing handlers due to SDK being disabled", "Handlers remain paused", "Resuming handlers due to SDK being enabled");
        }
    }

    private void updateStatus(boolean pausingState, String pausingMessage, String remainsPausedMessage, String unPausingMessage) {
        if (pausingState) {
            this.logger.info(pausingMessage, new Object[0]);
        } else if (!pausedI(false)) {
            this.logger.info(unPausingMessage, new Object[0]);
        } else if (pausedI(true)) {
            this.logger.info(remainsPausedMessage, new Object[0]);
        } else {
            this.logger.info(remainsPausedMessage + ", except the Sdk Click Handler", new Object[0]);
        }
        updateHandlersStatusAndSend();
    }

    private boolean hasChangedState(boolean previousState, boolean newState, String trueMessage, String falseMessage) {
        if (previousState != newState) {
            return true;
        }
        if (previousState) {
            this.logger.debug(trueMessage, new Object[0]);
            return false;
        }
        this.logger.debug(falseMessage, new Object[0]);
        return false;
    }

    public void setOfflineMode(boolean offline) {
        if (hasChangedState(this.internalState.isOffline(), offline, "Adjust already in offline mode", "Adjust already in online mode")) {
            this.internalState.offline = offline;
            if (this.activityState == null) {
                updateStatus(offline, "Handlers will start paused due to SDK being offline", "Handlers will still start as paused", "Handlers will start as active due to SDK being online");
            } else {
                updateStatus(offline, "Pausing handlers to put SDK offline mode", "Handlers remain paused", "Resuming handlers to put SDK in online mode");
            }
        }
    }

    public boolean isEnabled() {
        return isEnabledI();
    }

    private boolean isEnabledI() {
        if (this.activityState != null) {
            return this.activityState.enabled;
        }
        return this.internalState.isEnabled();
    }

    public void readOpenUrl(final Uri url, final long clickTime) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.readOpenUrlI(url, clickTime);
            }
        });
    }

    private void updateAdidI(String adid) {
        if (adid != null && !adid.equals(this.activityState.adid)) {
            this.activityState.adid = adid;
            writeActivityStateI();
        }
    }

    public boolean updateAttributionI(AdjustAttribution attribution) {
        if (attribution == null || attribution.equals(this.attribution)) {
            return false;
        }
        this.attribution = attribution;
        writeAttributionI();
        return true;
    }

    public void setAskingAttribution(final boolean askingAttribution) {
        writeActivityStateS(new Runnable() {
            public void run() {
                ActivityHandler.this.activityState.askingAttribution = askingAttribution;
            }
        });
    }

    public void sendReferrer(final String referrer, final long clickTime) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.sendReferrerI(referrer, clickTime);
            }
        });
    }

    public void launchEventResponseTasks(final EventResponseData eventResponseData) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchEventResponseTasksI(eventResponseData);
            }
        });
    }

    public void launchSessionResponseTasks(final SessionResponseData sessionResponseData) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchSessionResponseTasksI(sessionResponseData);
            }
        });
    }

    public void launchAttributionResponseTasks(final AttributionResponseData attributionResponseData) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.launchAttributionResponseTasksI(attributionResponseData);
            }
        });
    }

    public void sendFirstPackages() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.sendFirstPackagesI();
            }
        });
    }

    public void addSessionCallbackParameter(final String key, final String value) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.addSessionCallbackParameterI(key, value);
            }
        });
    }

    public void addSessionPartnerParameter(final String key, final String value) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.addSessionPartnerParameterI(key, value);
            }
        });
    }

    public void removeSessionCallbackParameter(final String key) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.removeSessionCallbackParameterI(key);
            }
        });
    }

    public void removeSessionPartnerParameter(final String key) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.removeSessionPartnerParameterI(key);
            }
        });
    }

    public void resetSessionCallbackParameters() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.resetSessionCallbackParametersI();
            }
        });
    }

    public void resetSessionPartnerParameters() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.resetSessionPartnerParametersI();
            }
        });
    }

    public void setPushToken(final String token) {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                if (ActivityHandler.this.activityState == null) {
                    ActivityHandler.this.startI();
                }
                ActivityHandler.this.setPushTokenI(token);
            }
        });
    }

    public void foregroundTimerFired() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.foregroundTimerFiredI();
            }
        });
    }

    public void backgroundTimerFired() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.backgroundTimerFiredI();
            }
        });
    }

    public String getAdid() {
        if (this.activityState == null) {
            return null;
        }
        return this.activityState.adid;
    }

    public AdjustAttribution getAttribution() {
        return this.attribution;
    }

    public ActivityPackage getAttributionPackageI() {
        return new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, System.currentTimeMillis()).buildAttributionPackage();
    }

    public InternalState getInternalState() {
        return this.internalState;
    }

    private void updateHandlersStatusAndSend() {
        this.scheduledExecutor.submit(new Runnable() {
            public void run() {
                ActivityHandler.this.updateHandlersStatusAndSendI();
            }
        });
    }

    private void initI() {
        SESSION_INTERVAL = AdjustFactory.getSessionInterval();
        SUBSESSION_INTERVAL = AdjustFactory.getSubsessionInterval();
        FOREGROUND_TIMER_INTERVAL = AdjustFactory.getTimerInterval();
        FOREGROUND_TIMER_START = AdjustFactory.getTimerStart();
        BACKGROUND_TIMER_INTERVAL = AdjustFactory.getTimerInterval();
        readAttributionI(this.adjustConfig.context);
        readActivityStateI(this.adjustConfig.context);
        this.sessionParameters = new SessionParameters();
        readSessionCallbackParametersI(this.adjustConfig.context);
        readSessionPartnerParametersI(this.adjustConfig.context);
        if (this.activityState != null) {
            this.internalState.enabled = this.activityState.enabled;
            this.internalState.updatePackages = this.activityState.updatePackages;
            this.internalState.firstLaunch = false;
        } else {
            this.internalState.firstLaunch = true;
        }
        readConfigFile(this.adjustConfig.context);
        this.deviceInfo = new DeviceInfo(this.adjustConfig.context, this.adjustConfig.sdkPrefix);
        if (this.adjustConfig.eventBufferingEnabled) {
            this.logger.info("Event buffering is enabled", new Object[0]);
        }
        if (Util.getPlayAdId(this.adjustConfig.context) == null) {
            this.logger.warn("Unable to get Google Play Services Advertising ID at start time", new Object[0]);
            if (this.deviceInfo.macSha1 == null && this.deviceInfo.macShortMd5 == null && this.deviceInfo.androidId == null) {
                this.logger.error("Unable to get any device id's. Please check if Proguard is correctly set with Adjust SDK", new Object[0]);
            }
        } else {
            this.logger.info("Google Play Services Advertising ID read correctly at start time", new Object[0]);
        }
        if (this.adjustConfig.defaultTracker != null) {
            this.logger.info("Default tracker: '%s'", this.adjustConfig.defaultTracker);
        }
        if (this.adjustConfig.pushToken != null) {
            this.logger.info("Push token: '%s'", this.adjustConfig.pushToken);
            if (this.activityState != null) {
                setPushToken(this.adjustConfig.pushToken);
            }
        }
        this.foregroundTimer = new TimerCycle(new Runnable() {
            public void run() {
                ActivityHandler.this.foregroundTimerFired();
            }
        }, FOREGROUND_TIMER_START, FOREGROUND_TIMER_INTERVAL, FOREGROUND_TIMER_NAME);
        if (this.adjustConfig.sendInBackground) {
            this.logger.info("Send in background configured", new Object[0]);
            this.backgroundTimer = new TimerOnce(new Runnable() {
                public void run() {
                    ActivityHandler.this.backgroundTimerFired();
                }
            }, BACKGROUND_TIMER_NAME);
        }
        if (this.activityState == null && this.adjustConfig.delayStart != null && this.adjustConfig.delayStart.doubleValue() > 0.0d) {
            this.logger.info("Delay start configured", new Object[0]);
            this.internalState.delayStart = true;
            this.delayStartTimer = new TimerOnce(new Runnable() {
                public void run() {
                    ActivityHandler.this.sendFirstPackages();
                }
            }, DELAY_START_TIMER_NAME);
        }
        UtilNetworking.setUserAgent(this.adjustConfig.userAgent);
        this.packageHandler = AdjustFactory.getPackageHandler(this, this.adjustConfig.context, toSendI(false));
        this.attributionHandler = AdjustFactory.getAttributionHandler(this, getAttributionPackageI(), toSendI(false));
        this.sdkClickHandler = AdjustFactory.getSdkClickHandler(toSendI(true));
        if (isToUpdatePackagesI()) {
            updatePackagesI();
        }
        if (this.adjustConfig.referrer != null) {
            sendReferrerI(this.adjustConfig.referrer, this.adjustConfig.referrerClickTime);
        }
        sessionParametersActionsI(this.adjustConfig.sessionParametersActionsArray);
    }

    private void readConfigFile(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("adjust_config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            this.logger.verbose("adjust_config.properties file read and loaded", new Object[0]);
            String defaultTracker = properties.getProperty("defaultTracker");
            if (defaultTracker != null) {
                this.adjustConfig.defaultTracker = defaultTracker;
            }
        } catch (Exception e) {
            this.logger.debug("%s file not found in this app", e.getMessage());
        }
    }

    private void sessionParametersActionsI(List<IRunActivityHandler> sessionParametersActionsArray) {
        if (sessionParametersActionsArray != null) {
            for (IRunActivityHandler sessionParametersAction : sessionParametersActionsArray) {
                sessionParametersAction.run(this);
            }
        }
    }

    private void startI() {
        if (this.activityState == null || this.activityState.enabled) {
            updateHandlersStatusAndSendI();
            processSessionI();
            checkAttributionStateI();
        }
    }

    private void processSessionI() {
        long now = System.currentTimeMillis();
        if (this.activityState == null) {
            this.activityState = new ActivityState();
            this.activityState.sessionCount = 1;
            this.activityState.pushToken = this.adjustConfig.pushToken;
            transferSessionPackageI(now);
            this.activityState.resetSessionAttributes(now);
            this.activityState.enabled = this.internalState.isEnabled();
            this.activityState.updatePackages = this.internalState.isToUpdatePackages();
            writeActivityStateI();
            return;
        }
        long lastInterval = now - this.activityState.lastActivity;
        if (lastInterval < 0) {
            this.logger.error(TIME_TRAVEL, new Object[0]);
            this.activityState.lastActivity = now;
            writeActivityStateI();
        } else if (lastInterval > SESSION_INTERVAL) {
            r4 = this.activityState;
            r4.sessionCount++;
            this.activityState.lastInterval = lastInterval;
            transferSessionPackageI(now);
            this.activityState.resetSessionAttributes(now);
            writeActivityStateI();
        } else if (lastInterval > SUBSESSION_INTERVAL) {
            r4 = this.activityState;
            r4.subsessionCount++;
            r4 = this.activityState;
            r4.sessionLength += lastInterval;
            this.activityState.lastActivity = now;
            this.logger.verbose("Started subsession %d of session %d", Integer.valueOf(this.activityState.subsessionCount), Integer.valueOf(this.activityState.sessionCount));
            writeActivityStateI();
        } else {
            this.logger.verbose("Time span since last activity too short for a new subsession", new Object[0]);
        }
    }

    private void checkAttributionStateI() {
        if (!checkActivityStateI(this.activityState)) {
            return;
        }
        if (this.internalState.isFirstLaunch() && !this.internalState.isSessionResponseProcessed()) {
            return;
        }
        if (this.attribution == null || this.activityState.askingAttribution) {
            this.attributionHandler.getAttribution();
        }
    }

    private void endI() {
        if (!toSendI()) {
            pauseSendingI();
        }
        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    private void trackEventI(AdjustEvent event) {
        if (checkActivityStateI(this.activityState) && isEnabledI() && checkEventI(event) && checkOrderIdI(event.orderId)) {
            long now = System.currentTimeMillis();
            ActivityState activityState = this.activityState;
            activityState.eventCount++;
            updateActivityStateI(now);
            this.packageHandler.addPackage(new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, now).buildEventPackage(event, this.sessionParameters, this.internalState.isDelayStart()));
            if (this.adjustConfig.eventBufferingEnabled) {
                this.logger.info("Buffered event %s", eventPackage.getSuffix());
            } else {
                this.packageHandler.sendFirstPackage();
            }
            if (this.adjustConfig.sendInBackground && this.internalState.isBackground()) {
                startBackgroundTimerI();
            }
            writeActivityStateI();
        }
    }

    private void launchEventResponseTasksI(final EventResponseData eventResponseData) {
        updateAdidI(eventResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (eventResponseData.success && this.adjustConfig.onEventTrackingSucceededListener != null) {
            this.logger.debug("Launching success event tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onEventTrackingSucceededListener.onFinishedEventTrackingSucceeded(eventResponseData.getSuccessResponseData());
                }
            });
        } else if (!eventResponseData.success && this.adjustConfig.onEventTrackingFailedListener != null) {
            this.logger.debug("Launching failed event tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onEventTrackingFailedListener.onFinishedEventTrackingFailed(eventResponseData.getFailureResponseData());
                }
            });
        }
    }

    private void launchSessionResponseTasksI(SessionResponseData sessionResponseData) {
        updateAdidI(sessionResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttributionI(sessionResponseData.attribution)) {
            launchAttributionListenerI(handler);
        }
        launchSessionResponseListenerI(sessionResponseData, handler);
        this.internalState.sessionResponseProcessed = true;
    }

    private void launchSessionResponseListenerI(final SessionResponseData sessionResponseData, Handler handler) {
        if (sessionResponseData.success && this.adjustConfig.onSessionTrackingSucceededListener != null) {
            this.logger.debug("Launching success session tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onSessionTrackingSucceededListener.onFinishedSessionTrackingSucceeded(sessionResponseData.getSuccessResponseData());
                }
            });
        } else if (!sessionResponseData.success && this.adjustConfig.onSessionTrackingFailedListener != null) {
            this.logger.debug("Launching failed session tracking listener", new Object[0]);
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onSessionTrackingFailedListener.onFinishedSessionTrackingFailed(sessionResponseData.getFailureResponseData());
                }
            });
        }
    }

    private void launchAttributionResponseTasksI(AttributionResponseData attributionResponseData) {
        updateAdidI(attributionResponseData.adid);
        Handler handler = new Handler(this.adjustConfig.context.getMainLooper());
        if (updateAttributionI(attributionResponseData.attribution)) {
            launchAttributionListenerI(handler);
        }
        prepareDeeplinkI(attributionResponseData.deeplink, handler);
    }

    private void launchAttributionListenerI(Handler handler) {
        if (this.adjustConfig.onAttributionChangedListener != null) {
            handler.post(new Runnable() {
                public void run() {
                    ActivityHandler.this.adjustConfig.onAttributionChangedListener.onAttributionChanged(ActivityHandler.this.attribution);
                }
            });
        }
    }

    private void prepareDeeplinkI(final Uri deeplink, Handler handler) {
        if (deeplink != null) {
            this.logger.info("Deferred deeplink received (%s)", deeplink);
            final Intent deeplinkIntent = createDeeplinkIntentI(deeplink);
            handler.post(new Runnable() {
                public void run() {
                    boolean toLaunchDeeplink = true;
                    if (ActivityHandler.this.adjustConfig.onDeeplinkResponseListener != null) {
                        toLaunchDeeplink = ActivityHandler.this.adjustConfig.onDeeplinkResponseListener.launchReceivedDeeplink(deeplink);
                    }
                    if (toLaunchDeeplink) {
                        ActivityHandler.this.launchDeeplinkMain(deeplinkIntent, deeplink);
                    }
                }
            });
        }
    }

    private Intent createDeeplinkIntentI(Uri deeplink) {
        Intent mapIntent;
        if (this.adjustConfig.deepLinkComponent == null) {
            mapIntent = new Intent("android.intent.action.VIEW", deeplink);
        } else {
            mapIntent = new Intent("android.intent.action.VIEW", deeplink, this.adjustConfig.context, this.adjustConfig.deepLinkComponent);
        }
        mapIntent.setFlags(268435456);
        mapIntent.setPackage(this.adjustConfig.context.getPackageName());
        return mapIntent;
    }

    private void launchDeeplinkMain(Intent deeplinkIntent, Uri deeplink) {
        boolean isIntentSafe;
        if (this.adjustConfig.context.getPackageManager().queryIntentActivities(deeplinkIntent, 0).size() > 0) {
            isIntentSafe = true;
        } else {
            isIntentSafe = false;
        }
        if (isIntentSafe) {
            this.logger.info("Open deferred deep link (%s)", deeplink);
            this.adjustConfig.context.startActivity(deeplinkIntent);
            return;
        }
        this.logger.error("Unable to open deferred deep link (%s)", deeplink);
    }

    private void sendReferrerI(String referrer, long clickTime) {
        if (referrer != null && referrer.length() != 0) {
            this.logger.verbose("Referrer to parse (%s)", referrer);
            UrlQuerySanitizer querySanitizer = new UrlQuerySanitizer();
            querySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
            querySanitizer.setAllowUnregisteredParamaters(true);
            querySanitizer.parseQuery(referrer);
            PackageBuilder clickPackageBuilder = queryStringClickPackageBuilderI(querySanitizer.getParameterList());
            if (clickPackageBuilder != null) {
                clickPackageBuilder.referrer = referrer;
                clickPackageBuilder.clickTime = clickTime;
                this.sdkClickHandler.sendSdkClick(clickPackageBuilder.buildClickPackage(Constants.REFTAG));
            }
        }
    }

    private void readOpenUrlI(Uri url, long clickTime) {
        if (url != null) {
            String urlString = url.toString();
            this.logger.verbose("Url to parse (%s)", url);
            UrlQuerySanitizer querySanitizer = new UrlQuerySanitizer();
            querySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
            querySanitizer.setAllowUnregisteredParamaters(true);
            querySanitizer.parseUrl(urlString);
            PackageBuilder clickPackageBuilder = queryStringClickPackageBuilderI(querySanitizer.getParameterList());
            if (clickPackageBuilder != null) {
                clickPackageBuilder.deeplink = url.toString();
                clickPackageBuilder.clickTime = clickTime;
                this.sdkClickHandler.sendSdkClick(clickPackageBuilder.buildClickPackage(Constants.DEEPLINK));
            }
        }
    }

    private PackageBuilder queryStringClickPackageBuilderI(List<ParameterValuePair> queryList) {
        if (queryList == null) {
            return null;
        }
        Map<String, String> queryStringParameters = new LinkedHashMap();
        AdjustAttribution queryStringAttribution = new AdjustAttribution();
        for (ParameterValuePair parameterValuePair : queryList) {
            readQueryStringI(parameterValuePair.mParameter, parameterValuePair.mValue, queryStringParameters, queryStringAttribution);
        }
        String reftag = (String) queryStringParameters.remove(Constants.REFTAG);
        PackageBuilder builder = new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, System.currentTimeMillis());
        builder.extraParameters = queryStringParameters;
        builder.attribution = queryStringAttribution;
        builder.reftag = reftag;
        return builder;
    }

    private boolean readQueryStringI(String key, String value, Map<String, String> extraParameters, AdjustAttribution queryStringAttribution) {
        if (key == null || value == null || !key.startsWith(ADJUST_PREFIX)) {
            return false;
        }
        String keyWOutPrefix = key.substring(ADJUST_PREFIX.length());
        if (keyWOutPrefix.length() == 0) {
            return false;
        }
        if (!trySetAttributionI(queryStringAttribution, keyWOutPrefix, value)) {
            extraParameters.put(keyWOutPrefix, value);
        }
        return true;
    }

    private boolean trySetAttributionI(AdjustAttribution queryStringAttribution, String key, String value) {
        if (key.equals("tracker")) {
            queryStringAttribution.trackerName = value;
            return true;
        } else if (key.equals("campaign")) {
            queryStringAttribution.campaign = value;
            return true;
        } else if (key.equals("adgroup")) {
            queryStringAttribution.adgroup = value;
            return true;
        } else if (!key.equals("creative")) {
            return false;
        } else {
            queryStringAttribution.creative = value;
            return true;
        }
    }

    private void updateHandlersStatusAndSendI() {
        if (toSendI()) {
            resumeSendingI();
            if (!this.adjustConfig.eventBufferingEnabled) {
                this.packageHandler.sendFirstPackage();
                return;
            }
            return;
        }
        pauseSendingI();
    }

    private void pauseSendingI() {
        this.attributionHandler.pauseSending();
        this.packageHandler.pauseSending();
        if (toSendI(true)) {
            this.sdkClickHandler.resumeSending();
        } else {
            this.sdkClickHandler.pauseSending();
        }
    }

    private void resumeSendingI() {
        this.attributionHandler.resumeSending();
        this.packageHandler.resumeSending();
        this.sdkClickHandler.resumeSending();
    }

    private boolean updateActivityStateI(long now) {
        if (!checkActivityStateI(this.activityState)) {
            return false;
        }
        long lastInterval = now - this.activityState.lastActivity;
        if (lastInterval > SESSION_INTERVAL) {
            return false;
        }
        this.activityState.lastActivity = now;
        if (lastInterval < 0) {
            this.logger.error(TIME_TRAVEL, new Object[0]);
        } else {
            ActivityState activityState = this.activityState;
            activityState.sessionLength += lastInterval;
            activityState = this.activityState;
            activityState.timeSpent += lastInterval;
        }
        return true;
    }

    public static boolean deleteActivityState(Context context) {
        return context.deleteFile(Constants.ACTIVITY_STATE_FILENAME);
    }

    public static boolean deleteAttribution(Context context) {
        return context.deleteFile(Constants.ATTRIBUTION_FILENAME);
    }

    public static boolean deleteSessionCallbackParameters(Context context) {
        return context.deleteFile(Constants.SESSION_CALLBACK_PARAMETERS_FILENAME);
    }

    public static boolean deleteSessionPartnerParameters(Context context) {
        return context.deleteFile(Constants.SESSION_PARTNER_PARAMETERS_FILENAME);
    }

    private void transferSessionPackageI(long now) {
        this.packageHandler.addPackage(new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, now).buildSessionPackage(this.sessionParameters, this.internalState.isDelayStart()));
        this.packageHandler.sendFirstPackage();
    }

    private void startForegroundTimerI() {
        if (isEnabledI()) {
            this.foregroundTimer.start();
        }
    }

    private void stopForegroundTimerI() {
        this.foregroundTimer.suspend();
    }

    private void foregroundTimerFiredI() {
        if (isEnabledI()) {
            if (toSendI()) {
                this.packageHandler.sendFirstPackage();
            }
            if (updateActivityStateI(System.currentTimeMillis())) {
                writeActivityStateI();
                return;
            }
            return;
        }
        stopForegroundTimerI();
    }

    private void startBackgroundTimerI() {
        if (this.backgroundTimer != null && toSendI() && this.backgroundTimer.getFireIn() <= 0) {
            this.backgroundTimer.startIn(BACKGROUND_TIMER_INTERVAL);
        }
    }

    private void stopBackgroundTimerI() {
        if (this.backgroundTimer != null) {
            this.backgroundTimer.cancel();
        }
    }

    private void backgroundTimerFiredI() {
        if (toSendI()) {
            this.packageHandler.sendFirstPackage();
        }
    }

    private void delayStartI() {
        if (!this.internalState.isToStartNow() && !isToUpdatePackagesI()) {
            String delayStartFormatted;
            double delayStartSeconds = this.adjustConfig.delayStart != null ? this.adjustConfig.delayStart.doubleValue() : 0.0d;
            long maxDelayStartMilli = AdjustFactory.getMaxDelayStart();
            long delayStartMilli = (long) (1000.0d * delayStartSeconds);
            if (delayStartMilli > maxDelayStartMilli) {
                double maxDelayStartSeconds = (double) (maxDelayStartMilli / 1000);
                delayStartFormatted = Util.SecondsDisplayFormat.format(delayStartSeconds);
                String maxDelayStartFormatted = Util.SecondsDisplayFormat.format(maxDelayStartSeconds);
                this.logger.warn("Delay start of %s seconds bigger than max allowed value of %s seconds", delayStartFormatted, maxDelayStartFormatted);
                delayStartMilli = maxDelayStartMilli;
                delayStartSeconds = maxDelayStartSeconds;
            }
            delayStartFormatted = Util.SecondsDisplayFormat.format(delayStartSeconds);
            this.logger.info("Waiting %s seconds before starting first session", delayStartFormatted);
            this.delayStartTimer.startIn(delayStartMilli);
            this.internalState.updatePackages = true;
            if (this.activityState != null) {
                this.activityState.updatePackages = true;
                writeActivityStateI();
            }
        }
    }

    private void sendFirstPackagesI() {
        if (this.internalState.isToStartNow()) {
            this.logger.info("Start delay expired or never configured", new Object[0]);
            return;
        }
        updatePackagesI();
        this.internalState.delayStart = false;
        this.delayStartTimer.cancel();
        this.delayStartTimer = null;
        updateHandlersStatusAndSendI();
    }

    private void updatePackagesI() {
        this.packageHandler.updatePackages(this.sessionParameters);
        this.internalState.updatePackages = false;
        if (this.activityState != null) {
            this.activityState.updatePackages = false;
            writeActivityStateI();
        }
    }

    private boolean isToUpdatePackagesI() {
        if (this.activityState != null) {
            return this.activityState.updatePackages;
        }
        return this.internalState.isToUpdatePackages();
    }

    public void addSessionCallbackParameterI(String key, String value) {
        if (Util.isValidParameter(key, "key", "Session Callback") && Util.isValidParameter(value, "value", "Session Callback")) {
            if (this.sessionParameters.callbackParameters == null) {
                this.sessionParameters.callbackParameters = new LinkedHashMap();
            }
            String oldValue = (String) this.sessionParameters.callbackParameters.get(key);
            if (value.equals(oldValue)) {
                this.logger.verbose("Key %s already present with the same value", key);
                return;
            }
            if (oldValue != null) {
                this.logger.warn("Key %s will be overwritten", key);
            }
            this.sessionParameters.callbackParameters.put(key, value);
            writeSessionCallbackParametersI();
        }
    }

    public void addSessionPartnerParameterI(String key, String value) {
        if (Util.isValidParameter(key, "key", "Session Partner") && Util.isValidParameter(value, "value", "Session Partner")) {
            if (this.sessionParameters.partnerParameters == null) {
                this.sessionParameters.partnerParameters = new LinkedHashMap();
            }
            String oldValue = (String) this.sessionParameters.partnerParameters.get(key);
            if (value.equals(oldValue)) {
                this.logger.verbose("Key %s already present with the same value", key);
                return;
            }
            if (oldValue != null) {
                this.logger.warn("Key %s will be overwritten", key);
            }
            this.sessionParameters.partnerParameters.put(key, value);
            writeSessionPartnerParametersI();
        }
    }

    public void removeSessionCallbackParameterI(String key) {
        if (!Util.isValidParameter(key, "key", "Session Callback")) {
            return;
        }
        if (this.sessionParameters.callbackParameters == null) {
            this.logger.warn("Session Callback parameters are not set", new Object[0]);
        } else if (((String) this.sessionParameters.callbackParameters.remove(key)) == null) {
            this.logger.warn("Key %s does not exist", key);
        } else {
            this.logger.debug("Key %s will be removed", key);
            writeSessionCallbackParametersI();
        }
    }

    public void removeSessionPartnerParameterI(String key) {
        if (!Util.isValidParameter(key, "key", "Session Partner")) {
            return;
        }
        if (this.sessionParameters.partnerParameters == null) {
            this.logger.warn("Session Partner parameters are not set", new Object[0]);
        } else if (((String) this.sessionParameters.partnerParameters.remove(key)) == null) {
            this.logger.warn("Key %s does not exist", key);
        } else {
            this.logger.debug("Key %s will be removed", key);
            writeSessionPartnerParametersI();
        }
    }

    public void resetSessionCallbackParametersI() {
        if (this.sessionParameters.callbackParameters == null) {
            this.logger.warn("Session Callback parameters are not set", new Object[0]);
        }
        this.sessionParameters.callbackParameters = null;
        writeSessionCallbackParametersI();
    }

    public void resetSessionPartnerParametersI() {
        if (this.sessionParameters.partnerParameters == null) {
            this.logger.warn("Session Partner parameters are not set", new Object[0]);
        }
        this.sessionParameters.partnerParameters = null;
        writeSessionPartnerParametersI();
    }

    private void setPushTokenI(String token) {
        if (token != null && !token.equals(this.activityState.pushToken)) {
            this.activityState.pushToken = token;
            writeActivityStateI();
            this.packageHandler.addPackage(new PackageBuilder(this.adjustConfig, this.deviceInfo, this.activityState, System.currentTimeMillis()).buildInfoPackage(Constants.PUSH));
            this.packageHandler.sendFirstPackage();
        }
    }

    private void readActivityStateI(Context context) {
        try {
            this.activityState = (ActivityState) Util.readObject(context, Constants.ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME, ActivityState.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", ACTIVITY_STATE_NAME, e.getMessage());
            this.activityState = null;
        }
    }

    private void readAttributionI(Context context) {
        try {
            this.attribution = (AdjustAttribution) Util.readObject(context, Constants.ATTRIBUTION_FILENAME, ATTRIBUTION_NAME, AdjustAttribution.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", ATTRIBUTION_NAME, e.getMessage());
            this.attribution = null;
        }
    }

    private void readSessionCallbackParametersI(Context context) {
        try {
            this.sessionParameters.callbackParameters = (Map) Util.readObject(context, Constants.SESSION_CALLBACK_PARAMETERS_FILENAME, SESSION_CALLBACK_PARAMETERS_NAME, Map.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", SESSION_CALLBACK_PARAMETERS_NAME, e.getMessage());
            this.sessionParameters.callbackParameters = null;
        }
    }

    private void readSessionPartnerParametersI(Context context) {
        try {
            this.sessionParameters.partnerParameters = (Map) Util.readObject(context, Constants.SESSION_PARTNER_PARAMETERS_FILENAME, SESSION_PARTNER_PARAMETERS_NAME, Map.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", SESSION_PARTNER_PARAMETERS_NAME, e.getMessage());
            this.sessionParameters.partnerParameters = null;
        }
    }

    private void writeActivityStateI() {
        writeActivityStateS(null);
    }

    private void writeActivityStateS(Runnable changeActivityState) {
        synchronized (ActivityState.class) {
            if (this.activityState == null) {
                return;
            }
            if (changeActivityState != null) {
                changeActivityState.run();
            }
            Util.writeObject(this.activityState, this.adjustConfig.context, Constants.ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME);
        }
    }

    private void teardownActivityStateS(boolean toDelete) {
        synchronized (ActivityState.class) {
            if (this.activityState == null) {
                return;
            }
            if (!(!toDelete || this.adjustConfig == null || this.adjustConfig.context == null)) {
                deleteActivityState(this.adjustConfig.context);
            }
            this.activityState = null;
        }
    }

    private void writeAttributionI() {
        synchronized (AdjustAttribution.class) {
            if (this.attribution == null) {
                return;
            }
            Util.writeObject(this.attribution, this.adjustConfig.context, Constants.ATTRIBUTION_FILENAME, ATTRIBUTION_NAME);
        }
    }

    private void teardownAttributionS(boolean toDelete) {
        synchronized (AdjustAttribution.class) {
            if (this.attribution == null) {
                return;
            }
            if (!(!toDelete || this.adjustConfig == null || this.adjustConfig.context == null)) {
                deleteAttribution(this.adjustConfig.context);
            }
            this.attribution = null;
        }
    }

    private void writeSessionCallbackParametersI() {
        synchronized (SessionParameters.class) {
            if (this.sessionParameters == null) {
                return;
            }
            Util.writeObject(this.sessionParameters.callbackParameters, this.adjustConfig.context, Constants.SESSION_CALLBACK_PARAMETERS_FILENAME, SESSION_CALLBACK_PARAMETERS_NAME);
        }
    }

    private void writeSessionPartnerParametersI() {
        synchronized (SessionParameters.class) {
            if (this.sessionParameters == null) {
                return;
            }
            Util.writeObject(this.sessionParameters.partnerParameters, this.adjustConfig.context, Constants.SESSION_PARTNER_PARAMETERS_FILENAME, SESSION_PARTNER_PARAMETERS_NAME);
        }
    }

    private void teardownAllSessionParametersS(boolean toDelete) {
        synchronized (SessionParameters.class) {
            if (this.sessionParameters == null) {
                return;
            }
            if (!(!toDelete || this.adjustConfig == null || this.adjustConfig.context == null)) {
                deleteSessionCallbackParameters(this.adjustConfig.context);
                deleteSessionPartnerParameters(this.adjustConfig.context);
            }
            this.sessionParameters = null;
        }
    }

    private boolean checkEventI(AdjustEvent event) {
        if (event == null) {
            this.logger.error("Event missing", new Object[0]);
            return false;
        } else if (event.isValid()) {
            return true;
        } else {
            this.logger.error("Event not initialized correctly", new Object[0]);
            return false;
        }
    }

    private boolean checkOrderIdI(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return true;
        }
        if (this.activityState.findOrderId(orderId)) {
            this.logger.info("Skipping duplicated order ID '%s'", orderId);
            return false;
        }
        this.activityState.addOrderId(orderId);
        this.logger.verbose("Added order ID '%s'", orderId);
        return true;
    }

    private boolean checkActivityStateI(ActivityState activityState) {
        if (activityState != null) {
            return true;
        }
        this.logger.error("Missing activity state", new Object[0]);
        return false;
    }

    private boolean pausedI() {
        return pausedI(false);
    }

    private boolean pausedI(boolean sdkClickHandlerOnly) {
        if (sdkClickHandlerOnly) {
            if (this.internalState.isOffline() || !isEnabledI()) {
                return true;
            }
            return false;
        } else if (this.internalState.isOffline() || !isEnabledI() || this.internalState.isDelayStart()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean toSendI() {
        return toSendI(false);
    }

    private boolean toSendI(boolean sdkClickHandlerOnly) {
        if (pausedI(sdkClickHandlerOnly)) {
            return false;
        }
        if (this.adjustConfig.sendInBackground) {
            return true;
        }
        return this.internalState.isForeground();
    }
}
