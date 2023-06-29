package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        getSupportActionBar().hide();

        TextView cadastroText = findViewById(R.id.cadastroText);
        SpannableString spannableString = new SpannableString(cadastroText.getText());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        cadastroText.setText(spannableString);
        cadastroText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(intent);
        });
    }
}