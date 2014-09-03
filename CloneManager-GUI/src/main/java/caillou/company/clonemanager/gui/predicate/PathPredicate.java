/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.predicate;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import java.util.function.Predicate;

/**
 *
 * @author pierre
 */
public class PathPredicate implements Predicate<GUIApplicationFile>{

    public enum TYPE { STARTWITH, CONTAINS, ENDWITH};
    
    private TYPE currentType = TYPE.STARTWITH;
    private String path;
        
    @Override
    public boolean test(GUIApplicationFile t) {
        if(currentType.equals(TYPE.STARTWITH)){
            return t.getAbsolutePath().startsWith(this.path);
        }else if(currentType.equals(TYPE.CONTAINS)){
             return t.getAbsolutePath().contains(this.path);
        }else if(currentType.equals(TYPE.ENDWITH)){
             return t.getAbsolutePath().endsWith(this.path);
        }else{
            return false;
        }
    }

    public void setCurrentType(TYPE currentType) {
        this.currentType = currentType;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
