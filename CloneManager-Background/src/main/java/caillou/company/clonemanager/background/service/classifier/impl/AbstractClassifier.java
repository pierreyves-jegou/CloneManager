package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileTreatedEvent;
import caillou.company.clonemanager.background.exception.CloneManagerException;
import caillou.company.clonemanager.background.exception.CloneManagerIOException;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import com.google.common.eventbus.EventBus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public abstract class AbstractClassifier<T, V extends ApplicationFile> implements Filterable<T, V>, Classifier<V> {

    private static final Logger log = Logger.getLogger(AbstractClassifier.class.getName());
    private final Map<T, Set<V>> results = new HashMap();
    private FilterStrategy<T, V> filterStrategy;
    protected EventBus eventBus = new EventBus("CLASSIFIER_SIZE");
    private Cancellable callingThread;

    public AbstractClassifier() {
    }

    public abstract <U extends FileTreatedEvent> U getFileTreatedEvent();

    public Map<T, Set<V>> getResults() {
        return results;
    }

    @Override
    public void setFilterStrategy(FilterStrategy<T, V> filterStrategy) {
        this.filterStrategy = filterStrategy;
    }

    @Override
    public FilterStrategy<T, V> getFilterStrategy() {
        return filterStrategy;
    }

    public Analyse<T, V> process(Analyse<T, V> analyse) throws CloneManagerException{
        this.classify(analyse);
        return this.filter(analyse);
    }

    private Analyse<T, V> filter(Analyse<T, V> analyse){
        Analyse<T, V> analyseResult = this.getFilterStrategy().filter(this.getResults());
        analyseResult.addAllEntryThatDoMatch(analyse.getFilesThatDoMatch());
        analyseResult.addAllEntryThatDoMatch(analyse.getFilesThatDoMatch());
        return analyseResult;
    }
    
    private void classify(Analyse<T, V> analyse) throws CloneManagerException{
        this.classifyAll(analyse.getFilesNotTreated(), true);
        for (Map.Entry<T, Set<V>> entry : analyse.getFilesThatMigthMatch().entrySet()) {
            // Cancelling
            if (this.callingThread.isCancelled()) {
                return;
            }
            this.classifyAll(entry.getValue(), true);
        }
    }
    
    private void classifyAll(Set<V> myFilesNotSorted, boolean useEventBus) throws CloneManagerException {
        for (V myFile : myFilesNotSorted) {
    
            // Cancelling
            if (this.callingThread.isCancelled()) {
                log.debug("Cancel by the user");
                return;
            }

            try{
                this.classify(myFile);
            }catch(CloneManagerIOException cloneManagerIOException){
                log.error(cloneManagerIOException.getMessage());
            }

            if (useEventBus) {
                FileTreatedEvent fileTreatedEvent = this.getFileTreatedEvent();
                fileTreatedEvent.setFileSize(myFile.getSize());
                eventBus.post(fileTreatedEvent);
            }
        }

    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setCallingThread(Cancellable callingThread) {
        this.callingThread = callingThread;
    }
}
