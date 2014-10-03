/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.exception;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;

/**
 *
 * @author pierre
 */
public class CloneManagerIOException extends CloneManagerException{
    
    private final ApplicationFile applicationFile;
           
    public CloneManagerIOException(String message, ApplicationFile applicationFile){
        super(message);
        this.applicationFile = applicationFile;
    }

    public CloneManagerIOException(String message, String technicalMessageException, ApplicationFile applicationFile){
        super(message, technicalMessageException);
        this.applicationFile = applicationFile;
    }
    
    public ApplicationFile getApplicationFile() {
        return applicationFile;
    }
}
