package edu.ccrm.domain;

/**
 * Represents the academic semesters.
 * Each enum constant has a display-friendly name.
 */
public enum Semester {
    FALL("Fall"),
    SPRING("Spring"),
    SUMMER("Summer");

    private final String displayName;

    // Constructor for the enum
    Semester(String displayName) {
        this.displayName = displayName;
    }

    // Getter for the display name
    public String getDisplayName() {
        return displayName;
    }
}
