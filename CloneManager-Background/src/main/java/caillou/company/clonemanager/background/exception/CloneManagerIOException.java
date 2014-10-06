/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.exception;

import java.io.File;

/**
 *
 * @author pierre
 */
public class CloneManagerIOException extends CloneManagerException{
    
    private final String path;
           
    public CloneManagerIOException(String message, String path){
        super(message);
        this.path = path;
    }

    public CloneManagerIOException(String message, String technicalMessageException, String path){
        super(message, technicalMessageException);
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}
