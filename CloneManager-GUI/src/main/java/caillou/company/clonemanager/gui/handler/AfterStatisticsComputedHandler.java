/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.common.StatisticsModel;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticAnalyse;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class AfterStatisticsComputedHandler implements EventHandler<WorkerStateEvent> {

    private MainModel mainModel;

    @Override
    public void handle(WorkerStateEvent event) {
        StatisticAnalyse statisticAnalyse = (StatisticAnalyse) event.getSource().getValue();
        StatisticsModel searchStatisticsModel = mainModel.getSearchStatisticsModel();
        TaskModel.TASK currentTask = mainModel.getTaskModel().getCurrentTask();
        searchStatisticsModel.setNbScannedFiles(statisticAnalyse.getNbFileScanned());
        switch (currentTask) {
            case DETECT_DOUBLONS:
                if(searchStatisticsModel.getInitialSpaceWasted().equals(new Long(-1))){
                    searchStatisticsModel.setInitialSpaceWasted(statisticAnalyse.getWastedSpace());
                }
                searchStatisticsModel.setNbDuplicateFiles(statisticAnalyse.getNumberOfDuplicateFiles());
                searchStatisticsModel.setSpaceWasted(statisticAnalyse.getWastedSpace());
                break;
            case DETECT_MISSING:
                searchStatisticsModel.setSpaceToDuplicate(statisticAnalyse.getSpaceToDuplicate());
                searchStatisticsModel.setNbFileToDuplicate(statisticAnalyse.getNbFileToDuplicate());
                break;
        }
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    
}
