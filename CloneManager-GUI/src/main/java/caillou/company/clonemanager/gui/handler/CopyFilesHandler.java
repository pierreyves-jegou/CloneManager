/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.gui.ControllerNames;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.StyleSheet;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.results.ConfirmCopyController;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import org.controlsfx.dialog.Dialog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class CopyFilesHandler implements EventHandler<ActionEvent>, ArgumentCheckable {

    private TableView<GUIApplicationFile> tableView;

    private ObservableList<GUIApplicationFile> guiApplicationFileList;

    private List<GUIApplicationFile> fileToShow;

    @Override
    public void handle(ActionEvent event) {
        try {
            checkArguments();
        } catch (CloneManagerArgumentException ex) {
            Logger.getLogger(PreSuppressionEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(SpringFxmlLoader.getResourceBundle().getString("title.selectDirectory"));
        File targetDirectory = directoryChooser.showDialog(null);
        if (targetDirectory != null && targetDirectory.exists() && targetDirectory.isDirectory()) {
            Dialog dialogConfirmCopy = new Dialog(MainApp.getInstance().getStage(), SpringFxmlLoader.getResourceBundle().getString("title.fileToCopy") + targetDirectory.getAbsolutePath());
            dialogConfirmCopy.getStylesheets().add(StyleSheet.DIALOG_CSS);
            LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.CONFIRM_POPUP, ControllerNames.CONFIRM_COPY_CONTROLLER);
            ConfirmCopyController confirmCopyController = (ConfirmCopyController) loadingMojo.getController();
            confirmCopyController.setDialog(dialogConfirmCopy);
            confirmCopyController.setGUIApplicationFileList(guiApplicationFileList);
            confirmCopyController.setTableView(tableView);
            confirmCopyController.setMyFilesToShow(fileToShow);
            confirmCopyController.setTargetDirectory(targetDirectory);
            try {
                confirmCopyController.checkArguments();
            } catch (CloneManagerArgumentException ex) {
                Logger.getLogger(PreSuppressionEventHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            dialogConfirmCopy.setContent(loadingMojo.getParent());
            dialogConfirmCopy.show();
        }
    }

    @Override
    public void checkArguments() throws CloneManagerArgumentException {
        if (tableView == null || guiApplicationFileList == null || fileToShow == null) {
            throw new CloneManagerArgumentException();
        }
    }
    
    public void setTableView(TableView<GUIApplicationFile> tableView) {
        this.tableView = tableView;
    }

    public void setGuiApplicationFileList(ObservableList<GUIApplicationFile> guiApplicationFileList) {
        this.guiApplicationFileList = guiApplicationFileList;
    }

    public void setFileToShow(List<GUIApplicationFile> fileToShow) {
        this.fileToShow = fileToShow;
    }

}
