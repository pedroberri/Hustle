package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.appstyle.adapter.TreinoAdapter;
import com.example.appstyle.decorator.SpaceItemDecoration;
import com.example.appstyle.fragment.model.TreinoViewModel;

import java.util.Objects;

public class AddExerciseActivity extends AppCompatActivity {

    private RecyclerView recyclerWorkouts;
    private TreinoViewModel treinoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        InicializarCampos();
        Objects.requireNonNull(getSupportActionBar()).hide();
        treinoViewModel = new ViewModelProvider(this).get(TreinoViewModel.class);

        TreinoAdapter adapter = new TreinoAdapter();

        adapter.setOnItemClickListener(new TreinoAdapter.OnItemClickListener()  {
            @Override
            public void onItemClick(String nomeTreino) {
                Intent intent = new Intent(AddExerciseActivity.this, WorkoutActivity.class);
                intent.putExtra("treino", nomeTreino);
                startActivity(intent);
            }
        });

        recyclerWorkouts.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerWorkouts.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // Observar os treinos da ViewModel
        treinoViewModel.getTreinos().observe(this, adapter::setTreinos);

    }

    private void InicializarCampos() {
        recyclerWorkouts = findViewById(R.id.recyclerViewOpenSearch);
    }
}