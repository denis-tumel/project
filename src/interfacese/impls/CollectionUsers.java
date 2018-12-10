package interfacese.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ObjMessage;
import objects.Doctor;
import objects.User;
import start.StartClient;

import java.io.IOException;
import java.util.ArrayList;

public class CollectionUsers {
    private ObservableList<User> usersList = FXCollections.observableArrayList();

    public ObservableList<User> getUsersList() {
        return usersList;
    }

    public void fillData() {
        ObjMessage objMessage = new ObjMessage();
        User user = new User();
        user.setType(ObjMessage.VIEW_USERS);

        objMessage.setTypeObject(ObjMessage.USER);
        objMessage.setUserObject(user);

        try {
            StartClient.getOutputStream().writeObject(objMessage);
            StartClient.getOutputStream().flush();

            ArrayList<User> users = (ArrayList<User>) StartClient.getInputStream().readObject();

            usersList.clear();
            usersList.addAll(users);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
