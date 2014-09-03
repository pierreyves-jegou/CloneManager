/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.results;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author pierre
 */
public class PathCellFactory implements Callback<TableColumn<MyFileFX, String>, TableCell<MyFileFX, String>> {

    @Override
    public TableCell<MyFileFX, String> call(TableColumn<MyFileFX, String> param) {
        TableCell<MyFileFX, String> tableCell = new TableCell<MyFileFX, String>(){
        @Override
        protected void updateItem(final String path, boolean empty){
            
        }
        };
       return tableCell;
    }
    
    
    
}
