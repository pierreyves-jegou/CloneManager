/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.critere;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;


/**
 *
 * @author pierre
 */
public class CritereView extends GridPane{
    
    public CritereView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "CritereView.fxml"));
        fxmlLoader.setRoot(this);
        CritereController critereController = new CritereController();
        fxmlLoader.setController(critereController);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    
    
    
}
