/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.common.contract;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author pierre
 */
public interface Checkable {
    
    public boolean isSelected();

    public void setSelected(boolean value);

    public BooleanProperty selectedProperty();
}
