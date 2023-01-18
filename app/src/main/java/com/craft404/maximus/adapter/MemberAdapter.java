package com.craft404.maximus.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.craft404.maximus.R;
import com.craft404.maximus.model.People;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private static final String TAG = "MemberAdapter";
    Context context;
    List<People> list;

    public MemberAdapter(Context context, List<People> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<People> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MemberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        People person = list.get(position);
        Log.d(TAG, "onBindViewHolder: " + list.get(position).getName());
        holder.name.setText(person.getName());
        if (person.isElite()) {
            holder.spammer.setVisibility(View.GONE);
            holder.important.setVisibility(View.VISIBLE);
        }
        if (person.isSpammer()) {
            holder.spammer.setVisibility(View.VISIBLE);
            holder.important.setVisibility(View.GONE);
        } else if (!person.isSpammer() && !person.isElite()) {
            holder.spammer.setVisibility(View.GONE);
            holder.important.setVisibility(View.GONE);
        }
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(list.get(position).getIcon());
        holder.icon.setBackground(draw);
        holder.icon.setText(String.valueOf(person.getName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView name, icon;
        ImageView important, spammer;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            important = itemView.findViewById(R.id.important);
            spammer = itemView.findViewById(R.id.spammer);
        }
    }
}
