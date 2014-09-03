/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.location;

import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.common.Controller;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.excludeTree.ExcludeTreeController;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javax.annotation.PostConstruct;
import org.controlsfx.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope("prototype")
public class LocationController extends Controller<LocationModel> implements Initializable {

    @FXML
    private TextField path;

    @FXML
    private ComboBox<String> groupId;

    @FXML
    private Button button;

    @FXML
    private ListView<caillou.company.clonemanager.gui.bean.error.Error> errorsId;

    private final BooleanProperty disabled = new SimpleBooleanProperty(false);

    private final BooleanProperty updated = new SimpleBooleanProperty();

    private MainModel mainModel;

    
    public LocationController() {
    }

    @PostConstruct
    public void initializeSpring() {
        LocationModel locationModel = mainModel.getLocationsModel().addLocation();
        this.setModel(locationModel);
     }

    public String getPath() {
        return path.textProperty().get();
    }

    public String getGroup() {
        return groupId.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.disabledProperty().bind(getModel().disabledProperty());
        final LocationController thisInstance = this;
        this.updatedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                getModel().updateLocation(thisInstance.getPath(), thisInstance.getGroup());
            }
        });

        this.getErrorsId().setItems(getModel().getErrors());
        this.setListListener();

        LocationsModel locationsModel = mainModel.getLocationsModel();
        groupId.visibleProperty().bind(locationsModel.enableGroupingProperty());
        groupId.managedProperty().bind(locationsModel.enableGroupingProperty());

        path.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updated.set(!updated.get());
            }
        });

        groupId.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updated.set(!updated.get());
            }
        });

        disabledProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                path.setDisable(newValue);
                groupId.setDisable(newValue);
                button.setDisable(newValue);
            }

        });

        errorsId.setCellFactory(new ErrorCellFactory());
        errorsId.setVisible(false);
        errorsId.setManaged(false);
    }

    @FXML
    private void handleFilechooserAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Sélectionner le répertoire");
        File file = directoryChooser.showDialog(null);
        if (file != null) {
            path.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleFilterAction(ActionEvent event) {
        if (this.getModel().getPath() != null) {
            Dialog dialogExclude = new Dialog(MainApp.getInstance().getStage(), "Gestion des exclusions");
            LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.EXCLUDE_TREE);
            ExcludeTreeController excludeTreeController = (ExcludeTreeController) loadingMojo.getController();
            excludeTreeController.setWrappingDialog(dialogExclude);
            excludeTreeController.setModel(this.getModel().getExcludeModel());
            excludeTreeController.initialiseRootDirectory();
            dialogExclude.setContent(loadingMojo.getParent());
            dialogExclude.show();
        }       
    }

    public void setListListener() {
        this.errorsId.getItems().addListener(new ListChangeListener<caillou.company.clonemanager.gui.bean.error.Error>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends caillou.company.clonemanager.gui.bean.error.Error> c) {
                if (errorsId.getItems().size() == 0) {
                    errorsId.setVisible(false);
                    errorsId.setManaged(false);
                } else {
                    errorsId.setVisible(true);
                    errorsId.setManaged(true);
                }
            }
        });
    }

    public boolean isUpdated() {
        return updated.get();
    }

    public void setUpdated(boolean value) {
        updated.set(value);
    }

    public BooleanProperty updatedProperty() {
        return updated;
    }

    public boolean isDisabled() {
        return disabled.get();
    }

    public void setDisabled(boolean value) {
        disabled.set(value);
    }

    public BooleanProperty disabledProperty() {
        return disabled;
    }

    public ListView<caillou.company.clonemanager.gui.bean.error.Error> getErrorsId() {
        return errorsId;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
