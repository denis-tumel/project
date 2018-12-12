package config;

import controller.adminControllers.EditDoctorController;
import controller.adminControllers.AddPaidServicesController;
import controller.clientControllers.*;
import controller.doctorControllers.PieChartController;
import controller.mainControllers.MainController;
import javafx.collections.ObservableList;
import objects.Doctor;
import objects.OrderTicket;
import objects.Service;
import start.StartClient;
import controller.adminControllers.AdminController;
import controller.doctorControllers.DoctorController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.User;
import utils.LocaleManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoaderStage {
    private static AdminController adminController;
    private static EditDoctorController editDoctorController;
    private static DoctorController doctorController;
    private static AddPaidServicesController addPaidServicesController;
    private static ClientController clientController;
    private static InformationViewController informationViewController;
    private static OrderController orderController;
    private static PaidServicesController paidServicesController;
    private static OrderConfirmServiceController orderConfirmServiceController;
    private static PieChartController pieChartController;
    private static MainController mainController;
    private static Parent root;
    private static Scene scene;
    private static Stage orderPaidServiceStage;
    private static Stage orderConfirmServiceStage;
    private static Stage usersStage;
    private static Stage paidStage;
    private static Stage addPaidStage;
    private static Stage orderStage;
    private static Stage mainStage;
    private static Stage authenticationStage;
    private static Stage registrationStage;
    private static Stage clientStage;
    private static Stage doctorStage;
    private static Stage adminStage;
    private static Stage editDoctorStage;
    private static Stage viewStage;
    private static Stage viewPieStage;
    LocaleManager localeManager = new LocaleManager();

    public static void mainView(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoaderStage.class.getResource(Const.PATH_MAIN_VIEW));
            LocaleManager.getCurrentLang();
            loader.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("en")));

            root = loader.load();
            setMainStage(primaryStage);
            primaryStage.setTitle(loader.getResources().getString("key.main.title"));
            scene = new Scene(root, 680, 550);
            scene.getStylesheets().add(LoaderStage.class.getResource(Const.PATH_MAIN_CSS).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            eventCloseClient(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void authenticationView() {
        try {
            if (authenticationStage == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_AUTHENTICATION));
                loader.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("en")));
                root = loader.load();
                scene = new Scene(root);
                authenticationStage = new Stage();
                setAuthenticationStage(authenticationStage);
                authenticationStage.setTitle(loader.getResources().getString("key.main.authorization"));
                authenticationStage.setResizable(false);
                authenticationStage.setScene(scene);
                authenticationStage.initModality(Modality.WINDOW_MODAL);
                authenticationStage.initOwner(mainStage);
            }
            mainStage.hide();
            authenticationStage.show();
            eventCloseClient(authenticationStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registrationView() {
        try {
            if (registrationStage == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_REGISTRATION));
                loader.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("en")));
                root = loader.load();
                scene = new Scene(root);
                registrationStage = new Stage();
                setRegistrationStage(registrationStage);
                registrationStage.setTitle(loader.getResources().getString("key.main.register"));
                registrationStage.setResizable(false);
                registrationStage.setScene(scene);
                registrationStage.initModality(Modality.WINDOW_MODAL);
                registrationStage.initOwner(mainStage);
            }
            mainStage.hide();
            registrationStage.show();
            eventCloseClient(registrationStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void adminStageView(User user) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (adminStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_ADMIN_STAGE));
                root = loader.load();
                scene = new Scene(root);
                adminStage = new Stage();
                setAdminStage(adminStage);
                adminStage.setTitle("Админ");
                adminStage.setMinWidth(826);
                adminStage.setMinHeight(655);
                adminStage.setScene(scene);
                adminStage.initModality(Modality.WINDOW_MODAL);
                adminStage.initOwner(mainStage);
                adminController = (AdminController) loader.getController();
            }
            adminController.setInformation(user);
            authenticationStage.hide();
            adminStage.show();
            eventCloseClient(adminStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editDoctorView(Doctor doctor) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (editDoctorStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_ADD_DOCTOR));
                root = loader.load();
                scene = new Scene(root);
                editDoctorStage = new Stage();
                setEditDoctorStage(editDoctorStage);
                editDoctorStage.setTitle("Окно изменение");
                editDoctorStage.setResizable(false);
                editDoctorStage.setScene(scene);
                editDoctorStage.initModality(Modality.WINDOW_MODAL);
                editDoctorStage.initOwner(mainStage);
                editDoctorController = (EditDoctorController) loader.getController();
            }
            editDoctorController.setInformationDoctor(doctor);
            editDoctorStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doctorStageView(User user) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (doctorStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_DOCTOR_STAGE));
                root = loader.load();
                scene = new Scene(root);
                doctorStage = new Stage();
                setDoctorStage(doctorStage);
                doctorStage.setTitle("Страница доктора");
                doctorStage.setScene(scene);
                doctorStage.initModality(Modality.WINDOW_MODAL);
                doctorStage.initOwner(mainStage);
                doctorController = (DoctorController) loader.getController();
            }
            doctorController.setInformation(user);
            authenticationStage.hide();
            doctorStage.show();
            eventCloseClient(doctorStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clientStageView(User user, String type) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (clientStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_CLIENT_VIEW));
                root = loader.load();
                scene = new Scene(root);
                clientStage = new Stage();
                setClientStage(clientStage);
                clientStage.setTitle("Страница клиента");
                clientStage.setScene(scene);
                clientStage.initModality(Modality.WINDOW_MODAL);
                clientStage.initOwner(mainStage);
                clientController = (ClientController) loader.getController();
            }
            clientController.setInformation(user);
            if (type.equals("login")) {
                authenticationStage.hide();
            } else {
                registrationStage.hide();
            }
            clientStage.show();
            eventCloseClient(clientStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void orderView(User user) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (orderStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_ORDER_VIEW));
                root = loader.load();
                scene = new Scene(root);
                orderStage = new Stage();
                setOrderStage(orderStage);
                setClientStage(clientStage);
                orderStage.setTitle("Страница заказа");
                orderStage.setScene(scene);
                orderController = (OrderController) loader.getController();
            }
            orderController.setInformation(user);
            clientStage.close();
            orderStage.show();
            eventCloseClient(orderStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewTicket(Doctor selectedDoctor, User user) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (viewStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_INFORMATION_VIEW));
                root = loader.load();
                scene = new Scene(root);
                viewStage = new Stage();
                viewStage.setTitle("заказ");
                viewStage.setScene(scene);
                viewStage.initModality(Modality.WINDOW_MODAL);
                viewStage.initOwner(orderStage);
                informationViewController = (InformationViewController) loader.getController();
            }
            informationViewController.setInformation(selectedDoctor, user);
            orderStage.close();
            viewStage.showAndWait();
            eventCloseClient(viewStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPaidServicesStage() {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (addPaidStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_ADD_PAID_SERVICES));
                root = loader.load();
                scene = new Scene(root);
                addPaidStage = new Stage();
                addPaidStage.setTitle("Добавление платные услуги");
                addPaidStage.setScene(scene);
                addPaidStage.initModality(Modality.WINDOW_MODAL);
                addPaidStage.initOwner(clientStage);
                addPaidServicesController = (AddPaidServicesController) loader.getController();
            }
            addPaidServicesController.setInformation();
            addPaidStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void paidServicesStage(User user) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (paidStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_PAID_SERVICES));
                root = loader.load();
                scene = new Scene(root);
                paidStage = new Stage();
                setPaidStage(paidStage);
                paidStage.setTitle("Платные услуги");
                paidStage.setScene(scene);
                paidStage.initModality(Modality.WINDOW_MODAL);
                paidStage.initOwner(clientStage);
                paidServicesController = (PaidServicesController) loader.getController();
            }
            paidServicesController.setInformation(user);
            paidStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewUsers() {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (usersStage == null) {
                loader.setLocation(LoaderStage.class.getResource(Const.PATH_USERS_VIEW));
                root = loader.load();
                scene = new Scene(root);
                usersStage = new Stage();
                usersStage.setTitle("Работа с пользователями");
                usersStage.setScene(scene);
                usersStage.initModality(Modality.WINDOW_MODAL);
                usersStage.initOwner(clientStage);
            }
            usersStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewConfirmOrderService(Service service, String day, String time, User user) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            if(orderConfirmServiceStage == null){
                fxmlLoader.setLocation(LoaderStage.class.getResource(Const.PATH_ORDER_CONFIRM));
                root = fxmlLoader.load();
                scene = new Scene(root);
                orderConfirmServiceStage = new Stage();
                setOrderConfirmServiceStage(orderConfirmServiceStage);
                orderConfirmServiceStage.setTitle("заказ платной услуги");
                orderConfirmServiceStage.setScene(scene);
                orderConfirmServiceController = fxmlLoader.getController();
            }
            orderConfirmServiceController.setInformation(service, day, time, user);
            orderConfirmServiceStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewPie(ObservableList<OrderTicket> items) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            if(viewPieStage == null){
                fxmlLoader.setLocation(LoaderStage.class.getResource(Const.PATH_PIE));
                root = fxmlLoader.load();
                scene = new Scene(root);
                viewPieStage = new Stage();
                setViewPieStage(viewPieStage);
                viewPieStage.setTitle("круговая диаграмма");
                viewPieStage.setScene(scene);
                pieChartController = fxmlLoader.getController();
            }
            pieChartController.setInformation(items);
            viewPieStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void eventCloseClient(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                StartClient.close();
            }
        });
    }

    public static void setMainStage(Stage primaryStage) {
        mainStage = primaryStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Stage getAuthenticationStage() {
        return authenticationStage;
    }

    public static void setAuthenticationStage(Stage authenticationStage) {
        LoaderStage.authenticationStage = authenticationStage;
    }

    public static Stage getRegistrationStage() {
        return registrationStage;
    }

    public static void setRegistrationStage(Stage registrationStage) {
        LoaderStage.registrationStage = registrationStage;
    }

    public static Stage getAddPaidStage() {
        return addPaidStage;
    }

    public static void setAddPaidStage(Stage addPaidStage) {
        LoaderStage.addPaidStage = addPaidStage;
    }

    public static Stage getClientStage() {
        return clientStage;
    }

    public static void setClientStage(Stage clientStage) {
        LoaderStage.clientStage = clientStage;
    }

    public static Stage getDoctorStage() {
        return doctorStage;
    }

    public static void setDoctorStage(Stage doctorStage) {
        LoaderStage.doctorStage = doctorStage;
    }

    public static Stage getAdminStage() {
        return adminStage;
    }

    public static void setAdminStage(Stage adminStage) {
        LoaderStage.adminStage = adminStage;
    }

    public static Stage getEditDoctorStage() {
        return editDoctorStage;
    }

    public static void setEditDoctorStage(Stage editDoctorStage) {
        LoaderStage.editDoctorStage = editDoctorStage;
    }

    public static Stage getOrderStage() {
        return orderStage;
    }

    public static void setOrderStage(Stage orderStage) {
        LoaderStage.orderStage = orderStage;
    }

    public static AdminController getAdminController() {
        return adminController;
    }

    public static Stage getViewStage() {
        return viewStage;
    }

    public static void setViewStage(Stage viewStage) {
        LoaderStage.viewStage = viewStage;
    }

    public static ClientController getClientController() {
        return clientController;
    }

    public static Stage getPaidStage() {
        return paidStage;
    }

    public static void setPaidStage(Stage paidStage) {
        LoaderStage.paidStage = paidStage;
    }

    public static Stage getOrderConfirmServiceStage() {
        return orderConfirmServiceStage;
    }

    public static void setOrderConfirmServiceStage(Stage orderConfirmServiceStage) {
        LoaderStage.orderConfirmServiceStage = orderConfirmServiceStage;
    }

    public static Stage getViewPieStage() {
        return viewPieStage;
    }

    public static void setViewPieStage(Stage viewPieStage) {
        LoaderStage.viewPieStage = viewPieStage;
    }
}
