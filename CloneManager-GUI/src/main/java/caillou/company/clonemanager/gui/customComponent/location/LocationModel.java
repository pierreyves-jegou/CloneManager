/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.location;

import caillou.company.clonemanager.background.bean.applicationFile.contract.GroupProvider;
import caillou.company.clonemanager.background.bean.contract.EventBusProvider;
import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.common.Model;
import caillou.company.clonemanager.gui.customComponent.excludeTree.ExcludeModel;
import caillou.company.clonemanager.gui.event.ShowErrorsEvent;
import com.google.common.eventbus.EventBus;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class LocationModel implements Model, GroupProvider, EventBusProvider{

    private final StringProperty path = new SimpleStringProperty();
    private Group.VALUE groupValue = null;
    
    private final BooleanProperty disabled = new SimpleBooleanProperty(false);
    private ObservableList<caillou.company.clonemanager.gui.bean.error.Error> errors = FXCollections.observableArrayList(new ArrayList<caillou.company.clonemanager.gui.bean.error.Error>());
    
    private Set<Path> exludedPathFromApplication = new HashSet<>();   
    private Set<Path> exludedPathFromUser = new HashSet<>();   

    private MainModel mainModel;
    
    private ExcludeModel excludeModel;
    
    protected EventBus errorBus = new EventBus("ERROR_BUS");
    
    public ObservableList<caillou.company.clonemanager.gui.bean.error.Error> getErrors() {
        return errors;
    }
    
    public LocationModel() {
    }
    
    @PostConstruct
    private void postConstruct(){
        this.excludeModel.pathProperty().bind(pathProperty());
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String value) {
        path.set(value);
    }

    public StringProperty pathProperty() {
        return path;
    }

    @Override
    public Group.VALUE getGroup() {
        return groupValue;
    }

    public void setGroupValue(Group.VALUE groupValue) {
        this.groupValue = groupValue;
    }

    public void resetErrors(){
        this.getErrors().clear();
    }
    
    public void showErrors(){
        this.getEventBus().post(new ShowErrorsEvent());
    }
    
    public void updateLocation(String path, Group.VALUE groupValue) {
        mainModel.getLocationsModel().resetErrors();
        this.setPath(path);
        this.setGroupValue(groupValue);
        mainModel.getLocationsModel().sanityCheck();
        mainModel.getLocationsModel().showErrors();
    }

    public void addError(caillou.company.clonemanager.gui.bean.error.Error error) {
        this.errors.add(error);
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

    public ExcludeModel getExcludeModel() {
        return excludeModel;
    }

    @Autowired
    public void setExcludeModel(ExcludeModel excludeModel) {
        this.excludeModel = excludeModel;
    }

    @Override
    public EventBus getEventBus() {
        return errorBus;
    }
    
}
