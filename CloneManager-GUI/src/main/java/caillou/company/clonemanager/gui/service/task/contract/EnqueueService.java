/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.task.contract;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.gui.service.contract.MyFileProcessor;
import java.util.Set;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 *
 * @author pierre
 */
public interface EnqueueService<V> extends Worker<V>, EventTarget, MyFileProcessor, BeanFactoryAware{

    public void setOnSucceeded(EventHandler<WorkerStateEvent> value);
    
    public Set<ApplicationFile> getMyFilesToTreat();
    
    public void restart();
    
    public void start();
    
}
