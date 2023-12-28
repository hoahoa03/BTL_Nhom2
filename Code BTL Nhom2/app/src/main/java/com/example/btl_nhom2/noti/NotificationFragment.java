package com.example.btl_nhom2.noti;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewFragment;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.models.Task;
import com.example.btl_nhom2.search.SearchFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationFragment extends Fragment {

    AppCompatImageView btnSearch;

    AppCompatButton btnAboutToExpire, btnLateNoti;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    private DBHelper dbHelper;
    LinearLayout linearLayoutNoti;
    private List<Task> taskList = new ArrayList<>();

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

        dbHelper = new DBHelper(getContext());

        List<Task> taskList = dbHelper.getAllTasks();

        btnSearch = view.findViewById(R.id.img_search);
        btnLateNoti = view.findViewById(R.id.btn_late);
        btnAboutToExpire = view.findViewById(R.id.btn_expire);
        linearLayoutNoti = view.findViewById(R.id.linearLayoutNoti);
        init(taskList);

        btnAboutToExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               init(taskList);

            }
        });

        btnLateNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnLateNoti);
                setButtonUnselected(btnAboutToExpire);

                Boolean check = false;
                linearLayoutNoti = view.findViewById(R.id.linearLayoutNoti);

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

                    transactionMain.replace(R.id.linearLayoutNoti, recycleViewFragment);
                    // Commit transaction
                    transactionMain.commit();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity mainActivity = (MainActivity) requireActivity();
                ActivityMainBinding mainBinding = mainActivity.getMainBinding();
                mainBinding.layoutNav.setVisibility(View.GONE);
                mainBinding.bottomNavigation.setVisibility(View.GONE);
                mainBinding.addButton.setVisibility(View.GONE);
                mainActivity.navController.popBackStack();
                mainBinding.bottomNavigation.setOnClickListener(view1 -> {
                    view1.findViewById(R.id.home);

                });
                mainActivity.navController.navigate(R.id.searchFragment);

            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {


            }
        });


        return view;
    }

    private void init(List<Task> taskList) {
        setButtonSelected(btnAboutToExpire);
        setButtonUnselected(btnLateNoti);

        List<Task> filteredTasks = new ArrayList<>();

        boolean check = false;

        for (int i=0; i<taskList.size(); i++){
            if (taskList.get(i).getCategoryID() == 0 && isSameDay(formatToDate(taskList.get(i).getEndDay()), new Date())){
                check = true;
                break;
            }

        }

        if (!check){
            Toast.makeText(getActivity(), "Không có công việc nào sắp hết hạn hôm nay.", Toast.LENGTH_SHORT).show();
        } else {
            FragmentTransaction transactionMain = getChildFragmentManager().beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putInt("type_to_show", 4);
            bundle.putSerializable("filtered_tasks", new ArrayList<>(filteredTasks));

            RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
            recycleViewFragment.setArguments(bundle);

            transactionMain.replace(R.id.linearLayoutNoti, recycleViewFragment);
            transactionMain.commit();
        }

    }

    public Date formatToDate(String text) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);

        Date date = null;
        try {
            date = outputFormat.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    private static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        return dayFormat.format(date1).equals(dayFormat.format(date2));
    }

    private Date parseDateString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
}