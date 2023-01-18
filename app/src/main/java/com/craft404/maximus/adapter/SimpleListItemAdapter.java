package com.craft404.maximus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SimpleListItemAdapter extends RecyclerView.Adapter<SimpleListItemAdapter.SimpleListViewHolder> {
    Context context;
    ArrayList<String> arrayList;

    public SimpleListItemAdapter(Context context, ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SimpleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleListViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleListViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SimpleListViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SimpleListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
//
//    public void addItem(String string) {
//        list.add(0, string);
//        notifyItemInserted(0);
//    }
//
//    public void removeItem(String string) {
//        int position = list.indexOf(string);
//        list.remove(string);
//        notifyItemRemoved(position);
//    }
}
