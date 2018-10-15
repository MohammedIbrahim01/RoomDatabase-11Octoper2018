package com.example.x.myroomdatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    private EditText nameEditText, dayEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        nameEditText = findViewById(R.id.title_editText);
        dayEditText = findViewById(R.id.day_editText);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewTask();
                startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveNewTask() {

        String title = nameEditText.getText().toString();
        int day = Integer.parseInt(dayEditText.getText().toString());

        final Task task = new Task(title, day);

        AppExecutors.getInstance().diskIO.execute(new Runnable() {
            @Override
            public void run() {

                AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
            }
        });
    }
}
