/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FilePartiallyHashedEvent;
import caillou.company.clonemanager.background.exception.OrganizerException;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.impl.PartialHashClassifier;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.service.task.contract.PartialHashTask;
import com.google.common.eventbus.Subscribe;


import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class PartialHashTaskImpl extends Task<Analyse<String, ApplicationFile>> implements PartialHashTask<Analyse<String, ApplicationFile>>{
    
    private Set<ApplicationFile> filesToTreat;
    
    private Long bytesToTreat;
    
    private Long bytesRead = (long) 0;
    
    private FilterStrategy<String, ApplicationFile> filterStrategy;
         
    @Override
    protected Analyse<String, ApplicationFile> call() {
        if(filesToTreat == null){
            try {
                throw new OrganizerException("Error while reading arguments : filesToTreat is null");
            } catch (OrganizerException ex) {
                Logger.getLogger(PartialHashTaskImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(bytesToTreat == null){
            try {
                throw new OrganizerException("Error while reading arguments : bytesToTreat is null");
            } catch (OrganizerException ex) {
                Logger.getLogger(PartialHashTaskImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        PartialHashClassifier partialHashClassifier = new PartialHashClassifier();
        partialHashClassifier.setCallingThread(this);
        partialHashClassifier.setFilterStrategy(filterStrategy);
        partialHashClassifier.getEventBus().register(this);
        Analyse<String, ApplicationFile> results = partialHashClassifier.classify(filesToTreat);
        return results;
    }

    @Override
    public void setFilterStrategy(FilterStrategy<String, ApplicationFile> filterStrategy){
        this.filterStrategy = filterStrategy;
    }
    
    public Set<ApplicationFile> getFilesToTreat() {
        return filesToTreat;
    }

    public Long getBytesToTreat() {
        return bytesToTreat;
    }

    @Override
    public void setBytesToTreat(Long bytesToTreat) {
        this.bytesToTreat = bytesToTreat;
    }

    @Subscribe
    public void handleFileTreatedEvent(FilePartiallyHashedEvent filePartiallyHashedEvent){
        bytesRead += filePartiallyHashedEvent.getFileSize();
        this.updateProgress(bytesRead, bytesToTreat);
    }

    @Override
    public void setMyFilesToTreat(Set<ApplicationFile> myFilesToTreat) {
        this.filesToTreat = myFilesToTreat;
    }
}
