package com.example.btl_nhom2.details;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.databinding.FragmentAddWorkBinding;
import com.example.btl_nhom2.databinding.FragmentWorkDetailsBinding;

public class WorkDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WorkDetailsFragment() {
        // Required empty public constructor
    }
    
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        View view = inflater.inflate(R.layout.fragment_work_details, container, false);

        FragmentWorkDetailsBinding workDetailsBinding = FragmentWorkDetailsBinding.inflate(getLayoutInflater());

        MainActivity mainActivity = (MainActivity) getActivity();
        ActivityMainBinding mainBinding = mainActivity.getMainBinding();

        AppCompatImageView imgBack = view.findViewById(R.id.img_back);

        imgBack.setOnClickListener(view1 -> {
            mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
            mainBinding.addButton.setVisibility(View.VISIBLE);
            mainBinding.layoutNav.setVisibility(View.VISIBLE);
            mainActivity.navController.navigate(R.id.homeFragment);
        });
        return view;
    }
}