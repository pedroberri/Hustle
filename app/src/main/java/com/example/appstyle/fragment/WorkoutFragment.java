package com.example.appstyle.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.example.appstyle.WorkoutActivity;
import com.example.appstyle.WorkoutRegisterActivity;
import com.example.appstyle.adapter.TreinoAdapter;
import com.example.appstyle.decorator.SpaceItemDecoration;
import com.example.appstyle.fragment.model.TreinoViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {

    private ImageView logout_button;
    private Button workOut_button;

    private RecyclerView recyclerView;
    private TreinoViewModel treinoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);
        buscarTreinoDoDia();
        InicializarCampos(rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        treinoViewModel = new ViewModelProvider(requireActivity()).get(TreinoViewModel.class);
        TreinoAdapter adapter = new TreinoAdapter();

        adapter.setOnItemClickListener(new TreinoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String nomeTreino) {
                Intent intent = new Intent(requireContext(), WorkoutActivity.class);
                intent.putExtra("treino", nomeTreino);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        // Aplicar o SpaceItemDecoration
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // Observar os treinos da ViewModel
        treinoViewModel.getTreinos().observe(getViewLifecycleOwner(), adapter::setTreinos);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        workOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkoutRegisterActivity.class);
                startActivity(intent);
            }
        });

        Log.e("teste", treinoViewModel.getTreinos().getValue().toString());
        return rootView;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
        requireActivity().finish();
    }


    private void InicializarCampos(View rootView) {
        logout_button = rootView.findViewById(R.id.logout);
        workOut_button = rootView.findViewById(R.id.workout);
        recyclerView = rootView.findViewById(R.id.recyclerViewTreinos);
    }

    private void buscarTreinoDoDia() {
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