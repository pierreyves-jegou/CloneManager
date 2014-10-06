/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsFullHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsPlusGroupFullHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.MissingFullHashStrategy;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.customComponent.results.ResultController;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.customComponent.transition.TransitionController;
import caillou.company.clonemanager.gui.service.task.impl.FullHashTask;
import caillou.company.clonemanager.gui.service.task.impl.WorkerMonitor;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class AfterPartialHashTaskHandler implements EventHandler<WorkerStateEvent> {

    private MainModel mainModel;
    
    private FullHashTask fullHashTask;

    private TransitionController transitionController;
    
    private ResultController resultController;
    
    private AfterFullHashTaskHandler afterFullHashTaskHandler;
        
    @Override
    public void handle(WorkerStateEvent event) {
        FilterStrategy<String, ApplicationFile> fullHashStrategy = null;
        boolean detectDoublonOperation = false;
        final TaskModel.TASK currentTask = mainModel.getTaskModel().getCurrentTask();
        final LocationsModel locationsModel = mainModel.getLocationsModel();
        switch (currentTask) {
            case DETECT_DOUBLONS:
                if (locationsModel.isDetectsIdentiqueFilesWithinALocation()) {
                    fullHashStrategy = new DoublonsFullHashStrategy();
                } else {
                    fullHashStrategy = new DoublonsPlusGroupFullHashStrategy();
                }
                detectDoublonOperation = true;
                break;
            case DETECT_MISSING:
                fullHashStrategy = new MissingFullHashStrategy();
                break;
        }
        Analyse<String, ApplicationFile> previousAnalyse = (Analyse<String, ApplicationFile>) event.getSource().getValue();
        fullHashTask.setFilterStrategy(fullHashStrategy);
        fullHashTask.setInputData(previousAnalyse);
        fullHashTask.setDoCssStuff(detectDoublonOperation);
        ProgressIndicator fullHashTaskProgress = transitionController.getProgressBarFullHashId();
        fullHashTaskProgress.progressProperty().bind(fullHashTask.progressProperty());
        resultController.valuesProperty().bind(fullHashTask.valueProperty());
        fullHashTask.setOnSucceeded(afterFullHashTaskHandler);
        new Thread(fullHashTask).start();
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
    
    @Autowired
    public void setFullHashTask(FullHashTask fullHashTask){
        this.fullHashTask = fullHashTask;
    }
    
    public void setTransitionController(TransitionController transitionController) {
        this.transitionController = transitionController;
    }

    @Autowired
    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    @Autowired
    public void setAfterFullHashTaskHandler(AfterFullHashTaskHandler AfterFullHashTaskHandler) {
        this.afterFullHashTaskHandler = AfterFullHashTaskHandler;
    }    
}
