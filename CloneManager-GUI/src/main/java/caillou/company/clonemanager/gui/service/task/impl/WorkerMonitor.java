/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Worker;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class WorkerMonitor {
    
    public static String HASH_WORKER;
    public static String STATISTIC_WORKER;
    
    private Map<String, List<Worker<?>>> workerToMonitor = new HashMap<>();
    
    public void addWorker(String category, Worker<?> worker){
        if(!workerToMonitor.containsKey(category)){
            workerToMonitor.put(category, new ArrayList<Worker<?>>());
        }
        workerToMonitor.get(category).add(worker);
    }
    
    public boolean removeWorker(String category, Worker<?> task){
        if(workerToMonitor.containsKey(category)){
            if(workerToMonitor.get(category).contains(task)){
                workerToMonitor.get(category).remove(task);
                return true;
            }
        }
        return false;
    }
    
    public void stop(String category){
        if(workerToMonitor.containsKey(category)){
            for(Worker<?> worker : workerToMonitor.get(category)){
                worker.cancel();
            }
        }
    }
    
}
