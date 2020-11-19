package com.example.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    EditText newTaskName;
    private int todoListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Task");
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        todoListId = bundle.getInt("todoListId");

        newTaskName = findViewById(R.id.newTask);

        findViewById(R.id.saveNewTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewTask();
            }
        });
    }

    public void saveNewTask() {
        final String name = newTaskName.getText().toString().trim();

        if (name.isEmpty()) {
            newTaskName.setError("You must name your task");
            newTaskName.requestFocus();
            return;
        }

        // Save Task on Another Thread

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Task task = new Task();
                task.setName(name);
                task.setIsChecked(false);
                task.setTodoListId(todoListId);

                DatabaseClient.getInstance(getApplicationContext()).getTodoListAppDatabase().taskDao().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                intent.putExtra("todoListId", todoListId);

                //startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask save = new SaveTask();
        save.execute();
        this.finish();
    }

}
