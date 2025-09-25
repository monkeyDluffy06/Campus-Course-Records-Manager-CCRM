package edu.ccrm.domain;

/**
 * Represents the letter grades and their corresponding grade points.
 */
public enum Grade {
    S(10.0),
    A(9.0),
    B(8.0),
    C(7.0),
    D(6.0),
    E(5.0),
    F(0.0);

    private final double gradePoints;

    // Constructor for the enum
    Grade(double gradePoints) {
        this.gradePoints = gradePoints;
    }

    // Getter for the grade points
    public double getGradePoints() {
        return gradePoints;
    }
}
