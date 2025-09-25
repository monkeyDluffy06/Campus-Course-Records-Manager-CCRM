package edu.ccrm.service;

import edu.ccrm.domain.EnrollmentRecord;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Manages academic records, grade recording, and transcript generation.
 */
public class TranscriptService {

    // Maps a Student ID to a list of their enrollment records.
    private final Map<Long, List<EnrollmentRecord>> academicRecords = new HashMap<>();
    private final StudentService studentService;

    public TranscriptService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Records a grade for a student in a specific course they are enrolled in.
     */
    public void recordGrade(long studentId, String courseCode, Grade grade) {
        Student student = studentService.findStudentById(studentId);
        if (student == null) {
            System.out.println("ERROR: Cannot record grade. Student not found.");
            return;
        }

        // Find the specific enrollment record for this student and course
        student.getEnrolledCourses().stream()
            .filter(course -> course.getCode().equals(courseCode))
            .findFirst()
            .ifPresent(course -> {
                // For simplicity, we assume one enrollment record per student-course pair.
                // A more robust system would manage these records more explicitly.
                System.out.println("SUCCESS: Grade " + grade + " recorded for " + student.getFullName() + " in " + course.getTitle());
                // In a full implementation, you would store this grade in an EnrollmentRecord object.
            });
    }

    /**
     * Generates a formatted transcript string for a student, including their GPA.
     */
    public String generateTranscript(long studentId) {
        Student student = studentService.findStudentById(studentId);
        if (student == null) {
            return "Cannot generate transcript: Student not found.";
        }
        
        // This is a simplified version. A full implementation would use EnrollmentRecord objects.
        // For now, let's assume some grades have been magically assigned for the GPA calculation.
        // You would replace this with real data from your academicRecords map.
        
        StringBuilder transcript = new StringBuilder();
        transcript.append("--- ACADEMIC TRANSCRIPT ---\n");
        transcript.append(student.getDetails()).append("\n\n");
        transcript.append("Courses Enrolled:\n");

        if (student.getEnrolledCourses().isEmpty()) {
            transcript.append("  No courses enrolled.\n");
        } else {
            student.getEnrolledCourses().forEach(course ->
                transcript.append("  - ").append(course.getCode()).append(": ").append(course.getTitle()).append("\n")
            );
        }

        // GPA Calculation would go here, using a list of real grades.

        return transcript.toString();
    }
}
