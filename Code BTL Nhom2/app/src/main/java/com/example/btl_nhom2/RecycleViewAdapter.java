package com.example.btl_nhom2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_nhom2.models.Task;

import java.util.List;


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

    public RecycleViewAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.job_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder viewHolder, int position) {

        Task task = taskList.get(position);

        CheckBox checkBoxItem;
        TextView txtTitleItem, txtTimeItem;

        checkBoxItem = viewHolder.checkBoxItem;

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

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return taskList.size();
    }


}
