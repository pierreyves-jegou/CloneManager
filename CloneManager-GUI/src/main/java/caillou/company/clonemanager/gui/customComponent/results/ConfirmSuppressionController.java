/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.background.exception.CloneManagerException;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.handler.RemoveFromViewHandler;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
import caillou.company.clonemanager.gui.service.task.impl.DeleteFilesTask;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class ConfirmSuppressionController extends AbstractConfirmController {
        
    private Boolean suppressOnlyOnSelected = null;
        
    @FXML
    private void confirmAction(ActionEvent event) throws Exception {       
        DeleteFilesTask deleteFilesTask = SpringFxmlLoader.getBean(DeleteFilesTask.class);
        deleteFilesTask.setFilesToDelete(filesToShowViewId.getItems());
        RemoveFromViewHandler removeFromViewHandler = SpringFxmlLoader.getBean(RemoveFromViewHandler.class);
        removeFromViewHandler.setGUIApplicationFileList(guiApplicationFileList);
        removeFromViewHandler.setTableView(tableView);
        removeFromViewHandler.setSuppressOnlyOnSelected(suppressOnlyOnSelected);
        removeFromViewHandler.checkArguments();
        deleteFilesTask.setOnSucceeded(removeFromViewHandler);
        new Thread(deleteFilesTask).start();
        confirmAction = true;
        dialog.hide();
    }

    @FXML
    private void cancelAction(ActionEvent event) throws Exception {
        confirmAction = false;
        dialog.hide();
    }

    @Override
    protected void initializeChild() {
    }

    @Override
    protected void checkArgumentsChild() throws CloneManagerArgumentException{
        if(suppressOnlyOnSelected == null){
            throw new CloneManagerArgumentException();
        }
    }
 
    public void setSuppressOnlyOnSelected(Boolean suppressOnlyOnSelected) {
        this.suppressOnlyOnSelected = suppressOnlyOnSelected;
    }
    
    
}
