package main;

import java.util.*;
import model.*;
import static db.DBMS.*;
import static util.AppUtils.*;

public class Student {
    public static Scanner scanner = new Scanner(System.in);

    public static void changeStudentPassword(int id) {
        changePassword(id);
    }

    public static void viewMarks(int id) {
        print("Name\t\tMaths\tPhysics\t\tChemistry\tEnglish\t\tCS\tTotal");
        List<Student_Data> list = selectAllDataFromStudentData();
        list.forEach(x -> {
            if (x.getId() == id) {
                System.out
                        .println(x.getName() + "\t" + x.getMaths() + "\t" + x.getPhysics() + "\t\t" + x.getChemistry()
                                + "\t\t" + x.getEnglish() + "\t\t" + x.getCs() + "\t" + x.getTotal() + "");
            }
        });

        scanner.nextLine();
    }

}
