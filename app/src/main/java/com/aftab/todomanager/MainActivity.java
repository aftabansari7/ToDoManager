package com.aftab.todomanager;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.aftab.todomanager.model.TodoEntity;
import com.aftab.todomanager.presenter.TodoPresenter;
import com.aftab.todomanager.repo.TodoRepo;
import com.aftab.todomanager.view.TodoAdapter;
import com.aftab.todomanager.view.TodoView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoView {

    private TodoPresenter presenter;
    private TodoAdapter todoAdapter;

    private EditText titleEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        Button addButton = findViewById(R.id.addButton);
        ListView todoListView = findViewById(R.id.todoListView);

        TodoRepo todoRepo = new TodoRepo(this);
        presenter = new TodoPresenter(this, todoRepo);

        addButton.setOnClickListener((View v) -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            presenter.addTodoItem(title, description);
        });

        todoAdapter = new TodoAdapter(this, R.layout.todo_item, presenter);

        todoListView.setAdapter(todoAdapter);
        presenter.loadTodoItems();
    }

    @Override
    public void displayTodos(List<TodoEntity> todoEntityList) {
        todoAdapter.clear();
        todoAdapter.addAll(todoEntityList);
    }

    @Override
    public void showAddSuccess() {
        Toast.makeText(this, "Todo added successfully", Toast.LENGTH_SHORT).show();
        clearInputFields();
    }

    @Override
    public void showAddError() {
        Toast.makeText(this, "Error adding todo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdateSuccess() {
        Toast.makeText(this, "Todo updated successfully", Toast.LENGTH_SHORT).show();
        clearInputFields();
    }

    @Override
    public void showDeleteSuccess() {
        Toast.makeText(this, "Todo deleted successfully", Toast.LENGTH_SHORT).show();
        clearInputFields();
    }

    @Override
    public void showEditDialog(TodoEntity todoEntity) {
        // Update the button label for editing
        Button addButton = findViewById(R.id.addButton);
        addButton.setText(R.string.edit_todo);

        // Change the button color for editing
        int editColor = ContextCompat.getColor(this, R.color.green);
        addButton.setBackgroundColor(editColor);

        // Populate the EditText fields with the existing values
        titleEditText.setText(todoEntity.getTitle());
        descriptionEditText.setText(todoEntity.getDescription());

        // Update the click listener for editing
        addButton.setOnClickListener(v -> {
            // Get the updated values from the EditText fields
            String newTitle = titleEditText.getText().toString();
            String newDescription = descriptionEditText.getText().toString();

            // Update the todoEntity with the new values
            todoEntity.setTitle(newTitle);
            todoEntity.setDescription(newDescription);

            // Call the presenter method to handle the update
            presenter.updateTodo(todoEntity);

            // Reset the button label, color, and clear input fields
            addButton.setText(R.string.add_todo);
            addButton.setBackgroundResource(android.R.drawable.btn_default); // Use the default button background
            clearInputFields();
        });
    }

    private void clearInputFields() {
        titleEditText.setText("");
        descriptionEditText.setText("");
    }
}

