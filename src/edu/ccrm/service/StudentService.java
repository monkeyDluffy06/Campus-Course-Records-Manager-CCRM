package edu.ccrm.service;

import edu.ccrm.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all business logic related to students.
 * This includes creating, finding, and updating student records.
 */
public class StudentService {

    // Using a Map as a simple in-memory database. The key is the student's ID.
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long nextId = 1; // Used to generate unique IDs for new students.

    /**
     * Creates a new student, assigns a unique ID, and stores it.
     * @param regNo The student's registration number.
     * @param fullName The student's full name.
     * @param email The student's email address.
     * @return The newly created Student object.
     */
    public Student addStudent(String regNo, String fullName, String email) {
        // Assert that the registration number is valid.
        assert regNo != null && !regNo.trim().isEmpty() : "Registration number cannot be null or empty";
        
        long newId = nextId++;
        Student newStudent = new Student(newId, regNo, fullName, email);
        studentMap.put(newId, newStudent);
        System.out.println("SUCCESS: Student " + fullName + " added with ID " + newId);
        return newStudent;
    }

    /**
     * Finds a student by their unique ID.
     * @param id The ID of the student to find.
     * @return The Student object if found, otherwise null.
     */
    public Student findStudentById(long id) {
        return studentMap.get(id);
    }

    /**
     * Retrieves a list of all students in the system.
     * @return A List containing all Student objects.
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }
}
