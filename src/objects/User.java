package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String error = "";
    private String type;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
    private int role;
    private ArrayList<User> users = new ArrayList<User>();

    public User(String firstName, String lastName, String email, String password, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        users.add(this);
        this.type = type;
    }

    public User() {}

    public void setType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
