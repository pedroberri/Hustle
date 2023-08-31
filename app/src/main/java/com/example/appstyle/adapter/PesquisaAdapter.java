package com.example.appstyle.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appstyle.OpenSearchActivity;
import com.example.appstyle.R;

import java.util.List;

public class PesquisaAdapter extends RecyclerView.Adapter<PesquisaAdapter.ViewHolder> {

    private List<String> itemList;
    private int[] backgrounds = {R.drawable.card_c1, R.drawable.card_c2, R.drawable.card_c3,
            R.drawable.card_c4, R.drawable.card_c5, R.drawable.card_c6,
            R.drawable.card_c7, R.drawable.card_c8, R.drawable.card_c9,
            R.drawable.card_c10};

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
            holder.drawable1.setText(itemList.get(startIndex));
            holder.drawable1.setBackgroundResource(backgrounds[startIndex]);
        }

        int endIndex = startIndex + 1;
        if (endIndex < itemList.size()) {
            holder.drawable2.setText(itemList.get(endIndex));
            holder.drawable2.setBackgroundResource(backgrounds[endIndex]);
        }
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((double) itemList.size() / 2);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView drawable1, drawable2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            drawable1 = itemView.findViewById(R.id.tv1);
            drawable2 = itemView.findViewById(R.id.tv2);

            drawable1.setOnClickListener(view -> {
                String text = drawable1.getText().toString();
                Context context = view.getContext();

                Intent intent = new Intent(context, OpenSearchActivity.class);
                intent.putExtra("text", text);
                context.startActivity(intent);
            });

            drawable2.setOnClickListener(view -> {
                String text = drawable2.getText().toString();
                Context context = view.getContext();

                Intent intent = new Intent(context, OpenSearchActivity.class);
                intent.putExtra("text", text);
                context.startActivity(intent);
            });
        }
    }
}
