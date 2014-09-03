/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.comparator.impl.MD5Comparator;
import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileFullyHashedEvent;
import caillou.company.clonemanager.background.exception.OrganizerException;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.impl.HashClassifier;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.customComponent.results.MyFileFX;
import caillou.company.clonemanager.gui.customComponent.results.MyRowFactory;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author pierre
 */
public class FullHashTask extends Task<List<GUIApplicationFile>> implements Cancellable{

    private Analyse<String, ApplicationFile> analyse = null;

    private Long bytesToTreat = (long) 0;
    private Long bytesRead = (long) 0;
    
    private final FilterStrategy<String, ApplicationFile> filterStrategy;
    private boolean doCssStuff = false;

    public FullHashTask(FilterStrategy<String, ApplicationFile> filterStrategy, Analyse<String, ApplicationFile> analyse) {
        this.analyse = analyse;
        this.filterStrategy = filterStrategy;
        if (analyse != null && analyse.getFilesThatMigthMatch() != null) {
            for (Map.Entry<String, Set<ApplicationFile>> entry : analyse.getFilesThatMigthMatch().entrySet()) {
                for (ApplicationFile myFile : entry.getValue()) {
                    bytesToTreat += myFile.getSize();
                }
            }
        }
    }

    @Override
    protected List<GUIApplicationFile> call() throws OrganizerException {
        List<GUIApplicationFile> results = new ArrayList<>();

        if (bytesToTreat == null) {
            throw new OrganizerException("Error while reading arguments : bytesToTreat is null");
        }

        if (filterStrategy == null) {
            throw new OrganizerException("Error while reading arguments : filterStrategy is null");
        }

        HashClassifier hashClassifier = new HashClassifier();
        hashClassifier.setCallingThread(this);
        hashClassifier.setFilterStrategy(filterStrategy);
        hashClassifier.getEventBus().register(this);

        Analyse<String, ApplicationFile> analyseResult = hashClassifier.classify(this.analyse);

        for (Map.Entry<String, Set<ApplicationFile>> entry : analyseResult.getFilesThatDoMatch().entrySet()) {
            for (ApplicationFile myFile : entry.getValue()) {
                GUIApplicationFile myFileFX = new MyFileFX(myFile);
                results.add(myFileFX);
            }
        }
        
        if(doCssStuff){
            this.doCssStuff(results);
        }
        
        ObservableList<GUIApplicationFile> observableList = FXCollections.observableList(results);
        this.updateProgress(1, 1);
        return observableList;
    }

    @Subscribe
    public void handleFileTreatedEvent(FileFullyHashedEvent fileFullyHashedEvent) {
        bytesRead += fileFullyHashedEvent.getFileSize();
        this.updateProgress(bytesRead, bytesToTreat);
    }

    private void doCssStuff(List<GUIApplicationFile> results){
        Collections.sort(results, new MD5Comparator());
        String lastMD5Print = null;
        String lastCssStyle = null;
        for (GUIApplicationFile myFileFX : results) {
            if (lastMD5Print == null) {
                lastCssStyle = MyRowFactory.DEFAULT_STYLE;
            } else {
                if (!myFileFX.getMD5Print().equals(lastMD5Print)) {
                    lastCssStyle = lastCssStyle.equals(MyRowFactory.HIGTHLIGHTED_STYLE) ? MyRowFactory.DEFAULT_STYLE : MyRowFactory.HIGTHLIGHTED_STYLE;
                }
            }
            myFileFX.setCssColor(lastCssStyle);
            lastMD5Print = myFileFX.getMD5Print();
        }
    }
    
    public void setDoCssStuff(boolean doCssStuff) {
        this.doCssStuff = doCssStuff;
    }    
}
