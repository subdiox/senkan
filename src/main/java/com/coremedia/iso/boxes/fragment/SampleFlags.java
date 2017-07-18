package com.coremedia.iso.boxes.fragment;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import java.nio.ByteBuffer;

public class SampleFlags {
    private byte reserved;
    private int sampleDegradationPriority;
    private byte sampleDependsOn;
    private byte sampleHasRedundancy;
    private byte sampleIsDependedOn;
    private boolean sampleIsDifferenceSample;
    private byte samplePaddingValue;

    public SampleFlags(ByteBuffer bb) {
        boolean z = true;
        long a = IsoTypeReader.readUInt32(bb);
        this.sampleDegradationPriority = (int) (65535 & a);
        a >>= 16;
        this.sampleIsDependedOn = (byte) ((int) ((192 & a) >> 6));
        this.sampleHasRedundancy = (byte) ((int) ((48 & a) >> 4));
        this.samplePaddingValue = (byte) ((int) ((14 & a) >> 1));
        if ((a & 1) != 1) {
            z = false;
        }
        this.sampleIsDifferenceSample = z;
        a >>= 8;
        this.reserved = (byte) ((int) ((252 & a) >> 2));
        this.sampleDependsOn = (byte) ((int) (3 & a));
    }

    public void getContent(ByteBuffer os) {
        IsoTypeWriter.writeUInt32(os, (((long) ((((this.reserved << 2) & 252) | (this.sampleDependsOn & 3)) << 24)) | ((long) (((this.sampleIsDifferenceSample ? 1 : 0) | (((this.samplePaddingValue << 1) & 14) | (((this.sampleIsDependedOn << 6) & 192) | ((this.sampleHasRedundancy << 4) & 48)))) << 16))) | ((long) (this.sampleDegradationPriority & 65535)));
    }

    public int getReserved() {
        return this.reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = (byte) reserved;
    }

    public int getSampleDependsOn() {
        return this.sampleDependsOn;
    }

    public void setSampleDependsOn(int sampleDependsOn) {
        this.sampleDependsOn = (byte) sampleDependsOn;
    }

    public int getSampleIsDependedOn() {
        return this.sampleIsDependedOn;
    }

    public void setSampleIsDependedOn(int sampleIsDependedOn) {
        this.sampleIsDependedOn = (byte) sampleIsDependedOn;
    }

    public int getSampleHasRedundancy() {
        return this.sampleHasRedundancy;
    }

    public void setSampleHasRedundancy(int sampleHasRedundancy) {
        this.sampleHasRedundancy = (byte) sampleHasRedundancy;
    }

    public int getSamplePaddingValue() {
        return this.samplePaddingValue;
    }

    public void setSamplePaddingValue(int samplePaddingValue) {
        this.samplePaddingValue = (byte) samplePaddingValue;
    }

    public boolean isSampleIsDifferenceSample() {
        return this.sampleIsDifferenceSample;
    }

    public void setSampleIsDifferenceSample(boolean sampleIsDifferenceSample) {
        this.sampleIsDifferenceSample = sampleIsDifferenceSample;
    }

    public int getSampleDegradationPriority() {
        return this.sampleDegradationPriority;
    }

    public void setSampleDegradationPriority(int sampleDegradationPriority) {
        this.sampleDegradationPriority = sampleDegradationPriority;
    }

    public String toString() {
        return "SampleFlags{reserved=" + this.reserved + ", sampleDependsOn=" + this.sampleDependsOn + ", sampleHasRedundancy=" + this.sampleHasRedundancy + ", samplePaddingValue=" + this.samplePaddingValue + ", sampleIsDifferenceSample=" + this.sampleIsDifferenceSample + ", sampleDegradationPriority=" + this.sampleDegradationPriority + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SampleFlags that = (SampleFlags) o;
        if (this.reserved != that.reserved) {
            return false;
        }
        if (this.sampleDegradationPriority != that.sampleDegradationPriority) {
            return false;
        }
        if (this.sampleDependsOn != that.sampleDependsOn) {
            return false;
        }
        if (this.sampleHasRedundancy != that.sampleHasRedundancy) {
            return false;
        }
        if (this.sampleIsDependedOn != that.sampleIsDependedOn) {
            return false;
        }
        if (this.sampleIsDifferenceSample != that.sampleIsDifferenceSample) {
            return false;
        }
        if (this.samplePaddingValue != that.samplePaddingValue) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((((this.reserved * 31) + this.sampleDependsOn) * 31) + this.sampleIsDependedOn) * 31) + this.sampleHasRedundancy) * 31) + this.samplePaddingValue) * 31) + (this.sampleIsDifferenceSample ? 1 : 0)) * 31) + this.sampleDegradationPriority;
    }
}
