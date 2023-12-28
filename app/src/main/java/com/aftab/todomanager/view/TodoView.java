package com.aftab.todomanager.view;

import com.aftab.todomanager.model.TodoEntity;

import java.util.List;

public interface TodoView {


    void displayTodos(List<TodoEntity> todoEntityList);

    void showAddSuccess();

    void showAddError();

    void showUpdateSuccess();

    void showDeleteSuccess();

    void showEditDialog(TodoEntity todoEntity);

}
