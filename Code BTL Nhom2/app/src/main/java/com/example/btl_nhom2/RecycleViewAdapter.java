package com.example.btl_nhom2;


import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom2.databinding.ActivityMainBinding;
import com.example.btl_nhom2.models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class RecycleViewAdapter extends
        RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBoxItem;
        public TextView txtTitleItem, txtTimeItem;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxItem = itemView.findViewById(R.id.checkboxItem);
            txtTitleItem = itemView.findViewById(R.id.txtTitleItem);
            txtTimeItem = itemView.findViewById(R.id.txtTimeItem);
        }
    }

    private List<Task> taskList;

    public RecycleViewAdapter(List<Task> taskList, MainActivity mainActivity) {
        this.taskList = taskList;
        this.mainActivity = mainActivity;
    }
    Context context;
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.job_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }
    MainActivity mainActivity;
    public void setData(List<Task> newData) {
        this.taskList = newData;
    }
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder viewHolder, int position) {
        Task task = taskList.get(position);

        CheckBox checkBoxItem;
        TextView txtTitleItem, txtTimeItem;

        checkBoxItem = viewHolder.checkBoxItem;
        checkBoxItem.setChecked(task.getCategoryID() == 2);
        checkBoxItem.setOnClickListener(view -> {
            if (task.getCategoryID() ==2){
                int category = 0;

                Date currentDate = new Date();

                Date dateTimeStart, dateTimeEnd;
                try {
                    dateTimeStart = formatToFullDateTime(task.getStartDay(), task.getStartTime());
                    dateTimeEnd = formatToFullDateTime(task.getEndDay(), task.getEndTime());
                } catch (ParseException | java.text.ParseException e) {
                    throw new RuntimeException(e);
                }

                if (dateTimeStart.after(currentDate)){
                    category = 1;
                } else if (dateTimeEnd.before(currentDate)){
                    category = 3;
                } else {
                    category = 0;
                }

                DBHelper dbHelper = new DBHelper(context);
                dbHelper.updateTaskCompele(task.getID(),category);
                checkBoxItem.setChecked(false);
                task.setCategoryID(category);
                notifyDataSetChanged();
                dbHelper.close();
                Toast.makeText(context, "Set to uncompleted", Toast.LENGTH_SHORT).show();

            } else {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.updateTaskCompele(task.getID(),2);
                task.setCategoryID(2);
                taskList.remove(position);
                notifyItemRemoved(position);
                checkBoxItem.setChecked(false);
                notifyItemRangeChanged(position, taskList.size());
                notifyDataSetChanged();
                Toast.makeText(context, "Set to completed", Toast.LENGTH_SHORT).show();
            }

        });
//        checkBoxItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//
//                      } else {
//
//                        }
//            }
//        });

        txtTitleItem = viewHolder.txtTitleItem;
        txtTitleItem.setText(task.getTaskName());

        txtTimeItem = viewHolder.txtTimeItem;
        if (task.getCategoryID() == 0) {
            txtTimeItem.setText(task.getStringWorking());
        } else if (task.getCategoryID() == 1) {
            txtTimeItem.setText(task.getStringNewWork());
        } else if (task.getCategoryID() == 2) {
            txtTimeItem.setText("Completed");
        } else {
            txtTimeItem.setText("Late");
        }

        viewHolder.itemView.setOnLongClickListener(
                view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure to delete this task?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBHelper dbHelper = new DBHelper(context);
                                    dbHelper.deleteTask(task.getID());
                                    taskList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, taskList.size());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Deleted "+ viewHolder.txtTitleItem.getText(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }

        );

        viewHolder.itemView.setOnClickListener(view -> {

            //sửa
            //khai báo navigate
            NavController navController = mainActivity.getNavController();
            ActivityMainBinding mainBinding = mainActivity.getMainBinding();
            mainBinding.layoutNav.setVisibility(View.GONE);
            mainBinding.bottomNavigation.setVisibility(View.GONE);
            mainBinding.addButton.setVisibility(View.GONE);

            //chuẩn bị data sang màn detail
            Bundle bundle = new Bundle();
            bundle.putInt("taskId", task.getID());

            //navigate
            navController.popBackStack();
            navController.navigate(R.id.workDetailsFragment, bundle);
        });

    }

    public Date formatToFullDateTime(String textDate, String textTime) throws ParseException, java.text.ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm", Locale.ENGLISH);
        Date date = dateFormat.parse(textDate + " " + textTime);
        System.out.println("Formatted Date: " + date);
        return date;
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return taskList.size();
    }


}
