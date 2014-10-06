/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.transition;

import caillou.company.clonemanager.background.bean.contract.EventBusProvider;
import caillou.company.clonemanager.gui.customComponent.common.contract.DialogWrapper;
import caillou.company.clonemanager.gui.service.task.impl.WorkerMonitor;
import com.google.common.eventbus.EventBus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import org.controlsfx.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class TransitionController implements Initializable, EventBusProvider, DialogWrapper {

    @FXML
    private ProgressIndicator progressBarPartialHashId;

    @FXML
    private ProgressIndicator progressBarFullHashId;
    
    @FXML
    private ProgressIndicator progressScanningFiles;
    
    private Dialog wrappingDialog;
    
    protected EventBus cancelBus = new EventBus("CANCEL_BUS");
    
    private WorkerMonitor workerMonitor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        progressBarFullHashId.progressProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                if(newValue.equals((double) 1)){
                    wrappingDialog.hide();
                }
            }
            
        });
    }    
      
    @Override
    public void setWrappingDialog(Dialog wrappingDialog) {
        this.wrappingDialog = wrappingDialog;
        wrappingDialog.setClosable(false);
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
        this.workerMonitor.stop(WorkerMonitor.HASH_WORKER);
        this.wrappingDialog.hide();
    }
    
    @Override
    public EventBus getEventBus() {
        return cancelBus;
    }

    @Autowired
    public void setWorkerMonitor(WorkerMonitor workerMonitor) {
        this.workerMonitor = workerMonitor;
    }
    
    
}
