package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Person;
import model.Student_Data;
import model.Teacher_Data;
import util.AppUtils;

public class DBMS {
    public static Connection conn = null;

    public static void connect() {
        try {
            String url = "jdbc:sqlite:CMS.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createDb() {
        String sql = "CREATE TABLE IF NOT EXISTS CMS ( " +
                "id integer PRIMARY KEY AUTOINCREMENT, " +
                "name text NOT NULL, " +
                "type text NOT NULL, " +
                "password text NOT NULL);";
        executeStatement(sql);
        sql = "CREATE TABLE IF NOT EXISTS Student_Data ( " +
                "ID integer PRIMARY KEY, " +
                "Name text NOT NULL, " +
                "Maths integer NOT NULL, " +
                "Physics integer NOT NULL, " +
                "Chemistry integer NOT NULL, " +
                "English integer NOT NULL, " +
                "CS integer NOT NULL, " +
                "Total integer NOT NULL);";
        executeStatement(sql);
        sql = "CREATE TABLE IF NOT EXISTS Teacher_Data ( " +
                "ID integer PRIMARY KEY, " +
                "Name text NOT NULL, " +
                "Subject integer NOT NULL);";
        executeStatement(sql);
    }

    public static void truncateTable() {
        executeStatement("DROP TABLE IF EXISTS CMS");
        executeStatement("DROP TABLE IF EXISTS Student_Data");
        executeStatement("DROP TABLE IF EXISTS Teacher_Data");

    }

    private static void executeStatement(String sql) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMockData() {
        insertDataIntoCMS(new Person("Admin", "Admin", AppUtils.encrypt("Admin", "Admin")));
        insertDataIntoCMS(new Person("Indira Natt", "Teacher", AppUtils.encrypt("Indira", "Teacher")));
        insertDataIntoCMS(new Person("Prasad Dada", "Teacher", AppUtils.encrypt("Prasad", "Teacher")));
        insertDataIntoCMS(new Person("Sushila Iyer", "Teacher", AppUtils.encrypt("Sushila", "Teacher")));
        insertDataIntoCMS(new Person("Hrithik Kalita", "Teacher", AppUtils.encrypt("Hrithik", "Teacher")));
        insertDataIntoCMS(new Person("Lilavati Narang", "Teacher", AppUtils.encrypt("Lilavati", "Teacher")));
        List<String> list = Arrays.asList("Akanksha Sem", "Gurdeep Kota", "Vipul Andra", "Kushal Oak", "Tejas Dutta",
                "Nilam Pandit", "Chanda Chahal", "Uma Bhatt", "Dayaram Lanka", "Kabir Keer");
        list.forEach(x -> {
            insertDataIntoCMS(new Person(x, "Student", AppUtils.encrypt(x.split(" ")[0], "Student")));
        });
        insertAllDataIntoStudentData();
        insertAllDataIntoTeacherData();
        
    }

    private static void insertAllDataIntoTeacherData() {
        List<Person> list = selectAllDataFromCMS();
        list.forEach(x -> { 
            if (x.getType().equalsIgnoreCase("Teacher")) {
                if(x.getId()==2){
                    insertDataIntoTeacherData(new Teacher_Data(x.getId(),
                            x.getName(),
                            "Maths"));
                } else if (x.getId()==3){
                    insertDataIntoTeacherData(new Teacher_Data(x.getId(),
                            x.getName(),
                            "Physics"));
                } else if (x.getId()==4){
                    insertDataIntoTeacherData(new Teacher_Data(x.getId(),
                            x.getName(),
                            "Chemistry"));
                } else if (x.getId()==5){
                    insertDataIntoTeacherData(new Teacher_Data(x.getId(),
                            x.getName(),
                            "English"));
                } else if (x.getId()==6){
                    insertDataIntoTeacherData(new Teacher_Data(x.getId(),
                            x.getName(),
                            "CS"));
                }
            }
        });
    }

    private static void insertDataIntoTeacherData(Teacher_Data teacher_Data) {
        String sql = "insert into Teacher_Data VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, teacher_Data.getId());
            pstmt.setString(2, teacher_Data.getName());
            pstmt.setString(3, teacher_Data.getSubject());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertAllDataIntoStudentData() {
        List<Person> list = selectAllDataFromCMS();
        list.forEach(x -> {
            if (x.getType().equalsIgnoreCase("Student")) {
                insertDataIntoStudenData(new Student_Data(x.getId(),
                        x.getName(),
                        AppUtils.getRandomMarks(),
                        AppUtils.getRandomMarks(),
                        AppUtils.getRandomMarks(),
                        AppUtils.getRandomMarks(),
                        AppUtils.getRandomMarks(),0));
            }
        });
    }

    private static void insertDataIntoStudenData(Student_Data student_Data) {
        String sql = "insert into Student_Data VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student_Data.getId());
            pstmt.setString(2, student_Data.getName());
            pstmt.setInt(3, student_Data.getMaths());
            pstmt.setInt(4, student_Data.getPhysics());
            pstmt.setInt(5, student_Data.getChemistry());
            pstmt.setInt(6, student_Data.getEnglish());
            pstmt.setInt(7, student_Data.getCs());
            pstmt.setInt(8, (student_Data.getMaths() + student_Data.getPhysics() + student_Data.getChemistry()
                    + student_Data.getEnglish() + student_Data.getCs()) / 5);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static List<Person> selectAllDataFromCMS() {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM CMS";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Person(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("password")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public static List<Student_Data> selectAllDataFromStudentData() {
        List<Student_Data> list = new ArrayList<>();
        String sql = "SELECT * FROM Student_Data";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Student_Data(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getInt("Maths"),
                        rs.getInt("Physics"),
                        rs.getInt("Chemistry"),
                        rs.getInt("English"),
                        rs.getInt("CS"),
                        rs.getInt("Total")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public static List<Teacher_Data> selectAllDataFromTeacherData() {
        List<Teacher_Data> list = new ArrayList<>();
        String sql = "SELECT * FROM Teacher_Data";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Teacher_Data(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Subject")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public static void insertDataIntoCMS(Person person) {
        String sql = "insert into CMS VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // pstmt.setInt(1, person.getId());
            pstmt.setString(2, person.getName());
            pstmt.setString(3, person.getType());
            pstmt.setString(4, person.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateDataIntoCMS(Person person) {
        String sql = "UPDATE CMS SET password = ? where id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, person.getPassword());
            pstmt.setInt(2, person.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateDataIntoStudentData(Student_Data student_Data) {
        String sql = "UPDATE Student_Data SET Maths = ?, Physics = ?,Chemistry = ?,English = ?,CS = ?, Total = ? where id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // pstmt.setString(2, student_Data.getName());
            pstmt.setInt(1, student_Data.getMaths());
            pstmt.setInt(2, student_Data.getPhysics());
            pstmt.setInt(3, student_Data.getChemistry());
            pstmt.setInt(4, student_Data.getEnglish());
            pstmt.setInt(5, student_Data.getCs());
            pstmt.setInt(6, (student_Data.getMaths() + student_Data.getPhysics() + student_Data.getChemistry()
            + student_Data.getEnglish() + student_Data.getCs()) / 5);
            pstmt.setInt(7, student_Data.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
