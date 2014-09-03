package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileTreatedEvent;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import com.google.common.eventbus.EventBus;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractClassifier<T, V extends ApplicationFile> implements Filterable<T, V>, Classifier<V> {

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

    public Analyse<T, V> classify(Set<V> filesNotTreated) {
        Analyse<T, V> analyse = new Analyse<>();
        analyse.setFilesNotTreated(filesNotTreated);
        return this.classify(analyse);
    }

    public Analyse<T, V> classify(Analyse<T, V> analyse) {

        this.classifyAll(analyse.getFilesNotTreated(), true);

        for (Map.Entry<T, Set<V>> entry : analyse.getFilesThatMigthMatch().entrySet()) {

            // Cancelling
            if (this.callingThread.isCancelled()) {
                System.out.println("Jarrete dans AbstractHashClassifier 1");
                return this.getFilterStrategy().filter(this.getResults());
            }

            this.classifyAll(entry.getValue(), true);
        }
        Analyse<T, V> analyseResult = this.getFilterStrategy().filter(this.getResults());
        analyseResult.addAllEntryThatDoMatch(analyse.getFilesThatDoMatch());
        analyseResult.addAllEntryThatDoMatch(analyse.getFilesThatDoMatch());
        return analyseResult;
    }

    public void classifyAll(Set<V> myFilesNotSorted, boolean useEventBus) {
        for (V myFile : myFilesNotSorted) {

            // Cancelling
            if (this.callingThread.isCancelled()) {
                return;
            }

            try {
                this.classify(myFile);
            } catch (FileNotFoundException fileNotFoundException) {
                System.err.println("Problem while reading file : " + myFile.getAbsolutePath());
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
