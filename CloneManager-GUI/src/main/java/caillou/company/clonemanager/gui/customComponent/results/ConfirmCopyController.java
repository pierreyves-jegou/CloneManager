/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.gui.service.task.impl.CopyToTask;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class ConfirmCopyController extends AbstractConfirmController {

    private File targetDirectory;

    @FXML
    private void confirmAction(ActionEvent event) throws Exception {
        CopyToTask copyToTask = new CopyToTask();
        copyToTask.setTargetDirectory(targetDirectory);
        copyToTask.setFilesToCopy(tableView.getSelectionModel().getSelectedItems());
        new Thread(copyToTask).start();
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
    protected void checkArgumentsChild() throws CloneManagerArgumentException {
    }

    public void setTargetDirectory(File targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

}
