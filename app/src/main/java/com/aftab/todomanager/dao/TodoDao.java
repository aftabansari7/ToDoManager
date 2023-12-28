package com.aftab.todomanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aftab.todomanager.model.TodoEntity;

import java.util.List;

@androidx.room.Dao
public interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY completed")
    LiveData<List<TodoEntity>> getAllTodos();

    @Insert
    void insert(TodoEntity model);

    @Update
    void update(TodoEntity model);

    @Delete
    void delete(TodoEntity todo);


}
