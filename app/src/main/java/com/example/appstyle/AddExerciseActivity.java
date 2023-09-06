package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.appstyle.adapter.TreinoAdapter;

import java.util.Objects;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView textHeader;
    private RecyclerView recyclerWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InicializarCampos();
        setContentView(R.layout.activity_add_exercise);
        Objects.requireNonNull(getSupportActionBar()).hide();

        textHeader.setText("Treinos");

//        TreinoAdapter adapter = new TreinoAdapter();
//        recyclerWorkouts.setAdapter(adapter);
    }

    private void InicializarCampos() {
        textHeader = findViewById(R.id.props);
        recyclerWorkouts = findViewById(R.id.recyclerViewOpenSearch);
    }
}