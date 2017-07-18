package com.googlecode.mp4parser.h264.model;

import org.apache.commons.io.IOUtils;

public class VUIParameters {
    public AspectRatio aspect_ratio;
    public boolean aspect_ratio_info_present_flag;
    public BitstreamRestriction bitstreamRestriction;
    public boolean chroma_loc_info_present_flag;
    public int chroma_sample_loc_type_bottom_field;
    public int chroma_sample_loc_type_top_field;
    public boolean colour_description_present_flag;
    public int colour_primaries;
    public boolean fixed_frame_rate_flag;
    public boolean low_delay_hrd_flag;
    public int matrix_coefficients;
    public HRDParameters nalHRDParams;
    public int num_units_in_tick;
    public boolean overscan_appropriate_flag;
    public boolean overscan_info_present_flag;
    public boolean pic_struct_present_flag;
    public int sar_height;
    public int sar_width;
    public int time_scale;
    public boolean timing_info_present_flag;
    public int transfer_characteristics;
    public HRDParameters vclHRDParams;
    public int video_format;
    public boolean video_full_range_flag;
    public boolean video_signal_type_present_flag;

    public static class BitstreamRestriction {
        public int log2_max_mv_length_horizontal;
        public int log2_max_mv_length_vertical;
        public int max_bits_per_mb_denom;
        public int max_bytes_per_pic_denom;
        public int max_dec_frame_buffering;
        public boolean motion_vectors_over_pic_boundaries_flag;
        public int num_reorder_frames;

        public String toString() {
            StringBuilder sb = new StringBuilder("BitstreamRestriction{");
            sb.append("motion_vectors_over_pic_boundaries_flag=").append(this.motion_vectors_over_pic_boundaries_flag);
            sb.append(", max_bytes_per_pic_denom=").append(this.max_bytes_per_pic_denom);
            sb.append(", max_bits_per_mb_denom=").append(this.max_bits_per_mb_denom);
            sb.append(", log2_max_mv_length_horizontal=").append(this.log2_max_mv_length_horizontal);
            sb.append(", log2_max_mv_length_vertical=").append(this.log2_max_mv_length_vertical);
            sb.append(", num_reorder_frames=").append(this.num_reorder_frames);
            sb.append(", max_dec_frame_buffering=").append(this.max_dec_frame_buffering);
            sb.append('}');
            return sb.toString();
        }
    }

    public String toString() {
        return "VUIParameters{\naspect_ratio_info_present_flag=" + this.aspect_ratio_info_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", sar_width=" + this.sar_width + IOUtils.LINE_SEPARATOR_UNIX + ", sar_height=" + this.sar_height + IOUtils.LINE_SEPARATOR_UNIX + ", overscan_info_present_flag=" + this.overscan_info_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", overscan_appropriate_flag=" + this.overscan_appropriate_flag + IOUtils.LINE_SEPARATOR_UNIX + ", video_signal_type_present_flag=" + this.video_signal_type_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", video_format=" + this.video_format + IOUtils.LINE_SEPARATOR_UNIX + ", video_full_range_flag=" + this.video_full_range_flag + IOUtils.LINE_SEPARATOR_UNIX + ", colour_description_present_flag=" + this.colour_description_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", colour_primaries=" + this.colour_primaries + IOUtils.LINE_SEPARATOR_UNIX + ", transfer_characteristics=" + this.transfer_characteristics + IOUtils.LINE_SEPARATOR_UNIX + ", matrix_coefficients=" + this.matrix_coefficients + IOUtils.LINE_SEPARATOR_UNIX + ", chroma_loc_info_present_flag=" + this.chroma_loc_info_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", chroma_sample_loc_type_top_field=" + this.chroma_sample_loc_type_top_field + IOUtils.LINE_SEPARATOR_UNIX + ", chroma_sample_loc_type_bottom_field=" + this.chroma_sample_loc_type_bottom_field + IOUtils.LINE_SEPARATOR_UNIX + ", timing_info_present_flag=" + this.timing_info_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", num_units_in_tick=" + this.num_units_in_tick + IOUtils.LINE_SEPARATOR_UNIX + ", time_scale=" + this.time_scale + IOUtils.LINE_SEPARATOR_UNIX + ", fixed_frame_rate_flag=" + this.fixed_frame_rate_flag + IOUtils.LINE_SEPARATOR_UNIX + ", low_delay_hrd_flag=" + this.low_delay_hrd_flag + IOUtils.LINE_SEPARATOR_UNIX + ", pic_struct_present_flag=" + this.pic_struct_present_flag + IOUtils.LINE_SEPARATOR_UNIX + ", nalHRDParams=" + this.nalHRDParams + IOUtils.LINE_SEPARATOR_UNIX + ", vclHRDParams=" + this.vclHRDParams + IOUtils.LINE_SEPARATOR_UNIX + ", bitstreamRestriction=" + this.bitstreamRestriction + IOUtils.LINE_SEPARATOR_UNIX + ", aspect_ratio=" + this.aspect_ratio + IOUtils.LINE_SEPARATOR_UNIX + '}';
    }
}
