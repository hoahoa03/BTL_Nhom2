package com.example.btl_nhom2;

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

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWorkFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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

    LinearLayout btnAddDeadline, layoutTime, startDay, endDay, startTime, endTime, btnAddWork;
    AppCompatImageView deleteButton;
    TextView txtNgayKetThuc, txtNgayBatDau, txtGioBatDau, txtGioKetThuc;

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

        return view;
    }

    private void initView(View view) {
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
        btnAddWork = view.findViewById(R.id.btn_addWork);
    }

    private void deleteThisFragment() {
        // Lấy ra FragmentManager của Activity
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        // Xoá Fragment hiện tại
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(this);
        transaction.commit();

        // Nếu bạn muốn quay lại Fragment trước đó sau khi xoá
        fragmentManager.popBackStack();
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
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE,d 'tháng' M 'năm' y", new Locale("vi"));
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
        if (view == deleteButton) {
            deleteThisFragment();
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
        if (validate()){
            showNameInputDialog();
        }
    }

    private void showNameInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_name_input, null);

        final EditText editTextName = view.findViewById(R.id.editTextName);

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String enteredName = editTextName.getText().toString();
                        Toast.makeText(getContext(), enteredName, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled the dialog
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean validate() throws ParseException {
        if (layoutTime.getVisibility() == View.VISIBLE) {
            Date dateStart = formatToDate(txtNgayBatDau.getText() + "");
            Date dateEnd = formatToDate(txtNgayKetThuc.getText() + "");

            if (dateStart.before(dateEnd)) {
                return true;
            } else if (dateStart.after(dateEnd)) {
                Toast.makeText(getContext(), "Thời điểm bắt đầu phải bé hơn thời điểm kết thúc", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                LocalTime timeStart = formatToTime(txtGioBatDau.getText() + "");
                LocalTime timeEnd = formatToTime(txtGioKetThuc.getText() + "");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (timeStart.isBefore(timeEnd)) {
                        return true;
                    } else if (dateStart.after(dateEnd)) {
                        Toast.makeText(getContext(), "Thời điểm bắt đầu phải bé hơn thời điểm kết thúc", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        Toast.makeText(getContext(), "Thời điểm bắt đầu phải bé hơn thời điểm kết thúc", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Date formatToDate(String text) throws ParseException {
        Pattern pattern = Pattern.compile("\\d+");

        // Tạo matcher từ inputString
        Matcher matcher = pattern.matcher(text);
        StringBuilder dateStringBuilder = new StringBuilder();

        // Duyệt qua tất cả các số và in ra
        while (matcher.find()) {
            dateStringBuilder.append(matcher.group()).append(" ");
        }
        String dateStr = dateStringBuilder.toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Date date = dateFormat.parse(dateStr);
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