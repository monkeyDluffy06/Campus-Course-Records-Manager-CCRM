package edu.ccrm.domain;

/**
 * Represents a course in the institution.
 * This class uses the Builder pattern for object creation.
 */
public class Course {

    private final String code;
    private final String title;
    private final int credits;
    private final Semester semester;
    private Instructor instructor;

    // The constructor is private to force the use of the Builder.
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.semester = builder.semester;
        this.instructor = builder.instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
               "code='" + code + '\'' +
               ", title='" + title + '\'' +
               ", credits=" + credits +
               ", semester=" + semester.getDisplayName() +
               '}';
    }

    // --- Getters ---
    // No setters are provided to make the Course object immutable after creation.

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public Semester getSemester() {
        return semester;
    }

    public Instructor getInstructor() {
        return instructor;
    }
    
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    // --- The static nested Builder class ---

    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private Semester semester;
        private Instructor instructor;

        public Builder(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this; // Return the builder for chaining
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            // Create the Course object using the private constructor
            return new Course(this);
        }
    }
}
