/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.exception;

/**
 *
 * @author pierre
 */
public class ArgumentException extends OrganizerException {
    
    public ArgumentException(){
        super("Arguments are not set properly");
    }
    
}
