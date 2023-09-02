package com.example.appstyle.fragment;

import android.content.Intent;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.inputmethod.EditorInfo;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appstyle.OpenSearchActivity;
import com.example.appstyle.SearchNameActivity;
import com.example.appstyle.adapter.PesquisaAdapter;
import com.example.appstyle.api.StringCallback;
import com.example.appstyle.api.ExerciseApiService;
import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.example.appstyle.decorator.SpaceItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private TextView searchInput;
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

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(v.getContext(), SearchNameActivity.class);
                intent.putExtra("text", searchInput.getText());
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Pesquisa
        PesquisaAdapter pesquisaAdapter = new PesquisaAdapter(itemList);
        recyclerView.setAdapter(pesquisaAdapter);

        // Aplicar o SpaceItemDecoration
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        return rootView;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginPage.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void InicializarCampos(View rootView) {
        searchInput = rootView.findViewById(R.id.searchInput);
        logout_button = rootView.findViewById(R.id.logout);
        recyclerView = rootView.findViewById(R.id.recyclerViewSearch);
    }

}
