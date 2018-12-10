package interfacese.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ObjMessage;
import objects.Doctor;
import objects.OrderTicket;
import objects.User;
import start.StartClient;

import java.io.IOException;
import java.util.ArrayList;

public class CollectionOrderTicket {
    private ObservableList<OrderTicket> orderTicketsList = FXCollections.observableArrayList();

    public ObservableList<OrderTicket> getOrderTicketsList() {
        return orderTicketsList;
    }

    public void fillData(User user) {
        ObjMessage objMessage = new ObjMessage();
        Doctor doctor = new Doctor();
        doctor.setType(ObjMessage.VIEW_TICKET);

        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);
        objMessage.setUserObject(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            ArrayList<OrderTicket> orderTickets = (ArrayList<OrderTicket>) StartClient.getInputStream().readObject();
            orderTicketsList.clear();
            orderTicketsList.addAll(orderTickets);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
