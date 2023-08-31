package com.example.appstyle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstyle.Exercise;
import com.example.appstyle.R;

import java.util.List;

public class OpenSearchAdapter extends RecyclerView.Adapter<OpenSearchAdapter.ViewHolder> {

    private final List<Exercise> exercises;

    public OpenSearchAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(exercises.get(position).getName());
        holder.target.setText(exercises.get(position).getTarget());
        holder.equipament.setText(exercises.get(position).getEquipament());
        holder.gif.setImageResource(Integer.parseInt(exercises.get(position).getGif()));
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((double) exercises.size() / 2);
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
