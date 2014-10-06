/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
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

/**
 *
 * @author pierre
 */
public abstract class AbstractConfirmController implements Initializable, ArgumentCheckable{
 
    @FXML
    protected ListView<GUIApplicationFile> filesToShowViewId;

    @FXML
    protected VBox listViewContainerId;

    protected Dialog dialog;

    protected ObservableList<GUIApplicationFile> guiApplicationFileList;

    protected TableView<GUIApplicationFile> tableView;
    
    protected boolean confirmAction = false;
        
    protected abstract void initializeChild();
    
    protected abstract void checkArgumentsChild() throws CloneManagerArgumentException;
    
    public void setMyFilesToShow(List<GUIApplicationFile> myFileFXs) {
        ObservableList<GUIApplicationFile> list = FXCollections.observableArrayList();
        for (GUIApplicationFile applicationFile : myFileFXs) {
            list.add(applicationFile);
        }
        filesToShowViewId.setItems(list);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initializePhaseAutomaticResizing();
        
        filesToShowViewId.setCellFactory(new Callback<ListView<GUIApplicationFile>, ListCell<GUIApplicationFile>>() {
            @Override
            public ListCell<GUIApplicationFile> call(ListView<GUIApplicationFile> list) {
                return new MyListViewCellFactory();
            }
        }
        );
        
        this.initializeChild();
    }
    
    @Override
    public void checkArguments() throws CloneManagerArgumentException {
        if(this.dialog == null || guiApplicationFileList == null || tableView == null){
            throw new CloneManagerArgumentException();
        }
        this.checkArgumentsChild();
    }
    
    private void initializePhaseAutomaticResizing() {
        filesToShowViewId.prefHeightProperty().bind(listViewContainerId.heightProperty());
        filesToShowViewId.prefWidthProperty().bind(listViewContainerId.widthProperty());
    }
    
    public void setGUIApplicationFileList(ObservableList<GUIApplicationFile> guiApplicationFileList) {
        this.guiApplicationFileList = guiApplicationFileList;
    }

    public void setTableView(TableView<GUIApplicationFile> tableView) {
        this.tableView = tableView;
    }
    
    public boolean isConfirmAction() {
        return confirmAction;
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
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
    
}
