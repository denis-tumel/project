package controller.adminControllers;

import config.LoaderStage;
import interfacese.impls.CollectionUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ObjMessage;
import objects.User;
import start.StartClient;
import utils.DialogManager;

import java.io.IOException;


public class AdminViewUsersController {
    @FXML
    private Button block;
    @FXML
    private Button unlock;
    @FXML
    private TableView<User> tableViewUser;
    @FXML
    private TableColumn<User, Integer> role_id;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> surname;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> password;

    @FXML
    public void initialize() {
        refreshTable();
    }

    private CollectionUsers collectionUsers = new CollectionUsers();
    private ObjMessage objMessage;
    private User user;

    private void refreshTable() {
        role_id.setCellValueFactory(new PropertyValueFactory<User, Integer>("role"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        surname.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

        collectionUsers.fillData();

        tableViewUser.setItems(collectionUsers.getUsersList());
        tableViewUser.refresh();
    }

    public void ActionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        User selectedUser = (User) tableViewUser.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "block":
                if (selectedUser != null)
                    blockUser(selectedUser);
                else
                    DialogManager.showErrorDialog("ошибка", "Выберите пользователя из таблицы!");
                break;
            case "unlock":
                if (selectedUser != null)
                    unlockUser(selectedUser);
                else
                    DialogManager.showErrorDialog("ошибка", "Выберите пользователя из таблицы!");
                break;
        }
    }

    private void blockUser(User selectedUser) {
        user = selectedUser;
        user.setType(ObjMessage.BLOCK_USERS);
        objMessage = createObjMessage(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DialogManager.showInfoDialog("инфо", "Пользователь заблокирован!");
        refreshTable();
    }

    private void unlockUser(User selectedUser) {
        user = selectedUser;
        user.setType(ObjMessage.UNLOCK_USERS);
        objMessage = createObjMessage(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DialogManager.showInfoDialog("инфо", "Пользователь разаблокирован!");
        refreshTable();
    }

    private ObjMessage createObjMessage(User selectedUser) {
        user = selectedUser;
        objMessage = new ObjMessage();
        objMessage.setTypeObject(ObjMessage.USER);
        objMessage.setUserObject(user);

        return objMessage;
    }
}
