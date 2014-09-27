/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.exception.ArgumentException;
import caillou.company.clonemanager.background.exception.OrganizerException;
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
public class ConfirmSuppressionController implements Initializable, ArgumentCheckable {

    @FXML
    private ListView<GUIApplicationFile> filesToDeleteViewId;

    @FXML
    private VBox listViewContainerId;
    
    private boolean suppressionAction = false;

    private Dialog dialog;

    private ObservableList<GUIApplicationFile> guiApplicationFileList;

    private TableView<GUIApplicationFile> tableView;
    
    private Boolean suppressOnlyOnSelected = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initializePhaseAutomaticResizing();
        
        filesToDeleteViewId.setCellFactory(new Callback<ListView<GUIApplicationFile>, ListCell<GUIApplicationFile>>() {
            @Override
            public ListCell<GUIApplicationFile> call(ListView<GUIApplicationFile> list) {
                return new MyListViewCellFactory();
            }
        }
        );
    }

    private void initializePhaseAutomaticResizing() {
        filesToDeleteViewId.prefHeightProperty().bind(listViewContainerId.heightProperty());
        filesToDeleteViewId.prefWidthProperty().bind(listViewContainerId.widthProperty());
    }
    
    @Override
    public void checkArguments() throws ArgumentException {
        if(this.dialog == null || guiApplicationFileList == null || tableView == null || suppressOnlyOnSelected == null){
            throw new ArgumentException();
        }
    }
    
    @FXML
    private void confirmSuppressionAction(ActionEvent event) throws Exception {       
        DeleteFilesTask deleteFilesTask = SpringFxmlLoader.getBean(DeleteFilesTask.class);
        deleteFilesTask.setFilesToDelete(filesToDeleteViewId.getItems());
        RemoveFromViewHandler removeFromViewHandler = SpringFxmlLoader.getBean(RemoveFromViewHandler.class);
        removeFromViewHandler.setGUIApplicationFileList(guiApplicationFileList);
        removeFromViewHandler.setTableView(tableView);
        removeFromViewHandler.setSuppressOnlyOnSelected(suppressOnlyOnSelected);
        removeFromViewHandler.checkArguments();
        deleteFilesTask.setOnSucceeded(removeFromViewHandler);
        new Thread(deleteFilesTask).start();
        suppressionAction = true;
        dialog.hide();
    }

    @FXML
    private void cancelSuppressionAction(ActionEvent event) throws Exception {
        suppressionAction = false;
        dialog.hide();
    }

    public void setMyFilesToDelete(List<GUIApplicationFile> myFileFXs) {
        ObservableList<GUIApplicationFile> list = FXCollections.observableArrayList();
        for (GUIApplicationFile applicationFile : myFileFXs) {
            list.add(applicationFile);
        }
        filesToDeleteViewId.setItems(list);
    }
    
    class MyListViewCellFactory extends ListCell<GUIApplicationFile> {

        @Override
        public void updateItem(GUIApplicationFile item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getAbsolutePath());
            }
        }
    }

    public boolean isSuppressionAction() {
        return suppressionAction;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void setGUIApplicationFileList(ObservableList<GUIApplicationFile> guiApplicationFileList) {
        this.guiApplicationFileList = guiApplicationFileList;
    }

    public void setTableView(TableView<GUIApplicationFile> tableView) {
        this.tableView = tableView;
    }

    public void setSuppressOnlyOnSelected(Boolean suppressOnlyOnSelected) {
        this.suppressOnlyOnSelected = suppressOnlyOnSelected;
    }
    
    
}
