package controller.clientControllers;

import config.LoaderStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import model.ObjMessage;
import objects.Doctor;
import objects.OrderTicket;
import objects.User;
import start.StartClient;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController {

    @FXML
    private TextArea textAreaOrder;
    @FXML
    private Label helloLabel;
    private User user;
    private ObjMessage objMessage;
    private int countTicket;
    private String information;

    @FXML
    public void initialize(){

    }

    private void fillData(User user) {
        ArrayList<OrderTicket> orderTickets;
        objMessage = new ObjMessage();
        user.setType(ObjMessage.VIEW_ORDER_TICKET);

        objMessage.setTypeObject(ObjMessage.USER);
        objMessage.setUserObject(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            orderTickets = (ArrayList<OrderTicket>) StartClient.getInputStream().readObject();
            textAreaOrder.clear();
            for (OrderTicket info : orderTickets){
                countTicket = info.getNumberTicket();
                information = "\n\tТалон № "+countTicket+"\nДень - "+info.getDay()+"\nВремя - "+info.getTime()+"\nВрач - "+info.getDoctorName()+
                        " "+info.getDoctorSurname()+"\n\t"+info.getDoctorSpecialty()+"\nПациент - "+info.getUser()+
                        "\n____________________________";
                textAreaOrder.appendText(information);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ActionLogout(ActionEvent actionEvent) {
        LoaderStage.getClientStage().close();
        LoaderStage.getMainStage().show();
    }

    public void setInformation(User user) {
        this.user = user;
        fillData(user);
        helloLabel.setText("Привет: " + user.getFirstName());
    }

    public void ActionOrder(ActionEvent actionEvent) {
        LoaderStage.orderView(user);
    }

    public void ActionViewPaidServices(ActionEvent actionEvent) {
        LoaderStage.paidServicesStage(user);
    }
}
