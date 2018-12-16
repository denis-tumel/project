package start;

import config.Const;
import config.LoaderStage;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ObjMessage;
import objects.Lang;
import objects.User;
import utils.LocaleManager;

import java.io.*;
import java.net.*;

public class StartClient extends Application {

    private static Socket clientSocket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    @Override
    public void start(Stage primaryStage){
        LoaderStage loaderStage = new LoaderStage();
        Lang langRU = new Lang(0, "ru", "Русский", LocaleManager.RU_LOCALE);
        LocaleManager.setCurrentLang(langRU);
        loaderStage.createMainGUI(LocaleManager.RU_LOCALE, primaryStage);
        LoaderStage.getMainStage().show();
    }

    public static void main(String[] args) {
        createClientSocket();
        launch(args);
    }

    private static void createClientSocket() {
        try {
            clientSocket = new Socket(Const.HOST, Const.PORT);
            System.out.println("Connection accepted " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public static ObjectInputStream getInputStream() {
        return inputStream;
    }

    public static void close() {
        try {
            User user = new User();
            user.setType(ObjMessage.LOGOUT);

            ObjMessage objMessage = new ObjMessage();
            objMessage.setTypeObject(ObjMessage.USER);
            objMessage.setUserObject(user);

            outputStream.writeObject(objMessage);

            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            if (clientSocket != null) clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




