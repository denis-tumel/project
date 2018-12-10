package interfacese.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ObjMessage;
import objects.Doctor;
import objects.Service;
import start.StartClient;

import java.io.IOException;
import java.util.ArrayList;

public class CollectionServices {
    private ObservableList<Service> serviceList = FXCollections.observableArrayList();
    private ObservableList<String> doctorList = FXCollections.observableArrayList();

    public void fillData() {
        ObjMessage objMessage = new ObjMessage();
        Doctor doctor = new Doctor();
        doctor.setType(ObjMessage.VIEW_SERVICES);

        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            ArrayList<Service> services = (ArrayList<Service>) StartClient.getInputStream().readObject();

            serviceList.clear();
            serviceList.addAll(services);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getDoctorList() {
        return doctorList;
    }

    public ObservableList<Service> getServiceList() {
        return serviceList;
    }
}
