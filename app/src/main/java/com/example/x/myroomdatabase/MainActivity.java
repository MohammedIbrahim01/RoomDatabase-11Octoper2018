package com.example.x.myroomdatabase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // TaskDao
    private TaskDao taskDao;

    // RecyclerView and its Adapter
    private RecyclerView tasksRecyclerView;
    private TasksAdapter tasksAdapter;

    // Floating action button
    private FloatingActionButton addFab;

    // Tasks to display in recyclerView
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views reference
        tasksRecyclerView = findViewById(R.id.tasks_recyclerView);
        addFab = findViewById(R.id.add_fab);

        // set onClickListeners
        addFab.setOnClickListener(this);

        // get adapter instance
        tasksAdapter = new TasksAdapter();
        retrieveTasks();


        // Setup recyclerView
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setHasFixedSize(true);
        tasksRecyclerView.setAdapter(tasksAdapter);

        // Setup swipe to delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                // Get the swiped task
                int swipedTaskPosition = viewHolder.getAdapterPosition();
                List<Task> taskList = tasksAdapter.getTaskList();
                Task taskToDelete = taskList.get(swipedTaskPosition);

                // delete the task from database
                deleteTaskFromDatabase(taskToDelete);

                retrieveTasks();
            }
        });

        // Connect itemTouchHelper to tasks recyclerView
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);
    }

    private void retrieveTasks() {
        // Go out mainThread
        AppExecutors.getInstance().diskIO.execute(new Runnable() {
            @Override
            public void run() {

                // Get tasks list from the database
                taskDao = AppDatabase.getInstance(getApplicationContext()).taskDao();
                taskList = taskDao.getAllTasks();

                // Go back to UiThread (MainThread)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // pass tasks list to adapter
                        tasksAdapter.setTaskList(taskList);
                        tasksAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void deleteTaskFromDatabase(final Task taskToDelete) {

        // Go out mainThread
        AppExecutors.getInstance().diskIO.execute(new Runnable() {
            @Override
            public void run() {

                taskDao.deleteTask(taskToDelete);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_fab:
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
                finish();
                break;
        }
    }
}
