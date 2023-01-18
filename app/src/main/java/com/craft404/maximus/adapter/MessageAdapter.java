package com.craft404.maximus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.craft404.maximus.R;
import com.craft404.maximus.model.MessageQuery;
import com.craft404.maximus.util.SpannableStringBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.craft404.maximus.util.Functions.isSameDay;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final String TAG = "MessageAdapter";
    Context context;
    List<MessageQuery> list;
    boolean showGroupName;

    public MessageAdapter(Context context, List<MessageQuery> list, boolean showGroupName) {
        this.context = context;
        this.list = list;
        this.showGroupName = showGroupName;
    }

    public void setList(List<MessageQuery> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageQuery message = list.get(position);
        if (message.getGroupName() != null) {
            if (message.isElite()) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                spannableStringBuilder.appendWithSpace(message.getPersonName(), new ForegroundColorSpan(context.getColor(R.color.colorAccent)));
                if (showGroupName) {
                    spannableStringBuilder.appendWithSpace("@");
                    spannableStringBuilder.appendWithSpace(message.getGroupName(), new ForegroundColorSpan(context.getColor(R.color.color_5)));
                }
                holder.from.setText(spannableStringBuilder.build());
            } else if (message.isSpammer()) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                spannableStringBuilder.appendWithSpace(message.getPersonName(), new ForegroundColorSpan(context.getColor(R.color.black_2)));
                if (showGroupName) {
                    spannableStringBuilder.appendWithSpace("@");
                    spannableStringBuilder.appendWithSpace(message.getGroupName(), new ForegroundColorSpan(context.getColor(R.color.color_5)));
                }
                holder.from.setText(spannableStringBuilder.build());
            } else {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                if (showGroupName) {
                    spannableStringBuilder.appendWithSpace(message.getPersonName(), new ForegroundColorSpan(context.getColor(R.color.color_2)));
                    spannableStringBuilder.appendWithSpace("@");
                    spannableStringBuilder.appendWithSpace(message.getGroupName(), new ForegroundColorSpan(context.getColor(R.color.color_5)));
                    holder.from.setText(spannableStringBuilder.build());
                } else {
                    holder.from.setText(message.getPersonName());
                }
            }
        } else {
            holder.from.setText(message.getPersonName());
            holder.from.setTextColor(context.getColor(R.color.colorAccent));
        }
        holder.message.setText(message.getMessage());
        Linkify.addLinks(holder.message, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
        holder.message.setLinkTextColor(ContextCompat.getColor(context, R.color.color_4));

        Linkify.addLinks(holder.from, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
        holder.date.setText(message.getTimestamp());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
        Date date = new Date(message.getTimestamp());
        holder.date.setText(isSameDay(date, new Date()) ? time.format(date) : dateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView from, message, date;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
        }
    }
}
