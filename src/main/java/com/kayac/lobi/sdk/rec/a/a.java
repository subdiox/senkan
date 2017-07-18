package com.kayac.lobi.sdk.rec.a;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.OneLine;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.components.SectionView;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.utils.TimeUtil;
import com.kayac.lobi.libnakamap.value.ProfileValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.rec.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class a {
    public a(ProfileValue profileValue, Fragment fragment, Context context, boolean z, String str) {
        String uid = profileValue.getUserValue().getUid();
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = fragment.getView();
        SectionView sectionView = (SectionView) view.findViewById(R.id.lobi_profile_rec_section);
        sectionView.setVisibility(0);
        sectionView.setImage(null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lobi_profile_profile_cover_content_video_list);
        linearLayout.removeAllViews();
        JSONArray videos = profileValue.getVideos();
        int min = Math.min(videos.length(), 3);
        int i = 0;
        while (i < min) {
            try {
                JSONObject jSONObject = videos.getJSONObject(i);
                String string = jSONObject.getString("uid");
                JSONObject optJSONObject = jSONObject.optJSONObject("user");
                UserValue userValue = optJSONObject != null ? new UserValue(optJSONObject) : null;
                String string2 = jSONObject.getString("thumbnail_url");
                String string3 = jSONObject.getString(LoginEntranceDialog.ARGUMENTS_TITLE);
                int i2 = jSONObject.getInt("views");
                int i3 = jSONObject.getInt("play_time");
                boolean equals = jSONObject.optString("has_mic", "0").equals("1");
                long parseLong = Long.parseLong(JSONUtil.getString(jSONObject, "created_date", "0"));
                int i4 = i3 / 60;
                int i5 = i3 % 60;
                View inflate = layoutInflater.inflate(R.layout.lobi_video_list_item, null);
                ImageLoaderView imageLoaderView = (ImageLoaderView) inflate.findViewById(R.id.lobi_video_thumbnail);
                TextView textView = (TextView) inflate.findViewById(R.id.lobi_video_title);
                TextView textView2 = (TextView) inflate.findViewById(R.id.lobi_video_user_name);
                TextView textView3 = (TextView) inflate.findViewById(R.id.lobi_video_created_at);
                TextView textView4 = (TextView) inflate.findViewById(R.id.lobi_video_views);
                TextView textView5 = (TextView) inflate.findViewById(R.id.lobi_video_playtime);
                View findViewById = inflate.findViewById(R.id.lobi_video_has_mic_icon);
                imageLoaderView.loadImage(string2, 128);
                textView.setText(string3);
                if (userValue != null) {
                    textView2.setText(context.getString(R.string.lobisdk_video_user_name, new Object[]{userValue.getName()}));
                } else {
                    textView2.setText("");
                }
                textView3.setText(String.format("%s", new Object[]{TimeUtil.getTimeSpan(context, parseLong * 1000)}));
                textView4.setText(context.getString(R.string.lobisdk_views, new Object[]{Integer.valueOf(i2)}));
                textView5.setText(context.getString(R.string.lobisdk_time__int__colon__int, new Object[]{Integer.valueOf(i4), Integer.valueOf(i5)}));
                findViewById.setVisibility(equals ? 0 : 8);
                inflate.setOnClickListener(new b(this, string));
                linearLayout.addView(inflate);
                i++;
            } catch (Throwable e) {
                b.a(e);
            }
        }
        ListRow listRow = (ListRow) view.findViewById(R.id.lobi_profile_profile_cover_content_video_area_read_more);
        TextView textView6 = (TextView) view.findViewById(R.id.lobi_profile_profile_cover_content_video_area_banner);
        if (min > 0) {
            listRow.setVisibility(0);
            textView6.setVisibility(8);
            ((OneLine) listRow.getContent(1)).setText(0, context.getString(R.string.lobi_see_more));
            listRow.findViewById(R.id.lobi_list_row_area).setOnClickListener(new c(this, uid));
            return;
        }
        listRow.setVisibility(8);
        textView6.setVisibility(0);
    }
}
