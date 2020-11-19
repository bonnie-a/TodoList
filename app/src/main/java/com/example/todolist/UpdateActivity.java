package com.example.todolist;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText updateName;
    private int id;
    private String entityType;
    Button btnUpdateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Update");
        setSupportActionBar(toolbar);

        updateName = findViewById(R.id.updatedName);
        btnUpdateName = findViewById(R.id.btnUpdateName);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id");
        updateName.setText(bundle.getString("updateName"));
        entityType = bundle.getString("updateEntityType");

        btnUpdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entityType.equals("task")) {
                    updateTask();
                } else if (entityType.equals("todoList")) {
                    updateTodoList();
                }

            }
        });

    }

    public void updateTask() {

        final String name = updateName.getText().toString();

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Task updateTask = DatabaseClient.getInstance(getApplicationContext()).getTodoListAppDatabase().taskDao().getById(id);
                updateTask.setName(name);
                DatabaseClient.getInstance(getApplicationContext()).getTodoListAppDatabase().taskDao().update(updateTask);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                //Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
            }
        }

        UpdateTask update = new UpdateTask();
        update.execute();

    }

    public void updateTodoList() {

        final String name = updateName.getText().toString();

        class UpdateTodoList extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                TodoList updateTodoList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getTodoListAppDatabase()
                        .todoListDao()
                        .getById(id);

                updateTodoList.setName(name);
                DatabaseClient.getInstance(getApplicationContext()).getTodoListAppDatabase().todoListDao().update(updateTodoList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), "Todo List Updated", Toast.LENGTH_LONG).show();
            }
        }

        UpdateTodoList update = new UpdateTodoList();
        update.execute();

    }



}
