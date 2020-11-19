package com.example.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoListActivity extends AppCompatActivity {

    EditText newTodoListName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add List");
        setSupportActionBar(toolbar);

        newTodoListName = findViewById(R.id.newTodoListName);

        findViewById(R.id.saveNewTodoList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewTodoList();
            }
        });

    }

    public void saveNewTodoList() {
        final String name = newTodoListName.getText().toString().trim();

        if (name.isEmpty()) {
            newTodoListName.setError("You must name your list");
            newTodoListName.requestFocus();
            return;
        }

        // Save TodoList on Another Thread

        class SaveTodoList extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                TodoList todoList = new TodoList();
                todoList.setName(name);

                DatabaseClient.getInstance(getApplicationContext()).getTodoListAppDatabase().todoListDao().insert(todoList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), TodoListActivity.class));
                //Toast.makeText(getApplicationContext(), "List Created", Toast.LENGTH_LONG).show();
            }
        }

        SaveTodoList save = new SaveTodoList();
        save.execute();
    }


}
