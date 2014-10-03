/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.filter.Filter;
import caillou.company.clonemanager.background.exception.CloneManagerException;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import caillou.company.clonemanager.background.service.impl.FileVisitor;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.location.LocationModel;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.service.contract.MyFileProcessor;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class VisitLocationTask extends Task<Long> implements Cancellable {

    private MainModel mainModel;

    private MyFileProcessor myFileProcessor;
    
    @Override
    protected Long call() throws Exception {
        Set<ApplicationFile> myFileToTreat = new HashSet<>();
        Long byteToTreat = (long) 0;
        
        Filter<ApplicationFile> filter = mainModel.getCritereModel().getFilters();
        LocationsModel locationsModel = mainModel.getLocationsModel();
        Iterator<LocationModel> ite = locationsModel.iterator();
        
        try {
            while (ite.hasNext()) {
                FileVisitor fileVisitor = new FileVisitor(filter);
                LocationModel locationModel = ite.next();
                fileVisitor.addFilter(locationModel.getExcludeModel().getFilters());
                if (locationModel.getPath() != null) {
                    File file = new File(locationModel.getPath());
                    if (file.exists() && file.isDirectory()) {
                        fileVisitor.visit(this, file.toPath(), locationModel.getGroup());
                        myFileToTreat.addAll(fileVisitor.getFilesToTreat());
                        byteToTreat += fileVisitor.getBytesToTreat();
                    }
                }
            }
        } catch (CloneManagerException | IOException e) {
            System.out.println(e);
        }
        this.myFileProcessor.setMyFilesToTreat(myFileToTreat);        
        this.updateProgress(1.0, 1.0);
        return byteToTreat;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Autowired
    @Qualifier(value = "EnqueueMyFileProcessor")
    public void setMyFileProcessor(MyFileProcessor myFileProcessor) {
        this.myFileProcessor = myFileProcessor;
    }


}
