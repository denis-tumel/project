package objects;

import java.io.Serializable;

public class Service implements Serializable {

    private String nameServices;
    private String nameDoctor;
    private String priceServices;
    private int id;
    private int doctorID;
    private String day;
    private String time;

    public String getNameServices() {
        return nameServices;
    }

    public void setNameServices(String nameServices) {
        this.nameServices = nameServices;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriceServices() {
        return priceServices;
    }

    public void setPriceServices(String priceServices) {
        this.priceServices = priceServices;
    }

    public int getDoctorID(){
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getDay(){
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
