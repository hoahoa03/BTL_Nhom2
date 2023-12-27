package com.example.btl_nhom2.noti;

import android.graphics.Color;
import android.os.Bundle;

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
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.RecycleViewFragment;
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

        btnAboutToExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(btnAboutToExpire);
                setButtonUnselected(btnLateNoti);

                List<Task> filteredTasks = new ArrayList<>();

                // Lọc các công việc có endDay là hôm nay

                String todayDateString = "27/12/2023"; // Ngày hôm nay dưới dạng chuỗi
                String endDayDateString = "27/12/2023"; // Ngày cần so sánh dưới dạng chuỗi

                Date today = parseDateString(todayDateString);
                Date endDay = parseDateString(endDayDateString);

                if (today != null && endDay != null) {
                    boolean isSameDay = isSameDay(today, endDay);
                    if (isSameDay) {
                        // Các ngày giống nhau
                    } else {
                        // Các ngày khác nhau
                    }
                }

                for (int i = 0; i < taskList.size(); i++) {
                    Task task = taskList.get(i);
                    if (endDay != null && isSameDay(today, endDay)) {
                        filteredTasks.add(task);
                    }
                }

                if (!filteredTasks.isEmpty()) {
                    // Hiển thị danh sách công việc có endDay là hôm nay
                    FragmentTransaction transactionMain = getChildFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putInt("type_to_show", 4);
                    bundle.putSerializable("filtered_tasks", new ArrayList<>(filteredTasks));

                    RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
                    recycleViewFragment.setArguments(bundle);

                    transactionMain.replace(R.id.linearLayoutNoti, recycleViewFragment);
                    transactionMain.commit();
                } else {
                    // Hiển thị thông báo khi không có công việc nào có endDay là hôm nay
                    Toast.makeText(getActivity(), "Không có công việc nào sắp hết hạn hôm nay.", Toast.LENGTH_SHORT).show();
                }
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

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);
        int month1 = cal1.get(Calendar.MONTH);
        int year1 = cal1.get(Calendar.YEAR);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        int year2= cal2.get(Calendar.YEAR);

        return day1 == day2 && month1 == month2 && year1 == year2;
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