package com.example.appstyle.fragment;

import android.content.Intent;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appstyle.adapter.PesquisaAdapter;
import com.example.appstyle.api.StringCallback;
import com.example.appstyle.api.ExerciseApiService;
import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private ImageView logout_button;
    private RecyclerView recyclerView;
    private List<String> itemList = new ArrayList<>(Arrays.asList(
            "Back", "Cardio", "Chest", "Lower arms", "Lower legs",
            "Neck", "Shoulders", "Upper arms", "Upper legs", "Waist"
    ));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        InicializarCampos(rootView);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Pesquisa
        PesquisaAdapter pesquisaAdapter = new PesquisaAdapter(itemList);
        recyclerView.setAdapter(pesquisaAdapter);

        return rootView;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void InicializarCampos(View rootView) {
        logout_button = rootView.findViewById(R.id.logout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

}
