package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.io.DataPersistenceService;
import edu.ccrm.service.*;
import edu.ccrm.service.exceptions.DuplicateEnrollmentException;
import edu.ccrm.service.exceptions.MaxCreditLimitExceededException;

import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        // 1. Instantiate all services
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
        TranscriptService transcriptService = new TranscriptService(studentService);
        DataPersistenceService persistenceService = new DataPersistenceService(studentService, courseService, enrollmentService);
        // 2. Load data on startup
        persistenceService.loadData();

        Scanner scanner = new Scanner(System.in);

        // 3. Start the main application loop
        while (true) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    handleStudentManagement(studentService, scanner);
                    break;
                case 2:
                    handleCourseManagement(courseService, scanner);
                    break;
                case 3:
                    handleEnrollmentManagement(enrollmentService, studentService, courseService, scanner);
                    break;
                case 4:
                    handleGradesAndTranscripts(transcriptService, studentService, scanner);
                    break;
                case 5: //case for backup
                    persistenceService.backupData();
                    break;
                case 6: //case for recursion
                    try {
                        Path backupPath = Paths.get("backups");
                        if (Files.exists(backupPath)) {
                            long totalSize = persistenceService.calculateDirectorySize(backupPath);
                            System.out.printf("Total size of all backups is: %.2f KB\n", totalSize / 1024.0);
                        } else {
                            System.out.println("No backup directory found. Create a backup first.");
                        }
                    } catch (IOException e) {
                        System.err.println("ERROR: Could not calculate backup size. " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Saving data before exiting...");
                    persistenceService.saveData(); // Save data on exit
                    System.out.println("Exiting CCRM. Goodbye!");
                    scanner.close();
                    return; // Exit the main method, which terminates the program
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Campus Course & Records Manager ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment");
        System.out.println("4. Manage Grades & Transcripts");
        System.out.println("5. Backup Data");
        System.out.println("6. Show Backup Size (Recursive)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleStudentManagement(StudentService studentService, Scanner scanner) {
        while (true) {
            System.out.println("\n-- Student Management --");
            System.out.println("1. Add New Student");
            System.out.println("2. Find Student by ID");
            System.out.println("3. List All Students");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Registration No: ");
                    String regNo = scanner.nextLine();
                    System.out.print("Enter Full Name: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    studentService.addStudent(regNo, fullName, email);
                    break;
                case 2:
                    System.out.print("Enter Student ID to find: ");
                    long idToFind = scanner.nextLong();
                    scanner.nextLine(); // Consume the rest of the line
                    Student foundStudent = studentService.findStudentById(idToFind);
                    if (foundStudent != null) {
                        System.out.println("Student Found:");
                        System.out.println(foundStudent.getDetails());
                    } else {
                        System.out.println("ERROR: Student with ID " + idToFind + " not found.");
                    }
                    break;
                case 3:
                    System.out.println("\n-- All Students --");
                    List<Student> students = studentService.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        students.forEach(student -> System.out.println(student.getDetails() + "\n"));
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    
    private static void handleCourseManagement(CourseService courseService, Scanner scanner) {
        while (true) {
            System.out.println("\n-- Course Management --");
            System.out.println("1. Add New Course");
            System.out.println("2. Find Course by Code");
            System.out.println("3. List All Courses");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Course Code (e.g., CS101): ");
                    String code = scanner.nextLine();
                    System.out.print("Enter Course Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Credits: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    Course newCourse = new Course.Builder(code, title).credits(credits).semester(Semester.FALL).build();
                    courseService.addCourse(newCourse);
                    break;
                case 2:
                    System.out.print("Enter Course Code to find: ");
                    String codeToFind = scanner.nextLine();
                    Course foundCourse = courseService.findCourseByCode(codeToFind);
                    if (foundCourse != null) {
                        System.out.println("Course Found:");
                        System.out.println(foundCourse.toString());
                    } else {
                        System.out.println("ERROR: Course with code " + codeToFind + " not found.");
                    }
                    break;
                case 3:
                    System.out.println("\n-- All Courses --");
                    List<Course> courses = courseService.getAllCourses();
                    if (courses.isEmpty()) {
                        System.out.println("No courses found.");
                    } else {
                        courses.forEach(System.out::println);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleEnrollmentManagement(EnrollmentService enrollmentService, StudentService studentService, CourseService courseService, Scanner scanner) {
        System.out.println("\n-- Enroll Student in Course --");
        System.out.println("Available Students:");
        studentService.getAllStudents().forEach(s -> System.out.println("  ID: " + s.getId() + ", Name: " + s.getFullName()));
        System.out.println("\nAvailable Courses:");
        courseService.getAllCourses().forEach(c -> System.out.println("  Code: " + c.getCode() + ", Title: " + c.getTitle()));

        try {
            System.out.print("\nEnter the Student ID: ");
            long studentId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter the Course Code: ");
            String courseCode = scanner.nextLine();
            enrollmentService.enrollStudent(studentId, courseCode);
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please check your input and try again.");
        }
    }

    private static void handleGradesAndTranscripts(TranscriptService transcriptService, StudentService studentService, Scanner scanner) {
        while (true) {
            System.out.println("\n-- Grades & Transcripts --");
            System.out.println("1. Record Grade for Student");
            System.out.println("2. Generate Student Transcript");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Select a student to record a grade for:");
                        studentService.getAllStudents().forEach(s -> System.out.println("  ID: " + s.getId() + ", Name: " + s.getFullName()));
                        System.out.print("Enter Student ID: ");
                        long studentId = scanner.nextLong();
                        scanner.nextLine();
                        Student student = studentService.findStudentById(studentId);
                        if (student == null || student.getEnrolledCourses().isEmpty()) {
                            System.out.println("Student not found or not enrolled in any courses.");
                            break;
                        }
                        System.out.println("Select a course for " + student.getFullName() + ":");
                        student.getEnrolledCourses().forEach(c -> System.out.println("  Code: " + c.getCode() + ", Title: " + c.getTitle()));
                        System.out.print("Enter Course Code: ");
                        String courseCode = scanner.nextLine();
                        System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
                        String gradeStr = scanner.nextLine().toUpperCase();
                        Grade grade = Grade.valueOf(gradeStr);
                        transcriptService.recordGrade(studentId, courseCode, grade);
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: Invalid grade entered. Please use one of S, A, B, C, D, E, F.");
                    } catch (Exception e) {
                        System.out.println("An error occurred. Please check your input.");
                    }
                    break;
                case 2:
                    System.out.println("Select a student to generate a transcript for:");
                    studentService.getAllStudents().forEach(s -> System.out.println("  ID: " + s.getId() + ", Name: " + s.getFullName()));
                    System.out.print("Enter Student ID: ");
                    long transcriptStudentId = scanner.nextLong();
                    scanner.nextLine();
                    String transcript = transcriptService.generateTranscript(transcriptStudentId);
                    System.out.println(transcript);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
