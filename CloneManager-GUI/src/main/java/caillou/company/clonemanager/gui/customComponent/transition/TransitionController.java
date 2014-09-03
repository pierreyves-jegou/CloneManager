/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.transition;

import caillou.company.clonemanager.background.bean.contract.EventBusProvider;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.customComponent.common.contract.DialogWrapper;
import caillou.company.clonemanager.gui.event.CancelTaskEvent;
import com.google.common.eventbus.EventBus;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import org.controlsfx.dialog.Dialog;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author pierre
 */
@Component
public class TransitionController implements Initializable, EventBusProvider, DialogWrapper {

    @FXML
    private ProgressIndicator progressBarPartialHashId;

    @FXML
    private ProgressIndicator progressBarFullHashId;
    
    @FXML
    private ProgressIndicator progressScanningFiles;
    
    private Dialog wrappingDialog;
    
    protected EventBus cancelBus = new EventBus("CANCEL_BUS");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressBarFullHashId.progressProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                if(newValue.equals((double) 1)){
                    try {
                        wrappingDialog.hide();
                        MainApp app = MainApp.getInstance();
                        app.replaceSceneContent(Navigation.RESULTS_VIEW, 800.0, 600.0);
                    } catch (Exception ex) {
                        Logger.getLogger(TransitionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
    }    
      
    @Override
    public void setWrappingDialog(Dialog wrappingDialog) {
        this.wrappingDialog = wrappingDialog;
    }
    
    @Override
    public Dialog getWrappingDialog(){
        return this.wrappingDialog;
    }
    
    public ProgressIndicator getProgressBarFullHashId() {
        return progressBarFullHashId;
    }

    public ProgressIndicator getProgressBarPartialHashId() {
        return progressBarPartialHashId;
    }

    public ProgressIndicator getProgressScanningFiles() {
        return progressScanningFiles;
    }    
    
    @FXML
    private void stopAction(ActionEvent event) throws Exception {
        cancelBus.post(new CancelTaskEvent());
        this.wrappingDialog.hide();
    }
    
    @Override
    public EventBus getEventBus() {
        return cancelBus;
    }
}
