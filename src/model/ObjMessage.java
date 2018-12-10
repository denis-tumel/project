package model;

import objects.Doctor;
import objects.OrderTicket;
import objects.Service;
import objects.User;

import java.io.Serializable;

public class ObjMessage implements Serializable {

    public static final String
            LOGIN = "login",
            LOGOUT = "logout",
            REGISTER = "register",
            VIEW_DOCTOR = "view_doctor",
            ADD_DOCTOR = "add_doctor",
            EDIT_DOCTOR = "edit_doctor",
            DELETE_DOCTOR = "delete_doctor",
            USER = "user",
            DOCTOR = "doctor",
            ORDER_TICKET = "order_ticket",
            VIEW_ORDER_TICKET = "view_order_ticket",
            UPDATE_ALL = "update_all",
            VIEW_TICKET = "view_ticket",
            VIEW_USERS = "view_users",
            BLOCK_USERS = "block_users",
            UNLOCK_USERS = "unlock_users",
            VIEW_SERVICES = "view_services",
            ADD_SERVICE = "add_service",
            DELETE_SERVICE = "delete_service";

    private String typeObject;
    private User userObject;
    private Doctor doctorObject;
    private OrderTicket orderTicket;
    private Service service;

    public String getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(String typeObject) {
        this.typeObject = typeObject;
    }

    public User getUserObject() {
        return userObject;
    }

    public void setUserObject(User userObject) {
        this.userObject = userObject;
    }

    public Doctor getDoctorObject() {
        return doctorObject;
    }

    public void setDoctorObject(Doctor doctorObject) {
        this.doctorObject = doctorObject;
    }

    public OrderTicket getOrderTicket(){
        return orderTicket;
    }
    public void setTicket(OrderTicket orderTicket) {
        this.orderTicket = orderTicket;
    }

    public Service getServiceObject(){
        return service;
    }
    public void setServiceObject(Service service) {
        this.service = service;
    }
}
