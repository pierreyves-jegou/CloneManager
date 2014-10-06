/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsPartialHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsPlusGroupPartialHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.MissingPartialHashStrategy;
import caillou.company.clonemanager.gui.bean.impl.VisitDirectoriesResult;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.customComponent.transition.TransitionController;
import caillou.company.clonemanager.gui.service.task.contract.PartialHashTask;
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
public class AfterEnqueueHandler implements EventHandler<WorkerStateEvent> {

    private MainModel mainModel;

    private PartialHashTask<Analyse<String, ApplicationFile>> partialHashTask;
    
    private TransitionController transitionController;

    private AfterPartialHashTaskHandler afterPartialHashTaskHandler;
        
    @Override
    public void handle(WorkerStateEvent event) {
        VisitDirectoriesResult visitDirectoriesResult = (VisitDirectoriesResult) event.getSource().getValue();
        Long byteToTreat = visitDirectoriesResult.getByteToTreat();
        FilterStrategy<String, ApplicationFile> partialHashStrategy = null;
        final LocationsModel locationsModel = mainModel.getLocationsModel();

        final TaskModel.TASK currentTask = mainModel.getTaskModel().getCurrentTask();
        switch (currentTask) {
            case DETECT_DOUBLONS:
                if (locationsModel.isDetectsIdentiqueFilesWithinALocation()) {
                    partialHashStrategy = new DoublonsPartialHashStrategy();
                } else {
                    partialHashStrategy = new DoublonsPlusGroupPartialHashStrategy();
                }

                break;
            case DETECT_MISSING:
                partialHashStrategy = new MissingPartialHashStrategy();
                break;
        }

        partialHashTask.setFilterStrategy(partialHashStrategy);
        partialHashTask.setBytesToTreat(byteToTreat);
        partialHashTask.setMyFilesToTreat(visitDirectoriesResult.getFilesToTreat());

        // Attach the progress bar to that task
        ProgressIndicator partialTaskProgress = transitionController.getProgressBarPartialHashId();
        partialTaskProgress.progressProperty().bind(partialHashTask.progressProperty());

        afterPartialHashTaskHandler.setTransitionController(transitionController);
        partialHashTask.setOnSucceeded(afterPartialHashTaskHandler);
        new Thread(partialHashTask).start();
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void setTransitionController(TransitionController transitionController) {
        this.transitionController = transitionController;
    }

    @Autowired
    public void setAfterPartialHashTaskHandler(AfterPartialHashTaskHandler AfterPartialHashTaskHandler) {
        this.afterPartialHashTaskHandler = AfterPartialHashTaskHandler;
    }

    @Autowired
    public void setPartialHashTask(PartialHashTask<Analyse<String, ApplicationFile>> partialHashTask) {
        this.partialHashTask = partialHashTask;
    }   
    
}
