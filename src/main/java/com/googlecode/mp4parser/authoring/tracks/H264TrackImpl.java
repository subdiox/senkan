package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.h264.model.PictureParameterSet;
import com.googlecode.mp4parser.h264.model.SeqParameterSet;
import com.googlecode.mp4parser.h264.read.CAVLCReader;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class H264TrackImpl extends AbstractTrack {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions;
    static int BUFFER = 67107840;
    private static final Logger LOG = Logger.getLogger(H264TrackImpl.class.getName());
    List<Entry> ctts;
    private DataSource dataSource;
    private long[] decodingTimes;
    private boolean determineFrameRate = true;
    int frameNrInGop = 0;
    private int frametick;
    private int height;
    private String lang = "eng";
    PictureParameterSet pictureParameterSet = null;
    LinkedList<byte[]> pictureParameterSetList = new LinkedList();
    boolean readSamples = false;
    SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    List<SampleDependencyTypeBox.Entry> sdtp;
    private SEIMessage seiMessage;
    SeqParameterSet seqParameterSet = null;
    LinkedList<byte[]> seqParameterSetList = new LinkedList();
    List<Integer> stss;
    private long timescale;
    TrackMetaData trackMetaData = new TrackMetaData();
    private int width;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions = new int[NALActions.values().length];

        static {
            try {
                $SwitchMap$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions[NALActions.IGNORE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions[NALActions.BUFFER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions[NALActions.STORE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions[NALActions.END.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public class ByteBufferBackedInputStream extends InputStream {
        private final ByteBuffer buf;

        public ByteBufferBackedInputStream(ByteBuffer buf) {
            this.buf = buf.duplicate();
        }

        public int read() throws IOException {
            if (this.buf.hasRemaining()) {
                return this.buf.get() & 255;
            }
            return -1;
        }

        public int read(byte[] bytes, int off, int len) throws IOException {
            if (!this.buf.hasRemaining()) {
                return -1;
            }
            len = Math.min(len, this.buf.remaining());
            this.buf.get(bytes, off, len);
            return len;
        }
    }

    class LookAhead {
        ByteBuffer buffer;
        long bufferStartPos = 0;
        DataSource dataSource;
        int inBufferPos = 0;
        long start;

        public void fillBuffer() throws IOException {
            this.buffer = this.dataSource.map(this.bufferStartPos, Math.min(this.dataSource.size() - this.bufferStartPos, (long) H264TrackImpl.BUFFER));
        }

        LookAhead(DataSource dataSource) throws IOException {
            this.dataSource = dataSource;
            fillBuffer();
        }

        boolean nextThreeEquals001() throws IOException {
            if (this.buffer.limit() - this.inBufferPos >= 3) {
                if (this.buffer.get(this.inBufferPos) == (byte) 0 && this.buffer.get(this.inBufferPos + 1) == (byte) 0 && this.buffer.get(this.inBufferPos + 2) == (byte) 1) {
                    return true;
                }
                return false;
            } else if ((this.bufferStartPos + ((long) this.inBufferPos)) + 3 < this.dataSource.size()) {
                return false;
            } else {
                throw new EOFException();
            }
        }

        boolean nextThreeEquals000or001orEof() throws IOException {
            if (this.buffer.limit() - this.inBufferPos >= 3) {
                if (this.buffer.get(this.inBufferPos) == (byte) 0 && this.buffer.get(this.inBufferPos + 1) == (byte) 0 && (this.buffer.get(this.inBufferPos + 2) == (byte) 0 || this.buffer.get(this.inBufferPos + 2) == (byte) 1)) {
                    return true;
                }
                return false;
            } else if ((this.bufferStartPos + ((long) this.inBufferPos)) + 3 <= this.dataSource.size()) {
                this.bufferStartPos = this.start;
                this.inBufferPos = 0;
                fillBuffer();
                return nextThreeEquals000or001orEof();
            } else if (this.bufferStartPos + ((long) this.inBufferPos) != this.dataSource.size()) {
                return false;
            } else {
                return true;
            }
        }

        void discardByte() {
            this.inBufferPos++;
        }

        void discardNext3AndMarkStart() {
            this.inBufferPos += 3;
            this.start = this.bufferStartPos + ((long) this.inBufferPos);
        }

        public ByteBuffer getNal() {
            if (this.start >= this.bufferStartPos) {
                this.buffer.position((int) (this.start - this.bufferStartPos));
                Buffer sample = this.buffer.slice();
                sample.limit((int) (((long) this.inBufferPos) - (this.start - this.bufferStartPos)));
                return (ByteBuffer) sample;
            }
            throw new RuntimeException("damn! NAL exceeds buffer");
        }
    }

    private enum NALActions {
        IGNORE,
        BUFFER,
        STORE,
        END
    }

    public class SEIMessage {
        boolean clock_timestamp_flag;
        int cnt_dropped_flag;
        int counting_type;
        int cpb_removal_delay;
        int ct_type;
        int discontinuity_flag;
        int dpb_removal_delay;
        int full_timestamp_flag;
        int hours_value;
        int minutes_value;
        int n_frames;
        int nuit_field_based_flag;
        int payloadSize = 0;
        int payloadType = 0;
        int pic_struct;
        boolean removal_delay_flag;
        int seconds_value;
        SeqParameterSet sps;
        int time_offset;
        int time_offset_length;

        public SEIMessage(InputStream is, SeqParameterSet sps) throws IOException {
            this.sps = sps;
            is.read();
            int datasize = is.available();
            int read = 0;
            while (read < datasize) {
                this.payloadType = 0;
                this.payloadSize = 0;
                int last_payload_type_bytes = is.read();
                read++;
                while (last_payload_type_bytes == 255) {
                    this.payloadType += last_payload_type_bytes;
                    last_payload_type_bytes = is.read();
                    read++;
                }
                this.payloadType += last_payload_type_bytes;
                int last_payload_size_bytes = is.read();
                read++;
                while (last_payload_size_bytes == 255) {
                    this.payloadSize += last_payload_size_bytes;
                    last_payload_size_bytes = is.read();
                    read++;
                }
                this.payloadSize += last_payload_size_bytes;
                if (datasize - read < this.payloadSize) {
                    read = datasize;
                } else if (this.payloadType != 1) {
                    for (i = 0; i < this.payloadSize; i++) {
                        is.read();
                        read++;
                    }
                } else if (sps.vuiParams == null || (sps.vuiParams.nalHRDParams == null && sps.vuiParams.vclHRDParams == null && !sps.vuiParams.pic_struct_present_flag)) {
                    for (i = 0; i < this.payloadSize; i++) {
                        is.read();
                        read++;
                    }
                } else {
                    byte[] data = new byte[this.payloadSize];
                    is.read(data);
                    read += this.payloadSize;
                    CAVLCReader reader = new CAVLCReader(new ByteArrayInputStream(data));
                    if (sps.vuiParams.nalHRDParams == null && sps.vuiParams.vclHRDParams == null) {
                        this.removal_delay_flag = false;
                    } else {
                        this.removal_delay_flag = true;
                        this.cpb_removal_delay = reader.readU(sps.vuiParams.nalHRDParams.cpb_removal_delay_length_minus1 + 1, "SEI: cpb_removal_delay");
                        this.dpb_removal_delay = reader.readU(sps.vuiParams.nalHRDParams.dpb_output_delay_length_minus1 + 1, "SEI: dpb_removal_delay");
                    }
                    if (sps.vuiParams.pic_struct_present_flag) {
                        int numClockTS;
                        this.pic_struct = reader.readU(4, "SEI: pic_struct");
                        switch (this.pic_struct) {
                            case 3:
                            case 4:
                            case 7:
                                numClockTS = 2;
                                break;
                            case 5:
                            case 6:
                            case 8:
                                numClockTS = 3;
                                break;
                            default:
                                numClockTS = 1;
                                break;
                        }
                        for (i = 0; i < numClockTS; i++) {
                            this.clock_timestamp_flag = reader.readBool("pic_timing SEI: clock_timestamp_flag[" + i + "]");
                            if (this.clock_timestamp_flag) {
                                this.ct_type = reader.readU(2, "pic_timing SEI: ct_type");
                                this.nuit_field_based_flag = reader.readU(1, "pic_timing SEI: nuit_field_based_flag");
                                this.counting_type = reader.readU(5, "pic_timing SEI: counting_type");
                                this.full_timestamp_flag = reader.readU(1, "pic_timing SEI: full_timestamp_flag");
                                this.discontinuity_flag = reader.readU(1, "pic_timing SEI: discontinuity_flag");
                                this.cnt_dropped_flag = reader.readU(1, "pic_timing SEI: cnt_dropped_flag");
                                this.n_frames = reader.readU(8, "pic_timing SEI: n_frames");
                                if (this.full_timestamp_flag == 1) {
                                    this.seconds_value = reader.readU(6, "pic_timing SEI: seconds_value");
                                    this.minutes_value = reader.readU(6, "pic_timing SEI: minutes_value");
                                    this.hours_value = reader.readU(5, "pic_timing SEI: hours_value");
                                } else if (reader.readBool("pic_timing SEI: seconds_flag")) {
                                    this.seconds_value = reader.readU(6, "pic_timing SEI: seconds_value");
                                    if (reader.readBool("pic_timing SEI: minutes_flag")) {
                                        this.minutes_value = reader.readU(6, "pic_timing SEI: minutes_value");
                                        if (reader.readBool("pic_timing SEI: hours_flag")) {
                                            this.hours_value = reader.readU(5, "pic_timing SEI: hours_value");
                                        }
                                    }
                                }
                                if (sps.vuiParams.nalHRDParams != null) {
                                    this.time_offset_length = sps.vuiParams.nalHRDParams.time_offset_length;
                                } else if (sps.vuiParams.vclHRDParams != null) {
                                    this.time_offset_length = sps.vuiParams.vclHRDParams.time_offset_length;
                                } else {
                                    this.time_offset_length = 24;
                                }
                                this.time_offset = reader.readU(24, "pic_timing SEI: time_offset");
                            }
                        }
                    }
                }
                H264TrackImpl.LOG.fine(toString());
            }
        }

        public String toString() {
            String out = "SEIMessage{payloadType=" + this.payloadType + ", payloadSize=" + this.payloadSize;
            if (this.payloadType == 1) {
                if (!(this.sps.vuiParams.nalHRDParams == null && this.sps.vuiParams.vclHRDParams == null)) {
                    out = new StringBuilder(String.valueOf(out)).append(", cpb_removal_delay=").append(this.cpb_removal_delay).append(", dpb_removal_delay=").append(this.dpb_removal_delay).toString();
                }
                if (this.sps.vuiParams.pic_struct_present_flag) {
                    out = new StringBuilder(String.valueOf(out)).append(", pic_struct=").append(this.pic_struct).toString();
                    if (this.clock_timestamp_flag) {
                        out = new StringBuilder(String.valueOf(out)).append(", ct_type=").append(this.ct_type).append(", nuit_field_based_flag=").append(this.nuit_field_based_flag).append(", counting_type=").append(this.counting_type).append(", full_timestamp_flag=").append(this.full_timestamp_flag).append(", discontinuity_flag=").append(this.discontinuity_flag).append(", cnt_dropped_flag=").append(this.cnt_dropped_flag).append(", n_frames=").append(this.n_frames).append(", seconds_value=").append(this.seconds_value).append(", minutes_value=").append(this.minutes_value).append(", hours_value=").append(this.hours_value).append(", time_offset_length=").append(this.time_offset_length).append(", time_offset=").append(this.time_offset).toString();
                    }
                }
            }
            return new StringBuilder(String.valueOf(out)).append('}').toString();
        }
    }

    public static class SliceHeader {
        public boolean bottom_field_flag = false;
        public int colour_plane_id;
        public int delta_pic_order_cnt_bottom;
        public boolean field_pic_flag = false;
        public int first_mb_in_slice;
        public int frame_num;
        public int idr_pic_id;
        public int pic_order_cnt_lsb;
        public int pic_parameter_set_id;
        public SliceType slice_type;

        public enum SliceType {
            P,
            B,
            I,
            SP,
            SI
        }

        public SliceHeader(InputStream is, SeqParameterSet sps, PictureParameterSet pps, boolean IdrPicFlag) throws IOException {
            is.read();
            CAVLCReader reader = new CAVLCReader(is);
            this.first_mb_in_slice = reader.readUE("SliceHeader: first_mb_in_slice");
            switch (reader.readUE("SliceHeader: slice_type")) {
                case 0:
                case 5:
                    this.slice_type = SliceType.P;
                    break;
                case 1:
                case 6:
                    this.slice_type = SliceType.B;
                    break;
                case 2:
                case 7:
                    this.slice_type = SliceType.I;
                    break;
                case 3:
                case 8:
                    this.slice_type = SliceType.SP;
                    break;
                case 4:
                case 9:
                    this.slice_type = SliceType.SI;
                    break;
            }
            this.pic_parameter_set_id = reader.readUE("SliceHeader: pic_parameter_set_id");
            if (sps.residual_color_transform_flag) {
                this.colour_plane_id = reader.readU(2, "SliceHeader: colour_plane_id");
            }
            this.frame_num = reader.readU(sps.log2_max_frame_num_minus4 + 4, "SliceHeader: frame_num");
            if (!sps.frame_mbs_only_flag) {
                this.field_pic_flag = reader.readBool("SliceHeader: field_pic_flag");
                if (this.field_pic_flag) {
                    this.bottom_field_flag = reader.readBool("SliceHeader: bottom_field_flag");
                }
            }
            if (IdrPicFlag) {
                this.idr_pic_id = reader.readUE("SliceHeader: idr_pic_id");
                if (sps.pic_order_cnt_type == 0) {
                    this.pic_order_cnt_lsb = reader.readU(sps.log2_max_pic_order_cnt_lsb_minus4 + 4, "SliceHeader: pic_order_cnt_lsb");
                    if (pps.pic_order_present_flag && !this.field_pic_flag) {
                        this.delta_pic_order_cnt_bottom = reader.readSE("SliceHeader: delta_pic_order_cnt_bottom");
                    }
                }
            }
        }

        public String toString() {
            return "SliceHeader{first_mb_in_slice=" + this.first_mb_in_slice + ", slice_type=" + this.slice_type + ", pic_parameter_set_id=" + this.pic_parameter_set_id + ", colour_plane_id=" + this.colour_plane_id + ", frame_num=" + this.frame_num + ", field_pic_flag=" + this.field_pic_flag + ", bottom_field_flag=" + this.bottom_field_flag + ", idr_pic_id=" + this.idr_pic_id + ", pic_order_cnt_lsb=" + this.pic_order_cnt_lsb + ", delta_pic_order_cnt_bottom=" + this.delta_pic_order_cnt_bottom + '}';
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions() {
        int[] iArr = $SWITCH_TABLE$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions;
        if (iArr == null) {
            iArr = new int[NALActions.values().length];
            try {
                iArr[NALActions.BUFFER.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[NALActions.END.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[NALActions.IGNORE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[NALActions.STORE.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions = iArr;
        }
        return iArr;
    }

    public H264TrackImpl(DataSource dataSource, String lang, long timescale, int frametick) throws IOException {
        super(dataSource.toString());
        this.lang = lang;
        this.timescale = timescale;
        this.frametick = frametick;
        this.dataSource = dataSource;
        if (timescale > 0 && frametick > 0) {
            this.determineFrameRate = false;
        }
        parse(new LookAhead(dataSource));
    }

    public void close() throws IOException {
        this.dataSource.close();
    }

    public H264TrackImpl(DataSource dataSource, String lang) throws IOException {
        super(dataSource.toString());
        this.lang = lang;
        this.dataSource = dataSource;
        parse(new LookAhead(dataSource));
    }

    public H264TrackImpl(DataSource dataSource) throws IOException {
        super(dataSource.toString());
        this.dataSource = dataSource;
        parse(new LookAhead(dataSource));
    }

    private void parse(LookAhead la) throws IOException {
        this.ctts = new LinkedList();
        this.sdtp = new LinkedList();
        this.stss = new LinkedList();
        this.samples = new LinkedList();
        if (!readSamples(la)) {
            throw new IOException();
        } else if (readVariables()) {
            this.sampleDescriptionBox = new SampleDescriptionBox();
            VisualSampleEntry visualSampleEntry = new VisualSampleEntry(VisualSampleEntry.TYPE3);
            visualSampleEntry.setDataReferenceIndex(1);
            visualSampleEntry.setDepth(24);
            visualSampleEntry.setFrameCount(1);
            visualSampleEntry.setHorizresolution(72.0d);
            visualSampleEntry.setVertresolution(72.0d);
            visualSampleEntry.setWidth(this.width);
            visualSampleEntry.setHeight(this.height);
            visualSampleEntry.setCompressorname("AVC Coding");
            AvcConfigurationBox avcConfigurationBox = new AvcConfigurationBox();
            avcConfigurationBox.setSequenceParameterSets(this.seqParameterSetList);
            avcConfigurationBox.setPictureParameterSets(this.pictureParameterSetList);
            avcConfigurationBox.setAvcLevelIndication(this.seqParameterSet.level_idc);
            avcConfigurationBox.setAvcProfileIndication(this.seqParameterSet.profile_idc);
            avcConfigurationBox.setBitDepthLumaMinus8(this.seqParameterSet.bit_depth_luma_minus8);
            avcConfigurationBox.setBitDepthChromaMinus8(this.seqParameterSet.bit_depth_chroma_minus8);
            avcConfigurationBox.setChromaFormat(this.seqParameterSet.chroma_format_idc.getId());
            avcConfigurationBox.setConfigurationVersion(1);
            avcConfigurationBox.setLengthSizeMinusOne(3);
            avcConfigurationBox.setProfileCompatibility(((byte[]) this.seqParameterSetList.get(0))[1]);
            visualSampleEntry.addBox(avcConfigurationBox);
            this.sampleDescriptionBox.addBox(visualSampleEntry);
            this.trackMetaData.setCreationTime(new Date());
            this.trackMetaData.setModificationTime(new Date());
            this.trackMetaData.setLanguage(this.lang);
            this.trackMetaData.setTimescale(this.timescale);
            this.trackMetaData.setWidth((double) this.width);
            this.trackMetaData.setHeight((double) this.height);
        } else {
            throw new IOException();
        }
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public List<Entry> getCompositionTimeEntries() {
        return this.ctts;
    }

    public long[] getSyncSamples() {
        long[] returns = new long[this.stss.size()];
        for (int i = 0; i < this.stss.size(); i++) {
            returns[i] = (long) ((Integer) this.stss.get(i)).intValue();
        }
        return returns;
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return this.sdtp;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return "vide";
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    private boolean readVariables() {
        this.width = (this.seqParameterSet.pic_width_in_mbs_minus1 + 1) * 16;
        int mult = 2;
        if (this.seqParameterSet.frame_mbs_only_flag) {
            mult = 1;
        }
        this.height = ((this.seqParameterSet.pic_height_in_map_units_minus1 + 1) * 16) * mult;
        if (this.seqParameterSet.frame_cropping_flag) {
            int chromaArrayType = 0;
            if (!this.seqParameterSet.residual_color_transform_flag) {
                chromaArrayType = this.seqParameterSet.chroma_format_idc.getId();
            }
            int cropUnitX = 1;
            int cropUnitY = mult;
            if (chromaArrayType != 0) {
                cropUnitX = this.seqParameterSet.chroma_format_idc.getSubWidth();
                cropUnitY = this.seqParameterSet.chroma_format_idc.getSubHeight() * mult;
            }
            this.width -= (this.seqParameterSet.frame_crop_left_offset + this.seqParameterSet.frame_crop_right_offset) * cropUnitX;
            this.height -= (this.seqParameterSet.frame_crop_top_offset + this.seqParameterSet.frame_crop_bottom_offset) * cropUnitY;
        }
        return true;
    }

    private ByteBuffer findNextNal(LookAhead la) throws IOException {
        while (!la.nextThreeEquals001()) {
            try {
                la.discardByte();
            } catch (EOFException e) {
                return null;
            }
        }
        la.discardNext3AndMarkStart();
        while (!la.nextThreeEquals000or001orEof()) {
            la.discardByte();
        }
        return la.getNal();
    }

    protected Sample createSample(List<? extends ByteBuffer> nals) {
        byte[] sizeInfo = new byte[(nals.size() * 4)];
        ByteBuffer sizeBuf = ByteBuffer.wrap(sizeInfo);
        for (ByteBuffer b : nals) {
            sizeBuf.putInt(b.remaining());
        }
        ByteBuffer[] data = new ByteBuffer[(nals.size() * 2)];
        for (int i = 0; i < nals.size(); i++) {
            data[i * 2] = ByteBuffer.wrap(sizeInfo, i * 4, 4);
            data[(i * 2) + 1] = (ByteBuffer) nals.get(i);
        }
        return new SampleImpl(data);
    }

    private boolean readSamples(LookAhead la) throws IOException {
        if (!this.readSamples) {
            this.readSamples = true;
            List<ByteBuffer> buffered = new ArrayList();
            int frameNr = 0;
            while (true) {
                ByteBuffer nal = findNextNal(la);
                if (nal != null) {
                    int type = nal.get(0);
                    int nal_unit_type = type & 31;
                    switch ($SWITCH_TABLE$com$googlecode$mp4parser$authoring$tracks$H264TrackImpl$NALActions()[handleNALUnit((type >> 5) & 3, nal_unit_type, nal).ordinal()]) {
                        case 1:
                            break;
                        case 2:
                            buffered.add(nal);
                            break;
                        case 3:
                            int stdpValue = 22;
                            frameNr++;
                            buffered.add(nal);
                            boolean IdrPicFlag = false;
                            if (nal_unit_type == 5) {
                                stdpValue = 22 + 16;
                                IdrPicFlag = true;
                            }
                            if (new SliceHeader(cleanBuffer(new ByteBufferBackedInputStream((ByteBuffer) buffered.get(buffered.size() - 1))), this.seqParameterSet, this.pictureParameterSet, IdrPicFlag).slice_type == SliceType.B) {
                                stdpValue += 4;
                            }
                            Sample bb = createSample(buffered);
                            buffered = new ArrayList();
                            this.samples.add(bb);
                            if (nal_unit_type == 5) {
                                this.stss.add(Integer.valueOf(frameNr));
                            }
                            if (this.seiMessage == null || this.seiMessage.n_frames == 0) {
                                this.frameNrInGop = 0;
                            }
                            int offset = 0;
                            if (this.seiMessage != null && this.seiMessage.clock_timestamp_flag) {
                                offset = this.seiMessage.n_frames - this.frameNrInGop;
                            } else if (this.seiMessage != null && this.seiMessage.removal_delay_flag) {
                                offset = this.seiMessage.dpb_removal_delay / 2;
                            }
                            this.ctts.add(new Entry(1, this.frametick * offset));
                            this.sdtp.add(new SampleDependencyTypeBox.Entry(stdpValue));
                            this.frameNrInGop++;
                            break;
                        case 4:
                            return true;
                        default:
                            break;
                    }
                }
                this.decodingTimes = new long[this.samples.size()];
                Arrays.fill(this.decodingTimes, (long) this.frametick);
                return true;
            }
        }
        return true;
    }

    protected InputStream cleanBuffer(InputStream is) {
        return new CleanInputStream(is);
    }

    public long[] getSampleDurations() {
        return this.decodingTimes;
    }

    static byte[] toArray(ByteBuffer buf) {
        buf = buf.duplicate();
        byte[] b = new byte[buf.remaining()];
        buf.get(b, 0, b.length);
        return b;
    }

    private NALActions handleNALUnit(int nal_ref_idc, int nal_unit_type, ByteBuffer data) throws IOException {
        InputStream is;
        switch (nal_unit_type) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return NALActions.STORE;
            case 6:
                this.seiMessage = new SEIMessage(cleanBuffer(new ByteBufferBackedInputStream(data)), this.seqParameterSet);
                return NALActions.BUFFER;
            case 7:
                if (this.seqParameterSet == null) {
                    is = cleanBuffer(new ByteBufferBackedInputStream(data));
                    is.read();
                    this.seqParameterSet = SeqParameterSet.read(is);
                    this.seqParameterSetList.add(toArray(data));
                    configureFramerate();
                }
                return NALActions.IGNORE;
            case 8:
                if (this.pictureParameterSet == null) {
                    is = new ByteBufferBackedInputStream(data);
                    is.read();
                    this.pictureParameterSet = PictureParameterSet.read(is);
                    this.pictureParameterSetList.add(toArray(data));
                }
                return NALActions.IGNORE;
            case 9:
                return NALActions.BUFFER;
            case 10:
            case 11:
                return NALActions.END;
            default:
                System.err.println("Unknown NAL unit type: " + nal_unit_type);
                return NALActions.IGNORE;
        }
    }

    private void configureFramerate() {
        if (!this.determineFrameRate) {
            return;
        }
        if (this.seqParameterSet.vuiParams != null) {
            this.timescale = (long) (this.seqParameterSet.vuiParams.time_scale >> 1);
            this.frametick = this.seqParameterSet.vuiParams.num_units_in_tick;
            if (this.timescale == 0 || this.frametick == 0) {
                System.err.println("Warning: vuiParams contain invalid values: time_scale: " + this.timescale + " and frame_tick: " + this.frametick + ". Setting frame rate to 25fps");
                this.timescale = 90000;
                this.frametick = 3600;
                return;
            }
            return;
        }
        System.err.println("Warning: Can't determine frame rate. Guessing 25 fps");
        this.timescale = 90000;
        this.frametick = 3600;
    }
}
