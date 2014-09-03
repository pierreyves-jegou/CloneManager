/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.results;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.dialog.Dialog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class ConfirmSuppressionAllDoublonsController implements Initializable {

    private Dialog dialog;
    private boolean suppressionAction = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void confirmSuppressionAction(ActionEvent event) throws Exception {
        suppressionAction = true;
        dialog.hide();
    }
    
    @FXML
    private void cancelSuppressionAction(ActionEvent event) throws Exception {
        suppressionAction = false;
        dialog.hide();
    }
    
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
    
    public boolean isSuppressionAction() {
        return suppressionAction;
    }
    
}
