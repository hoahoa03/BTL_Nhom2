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


//        HomeFragment homeFrag = new HomeFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.container_main, homeFrag);
//        fragmentTransaction.commit();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);


        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                mainBinding.bottomNavigation.setVisibility(View.GONE);
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

//            else if (itemId == R.id.img_search) {
//                navigateToSearchFragmentWithoutBottomNavigation();
//                return false;
//
//            }

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

//    private void navigateToSearchFragmentWithoutBottomNavigation() {
//
//        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
//
//        // Ẩn Bottom Navigation khi chuyển đến màn hình "searchFragment"
//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if (destination.getId() == R.id.searchFragment) {
//                bottomNavigation.setVisibility(View.GONE); // Ẩn Bottom Navigation
//            } else {
//                bottomNavigation.setVisibility(View.VISIBLE); // Hiển thị Bottom Navigation cho các màn hình khác
//            }
//        });
//
//        // Điều hướng đến màn hình "searchFragment"
//        navController.navigate(R.id.action_homeFragment_to_searchFragment);
//    }



    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}