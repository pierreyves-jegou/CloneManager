/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.search;

import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.StyleSheet;
import caillou.company.clonemanager.gui.WindowsPreferredDimensions;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.bean.impl.StatisticsPojo;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.common.StatisticsModel;
import caillou.company.clonemanager.gui.customComponent.results.ResultController;
import caillou.company.clonemanager.gui.customComponent.transition.TransitionController;
import caillou.company.clonemanager.gui.event.CancelTaskEvent;
import caillou.company.clonemanager.gui.handler.AfterEnqueueHandler;
import caillou.company.clonemanager.gui.service.task.contract.EnqueueTask;
import caillou.company.clonemanager.gui.service.task.contract.PartialHashTask;
import caillou.company.clonemanager.gui.service.task.impl.CloneManagerIOExceptionBean;
import caillou.company.clonemanager.gui.service.task.impl.ComputeStatisticTask;
import caillou.company.clonemanager.gui.service.task.impl.FullHashTask;
import caillou.company.clonemanager.gui.service.task.impl.WorkerMonitor;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import com.google.common.eventbus.Subscribe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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

    private EnqueueTask<StatisticsPojo> enqueueService;

    private ResultController resultController;

    private MainModel mainModel;

    private TransitionController transitionController;

    private Parent transitionFxml;

    private CloneManagerIOExceptionBean cloneManagerIOExceptionBean;

    @Autowired
    public void setEnqueueService(EnqueueTask enqueueService) {
        this.enqueueService = enqueueService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainApp app = MainApp.getInstance();
        app.getStage().sizeToScene();
        app.getStage().hide();
        app.getStage().show();
        validerButton.disableProperty().bind(Bindings.not(Bindings.and(mainModel.getLocationsModel().validProperty(), mainModel.getCritereModel().validProperty())));
        StatisticsModel searchStatisticsModel = SpringFxmlLoader.getBean(StatisticsModel.class);
        mainModel.setSearchStatisticsModel(searchStatisticsModel);
    }

    private void buildTransitionPopup() {
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.TRANSITION_POPUP);
        transitionController = (TransitionController) loadingMojo.getController();
        transitionController.getEventBus().register(this);
        transitionFxml = loadingMojo.getParent();
        final Dialog dialogTransition = new Dialog(MainApp.getInstance().getStage(), SpringFxmlLoader.getResourceBundle().getString("title.processingData"));
        dialogTransition.getStylesheets().add(StyleSheet.DIALOG_CSS);
        transitionController.setWrappingDialog(dialogTransition);
        dialogTransition.setContent(transitionFxml);
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
    private void searchAction(ActionEvent event) {
        this.cloneManagerIOExceptionBean.clear();
        buildTransitionPopup();
        enqueueService = SpringFxmlLoader.getBean(EnqueueTask.class);
        AfterEnqueueHandler afterEnqueueHandler = SpringFxmlLoader.getBean(AfterEnqueueHandler.class);
        ProgressIndicator scanningTaskProgress = transitionController.getProgressScanningFiles();
        scanningTaskProgress.progressProperty().bind(enqueueService.progressProperty());
        afterEnqueueHandler.setTransitionController(transitionController);
        enqueueService.setOnSucceeded(afterEnqueueHandler);
        new Thread(enqueueService).start();
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

    @Autowired
    public void setCloneManagerIOExceptionBean(CloneManagerIOExceptionBean cloneManagerIOExceptionBean) {
        this.cloneManagerIOExceptionBean = cloneManagerIOExceptionBean;
    }
}
