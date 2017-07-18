package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.coremedia.iso.boxes.ProtectionSchemeInformationBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SchemeTypeBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.MemoryDataSourceImpl;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.CencSampleEncryptionInformationGroupEntry;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import com.googlecode.mp4parser.util.RangeStartMap;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CencDecryptingTrackImpl extends AbstractTrack {
    RangeStartMap<Integer, SecretKey> indexToKey;
    Track original;
    CencDecryptingSampleList samples;

    public static class CencDecryptingSampleList extends AbstractList<Sample> {
        RangeStartMap<Integer, SecretKey> keys = new RangeStartMap();
        List<Sample> parent;
        List<CencSampleAuxiliaryDataFormat> sencInfo;

        public CencDecryptingSampleList(SecretKey secretKey, List<Sample> parent, List<CencSampleAuxiliaryDataFormat> sencInfo) {
            this.sencInfo = sencInfo;
            this.keys.put(Integer.valueOf(0), (Object) secretKey);
            this.parent = parent;
        }

        public CencDecryptingSampleList(RangeStartMap<Integer, SecretKey> keys, List<Sample> parent, List<CencSampleAuxiliaryDataFormat> sencInfo) {
            this.sencInfo = sencInfo;
            this.keys = keys;
            this.parent = parent;
        }

        Cipher getCipher(SecretKey sk, byte[] iv) {
            byte[] fullIv = new byte[16];
            System.arraycopy(iv, 0, fullIv, 0, iv.length);
            try {
                Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
                cipher.init(2, sk, new IvParameterSpec(fullIv));
                return cipher;
            } catch (InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e2) {
                throw new RuntimeException(e2);
            } catch (NoSuchAlgorithmException e3) {
                throw new RuntimeException(e3);
            } catch (NoSuchPaddingException e4) {
                throw new RuntimeException(e4);
            }
        }

        public Sample get(int index) {
            if (this.keys.get(Integer.valueOf(index)) == null) {
                return (Sample) this.parent.get(index);
            }
            Sample encSample = (Sample) this.parent.get(index);
            ByteBuffer encSampleBuffer = encSample.asByteBuffer();
            encSampleBuffer.rewind();
            ByteBuffer decSampleBuffer = ByteBuffer.allocate(encSampleBuffer.limit());
            CencSampleAuxiliaryDataFormat sencEntry = (CencSampleAuxiliaryDataFormat) this.sencInfo.get(index);
            Cipher cipher = getCipher((SecretKey) this.keys.get(Integer.valueOf(index)), sencEntry.iv);
            try {
                if (sencEntry.pairs == null || sencEntry.pairs.length <= 0) {
                    byte[] fullyEncryptedSample = new byte[encSampleBuffer.limit()];
                    encSampleBuffer.get(fullyEncryptedSample);
                    decSampleBuffer.put(cipher.doFinal(fullyEncryptedSample));
                } else {
                    for (Pair pair : sencEntry.pairs) {
                        int clearBytes = pair.clear();
                        int encrypted = CastUtils.l2i(pair.encrypted());
                        byte[] clears = new byte[clearBytes];
                        encSampleBuffer.get(clears);
                        decSampleBuffer.put(clears);
                        if (encrypted > 0) {
                            byte[] encs = new byte[encrypted];
                            encSampleBuffer.get(encs);
                            decSampleBuffer.put(cipher.update(encs));
                        }
                    }
                    if (encSampleBuffer.remaining() > 0) {
                        System.err.println("Decrypted sample but still data remaining: " + encSample.getSize());
                    }
                    decSampleBuffer.put(cipher.doFinal());
                }
                encSampleBuffer.rewind();
                decSampleBuffer.rewind();
                return new SampleImpl(decSampleBuffer);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e2) {
                throw new RuntimeException(e2);
            }
        }

        public int size() {
            return this.parent.size();
        }
    }

    public CencDecryptingTrackImpl(CencEncyprtedTrack original, SecretKey sk) {
        this(original, Collections.singletonMap(original.getDefaultKeyId(), sk));
    }

    public CencDecryptingTrackImpl(CencEncyprtedTrack original, Map<UUID, SecretKey> keys) {
        super("dec(" + original.getName() + ")");
        this.indexToKey = new RangeStartMap();
        this.original = original;
        if ("cenc".equals(((SchemeTypeBox) Path.getPath(original.getSampleDescriptionBox(), "enc./sinf/schm")).getSchemeType())) {
            List<CencSampleEncryptionInformationGroupEntry> groupEntries = new ArrayList();
            for (Entry<GroupEntry, long[]> groupEntry : original.getSampleGroups().entrySet()) {
                if (groupEntry.getKey() instanceof CencSampleEncryptionInformationGroupEntry) {
                    groupEntries.add((CencSampleEncryptionInformationGroupEntry) groupEntry.getKey());
                } else {
                    getSampleGroups().put((GroupEntry) groupEntry.getKey(), (long[]) groupEntry.getValue());
                }
            }
            int lastSampleGroupDescriptionIndex = -1;
            for (int i = 0; i < original.getSamples().size(); i++) {
                int index = 0;
                for (int j = 0; j < groupEntries.size(); j++) {
                    if (Arrays.binarySearch((long[]) original.getSampleGroups().get((GroupEntry) groupEntries.get(j)), (long) i) >= 0) {
                        index = j + 1;
                    }
                }
                if (lastSampleGroupDescriptionIndex != index) {
                    if (index == 0) {
                        this.indexToKey.put(Integer.valueOf(i), (SecretKey) keys.get(original.getDefaultKeyId()));
                    } else if (((CencSampleEncryptionInformationGroupEntry) groupEntries.get(index - 1)).isEncrypted()) {
                        Object sk = (SecretKey) keys.get(((CencSampleEncryptionInformationGroupEntry) groupEntries.get(index - 1)).getKid());
                        if (sk == null) {
                            throw new RuntimeException("Key " + ((CencSampleEncryptionInformationGroupEntry) groupEntries.get(index - 1)).getKid() + " was not supplied for decryption");
                        }
                        this.indexToKey.put(Integer.valueOf(i), sk);
                    } else {
                        this.indexToKey.put(Integer.valueOf(i), null);
                    }
                    lastSampleGroupDescriptionIndex = index;
                }
            }
            this.samples = new CencDecryptingSampleList(this.indexToKey, original.getSamples(), original.getSampleEncryptionEntries());
            return;
        }
        throw new RuntimeException("You can only use the CencDecryptingTrackImpl with CENC encrypted tracks");
    }

    public void close() throws IOException {
        this.original.close();
    }

    public long[] getSyncSamples() {
        return this.original.getSyncSamples();
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        OriginalFormatBox frma = (OriginalFormatBox) Path.getPath(this.original.getSampleDescriptionBox(), "enc./sinf/frma");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            this.original.getSampleDescriptionBox().getBox(Channels.newChannel(baos));
            SampleDescriptionBox stsd = (SampleDescriptionBox) new IsoFile(new MemoryDataSourceImpl(baos.toByteArray())).getBoxes().get(0);
            if (stsd.getSampleEntry() instanceof AudioSampleEntry) {
                ((AudioSampleEntry) stsd.getSampleEntry()).setType(frma.getDataFormat());
            } else if (stsd.getSampleEntry() instanceof VisualSampleEntry) {
                ((VisualSampleEntry) stsd.getSampleEntry()).setType(frma.getDataFormat());
            } else {
                throw new RuntimeException("I don't know " + stsd.getSampleEntry().getType());
            }
            List<Box> nuBoxes = new LinkedList();
            for (Box box : stsd.getSampleEntry().getBoxes()) {
                if (!box.getType().equals(ProtectionSchemeInformationBox.TYPE)) {
                    nuBoxes.add(box);
                }
            }
            stsd.getSampleEntry().setBoxes(nuBoxes);
            return stsd;
        } catch (IOException e) {
            throw new RuntimeException("Dumping stsd to memory failed");
        }
    }

    public long[] getSampleDurations() {
        return this.original.getSampleDurations();
    }

    public TrackMetaData getTrackMetaData() {
        return this.original.getTrackMetaData();
    }

    public String getHandler() {
        return this.original.getHandler();
    }

    public List<Sample> getSamples() {
        return this.samples;
    }
}
