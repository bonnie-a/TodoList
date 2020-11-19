package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNewTodoList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lists");
        setSupportActionBar(toolbar);

        buttonAddNewTodoList = findViewById(R.id.floating_button_new_todolist);

        recyclerView = findViewById(R.id.recyclerview_todolists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddNewTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TodoListActivity.this, AddTodoListActivity.class);
                startActivity(intent);
            }
        });

        getTodoLists();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getTodoLists();
    }

    private void getTodoLists() {
        class GetTodoLists extends AsyncTask<Void, Void, List<TodoList>> {

            @Override
            protected List<TodoList> doInBackground(Void... voids) {
                List<TodoList> todoLists =
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getTodoListAppDatabase()
                                .todoListDao()
                                .getAll();
                return todoLists;
            }

            @Override
            protected void onPostExecute(List<TodoList> todoLists) {
                super.onPostExecute(todoLists);
                TodoListAdapter todoListAdapter = new TodoListAdapter(TodoListActivity.this, todoLists, TodoListActivity.this);
                recyclerView.setAdapter(todoListAdapter);
            }
        }

        GetTodoLists getTodoLists = new GetTodoLists();
        getTodoLists.execute();
    }


}
