package com.aftab.todomanager.presenter;

import androidx.lifecycle.LiveData;

import com.aftab.todomanager.MainActivity;
import com.aftab.todomanager.model.TodoEntity;
import com.aftab.todomanager.repo.TodoRepo;

import java.util.List;

public class TodoPresenter {

    private final MainActivity mainActivity;
    private final TodoRepo todoRepo;

    public TodoPresenter(MainActivity mainActivity, TodoRepo todoRepo) {
        this.mainActivity = mainActivity;
        this.todoRepo = todoRepo;
    }

    public void addTodoItem(String title, String description) {
        TodoEntity todo = new TodoEntity(title, description);
        todoRepo.insert(todo);
        loadTodoItems();
        mainActivity.showAddSuccess();
    }

    public void loadTodoItems() {
        LiveData<List<TodoEntity>> todoEntityList = todoRepo.getAllTodos();

        // Update UI with the new data
        todoEntityList.observe(mainActivity, mainActivity::displayTodos);
    }

    public void updateTodo(TodoEntity todoEntity) {
        todoRepo.update(todoEntity);
        loadTodoItems();
        mainActivity.showUpdateSuccess();
    }

    public void deleteTodoItem(TodoEntity todo) {
        todoRepo.delete(todo);
        loadTodoItems();
        mainActivity.showDeleteSuccess();
    }

    public void editTodo(TodoEntity todo) {
        mainActivity.showEditDialog(todo);

    }
}

