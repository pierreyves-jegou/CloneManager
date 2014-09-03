/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.common.contract;

import org.controlsfx.dialog.Dialog;

/**
 *
 * @author pierre
 */
public interface DialogWrapper {
    
    public void setWrappingDialog(Dialog wrappingDialog);
    
    public Dialog getWrappingDialog();
    
}
