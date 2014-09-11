/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.bean.error;

/**
 *
 * @author pierre
 */
public class IncludedFileError extends Error {
    
    public IncludedFileError(){
        this.setMessage(this.getI18NMessage("error.locationIncluded"));
        this.setSeverity(SEVERITY_LEVEL.WARNING);
    }
    
    @Override
    public TYPE getType() {
        return Error.TYPE.INCLUDEDFILEERROR;
    }
    
}
