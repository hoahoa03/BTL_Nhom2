package com.example.btl_nhom2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public NavController navController;
    AppCompatImageButton btnAdd;
    public ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);


        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
//                mainBinding.bottomNavigation.setVisibility(View.GONE);
                navController.navigate(R.id.homeFragment);
                return false;
            } else if (itemId == R.id.list_work) {
                navController.navigate(R.id.listWorkFragment);
                return false;
            } else if (itemId == R.id.nottification) {
                navController.navigate(R.id.notificationFragment);
                return false;
            } else if (itemId == R.id.setting) {
                navController.navigate(R.id.settingFragment);
                return false;

            }
            else {
                bottomNavigationView.setVisibility(View.GONE);
                return true;
            }


        });

        btnAdd = findViewById(R.id.add_button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ẩn nav
                mainBinding.layoutNav.setVisibility(View.GONE);
                mainBinding.bottomNavigation.setVisibility(View.GONE);
                // Ẩn nút Add
                btnAdd.setVisibility(View.GONE);
                navController.navigate(R.id.addWorkFragment);
            }
        });



    }

    public ActivityMainBinding getMainBinding() {
        return mainBinding;
    }
    public NavController getNavController() {
        return navController;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}