package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.appstyle.api.QuoteApiService;
import com.example.appstyle.api.StringCallback;
import com.example.appstyle.databinding.ActivityHomeBinding;
import com.example.appstyle.fragment.model.TreinoViewModel;
import com.google.firebase.auth.FirebaseAuth;

import com.example.appstyle.fragment.HomeFragment;
import com.example.appstyle.fragment.SearchFragment;
import com.example.appstyle.fragment.WorkoutFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {


    private ImageView logout_button;

    ActivityHomeBinding binding;

    private TreinoViewModel treinoViewModel;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        replaceFragment(new HomeFragment());


        binding.navBar.setOnItemSelectedListener(item -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

            switch (item.getItemId()) {
                case R.id.home:
                    if (!(currentFragment instanceof HomeFragment)) {
                        replaceFragment(new HomeFragment());
                    }
                    break;
                case R.id.search:
                    if (!(currentFragment instanceof SearchFragment)) {
                        replaceFragment(new SearchFragment());
                    }
                    break;
                case R.id.workout:
                    if (!(currentFragment instanceof WorkoutFragment)) {
                        replaceFragment(new WorkoutFragment());
                    }
                    break;
            }
            return true;
        });


        treinoViewModel = new ViewModelProvider(this).get(TreinoViewModel.class);

        // Verificar se o treino do dia já foi buscado antes
        if (treinoViewModel.getTreinoDoDia().getValue() == null) {
            buscarTreinoDoDia();
        }

        if (treinoViewModel.getQuote().getValue() == null) {
            buscaQuote();
        }


    }

    private void buscaQuote() {
        QuoteApiService quoteApiService = new QuoteApiService();
        quoteApiService.getRandomQuote(new StringCallback() { @Override
        public void callbacK(String response) {
            if (response != null) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    // Verificando se a lista não está vazia
                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0); // Pega o primeiro objeto da lista

                        String content = jsonObject.getString("content");
                        String author = jsonObject.getString("author");
                        treinoViewModel.setQuote(content + "\n- " + author);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Trate o caso de erro
            }
        }
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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
                        Calendar calendar = Calendar.getInstance();
                        int diaAtual = calendar.get(Calendar.DAY_OF_WEEK); // 1 (Domingo) a 7 (Sábado)

                        List<DocumentSnapshot> treinos = queryDocumentSnapshots.getDocuments();
                        ArrayList<String> listaDeTreinos = new ArrayList<>();
                        for (DocumentSnapshot treinoSnapshot : treinos) {
                            listaDeTreinos.add(treinoSnapshot.getId());
                            String diaSemanaTreino = treinoSnapshot.getString("diaSemana");

                            assert diaSemanaTreino != null;
                            int diaSemanaTreinoNumero = converterDiaSemanaParaNumero(diaSemanaTreino);

                            if (diaSemanaTreinoNumero == diaAtual) {
                                // Treino correspondente ao dia atual
                                String nomeTreino = treinoSnapshot.getId();
                                treinoViewModel.setTreinoDoDia(nomeTreino);
                            }
                        }
                        treinoViewModel.setTreinos(listaDeTreinos);
                    })
                    .addOnFailureListener(e -> {
                        // Erro ao buscar os treinos
                        Log.e("Buscar Treinos", "Erro ao buscar treinos", e);
                    });
        }
    }
    private int converterDiaSemanaParaNumero(String diaSemana) {
        switch (diaSemana) {
            case "Sunday":
                return Calendar.SUNDAY;
            case "Monday":
                return Calendar.MONDAY;
            case "Tuesday":
                return Calendar.TUESDAY;
            case "Wednesday":
                return Calendar.WEDNESDAY;
            case "Thursday":
                return Calendar.THURSDAY;
            case "Friday":
                return Calendar.FRIDAY;
            case "Saturday":
                return Calendar.SATURDAY;
            default:
                return -1; // Dia desconhecido
        }
    }




}