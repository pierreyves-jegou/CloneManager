/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.filter.Filter;
import caillou.company.clonemanager.background.service.impl.FileVisitor;
import caillou.company.clonemanager.gui.bean.impl.VisitDirectoriesResult;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.location.LocationModel;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.service.task.contract.EnqueueTask;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.concurrent.Task;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class EnqueueImplTask extends Task<VisitDirectoriesResult> implements EnqueueTask<VisitDirectoriesResult> {

    private Set<ApplicationFile> filesToTreat = new HashSet<>();

    private MainModel mainModel;
    
    private WorkerMonitor workerMonitor;

    @Override
    public Set<ApplicationFile> getMyFilesToTreat() {
        return filesToTreat;
    }

    @Override
    public void setMyFilesToTreat(Set<ApplicationFile> myFilesToTreat) {
        this.filesToTreat = myFilesToTreat;
    }

    @Override
    protected VisitDirectoriesResult call() throws Exception {
        workerMonitor.addWorker(WorkerMonitor.HASH_WORKER, this);
        Set<ApplicationFile> myFileToTreat = new HashSet<>();
        Long byteToTreat = (long) 0;

        Filter<ApplicationFile> filter = mainModel.getCritereModel().getFilters();
        LocationsModel locationsModel = mainModel.getLocationsModel();
        Iterator<LocationModel> ite = locationsModel.iterator();

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

        this.setMyFilesToTreat(myFileToTreat);
        this.updateProgress(1.0, 1.0);

        VisitDirectoriesResult visitDirectoriesResult = new VisitDirectoriesResult();
        visitDirectoriesResult.setByteToTreat(byteToTreat);
        visitDirectoriesResult.setFilesToTreat(myFileToTreat);
        workerMonitor.removeWorker(WorkerMonitor.HASH_WORKER, this);
        return visitDirectoriesResult;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
    
    @Autowired
    public void setWorkerMonitor(WorkerMonitor workerMonitor) {
        this.workerMonitor = workerMonitor;
    }
    
}
