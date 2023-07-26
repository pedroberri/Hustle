package com.example.appstyle.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstyle.LoginPage;
import com.example.appstyle.R;
import com.example.appstyle.api.ExerciseApiService;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private ImageView logout_button;
    private TextView weekDayTextView, dateTextView;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        InicializarCampos(rootView);
        Calendar calendar = Calendar.getInstance();
        String weekDay = new SimpleDateFormat("EEEE", Locale.US).format(calendar.getTime());

        // Formatar o dia com "st", "nd", "rd" ou "th"
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayWithSuffix = getDayWithSuffix(day);

        // Formatar o mês
        String month = new SimpleDateFormat("MMMM", Locale.US).format(calendar.getTime());

        // Atualizar os TextViews
        weekDayTextView.setText(weekDay);
        dateTextView.setText(dayWithSuffix + " " + month);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
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
        weekDayTextView = rootView.findViewById(R.id.weekDay);
        dateTextView = rootView.findViewById(R.id.date);
    }

    private String getDayWithSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return day + "th";
        }
        switch (day % 10) {
            case 1:
                return day + "st";
            case 2:
                return day + "nd";
            case 3:
                return day + "rd";
            default:
                return day + "th";
        }
    }

}