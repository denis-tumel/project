package controller.mainControllers;

import model.ObjMessage;
import start.StartClient;
import config.LoaderStage;
import interfacese.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import objects.User;
import utils.DialogManager;

import java.io.IOException;
import java.util.Optional;

public class LoginController implements Validation {

    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField emailField;
    @FXML
    public AnchorPane registerLabel;

    private User user;

    public void ActionLogin(ActionEvent actionEvent) {
        if (validate()) {
            success();
        } else {
            errFillFields();
        }
    }

    public void ActionBack(ActionEvent actionEvent) {
        LoaderStage.getAuthenticationStage().close();
        LoaderStage.getMainStage().show();
    }

    private void success() {
        user = new User();
        user.setEmail(emailField.getText().trim());
        user.setPassword(passwordField.getText().trim());
        user.setType(ObjMessage.LOGIN);

        ObjMessage objMessage = new ObjMessage();
        objMessage.setTypeObject(ObjMessage.USER);
        objMessage.setUserObject(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            user = (User) StartClient.getInputStream().readObject();

            if (user != null) {

                switch (user.getRole()){
                    case 1:
                        LoaderStage.getAuthenticationStage().close();
                        viewAdminStage(user);
                        showInformation();
                        clearField();
                        break;
                    case 2:
                        LoaderStage.getAuthenticationStage().close();
                        viewClientStage(user , "login");
                        showInformation();
                        clearField();
                        break;
                    case 3:
                        LoaderStage.getAuthenticationStage().close();
                        viewDoctorStage(user);
                        showInformation();
                        clearField();
                        break;
                    case -1:
                        viewBlockException(user);
                        clearField();
                        break;
                }
            } else{
                errLogin();
                clearField();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void clearField() {
        emailField.clear();
        passwordField.clear();
    }

    private void viewBlockException(User user) {
        DialogManager.showErrorDialog("ошибка", user.getFirstName()+" вы заблокированы!");
    }

    private void viewDoctorStage(User user) {
        LoaderStage.doctorStageView(user);
    }

    private void viewAdminStage(User user) {
        LoaderStage.adminStageView(user);
    }

    void viewClientStage(User user, String type) {
        LoaderStage.clientStageView(user, type);
    }

    private void showInformation() {
        DialogManager.showInfoDialog("инфо", "Авторизация прошла успешно!");
    }

    private void errLogin() {
        DialogManager.showErrorDialog("ошибка", "Непрвильный логин или пароль!");
    }

    private void errFillFields() {
        DialogManager.showErrorDialog("ошибка", "Заполните все поля!");
    }

    @Override
    public boolean validate() {
        System.out.println("логин - " + emailField.getText() + " пароль - " + passwordField.getText());
        boolean flag = false;
        if ((!emailField.getText().equals("")) || (!passwordField.getText().equals(""))) {
            flag = true;
        }
        return flag;
    }

    public void ActionRegister(ActionEvent actionEvent) {
        new MainController().ActionRegister(actionEvent);
        LoaderStage.getAuthenticationStage().close();
    }
}
