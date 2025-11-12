import java.util.*;

// ------------------------- Custom Exception -------------------------
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

// ------------------------- Student Class -------------------------
class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    // ✅ Constructor
    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    // ✅ validateMarks() method
    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    // ✅ calculateAverage()
    public double calculateAverage() {
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return (double) sum / marks.length;
    }

    // ✅ displayResult()
    public void displayResult() {
        double avg = calculateAverage();
        String status = (avg >= 40) ? "Pass" : "Fail";
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (int m : marks) {
            System.out.print(m + " ");
        }
        System.out.println("\nAverage: " + avg);
        System.out.println("Result: " + status);
    }

    // ✅ Getter
    public int getRollNumber() {
        return rollNumber;
    }
}

// ------------------------- Result Manager Class -------------------------
public class ResultManager {
    private static Student[] students = new Student[100];
    private static int count = 0;

    public static void addStudent(Scanner sc) {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }

            // ✅ Create Student and validate marks
            Student s = new Student(roll, name, marks);
            s.validateMarks();

            students[count++] = s;
            System.out.println("Student added successfully. Returning to main menu...");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type! Please enter valid numbers.");
            sc.nextLine(); // clear buffer
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }

    public static void showStudentDetails(Scanner sc) {
        System.out.print("Enter Roll Number to search: ");
        int roll = sc.nextInt();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getRollNumber() == roll) {
                students[i].displayResult();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student with Roll Number " + roll + " not found!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(sc);
                        break;
                    case 2:
                        showStudentDetails(sc);
                        break;
                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number for menu choice.");
        } finally {
            System.out.println("Closing resources...");
            sc.close();
        }
    }
}
