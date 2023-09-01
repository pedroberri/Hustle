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
    private OnItemClickListener onItemClickListener;


    //Pegar o treino que foi clicado
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

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
        String nomeTreino = treinos.get(position);
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(nomeTreino);
            }
        });
        holder.bind(nomeTreino, position);
    }

    @Override
    public int getItemCount() {
        return treinos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String nomeTreino);
    }



    static class TreinoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeTreino;
        int[] backgroundDrawables = {R.drawable.card_c1, R.drawable.card_c2, R.drawable.card_c3,
                R.drawable.card_c4,R.drawable.card_c5,R.drawable.card_c6,R.drawable.card_c7};


    public TreinoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeTreino = itemView.findViewById(R.id.textViewNomeTreino);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(String nomeTreino, int position) {
            textViewNomeTreino.setText(nomeTreino);
            textViewNomeTreino.setBackgroundResource(backgroundDrawables[position % backgroundDrawables.length]);

        }
    }
}
