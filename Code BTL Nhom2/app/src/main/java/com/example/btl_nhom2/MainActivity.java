package com.example.btl_nhom2;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public NavController navController;
    AppCompatImageButton btnAdd;
    public ActivityMainBinding mainBinding;

    private MenuItem homeItem;
    private MenuItem listWorkItem;
    private MenuItem notificationItem;
    private MenuItem aboutUsItem;

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
                item.setChecked(true);
                navController.popBackStack();
                navController.navigate(R.id.homeFragment);

                return false;
            } else if (itemId == R.id.list_work) {
                item.setChecked(true);
                navController.popBackStack();
                navController.navigate(R.id.listWorkFragment);

                return false;
            } else if (itemId == R.id.nottification) {
                item.setChecked(true);
                navController.popBackStack();
                navController.navigate(R.id.notificationFragment);

                return false;
            } else if (itemId == R.id.setting) {
                item.setChecked(true);
                navController.popBackStack();
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
                navController.popBackStack();
                navController.navigate(R.id.addWorkFragment);
            }
        });

        homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                homeItem.setIcon(R.drawable.home_white);
                listWorkItem.setIcon(R.drawable.list_work);
                notificationItem.setIcon(R.drawable.notification);
                aboutUsItem.setIcon(R.drawable.gioithieu);
                return true;
            }
        });

        listWorkItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                homeItem.setIcon(R.drawable.home_black);
                listWorkItem.setIcon(R.drawable.list_white);
                notificationItem.setIcon(R.drawable.notification);
                aboutUsItem.setIcon(R.drawable.gioithieu);
                return true;
            }
        });

        notificationItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                homeItem.setIcon(R.drawable.home_black);
                listWorkItem.setIcon(R.drawable.list_work);
                notificationItem.setIcon(R.drawable.alarm_white);
                aboutUsItem.setIcon(R.drawable.gioithieu);
                return true;
            }
        });

        aboutUsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                homeItem.setIcon(R.drawable.home_black);
                listWorkItem.setIcon(R.drawable.list_work);
                notificationItem.setIcon(R.drawable.notification);
                aboutUsItem.setIcon(R.drawable.about_white);
                return true;
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_bottom_bar, menu);
//
//        homeItem = menu.findItem(R.id.home);
//        listWorkItem = menu.findItem(R.id.list_work);
//        notificationItem = menu.findItem(R.id.nottification);
//        aboutUsItem = menu.findItem(R.id.setting);
//
//        return true;
//    }

//    /
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//
//        switch (itemId) {
//            case R.id.home:
//                // Xử lý khi nút "Home" được chọn
//                // Đặt màu trắng cho nút Home
//                homeItem.setIcon(R.drawable.home_white);
//                listWorkItem.setIcon(R.drawable.list_work);
//                notificationItem.setIcon(R.drawable.notification);
//                aboutUsItem.setIcon(R.drawable.gioithieu);
//                return true;
//
//            case R.id.list_work:
//                // Xử lý khi nút "List Work" được chọn
//                // Đặt màu trắng cho nút List Work
//                homeItem.setIcon(R.drawable.home_black);
//                listWorkItem.setIcon(R.drawable.list_white);
//                notificationItem.setIcon(R.drawable.notification);
//                aboutUsItem.setIcon(R.drawable.gioithieu);
//                return true;
//
//            case R.id.nottification:
//                // Xử lý khi nút "Notification" được chọn
//                // Đặt màu trắng cho nút Notification
//                homeItem.setIcon(R.drawable.home_black);
//                listWorkItem.setIcon(R.drawable.list_work);
//                notificationItem.setIcon(R.drawable.alarm_white);
//                aboutUsItem.setIcon(R.drawable.gioithieu);
//                return true;
//
//            case R.id.setting:
//                // Xử lý khi nút "About us" được chọn
//                // Đặt màu trắng cho nút About us
//                homeItem.setIcon(R.drawable.home_black);
//                listWorkItem.setIcon(R.drawable.list_work);
//                notificationItem.setIcon(R.drawable.notification);
//                aboutUsItem.setIcon(R.drawable.about_white);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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