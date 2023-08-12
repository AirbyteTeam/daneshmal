package com.airbyte.daneshmal.dto;

import java.io.Serializable;

public class CommentDTO implements Serializable {
    private String fullName;
    private String email;
    private String message;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
