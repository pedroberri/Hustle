package com.example.appstyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstyle.adapter.TreinoAdapter;
import com.example.appstyle.decorator.SpaceItemDecoration;
import com.example.appstyle.fragment.model.TreinoViewModel;
import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        InicializarCampos();
        Objects.requireNonNull(getSupportActionBar()).hide();
        buscarTreinoDoDia();

        Exercise exercise = (Exercise) getIntent().getSerializableExtra("exercise");

        recyclerWorkouts.setLayoutManager(new LinearLayoutManager(this));

        treinoViewModel = new ViewModelProvider(this).get(TreinoViewModel.class);

        TreinoAdapter adapter = new TreinoAdapter();
        adapter.setOnItemClickListener(new TreinoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String nomeTreino) {
                Intent intent = new Intent(AddExerciseActivity.this, WorkoutActivity.class);
                intent.putExtra("treino", nomeTreino);
                startActivity(intent);
            }
        });

        recyclerWorkouts.setAdapter(adapter);

        // Aplicar o SpaceItemDecoration
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerWorkouts.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // Observar os treinos da ViewModel
        treinoViewModel.getTreinos().observe(this, adapter::setTreinos);
    }

    private void InicializarCampos() {
        recyclerWorkouts = findViewById(R.id.recyclerViewOpenSearch);
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
}