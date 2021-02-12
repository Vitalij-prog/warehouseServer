package entities;

import java.io.Serializable;

public class User implements Serializable {

    int id;
    String userName;
    String password;
    String role;
    String status;

    public User(int id, String userName, String password, String role, String status){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public User(int id, String userName, String role){
        this.id = id;
        this.userName = userName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}