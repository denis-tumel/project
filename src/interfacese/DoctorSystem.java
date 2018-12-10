package interfacese;

import model.ObjMessage;
import objects.Doctor;

public interface DoctorSystem {
    void add(Doctor doctor, ObjMessage objMessage);
    void edit(Doctor doctor, ObjMessage objMessage);
    void delete(Doctor doctor);
}
