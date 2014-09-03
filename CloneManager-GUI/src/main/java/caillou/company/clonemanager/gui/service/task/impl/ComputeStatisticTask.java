/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.exception.OrganizerException;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticAnalyse;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticToCompute;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
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
    
    @Override
    protected StatisticAnalyse call() throws Exception {
        StatisticAnalyse statisticAnalyse = new StatisticAnalyse();

        if (getStatisticToCompute() == null) {
            throw new OrganizerException("Member 'statisticAnalyse' can't be null");
        }

        statisticAnalyse.setNbFileScanned(new Long(getStatisticToCompute().getMyFilesToTreat().size()));
        
        Map<String, Set<GUIApplicationFile>> setPerHash = new HashMap<>();
        for(GUIApplicationFile myFileFX : resultingList){
            if(!setPerHash.containsKey(myFileFX.getMD5Print())){
                setPerHash.put(myFileFX.getMD5Print(), new HashSet<GUIApplicationFile>());
            }
            setPerHash.get(myFileFX.getMD5Print()).add(myFileFX);
        }
        
        if (getMainModel().getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) { 
            Long wastedSpace = (long) 0;
            Long numberOfDuplicateFiles = (long) 0;
                        
            for (Map.Entry<String, Set<GUIApplicationFile>> entry : setPerHash.entrySet()) {
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
            Long spaceToDuplicate = (long) 0;
            Long numberOfFileToDuplicate = (long) 0;
            for (Map.Entry<String, Set<GUIApplicationFile>> entry : setPerHash.entrySet()) {
                Iterator<GUIApplicationFile> iterator = entry.getValue().iterator();
                numberOfFileToDuplicate += 1;
                spaceToDuplicate += iterator.next().getSize();
            }
            statisticAnalyse.setNbFileToDuplicate(numberOfFileToDuplicate);
            statisticAnalyse.setSpaceToDuplicate(spaceToDuplicate);
        }

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

}
