/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.search;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsFullHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsPartialHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsPlusGroupFullHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.DoublonsPlusGroupPartialHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.MissingFullHashStrategy;
import caillou.company.clonemanager.background.service.classifier.strategy.MissingPartialHashStrategy;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.StyleSheet;
import caillou.company.clonemanager.gui.WindowsPreferredDimensions;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.bean.impl.StatisticsPojo;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.common.StatisticsModel;
import caillou.company.clonemanager.gui.customComponent.locationContainer.LocationsModel;
import caillou.company.clonemanager.gui.customComponent.results.ResultController;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticToCompute;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.customComponent.transition.TransitionController;
import caillou.company.clonemanager.gui.event.CancelTaskEvent;
import caillou.company.clonemanager.gui.handler.AfterStatisticsComputedHandler;
import caillou.company.clonemanager.gui.service.task.contract.EnqueueService;
import caillou.company.clonemanager.gui.service.task.contract.PartialHashTask;
import caillou.company.clonemanager.gui.service.task.impl.ComputeStatisticTask;
import caillou.company.clonemanager.gui.service.task.impl.FullHashTask;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import com.google.common.eventbus.Subscribe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import org.controlsfx.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pc
 */
@Component
public class SearchController implements Initializable {

    @FXML
    private Button validerButton;

    private EnqueueService<StatisticsPojo> enqueueService;

    private PartialHashTask partialHashTask;

    private FullHashTask fullHashTask;

    private ComputeStatisticTask computeStatisticTask;

    private ResultController resultController;

    private MainModel mainModel;

    private TransitionController transitionController;

    private Parent transitionFxml;
    
    private String bundleForKeyTitleProcessingData;

    public EnqueueService getEnqueueService() {
        return enqueueService;
    }

    @Autowired
    public void setEnqueueService(EnqueueService enqueueService) {
        this.enqueueService = enqueueService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainApp app = MainApp.getInstance();
        app.getStage().sizeToScene();
        app.getStage().hide();
        app.getStage().show();
        validerButton.disableProperty().bind(Bindings.not(Bindings.and(mainModel.getLocationsModel().validProperty(), mainModel.getCritereModel().validProperty())));
        this.buildTransitionPopup();
        transitionController.getEventBus().register(this);
        StatisticsModel searchStatisticsModel = SpringFxmlLoader.getBean(StatisticsModel.class);
        mainModel.setSearchStatisticsModel(searchStatisticsModel);
    }

    private void initializeResourceBundle(ResourceBundle resources){
       bundleForKeyTitleProcessingData = resources.getString("title.processingData");
    }
    
    private void buildTransitionPopup() {
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.TRANSITION_POPUP);
        transitionController = (TransitionController) loadingMojo.getController();
        transitionFxml = loadingMojo.getParent();
        final Dialog dialogTransition = new Dialog(MainApp.getInstance().getStage(), bundleForKeyTitleProcessingData);
        dialogTransition.getStylesheets().add(StyleSheet.DIALOG_CSS);
        transitionController.setWrappingDialog(dialogTransition);
        dialogTransition.setContent(transitionFxml);
    }

    private void showTransition() {
        Platform.runLater(new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                transitionController.getWrappingDialog().show();
                return null;
            }
        });
    }

    @FXML
    private void mainMenuAction(ActionEvent event) throws Exception {
        mainModel.resetLocationsModel();
        MainApp app = MainApp.getInstance();
        app.replaceSceneContent(Navigation.TASK_CHOICE_VIEW, WindowsPreferredDimensions.TASKCHOICE_VIEW_WIDTH, WindowsPreferredDimensions.TASKCHOICE_VIEW_HEIGHT);
    }

    @FXML
    private void searchAction(ActionEvent event) throws Exception {
        showTransition();
        try {
            final LocationsModel locationsModel = mainModel.getLocationsModel();
            if (!locationsModel.enableGroupingProperty().get()) {
                locationsModel.setDefaultGroups();
            }

            final TaskModel.TASK currentTask = mainModel.getTaskModel().getCurrentTask();

            ProgressIndicator scanningTaskProgress = transitionController.getProgressScanningFiles();
            scanningTaskProgress.progressProperty().bind(enqueueService.progressProperty());
            enqueueService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                @Override
                public void handle(WorkerStateEvent t) {

                    Long byteToTreat = (Long) t.getSource().getValue();
                    FilterStrategy<String, GUIApplicationFile> partialHashStrategy = null;

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

                    partialHashTask = SpringFxmlLoader.getBean(null, PartialHashTask.class);
                    partialHashTask.setFilterStrategy(partialHashStrategy);
                    partialHashTask.setBytesToTreat(byteToTreat);
                    partialHashTask.setMyFilesToTreat(enqueueService.getMyFilesToTreat());

                    // Attach the progress bar to that task
                    ProgressIndicator partialTaskProgress = transitionController.getProgressBarPartialHashId();
                    partialTaskProgress.progressProperty().bind(partialHashTask.progressProperty());

                    partialHashTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                        @Override
                        public void handle(WorkerStateEvent event) {
                            FilterStrategy<String, ApplicationFile> fullHashStrategy = null;
                            boolean detectDoublonOperation = false;

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

                            fullHashTask = new FullHashTask(fullHashStrategy, previousAnalyse);
                            fullHashTask.setDoCssStuff(detectDoublonOperation);
                            ProgressIndicator fullHashTaskProgress = transitionController.getProgressBarFullHashId();
                            fullHashTaskProgress.progressProperty().bind(fullHashTask.progressProperty());
                            resultController.valuesProperty().bind(fullHashTask.valueProperty());

                            fullHashTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent event) {
                                    StatisticToCompute statisticToCompute = SpringFxmlLoader.getBean("statisticToCompute", StatisticToCompute.class);
                                    computeStatisticTask = SpringFxmlLoader.getBean(null, ComputeStatisticTask.class);
                                    computeStatisticTask.setStatisticToCompute(statisticToCompute);
                                    computeStatisticTask.setResultingList(resultController.valuesProperty());
                                    
                                    AfterStatisticsComputedHandler afterStatisticsComputedHandler = SpringFxmlLoader.getBean(AfterStatisticsComputedHandler.class);
                                    computeStatisticTask.setOnSucceeded(afterStatisticsComputedHandler);
                                    new Thread(computeStatisticTask).start();
                                }
                            });

                            new Thread(fullHashTask).start();
                        }
                    });

                    new Thread(partialHashTask).start();
                }
            });
            if (!enqueueService.getState().equals(javafx.concurrent.Worker.State.READY)) {
                enqueueService.restart();
            } else {
                enqueueService.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Subscribe
    public void handleCancelEvent(CancelTaskEvent cancelTaskEvent) {
        if (fullHashTask != null) {
            fullHashTask.cancel();
        }
        if (partialHashTask != null) {
            partialHashTask.cancel();
        }
        if (enqueueService != null) {
            enqueueService.cancel();
        }
    }

    public ResultController getResultController() {
        return resultController;
    }

    @Autowired
    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
