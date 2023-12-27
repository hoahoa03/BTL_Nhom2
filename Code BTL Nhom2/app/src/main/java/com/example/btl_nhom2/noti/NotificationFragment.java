package com.example.btl_nhom2.noti;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationFragment extends Fragment {

    AppCompatImageView btnSearch;

    AppCompatButton btnAbouttoExpire, btnLate;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        btnSearch = view.findViewById(R.id.img_search);
        btnAbouttoExpire = view.findViewById(R.id.btn_expire);
        btnLate = view.findViewById(R.id.btn_late);
        btnAbouttoExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnAbouttoExpire);
                setButtonUnselected(btnLate);
            }
        });

        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnLate);
                setButtonUnselected(btnAbouttoExpire);
            }
        });

        AppCompatImageView searchButton = view.findViewById(R.id.img_search);
        searchButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            MainActivity mainActivity = (MainActivity) requireActivity();
            ActivityMainBinding mainBinding = mainActivity.getMainBinding();
            mainBinding.layoutNav.setVisibility(View.GONE);
            mainBinding.bottomNavigation.setVisibility(View.GONE);
            mainBinding.addButton.setVisibility(View.GONE);
            transaction.add(R.id.container_main, new SearchFragment());
            transaction.addToBackStack(null);

            // Commit transaction
            transaction.commit();
        });
        return view;
    }

    private void setButtonSelected(AppCompatButton button) {
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#FEB36D"));
        button.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_search_button));
    }

    // Phương thức để đặt trạng thái không chọn cho nút và thay đổi màu
    private void setButtonUnselected(AppCompatButton button) {
        button.setTextColor(Color.parseColor("#8B8888"));
        button.setBackgroundColor(Color.parseColor("#EAE9E9"));
        button.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_phanloai));
    }

    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) requireActivity();
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        AppCompatImageButton btnAdd = mainActivity.findViewById((R.id.add_button));
        btnAdd.setVisibility(View.VISIBLE);
    }
}