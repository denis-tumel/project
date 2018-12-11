package controller.clientControllers;

import config.Const;
import config.LoaderStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import model.ObjMessage;
import objects.Doctor;
import objects.Service;
import objects.User;
import start.StartClient;

import java.io.IOException;
import java.util.Optional;

public class OrderConfirmServiceController {
    @FXML
    private Label nameService;
    @FXML
    private Label nameDoctor;
    @FXML
    private Label price;
    @FXML
    private Label day;
    @FXML
    private Label time;

    private ObjMessage objMessage;
    private User user;
    private Service service;

    public void setInformation(Service service, String day, String time, User user) {
        this.user = user;
        this.service = service;
        service.setDay(day);
        service.setTime(time);
        nameService.setText(service.getNameServices());
        nameDoctor.setText(service.getNameDoctor());
        price.setText(service.getPriceServices());
        this.day.setText(day);
        this.time.setText(time);
    }

    public void ActionConfirm(ActionEvent actionEvent) {
        objMessage = createObjMessage();
        System.out.println(objMessage.getUserObject().getType());
        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Заказ успешно оформлен!");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                LoaderStage.getOrderConfirmServiceStage().close();
                LoaderStage.getClientStage().show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjMessage createObjMessage() {
        objMessage = new ObjMessage();
        objMessage.setTypeObject(ObjMessage.DOCTOR);
        Doctor doctor = new Doctor();
        doctor.setType(Const.ORDER_PAID_SERVICE);

        objMessage.setDoctorObject(doctor);
        objMessage.setServiceObject(service);
        objMessage.setUserObject(user);

        return objMessage;
    }

    public void ActionCancel(ActionEvent actionEvent) {
        LoaderStage.getOrderConfirmServiceStage().close();
        LoaderStage.getPaidStage().show();
    }
}
