package com.example.btl_nhom2.list;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListWorkFragment extends Fragment {

    AppCompatImageView btnBack, btnSearch;

    AppCompatButton btnWorking, btnNewWork, btnComplete, btnLate;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListWorkFragment() {
        // Required empty public constructor
    }

    private void initView(View view)
    {
        btnBack = view.findViewById(R.id.img_back);
        btnSearch = view.findViewById(R.id.img_search);
        btnWorking = view.findViewById(R.id.btn_working);
        btnNewWork = view.findViewById(R.id.btn_new_work);
        btnComplete = view.findViewById(R.id.btn_complete);
        btnLate = view.findViewById(R.id.btn_late);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_list_work, container, false);

        initView(view);

        // Gán sự kiện click cho các nút
        btnWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnWorking);
                setButtonUnselected(btnNewWork);
                setButtonUnselected(btnComplete);
                setButtonUnselected(btnLate);
            }
        });

        btnNewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnNewWork);
                setButtonUnselected(btnWorking);
                setButtonUnselected(btnComplete);
                setButtonUnselected(btnLate);
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnComplete);
                setButtonUnselected(btnWorking);
                setButtonUnselected(btnNewWork);
                setButtonUnselected(btnLate);
            }
        });

        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnLate);
                setButtonUnselected(btnWorking);
                setButtonUnselected(btnNewWork);
                setButtonUnselected(btnComplete);
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

    // Phương thức để đặt trạng thái chọn cho nút và thay đổi màu
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
//        MainActivity mainActivity = (MainActivity) requireActivity();
//        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setVisibility(View.VISIBLE);
//        AppCompatImageButton btnAdd = mainActivity.findViewById((R.id.add_button));
//        btnAdd.setVisibility(View.VISIBLE);
    }
}