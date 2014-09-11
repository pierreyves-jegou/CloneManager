/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.menubar;

import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author pierre
 */
@Component
public class MenuBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    @FXML
    private void changeToEnglish(ActionEvent event) throws Exception {
        SpringFxmlLoader.changeLocale(Locale.ENGLISH);
        MainApp.getInstance().replaceSceneContent(SpringFxmlLoader.getLastLoadedURL(), null, null);
    }
   
    @FXML
    private void changeToFrench(ActionEvent event) throws Exception {
        SpringFxmlLoader.changeLocale(Locale.FRENCH);
        MainApp.getInstance().replaceSceneContent(SpringFxmlLoader.getLastLoadedURL(), null, null);
    }
    
}
