package controller.clientControllers;

import config.LoaderStage;
import interfacese.impls.CollectionServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Service;

import java.io.IOException;

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

    public void ActionConfirm(ActionEvent actionEvent) throws IOException {
        Service service = (Service) tableViewServices.getSelectionModel().getSelectedItem();
        LoaderStage.viewPaidServices(service);
    }

}
