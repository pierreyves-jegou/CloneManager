/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.exception.CloneManagerIOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class CloneManagerIOExceptionBean {
    
    private final List<CloneManagerIOException> readingErrors = new ArrayList<>();
    
    public List<CloneManagerIOException> getReadingErrors() {
        return readingErrors;
    }
    
    public void clear(){
        this.readingErrors.clear();
    }
    
}
