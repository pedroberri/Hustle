package com.example.appstyle;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appstyle.adapter.OpenSearchAdapter;
import com.example.appstyle.api.ExerciseApiService;
import com.example.appstyle.api.StringCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchNameActivity extends AppCompatActivity {

    private ImageView logout_button;
    private TextView props;
    private RecyclerView recyclerViewOpen;
    private ExerciseApiService exerciseApiService = new ExerciseApiService();
    private List<Exercise> exercises = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_search);

        InicializarCampos();
        Objects.requireNonNull(getSupportActionBar()).hide();

        String propsString = getIntent().getStringExtra("text");
        props.setText(propsString.substring(0, 1).toUpperCase() + propsString.substring(1));

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        exerciseApiService.getSearch(props.getText().toString().toLowerCase(), new StringCallback() {
            @Override
            public void callbacK(String value) {
                Log.e("valueReturn", value);
                if (value != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(value);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String target = jsonObject.getString("target");
                                String equipament = jsonObject.getString("equipment");
                                String gif = jsonObject.getString("gifUrl");
                                Exercise exercise = new Exercise(name, target, equipament, gif);
                                exercises.add(exercise);
                            }
                            OpenSearchAdapter adapter = new OpenSearchAdapter(exercises);
                            recyclerViewOpen.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void logout() {
        finish();
    }

    private void InicializarCampos() {
        logout_button = findViewById(R.id.logout);
        recyclerViewOpen = findViewById(R.id.recyclerViewOpenSearch);
        props = findViewById(R.id.props);
    }
}