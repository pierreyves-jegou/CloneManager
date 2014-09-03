/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.statistic;

import caillou.company.clonemanager.gui.Image;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.converter.ByteStringConverter;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class StatisticHelper {

    private static MainModel mainModel;

    public static List<Node> createStaticList() {

        if (StatisticHelper.mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            return createStaticDoublonList();
        }else if(StatisticHelper.mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_MISSING)){
            return createStaticMissingList();
        }else{
            return null;
        }
    }

    private static List<Node> createStaticDoublonList() {
        List<Node> results = new ArrayList<>();
        // Number of file scanned
        LoadingMojo loadingMojoFilesScanned = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticControllerFilesScanned = (StatisticController) loadingMojoFilesScanned.getController();
        statisticControllerFilesScanned.setImage(Image.SCANNED_FILES);
        statisticControllerFilesScanned.setStaticText("Nombre de fichiers scannés");        
        statisticControllerFilesScanned.dynamicTextProperty().bind(mainModel.getSearchStatisticsModel().nbScannedFilesProperty().asString());
        results.add(loadingMojoFilesScanned.getParent());
        
        // Space wasted
        LoadingMojo loadingMojoWastedSpace = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticControllerWastedSpace = (StatisticController) loadingMojoWastedSpace.getController();
        statisticControllerWastedSpace.setImage(Image.WASTED_SPACE);
        statisticControllerWastedSpace.setStaticText("Espace dupliqué");
        Bindings.bindBidirectional(statisticControllerWastedSpace.dynamicTextProperty(), mainModel.getSearchStatisticsModel().spaceWastedProperty(), new ByteStringConverter());
        results.add(loadingMojoWastedSpace.getParent());
        
        // Number of file duplicated
        LoadingMojo loadingMojoNumberDuplicated = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticControllerNumberDuplicated = (StatisticController) loadingMojoNumberDuplicated.getController();
        statisticControllerNumberDuplicated.setImage(Image.FILES_DUPLICATED);
        statisticControllerNumberDuplicated.setStaticText("Nombre de fichiers dupliqués");
        statisticControllerNumberDuplicated.dynamicTextProperty().bind(mainModel.getSearchStatisticsModel().nbDuplicateFilesProperty().asString());
        results.add(loadingMojoNumberDuplicated.getParent());
        
        // Space released
        LoadingMojo loadingMojoSpaceReleased = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticControllerSpaceReleased = (StatisticController) loadingMojoSpaceReleased.getController();
        statisticControllerSpaceReleased.setImage(Image.GARBAGE);
        statisticControllerSpaceReleased.setStaticText("Espace libéré");
        Bindings.bindBidirectional(statisticControllerSpaceReleased.dynamicTextProperty(), mainModel.getSearchStatisticsModel().spaceReleasedProperty(), new ByteStringConverter());
        results.add(loadingMojoSpaceReleased.getParent());        
        
        return results;
    }

    private static List<Node> createStaticMissingList() {
        List<Node> results = new ArrayList<>();
        // Number of file scanned
        LoadingMojo loadingMojo = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticController = (StatisticController) loadingMojo.getController();
        statisticController.setImage(Image.SCANNED_FILES);
        statisticController.setStaticText("Nombre de fichiers scannés");
        statisticController.dynamicTextProperty().bind(mainModel.getSearchStatisticsModel().nbScannedFilesProperty().asString());
        results.add(loadingMojo.getParent());
        
        // Amount of space to copy
        LoadingMojo loadingMojoWastedSpace = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticControllerWastedSpace = (StatisticController) loadingMojoWastedSpace.getController();
        statisticControllerWastedSpace.setImage(Image.WASTED_SPACE);
        statisticControllerWastedSpace.setStaticText("Volume à copier");
        Bindings.bindBidirectional(statisticControllerWastedSpace.dynamicTextProperty(), mainModel.getSearchStatisticsModel().spaceToDuplicateProperty(), new ByteStringConverter());
        results.add(loadingMojoWastedSpace.getParent());        
        
        // Number of file to duplicate
        LoadingMojo loadingMojoNumberToDuplicate = SpringFxmlLoader.load(Navigation.STATISTIC_COMPONENT);
        StatisticController statisticControllerNumberToDuplicate = (StatisticController) loadingMojoNumberToDuplicate.getController();
        statisticControllerNumberToDuplicate.setImage(Image.FILES_DUPLICATED);
        statisticControllerNumberToDuplicate.setStaticText("Nombre de fichiers à dupliquer");
        statisticControllerNumberToDuplicate.dynamicTextProperty().bind(mainModel.getSearchStatisticsModel().nbFileToDuplicateProperty().asString());
        results.add(loadingMojoNumberToDuplicate.getParent());
        
        return results;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        StatisticHelper.mainModel = mainModel;
    }

}
