/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.locationContainer;

import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author pierre
 */
@Component
public class LocationContainerController implements Initializable {

    @FXML
    private VBox locationContainerId;

    @FXML
    private CheckBox detectDoublonsWithinSameLocationId;

    @FXML
    private CheckBox enableGroupingId;

    @FXML
    private HBox boutonContainer;

    @FXML
    private Hyperlink advancedOptionId;

    private final BooleanProperty showAdvancedOptions = new SimpleBooleanProperty(true);
    
    private MainModel mainModel;

    public LocationContainerController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final LocationsModel locationsModel = mainModel.getLocationsModel();
        try {
            // Put two locations into the scene
            this.addLocation();
            this.addLocation();
        } catch (IOException ex) {
            Logger.getLogger(LocationContainerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        toogleAdvancedOptions(null);
        boutonContainer.visibleProperty().bind(enableGroupingId.selectedProperty());
        boutonContainer.managedProperty().bind(enableGroupingId.selectedProperty());

        if (mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            locationsModel.detectsIdentiqueFilesWithinALocationProperty().bind(detectDoublonsWithinSameLocationId.selectedProperty());
        }

        enableGroupingId.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                locationsModel.setEnableGrouping(newValue);

                // The maximal number of location in this case is 2
                if (!newValue) {
                    locationsModel.disableFromThirdLocation();
                } else {
                    locationsModel.enableFromThirdLocation();
                }

                locationsModel.validLocations();
            }

        });

        TaskModel taskModel = mainModel.getTaskModel();
        if (taskModel.getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            detectDoublonsWithinSameLocationId.selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    locationsModel.validLocations();
                }

            });
        }

    }

    @FXML
    private void addLocationFromLink(ActionEvent event) throws IOException {
        this.addLocation();
    }

    @FXML
    private void addLocationFromButton(MouseEvent event) throws IOException {
        this.addLocation();
    }

    @FXML
    private void toogleAdvancedOptions(ActionEvent event) {
        if (event == null) {
            this.hideAdvancedOptions();
        } else {
            if (showAdvancedOptions.get()) {
                this.hideAdvancedOptions();
            } else {
                this.showAdvancedOptions();
            }
        }
    }

    private void showAdvancedOptions() {
        showAdvancedOptions.set(true);
        enableGroupingId.setVisible(true);
        enableGroupingId.setManaged(true);
        advancedOptionId.setText("Options Avancées \u25BC");

        TaskModel taskModel = mainModel.getTaskModel();
        if (taskModel.getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            detectDoublonsWithinSameLocationId.setVisible(true);
            detectDoublonsWithinSameLocationId.setManaged(true);
        }
    }

    private void hideAdvancedOptions() {
        showAdvancedOptions.set(false);
        enableGroupingId.setVisible(false);
        enableGroupingId.setManaged(false);
        detectDoublonsWithinSameLocationId.setVisible(false);
        detectDoublonsWithinSameLocationId.setManaged(false);
        advancedOptionId.setText("Options Avancées \u25B6");
    }

    private void addLocation() throws IOException {
        SpringFxmlLoader springFxmlLoader = new SpringFxmlLoader();
        LoadingMojo loadingMojo = springFxmlLoader.load(Navigation.LOCATION_VIEW);
        locationContainerId.getChildren().add(loadingMojo.getParent());
    }

    public boolean isShowAdvancedOptions() {
        return showAdvancedOptions.get();
    }

    public void setShowAdvancedOptions(boolean value) {
        showAdvancedOptions.set(value);
    }

    public BooleanProperty showAdvancedOptionsProperty() {
        return showAdvancedOptions;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
    
}
