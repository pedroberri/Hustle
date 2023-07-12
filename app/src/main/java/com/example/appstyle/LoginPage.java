package com.example.appstyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    private EditText edit_email, edit_password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        InicializarCampos();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            TelaPrincipal();
                        } else {
                            Snackbar snackbar = Snackbar.make(v, "Invalid email or password", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.RED);
                            snackbar.setTextColor(Color.WHITE);
                            snackbar.show();
                        }
                    }
                });
            }
        });


        //Deixar sublinhado
        TextView cadastroText = findViewById(R.id.cadastroText);
        SpannableString spannableString = new SpannableString(cadastroText.getText());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        cadastroText.setText(spannableString);


        //Ir para página de cadastro
        cadastroText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            TelaPrincipal();
        }
    }

    public void TelaPrincipal() {
        Intent intent = new Intent(LoginPage.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void InicializarCampos() {
        edit_email = findViewById(R.id.nomeUser);
        edit_password = findViewById(R.id.senha);
        login_button = findViewById(R.id.buttonLogar);
    }
}