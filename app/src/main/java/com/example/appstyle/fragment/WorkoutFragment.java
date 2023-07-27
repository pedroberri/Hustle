package com.example.appstyle.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.example.appstyle.WorkoutRegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class WorkoutFragment extends Fragment {

    private ImageView logout_button;
    private Button workOut_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);
        InicializarCampos(rootView);

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
    }
}