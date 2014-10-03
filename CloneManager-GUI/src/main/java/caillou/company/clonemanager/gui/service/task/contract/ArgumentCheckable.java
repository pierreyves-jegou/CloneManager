/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.task.contract;

import caillou.company.clonemanager.background.exception.CloneManagerArgumentException;

/**
 *
 * @author pierre
 */
public interface ArgumentCheckable {
    
    public void checkArguments() throws CloneManagerArgumentException;
    
}
