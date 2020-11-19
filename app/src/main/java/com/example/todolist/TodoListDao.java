package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoListDao {

    @Query("SELECT * FROM todolist")
    List<TodoList> getAll();

    @Insert
    void insert(TodoList todoList);

    @Delete
    void delete(TodoList todoList);

    @Update
    void update(TodoList todoList);

    @Query("SELECT * FROM todolist WHERE id = :todoListId")
    TodoList getById(int todoListId);
}
