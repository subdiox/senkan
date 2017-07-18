package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.UserBox;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyBoxParserImpl extends AbstractBoxParser {
    private static final String DEFAULT_PROTERTIES = "hint=com.coremedia.iso.boxes.TrackReferenceTypeBox(type)\ncdsc=com.coremedia.iso.boxes.TrackReferenceTypeBox(type)\nmeta-ilst=com.coremedia.iso.boxes.apple.AppleItemListBox\nrmra=com.coremedia.iso.boxes.apple.AppleReferenceMovieBox\nrmda=com.coremedia.iso.boxes.apple.AppleReferenceMovieDescriptorBox\nrmdr=com.coremedia.iso.boxes.apple.AppleDataRateBox\nrdrf=com.coremedia.iso.boxes.apple.AppleDataReferenceBox\n\nwave=com.coremedia.iso.boxes.apple.AppleWaveBox\n\nudta-ccid=com.coremedia.iso.boxes.odf.OmaDrmContentIdBox\nudta-yrrc=com.coremedia.iso.boxes.RecordingYearBox\nudta-titl=com.coremedia.iso.boxes.TitleBox\nudta-dscp=com.coremedia.iso.boxes.DescriptionBox\nudta-icnu=com.coremedia.iso.boxes.odf.OmaDrmIconUriBox\nudta-infu=com.coremedia.iso.boxes.odf.OmaDrmInfoUrlBox\nudta-albm=com.coremedia.iso.boxes.AlbumBox\nudta-cprt=com.coremedia.iso.boxes.CopyrightBox\nudta-gnre=com.coremedia.iso.boxes.GenreBox\nudta-perf=com.coremedia.iso.boxes.PerformerBox\nudta-auth=com.coremedia.iso.boxes.AuthorBox\nudta-kywd=com.coremedia.iso.boxes.KeywordsBox\nudta-loci=com.coremedia.iso.boxes.threegpp26244.LocationInformationBox\nudta-rtng=com.coremedia.iso.boxes.RatingBox\nudta-clsf=com.coremedia.iso.boxes.ClassificationBox\nudta-cdis=com.coremedia.iso.boxes.vodafone.ContentDistributorIdBox\nudta-albr=com.coremedia.iso.boxes.vodafone.AlbumArtistBox\nudta-cvru=com.coremedia.iso.boxes.odf.OmaDrmCoverUriBox\nudta-lrcu=com.coremedia.iso.boxes.odf.OmaDrmLyricsUriBox\n\n\n\n\ntx3g=com.coremedia.iso.boxes.sampleentry.TextSampleEntry\nstsd-text=com.googlecode.mp4parser.boxes.apple.QuicktimeTextSampleEntry\nenct=com.coremedia.iso.boxes.sampleentry.TextSampleEntry(type)\nsamr=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nsawb=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nmp4a=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\ndrms=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nstsd-alac=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nmp4s=com.coremedia.iso.boxes.sampleentry.MpegSampleEntry(type)\nowma=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nac-3=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\ndac3=com.googlecode.mp4parser.boxes.AC3SpecificBox\nec-3=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\ndec3=com.googlecode.mp4parser.boxes.EC3SpecificBox\nstsd-lpcm=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nstsd-dtsc=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nstsd-dtsh=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nstsd-dtsl=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nddts=com.googlecode.mp4parser.boxes.DTSSpecificBox\nstsd-dtse=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nstsd-mlpa=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\ndmlp=com.googlecode.mp4parser.boxes.MLPSpecificBox\nenca=com.coremedia.iso.boxes.sampleentry.AudioSampleEntry(type)\nencv=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\nmp4v=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\ns263=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\navc1=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\navc3=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\nhev1=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\nhvc1=com.coremedia.iso.boxes.sampleentry.VisualSampleEntry(type)\novc1=com.coremedia.iso.boxes.sampleentry.Ovc1VisualSampleEntryImpl\nstpp=com.coremedia.iso.boxes.sampleentry.XMLSubtitleSampleEntry\navcC=com.mp4parser.iso14496.part15.AvcConfigurationBox\nhvcC=com.mp4parser.iso14496.part15.HevcConfigurationBox\nalac=com.coremedia.iso.boxes.apple.AppleLosslessSpecificBox\nbtrt=com.mp4parser.iso14496.part12.BitRateBox\nftyp=com.coremedia.iso.boxes.FileTypeBox\nmdat=com.coremedia.iso.boxes.mdat.MediaDataBox\nmoov=com.coremedia.iso.boxes.MovieBox\nmvhd=com.coremedia.iso.boxes.MovieHeaderBox\ntrak=com.coremedia.iso.boxes.TrackBox\ntkhd=com.coremedia.iso.boxes.TrackHeaderBox\nedts=com.coremedia.iso.boxes.EditBox\nelst=com.coremedia.iso.boxes.EditListBox\nmdia=com.coremedia.iso.boxes.MediaBox\nmdhd=com.coremedia.iso.boxes.MediaHeaderBox\nhdlr=com.coremedia.iso.boxes.HandlerBox\nminf=com.coremedia.iso.boxes.MediaInformationBox\nvmhd=com.coremedia.iso.boxes.VideoMediaHeaderBox\nsmhd=com.coremedia.iso.boxes.SoundMediaHeaderBox\nsthd=com.coremedia.iso.boxes.SubtitleMediaHeaderBox\nhmhd=com.coremedia.iso.boxes.HintMediaHeaderBox\ndinf=com.coremedia.iso.boxes.DataInformationBox\ndref=com.coremedia.iso.boxes.DataReferenceBox\nurl\\ =com.coremedia.iso.boxes.DataEntryUrlBox\nurn\\ =com.coremedia.iso.boxes.DataEntryUrnBox\nstbl=com.coremedia.iso.boxes.SampleTableBox\nctts=com.coremedia.iso.boxes.CompositionTimeToSample\nstsd=com.coremedia.iso.boxes.SampleDescriptionBox\nstts=com.coremedia.iso.boxes.TimeToSampleBox\nstss=com.coremedia.iso.boxes.SyncSampleBox\nstsc=com.coremedia.iso.boxes.SampleToChunkBox\nstsz=com.coremedia.iso.boxes.SampleSizeBox\nstco=com.coremedia.iso.boxes.StaticChunkOffsetBox\nsubs=com.coremedia.iso.boxes.SubSampleInformationBox\nudta=com.coremedia.iso.boxes.UserDataBox\nskip=com.coremedia.iso.boxes.FreeSpaceBox\ntref=com.coremedia.iso.boxes.TrackReferenceBox\niloc=com.coremedia.iso.boxes.ItemLocationBox\nidat=com.coremedia.iso.boxes.ItemDataBox\n\ndamr=com.coremedia.iso.boxes.sampleentry.AmrSpecificBox\nmeta=com.coremedia.iso.boxes.MetaBox\nipro=com.coremedia.iso.boxes.ItemProtectionBox\nsinf=com.coremedia.iso.boxes.ProtectionSchemeInformationBox\nfrma=com.coremedia.iso.boxes.OriginalFormatBox\nschi=com.coremedia.iso.boxes.SchemeInformationBox\nodkm=com.coremedia.iso.boxes.odf.OmaDrmKeyManagenentSystemBox\nodaf=com.coremedia.iso.boxes.OmaDrmAccessUnitFormatBox\nschm=com.coremedia.iso.boxes.SchemeTypeBox\nuuid=com.coremedia.iso.boxes.UserBox(userType)\nfree=com.coremedia.iso.boxes.FreeBox\nstyp=com.coremedia.iso.boxes.fragment.SegmentTypeBox\nmvex=com.coremedia.iso.boxes.fragment.MovieExtendsBox\nmehd=com.coremedia.iso.boxes.fragment.MovieExtendsHeaderBox\ntrex=com.coremedia.iso.boxes.fragment.TrackExtendsBox\n\nmoof=com.coremedia.iso.boxes.fragment.MovieFragmentBox\nmfhd=com.coremedia.iso.boxes.fragment.MovieFragmentHeaderBox\ntraf=com.coremedia.iso.boxes.fragment.TrackFragmentBox\ntfhd=com.coremedia.iso.boxes.fragment.TrackFragmentHeaderBox\ntrun=com.coremedia.iso.boxes.fragment.TrackRunBox\nsdtp=com.coremedia.iso.boxes.SampleDependencyTypeBox\nmfra=com.coremedia.iso.boxes.fragment.MovieFragmentRandomAccessBox\ntfra=com.coremedia.iso.boxes.fragment.TrackFragmentRandomAccessBox\nmfro=com.coremedia.iso.boxes.fragment.MovieFragmentRandomAccessOffsetBox\ntfdt=com.coremedia.iso.boxes.fragment.TrackFragmentBaseMediaDecodeTimeBox\nnmhd=com.coremedia.iso.boxes.NullMediaHeaderBox\ngmhd=com.googlecode.mp4parser.boxes.apple.GenericMediaHeaderAtom\ngmhd-text=com.googlecode.mp4parser.boxes.apple.GenericMediaHeaderTextAtom\ngmin=com.googlecode.mp4parser.boxes.apple.BaseMediaInfoAtom\ncslg=com.coremedia.iso.boxes.CompositionShiftLeastGreatestAtom\npdin=com.coremedia.iso.boxes.ProgressiveDownloadInformationBox\nbloc=com.googlecode.mp4parser.boxes.dece.BaseLocationBox\nftab=com.googlecode.mp4parser.boxes.threegpp26245.FontTableBox\nco64=com.coremedia.iso.boxes.ChunkOffset64BitBox\nxml\\ =com.coremedia.iso.boxes.XmlBox\navcn=com.googlecode.mp4parser.boxes.basemediaformat.AvcNalUnitStorageBox\nainf=com.googlecode.mp4parser.boxes.dece.AssetInformationBox\npssh=com.mp4parser.iso23001.part7.ProtectionSystemSpecificHeaderBox\ntrik=com.coremedia.iso.boxes.dece.TrickPlayBox\nuuid[A2394F525A9B4F14A2446C427C648DF4]=com.googlecode.mp4parser.boxes.piff.PiffSampleEncryptionBox\nuuid[8974DBCE7BE74C5184F97148F9882554]=com.googlecode.mp4parser.boxes.piff.PiffTrackEncryptionBox\nuuid[D4807EF2CA3946958E5426CB9E46A79F]=com.googlecode.mp4parser.boxes.piff.TfrfBox\nuuid[6D1D9B0542D544E680E2141DAFF757B2]=com.googlecode.mp4parser.boxes.piff.TfxdBox\nuuid[D08A4F1810F34A82B6C832D8ABA183D3]=com.googlecode.mp4parser.boxes.piff.UuidBasedProtectionSystemSpecificHeaderBox\nsenc=com.googlecode.mp4parser.boxes.dece.SampleEncryptionBox\ntenc=com.mp4parser.iso23001.part7.TrackEncryptionBox\namf0=com.googlecode.mp4parser.boxes.adobe.ActionMessageFormat0SampleEntryBox\n\n#iods=com.googlecode.mp4parser.boxes.mp4.ObjectDescriptorBox\nesds=com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox\n\ntmcd=com.googlecode.mp4parser.boxes.apple.TimeCodeBox\nsidx=com.googlecode.mp4parser.boxes.threegpp26244.SegmentIndexBox\n\nsbgp=com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleToGroupBox\nsgpd=com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox\n\ntapt=com.googlecode.mp4parser.boxes.apple.TrackApertureModeDimensionAtom\nclef=com.googlecode.mp4parser.boxes.apple.CleanApertureAtom\nprof=com.googlecode.mp4parser.boxes.apple.TrackProductionApertureDimensionsAtom\nenof=com.googlecode.mp4parser.boxes.apple.TrackEncodedPixelsDimensionsAtom\npasp=com.googlecode.mp4parser.boxes.apple.PixelAspectRationAtom\nload=com.googlecode.mp4parser.boxes.apple.TrackLoadSettingsAtom\n\n\ndefault=com.coremedia.iso.boxes.UnknownBox(type)\n\n\n\n#stsd-rtp\\ =com.coremedia.iso.boxes.rtp.RtpHintSampleEntry(type)\n#udta-hnti=com.coremedia.iso.boxes.rtp.HintInformationBox\n#udta-hinf=com.coremedia.iso.boxes.rtp.HintStatisticsBox\n#hnti-sdp\\ =com.coremedia.iso.boxes.rtp.RtpTrackSdpHintInformationBox\n#hnti-rtp\\ =com.coremedia.iso.boxes.rtp.RtpMovieHintInformationBox\n#hinf-pmax=com.coremedia.iso.boxes.rtp.LargestHintPacketBox\n#hinf-payt=com.coremedia.iso.boxes.rtp.PayloadTypeBox\n#hinf-tmin=com.coremedia.iso.boxes.rtp.SmallestRelativeTransmissionTimeBox\n#hinf-tmax=com.coremedia.iso.boxes.rtp.LargestRelativeTransmissionTimeBox\n#hinf-maxr=com.coremedia.iso.boxes.rtp.MaximumDataRateBox\n#hinf-dmax=com.coremedia.iso.boxes.rtp.LargestHintPacketDurationBox\n#hinf-hnti=com.coremedia.iso.boxes.rtp.HintInformationBox\n#hinf-tims=com.coremedia.iso.boxes.rtp.TimeScaleEntry\n\n#hinf-nump=com.coremedia.iso.boxes.rtp.HintPacketsSentBox(type)\n#hinf-npck=com.coremedia.iso.boxes.rtp.HintPacketsSentBox(type)\n\n#hinf-trpy=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#hinf-totl=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#hinf-tpyl=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#hinf-tpay=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#hinf-dmed=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#hinf-dimm=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#hinf-drep=com.coremedia.iso.boxes.rtp.HintStatisticBoxes(type)\n#tims=com.coremedia.iso.boxes.rtp.TimeScaleEntry\n\n#odrm=com.coremedia.iso.boxes.odf.OmaDrmContainerBox\n#mdri=com.coremedia.iso.boxes.odf.MutableDrmInformationBox\n#odtt=com.coremedia.iso.boxes.odf.OmaDrmTransactionTrackingBox\n#odrb=com.coremedia.iso.boxes.odf.OmaDrmRightsObjectBox\n#odhe=com.coremedia.iso.boxes.odf.OmaDrmDiscreteHeadersBox\n#odda=com.coremedia.iso.boxes.odf.OmaDrmContentObjectBox\n#ohdr=com.coremedia.iso.boxes.odf.OmaDrmCommonHeadersBox\n#grpi=com.coremedia.iso.boxes.odf.OmaDrmGroupIdBox\n\\u00A9nam=com.googlecode.mp4parser.boxes.apple.AppleNameBox\n\\u00A9ART=com.googlecode.mp4parser.boxes.apple.AppleArtistBox\naART=com.googlecode.mp4parser.boxes.apple.AppleArtist2Box\n\\u00A9alb=com.googlecode.mp4parser.boxes.apple.AppleAlbumBox\n\\u00A9gen=com.googlecode.mp4parser.boxes.apple.AppleGenreBox\ngnre=com.googlecode.mp4parser.boxes.apple.AppleGenreIDBox\n#\\u00A9day=com.googlecode.mp4parser.boxes.apple.AppleRecordingYearBox\n\\u00A9day=com.googlecode.mp4parser.boxes.apple.AppleRecordingYear2Box\ntrkn=com.googlecode.mp4parser.boxes.apple.AppleTrackNumberBox\ncpil=com.googlecode.mp4parser.boxes.apple.AppleCompilationBox\npgap=com.googlecode.mp4parser.boxes.apple.AppleGaplessPlaybackBox\ndisk=com.googlecode.mp4parser.boxes.apple.AppleDiskNumberBox\napID=com.googlecode.mp4parser.boxes.apple.AppleAppleIdBox\ncprt=com.googlecode.mp4parser.boxes.apple.AppleCopyrightBox\natID=com.googlecode.mp4parser.boxes.apple.Apple_atIDBox\ngeID=com.googlecode.mp4parser.boxes.apple.Apple_geIDBox\nsfID=com.googlecode.mp4parser.boxes.apple.AppleCountryTypeBoxBox\ndesc=com.googlecode.mp4parser.boxes.apple.AppleDescriptionBox\ntvnn=com.googlecode.mp4parser.boxes.apple.AppleTVNetworkBox\ntvsh=com.googlecode.mp4parser.boxes.apple.AppleTVShowBox\ntven=com.googlecode.mp4parser.boxes.apple.AppleTVEpisodeNumberBox\ntvsn=com.googlecode.mp4parser.boxes.apple.AppleTVSeasonBox\ntves=com.googlecode.mp4parser.boxes.apple.AppleTVEpisodeBox\nxid\\ =com.googlecode.mp4parser.boxes.apple.Apple_xid_Box\nflvr=com.googlecode.mp4parser.boxes.apple.Apple_flvr_Box\nsdes=com.googlecode.mp4parser.boxes.apple.AppleShortDescriptionBox\nldes=com.googlecode.mp4parser.boxes.apple.AppleLongDescriptionBox\nsoal=com.googlecode.mp4parser.boxes.apple.AppleSortAlbumBox\npurd=com.googlecode.mp4parser.boxes.apple.ApplePurchaseDateBox\nstik=com.googlecode.mp4parser.boxes.apple.AppleMediaTypeBox\n\n\n#added by Tobias Bley / UltraMixer (04/25/2014)\n\\u00A9cmt=com.googlecode.mp4parser.boxes.apple.AppleCommentBox\ntmpo=com.googlecode.mp4parser.boxes.apple.AppleTempoBox\n\\u00A9too=com.googlecode.mp4parser.boxes.apple.AppleEncoderBox\n\\u00A9wrt=com.googlecode.mp4parser.boxes.apple.AppleTrackAuthorBox\n\\u00A9grp=com.googlecode.mp4parser.boxes.apple.AppleGroupingBox\ncovr=com.googlecode.mp4parser.boxes.apple.AppleCoverBox\n\\u00A9lyr=com.googlecode.mp4parser.boxes.apple.AppleLyricsBox\ncinf=com.googlecode.mp4parser.boxes.dece.ContentInformationBox\ntibr=com.mp4parser.iso14496.part15.TierBitRateBox\ntiri=com.mp4parser.iso14496.part15.TierInfoBox\nsvpr=com.mp4parser.iso14496.part15.PriotityRangeBox\nemsg=com.mp4parser.iso23009.part1.EventMessageBox\nsaio=com.mp4parser.iso14496.part12.SampleAuxiliaryInformationOffsetsBox\nsaiz=com.mp4parser.iso14496.part12.SampleAuxiliaryInformationSizesBox\n";
    static String[] EMPTY_STRING_ARRAY = new String[0];
    StringBuilder buildLookupStrings = new StringBuilder();
    String clazzName;
    Pattern constuctorPattern = Pattern.compile("(.*)\\((.*?)\\)");
    Properties mapping;
    String[] param;

    public PropertyBoxParserImpl(String... customProperties) {
        StringReader is = new StringReader(DEFAULT_PROTERTIES);
        InputStream customIS;
        try {
            this.mapping = new Properties();
            this.mapping.load(is);
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            Enumeration<URL> enumeration = cl.getResources("isoparser-custom.properties");
            while (enumeration.hasMoreElements()) {
                customIS = ((URL) enumeration.nextElement()).openStream();
                this.mapping.load(customIS);
                customIS.close();
            }
            for (String customProperty : customProperties) {
                this.mapping.load(getClass().getResourceAsStream(customProperty));
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public PropertyBoxParserImpl(Properties mapping) {
        this.mapping = mapping;
    }

    public Box createBox(String type, byte[] userType, String parent) {
        invoke(type, userType, parent);
        try {
            Class<Box> clazz = Class.forName(this.clazzName);
            if (this.param.length <= 0) {
                return (Box) clazz.newInstance();
            }
            Class[] constructorArgsClazz = new Class[this.param.length];
            Object[] constructorArgs = new Object[this.param.length];
            for (int i = 0; i < this.param.length; i++) {
                if ("userType".equals(this.param[i])) {
                    constructorArgs[i] = userType;
                    constructorArgsClazz[i] = byte[].class;
                } else if ("type".equals(this.param[i])) {
                    constructorArgs[i] = type;
                    constructorArgsClazz[i] = String.class;
                } else if ("parent".equals(this.param[i])) {
                    constructorArgs[i] = parent;
                    constructorArgsClazz[i] = String.class;
                } else {
                    throw new InternalError("No such param: " + this.param[i]);
                }
            }
            return (Box) clazz.getConstructor(constructorArgsClazz).newInstance(constructorArgs);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException(e3);
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        } catch (NoSuchMethodException e5) {
            throw new RuntimeException(e5);
        }
    }

    public void invoke(String type, byte[] userType, String parent) {
        String constructor;
        if (userType == null) {
            constructor = this.mapping.getProperty(type);
            if (constructor == null) {
                String lookup = this.buildLookupStrings.append(parent).append('-').append(type).toString();
                this.buildLookupStrings.setLength(0);
                constructor = this.mapping.getProperty(lookup);
            }
        } else if (UserBox.TYPE.equals(type)) {
            constructor = this.mapping.getProperty("uuid[" + Hex.encodeHex(userType).toUpperCase() + "]");
            if (constructor == null) {
                constructor = this.mapping.getProperty(new StringBuilder(String.valueOf(parent)).append("-uuid[").append(Hex.encodeHex(userType).toUpperCase()).append("]").toString());
            }
            if (constructor == null) {
                constructor = this.mapping.getProperty(UserBox.TYPE);
            }
        } else {
            throw new RuntimeException("we have a userType but no uuid box type. Something's wrong");
        }
        if (constructor == null) {
            constructor = this.mapping.getProperty("default");
        }
        if (constructor == null) {
            throw new RuntimeException("No box object found for " + type);
        } else if (constructor.endsWith(")")) {
            Matcher m = this.constuctorPattern.matcher(constructor);
            if (m.matches()) {
                this.clazzName = m.group(1);
                if (m.group(2).length() == 0) {
                    this.param = EMPTY_STRING_ARRAY;
                    return;
                } else {
                    this.param = m.group(2).length() > 0 ? m.group(2).split(",") : new String[0];
                    return;
                }
            }
            throw new RuntimeException("Cannot work with that constructor: " + constructor);
        } else {
            this.param = EMPTY_STRING_ARRAY;
            this.clazzName = constructor;
        }
    }
}
