package com.example.btl_nhom2.search;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewAdapter;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.models.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SearchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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


    EditText editTextSearch;
    AppCompatButton btnSearch;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    List<Task> tasks;

    DBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editTextSearch = view.findViewById(R.id.edtTextSearch);
        btnSearch = view.findViewById((R.id.btnStartSearch));
        recyclerView = view.findViewById(R.id.search_result);
        dbHelper = new DBHelper(getContext());
        tasks = dbHelper.getAllTasks();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                performSearch();
            }
        });

        MainActivity mainActivity = (MainActivity) getActivity();
        // Create adapter passing in the sample user data
        adapter = new RecycleViewAdapter(tasks, mainActivity);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        view.setFocusableInTouchMode(true);
        view.requestFocus();

        ActivityMainBinding mainBinding = mainActivity.getMainBinding();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mainBinding.layoutNav.setVisibility(View.VISIBLE);
                mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
                mainBinding.addButton.setVisibility(View.VISIBLE);
                mainActivity.navController.navigate(R.id.homeFragment);

            }
        });


        return view;
    }

    private boolean isKeyboardOpen() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        return imm != null && imm.isAcceptingText();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
        }
    }

    private void performSearch() {
        String searchText = editTextSearch.getText().toString().trim();

        // Gọi hàm tìm kiếm dữ liệu từ cơ sở dữ liệu
        List<Task> searchResults = dbHelper.searchData(searchText);

        // Cập nhật RecyclerView với kết quả tìm kiếm
        adapter.setData(searchResults);
        adapter.notifyDataSetChanged();
    }

}