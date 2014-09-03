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
public class UnexistingFileError extends Error {
    
    public UnexistingFileError(String message, SEVERITY_LEVEL severityLevel){
        super(message, severityLevel);
    }
    
    public UnexistingFileError(){
        super("Le répertoire n'existe pas", SEVERITY_LEVEL.ERROR);
    }
    

    @Override
    public TYPE getType() {
        return Error.TYPE.UNEXISTINGFILE;
    }
    
}
