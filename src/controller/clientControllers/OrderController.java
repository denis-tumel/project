package controller.clientControllers;

import config.LoaderStage;
import interfacese.impls.CollectionDoctors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.Doctor;
import objects.User;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderController {
    @FXML
    private ListView<String> listDay;
    @FXML
    private TableView<Doctor> tableDoctorView;
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
    private ArrayList<String> weeks = new ArrayList<String>(Arrays.asList(
            "понедельник",
            "вторник",
            "среда",
            "четверг",
            "пятница"
    ));

    private CollectionDoctors collectionDoctors = new CollectionDoctors();
    private User user;

    @FXML
    public void initialize(){
        listDay.setItems(FXCollections.observableArrayList(weeks));
        listDay.refresh();
        refreshTable();
    }

    private void refreshTable() {
        updateTable(columnName, columnSurname, columnSpecialty, columnEmail, columnPassword, collectionDoctors, tableDoctorView);
    }

    public static void updateTable(TableColumn<Doctor, String> columnName, TableColumn<Doctor, String> columnSurname, TableColumn<Doctor, String> columnSpecialty, TableColumn<Doctor, String> columnEmail, TableColumn<Doctor, String> columnPassword, CollectionDoctors collectionDoctors, TableView<Doctor> tableDoctorView) {
        columnName.setCellValueFactory(new PropertyValueFactory<Doctor, String>("nameDoctor"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Doctor, String>("surnameDoctor"));
        columnSpecialty.setCellValueFactory(new PropertyValueFactory<Doctor, String>("specialty"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Doctor, String>("email"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<Doctor, String>("password"));

        collectionDoctors.fillData();

        tableDoctorView.setItems(collectionDoctors.getDoctorList());
        tableDoctorView.refresh();
    }

    public void ActionConfirm(ActionEvent actionEvent) {
        Doctor selectedDoctor = (Doctor) tableDoctorView.getSelectionModel().getSelectedItem();
        String day = (String) listDay.getSelectionModel().getSelectedItem();
        selectedDoctor.setDayWeek(day);
        LoaderStage.viewTicket(selectedDoctor, user);
    }

    public void ActionBack(ActionEvent actionEvent) {
        LoaderStage.getOrderStage().close();
        LoaderStage.getClientStage().show();
    }

    public void setInformation(User user) {
        this.user = user;
    }
}
