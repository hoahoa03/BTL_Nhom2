package com.example.btl_nhom2.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.databinding.FragmentAddWorkBinding;
import com.example.btl_nhom2.models.Task;

import com.example.btl_nhom2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddWorkFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddWorkFragment() {
        // Required empty public constructor
    }

    public static AddWorkFragment newInstance(String param1, String param2) {
        AddWorkFragment fragment = new AddWorkFragment();
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        MainActivity mainActivity = (MainActivity) requireActivity();
//        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setVisibility(View.GONE);
//        AppCompatImageButton btnAdd = mainActivity.findViewById((R.id.add_button));
//        btnAdd.setVisibility(View.GONE);
//    }

    LinearLayout btnAddDeadline, layoutTime, startDay, endDay, startTime, endTime, btnAddWork;
    AppCompatImageView deleteButton;
    TextView txtNgayKetThuc, txtNgayBatDau, txtGioBatDau, txtGioKetThuc;
    EditText edtContent;
    DBHelper dbHelper;

    Date dateStart = null, dateEnd = null;
    LocalTime timeStart = null, timeEnd = null;
    NavController navController;
    ActivityMainBinding mainBinding;

    String textDateStart = "", textDateEnd = "", textTimeStart = "", textTimeEnd = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_work, container, false);

        initView(view);

        btnAddDeadline.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        startDay.setOnClickListener(this);
        endDay.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        btnAddWork.setOnClickListener(this);

        FragmentAddWorkBinding addWorkBinding = FragmentAddWorkBinding.inflate(getLayoutInflater());
        MainActivity mainActivity = (MainActivity) getActivity();
        mainBinding = mainActivity.getMainBinding();


        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();


//        addWorkBinding.imgBack.setOnClickListener(
//                view1 -> {
//                     navController.navigate(R.id.action_addWorkFragment_to_homeFragment);
//                }
//        );

        return view;
    }

    private void initView(View view) {
        dbHelper = new DBHelper(getContext());
        btnAddDeadline = view.findViewById(R.id.btn_addDeadline);
        layoutTime = view.findViewById(R.id.layoutTime);
        startDay = view.findViewById(R.id.ngayBatDau);
        endDay = view.findViewById(R.id.ngayKetThuc);
        startTime = view.findViewById(R.id.gioBatDau);
        endTime = view.findViewById(R.id.gioKetThuc);
        deleteButton = view.findViewById(R.id.img_back);
        txtNgayKetThuc = view.findViewById(R.id.txtNgayKetThuc);
        txtNgayBatDau = view.findViewById(R.id.txtNgayBatDau);
        txtGioBatDau = view.findViewById(R.id.txtGioBatDau);
        txtGioKetThuc = view.findViewById(R.id.txtGioKetThuc);
        edtContent = view.findViewById(R.id.editTextText);
        btnAddWork = view.findViewById(R.id.btn_addWork);
        Date currentDate = new Date();



        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        String formattedDate = sdf.format(currentDate);
        txtNgayBatDau.setText(formattedDate);
        txtNgayKetThuc.setText(formattedDate);

        sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String formattedTime = sdf.format(currentDate);
        txtGioBatDau.setText(formattedTime);
        txtGioKetThuc.setText(formattedTime);
    }


    private void showDatePickerDialog(TextView textView) {
        // Lấy ra ngày, tháng, năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                // Lấy thông tin về thứ, ngày, tháng và năm
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                // định dạng thành chuỗi
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
                String formattedDate = sdf.format(selectedDate.getTime());

                // Hiển thị thông tin ngày trên TextView
                textView.setText(formattedDate);
            }
        }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final TextView textView) {
        // Lấy ra giờ và phút hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Tạo TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                // Xử lý khi người dùng chọn giờ
                // Định dạng giờ thành chuỗi và hiển thị trên TextView
                String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                textView.setText(formattedTime);
            }
        }, hour, minute, DateFormat.is24HourFormat(requireContext()));

        // Hiển thị TimePickerDialog
        timePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddDeadline) {
            if (layoutTime.getVisibility() == View.GONE) {
                layoutTime.setVisibility(View.VISIBLE);
                btnAddDeadline.setVisibility(View.GONE);
            }
        }

        if (view == deleteButton){
            mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
            mainBinding.addButton.setVisibility(View.VISIBLE);
            mainBinding.layoutNav.setVisibility(View.VISIBLE);
            navController.navigate(R.id.action_addWorkFragment_to_homeFragment);
        }

        if (view == startDay) {
            showDatePickerDialog(txtNgayBatDau);
        }
        if (view == endDay) {
            showDatePickerDialog(txtNgayKetThuc);
        }
        if (view == startTime) {
            showTimePickerDialog(txtGioBatDau);
        }
        if (view == endTime) {
            showTimePickerDialog(txtGioKetThuc);
        }

        if (view == btnAddWork) {
            try {
                addWork();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void addWork() throws ParseException {
        if (validate()) {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure to add this task?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int category = 0;

                        Date currentDate = new Date();

                        Date dateTimeStart, dateTimeEnd;
                        try {
                            dateTimeStart = formatToFullDateTime(txtNgayBatDau.getText() + "", txtGioBatDau.getText()+"");
                            dateTimeEnd = formatToFullDateTime(txtNgayKetThuc.getText() + "", txtGioKetThuc.getText()+"");
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        if (dateTimeStart.after(currentDate)){
                            category = 1;
                        } else if (dateTimeEnd.before(currentDate)){
                            category = 3;
                        } else {
                            category = 0;
                        }

                        dbHelper.insertTask(edtContent.getText() + "",
                                0,
                                layoutTime.getVisibility() == View.GONE ? "" : txtNgayBatDau.getText() + "",
                                layoutTime.getVisibility() == View.GONE ? "" : txtGioBatDau.getText() + "",
                                layoutTime.getVisibility() == View.GONE ? "" : txtNgayKetThuc.getText() + "",
                                layoutTime.getVisibility() == View.GONE ? "" : txtGioKetThuc.getText() + "",
                                category);
                        List<Task> taskList = dbHelper.getAllTasks();
                        navController.navigate(R.id.action_addWorkFragment_to_homeFragment);
                        mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
                        mainBinding.addButton.setVisibility(View.VISIBLE);
                        mainBinding.layoutNav.setVisibility(View.VISIBLE);
//                        navController.clearBackStack();


                        Toast.makeText(getContext(), "Add successful", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean validate() throws ParseException {
        if (layoutTime.getVisibility() == View.VISIBLE) {
            dateStart = formatToDate(txtNgayBatDau.getText() + "");
            dateEnd = formatToDate(txtNgayKetThuc.getText() + "");

            if (dateStart.before(dateEnd)) {
                return true;
            } else if (dateStart.after(dateEnd)) {
                Toast.makeText(getContext(), "The starting time must be less than the ending time!", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                timeStart = formatToTime(txtGioBatDau.getText() + "");
                timeEnd = formatToTime(txtGioKetThuc.getText() + "");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (timeStart.isBefore(timeEnd)) {
                        return true;
                    } else if (dateStart.after(dateEnd)) {
                        Toast.makeText(getContext(), "The starting time must be less than the ending time!", Toast.LENGTH_SHORT).show();

                        return false;
                    } else {
                        Toast.makeText(getContext(), "The starting time must be less than the ending time!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        } else {
            Toast.makeText(getContext(), "You must enter a deadline for the task!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Date formatToDate(String text) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        Date date = dateFormat.parse(text);
        System.out.println("Formatted Date: " + date);
        return date;
    }

    public Date formatToFullDateTime(String textDate, String textTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm", Locale.ENGLISH);
        Date date = dateFormat.parse(textDate + " " + textTime);
        System.out.println("Formatted Date: " + date);
        return date;
    }

    public LocalTime formatToTime(String text) {
        DateTimeFormatter timeFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        }

        // Chuyển đổi chuỗi thời gian thành LocalTime
        LocalTime localTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localTime = LocalTime.parse(text, timeFormatter);
        }

        // In LocalTime
        System.out.println("Formatted Time: " + localTime);

        return localTime;
    }

}