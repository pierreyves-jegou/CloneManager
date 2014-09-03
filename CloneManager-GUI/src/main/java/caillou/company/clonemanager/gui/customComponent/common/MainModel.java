/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.common;

import caillou.company.clonemanager.gui.customComponent.critere.CritereModel;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class MainModel implements BeanFactoryAware, Model{

    CritereModel critereModel;

    TaskModel taskModel;

    LocationsModel locationsModel;

    BeanFactory beanFactory;
    
    StatisticsModel searchStatisticsModel;
    
    public void resetCritereModel(){
        critereModel = beanFactory.getBean(CritereModel.class);
    }
       
    public void resetLocationsModel(){
        locationsModel = beanFactory.getBean(LocationsModel.class);
    }
    
    public CritereModel getCritereModel() {
        return critereModel;
    }

    @Autowired
    public void setCritereModel(CritereModel critereModel) {
        this.critereModel = critereModel;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    @Autowired
    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    public LocationsModel getLocationsModel() {
        return locationsModel;
    }

    @Autowired
    public void setLocationsModel(LocationsModel locationsModel) {
        this.locationsModel = locationsModel;
    }

    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        this.beanFactory = bf;
    }

    public StatisticsModel getSearchStatisticsModel() {
        return searchStatisticsModel;
    }
    
    @Autowired
    public void setSearchStatisticsModel(StatisticsModel searchStatisticsModel) {
        this.searchStatisticsModel = searchStatisticsModel;
    }
}
