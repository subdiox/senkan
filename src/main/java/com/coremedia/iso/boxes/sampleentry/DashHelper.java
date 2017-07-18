package com.coremedia.iso.boxes.sampleentry;

import com.coremedia.iso.Hex;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.boxes.AC3SpecificBox;
import com.googlecode.mp4parser.boxes.DTSSpecificBox;
import com.googlecode.mp4parser.boxes.EC3SpecificBox;
import com.googlecode.mp4parser.boxes.EC3SpecificBox.Entry;
import com.googlecode.mp4parser.boxes.MLPSpecificBox;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.AudioSpecificConfig;
import com.googlecode.mp4parser.util.Path;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import java.util.List;

public final class DashHelper {

    public static class ChannelConfiguration {
        public String schemeIdUri = "";
        public String value = "";
    }

    public static ChannelConfiguration getChannelConfiguration(AudioSampleEntry e) {
        DTSSpecificBox ddts = (DTSSpecificBox) Path.getPath((AbstractContainerBox) e, DTSSpecificBox.TYPE);
        if (ddts != null) {
            return getDTSChannelConfig(e, ddts);
        }
        if (((MLPSpecificBox) Path.getPath((AbstractContainerBox) e, MLPSpecificBox.TYPE)) != null) {
            return null;
        }
        ESDescriptorBox esds = (ESDescriptorBox) Path.getPath((AbstractContainerBox) e, ESDescriptorBox.TYPE);
        if (esds != null) {
            return getAACChannelConfig(e, esds);
        }
        esds = (ESDescriptorBox) Path.getPath((AbstractContainerBox) e, "..../esds");
        if (esds != null) {
            return getAACChannelConfig(e, esds);
        }
        AC3SpecificBox dac3 = (AC3SpecificBox) Path.getPath((AbstractContainerBox) e, AC3SpecificBox.TYPE);
        if (dac3 != null) {
            return getAC3ChannelConfig(e, dac3);
        }
        EC3SpecificBox dec3 = (EC3SpecificBox) Path.getPath((AbstractContainerBox) e, EC3SpecificBox.TYPE);
        if (dec3 != null) {
            return getEC3ChannelConfig(e, dec3);
        }
        return null;
    }

    private static ChannelConfiguration getEC3ChannelConfig(AudioSampleEntry e, EC3SpecificBox dec3) {
        int audioChannelValue = 0;
        for (Entry ec3SpecificBoxEntry : dec3.getEntries()) {
            audioChannelValue |= getDolbyAudioChannelValue(ec3SpecificBoxEntry.acmod, ec3SpecificBoxEntry.lfeon);
        }
        ChannelConfiguration cc = new ChannelConfiguration();
        cc.value = Hex.encodeHex(new byte[]{(byte) ((audioChannelValue >> 8) & 255), (byte) (audioChannelValue & 255)});
        cc.schemeIdUri = "urn:dolby:dash:audio_channel_configuration:2011";
        return cc;
    }

    private static ChannelConfiguration getAC3ChannelConfig(AudioSampleEntry e, AC3SpecificBox dac3) {
        ChannelConfiguration cc = new ChannelConfiguration();
        int audioChannelValue = getDolbyAudioChannelValue(dac3.getAcmod(), dac3.getLfeon());
        cc.value = Hex.encodeHex(new byte[]{(byte) ((audioChannelValue >> 8) & 255), (byte) (audioChannelValue & 255)});
        cc.schemeIdUri = "urn:dolby:dash:audio_channel_configuration:2011";
        return cc;
    }

    private static int getDolbyAudioChannelValue(int acmod, int lfeon) {
        int audioChannelValue;
        switch (acmod) {
            case 0:
                audioChannelValue = 40960;
                break;
            case 1:
                audioChannelValue = 16384;
                break;
            case 2:
                audioChannelValue = 40960;
                break;
            case 3:
                audioChannelValue = 57344;
                break;
            case 4:
                audioChannelValue = 41216;
                break;
            case 5:
                audioChannelValue = 57600;
                break;
            case 6:
                audioChannelValue = 47104;
                break;
            case 7:
                audioChannelValue = 63488;
                break;
            default:
                throw new RuntimeException("Unexpected acmod " + acmod);
        }
        if (lfeon == 1) {
            return audioChannelValue + 1;
        }
        return audioChannelValue;
    }

    private static ChannelConfiguration getAACChannelConfig(AudioSampleEntry e, ESDescriptorBox esds) {
        AudioSpecificConfig audioSpecificConfig = esds.getEsDescriptor().getDecoderConfigDescriptor().getAudioSpecificInfo();
        ChannelConfiguration cc = new ChannelConfiguration();
        cc.schemeIdUri = "urn:mpeg:dash:23003:3:audio_channel_configuration:2011";
        cc.value = String.valueOf(audioSpecificConfig.getChannelConfiguration());
        return cc;
    }

    private static int getNumChannels(DTSSpecificBox dtsSpecificBox) {
        int channelLayout = dtsSpecificBox.getChannelLayout();
        int numChannels = 0;
        if ((channelLayout & 1) == 1) {
            numChannels = 0 + 1;
        }
        if ((channelLayout & 2) == 2) {
            numChannels += 2;
        }
        if ((channelLayout & 4) == 4) {
            numChannels += 2;
        }
        if ((channelLayout & 8) == 8) {
            numChannels++;
        }
        if ((channelLayout & 16) == 16) {
            numChannels++;
        }
        if ((channelLayout & 32) == 32) {
            numChannels += 2;
        }
        if ((channelLayout & 64) == 64) {
            numChannels += 2;
        }
        if ((channelLayout & 128) == 128) {
            numChannels++;
        }
        if ((channelLayout & 256) == 256) {
            numChannels++;
        }
        if ((channelLayout & 512) == 512) {
            numChannels += 2;
        }
        if ((channelLayout & 1024) == 1024) {
            numChannels += 2;
        }
        if ((channelLayout & 2048) == 2048) {
            numChannels += 2;
        }
        if ((channelLayout & 4096) == 4096) {
            numChannels++;
        }
        if ((channelLayout & 8192) == 8192) {
            numChannels += 2;
        }
        if ((channelLayout & 16384) == 16384) {
            numChannels++;
        }
        if ((channelLayout & 32768) == 32768) {
            numChannels += 2;
        }
        if ((channelLayout & 65536) == 65536) {
            numChannels++;
        }
        if ((channelLayout & 131072) == 131072) {
            return numChannels + 2;
        }
        return numChannels;
    }

    private static ChannelConfiguration getDTSChannelConfig(AudioSampleEntry e, DTSSpecificBox ddts) {
        ChannelConfiguration cc = new ChannelConfiguration();
        cc.value = Integer.toString(getNumChannels(ddts));
        cc.schemeIdUri = "urn:dts:dash:audio_channel_configuration:2012";
        return cc;
    }

    public static String getRfc6381Codec(SampleEntry se) {
        String type = se.getType();
        if (type.equals(VisualSampleEntry.TYPE_ENCRYPTED)) {
            type = ((OriginalFormatBox) ((VisualSampleEntry) se).getBoxes(OriginalFormatBox.class, true).get(0)).getDataFormat();
        } else if (type.equals(AudioSampleEntry.TYPE_ENCRYPTED)) {
            type = ((OriginalFormatBox) ((AudioSampleEntry) se).getBoxes(OriginalFormatBox.class, true).get(0)).getDataFormat();
        }
        if (VisualSampleEntry.TYPE3.equals(type)) {
            List<byte[]> spsbytes = ((AvcConfigurationBox) Path.getPath((Box) se, AvcConfigurationBox.TYPE)).getSequenceParameterSets();
            return new StringBuilder(String.valueOf(type)).append(".").append(Hex.encodeHex(new byte[]{((byte[]) spsbytes.get(0))[1], ((byte[]) spsbytes.get(0))[2], ((byte[]) spsbytes.get(0))[3]})).toString().toLowerCase();
        } else if (type.equals(AudioSampleEntry.TYPE3)) {
            ESDescriptorBox esDescriptorBox = (ESDescriptorBox) Path.getPath((Box) se, ESDescriptorBox.TYPE);
            if (esDescriptorBox == null) {
                esDescriptorBox = (ESDescriptorBox) Path.getPath((Box) se, "..../esds");
            }
            AudioSpecificConfig audioSpecificConfig = esDescriptorBox.getEsDescriptor().getDecoderConfigDescriptor().getAudioSpecificInfo();
            if (audioSpecificConfig.getSbrPresentFlag() == 1) {
                return "mp4a.40.5";
            }
            if (audioSpecificConfig.getPsPresentFlag() == 1) {
                return "mp4a.40.29";
            }
            return "mp4a.40.2";
        } else if (type.equals(AudioSampleEntry.TYPE11) || type.equals(AudioSampleEntry.TYPE11) || type.equals(AudioSampleEntry.TYPE13) || type.equals(AudioSampleEntry.TYPE9) || type.equals(AudioSampleEntry.TYPE8) || type.equals(AudioSampleEntry.TYPE10)) {
            return type;
        } else {
            if (type.equals(XMLSubtitleSampleEntry.TYPE)) {
                XMLSubtitleSampleEntry stpp = (XMLSubtitleSampleEntry) se;
                if (stpp.getSchemaLocation().contains("cff-tt-text-ttaf1-dfxp")) {
                    return "cfft";
                }
                if (stpp.getSchemaLocation().contains("cff-tt-image-ttaf1-dfxp")) {
                    return "cffi";
                }
                return XMLSubtitleSampleEntry.TYPE;
            }
            throw new RuntimeException("I don't know how to get codec of type " + se.getType());
        }
    }
}
