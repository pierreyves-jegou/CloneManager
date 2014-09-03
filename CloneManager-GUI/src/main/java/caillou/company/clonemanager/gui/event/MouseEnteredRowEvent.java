/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.event;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import javafx.scene.control.TableRow;

/**
 *
 * @author pierre
 */
public class MouseEnteredRowEvent {
    
    private TableRow<GUIApplicationFile> row;
    
    public MouseEnteredRowEvent(TableRow<GUIApplicationFile> row){
        this.row = row;
    }

    public TableRow<GUIApplicationFile> getRow() {
        return row;
    }


}
