package com.adjust.sdk;

import com.coremedia.iso.boxes.UserBox;
import com.rekoo.libs.net.URLCons;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

public class ActivityState implements Serializable, Cloneable {
    private static int ORDER_ID_MAXCOUNT = 10;
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField(UserBox.TYPE, String.class), new ObjectStreamField("enabled", Boolean.TYPE), new ObjectStreamField("askingAttribution", Boolean.TYPE), new ObjectStreamField("eventCount", Integer.TYPE), new ObjectStreamField("sessionCount", Integer.TYPE), new ObjectStreamField("subsessionCount", Integer.TYPE), new ObjectStreamField("sessionLength", Long.TYPE), new ObjectStreamField("timeSpent", Long.TYPE), new ObjectStreamField("lastActivity", Long.TYPE), new ObjectStreamField("lastInterval", Long.TYPE), new ObjectStreamField("updatePackages", Boolean.TYPE), new ObjectStreamField("orderIds", LinkedList.class), new ObjectStreamField("pushToken", String.class), new ObjectStreamField(URLCons.ADID, String.class)};
    private static final long serialVersionUID = 9039439291143138148L;
    protected String adid = null;
    protected boolean askingAttribution = false;
    protected boolean enabled = true;
    protected int eventCount = 0;
    protected long lastActivity = -1;
    protected long lastInterval = -1;
    private transient ILogger logger = AdjustFactory.getLogger();
    protected LinkedList<String> orderIds = null;
    protected String pushToken = null;
    protected int sessionCount = 0;
    protected long sessionLength = -1;
    protected int subsessionCount = -1;
    protected long timeSpent = -1;
    protected boolean updatePackages = false;
    protected String uuid = Util.createUuid();

    protected ActivityState() {
    }

    protected void resetSessionAttributes(long now) {
        this.subsessionCount = 1;
        this.sessionLength = 0;
        this.timeSpent = 0;
        this.lastActivity = now;
        this.lastInterval = -1;
    }

    protected void addOrderId(String orderId) {
        if (this.orderIds == null) {
            this.orderIds = new LinkedList();
        }
        if (this.orderIds.size() >= ORDER_ID_MAXCOUNT) {
            this.orderIds.removeLast();
        }
        this.orderIds.addFirst(orderId);
    }

    protected boolean findOrderId(String orderId) {
        if (this.orderIds == null) {
            return false;
        }
        return this.orderIds.contains(orderId);
    }

    public String toString() {
        return String.format(Locale.US, "ec:%d sc:%d ssc:%d sl:%.1f ts:%.1f la:%s uuid:%s", new Object[]{Integer.valueOf(this.eventCount), Integer.valueOf(this.sessionCount), Integer.valueOf(this.subsessionCount), Double.valueOf(((double) this.sessionLength) / 1000.0d), Double.valueOf(((double) this.timeSpent) / 1000.0d), stamp(this.lastActivity), this.uuid});
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        ActivityState otherActivityState = (ActivityState) other;
        if (!Util.equalString(this.uuid, otherActivityState.uuid)) {
            return false;
        }
        if (!Util.equalBoolean(Boolean.valueOf(this.enabled), Boolean.valueOf(otherActivityState.enabled))) {
            return false;
        }
        if (!Util.equalBoolean(Boolean.valueOf(this.askingAttribution), Boolean.valueOf(otherActivityState.askingAttribution))) {
            return false;
        }
        if (!Util.equalInt(Integer.valueOf(this.eventCount), Integer.valueOf(otherActivityState.eventCount))) {
            return false;
        }
        if (!Util.equalInt(Integer.valueOf(this.sessionCount), Integer.valueOf(otherActivityState.sessionCount))) {
            return false;
        }
        if (!Util.equalInt(Integer.valueOf(this.subsessionCount), Integer.valueOf(otherActivityState.subsessionCount))) {
            return false;
        }
        if (!Util.equalLong(Long.valueOf(this.sessionLength), Long.valueOf(otherActivityState.sessionLength))) {
            return false;
        }
        if (!Util.equalLong(Long.valueOf(this.timeSpent), Long.valueOf(otherActivityState.timeSpent))) {
            return false;
        }
        if (!Util.equalLong(Long.valueOf(this.lastInterval), Long.valueOf(otherActivityState.lastInterval))) {
            return false;
        }
        if (!Util.equalBoolean(Boolean.valueOf(this.updatePackages), Boolean.valueOf(otherActivityState.updatePackages))) {
            return false;
        }
        if (!Util.equalObject(this.orderIds, otherActivityState.orderIds)) {
            return false;
        }
        if (!Util.equalString(this.pushToken, otherActivityState.pushToken)) {
            return false;
        }
        if (Util.equalString(this.adid, otherActivityState.adid)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((Util.hashString(this.uuid) + 629) * 37) + Util.hashBoolean(Boolean.valueOf(this.enabled))) * 37) + Util.hashBoolean(Boolean.valueOf(this.askingAttribution))) * 37) + this.eventCount) * 37) + this.sessionCount) * 37) + this.subsessionCount) * 37) + Util.hashLong(Long.valueOf(this.sessionLength))) * 37) + Util.hashLong(Long.valueOf(this.timeSpent))) * 37) + Util.hashLong(Long.valueOf(this.lastInterval))) * 37) + Util.hashBoolean(Boolean.valueOf(this.updatePackages))) * 37) + Util.hashObject(this.orderIds)) * 37) + Util.hashString(this.pushToken)) * 37) + Util.hashString(this.adid);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        GetField fields = stream.readFields();
        this.eventCount = Util.readIntField(fields, "eventCount", 0);
        this.sessionCount = Util.readIntField(fields, "sessionCount", 0);
        this.subsessionCount = Util.readIntField(fields, "subsessionCount", -1);
        this.sessionLength = Util.readLongField(fields, "sessionLength", -1);
        this.timeSpent = Util.readLongField(fields, "timeSpent", -1);
        this.lastActivity = Util.readLongField(fields, "lastActivity", -1);
        this.lastInterval = Util.readLongField(fields, "lastInterval", -1);
        this.uuid = Util.readStringField(fields, UserBox.TYPE, null);
        this.enabled = Util.readBooleanField(fields, "enabled", true);
        this.askingAttribution = Util.readBooleanField(fields, "askingAttribution", false);
        this.updatePackages = Util.readBooleanField(fields, "updatePackages", false);
        this.orderIds = (LinkedList) Util.readObjectField(fields, "orderIds", null);
        this.pushToken = Util.readStringField(fields, "pushToken", null);
        this.adid = Util.readStringField(fields, URLCons.ADID, null);
        if (this.uuid == null) {
            this.uuid = Util.createUuid();
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    private static String stamp(long dateMillis) {
        Calendar.getInstance().setTimeInMillis(dateMillis);
        return String.format(Locale.US, "%02d:%02d:%02d", new Object[]{Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13)});
    }
}
