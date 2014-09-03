/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.taskchoice;


import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class TaskChoiceController extends HBox implements Initializable{
    
    private MainModel mainModel;
    
    public TaskChoiceController() {
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainApp app = MainApp.getInstance();
        app.getStage().sizeToScene();
        app.getStage().hide();
        app.getStage().show();
    }
    
    @FXML
    protected void handleMissings(ActionEvent event) throws Exception {
        try{
        MainApp app = MainApp.getInstance();
        TaskModel taskModel = mainModel.getTaskModel();
        taskModel.setCurrentTask(TaskModel.TASK.DETECT_MISSING);
        app.replaceSceneContent(Navigation.SEARCH_VIEW, MainApp.LARGE_WINDOWS_WIDTH, MainApp.LARGE_WINDOWS_HEIGTH);
                }catch(Exception e){
            System.out.println("exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
        @FXML
    protected void handleDoublons(ActionEvent event) throws Exception {
        try{
        MainApp app = MainApp.getInstance();
        TaskModel taskModel = mainModel.getTaskModel();
        taskModel.setCurrentTask(TaskModel.TASK.DETECT_DOUBLONS);
        app.replaceSceneContent(Navigation.SEARCH_VIEW, MainApp.LARGE_WINDOWS_WIDTH, MainApp.LARGE_WINDOWS_HEIGTH);
        }catch(Exception e){
            System.out.println("exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
