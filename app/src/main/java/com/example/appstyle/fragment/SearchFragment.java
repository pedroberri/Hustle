package com.example.appstyle.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appstyle.R;

public class SearchFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

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
}
