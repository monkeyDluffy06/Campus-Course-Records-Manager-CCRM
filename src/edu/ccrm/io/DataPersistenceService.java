package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Handles saving and loading application data to and from files.
 */
public class DataPersistenceService {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public DataPersistenceService(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public void saveData() {
        saveStudents();
        saveCourses();
        saveEnrollments();
    }

    public void loadData() {
        loadStudents();
        loadCourses();
        loadEnrollments(); // Must be loaded last!
    }

    private void saveStudents() {
        String studentsFile = AppConfig.getInstance().getStudentsFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(studentsFile))) {
            for (Student student : studentService.getAllStudents()) {
                String line = String.join(",", String.valueOf(student.getId()), student.getRegNo(), student.getFullName(), student.getEmail());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Student data saved.");
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }

    private void saveCourses() {
        String coursesFile = AppConfig.getInstance().getCoursesFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(coursesFile))) {
            for (Course course : courseService.getAllCourses()) {
                String line = String.join(",", course.getCode(), course.getTitle(), String.valueOf(course.getCredits()), course.getSemester().name());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Course data saved.");
        } catch (IOException e) {
            System.err.println("Error saving course data: " + e.getMessage());
        }
    }

    private void saveEnrollments() {
        String enrollmentsFile = AppConfig.getInstance().getEnrollmentsFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(enrollmentsFile))) {
            for (Student student : studentService.getAllStudents()) {
                for (Course course : student.getEnrolledCourses()) {
                    String line = student.getId() + "," + course.getCode();
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Enrollment data saved.");
        } catch (IOException e) {
            System.err.println("Error saving enrollment data: " + e.getMessage());
        }
    }

    private void loadStudents() {
        String studentsFile = AppConfig.getInstance().getStudentsFile();
        try (Stream<String> lines = Files.lines(Paths.get(studentsFile))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    studentService.loadStudent(Long.parseLong(parts[0]), parts[1], parts[2], parts[3]);
                }
            });
            System.out.println("Student data loaded.");
        } catch (IOException e) {
            System.out.println("No existing student data found.");
        }
    }

    private void loadCourses() {
        String coursesFile = AppConfig.getInstance().getCoursesFile();
        try (Stream<String> lines = Files.lines(Paths.get(coursesFile))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Course course = new Course.Builder(parts[0], parts[1])
                            .credits(Integer.parseInt(parts[2]))
                            .semester(Semester.valueOf(parts[3]))
                            .build();
                    courseService.loadCourse(course);
                }
            });
            System.out.println("Course data loaded.");
        } catch (IOException e) {
            System.out.println("No existing course data found.");
        }
    }

    private void loadEnrollments() {
        String enrollmentsFile = AppConfig.getInstance().getEnrollmentsFile();
        try (Stream<String> lines = Files.lines(Paths.get(enrollmentsFile))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        long studentId = Long.parseLong(parts[0]);
                        String courseCode = parts[1];
                        enrollmentService.enrollStudent(studentId, courseCode);
                    } catch (Exception e) {
                        System.err.println("Could not load enrollment for line '" + line + "': " + e.getMessage());
                    }
                }
            });
            System.out.println("Enrollment data loaded.");
        } catch (IOException e) {
            System.out.println("No existing enrollment data found.");
        }
    }

    public void backupData() {
        String backupsDirName = AppConfig.getInstance().getBackupsDirectory();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path backupDir = Paths.get(backupsDirName, "backup_" + timestamp);

        try {
            Files.createDirectories(backupDir);

            Path studentsSource = Paths.get(AppConfig.getInstance().getStudentsFile());
            Path coursesSource = Paths.get(AppConfig.getInstance().getCoursesFile());
            Path enrollmentsSource = Paths.get(AppConfig.getInstance().getEnrollmentsFile());

            if (Files.exists(studentsSource)) Files.copy(studentsSource, backupDir.resolve(studentsSource.getFileName()));
            if (Files.exists(coursesSource)) Files.copy(coursesSource, backupDir.resolve(coursesSource.getFileName()));
            if (Files.exists(enrollmentsSource)) Files.copy(enrollmentsSource, backupDir.resolve(enrollmentsSource.getFileName()));

            System.out.println("SUCCESS: Data backed up to " + backupDir);

        } catch (IOException e) {
            System.err.println("ERROR: Could not create backup. " + e.getMessage());
        }
    }

    public long calculateDirectorySize(Path path) throws IOException {
        long totalSize = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    totalSize += calculateDirectorySize(entry);
                } else {
                    totalSize += Files.size(entry);
                }
            }
        }
        return totalSize;
    }
}
