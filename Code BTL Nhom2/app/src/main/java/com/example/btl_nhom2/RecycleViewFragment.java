package com.example.btl_nhom2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl_nhom2.models.Task;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewFragment extends Fragment {
    List<Task> tasks;

    DBHelper dbHelper ;
    int type;

    public static RecycleViewFragment newInstance(String param1, String param2) {
        RecycleViewFragment fragment = new RecycleViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type_to_show");
        }
    }
    RecycleViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        RecyclerView rvTasks = (RecyclerView) view.findViewById(R.id.rvFragment);

        dbHelper = new DBHelper(getContext());

        // Initialize contacts
        tasks = dbHelper.getAllTasks();

        for (int i=0; i<tasks.size(); i++){
            if (tasks.get(i).getCategoryID() != type && type != 4){
                tasks.remove(i);
                i--;
            }
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        // Create adapter passing in the sample user data
        adapter = new RecycleViewAdapter(tasks, mainActivity);
        // Attach the adapter to the recyclerview to populate items
        rvTasks.setAdapter(adapter);
        // Set layout manager to position the items
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!
        return view;
    }


}