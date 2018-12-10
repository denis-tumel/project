package controller.clientControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderServiceController {
    @FXML
    private ComboBox<String> dayWeek;
    @FXML
    private ComboBox<String> timeDay;
    private ArrayList<String> days = new ArrayList<String>(Arrays.asList(
            "понедельнки",
            "вторник",
            "среда",
            "четверг",
            "пятница"
    ));
    private ArrayList<String> time = new ArrayList<String>(Arrays.asList(
            "12 : 00",
            "13 : 00",
            "14 : 00",
            "15 : 00"
    ));

    @FXML
    public void initialize(){
        dayWeek.setItems(FXCollections.observableArrayList(days));
        timeDay.setItems(FXCollections.observableArrayList(days));

    }

    public void ActionConfirm(ActionEvent actionEvent) {

    }
}
