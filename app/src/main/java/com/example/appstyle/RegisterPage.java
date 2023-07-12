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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class RegisterPage extends AppCompatActivity {
    private EditText edit_email, edit_password, edit_verify_password;
    private Button submit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Objects.requireNonNull(getSupportActionBar()).hide();


        InicializarCampos();

        //Sublima texto
        TextView loginText = findViewById(R.id.loginText);
        SpannableString spannableString = new SpannableString(loginText.getText());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        loginText.setText(spannableString);

        //Click em registrar-se
        submit_button.setOnClickListener(v -> {
            String email = edit_email.getText().toString();
            String senha = edit_password.getText().toString();
            String confimaSenha = edit_verify_password.getText().toString();

            String mensagemErro = "All fields are required";
            Snackbar snackbar = Snackbar.make(v, mensagemErro, Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.WHITE);

            if(email.isEmpty() || senha.isEmpty() || confimaSenha.isEmpty()) {
                snackbar.show();
            } else if (!senha.equals(confimaSenha)) {
                snackbar.setText("The passwords are different");
                snackbar.show();
            } else {
                CadastrarUsuario(email, senha, v);
            }


        });

        // Vai para página de login
        loginText.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }

    private void CadastrarUsuario(String email, String password, View v) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    TelaPrincipal();
                }else {
                    String erro = "";
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "The password must contain at least 6 characters";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "A use with this email address has already been registeredA use with this email address has already been registered";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "This email is not valid";
                    }
                    catch (Exception e) {
                        erro = "Something wrong, try to create an account later";
                    } finally {
                        Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    }
                }
            }
        });
    }

    public void TelaPrincipal() {
        Intent intent = new Intent(RegisterPage.this, LoginPage.class);
        startActivity(intent);
        finish();
    }

    private void InicializarCampos() {
        edit_email = findViewById(R.id.email);
        edit_password = findViewById(R.id.senha);
        edit_verify_password = findViewById(R.id.confirmarSenha);
        submit_button = findViewById(R.id.submit_button);
    }
}