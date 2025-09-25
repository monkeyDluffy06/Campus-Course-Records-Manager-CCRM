package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all business logic related to courses.
 */
public class CourseService implements Searchable<Course> {

    // Using the String course code as the unique key for the map.
    private final Map<String, Course> courseMap = new HashMap<>();

    /**
     * Adds a pre-built course to the system.
     * @param course The Course object to add (created using the Builder).
     * @return The added Course object, or null if a course with that code already exists.
     */
    public Course addCourse(Course course) {
        if (courseMap.containsKey(course.getCode())) {
            System.out.println("ERROR: Course with code " + course.getCode() + " already exists.");
            return null;
        }
        courseMap.put(course.getCode(), course);
        System.out.println("SUCCESS: Course '" + course.getTitle() + "' added.");
        return course;
    }

    /**
     * Finds a course by its unique code.
     * @param code The code of the course to find (e.g., "CS101").
     * @return The Course object if found, otherwise null.
     */
    public Course findCourseByCode(String code) {
        return courseMap.get(code);
    }

    /**
     * Retrieves a list of all courses in the system.
     * @return A List containing all Course objects.
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courseMap.values());
    }
    
    /**
     * Assigns an instructor to a specific course.
     * @param courseCode The code of the course.
     * @param instructor The instructor to be assigned.
     * @return The updated Course object, or null if the course was not found.
     */
    public Course assignInstructorToCourse(String courseCode, Instructor instructor) {
        Course course = findCourseByCode(courseCode);
        if (course != null) {
            course.setInstructor(instructor);
            System.out.println("SUCCESS: " + instructor.getFullName() + " assigned to " + course.getTitle());
            return course;
        } else {
            System.out.println("ERROR: Course with code " + courseCode + " not found.");
            return null;
        }
    }
        // Inside the CourseService class...

    /**
     * Loads a course with a specific code from a data file.
     * This method bypasses the check for existing courses.
     */
    public void loadCourse(Course course) {
        courseMap.put(course.getCode(), course);
    }
     /**
     * Searches for courses where the title contains the query string.
     * This method fulfills the Searchable interface contract.
     * @param query The text to search for in course titles.
     * @return A list of matching courses.
     */
    @Override
    public List<Course> search(String query) {
        // Use a Stream to filter the courses
        return courseMap.values().stream()
                .filter(course -> course.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

}
