package com.example.x.myroomdatabase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {

        Task task = taskList.get(position);

        holder.name.setText(task.getName());
        holder.day.setText(String.valueOf(task.getDay()));
    }

    @Override
    public int getItemCount() {
        if (taskList == null)
            return 0;
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, day;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_textView);
            day = itemView.findViewById(R.id.day_textView);
        }
    }
}
