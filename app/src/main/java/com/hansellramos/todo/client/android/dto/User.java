package com.hansellramos.todo.client.android.dto;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by hansell.ramos on 28/08/17.
 */

@IgnoreExtraProperties
public class User {

    private String id;
    private String email;

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
