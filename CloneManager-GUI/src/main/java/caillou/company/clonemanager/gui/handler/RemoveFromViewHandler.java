/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.handler;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.customComponent.results.GUIApplicationFileUtil;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticToCompute;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
import caillou.company.clonemanager.gui.service.task.impl.ComputeStatisticTask;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class RemoveFromViewHandler implements EventHandler<WorkerStateEvent>, ArgumentCheckable {

    private TableView<GUIApplicationFile> tableView;

    private List<GUIApplicationFile> guiApplicationFileList;

    private Boolean suppressOnlyOnSelected = null;
    
    @Override
    public void handle(WorkerStateEvent event) {
        List<GUIApplicationFile> fileToReinject = new ArrayList<>();
        List<GUIApplicationFile> deletedFiles = (List<GUIApplicationFile>) event.getSource().getValue();
        for (GUIApplicationFile myFileFX : deletedFiles) {
            List<GUIApplicationFile> listFromTableView = suppressOnlyOnSelected ? tableView.getSelectionModel().getSelectedItems() : tableView.getItems();
            for (GUIApplicationFile selectedFile : listFromTableView) {
                if (selectedFile.getAbsolutePath().equals(myFileFX.getAbsolutePath())) {
                    guiApplicationFileList.remove(selectedFile);
                    if(fileToReinject.contains(selectedFile)){
                        fileToReinject.remove(selectedFile);
                    }
                    GUIApplicationFile myFileAlone = GUIApplicationFileUtil.isolateSingleFile(tableView.getItems(), selectedFile);
                    if (myFileAlone != null) {
                        fileToReinject.add(myFileAlone);
                    }
                    break;
                }
            }
        }
        
        for(GUIApplicationFile myFileFX : fileToReinject){
            guiApplicationFileList.remove(myFileFX);
            guiApplicationFileList.add(myFileFX);
        }
        
        StatisticToCompute statisticToCompute = SpringFxmlLoader.getBean(null, StatisticToCompute.class);
        ComputeStatisticTask computeStatisticTask = SpringFxmlLoader.getBean(ComputeStatisticTask.class);
        computeStatisticTask.setStatisticToCompute(statisticToCompute);
        computeStatisticTask.setResultingList(guiApplicationFileList);

        AfterStatisticsComputedHandler afterStatisticsComputedHandler = SpringFxmlLoader.getBean(AfterStatisticsComputedHandler.class);
        computeStatisticTask.setOnSucceeded(afterStatisticsComputedHandler);
        new Thread(computeStatisticTask).start();
    }

    public void setTableView(TableView<GUIApplicationFile> tableView) {
        this.tableView = tableView;
    }

    public void setGUIApplicationFileList(List<GUIApplicationFile> guiApplicationFileList) {
        this.guiApplicationFileList = guiApplicationFileList;
    }

    public void setSuppressOnlyOnSelected(boolean suppressOnlyOnSelected) {
        this.suppressOnlyOnSelected = suppressOnlyOnSelected;
    }

    @Override
    public void checkArguments() throws CloneManagerArgumentException {
        if(this.tableView == null || this.guiApplicationFileList == null ||suppressOnlyOnSelected == null){
            throw new CloneManagerArgumentException();
        }
    }
}
