package controller.adminControllers;

import config.LoaderStage;
import controller.mainControllers.RegistrationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.ObjMessage;
import objects.Doctor;
import start.StartClient;

import java.io.IOException;

public class EditDoctorController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField specialityField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    private ObjMessage objMessage;
    private Doctor doctor;

    public void ActionAddInEditView(ActionEvent actionEvent) {
        setDoctorValue();
        objMessage = createObjMessage(doctor);
        System.out.println("Type - "+doctor.getType());
        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoaderStage.getAdminController().refreshTable();
        clearField();
        ActionCancelInEditView(actionEvent);
    }

    public void ActionCancelInEditView(ActionEvent actionEvent) {
        LoaderStage.getEditDoctorStage().close();
    }

    private void setDoctorValue() {
        doctor.setNameDoctor(nameField.getText());
        doctor.setSurnameDoctor(surnameField.getText());
        doctor.setSpecialty(specialityField.getText());
        doctor.setEmail(emailField.getText());
        doctor.setPassword(passwordField.getText());
    }
    private ObjMessage createObjMessage(Doctor selectedDoctor) {
        doctor = selectedDoctor;
        objMessage = new ObjMessage();
        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);

        return objMessage;
    }

    private void clearField() {
        RegistrationController.clearField(nameField, surnameField, specialityField, emailField, passwordField);
    }

    public void setInformationDoctor(Doctor selectedDoctor) {
        this.doctor = selectedDoctor;
        if(doctor.getId()!= 0) {
            nameField.setText(doctor.getNameDoctor());
            surnameField.setText(doctor.getSurnameDoctor());
            specialityField.setText(doctor.getSpecialty());
            emailField.setText(doctor.getEmail());
            passwordField.setText(doctor.getPassword());
        }
    }
}
