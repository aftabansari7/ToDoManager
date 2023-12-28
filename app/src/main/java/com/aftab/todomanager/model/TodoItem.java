package com.aftab.todomanager.model;

public class TodoItem {

    private String title;
    private String description;

    private Boolean Completed;

    public TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompleted() {
        return Completed;
    }

    public void setCompleted(Boolean completed) {
        Completed = completed;
    }
}
