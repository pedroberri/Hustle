package com.example.appstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appstyle.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

import com.example.appstyle.fragment.HomeFragment;
import com.example.appstyle.fragment.SearchFragment;
import com.example.appstyle.fragment.WorkoutFragment;

public class HomeActivity extends AppCompatActivity {


    private ImageView logout_button;
    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        replaceFragment(new HomeFragment());
//        InicializarCampos();

//        logout_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }
//        });

        binding.navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.workout:
                    replaceFragment(new WorkoutFragment());
                    break;
            }
            return true;
        });
    }

//    private void InicializarCampos() {
//        logout_button = findViewById(R.id.logout);
//    }

//    private void logout() {
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(HomeActivity.this, LoginPage.class);
//        startActivity(intent);
//        finish();
//    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}