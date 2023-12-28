package com.aftab.todomanager.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class TodoModel {

    private SQLiteDatabase database;

    public TodoModel(SQLiteDatabase database) {
        this.database = database;
    }

    public long addTodoItem(String title, String description) {
        ContentValues values = new ContentValues();
        values.put(TodoEntry.COLUMN_TITLE, title);
        values.put(TodoEntry.COLUMN_DESCRIPTION, description);
        return database.insert(TodoEntry.TABLE_NAME, null, values);
    }

    public List<TodoItem> getTodoItems() {
        List<TodoItem> todoItemList = new ArrayList<>();
        Cursor cursor = database.query(
                TodoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(TodoEntry.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(TodoEntry.COLUMN_DESCRIPTION));
                TodoItem todoItem = new TodoItem(title, description);
                todoItemList.add(todoItem);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return todoItemList;
    }

    public void updateTodoItem(String oldTitle, String newTitle, String newDescription) {
        ContentValues values = new ContentValues();
        values.put(TodoEntry.COLUMN_TITLE, newTitle);
        values.put(TodoEntry.COLUMN_DESCRIPTION, newDescription);

        database.update(
                TodoEntry.TABLE_NAME,
                values,
                TodoEntry.COLUMN_TITLE + " = ?",
                new String[]{oldTitle}
        );
    }

    public void deleteTodoItem(String title) {
        database.delete(
                TodoEntry.TABLE_NAME,
                TodoEntry.COLUMN_TITLE + " = ?",
                new String[]{title}
        );
    }

    // Inner class that defines the table contents
    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
