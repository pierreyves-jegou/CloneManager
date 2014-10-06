/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.comparator.impl.MD5Comparator;
import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileFullyHashedEvent;
import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.background.exception.CloneManagerException;

import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.impl.HashClassifier;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.customComponent.results.MyFileFX;

import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class FullHashTask extends Task<List<GUIApplicationFile>> implements Cancellable {

    private Analyse<String, ApplicationFile> analyse = null;

    private Long bytesToTreat = (long) 0;
    private Long bytesRead = (long) 0;

    private FilterStrategy<String, ApplicationFile> filterStrategy;
    private boolean doCssStuff = false;

    private WorkerMonitor workerMonitor;
    
    @Override
    protected List<GUIApplicationFile> call() throws CloneManagerException {
        workerMonitor.addWorker(WorkerMonitor.HASH_WORKER, this);
        List<GUIApplicationFile> results = new ArrayList<>();

         if (analyse != null && analyse.getFilesThatMigthMatch() != null) {
            for (Map.Entry<String, Set<ApplicationFile>> entry : analyse.getFilesThatMigthMatch().entrySet()) {
                for (ApplicationFile myFile : entry.getValue()) {
                    bytesToTreat += myFile.getSize();
                }
            }
        }
        
        if (bytesToTreat == null) {
            throw new CloneManagerArgumentException("bytesToTreat");
        }

        if (filterStrategy == null) {
            throw new CloneManagerArgumentException("filterStrategy");
        }

        HashClassifier hashClassifier = new HashClassifier();
        hashClassifier.setCallingThread(this);
        hashClassifier.setFilterStrategy(filterStrategy);
        hashClassifier.getEventBus().register(this);

        Analyse<String, ApplicationFile> analyseResult = hashClassifier.process(this.analyse);

        for (Map.Entry<String, Set<ApplicationFile>> entry : analyseResult.getFilesThatDoMatch().entrySet()) {
            for (ApplicationFile myFile : entry.getValue()) {
                GUIApplicationFile myFileFX = new MyFileFX(myFile);
                results.add(myFileFX);
            }
        }

        if (doCssStuff) {
            this.doCssStuff(results);
        }

        ObservableList<GUIApplicationFile> observableList = FXCollections.observableList(results);
        this.updateProgress(1, 1);
        workerMonitor.removeWorker(WorkerMonitor.HASH_WORKER, this);
        return observableList;
    }

    @Subscribe
    public void handleFileTreatedEvent(FileFullyHashedEvent fileFullyHashedEvent) {
        bytesRead += fileFullyHashedEvent.getFileSize();
        this.updateProgress(bytesRead, bytesToTreat);
    }

    private void doCssStuff(List<GUIApplicationFile> results) {
        Collections.sort(results, new MD5Comparator());
        Map<String, List<GUIApplicationFile>> guiApplicationFilesPerHash = new HashMap<>();
        for (GUIApplicationFile myFileFX : results) {
            if (!guiApplicationFilesPerHash.containsKey(myFileFX.getMD5Print())) {
                guiApplicationFilesPerHash.put(myFileFX.getMD5Print(), new ArrayList<GUIApplicationFile>());
            }
            guiApplicationFilesPerHash.get(myFileFX.getMD5Print()).add(myFileFX);
        }

        for (Map.Entry<String, List<GUIApplicationFile>> entry : guiApplicationFilesPerHash.entrySet()) {
            List<GUIApplicationFile> applicationFiles = entry.getValue();
            for (int i = 0; i < applicationFiles.size(); i++) {
                GUIApplicationFile guiApplicationFile = applicationFiles.get(i);
                if (i == 0) {
                    guiApplicationFile.setCurrentPostion(GUIApplicationFile.POSITION.FIRST);
                    continue;
                }
                if (i == applicationFiles.size() - 1) {
                    guiApplicationFile.setCurrentPostion(GUIApplicationFile.POSITION.LAST);
                } else {
                    guiApplicationFile.setCurrentPostion(GUIApplicationFile.POSITION.MIDDLE);
                }
            }
        }
    }

    public void setDoCssStuff(boolean doCssStuff) {
        this.doCssStuff = doCssStuff;
    }
    
    public void setFilterStrategy(FilterStrategy<String, ApplicationFile> filterStrategy){
        this.filterStrategy = filterStrategy;
    }
    
    public void setInputData(Analyse<String, ApplicationFile> inputData){
        this.analyse = inputData;
    }
    
    @Autowired
    public void setWorkerMonitor(WorkerMonitor workerMonitor) {
        this.workerMonitor = workerMonitor;
    }
    
}
