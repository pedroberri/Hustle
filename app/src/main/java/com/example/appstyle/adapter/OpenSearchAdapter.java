package com.example.appstyle.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.appstyle.AddExerciseActivity;
import com.example.appstyle.Exercise;
import com.example.appstyle.OpenSearchActivity;
import com.example.appstyle.R;

import java.io.Serializable;
import java.util.List;

public class OpenSearchAdapter extends RecyclerView.Adapter<OpenSearchAdapter.ViewHolder> {

    private final List<Exercise> exercises;

    public OpenSearchAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(exercises.get(position).getName());
        holder.target.setText(exercises.get(position).getTarget());
        holder.equipament.setText(exercises.get(position).getEquipament());
        Glide.with(holder.itemView.getContext())
                .load(exercises.get(position).getGif())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.gif);
        holder.addExercise.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, AddExerciseActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView target, name, equipament;
        ImageView gif;
        Button addExercise;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            target = itemView.findViewById(R.id.target);
            name = itemView.findViewById(R.id.name);
            equipament = itemView.findViewById(R.id.equipament);
            gif = itemView.findViewById(R.id.gif);
            addExercise = itemView.findViewById(R.id.addExercise);

//            addExercise.setOnClickListener(view -> {
//                Context context = view.getContext();
//                Intent intent = new Intent(context, AddExerciseActivity.class);
//            });
        }
    }
}
