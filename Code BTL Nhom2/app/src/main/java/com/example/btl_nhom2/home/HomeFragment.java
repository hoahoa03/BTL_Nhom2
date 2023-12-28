package com.example.btl_nhom2.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewFragment;
import com.example.btl_nhom2.add.AddWorkFragment;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.list.ListWorkFragment;
import com.example.btl_nhom2.models.Category;
import com.example.btl_nhom2.models.Task;
import com.example.btl_nhom2.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DBHelper dbHelper;
    LinearLayout linearlayoutHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbHelper = new DBHelper(getContext());

        List<Task> taskList = dbHelper.getAllTasks();

        Boolean check = false;
        linearlayoutHome = view.findViewById(R.id.linearlayoutHome);

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getCategoryID() == 0) {
                check = true;
                break;
            }
        }

        dbHelper.close();

        if (check) {

            linearlayoutHome.setVisibility(View.INVISIBLE);

            FragmentTransaction transactionMain = getActivity().getSupportFragmentManager().beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putInt("type_to_show", 0);

            RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
            recycleViewFragment.setArguments(bundle);

            transactionMain.replace(R.id.tab_home, recycleViewFragment);
            // Commit transaction
            transactionMain.commit();
        }


        AppCompatImageView searchButton = view.findViewById(R.id.img_search);
        searchButton.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            ActivityMainBinding mainBinding = mainActivity.getMainBinding();
            mainBinding.layoutNav.setVisibility(View.GONE);
            mainBinding.bottomNavigation.setVisibility(View.GONE);
            mainBinding.addButton.setVisibility(View.GONE);
            mainActivity.navController.navigate(R.id.searchFragment);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {


            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}