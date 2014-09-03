/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.critere;

import caillou.company.clonemanager.gui.bean.impl.IntegerField;
import caillou.company.clonemanager.gui.customComponent.common.Controller;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class CritereController extends Controller<MainModel> implements Initializable {

    @FXML
    private CheckBox formatAllId;

    @FXML
    private CheckBox formatVideoId;

    @FXML
    private CheckBox formatAudioId;

    @FXML
    private CheckBox formatImageId;

    @FXML
    private IntegerField sizeMinId;

    @FXML
    private IntegerField sizeMaxId;

    @FXML
    private ComboBox<String> minimumSizeFormatId;

    @FXML
    private ComboBox<String> maximumSizeFormatId;
    
    private MainModel mainModel;
    
    public CritereController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainModel.getCritereModel().getFormat().allProperty().bind(formatAllId.selectedProperty());
        mainModel.getCritereModel().getFormat().videoProperty().bind(Bindings.and(formatVideoId.selectedProperty(), Bindings.not(formatVideoId.disableProperty())));
        mainModel.getCritereModel().getFormat().audioProperty().bind(Bindings.and(formatAudioId.selectedProperty(), Bindings.not(formatAudioId.disableProperty())));
        mainModel.getCritereModel().getFormat().imageProperty().bind(Bindings.and(formatImageId.selectedProperty(), Bindings.not(formatImageId.disableProperty())));
        
        LongBinding minimumSizeComputationBinding = new LongBinding() {
            {
                super.bind(minimumSizeFormatId.valueProperty(), sizeMinId.valueProperty());
            }

            @Override
            protected long computeValue() {
                if (minimumSizeFormatId.getValue().equals("octet")) {
                    return (long) sizeMinId.valueProperty().get() * 1;
                }
                if (minimumSizeFormatId.getValue().equals("kilo octet")) {
                    return (long) sizeMinId.valueProperty().get() * 1000;
                }
                if (minimumSizeFormatId.getValue().equals("mega octet")) {
                    return (long) sizeMinId.valueProperty().get() * 1000000;
                }
                if (minimumSizeFormatId.getValue().equals("giga octet")) {
                    return (long) sizeMinId.valueProperty().get() * 1000000000;
                }
                throw new IllegalArgumentException("Unkowned size format");
            }
        };

        mainModel.getCritereModel().getSize().minimalSizeProperty().bind(minimumSizeComputationBinding);
                
        LongBinding maximumSizeComputationBinding = new LongBinding() {
            {
                super.bind(maximumSizeFormatId.valueProperty(), sizeMaxId.valueProperty());
            }

            @Override
            protected long computeValue() {
                if (maximumSizeFormatId.getValue().equals("octet")) {
                    return (long) sizeMaxId.valueProperty().get() * 1;
                }
                if (maximumSizeFormatId.getValue().equals("kilo octet")) {
                    return (long) sizeMaxId.valueProperty().get() * 1000;
                }
                if (maximumSizeFormatId.getValue().equals("mega octet")) {
                    return (long) sizeMaxId.valueProperty().get() * 1000000;
                }
                if (maximumSizeFormatId.getValue().equals("giga octet")) {
                    return (long) sizeMaxId.valueProperty().get() * 1000000000;
                }
                throw new IllegalArgumentException("Unkowned size format");
            }
        };

        mainModel.getCritereModel().getSize().minimalSizeProperty().bind(minimumSizeComputationBinding);
        mainModel.getCritereModel().getSize().maximalSizeProperty().bind(maximumSizeComputationBinding);   

        formatAllId.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                disableEnableCheckboxes(newValue);
                if (newValue) {
                    formatVideoId.setSelected(false);
                    formatAudioId.setSelected(false);
                    formatImageId.setSelected(false);
                }
            }
        });
    }

    private void disableEnableCheckboxes(Boolean value) {
        formatVideoId.setDisable(value);
        formatAudioId.setDisable(value);
        formatImageId.setDisable(value);
    }
    
    @Override
    @Autowired
    public void setModel(MainModel mainModel){
        this.mainModel = mainModel;
    }
}
