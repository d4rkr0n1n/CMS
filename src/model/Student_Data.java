package model;

/**
 * Student_Data
 */
public class Student_Data {

    private int id;
    private String name;
    private int maths;
    private int physics;
    private int chemistry;
    private int english;
    private int cs;
    private int total;
    
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public Student_Data(int id, String name, int maths, int physics, int chemistry, int english, int cs, int total) {
        this.id =id;
        this.name = name;
        this.maths = maths;
        this.physics = physics;
        this.chemistry = chemistry;
        this.english = english;
        this.cs = cs;
        this.total = total;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMaths() {
        return maths;
    }
    public void setMaths(int maths) {
        this.maths = maths;
    }
    public int getPhysics() {
        return physics;
    }
    public void setPhysics(int physics) {
        this.physics = physics;
    }
    public int getChemistry() {
        return chemistry;
    }
    public void setChemistry(int chemistry) {
        this.chemistry = chemistry;
    }
    public int getEnglish() {
        return english;
    }
    public void setEnglish(int english) {
        this.english = english;
    }
    public int getCs() {
        return cs;
    }
    public void setCs(int cs) {
        this.cs = cs;
    }


}