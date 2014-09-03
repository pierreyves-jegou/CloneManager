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
        super("Le fichier sélectionné n'est pas un répertoire", SEVERITY_LEVEL.ERROR);
    }
   
    @Override
    public TYPE getType() {
        return Error.TYPE.NOTADIRECTORYERROR;
    }
    
}
