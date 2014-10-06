/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.handler.RemoveFromViewHandler;
import caillou.company.clonemanager.gui.service.task.impl.DeleteFilesTask;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
        if(!deleteFilesTask.getFilesNotDeleted().isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for(GUIApplicationFile guiApplicationFile : deleteFilesTask.getFilesNotDeleted()){
                stringBuilder.append(guiApplicationFile.getAbsolutePath());
                stringBuilder.append("\n");
            }
            Notifications.create()
                    .title("Errors encountered while deleting the files")
                    .text(stringBuilder.toString()).hideAfter(Duration.INDEFINITE)
                    .showError();
        }
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
