package com.example.btl_nhom2.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewFragment;
import com.example.btl_nhom2.add.AddWorkFragment;
import com.example.btl_nhom2.list.ListWorkFragment;
import com.example.btl_nhom2.models.Category;
import com.example.btl_nhom2.models.Task;
import com.example.btl_nhom2.search.SearchFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.container_main, new SearchFragment());
            transaction.addToBackStack(null);

            // Commit transaction
            transaction.commit();
        });

//        AppCompatImageButton addTaskButton = view.findViewById(R.id.add_button);
//        addTaskButton.setOnClickListener(v -> {
//            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//            transaction.add(R.id.container_main, new AddWorkFragment());
//            transaction.addToBackStack(null);
//
//            // Commit transaction
//            transaction.commit();
//        });

        // Chuyển đến ListWorkFragment
//        view.findViewById(R.id.list_work).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_listWorkFragment);
//            }
//        });

        // Chuyển đến NotificationFragment
//        view.findViewById(R.id.nottification).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_notificationFragment);
//            }
//        });

        // Chuyển đến SettingFragment
//        view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_settingFragment);
//            }
//        });

        // Chuyển đến AddFragment
//        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_addWorkFragment);
//            }
//        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Chuyển đến ListWorkFragment
//        Toast.makeText(getContext(), "Helee", Toast.LENGTH_SHORT).show();
//        NavigationView navigationView = view.findViewById(R.id.bottom_navigation);

        // Thiết lập OnClickListener cho list_work
//        MenuItem listWorkItem = navigationView.getMenu().findItem(R.id.list_work);
//        listWorkItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(getContext(), "Hưllo", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        view.findViewById(R.id.list_work).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//            transaction.add(R.id.container_main, new ListWorkFragment());
//            transaction.addToBackStack(null);
//
//            // Commit transaction
//            transaction.commit();
//            }
//        });
//        view.findViewById(R.id.list_work).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_listWorkFragment));

        // Chuyển đến NotificationFragment
//        view.findViewById(R.id.nottification).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_notificationFragment);
//            }
//        });

        // Chuyển đến SettingFragment
//        view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_settingFragment);
//            }
//        });
    }
}