package com.example.x.myroomdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private TaskDao taskDao;
    private RecyclerView tasksRecyclerView;
    private TasksAdapter tasksAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference
        tasksRecyclerView = findViewById(R.id.tasks_recyclerView);

        // setup tasks adapter
        tasksAdapter = new TasksAdapter();

        // get database and taskDao
        database = AppDatabase.getInstance(getApplicationContext());
        taskDao = database.taskDao();

        AppExecutors.getInstance().diskIO.execute(new Runnable() {
            @Override
            public void run() {
                taskList = taskDao.getAllTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tasksAdapter.setTaskList(taskList);
                    }
                });
            }
        });



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
