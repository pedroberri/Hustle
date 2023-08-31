package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstyle.adapter.OpenSearchAdapter;
import com.example.appstyle.api.ExerciseApiService;
import com.example.appstyle.api.StringCallback;
import com.example.appstyle.fragment.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OpenSearchActivity extends AppCompatActivity {

    private ImageView logout_button;
    private TextView props;
    private RecyclerView recyclerView;
    private ExerciseApiService exerciseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_search);
        InicializarCampos();
        Objects.requireNonNull(getSupportActionBar()).hide();

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        exerciseApiService.getListOfExercises(props.getText().toString().toLowerCase(), new StringCallback() {
            @Override
            public void callbacK(String value) {
                Gson gson = new Gson();
                String[] bodyPartsArray = gson.fromJson(value, String[].class);
                System.out.println(bodyPartsArray[0]);
            }
        });

        props.setText(getIntent().getStringExtra("text") + " exercises");

        List<Exercise> exercises = new ArrayList<>();

//        OpenSearchAdapter adapter = new OpenSearchAdapter(exercises);
//        recyclerView.setAdapter(adapter);
    }

    private void logout() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void InicializarCampos() {
        logout_button = findViewById(R.id.logout);
        recyclerView = findViewById(R.id.recyclerViewSearch);
        props = findViewById(R.id.props);
    }
}