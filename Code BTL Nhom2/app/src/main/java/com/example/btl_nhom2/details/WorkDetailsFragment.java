package com.example.btl_nhom2.details;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.btl_nhom2.DBHelper;
import com.example.btl_nhom2.MainActivity;
import com.example.btl_nhom2.R;
import com.example.btl_nhom2.TaskViewModel;
import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.databinding.FragmentAddWorkBinding;
import com.example.btl_nhom2.databinding.FragmentWorkDetailsBinding;
import com.example.btl_nhom2.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WorkDetailsFragment() {
        // Required empty public constructor
    }

    MainActivity mainActivity;
    TextView ngayHetHan, thoiGianNhac;
    AppCompatButton btn_cap_nhat;
    Task task;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            int taskId = bundle.getInt("taskId"); // defaultValue là giá trị mặc định nếu key không tồn tại
            DBHelper db = new DBHelper(getContext());
            task = db.getTaskById(taskId + "");

        }

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_work_details, container, false);
        initView(view);
        FragmentWorkDetailsBinding workDetailsBinding = FragmentWorkDetailsBinding.inflate(getLayoutInflater());

        MainActivity mainActivity = (MainActivity) getActivity();
        ActivityMainBinding mainBinding = mainActivity.getMainBinding();

        AppCompatImageView imgBack = view.findViewById(R.id.img_back);

        imgBack.setOnClickListener(view1 -> {
            mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
            mainBinding.addButton.setVisibility(View.VISIBLE);
            mainBinding.layoutNav.setVisibility(View.VISIBLE);
            mainActivity.navController.navigate(R.id.homeFragment);
        });

        ngayHetHan = view.findViewById(R.id.ngayHetHan);
        thoiGianNhac = view.findViewById(R.id.thoiGianNhac);
        btn_cap_nhat = view.findViewById(R.id.btn_cap_nhat);

        ngayHetHan.setOnClickListener(view1 -> {
            showDatePickerDialog(ngayHetHan);
        });
        thoiGianNhac.setOnClickListener(view1 -> {
            showTimePickerDialog(thoiGianNhac);
        });

        btn_cap_nhat.setOnClickListener(view1 -> {

            DBHelper dbHelper = new DBHelper(getContext());

            int category = 0;

            Date currentDate = new Date();

            Date dateTimeEnd;
            try {
                dateTimeEnd = formatToFullDateTime(ngayHetHan.getText() + "", thoiGianNhac.getText()+"");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (dateTimeEnd.before(currentDate)){
                category = 3;
            } else {
                category = 0;
            }

            dbHelper.updateTask(task.getID(), formatToDate(ngayHetHan.getText() + ""), thoiGianNhac.getText() + "", category);

            mainBinding.bottomNavigation.setVisibility(View.VISIBLE);
            mainBinding.addButton.setVisibility(View.VISIBLE);
            mainBinding.layoutNav.setVisibility(View.VISIBLE);
            mainActivity.navController.navigate(R.id.homeFragment);
            TaskViewModel taskViewModel = new TaskViewModel();
            taskViewModel.notifyDataChanged();
            dbHelper.close();
            Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    public Date formatToFullDateTime(String textDate, String textTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = dateFormat.parse(textDate + " " + textTime);
        System.out.println("Formatted Date: " + date);
        return date;
    }


    public String formatToDate(String text) {

        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);

        // Định dạng ngày đích
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = inputFormat.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String outputDateString = outputFormat.format(date);
        System.out.println("Formatted Date: " + date);
        return outputDateString;
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
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
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


    private void initView(View view) {
        TextView textTest = view.findViewById(R.id.idTest);
        textTest.setText(task.getTaskName());
    }


}