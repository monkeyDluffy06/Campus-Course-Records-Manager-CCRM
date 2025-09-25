package edu.ccrm.domain;

/**
 * Represents a single enrollment record, linking a student to a course
 * and the grade they received.
 */
public class EnrollmentRecord {

    private final Student student;
    private final Course course;
    private Grade grade;

    public EnrollmentRecord(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.grade = null; // Grade is null until it is officially recorded
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
