/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.bean.applicationFileFX.contract;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;

/**
 *
 * @author pierre
 */
public interface GUIApplicationFile extends ApplicationFile {
    
    public enum POSITION {ALONE, FIRST, MIDDLE, LAST};
    
    public Boolean isAlone();
    
    public void setAlone(Boolean isAlone);
    
    public String getCssColor();
    
    public void setCssColor(String cssColor);
    
    public POSITION getCurrentPostion();

    public void setCurrentPostion(POSITION currentPostion);
    
}
