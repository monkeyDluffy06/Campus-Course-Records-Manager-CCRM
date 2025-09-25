package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.service.exceptions.DuplicateEnrollmentException;
import edu.ccrm.service.exceptions.MaxCreditLimitExceededException;

/**
 * Manages the business logic for enrolling students in courses.
 */
public class EnrollmentService {

    private static final int MAX_CREDITS_PER_SEMESTER = 21;

    // This service depends on the other services to function.
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Enrolls a student in a course after validating business rules.
     * @param studentId The ID of the student.
     * @param courseCode The code of the course.
     * @throws DuplicateEnrollmentException if the student is already enrolled.
     * @throws MaxCreditLimitExceededException if the enrollment exceeds the credit limit.
     */
    public void enrollStudent(long studentId, String courseCode)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {

        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseByCode(courseCode);

        if (student == null) {
            System.out.println("ERROR: Enrollment failed. Student with ID " + studentId + " not found.");
            return;
        }
        if (course == null) {
            System.out.println("ERROR: Enrollment failed. Course with code " + courseCode + " not found.");
            return;
        }

        // Rule 1: Check for duplicate enrollment
        if (student.getEnrolledCourses().contains(course)) {
            throw new DuplicateEnrollmentException(
                "Enrollment failed: Student " + student.getFullName() + " is already enrolled in " + course.getTitle()
            );
        }

        // Rule 2: Check for max credit limit
        int currentCredits = student.getEnrolledCourses().stream().mapToInt(Course::getCredits).sum();
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException(
                "Enrollment failed: Exceeds max credit limit of " + MAX_CREDITS_PER_SEMESTER
            );
        }

        // If all rules pass, complete the enrollment
        student.getEnrolledCourses().add(course);
        System.out.println("SUCCESS: " + student.getFullName() + " has been enrolled in " + course.getTitle());
    }
}
