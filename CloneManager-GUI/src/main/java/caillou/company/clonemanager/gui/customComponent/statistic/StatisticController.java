/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.statistic;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class StatisticController implements Initializable {

    @FXML
    private ImageView imageViewId;
    
    @FXML
    private Label dynamicTextId;
    
    @FXML
    private Text staticTextId;
    
    @FXML
    private Tooltip dynamicTooltipId;
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dynamicTooltipId.textProperty().bind(dynamicTextId.textProperty());
    }    
    
    public void setImage(String imgSrc){
        imageViewId.setImage(new Image(imgSrc));
    }
    
    public void setStaticText(String text){
        this.staticTextId.setText(text);
    }
    
    public StringProperty dynamicTextProperty(){
        return dynamicTextId.textProperty();
    }
    
}
