package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * An abstract base class for any person in the campus system.
 * It provides common properties like id, name, and email.
 */
public abstract class Person {

    protected long id;
    protected String fullName;
    protected String email;
    protected LocalDateTime dateCreated;
    protected LocalDateTime lastUpdated;

    public Person(long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    // Abstract method to be implemented by subclasses
    public abstract String getDetails();

    // --- Getters and Setters ---

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.lastUpdated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}

