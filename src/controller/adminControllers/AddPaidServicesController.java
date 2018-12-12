package controller.adminControllers;

import config.LoaderStage;
import interfacese.impls.CollectionDoctors;
import interfacese.impls.CollectionServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.ObjMessage;
import objects.Doctor;
import objects.Service;
import start.StartClient;
import utils.DialogManager;

import java.io.IOException;
import java.util.ArrayList;

public class AddPaidServicesController {
    @FXML
    private TableView<Doctor> tableDoctor;
    @FXML
    private TableColumn<Doctor, String> doctorNameColumn;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Service> tableServices;
    @FXML
    private TableColumn<Service, String> servicesColumn;
    @FXML
    private TableColumn<Service, String> doctorColumn;
    @FXML
    private TableColumn<Service, String> priceColumn;
    @FXML
    private TextField services;
    @FXML
    private TextField price;

    private CollectionServices collectionServices = new CollectionServices();
    private CollectionDoctors collectionDoctors = new CollectionDoctors();
    private ObjMessage objMessage;
    private Doctor doctor;
    private Service service;

    @FXML
    public void initialize(){
        refreshTable();
    }

    private void refreshTable() {
        servicesColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("nameServices"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("nameDoctor"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("priceServices"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<Doctor, String>("nameDoctor"));

        collectionServices.fillData();
        collectionDoctors.fillData();

        tableDoctor.setItems(collectionDoctors.getDoctorList());
        tableDoctor.refresh();
        tableServices.setItems(collectionServices.getServiceList());
        tableServices.refresh();
    }

    public void setInformation() {
    }

    public void ActionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if(!(source instanceof Button)){
            return;
        }
        Button clickedButton = (Button) source;
        Doctor selectedDoctor = (Doctor) tableDoctor.getSelectionModel().getSelectedItem();
        Service selectedService = (Service) tableServices.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()){
            case "btnAdd":
                objMessage = createObjMessage(selectedDoctor);

                try {
                    StartClient.getOutputStream().writeObject(objMessage);
                    StartClient.getOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clearField();
                refreshTable();
                break;
            case "btnDelete":
                if(selectedService != null)
                    delete(selectedService);
                else{
                    DialogManager.showErrorDialog("ошибка", "Выберите серивс из таблицы!");
                }
                break;
        }
    }

    private void clearField() {
        services.clear();
        price.clear();
    }

    private void delete(Service selectedService) {
        objMessage = new ObjMessage();

        doctor = new Doctor();
        service = selectedService;
        doctor.setId(selectedService.getDoctorID());
        doctor.setType(ObjMessage.DELETE_SERVICE);

        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);
        objMessage.setServiceObject(service);
        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    private ObjMessage createObjMessage(Doctor selectedDoctor) {
        objMessage = new ObjMessage();

        doctor = selectedDoctor;
        doctor.setType(ObjMessage.ADD_SERVICE);
        service = new Service();
        service.setNameServices(services.getText());
        service.setPriceServices(price.getText());
        service.setNameDoctor(doctor.getNameDoctor());

        objMessage.setTypeObject(ObjMessage.DOCTOR);
        objMessage.setDoctorObject(doctor);
        objMessage.setServiceObject(service);

        return objMessage;
    }

}
