package com.example.todolist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TodoList.class, Task.class}, version = 1)
public abstract class TodoListAppDatabase extends RoomDatabase {
    public abstract TodoListDao todoListDao();
    public abstract TaskDao taskDao();
}