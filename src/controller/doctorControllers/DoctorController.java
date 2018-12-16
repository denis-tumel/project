package controller.doctorControllers;

import config.Const;
import config.LoaderStage;
import interfacese.impls.CollectionOrderTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.OrderTicket;
import objects.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        LoaderStage.getAuthenticationStage().show();
    }

    public void setInformation(User user) {
        refreshTable(user);
        helloLabel.setText("Привет: " + user.getFirstName());
    }

    public void ActionSave(ActionEvent actionEvent) {
        saveInformationInFile(tableOrderView.getItems());
    }

    private void saveInformationInFile(ObservableList<OrderTicket> items) {
        int count = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Const.INFO_FILE, true))) {
            for (OrderTicket orderTicket : items) {
                count++;
                String out = count + " день:" + orderTicket.getDay() + ",\nвремя: " + orderTicket.getTime() + ",\nпользователь: " + orderTicket.getUser() + " \n";
                writer.append(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionViewStatistic(ActionEvent actionEvent) {
       LoaderStage.viewPie(tableOrderView.getItems());
    }

}
