package com.example.appstyle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstyle.adapter.TreinoAdapter;
import com.example.appstyle.decorator.SpaceItemDecoration;
import com.example.appstyle.fragment.model.TreinoViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddExerciseActivity extends AppCompatActivity {

    private RecyclerView recyclerWorkouts;
    private TreinoViewModel treinoViewModel;
    private Exercise exercise;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        InicializarCampos();
        Objects.requireNonNull(getSupportActionBar()).hide();
        buscarTreinoDoDia();

        this.exercise = (Exercise) getIntent().getSerializableExtra("exercise");

        recyclerWorkouts.setLayoutManager(new LinearLayoutManager(this));

        treinoViewModel = new ViewModelProvider(this).get(TreinoViewModel.class);

        TreinoAdapter adapter = new TreinoAdapter();
        adapter.setOnItemClickListener(new TreinoAdapter.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(String nomeTreino) {
                Log.e("treino", nomeTreino);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DocumentReference treinoRef = db.collection("usuarios").document(usuarioID)
                        .collection("treinos").document(nomeTreino);

                treinoRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Exercise> listaDeExercicios = (List<Exercise>) documentSnapshot.get("exercicios");
                        if (listaDeExercicios == null) listaDeExercicios = new ArrayList<>();
                        // Adicione o novo exercício à lista existente.
                        listaDeExercicios.add(exercise);

                        // Atualize o documento do treino com a lista de exercícios atualizada.
                        treinoRef.update("exercicios", listaDeExercicios)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("good", "Exercício adicionado com sucesso no Firestore");
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "The exercise was added to your workout", Snackbar.LENGTH_SHORT);

                                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.green));
                                    snackbar.setTextColor(Color.WHITE);
                                    snackbar.show();

                                    // Finaliza a atividade após um breve atraso (tempo suficiente para a Snackbar ser visível)
                                    new Handler().postDelayed(() -> {
                                        finish();
                                    }, 2000);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("bad", "Erro ao adicionar exercício no Firestore", e);
                                });
                    } else {
                        Log.e("dam", "Treino não encontrado no Firestore");
                    }
                }).addOnFailureListener(e -> {
                    // Erro ao acessar o documento do treino
                });


            }
        });

        recyclerWorkouts.setAdapter(adapter);

        // Aplicar o SpaceItemDecoration
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerWorkouts.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // Observar os treinos da ViewModel
        treinoViewModel.getTreinos().observe(this, adapter::setTreinos);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void  buscarTreinoDoDia() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            String usuarioID = firebaseAuth.getCurrentUser().getUid();

            db.collection("usuarios").document(usuarioID)
                    .collection("treinos")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        List<DocumentSnapshot> treinos = queryDocumentSnapshots.getDocuments();
                        ArrayList<String> listaDeTreinos = new ArrayList<>();
                        for (DocumentSnapshot treinoSnapshot : treinos) {
                            listaDeTreinos.add(treinoSnapshot.getId());
                        }
                        treinoViewModel.setTreinos(listaDeTreinos);
                    })
                    .addOnFailureListener(e -> {
                        // Erro ao buscar os treinos
                        Log.e("Buscar Treinos", "Erro ao buscar treinos", e);
                    });
        }
    }
    private void InicializarCampos() {
        recyclerWorkouts = findViewById(R.id.recyclerViewOpenSearch);
        imageView = findViewById(R.id.logout);
    }
}