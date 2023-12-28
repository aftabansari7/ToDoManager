package com.aftab.todomanager.repo;

import android.content.Context;


import androidx.lifecycle.LiveData;

import com.aftab.todomanager.dao.TodoDao;
import com.aftab.todomanager.db.TodoDatabase;
import com.aftab.todomanager.model.TodoEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TodoRepo {

    private TodoDao todoDao;
    private LiveData<List<TodoEntity>> allTodos;

    private Executor executor = Executors.newSingleThreadExecutor();

    public TodoRepo(Context context) {
        TodoDatabase database = TodoDatabase.getInstance(context);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
    }

    public void insert(TodoEntity model) {
        CompletableFuture.runAsync(() -> todoDao.insert(model), executor);
    }

    public void update(TodoEntity todo) {
        CompletableFuture.supplyAsync(() -> {
            todoDao.update(todo);
            return todo;
        }, executor);
    }

    public void delete(TodoEntity todo) {
        CompletableFuture.runAsync(() -> todoDao.delete(todo), executor);
    }

    public LiveData<List<TodoEntity>> getAllTodos() {
        return allTodos;
    }

    public CompletableFuture<LiveData<List<TodoEntity>>> getAllTodosAsync() {
        return CompletableFuture.supplyAsync(() -> todoDao.getAllTodos(), executor);
    }
}
