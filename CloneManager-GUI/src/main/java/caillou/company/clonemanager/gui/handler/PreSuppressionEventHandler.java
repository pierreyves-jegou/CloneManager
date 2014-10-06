/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.StyleSheet;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.results.ConfirmSuppressionController;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class PreSuppressionEventHandler implements EventHandler<WorkerStateEvent>, ArgumentCheckable{

    private TableView<GUIApplicationFile> tableView;
    
    private Boolean suppressOnlyOnSelected = null;
    
    private ObservableList<GUIApplicationFile> guiApplicationFileList;

    private void handleEvent(List<GUIApplicationFile> fileToDelete){
        try {
            checkArguments();
        } catch (CloneManagerArgumentException ex) {
            Logger.getLogger(PreSuppressionEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Dialog dialogConfirmSuppression = new Dialog(MainApp.getInstance().getStage(), SpringFxmlLoader.getResourceBundle().getString("title.fileToSuppress"));
        dialogConfirmSuppression.getStylesheets().add(StyleSheet.DIALOG_CSS);
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.CONFIRM_POPUP);
        ConfirmSuppressionController confirmSuppressionController = (ConfirmSuppressionController) loadingMojo.getController();
        confirmSuppressionController.setDialog(dialogConfirmSuppression);
        confirmSuppressionController.setGUIApplicationFileList(guiApplicationFileList);
        confirmSuppressionController.setTableView(tableView);
        confirmSuppressionController.setMyFilesToShow(fileToDelete);
        confirmSuppressionController.setSuppressOnlyOnSelected(suppressOnlyOnSelected);
        try {
            confirmSuppressionController.checkArguments();
        } catch (CloneManagerArgumentException ex) {
            Logger.getLogger(PreSuppressionEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialogConfirmSuppression.setContent(loadingMojo.getParent());
        dialogConfirmSuppression.show();
    }
    
    @Override
    public void handle(WorkerStateEvent event) {
        List<GUIApplicationFile> fileToDelete = new ArrayList<>((Set<GUIApplicationFile>) event.getSource().getValue());
        handleEvent(fileToDelete);
    }
    
    public void handleWithoutThread(List<GUIApplicationFile> fileToDelete){
        handleEvent(fileToDelete);
    }

    public void setTableView(TableView<GUIApplicationFile> tableView) {
        this.tableView = tableView;
    }

    public void setSuppressOnlyOnSelected(boolean suppressOnlyOnSelected) {
        this.suppressOnlyOnSelected = suppressOnlyOnSelected;
    }

    public void setGuiApplicationFileList(ObservableList<GUIApplicationFile> guiApplicationFileList) {
        this.guiApplicationFileList = guiApplicationFileList;
    }

    @Override
    public void checkArguments() throws CloneManagerArgumentException {
        if(tableView == null || guiApplicationFileList == null || suppressOnlyOnSelected == null){
            throw new CloneManagerArgumentException();
        }
    }

    
    
}
