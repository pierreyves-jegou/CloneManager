/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.contract;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.Set;

/**
 *
 * @author pierre
 */
public interface MyFileProcessor {
    
    public void setMyFilesToTreat(Set<ApplicationFile> myFilesToTreat);
    
}
