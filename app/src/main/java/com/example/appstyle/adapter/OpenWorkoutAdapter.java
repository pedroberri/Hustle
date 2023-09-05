package com.example.appstyle.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.appstyle.Exercise;
import com.example.appstyle.R;

import java.util.List;

public class OpenWorkoutAdapter extends RecyclerView.Adapter<OpenWorkoutAdapter.ViewHolder> {

    private final List<Exercise> exercises;

    public OpenWorkoutAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(exercises.get(position).getName());
        holder.target.setText(exercises.get(position).getTarget());
        holder.equipament.setText(exercises.get(position).getEquipament());
        Glide.with(holder.itemView.getContext())
                .load(exercises.get(position).getGif())
                .transition(DrawableTransitionOptions.withCrossFade()) // Opcional: adicionar efeito de transição
                .into(holder.gif);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView target, name, equipament;
        ImageView gif;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            target = itemView.findViewById(R.id.target);
            name = itemView.findViewById(R.id.name);
            equipament = itemView.findViewById(R.id.equipament);
            gif = itemView.findViewById(R.id.gif);
        }
    }
}
