package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        getSupportActionBar().hide();
        ImageView imageView = findViewById(R.id.logo);
        TextView loginText = findViewById(R.id.loginText);
        SpannableString spannableString = new SpannableString(loginText.getText());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        loginText.setText(spannableString);

        ExerciseApiService exerciseApiService = new ExerciseApiService();
        exerciseApiService.getExercises(value -> {
            System.out.println(value);
        });


        loginText.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}