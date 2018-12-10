package objects;

import java.io.Serializable;

public class OrderTicket implements Serializable {
    private String day;
    private String time;
    private String user;
    private String doctorName;
    private String doctorSurname;
    private String doctorSpecialty;
    private int numberTicket;
    private boolean flag;
    private int userID;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDoctorSurname(){
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public String getDoctorSpecialty(){
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    public String getDoctorName(){
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getNumberTicket(){
        return numberTicket;
    }

    public void setNumberTicket(int numberTicket) {
        this.numberTicket = numberTicket;
    }

    public boolean getFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getUserID(){
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setType(Object o) {
    }
}
