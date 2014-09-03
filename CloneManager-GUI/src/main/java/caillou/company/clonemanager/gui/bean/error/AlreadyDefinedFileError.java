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
public class AlreadyDefinedFileError extends Error {
    
    
    
    public AlreadyDefinedFileError(){
        super("Cet emplacement à déjà été spécifié", SEVERITY_LEVEL.ERROR);
    }
            
    public AlreadyDefinedFileError(String message, SEVERITY_LEVEL severityLevel){
        super(message, severityLevel);
    }

    @Override
    public TYPE getType() {
        return Error.TYPE.ALREADYDEFINEDFILEERROR;
    }
    
}
