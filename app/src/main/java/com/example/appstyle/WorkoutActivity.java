package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstyle.adapter.OpenWorkoutAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorkoutActivity extends AppCompatActivity {

    private TextView treinoText;
    private ImageView sair;
    private List<Exercise> exercises = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Objects.requireNonNull(getSupportActionBar()).hide();
        InicializarCampos();
        String treino = getIntent().getStringExtra("treino");
        treinoText.setText(treino);

        buscarExercicios(treino);
//        if (treino.equals("Teste")) {
//            String name = "chest";
//            String target = "pectorals";
//            String equipament = "band";
//            String gif = "https://api.exercisedb.io/image/5UAJuWk6buTr5c";
//            Exercise exercise = new Exercise(name, target, equipament, gif);
//            ArrayList<Exercise> list = new ArrayList<>();
//            list.add(exercise);
//            OpenWorkoutAdapter openWorkoutAdapter = new OpenWorkoutAdapter(list);
//            recyclerView.setAdapter(openWorkoutAdapter);
//        }

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void buscarExercicios(String treino) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DocumentReference treinoRef = db.collection("usuarios").document(usuarioID)
                .collection("treinos").document(treino);

        treinoRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, Object>> exercises = (List<Map<String, Object>>) documentSnapshot.get("exercicios");
                        ArrayList<Exercise> list = new ArrayList<>();
                        for (Map<String, Object> exercise: exercises
                             ) {
                            String name = "chest";
                            String target = "pectorals";
                            String equipament = "band";
                            String gif = "https://api.exercisedb.io/image/5UAJuWk6buTr5c";
                            Exercise auxExercise = new Exercise(name, target, equipament, gif);
                            list.add(auxExercise);
                        }
                        if (list.isEmpty()) {
                            list.add(semTreinos());
                        }
                        OpenWorkoutAdapter openWorkoutAdapter = new OpenWorkoutAdapter(list);
                        recyclerView.setAdapter(openWorkoutAdapter);
                    } else {
                        Log.e("dam", "Deu BO");
                    }
                })
                .addOnFailureListener(e -> {
                    // Erro ao obter o documento do treino
                });
    }

    private Exercise semTreinos() {
        String name = "";
        String target = "";
        String equipament = "";
        String gif = "";
        return new Exercise(name, target, equipament, gif);
    }

    private void InicializarCampos() {
        treinoText = findViewById(R.id.treino);
        sair = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerViewWorkout);
    }
}