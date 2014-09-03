/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.task.contract;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.contract.Cancellable;
import caillou.company.clonemanager.gui.service.contract.MyFileProcessor;
import java.util.concurrent.RunnableFuture;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;

/**
 *
 * @author pierre
 */
public interface PartialHashTask<V> extends Worker<V>, EventTarget, RunnableFuture<V>, Cancellable, MyFileProcessor{
    
    public void setFilterStrategy(FilterStrategy<String, ApplicationFile> filterStrategy);
    public void setBytesToTreat(Long bytesToTreat);
    public void setOnSucceeded(EventHandler<WorkerStateEvent> value);
}
