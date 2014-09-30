/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.locationContainer;

import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.location.LocationController;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class LocationContainerController implements Initializable{

    @FXML
    private VBox locationContainerGroupA;
    
    @FXML
    private VBox locationContainerGroupB;
    
    @FXML
    private CheckBox detectDoublonsWithinSameLocationId;
    
    private MainModel mainModel;
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addLocationWithinGroupA();
        this.addLocationWithinGroupB();    
        
        final LocationsModel locationsModel = mainModel.getLocationsModel();
        if (mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            locationsModel.detectsIdentiqueFilesWithinALocationProperty().bind(detectDoublonsWithinSameLocationId.selectedProperty());
            detectDoublonsWithinSameLocationId.selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    locationsModel.validLocations();
                }

            });
        }
        
    }
    
    private void addLocationWithinGroupA(){
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.LOCATION_VIEW);
        LocationController locationController = (LocationController) loadingMojo.getController();
        locationController.setGroup(Group.GROUPA);
        locationContainerGroupA.getChildren().add(loadingMojo.getParent());
    }
    
    private void addLocationWithinGroupB(){
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.LOCATION_VIEW);
        LocationController locationController = (LocationController) loadingMojo.getController();
        locationController.setGroup(Group.GROUPB);
        locationContainerGroupB.getChildren().add(loadingMojo.getParent());
    }  
    
    @FXML
    private void addLocationGroupAFromLink(ActionEvent event) throws IOException {
        this.addLocationWithinGroupA();
    }
    
    @FXML
    private void addLocationGroupBFromLink(ActionEvent event) throws IOException {
        this.addLocationWithinGroupB();
    }
    
    @FXML
    private void addLocationGroupAFromButton(MouseEvent event) throws IOException {
        this.addLocationWithinGroupA();
    }
    
    @FXML
    private void addLocationGroupBFromButton(MouseEvent event) throws IOException {
        this.addLocationWithinGroupB();
    }    
    
    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
