package model;

public class Student extends User {
    public Student() {
        super();
        this.role = "STUDENT";
    }
    
    public Student(int id, String name, String email, String password) {
        super(id, name, email, password, "STUDENT");
    }
}
