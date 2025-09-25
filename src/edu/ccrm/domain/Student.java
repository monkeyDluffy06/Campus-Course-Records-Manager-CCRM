package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student, inheriting common properties from Person.
 * Adds student-specific details like registration number and enrolled courses.
 */
public class Student extends Person {

    private String regNo;
    private String status; // e.g., "Active", "Inactive", "Graduated"
    private List<Course> enrolledCourses;

    public Student(long id, String regNo, String fullName, String email) {
        // Call the constructor of the parent class (Person) first.
        super(id, fullName, email);

        // Now, initialize the fields specific to the Student class.
        this.regNo = regNo;
        this.status = "Active"; // Default status for a new student
        this.enrolledCourses = new ArrayList<>();
    }

    /**
     * Provides a detailed string representation of the Student.
     * This fulfills the contract of the abstract getDetails() method in Person.
     */
    @Override
    public String getDetails() {
        return "Student Details:\n" +
               "  ID: " + id + "\n" +
               "  Reg No: " + regNo + "\n" +
               "  Name: " + fullName + "\n" +
               "  Email: " + email + "\n" +
               "  Status: " + status;
    }

    // --- Student-specific Getters and Setters ---

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}
