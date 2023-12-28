package com.example.btl_nhom2.list;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewFragment;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.models.Task;
import com.example.btl_nhom2.search.SearchFragment;

import java.util.List;

public class ListWorkFragment extends Fragment {

    AppCompatImageView btnSearch;
    AppCompatButton btnWorking, btnNewWork, btnComplete, btnLate;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LinearLayout linearLayoutList;
    DBHelper dbHelper;


    public ListWorkFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        btnSearch = view.findViewById(R.id.img_search);
        btnWorking = view.findViewById(R.id.btn_working);
        btnNewWork = view.findViewById(R.id.btn_new_work);
        btnComplete = view.findViewById(R.id.btn_complete);
        btnLate = view.findViewById(R.id.btn_late);
        linearLayoutList = view.findViewById(R.id.linearLayoutList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_work, container, false);

        initView(view);

        dbHelper = new DBHelper(getContext());

        List<Task> taskList = dbHelper.getAllTasks();
        working(taskList);


        // Gán sự kiện click cho các nút
        btnWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                working(taskList);
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


                for (int i = 0; i < taskList.size(); i++) {

                    if (taskList.get(i).getCategoryID() == 2) {
                        check = true;
                        break;
                    }
                }

                dbHelper.close();

                if (check) {

                    FragmentTransaction transactionMain = getActivity().getSupportFragmentManager().beginTransaction();


                    Bundle bundle = new Bundle();
                    bundle.putInt("type_to_show", 2);

                    RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
                    recycleViewFragment.setArguments(bundle);

                    transactionMain.replace(R.id.linearLayoutList, recycleViewFragment);
                    // Commit transaction
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
                    // linearLayoutList.setVisibility(View.INVISIBLE);

                    FragmentTransaction transactionMain = getActivity().getSupportFragmentManager().beginTransaction();


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
        MainActivity mainActivity = (MainActivity) getActivity();
        ActivityMainBinding mainBinding = mainActivity.getMainBinding();
        mainBinding.layoutNav.setVisibility(View.VISIBLE);
        mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
        mainBinding.addButton.setVisibility(View.VISIBLE);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

            }
        });


        return view;
    }

    private void working(List<Task> taskList) {
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
    }
}