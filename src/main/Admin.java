package main;

import java.util.*;
import model.*;
import static db.DBMS.*;
import static main.Menu.*;
import static util.AppUtils.*;

public class Admin {

    public static Scanner scanner = new Scanner(System.in);

    public static void changeAdminPassword(int id) {
        changePassword(1);
    }

    public static void changeTeacherPassword() {
        System.out.print("Please enter the id of the teacher :");
        changePassword(scanner.nextInt());
    }

    public static void changeStudentPassword() {
        System.out.print("Please enter the id of the student :");
        changePassword(scanner.nextInt());
    }

    public static void changeStudentMarks() {
        System.out.print("Please enter the id of the student :");
        List<Student_Data> list = selectAllDataFromStudentData();
        int id = scanner.nextInt();
        Optional<Student_Data> student = list.stream().filter(x -> x.getId() == id).findAny();
        if (student.isPresent()) {
            print("Marks ::");
            print("1. Maths :" + student.get().getMaths());
            print("2. Physics :" + student.get().getPhysics());
            print("3. Chemistry :" + student.get().getChemistry());
            print("4. English :" + student.get().getEnglish());
            print("5. CS :" + student.get().getCs());
            print("6. Admin Menu");
            print("0. Exit");
            char choice = scanner.next().charAt(0);
            switch (choice) {
                case '1':
                    System.out.print("Enter Marks ::");
                    int marks = scanner.nextInt();
                    student.get().setMaths(marks);
                    break;
                case '2':
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setPhysics(marks);
                    break;
                case '3':
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setChemistry(marks);
                    break;
                case '4':
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setEnglish(marks);
                    break;
                case '5':
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setCs(marks);
                    break;
                case '6':
                    adminMenu();
                    break;
                case '0':
                    truncateTable();
                    System.exit(0);
                    break;
                default:
                    refresh();
                    print("Invalid character response. Please Try Again !!");
                    adminMenu();
                    break;

            }
            updateDataIntoStudentData(student.get());
        } else {
            System.out.println("ID not found !!");
            scanner.nextLine();
            scanner.nextLine();
            adminMenu();
        }
    }

    public static void viewMeritList() {
        print("Name\t\tMaths\tPhysics\t\tChemistry\tEnglish\t\tCS\tTotal");
        List<Student_Data> list = selectAllDataFromStudentData();
        list.forEach(x -> System.out
                .println(x.getName() + "\t" + x.getMaths() + "\t" + x.getPhysics() + "\t\t" + x.getChemistry()
                        + "\t\t" + x.getEnglish() + "\t\t" + x.getCs() + "\t" + x.getTotal() + ""));

        scanner.nextLine();
    }
}
