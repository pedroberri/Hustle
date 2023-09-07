package com.example.appstyle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorkoutRegisterActivity extends AppCompatActivity {

    private ImageView close;
    private Button submit_button;
    private EditText workoutName;
    private RadioGroup radioGroupDaysOfWeek;

    private List<RadioButton> daysOfTheWeek = new ArrayList<>();

    private String usuarioID;

    private String[] daysOfTheWeekString = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        InicializarCampos();
        buscarTreino();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workoutName.getText().length() != 0) {
                    RadioGroup radioGroupDaysOfWeek = findViewById(R.id.radioGroupDaysOfWeek);
                    int checkedId = radioGroupDaysOfWeek.getCheckedRadioButtonId();
                    if (checkedId != -1) {
                        RadioButton selectedRadioButton = findViewById(checkedId);
                        String selectedDayOfWeek = selectedRadioButton.getText().toString();
                        salvarTreino(selectedDayOfWeek);
                    } else {
                        snackBar("You have to select one day of the week", v);
                    }
                } else {
                    snackBar("You have to name your workout", v);
                }
            }
        });

    }

    private void snackBar(String texto, View v) {
        Snackbar snackbar = Snackbar.make(v, texto, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void salvarTreino(String dayOfTheWeek) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            String usuarioID = firebaseAuth.getCurrentUser().getUid();

            DocumentReference documentReference = db.collection("usuarios").document(usuarioID);

            Map<String, Object> treino1 = new HashMap<>();
            treino1.put("diaSemana", dayOfTheWeek);
            CollectionReference treinosRef = documentReference.collection("treinos");
            DocumentReference treino1Ref = treinosRef.document(workoutName.getText().toString());

            treino1Ref.set(treino1)
                    .addOnSuccessListener(aVoid -> {
                        // Treino salvo com sucesso
                        Log.d("Salvar Treino", "Treino salvo com sucesso");
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You workout was added", Snackbar.LENGTH_SHORT);

                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.green));
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();

                        new Handler().postDelayed(this::goHome, 2000);
                    })
                    .addOnFailureListener(e -> {
                        // Erro ao salvar o treino
                        Log.e("Salvar Treino", "Erro ao salvar treino", e);
                    });

        }
    }

    private void InicializarCampos() {
        close = findViewById(R.id.close);
        submit_button = findViewById(R.id.buttonLogar);
        workoutName = findViewById(R.id.workoutName);
        radioGroupDaysOfWeek = findViewById(R.id.radioGroupDaysOfWeek);

        daysOfTheWeek.add(findViewById(R.id.radioButtonSunday));
        daysOfTheWeek.add(findViewById(R.id.radioButtonMonday));
        daysOfTheWeek.add(findViewById(R.id.radioButtonTuesday));
        daysOfTheWeek.add(findViewById(R.id.radioButtonWednesday));
        daysOfTheWeek.add(findViewById(R.id.radioButtonThursday));
        daysOfTheWeek.add(findViewById(R.id.radioButtonFriday));
        daysOfTheWeek.add(findViewById(R.id.radioButtonSaturday));

    }

    private void buscarTreino() {
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

                        for (int i = 0 ; i < treinos.size() ; i ++) {
                            listaDeTreinos.add(treinos.get(i).getId());
                            String diaSemanaTreino = treinos.get(i).getString("diaSemana");
                            for (int j = 0; j < daysOfTheWeekString.length; j ++) {
                                if (daysOfTheWeekString[j].equals(diaSemanaTreino)) {
                                    daysOfTheWeek.get(j).setVisibility(View.GONE);
                                }
                            }

                        }
                    })
                    .addOnFailureListener(e -> {
                        // Erro ao buscar os treinos
                        Log.e("Buscar Treinos", "Erro ao buscar treinos", e);
                    });
        }
    }


    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }
}