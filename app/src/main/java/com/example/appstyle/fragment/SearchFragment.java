package com.example.appstyle.fragment;

import android.content.Intent;

import android.annotation.SuppressLint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.google.firebase.auth.FirebaseAuth;

public class SearchFragment extends Fragment {


    private ImageView logout_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        logout_button = rootView.findViewById(R.id.logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        LinearLayout containerLayout = rootView.findViewById(R.id.div);

        // Define o número de views que você deseja criar
        int numberOfViews = 5;

        for (int i = 0; i < numberOfViews; i++) {
            // Crie uma nova TextView programaticamente
            TextView textView = new TextView(requireContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText("Texto da view " + (i + 1));
            textView.setTextSize(16); // Ajuste o tamanho do texto conforme necessário

            // Adicione a TextView ao contêiner
            containerLayout.addView(textView);
        }
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
    }


}
