package com.example.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNewTask;

    private RecyclerView recyclerView;

    private int todoListId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView title = findViewById(R.id.textView);

        Bundle listName = getIntent().getExtras();
        String name = listName.getString("todoListName");
        title.setText(String.valueOf(name));

        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        todoListId = bundle.getInt("todoListId");

        buttonAddNewTask = findViewById(R.id.floating_button_new_task);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
                intent.putExtra("todoListId", todoListId);
                startActivity(intent);
            }
        });

        //Log.i("TODOLIST ID", String.valueOf(todoListId));

        getTasks(todoListId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTasks(todoListId);
    }

    private void getTasks(final int todoListId) {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks =
                        DatabaseClient
                                .getInstance(getApplicationContext())
                                .getTodoListAppDatabase()
                                .taskDao()
                                .getAllByTodoList(todoListId);
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TaskListAdapter taskListAdapter = new TaskListAdapter(TaskActivity.this, tasks, TaskActivity.this);
                recyclerView.setAdapter(taskListAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        GetTasks getTasks = new GetTasks();
        getTasks.execute();
    }
}
