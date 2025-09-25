package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an instructor, inheriting common properties from Person.
 * Adds instructor-specific details like department.
 */
public class Instructor extends Person {

    private String department;
    private List<Course> coursesTaught;

    public Instructor(long id, String fullName, String email, String department) {
        // Call the constructor of the parent class (Person) first.
        super(id, fullName, email);
        
        // Initialize instructor-specific fields.
        this.department = department;
        this.coursesTaught = new ArrayList<>();
    }

    /**
     * Provides a detailed string representation of the Instructor.
     * This fulfills the contract of the abstract getDetails() method in Person.
     */
    @Override
    public String getDetails() {
        return "Instructor Details:\n" +
               "  ID: " + id + "\n" +
               "  Name: " + fullName + "\n" +
               "  Email: " + email + "\n" +
               "  Department: " + department;
    }

    // --- Instructor-specific Methods ---

    public void addCourseToTaughtList(Course course) {
        this.coursesTaught.add(course);
    }
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }
}
