package com.example.todolist;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient databaseClientInstance;
    private static TodoListAppDatabase database;

    private DatabaseClient(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, TodoListAppDatabase.class, "TodoListAppDatabase").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (databaseClientInstance == null) {
            databaseClientInstance = new DatabaseClient(context);
        }
        return databaseClientInstance;
    }

    public TodoListAppDatabase getTodoListAppDatabase() {
        return database;
    }


}
