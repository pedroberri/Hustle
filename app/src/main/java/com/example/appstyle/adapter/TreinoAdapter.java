package com.example.appstyle.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstyle.R;

import java.util.ArrayList;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder> {

    private ArrayList<String> treinos = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setTreinos(ArrayList<String> treinos) {
        this.treinos = treinos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TreinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treino, parent, false);
        return new TreinoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinoViewHolder holder, int position) {
        holder.bind(treinos.get(position));
    }

    @Override
    public int getItemCount() {
        return treinos.size();
    }

    static class TreinoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeTreino;

        public TreinoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeTreino = itemView.findViewById(R.id.textViewNomeTreino);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(String nomeTreino) {
            textViewNomeTreino.setText(nomeTreino);
            textViewNomeTreino.setBackgroundResource(R.drawable.card_c1);
        }
    }
}
