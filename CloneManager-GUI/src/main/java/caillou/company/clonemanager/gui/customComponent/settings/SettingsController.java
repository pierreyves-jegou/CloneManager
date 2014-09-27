/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.settings;

import caillou.company.clonemanager.background.bean.contract.EventBusProvider;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.WindowsPreferredDimensions;
import caillou.company.clonemanager.gui.bean.Language;
import caillou.company.clonemanager.gui.customComponent.common.contract.DialogWrapper;
import caillou.company.clonemanager.gui.customComponent.taskchoice.LanguageListCell;
import caillou.company.clonemanager.gui.event.LanguageEvent;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import com.google.common.eventbus.EventBus;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialog;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class SettingsController implements Initializable, DialogWrapper, EventBusProvider {

    @FXML
    private ComboBox<Language> languageComboBoxId;

    private Dialog wrappingDialog;
    
    protected final EventBus eventBus = new EventBus("REFRESH_BUS");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageComboBoxId.setCellFactory(new Callback<ListView<Language>, ListCell<Language>>() {
            @Override
            public ListCell<Language> call(ListView<Language> language) {
                return new LanguageListCell();
            }
        });

        languageComboBoxId.setButtonCell(new LanguageListCell());

        Language frenchLanguage = new Language(Locale.FRENCH);
        Language englishLanguage = new Language(Locale.ENGLISH);
        ObservableList<Language> languages = FXCollections.observableArrayList();
        languages.add(englishLanguage);
        languages.add(frenchLanguage);
        languageComboBoxId.setItems(languages);

        Locale currentLocale = SpringFxmlLoader.getLocale();
        if (currentLocale.equals(Locale.FRENCH)) {
            languageComboBoxId.setValue(frenchLanguage);
        } else {
            languageComboBoxId.setValue(englishLanguage);
        }

        languageComboBoxId.valueProperty().addListener(new ChangeListener<Language>() {

            @Override
            public void changed(ObservableValue<? extends Language> observable, Language oldValue, Language newValue) {
                SpringFxmlLoader.changeLocale(newValue.getLocale());
            }
        });
    }

    @Override
    public void setWrappingDialog(Dialog wrappingDialog) {
        this.wrappingDialog = wrappingDialog;
    }

    @Override
    public Dialog getWrappingDialog() {
        return this.wrappingDialog;
    }

    @Override
    public EventBus getEventBus() {
        return this.eventBus;
    }
    
    @FXML
    protected void onCloseAction(ActionEvent event) {
        try {
            MainApp.getInstance().replaceSceneContent(Navigation.TASK_CHOICE_VIEW, WindowsPreferredDimensions.TASKCHOICE_VIEW_WIDTH, WindowsPreferredDimensions.TASKCHOICE_VIEW_HEIGHT);
        } catch (Exception ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
