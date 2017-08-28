package com.hansellramos.todo.client.android.dto;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by hansell.ramos on 28/08/17.
 */

@IgnoreExtraProperties
public class Task {

    private String id;
    private String name;
    private String priority;
    private String dueDate;

    public Task() {
    }

    public Task(String id, String name, String priority, String dueDate) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
