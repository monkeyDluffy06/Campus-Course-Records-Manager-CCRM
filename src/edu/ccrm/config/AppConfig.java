package edu.ccrm.config;

/**
 * A Singleton class to manage global application configuration.
 */
public class AppConfig {

    // 1. The single, private, static, final instance of the class
    private static final AppConfig INSTANCE = new AppConfig();

    // Configuration properties
    private final String studentsFile = "students.csv";
    private final String coursesFile = "courses.csv";
    private final String enrollmentsFile = "enrollments.csv";
    private final String backupsDirectory = "backups";

    // 2. A private constructor to prevent anyone else from creating an instance
    private AppConfig() {
    }

    // 3. A public static method to get the single instance
    public static AppConfig getInstance() {
        return INSTANCE;
    }

    // --- Getters for configuration properties ---

    public String getStudentsFile() {
        return studentsFile;
    }

    public String getCoursesFile() {
        return coursesFile;
    }

    public String getEnrollmentsFile() {
        return enrollmentsFile;
    }

    public String getBackupsDirectory() {
        return backupsDirectory;
    }
}
