/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.WindowsPreferredDimensions;
import caillou.company.clonemanager.gui.customComponent.results.ResultController;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticToCompute;
import caillou.company.clonemanager.gui.service.task.impl.ComputeStatisticTask;
import caillou.company.clonemanager.gui.service.task.impl.WorkerMonitor;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class AfterFullHashTaskHandler implements EventHandler<WorkerStateEvent> {

    private ResultController resultController;

    private ComputeStatisticTask computeStatisticTask;

    private AfterStatisticsComputedHandler afterStatisticsComputedHandler;
        
    private StatisticToCompute statisticToCompute;
            
    @Override
    public void handle(WorkerStateEvent event) {
        MainApp app = MainApp.getInstance();
        try {
            app.replaceSceneContent(Navigation.RESULTS_VIEW, WindowsPreferredDimensions.RESULT_VIEW_WIDTH, WindowsPreferredDimensions.RESULT_VIEW_HEIGHT);
        } catch (Exception ex) {
            Logger.getLogger(AfterFullHashTaskHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        resultController.displayErrors();
        computeStatisticTask.setStatisticToCompute(statisticToCompute);
        computeStatisticTask.setResultingList(resultController.valuesProperty());
        computeStatisticTask.setOnSucceeded(afterStatisticsComputedHandler);
        new Thread(computeStatisticTask).start();
    }

    @Autowired
    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    @Autowired
    public void setComputeStatisticTask(ComputeStatisticTask computeStatisticTask) {
        this.computeStatisticTask = computeStatisticTask;
    }

    @Autowired
    public void setAfterStatisticsComputedHandler(AfterStatisticsComputedHandler afterStatisticsComputedHandler) {
        this.afterStatisticsComputedHandler = afterStatisticsComputedHandler;
    }

    @Autowired
    public void setStatisticToCompute(StatisticToCompute statisticToCompute) {
        this.statisticToCompute = statisticToCompute;
    }
    
    
    
}
