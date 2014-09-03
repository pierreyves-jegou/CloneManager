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
    
    public IncludedFileError(String message, SEVERITY_LEVEL severityLevel){
        super(message, severityLevel);
    }

    public IncludedFileError(){
        super("Cet emplacement est inclus dans un autre emplacement", SEVERITY_LEVEL.WARNING);
    }
    
    @Override
    public TYPE getType() {
        return Error.TYPE.INCLUDEDFILEERROR;
    }
    
}
