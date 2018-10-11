package com.example.x.myroomdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private TaskDao taskDao;
    private RecyclerView tasksRecyclerView;
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference
        tasksRecyclerView = findViewById(R.id.tasks_recyclerView);

        // get database and taskDao
        database = AppDatabase.getInstance(getApplicationContext());
        taskDao = database.taskDao();

        List<Task> taskList = taskDao.getAllTasks();

        // setup tasks adapter
        tasksAdapter = new TasksAdapter();

        tasksAdapter.setTaskList(taskList);

        // setup recyclerView
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setHasFixedSize(true);
        tasksRecyclerView.setAdapter(tasksAdapter);


//        Task taskToDelete = taskList.get(5);
//
//        taskDao.deleteTask(taskToDelete);

//        for (int i = 0; i < taskList.size(); i++) {
//            Task task = taskList.get(i);
//            Log.i("WWW", "name: " + task.getName() + " , day: " + task.getDay());
//        }
    }
}
