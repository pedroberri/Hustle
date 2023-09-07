package com.example.appstyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstyle.adapter.OpenWorkoutAdapter;
import com.example.appstyle.fragment.model.TreinoViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorkoutActivity extends AppCompatActivity {

    private TextView treinoText;
    private ImageView sair, delete;
    private List<Exercise> listaDeExercicios = new ArrayList<>();
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                DocumentReference treinoRef = db.collection("usuarios").document(usuarioID)
                        .collection("treinos").document(treino);

                treinoRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Treino excluído com sucesso
                                Log.d("excluirTreino", "Treino excluído com sucesso");
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Your workout was deleted", Snackbar.LENGTH_SHORT);

                                snackbar.getView().setBackgroundColor(getResources().getColor(R.color.red));
                                snackbar.setTextColor(Color.WHITE);
                                snackbar.show();


                                new Handler().postDelayed(WorkoutActivity.this::iniciarHome, 2000);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Erro ao excluir o treino
                                Log.e("excluirTreino", "Erro ao excluir o treino", e);
                            }
                        });
            }
        });

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
                    try {
                        if (documentSnapshot.exists()) {
                            List<Map<String, Object>> exercises = (List<Map<String, Object>>) documentSnapshot.get("exercicios");
                            if (exercises != null) {
                                for (Map<String, Object> exercise : exercises
                                ) {
                                    String name = (String) exercise.get("name");
                                    String target = (String) exercise.get("target");
                                    String equipament = (String) exercise.get("equipament");
                                    String gif = (String) exercise.get("gif");
                                    Exercise auxExercise = new Exercise(name, target, equipament, gif);
                                    listaDeExercicios.add(auxExercise);
                                }
                            }
                            OpenWorkoutAdapter openWorkoutAdapter = new OpenWorkoutAdapter(listaDeExercicios);
                            openWorkoutAdapter.setOnItemClickListener(new OpenWorkoutAdapter.OnItemClickListener() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onItemClick(Exercise exercise) {
                                    if (!exercise.getTarget().equals("None")) {
                                        listaDeExercicios.remove(exercise);
                                        openWorkoutAdapter.notifyDataSetChanged();

                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        DocumentReference treinoRef = db.collection("usuarios").document(usuarioID)
                                                .collection("treinos").document(treino);

                                        treinoRef.update("exercicios", listaDeExercicios)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("good", "Exercício removido com sucesso no Firestore");
                                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "The exercise was removed from your workout", Snackbar.LENGTH_SHORT);

                                                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.red));
                                                    snackbar.setTextColor(Color.WHITE);
                                                    snackbar.show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("bad", "Erro ao remover exercício no Firestore", e);
                                                });

                                     } else {
                                        Log.e("123", "OUT");

                                    }
                                }
                            });
                            recyclerView.setAdapter(openWorkoutAdapter);
                        } else {
                            Log.e("dam", "Deu BO");
                        }
                    } catch (Exception a) {
                        Log.e("bo", Objects.requireNonNull(a.getMessage()));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("dam", "Deu BO");
                });
    }

    private void InicializarCampos() {
        treinoText = findViewById(R.id.treino);
        sair = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerViewWorkout);
        delete = findViewById(R.id.delete);
    }

    private void iniciarHome() {
        Intent intent = new Intent(this, HomeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }
}