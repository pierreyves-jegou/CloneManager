/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.bean.impl;

import javafx.scene.Parent;

/**
 *
 * @author pierre
 */
public class LoadingMojo {
    
    private Parent parent;
    
    private Object controller;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
    
}
