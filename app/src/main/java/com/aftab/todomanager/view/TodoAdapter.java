package com.aftab.todomanager.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import com.aftab.todomanager.R;
import com.aftab.todomanager.model.TodoEntity;
import com.aftab.todomanager.presenter.TodoPresenter;

//ToDo Adapter class for list view
public class TodoAdapter extends ArrayAdapter<TodoEntity> {
    private final TodoPresenter presenter;
    private final Context context;

    public TodoAdapter(Context context, int resource, TodoPresenter presenter) {
        super(context, resource);
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.todo_item, null);
        }

        TodoEntity todoEntity = getItem(position);

        if (todoEntity != null) {
            TextView titleTextView = view.findViewById(R.id.titleTextView);
            TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
            ImageView menuIcon = view.findViewById(R.id.menuIcon);

            boolean isCompleted = todoEntity.getCompleted() != null && todoEntity.getCompleted();

            if (isCompleted) {
                int completedColor = ContextCompat.getColor(context, R.color.green);
                menuIcon.setColorFilter(completedColor);
            } else {
                // Reset the color filter to default
                menuIcon.clearColorFilter();
            }

            menuIcon.setOnClickListener(v -> showPopupMenu(v, todoEntity));
            titleTextView.setText(todoEntity.getTitle());
            descriptionTextView.setText(todoEntity.getDescription());
        }

        return view;
    }

    public void showPopupMenu(View v, TodoEntity todoEntity) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.getMenuInflater().inflate(R.menu.todo_menu, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();
        MenuItem completeItem = menu.findItem(R.id.complete);

        boolean isCompleted = todoEntity.getCompleted() != null && todoEntity.getCompleted();
        completeItem.setTitle(isCompleted ? R.string.uncomplete : R.string.complete);

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.complete) {
                boolean newCompletionStatus = !isCompleted;
                todoEntity.setCompleted(newCompletionStatus);
                presenter.updateTodo(todoEntity);
                return true;
            } else if (itemId == R.id.edit) {
                presenter.editTodo(todoEntity);
                return true;
            } else if (itemId == R.id.delete) {
                presenter.deleteTodoItem(todoEntity);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}

