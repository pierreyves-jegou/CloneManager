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
public class NotADirectoryError extends Error{
    
    public NotADirectoryError(){
        this.setMessage(this.getI18NMessage("error.notADirectory"));
        this.setSeverity(SEVERITY_LEVEL.ERROR);
    }
   
    @Override
    public TYPE getType() {
        return Error.TYPE.NOTADIRECTORYERROR;
    }
    
}
