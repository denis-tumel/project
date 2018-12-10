package controller.clientControllers;

import interfacese.impls.CollectionServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.Service;

public class PaidServicesController {
    @FXML
    private TableView<Service> tableViewServices;
    @FXML
    private TableColumn<Service, String> service;
    @FXML
    private TableColumn<Service, String> doctor;
    @FXML
    private TableColumn<Service, String> price;

    private CollectionServices collectionServices = new CollectionServices();

    @FXML
    public void initialize(){
        service.setCellValueFactory(new PropertyValueFactory<Service, String>("nameServices"));
        doctor.setCellValueFactory(new PropertyValueFactory<Service, String>("nameDoctor"));
        price.setCellValueFactory(new PropertyValueFactory<Service, String>("priceServices"));

        collectionServices.fillData();

        tableViewServices.setItems(collectionServices.getServiceList());
        tableViewServices.refresh();
    }

    public void ActionConfirm(ActionEvent actionEvent) {
    }

    public void setInformation() {
    }
}
