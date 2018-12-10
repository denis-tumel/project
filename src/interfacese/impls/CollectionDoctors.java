package interfacese.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ObjMessage;
import objects.Doctor;
import start.StartClient;

import java.io.IOException;
import java.util.ArrayList;

public class CollectionDoctors {
    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();

    public ObservableList<Doctor> getDoctorList() {
        return doctorList;
    }

    public void fillData() {
        ObjMessage objMessage = new ObjMessage();
        Doctor doctor = new Doctor();
        doctor.setType(ObjMessage.VIEW_DOCTOR);

        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            ArrayList<Doctor> doctors = (ArrayList<Doctor>) StartClient.getInputStream().readObject();

            doctorList.clear();
            doctorList.addAll(doctors);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
