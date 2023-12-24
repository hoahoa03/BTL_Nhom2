package com.example.btl_nhom2.list;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl_nhom2.R;

public class ListWorkFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ListWorkFragment() {
        // Required empty public constructor
    }

    public static ListWorkFragment newInstance(String param1, String param2) {
        ListWorkFragment fragment = new ListWorkFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_work, container, false);



        // Chuyển đến HomeFragment
        view.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_homeFragment);
            }
        });

        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_homeFragment);
            }
        });



        // Chuyển đến NotificationFragment
        view.findViewById(R.id.nottification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_notificationFragment);
            }
        });

        // Chuyển đến SettingFragment
        view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_settingFragment);
            }
        });

        // Chuyển đến SearchFragment
        view.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_searchFragment);
            }
        });

        // Chuyển đến DetailsFragment
        view.findViewById(R.id.item_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_workDetailsFragment);
            }
        });

        // Chuyển đến AddFragment
//        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_listWorkFragment_to_addWorkFragment);
//            }
//        });
        return view;
    }
}