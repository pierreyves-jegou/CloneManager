/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.taskchoice;

import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.StyleSheet;
import caillou.company.clonemanager.gui.WindowsPreferredDimensions;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.settings.SettingsController;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import org.controlsfx.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class TaskChoiceController extends HBox implements Initializable {

    private MainModel mainModel;

    public TaskChoiceController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML

    protected void handleMissings(ActionEvent event) throws Exception {
        try {
            MainApp app = MainApp.getInstance();
            TaskModel taskModel = mainModel.getTaskModel();
            taskModel.setCurrentTask(TaskModel.TASK.DETECT_MISSING);
            app.replaceSceneContent(Navigation.SEARCH_VIEW, WindowsPreferredDimensions.SEARCH_VIEW_WIDTH, WindowsPreferredDimensions.SEARCH_VIEW_HEIGHT);
        } catch (Exception e) {
            System.out.println("exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDoublons(ActionEvent event) throws Exception {
        try {
            MainApp app = MainApp.getInstance();
            TaskModel taskModel = mainModel.getTaskModel();
            taskModel.setCurrentTask(TaskModel.TASK.DETECT_DOUBLONS);
            app.replaceSceneContent(Navigation.SEARCH_VIEW, WindowsPreferredDimensions.SEARCH_VIEW_WIDTH, WindowsPreferredDimensions.SEARCH_VIEW_HEIGHT);
        } catch (Exception e) {
            System.out.println("exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSettingsAction(ActionEvent event) {
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.SETTINGS_VIEW);
        SettingsController settingsController = (SettingsController) loadingMojo.getController();
        String dialogTitle = SpringFxmlLoader.getResourceBundle().getString("link.settings");
        final Dialog dialogSettings = new Dialog(MainApp.getInstance().getStage(), dialogTitle);
        dialogSettings.getStylesheets().add(StyleSheet.DIALOG_CSS);
        settingsController.setWrappingDialog(dialogSettings);
        dialogSettings.setClosable(false);
        dialogSettings.setContent(loadingMojo.getParent());
        dialogSettings.show();
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
