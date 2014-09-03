/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.common;

/**
 *
 * @author pierre
 * @param <T> Model to associate
 */
public abstract class Controller<T extends Model> {
    
    T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
