package com.example.appstyle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstyle.R;

import java.util.List;

public class PesquisaAdapter extends RecyclerView.Adapter<PesquisaAdapter.ViewHolder> {

    private List<String> itemList;

    public PesquisaAdapter(List<String> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int startIndex = position * 2;
        if (startIndex < itemList.size()) {
            holder.textView1.setText(itemList.get(startIndex));
        }

        int endIndex = startIndex + 1;
        if (endIndex < itemList.size()) {
            holder.textView2.setText(itemList.get(endIndex));
        }
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((double) itemList.size() / 2);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
        }
    }
}
