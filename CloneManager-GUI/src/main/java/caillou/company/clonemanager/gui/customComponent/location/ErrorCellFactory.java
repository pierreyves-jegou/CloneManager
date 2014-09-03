/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.location;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author pierre
 */
public class ErrorCellFactory implements Callback<ListView<caillou.company.clonemanager.gui.bean.error.Error>, ListCell<caillou.company.clonemanager.gui.bean.error.Error>> {

    @Override
    public ListCell<caillou.company.clonemanager.gui.bean.error.Error> call(ListView<caillou.company.clonemanager.gui.bean.error.Error> error) {

        return new ListCell<caillou.company.clonemanager.gui.bean.error.Error>() {

            @Override
            protected void updateItem(caillou.company.clonemanager.gui.bean.error.Error error, boolean bln) {
                if (error != null) {
                    super.updateItem(error, false);
                    getStyleClass().remove("error");
                    getStyleClass().remove("warning");
                    setText(error.getMessage());
                    if (error.getSeverityLevel().equals(caillou.company.clonemanager.gui.bean.error.Error.SEVERITY_LEVEL.ERROR)) {
                        getStyleClass().add("error");
                    } else if (error.getSeverityLevel().equals(caillou.company.clonemanager.gui.bean.error.Error.SEVERITY_LEVEL.WARNING)) {
                        getStyleClass().add("warning");
                    }
                }else{
                   super.updateItem(error, true);
                    setText(null);
                }
            }
        };
    }
}
