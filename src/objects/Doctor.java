package objects;

import java.io.Serializable;

public class Doctor implements Serializable {

    private int id;
    private String nameDoctor;
    private String surnameDoctor;
    private String type;
    private String email;
    private String password;
    private String specialty;
    private String error = "";
    private int role_id;
    private String day;

    public Doctor(){}

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

    public String getSurnameDoctor() {
        return surnameDoctor;
    }

    public void setSurnameDoctor(String surnameDoctor) {
        this.surnameDoctor = surnameDoctor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError(){
        return error;
    }

    public void setRole(int role_id) {
        this.role_id = role_id;
    }
    public int getRole_id(){
        return role_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay(){
        return day;
    }
    public void setDayWeek(String day) {
        this.day = day;
    }
}
