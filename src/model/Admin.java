package model;

public class Admin extends User {
    public Admin() {
        super();
        this.role = "ADMIN";
    }
    
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN");
    }
}
