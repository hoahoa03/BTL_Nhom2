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
import android.widget.LinearLayout;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewFragment;
import com.example.btl_nhom2.models.Task;
import com.example.btl_nhom2.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ListWorkFragment extends Fragment {

    AppCompatImageView btnSearch;
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
        btnSearch = view.findViewById(R.id.img_search);
        btnWorking = view.findViewById(R.id.btn_working);
        btnNewWork = view.findViewById(R.id.btn_new_work);
        btnComplete = view.findViewById(R.id.btn_complete);
        btnLate = view.findViewById(R.id.btn_late);
        linearLayoutList = view.findViewById(R.id.linearLayoutList);
    }

    LinearLayout linearLayoutList;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_work, container, false);

        initView(view);

        dbHelper = new DBHelper(getContext());

        List<Task> taskList = dbHelper.getAllTasks();


        // Gán sự kiện click cho các nút
        btnWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnWorking);
                setButtonUnselected(btnNewWork);
                setButtonUnselected(btnComplete);
                setButtonUnselected(btnLate);

                Boolean check = false;

                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getCategoryID() == 0) {
                        check = true;
                        break;
                    }
                }

                dbHelper.close();

                if (check) {

                    linearLayoutList.setVisibility(View.INVISIBLE);

                    FragmentTransaction transactionMain = getActivity().getSupportFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putInt("type_to_show", 0);

                    RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
                    recycleViewFragment.setArguments(bundle);

                    transactionMain.replace(R.id.linearLayoutList, recycleViewFragment);
                    // Commit transaction
                    transactionMain.commit();
                }
            }
        });

        btnNewWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnNewWork);
                setButtonUnselected(btnWorking);
                setButtonUnselected(btnComplete);
                setButtonUnselected(btnLate);

                Boolean check = false;


                for (int i = 0; i < taskList.size(); i++) {

                    if (taskList.get(i).getCategoryID() == 1) {
                        check = true;
                        break;
                    }
                }

                dbHelper.close();

                if (check) {
                    // linearLayoutList.setVisibility(View.INVISIBLE);

                    FragmentTransaction transactionMain = getActivity().getSupportFragmentManager().beginTransaction();


                    Bundle bundle = new Bundle();
                    bundle.putInt("type_to_show", 1);

                    RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
                    recycleViewFragment.setArguments(bundle);

                    transactionMain.replace(R.id.linearLayoutList, recycleViewFragment);
                    // Commit transaction
                    transactionMain.commit();
                }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnComplete);
                setButtonUnselected(btnWorking);
                setButtonUnselected(btnNewWork);
                setButtonUnselected(btnLate);

                Boolean check = false;

                List<Task> completedTasks = new ArrayList<>();

                dbHelper = new DBHelper(getContext());

                for (int i = 0; i < taskList.size(); i++) {

                    int priority = taskList.get(i).getPriority();
                    boolean isChecked = isCheckboxChecked(priority);

                    if (taskList.get(i).getCategoryID() == 2 && isChecked) {
                        completedTasks.add(taskList.get(i));
                        check = true;
                        break;
                    }
                }

                dbHelper.close();

                if (check && !completedTasks.isEmpty()) {
                    FragmentTransaction transactionMain = getActivity().getSupportFragmentManager().beginTransaction();


                    Bundle bundle = new Bundle();
                    bundle.putInt("type_to_show", 2);

                    bundle.putSerializable("completed_tasks", new ArrayList<>(completedTasks));

                    RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
                    recycleViewFragment.setArguments(bundle);

                    transactionMain.replace(R.id.linearLayoutList, recycleViewFragment);
                    transactionMain.commit();
                }
            }
        });

        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnLate);
                setButtonUnselected(btnWorking);
                setButtonUnselected(btnNewWork);
                setButtonUnselected(btnComplete);

                Boolean check = false;

                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getCategoryID() == 3) {
                        check = true;
                        break;
                    }
                }

                dbHelper.close();

                if (check) {

//                    linearLayoutList.setVisibility(View.INVISIBLE);

                    FragmentTransaction transactionMain = getChildFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putInt("type_to_show", 3);

                    RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
                    recycleViewFragment.setArguments(bundle);

                    transactionMain.replace(R.id.linearLayoutList, recycleViewFragment);
                    // Commit transaction
                    transactionMain.commit();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.add(R.id.container_main, new SearchFragment());
                transaction.addToBackStack(null);

                // Commit transaction
                transaction.commit();

            }
        });

//        AppCompatImageView searchButton = view.findViewById(R.id.img_search);
//        searchButton.setOnClickListener(v -> {
//            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//            transaction.add(R.id.container_main, new SearchFragment());
//            transaction.addToBackStack(null);
//
//            // Commit transaction
//            transaction.commit();
//        });

        return view;
    }

    public boolean isCheckboxChecked(int priority) {
        return priority == 1;
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