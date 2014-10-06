/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FilePartiallyHashedEvent;
import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.background.exception.CloneManagerException;
import caillou.company.clonemanager.background.exception.CloneManagerIOException;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.impl.Classifier;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import com.google.common.eventbus.Subscribe;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

/**
 *
 * @author pierre
 */
public abstract class AbstractHashTask<V> extends Task<V>{
    
//    private Analyse<String, ApplicationFile> analyse = null;
//
//    private Long bytesToTreat = null;
//    private Long bytesRead = (long) 0;
//    
//    private FilterStrategy<String, ApplicationFile> filterStrategy;
//    private Classifier<ApplicationFile> classifier;
//    private int nbFileRead = 0;
//    
//    private List<CloneManagerIOException> readingErrors;
//    
//    public AbstractHashTask(FilterStrategy<String, ApplicationFile> filterStrategy, Analyse<String, ApplicationFile> analyse){
//        this.analyse = analyse;
//        this.filterStrategy = filterStrategy;
//        
//    }
    
    @Override
    protected V call() throws CloneManagerException {  
//        this.checkMembers();
//        this.classify();
//        this.filter();
        return null;
    }
    
//    private void classify() throws CloneManagerException{
//        Map<String, 
//        
//        this.classifyCollection(analyse.getFilesNotTreated());
//        
//        // Check if the user want to cancel the task
//        if(this.isCancelled()){
//            return;
//        }
//        
//        for (Map.Entry<String, Set<ApplicationFile>> entry : analyse.getFilesThatMigthMatch().entrySet()) {
//             this.classifyCollection(entry.getValue());
//             // Check if the user want to cancel the task
//            if(this.isCancelled()){
//                return;
//            }
//        }
//    }
//    
//    private void classifyCollection(Collection<ApplicationFile> collectionToClassify) throws CloneManagerException{
//        Iterator<ApplicationFile> iteFilesToClassify = collectionToClassify.iterator();
//        while(iteFilesToClassify.hasNext()){
//            ApplicationFile applicationFile = iteFilesToClassify.next();
//            try {
//                classifier.classify(applicationFile);
//            } catch (CloneManagerIOException ex) {
//                Logger.getLogger(AbstractHashTask.class.getName()).log(Level.SEVERE, null, ex);
//                iteFilesToClassify.remove();
//            }
//        
//            this.updateProgress(applicationFile.getSize(), bytesToTreat);
//        }
//    }
//        
//    private void filter(){
//        
//    }
//        
//    protected abstract V subCall();
//    
//    
//    protected void checkMembers() throws CloneManagerArgumentException{
//        if(filterStrategy == null){
//            CloneManagerArgumentException cloneManagerArgumentException = new CloneManagerArgumentException();
//            cloneManagerArgumentException.addArgument("filterStrategy");
//            throw cloneManagerArgumentException;
//        }
//    }
//
//    @Subscribe
//    public void handleFileTreatedEvent(FilePartiallyHashedEvent filePartiallyHashedEvent){
//        bytesRead += filePartiallyHashedEvent.getFileSize();
//        this.updateProgress(bytesRead, bytesToTreat);
//    }
//    
//    
//    public Long getBytesToTreat() {
//        return bytesToTreat;
//    }
//
//    public void setBytesToTreat(Long bytesToTreat) {
//        this.bytesToTreat = bytesToTreat;
//    }
//
//    public Long getBytesRead() {
//        return bytesRead;
//    }
//
//    public void setBytesRead(Long bytesRead) {
//        this.bytesRead = bytesRead;
//    }
//
//    public FilterStrategy<String, ApplicationFile> getFilterStrategy() {
//        return filterStrategy;
//    }
//
//    public void setFilterStrategy(FilterStrategy<String, ApplicationFile> filterStrategy) {
//        this.filterStrategy = filterStrategy;
//    }
//    
//    
//    
}
