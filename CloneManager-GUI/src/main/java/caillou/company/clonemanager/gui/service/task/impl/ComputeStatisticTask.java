/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.background.service.impl.HashProcessor;
import caillou.company.clonemanager.background.util.contract.HashUtil;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticAnalyse;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticToCompute;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ComputeStatisticTask extends Task<StatisticAnalyse> {

    private MainModel mainModel;

    private StatisticToCompute StatisticToCompute;

    private List<GUIApplicationFile> resultingList;
    
    private WorkerMonitor workerMonitor; 
    
    private HashUtil hashUtil;
    
    @Override
    protected StatisticAnalyse call() throws Exception {
        workerMonitor.addWorker(WorkerMonitor.STATISTIC_WORKER, this);
        StatisticAnalyse statisticAnalyse = new StatisticAnalyse();

        if (getStatisticToCompute() == null) {
            throw new CloneManagerArgumentException("statisticAnalyse");
        }

        statisticAnalyse.setNbFileScanned(new Long(getStatisticToCompute().getMyFilesToTreat().size()));
        
        if (getMainModel().getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {     
            Map<String, List<GUIApplicationFile>> setPerHash = hashUtil.sortPerMD5(resultingList);
                        
            Long wastedSpace = (long) 0;
            Long numberOfDuplicateFiles = (long) 0;
                        
            for (Map.Entry<String, List<GUIApplicationFile>> entry : setPerHash.entrySet()) {
                Iterator<GUIApplicationFile> iterator = entry.getValue().iterator();
                int i=0;
                while(iterator.hasNext()){
                    if(i++ == 0){
                        iterator.next();
                        continue;
                    }
                    GUIApplicationFile myFile = iterator.next();
                    wastedSpace += myFile.getSize();
                    numberOfDuplicateFiles += 1;
                }
            }
            statisticAnalyse.setNumberOfDuplicateFiles(numberOfDuplicateFiles);
            statisticAnalyse.setWastedSpace(wastedSpace);
        }else if(getMainModel().getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_MISSING)){
            
            Map<String, List<Long>> sizePerHash = new HashMap<>();
            for(GUIApplicationFile guiApplicationFile : resultingList){                
                String MD5Print = guiApplicationFile.getMD5Print() == null ? HashProcessor.process(guiApplicationFile, null) : guiApplicationFile.getMD5Print();
                if(!sizePerHash.containsKey(MD5Print)){
                    sizePerHash.put(MD5Print, new ArrayList<Long>());
                }
                sizePerHash.get(MD5Print).add(guiApplicationFile.getSize());
            }
                        
            Long spaceToDuplicate = (long) 0;
            Long numberOfFileToDuplicate = (long) 0;
            for (Map.Entry<String, List<Long>> entry : sizePerHash.entrySet()) {
                Iterator<Long> iteSize = entry.getValue().iterator();
                numberOfFileToDuplicate += 1;
                spaceToDuplicate += iteSize.next();
            }
            statisticAnalyse.setNbFileToDuplicate(numberOfFileToDuplicate);
            statisticAnalyse.setSpaceToDuplicate(spaceToDuplicate);
        }
        workerMonitor.removeWorker(WorkerMonitor.STATISTIC_WORKER, this);
        return statisticAnalyse;
    }

    public MainModel getMainModel() {
        return mainModel;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void setResultingList(List<GUIApplicationFile> resultingList) {
        this.resultingList = resultingList;
    }
    
    public StatisticToCompute getStatisticToCompute() {
        return StatisticToCompute;
    }

    public void setStatisticToCompute(StatisticToCompute StatisticToCompute) {
        this.StatisticToCompute = StatisticToCompute;
    }
    
    @Autowired
    public void setWorkerMonitor(WorkerMonitor workerMonitor) {
        this.workerMonitor = workerMonitor;
    }

    @Autowired
    public void setHashUtil(HashUtil hashUtil) {
        this.hashUtil = hashUtil;
    }

    
    
}
