package com.rekoo.libs.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rekoo.libs.entity.Feedback;
import com.rekoo.libs.utils.ResUtils;
import java.util.List;

public class FeedbackListAdapter extends BaseAdapter {
    private String[] arr;
    private Context context;
    private List<Feedback> list;
    private LayoutInflater mInflater;

    public final class ViewHolder {
        public TextView feedback_category;
        public TextView feedback_time;
        public TextView tv_feedback_question;
        public TextView tv_feedback_reply;
    }

    public FeedbackListAdapter(Context context, List<Feedback> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void handleMessage(Message msg) {
        int i = msg.what;
    }

    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        this.arr = new String[]{ResUtils.getString("game_bug", this.context), ResUtils.getString("game_suggest", this.context), ResUtils.getString("pay_question", this.context)};
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(ResUtils.getLayout("feedback_list_item", this.context), parent, false);
            int feedback_categoryId = ResUtils.getId("feedback_category", this.context);
            int feedback_timeId = ResUtils.getId("feedback_time", this.context);
            int tv_feedback_questionId = ResUtils.getId("tv_feedback_question", this.context);
            int tv_feedback_replyId = ResUtils.getId("tv_feedback_reply", this.context);
            holder.feedback_category = (TextView) convertView.findViewById(feedback_categoryId);
            holder.feedback_time = (TextView) convertView.findViewById(feedback_timeId);
            holder.tv_feedback_question = (TextView) convertView.findViewById(tv_feedback_questionId);
            holder.tv_feedback_reply = (TextView) convertView.findViewById(tv_feedback_replyId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.feedback_category.setText(new StringBuilder(String.valueOf(ResUtils.getString("feedback_category", this.context))).append(this.arr[((Feedback) this.list.get(position)).getCategory()]).toString());
        holder.feedback_time.setText(new StringBuilder(String.valueOf(ResUtils.getString("feedback_time", this.context))).append(((Feedback) this.list.get(position)).getDatetime()).toString());
        holder.tv_feedback_question.setText(((Feedback) this.list.get(position)).getContent());
        holder.tv_feedback_reply.setText(((Feedback) this.list.get(position)).getSubmiter());
        return convertView;
    }
}
