/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.exception;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pierre
 */
public class CloneManagerArgumentException extends CloneManagerException {
    
    private List<String> arguments = new ArrayList<>();
    
    public CloneManagerArgumentException(){
        super("Arguments are not set properly");
    }
    
    public void addArgument(String argument){
        this.arguments.add(argument);
    }
    
}
