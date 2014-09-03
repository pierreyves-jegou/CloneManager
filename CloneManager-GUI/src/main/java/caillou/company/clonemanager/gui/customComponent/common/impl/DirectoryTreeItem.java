/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.common.impl;

import caillou.company.clonemanager.gui.customComponent.common.contract.DirectoryLazyCheckableTreeItem;
import java.io.File;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author pierre
 */
public class DirectoryTreeItem extends File implements DirectoryLazyCheckableTreeItem{

    private boolean loaded = false;
    private final BooleanProperty selected = new SimpleBooleanProperty(true);

    public DirectoryTreeItem(String pathname) {
        super(pathname);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void setLoaded(boolean value) {
        this.loaded = value;
    }

    @Override
    public String getAbsolutePath() {
        return super.getAbsolutePath();
    }    
    
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
}
