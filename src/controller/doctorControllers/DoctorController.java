package controller.doctorControllers;

import config.LoaderStage;
import interfacese.impls.CollectionOrderTicket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.OrderTicket;
import objects.User;

public class DoctorController {
    @FXML
    private TableColumn<OrderTicket, String> day;
    @FXML
    private TableColumn<OrderTicket, String> time;
    @FXML
    private TableColumn<OrderTicket, String> userName;
    @FXML
    private TableView<OrderTicket> tableOrderView;
    @FXML
    private Label helloLabel;
    private CollectionOrderTicket collectionOrderTicket = new CollectionOrderTicket();

    @FXML
    public void initialize(){
    }

    private void refreshTable(User user) {
        day.setCellValueFactory(new PropertyValueFactory<OrderTicket, String>("day"));
        time.setCellValueFactory(new PropertyValueFactory<OrderTicket, String>("time"));
        userName.setCellValueFactory(new PropertyValueFactory<OrderTicket, String>("user"));

        collectionOrderTicket.fillData(user);

        tableOrderView.setItems(collectionOrderTicket.getOrderTicketsList());
        tableOrderView.refresh();
    }

    public void ActionLogout(ActionEvent actionEvent) {
        LoaderStage.getDoctorStage().close();
        LoaderStage.getMainStage().show();
    }

    public void setInformation(User user) {
        refreshTable(user);
        helloLabel.setText("Привет: "+user.getFirstName());
    }

}
