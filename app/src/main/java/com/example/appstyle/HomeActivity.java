package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstyle.databinding.ActivityHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import com.example.appstyle.fragment.HomeFragment;
import com.example.appstyle.fragment.SearchFragment;
import com.example.appstyle.fragment.WorkoutFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {


    private ImageView logout_button;
    ActivityHomeBinding binding;


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

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }




}