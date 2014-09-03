/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.location;

import caillou.company.clonemanager.background.bean.applicationFile.contract.GroupProvider;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.common.Model;
import caillou.company.clonemanager.gui.customComponent.excludeTree.ExcludeModel;
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
public class LocationModel implements Model, GroupProvider{

    private final StringProperty path = new SimpleStringProperty();
    private final StringProperty group = new SimpleStringProperty();
    
    private final BooleanProperty disabled = new SimpleBooleanProperty(false);
    private ObservableList<caillou.company.clonemanager.gui.bean.error.Error> errors = FXCollections.observableArrayList(new ArrayList<caillou.company.clonemanager.gui.bean.error.Error>());
    
    private Set<Path> exludedPathFromApplication = new HashSet<>();   
    private Set<Path> exludedPathFromUser = new HashSet<>();   

    private MainModel mainModel;
    
    private ExcludeModel excludeModel;
    
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
    public String getGroup() {
        return group.get();
    }

    public void setGroup(String value) {
        group.set(value);
    }

    public StringProperty groupProperty() {
        return group;
    }

    public String toString() {
        return getPath() + " - " + getGroup();
    }

    public void updateLocation(String path, String group) {
        mainModel.getLocationsModel().resetErrors();
        this.setPath(path);
        this.setGroup(group);
        mainModel.getLocationsModel().sanityCheck();
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
    
}
