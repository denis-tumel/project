package controller.clientControllers;

import config.LoaderStage;
import interfacese.impls.CollectionServices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Service;
import objects.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PaidServicesController {
    @FXML
    private ComboBox<String> day;
    @FXML
    private ComboBox<String> time;
    @FXML
    private TableView<Service> tableViewServices;
    @FXML
    private TableColumn<Service, String> service;
    @FXML
    private TableColumn<Service, String> doctor;
    @FXML
    private TableColumn<Service, String> price;
    private ArrayList<String> days = new ArrayList<String>(Arrays.asList(
            "понедельнки",
            "вторник",
            "среда",
            "четверг",
            "пятница"
    ));
    private ArrayList<String> times = new ArrayList<String>(Arrays.asList(
            "12 : 00",
            "13 : 00",
            "14 : 00",
            "15 : 00"
    ));

    private CollectionServices collectionServices = new CollectionServices();
    private User user;

    @FXML
    public void initialize(){
        day.setItems(FXCollections.observableArrayList(days));
        time.setItems(FXCollections.observableArrayList(times));

        service.setCellValueFactory(new PropertyValueFactory<Service, String>("nameServices"));
        doctor.setCellValueFactory(new PropertyValueFactory<Service, String>("nameDoctor"));
        price.setCellValueFactory(new PropertyValueFactory<Service, String>("priceServices"));

        collectionServices.fillData();

        tableViewServices.setItems(collectionServices.getServiceList());
        tableViewServices.refresh();
    }

    public void ActionConfirm(ActionEvent actionEvent) throws IOException {
        Service service = (Service) tableViewServices.getSelectionModel().getSelectedItem();
        String day = (String) this.day.getSelectionModel().getSelectedItem();
        String time = (String) this.time.getSelectionModel().getSelectedItem();
        LoaderStage.getPaidStage().close();
        LoaderStage.viewConfirmOrderService(service, day, time, user);
    }

    public void setInformation(User user) {
        this.user = user;
    }
}
