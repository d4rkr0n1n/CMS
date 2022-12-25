package main;

import static util.AppUtils.print;
import static util.AppUtils.printLogo;
import static util.AppUtils.clearConsole;
import static db.DBMS.*;
import java.util.List;
import model.Person;
import model.Teacher_Data;

import java.util.Scanner;

import util.AppUtils;

public class Menu {

    public static Scanner scanner = new Scanner(System.in);

    public static void init() {
        connect();
        createDb();
        insertMockData();
        mainMenu();
    }

    public static void mainMenu() {
        print("Main Menu");
        print("Please select your criteria ::");
        print(null);
        print("1. Admin");
        print("2. Teacher");
        print("3. Student");
        print("0. Exit");

        char choice = scanner.next().charAt(0);
        refresh();
        switch (choice) {
            case '1':
                checkCreds("Admin");
                break;
            case '2':
                checkCreds("Teacher");
                break;
            case '3':
                checkCreds("Student");
                break;
            case '0':
                truncateTable();
                System.exit(0);
                break;
            default:
                refresh();
                print("Invalid character response. Please Try Again !!");
                mainMenu();
                break;
        }
    }

    static void refresh() {
        clearConsole();
        printLogo();
        print(null);
    }

    private static void studentMenu(Person person) {
        refresh();
        print("Welcome Student " + person.getName() + "!!");
        print("Please select your criteria ::");
        print(null);
        print("1. Change password");
        print("2. View merit list total");
        print("3. View marks");
        print("4. Main Menu");
        print("0. Exit");

        char choice = scanner.next().charAt(0);
        switch (choice) {
            case '1':
                refresh();
                Student.changeStudentPassword(person.getId());
                refresh();
                loginAgain();
                break;
            case '2':
                refresh();
                Admin.viewMeritList();
                studentMenu(person);
                refresh();
                break;
            case '3':
                refresh();
                Student.viewMarks(person.getId());
                studentMenu(person);
                refresh();
                break;
            case '4':
                refresh();
                mainMenu();
                refresh();
                break;
            case '0':
                truncateTable();
                System.exit(0);
                break;
            default:
                refresh();
                print("Invalid character response. Please Try Again !!");
                mainMenu();
                break;
        }

    }

    static void teacherMenu(Person person) {
        refresh();
        print("Welcome Teacher " + person.getName() + "!!");
        List<Teacher_Data> tlist = selectAllDataFromTeacherData();
        Teacher_Data teacher = tlist.stream().filter(x -> x.getId() == person.getId()).findAny().get();
        print("Subject :: " + teacher.getSubject());
        print("Please select your criteria ::");
        print(null);
        print("1. Change password");
        print("2. Change marks of student");
        print("3. View merit list");
        print("4. View merit list total");
        print("5. Main Menu");
        print("0. Exit");

        char choice = scanner.next().charAt(0);
        switch (choice) {
            case '1':
                refresh();
                Teacher.changeTeacherPassword(person.getId());
                refresh();
                loginAgain();
                break;
            case '2':
                refresh();
                Teacher.changeStudentMarks(person);
                refresh();
                break;
            case '3':
                refresh();
                Teacher.viewMeritList(teacher.getSubject());
                teacherMenu(person);
                refresh();
                break;
            case '4':
                refresh();
                Admin.viewMeritList();
                teacherMenu(person);
                refresh();
                break;
            case '5':
                refresh();
                mainMenu();
                refresh();
                break;
            case '0':
                truncateTable();
                System.exit(0);
                break;
            default:
                refresh();
                print("Invalid character response. Please Try Again !!");
                mainMenu();
                break;
        }

    }

    static void adminMenu() {
        refresh();
        print("Welcome Admin!!");
        print("Please select your criteria ::");
        print(null);
        print("1. Change admin password");
        print("2. Change teacher password");
        print("3. Change student password");
        print("4. Change student marks");
        print("5. View merit list total");
        print("6. Main Menu");
        print("0. Exit");

        char choice = scanner.next().charAt(0);
        switch (choice) {
            case '1':
                refresh();
                Admin.changeAdminPassword(0);
                refresh();
                loginAgain();
                break;
            case '2':
                refresh();
                Admin.changeTeacherPassword();
                refresh();
                break;
            case '3':
                refresh();
                Admin.changeStudentPassword();
                refresh();
                break;
            case '4':
                refresh();
                Admin.changeStudentMarks();
                refresh();
                break;
            case '5':
                refresh();
                Admin.viewMeritList();
                refresh();
                break;
            case '6':
                mainMenu();
                break;
            case '0':
                truncateTable();
                System.exit(0);
                break;
            default:
                refresh();
                print("Invalid character response. Please Try Again !!");
                mainMenu();
                break;
        }

    }

    static void loginAgain() {
        print("Please login again !!");
        mainMenu();
    }

    private static void checkCreds(String type) {
        print("Enter Credentials for " + type + "::");
        System.out.print("Enter ID :");
        int id = scanner.nextInt();
        System.out.print("Enter Password :");
        scanner.nextLine();
        String password = scanner.nextLine();
        if (AppUtils.checkCreds(id, password) && type.equalsIgnoreCase("Admin")) {
            adminMenu();
        } else if (AppUtils.checkCreds(id, password) && type.equalsIgnoreCase("Teacher")) {
            List<Person> list = selectAllDataFromCMS();
            teacherMenu(list.stream().filter(x -> x.getId() == (id)).findAny().get());
        } else if (AppUtils.checkCreds(id, password) && type.equalsIgnoreCase("Student")) {
            List<Person> list = selectAllDataFromCMS();
            studentMenu(list.stream().filter(x -> x.getId() == (id)).findAny().get());
        } else {
            print("Invalid Credentials. Please try again !!");
            mainMenu();
        }
    }

}
