package com.airbyte.daneshmal.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table
public class Comment implements Serializable {
    @Id
    private @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String fullName;
    private @Column(columnDefinition = "VARCHAR(255)") String email;
    private @Column(columnDefinition = "VARCHAR(5000)") String message;
    private @Column Instant date;

    public Comment() {
        this.date = Instant.now();
    }

    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
