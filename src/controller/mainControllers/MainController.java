package controller.mainControllers;

import config.LoaderStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import objects.Lang;
import utils.LocaleManager;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainController extends Observable implements Initializable {

    @FXML
    private ComboBox<Lang> comboBoxLocale;

    private ResourceBundle resourceBundle;
    private static final String RU_CODE = "ru";
    private static final String EN_CODE = "en";
    private LoaderStage loaderStage = new LoaderStage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        initListeners();
        fillLangComboBox();
    }

    private void initListeners() {
        comboBoxLocale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lang selectedLang = (Lang) comboBoxLocale.getSelectionModel().getSelectedItem();
                LocaleManager.setCurrentLang(selectedLang);

                setChanged();
                notifyObservers(selectedLang);
            }
        });
    }

    private void fillLangComboBox() {
        Lang langRU = new Lang(0, RU_CODE, resourceBundle.getString("ru"), LocaleManager.RU_LOCALE);
        Lang langEN = new Lang(1, EN_CODE, resourceBundle.getString("en"), LocaleManager.EN_LOCALE);

        comboBoxLocale.getItems().add(langRU);
        comboBoxLocale.getItems().add(langEN);

        //LocaleManager.setCurrentLang(langRU);
        if(LocaleManager.getCurrentLang() == null)
            comboBoxLocale.getSelectionModel().select(0);
        else
            comboBoxLocale.getSelectionModel().select(LocaleManager.getCurrentLang().getIndex());
    }

    public void ActionLogin(ActionEvent actionEvent){
        loaderStage.createAuthenticationGUI();
        LoaderStage.getMainStage().close();
        LoaderStage.getAuthenticationStage().show();
    }

    public void ActionRegister(ActionEvent actionEvent){
        loaderStage.createRegistrationGUI();
        LoaderStage.getMainStage().close();
        loaderStage.createRegistrationGUI();
        LoaderStage.getRegistrationStage().show();
    }
}
