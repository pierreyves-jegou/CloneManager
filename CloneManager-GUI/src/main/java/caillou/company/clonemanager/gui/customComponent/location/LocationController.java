/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.location;

import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.StyleSheet;
import caillou.company.clonemanager.gui.bean.error.Error;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.customComponent.common.Controller;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.excludeTree.ExcludeTreeController;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.event.ShowErrorsEvent;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import com.google.common.eventbus.Subscribe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.annotation.PostConstruct;
import org.controlsfx.control.PopOver;
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
    private ComboBox<Group> groupId;

    @FXML
    private Button button;

    @FXML
    private Button filterButtonId;

    private final BooleanProperty disabled = new SimpleBooleanProperty(false);

    private final BooleanProperty updated = new SimpleBooleanProperty();

    private MainModel mainModel;

    private final PopOver errorPopOver = new PopOver();

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

    public Group getGroup() {
        return groupId.getValue();
    }

    public void setGroup(Group group) {
        this.groupId.setValue(group);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.disabledProperty().bind(getModel().disabledProperty());
        final LocationController thisInstance = this;
        this.updatedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                getModel().updateLocation(thisInstance.getPath(), thisInstance.getGroup().getValue());
            }
        });

        this.getModel().getEventBus().register(this);

        LocationsModel locationsModel = mainModel.getLocationsModel();
        groupId.visibleProperty().bind(locationsModel.enableGroupingProperty());
        groupId.managedProperty().bind(locationsModel.enableGroupingProperty());
        groupId.getItems().add(Group.GROUPA);
        groupId.getItems().add(Group.GROUPB);

        path.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean disableExclusion = true;
                if (newValue != null && !newValue.equals("")) {
                    File file = new File(newValue);
                    if (file.exists() && file.isDirectory()) {
                        disableExclusion = false;
                    }
                }
                filterButtonId.setDisable(disableExclusion);
                updated.set(!updated.get());
            }
        });

        groupId.valueProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
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

        filterButtonId.setDisable(true);
        
        /**
         * Due to the bug
         * "https://bitbucket.org/controlsfx/controlsfx/issue/185/nullpointerexception-when-using-popover"
         */
        MainApp.getInstance().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                errorPopOver.hide(Duration.millis(0));
            }
        });
        /**
         * End *
         */
        
    }

    @Subscribe
    public void showErrors(ShowErrorsEvent showErrorsEvent) {
        final List<Error> errors = this.getModel().getErrors();
        if (!errors.isEmpty()) {
            VBox vbox = new VBox();
            vbox.setSpacing(5);
            for (Error error : errors) {
                Label errorLabel = new Label(error.getMessage());
                errorLabel.getStylesheets().add(StyleSheet.LOCATION_CSS);
                if (error.getSeverityLevel().equals(caillou.company.clonemanager.gui.bean.error.Error.SEVERITY_LEVEL.ERROR)) {
                    errorLabel.getStyleClass().add("error");
                } else if (error.getSeverityLevel().equals(caillou.company.clonemanager.gui.bean.error.Error.SEVERITY_LEVEL.WARNING)) {
                    errorLabel.getStyleClass().add("warning");
                }

                vbox.getChildren().add(new Label(error.getMessage()));
            }
            errorPopOver.setContentNode(vbox);
            errorPopOver.show(path);
        }else{
            errorPopOver.hide();
        }
    }

    @FXML
    private void handleFilechooserAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(SpringFxmlLoader.getResourceBundle().getString("title.selectDirectory"));
        File file = directoryChooser.showDialog(null);
        if (file != null) {
            path.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleFilterAction(ActionEvent event) {
        String currentPath = this.getModel().getPath();
        if (currentPath != null) {
            File file = new File(currentPath);
            if (!file.exists() || !file.isDirectory()) {
                return;
            }
        }
        Dialog dialogExclude = new Dialog(MainApp.getInstance().getStage(), SpringFxmlLoader.getResourceBundle().getString("title.exclusion"));
        dialogExclude.getStylesheets().add(StyleSheet.DIALOG_CSS);

        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.EXCLUDE_TREE);
        ExcludeTreeController excludeTreeController = (ExcludeTreeController) loadingMojo.getController();
        excludeTreeController.setWrappingDialog(dialogExclude);
        excludeTreeController.setModel(this.getModel().getExcludeModel());
        excludeTreeController.initialiseRootDirectory();
        dialogExclude.setContent(loadingMojo.getParent());
        dialogExclude.show();
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

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
