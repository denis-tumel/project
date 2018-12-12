package controller.mainControllers;

import model.ObjMessage;
import start.StartClient;
import config.LoaderStage;
import interfacese.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.User;
import utils.DialogManager;

import java.io.IOException;
import java.util.Optional;


public class RegistrationController implements Validation {

    public TextField firstName;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public TextField lastName;

    public void ActionRegister(ActionEvent actionEvent) {
        if (validate()) {
            success();
        } else {
            errFillFields();
        }
    }

    private void success() {
        User user = new User(firstName.getText().trim(), lastName.getText().trim(), emailField.getText().trim(), passwordField.getText().trim(), ObjMessage.REGISTER);

        ObjMessage objMessage = new ObjMessage();
        objMessage.setTypeObject(ObjMessage.USER);
        objMessage.setUserObject(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            user = (User)StartClient.getInputStream().readObject();

            if(user.getError().equals("")){
                LoaderStage.getRegistrationStage().close();
                new LoginController().viewClientStage(user , "REGISTER");
                DialogManager.showInfoDialog("инфо", "Регистрация прошла успешно!");
                clearField(firstName, lastName, emailField, passwordField, confirmPassword);
            }else{
                DialogManager.showErrorDialog("ошибка", user.getError());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void clearField(TextField firstName, TextField lastName, TextField emailField, TextField passwordField, TextField confirmPassword) {
        firstName.clear();
        lastName.clear();
        emailField.clear();
        passwordField.clear();
        confirmPassword.clear();
    }

    private void errFillFields() {
        DialogManager.showErrorDialog("ошибка", "Заполните все поля!");
    }

    public void ActionBack(ActionEvent actionEvent) {
        LoaderStage.getRegistrationStage().close();
        LoaderStage.getMainStage().show();
    }

    @Override
    public boolean validate() {
        System.out.println("имя - "+firstName.getText()+" фамилия - "+lastName.getText());
        System.out.println("mail - "+emailField.getText()+"\n пароль - "+passwordField.getText());

        boolean flag = false;
        if((!emailField.getText().equals(""))
                || (!passwordField.getText().equals(""))
                || (!firstName.getText().equals(""))
                || (!lastName.getText().equals(""))
                || (!confirmPassword.getText().equals(""))
                || (passwordField.getText().equals(confirmPassword.getText()))){
            flag = true;
        }
        return flag;
    }

    public void ActionLogin(ActionEvent actionEvent) {
        new MainController().ActionLogin(actionEvent);
        LoaderStage.getRegistrationStage().close();
    }
}
