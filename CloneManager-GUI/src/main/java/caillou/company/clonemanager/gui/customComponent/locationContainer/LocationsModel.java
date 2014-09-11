/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.locationContainer;

import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.gui.customComponent.location.LocationModel;
import caillou.company.clonemanager.gui.service.contract.LocationsValidationService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class LocationsModel implements BeanFactoryAware{

    private ObservableList<LocationModel> observableLocations;
    private final BooleanProperty valid
            = new SimpleBooleanProperty(false);
    private final BooleanProperty enableGrouping = new SimpleBooleanProperty(false);
    private final BooleanProperty detectsIdentiqueFilesWithinALocation = new SimpleBooleanProperty(false);

    private BeanFactory beanFactory;
    
    private LocationsValidationService locationValidationService;
    
    public LocationsModel() {
        observableLocations = FXCollections.observableList(new ArrayList<LocationModel>());
    }

    public LocationModel addLocation() {
        LocationModel locationModel = beanFactory.getBean(LocationModel.class);
        observableLocations.add(locationModel);
        return locationModel;
    }

    public int size() {
        return observableLocations.size();
    }

    public Iterator<LocationModel> iterator() {
        return observableLocations.iterator();
    }

    public List<LocationModel> getLocations(){
        return observableLocations;
    }
    
    public boolean isValid() {
        return valid.get();
    }

    public void setValid(boolean value) {
        valid.set(value);
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public void resetErrors() {
        for(LocationModel locationModel : observableLocations){
            locationModel.getErrors().clear();
        }
    }
    
    public void sanityCheck() {
        locationValidationService.checkErrors(observableLocations);
        this.validLocations();
    }
    
    public void validLocations(){
        boolean isValid = locationValidationService.validLocations(observableLocations, enableGrouping.get(), detectsIdentiqueFilesWithinALocation.get());
        this.setValid(isValid);
    }
    
    private void toogleStateFromThirdLocation(boolean value) {
        for (int i = 2; i < observableLocations.size(); i++) {
            LocationModel location = observableLocations.get(i);
            location.setDisabled(value);
        }
    }

    public void setDefaultGroups(){
        for(int i=0; i < observableLocations.size(); i++){
            LocationModel locationModel = observableLocations.get(i);
            if(i == 0){
                locationModel.setGroupValue(Group.VALUE.GROUP1);
            }
            if(i == 1){
                locationModel.setGroupValue(Group.VALUE.GROUP2);
                return;
            }
        }
    }
    
    public void disableFromThirdLocation() {
        toogleStateFromThirdLocation(true);
    }

    public void enableFromThirdLocation() {
        toogleStateFromThirdLocation(false);
    }

    public boolean isEnableGrouping() {
        return enableGrouping.get();
    }

    public void setEnableGrouping(boolean value) {
        enableGrouping.set(value);
    }

    public BooleanProperty enableGroupingProperty() {
        return enableGrouping;
    }

    public boolean isDetectsIdentiqueFilesWithinALocation() {
        return detectsIdentiqueFilesWithinALocation.get();
    }

    public void setDetectsIdentiqueFilesWithinALocation(boolean value) {
        detectsIdentiqueFilesWithinALocation.set(value);
    }

    public BooleanProperty detectsIdentiqueFilesWithinALocationProperty() {
        return detectsIdentiqueFilesWithinALocation;
    }

    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        this.beanFactory = bf;
    }
    
    @Autowired
    public void setLocationValidationService(LocationsValidationService locationValidationService) {
        this.locationValidationService = locationValidationService;
    }
}
