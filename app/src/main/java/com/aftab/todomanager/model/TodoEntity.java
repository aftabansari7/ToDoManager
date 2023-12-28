package com.aftab.todomanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo")
public class TodoEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String title;

    private String description;

    private Boolean completed;

    public TodoEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    public TodoEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
