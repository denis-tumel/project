package controller.adminControllers;

import config.LoaderStage;
import controller.clientControllers.OrderController;
import interfacese.impls.CollectionDoctors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ObjMessage;
import objects.Doctor;
import objects.User;
import start.StartClient;
import utils.DialogManager;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class AdminController implements Observer {
    @FXML
    private Label helloLabel;
    @FXML
    private TableColumn<Doctor, String> columnName;
    @FXML
    private TableColumn<Doctor, String> columnSurname;
    @FXML
    private TableColumn<Doctor, String> columnSpecialty;
    @FXML
    private TableColumn<Doctor, String> columnEmail;
    @FXML
    private TableColumn<Doctor, String> columnPassword;
    @FXML
    private TableView<Doctor> tableDoctorView;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    private CollectionDoctors collectionDoctors = new CollectionDoctors();
    private ObjMessage objMessage;
    private Doctor doctor;

    @FXML
    private void initialize(){
        refreshTable();
    }

    public void ActionLogout(ActionEvent actionEvent) {
        LoaderStage.getAdminStage().close();
        LoaderStage.getAuthenticationStage().show();
    }

    public void setInformation(User user) {
        helloLabel.setText("Привет: "+user.getFirstName());
    }

    public void ActionButtonPressed(ActionEvent actionEvent){
        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }
        Button clickedButton = (Button) source;
        Doctor selectedDoctor = (Doctor) tableDoctorView.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()){
            case "btnAdd":
                addDoctor();
                break;
            case "btnEdit":
                if(selectedDoctor!=null)
                    editDoctor(selectedDoctor);
                else
                    DialogManager.showErrorDialog("ошибка", "Выберите доктора из таблицы!");
                break;
            case "btnDelete":
                if(selectedDoctor!=null)
                    deleteDoctor(selectedDoctor);
                else
                    DialogManager.showErrorDialog("ошибка", "Выберите доктора из таблицы!");
                break;
        }
    }

    private void deleteDoctor(Doctor selectedDoctor) {
        objMessage = createObjMessage(selectedDoctor);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    private void editDoctor(Doctor selectedDoctor) {
        doctor = selectedDoctor;
        doctor.setType(ObjMessage.EDIT_DOCTOR);
        LoaderStage.editDoctorView(doctor);
    }

    private void addDoctor() {
        doctor = new Doctor();
        doctor.setType(ObjMessage.ADD_DOCTOR);
        LoaderStage.editDoctorView(doctor);
    }

    private ObjMessage createObjMessage(Doctor selectedDoctor) {
        doctor = selectedDoctor;
        objMessage = new ObjMessage();
        doctor.setType(ObjMessage.DELETE_DOCTOR);
        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);

        return objMessage;
    }

    public void refreshTable() {
        OrderController.updateTable(columnName, columnSurname, columnSpecialty, columnEmail, columnPassword, collectionDoctors, tableDoctorView);
    }

    public void ActionUpdateTicket(ActionEvent actionEvent) {
        Doctor doctor = new Doctor();
        ObjMessage objMessage = new ObjMessage();
        doctor.setType(ObjMessage.UPDATE_ALL);
        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DialogManager.showInfoDialog("инфо", "Обновление прошло успешно!");
    }

    public void ActionViewUsers(ActionEvent actionEvent) {
        LoaderStage.viewUsers();
    }

    public void ActionAddPaidServices(ActionEvent actionEvent) {
        LoaderStage.addPaidServicesStage();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
