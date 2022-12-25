package main;

import java.util.*;
import model.*;
import static db.DBMS.*;
import static main.Menu.*;
import static util.AppUtils.*;

public class Teacher {

    public static Scanner scanner = new Scanner(System.in);

    public static void changeTeacherPassword(int id) {
        changePassword(id);
    }

    public static void changeStudentMarks(Person person) {
        List<Teacher_Data> tlist = selectAllDataFromTeacherData();
        Teacher_Data teacher = tlist.stream().filter(x -> x.getId() == person.getId()).findAny().get();
        System.out.print("Please enter the id of the student :");
        List<Student_Data> list = selectAllDataFromStudentData();
        int id = scanner.nextInt();
        Optional<Student_Data> student = list.stream().filter(x -> x.getId() == id).findAny();
        if (student.isPresent()) {
            String choice = teacher.getSubject();
            switch (choice) {
                case "Maths":
                    print("Current Marks ::" + student.get().getMaths());
                    System.out.print("Enter Marks ::");
                    int marks = scanner.nextInt();
                    student.get().setMaths(marks);
                    break;
                case "Physics":
                    print("Current Marks ::" + student.get().getPhysics());
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setPhysics(marks);
                    break;
                case "Chemistry":
                    print("Current Marks ::" + student.get().getChemistry());
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setChemistry(marks);
                    break;
                case "English":
                    print("Current Marks ::" + student.get().getEnglish());
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setEnglish(marks);
                    break;
                case "CS":
                    print("Current Marks ::" + student.get().getCs());
                    System.out.print("Enter Marks ::");
                    marks = scanner.nextInt();
                    student.get().setCs(marks);
                    break;
                default:
                    refresh();
                    print("Invalid response. Please Try Again !!");
                    teacherMenu(person);
                    break;

            }
            updateDataIntoStudentData(student.get());
            print("Marks Updated");

        } else {
            System.out.println("ID not found !!");
        }
        scanner.nextLine();
        scanner.nextLine();
        teacherMenu(person);
    }

    public static void viewMeritList(String subject) {
        print("Name\t\t" + subject);
        List<Student_Data> list = selectAllDataFromStudentData();

        switch (subject) {
            case "Maths":
                list.forEach(x -> System.out
                        .println(x.getName() + "\t" + x.getMaths()));
                break;
            case "Physics":
                list.forEach(x -> System.out
                        .println(x.getName() + "\t" + x.getPhysics()));
                break;
            case "Chemistry":
                list.forEach(x -> System.out
                        .println(x.getName() + "\t" + x.getChemistry()));
                break;
            case "English":
                list.forEach(x -> System.out
                        .println(x.getName() + "\t" + x.getEnglish()));
                break;
            case "CS":
                list.forEach(x -> System.out
                        .println(x.getName() + "\t" + x.getCs()));
                break;

        }
        scanner.nextLine();
    }
}
