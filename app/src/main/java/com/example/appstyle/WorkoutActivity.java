package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        String treino = getIntent().getStringExtra("treino");
        if (treino.equals("Teste")) {

        }
    }
}