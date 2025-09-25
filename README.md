# Campus Course & Records Manager (CCRM)

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-DB413D?style=for-the-badge&logo=java" alt="Language: Java">
  <img src="https://img.shields.io/badge/JDK-21%2B-orange?style=for-the-badge" alt="JDK: 21+">
  <img src="https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge" alt="Status: Completed">
  
    
  </a>
</p>

The Campus Course & Records Manager (CCRM) is a comprehensive, console-based application built with Java SE. It simulates an academic records system, allowing an administrative user to manage students, courses, enrollments, and grades through a command-line interface.

---

## Setup & Installation

Follow the instructions for your operating system.

### 1. Prerequisite: Install the JDK (Java Development Kit)
This project requires **JDK version 21 or later**.

1.  Download and install the JDK from an official provider like Oracle or OpenJDK.
2.  Set the `JAVA_HOME` environment variable on your system to point to your JDK installation directory.
3.  Add the JDK's `bin` folder to your system's `PATH` environment variable.
4.  Verify the installation by opening a terminal or command prompt and running:
    ```bash
    java -version
    ```
    * _`[Your screenshot of the 'java -version' command output here]`_

---

### Option A: Windows with Eclipse IDE

Follow these steps to set up and run the project using the Eclipse IDE on a Windows machine.

1.  **Install Eclipse IDE:**
    * Download the **"Eclipse IDE for Java Developers"** package for Windows from the official Eclipse Packages page: `https://www.eclipse.org/downloads/packages/`
    * Extract the downloaded `.zip` file to a permanent location (e.g., `C:\Tools\eclipse`).
    * Run `eclipse.exe` to launch the IDE.

2.  **Import the Project:**
    * After cloning this repository, open Eclipse.
    * Go to `File > New > Java Project`.
    * Enter a project name (e.g., `CCRM-Project`).
    * **Uncheck** the "Use default location" box and **"Browse..."** to the root folder where you cloned this project. Eclipse will automatically detect the `src` folder.
    * Click **"Finish"**.
    * _`[Your screenshot of the Eclipse 'New Java Project' wizard here]`_

3.  **Configure and Run:**
    * In the "Package Explorer", navigate to `src > edu.ccrm.cli > Main.java`.
    * Right-click `Main.java` and select **`Run As > Run Configurations...`**.
    * Go to the **"Arguments"** tab.
    * In the **"VM arguments"** box, enter `-ea`. This is required to enable assertions.
    * Click **"Apply"**, then **"Run"**. The application will start in the "Console" view.
    * _`[Your screenshot of the 'Run Configurations' window with the -ea flag here]`_

---

### Option B: Linux (Debian) with Command Line & Text Editor

Follow these steps to set up and run the project from the terminal on a Debian-based Linux system.

1.  **Install Dependencies:**
    Open a terminal and install the default JDK and Git.
    ```bash
    sudo apt update
    sudo apt install default-jdk git
    ```

2.  **Clone the Repository:**
    Clone the project from your GitHub repository.
    ```bash
    git clone <YOUR_GITHUB_REPOSITORY_URL>
    cd CCRM-Project
    ```

3.  **Compile the Code:**
    From the project's root directory, create a `bin` directory for the compiled files and then compile all `.java` source files.
    ```bash
    mkdir bin
    javac -d bin -sourcepath src src/edu/ccrm/cli/Main.java
    ```

4.  **Run the Application:**
    Navigate to the `bin` directory and run the main class. The `-ea` flag is required to enable assertions.
    ```bash
    cd bin
    java -ea edu.ccrm.cli.Main
    ```
    * _`[Your screenshot of the project open in your text editor and the application running in the terminal here]`_

---

## Java Concepts Demonstrated

This table maps the key Java concepts from the syllabus to where they are demonstrated in the source code.

| Concept                       | File(s) / Location(s) where demonstrated                       |
| ----------------------------- | -------------------------------------------------------------- |
| **OOP: Inheritance** | `Person.java` (base), `Student.java`, `Instructor.java` (subclasses) |
| **OOP: Abstraction** | `Person.java` (abstract class), `getDetails()` (abstract method) |
| **OOP: Polymorphism** | `getDetails()` overridden in `Student` and `Instructor`.          |
| **OOP: Encapsulation** | All `domain` classes use `private`/`protected` fields with getters/setters. |
| **Design Pattern: Builder** | `Course.java` contains a static nested `Builder` class.        |
| **Design Pattern: Singleton** | `AppConfig.java` is implemented as a Singleton.                |
| **Exception Handling** | `EnrollmentService.java` throws custom exceptions; `cli.Main` handles them with `try-catch`. |
| **Custom Exceptions** | `DuplicateEnrollmentException.java`, `MaxCreditLimitExceededException.java` |
| **File I/O (NIO.2)** | `DataPersistenceService.java` uses `Paths`, `Files`, `BufferedWriter`. |
| **Java Streams API** | `DataPersistenceService.java` uses `Files.lines()`. `CourseService` uses streams for searching. |
| **Date/Time API** | `DataPersistenceService.java` uses `LocalDateTime` for backup timestamps. |
| **Recursion** | `DataPersistenceService.java` contains the `calculateDirectorySize()` method. |
| **Interfaces** | `Searchable.java` interface, implemented by `CourseService.java`. |
| **Enums with Constructors** | `Grade.java` and `Semester.java`.                              |
| **Assertions** | `StudentService.java` uses `assert` to check for valid input.  |
| **Packages** | The project is organized into `cli`, `config`, `domain`, `io`, and `service` packages. |

---

## Java Architecture Explained

* **JVM (Java Virtual Machine):** An abstract machine that provides the runtime environment in which Java bytecode can be executed. It is the core component that makes Java platform-independent ("write once, run anywhere").
* **JRE (Java Runtime Environment):** The software package that contains everything required to run a Java program, but not to develop one. It includes the JVM, standard libraries, and other components.
* **JDK (Java Development Kit):** A superset of the JRE. It contains everything in the JRE, plus the tools necessary for Java development, such as the compiler (`javac`) and debugger.

---

## Java Platforms: ME vs. SE vs. EE

| Aspect              | Java ME (Micro Edition)          | Java SE (Standard Edition)        | Java EE (Enterprise Edition)       |
| ------------------- | -------------------------------- | --------------------------------- | ---------------------------------- |
| **Target** | Small, resource-constrained devices (e.g., old mobile phones, embedded systems). | Desktop applications, servers, and applets. This is the core Java platform. | Large-scale, distributed, enterprise-level applications (e.g., web services, banking apps). |
| **Core APIs** | A subset of Java SE APIs, plus specific APIs for mobile devices. | The fundamental Java APIs (collections, I/O, networking, etc.). | A superset of Java SE APIs, adding extensive libraries for web development, component models, and more. |
