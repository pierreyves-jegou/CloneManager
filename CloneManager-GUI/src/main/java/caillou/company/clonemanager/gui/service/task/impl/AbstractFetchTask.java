/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.util.contract.ApplicationFileUtil;
import caillou.company.clonemanager.background.util.contract.HashUtil;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import java.util.List;
import java.util.Set;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pierre
 */
public abstract class AbstractFetchTask extends Task<Set<GUIApplicationFile>> {
    
    protected List<GUIApplicationFile> guiApplicationFileListToDelete;
    
    protected HashUtil hashUtil;
    
    protected ApplicationFileUtil applicationFileUtil;
    
    public void setGuiApplicationFileListToDelete(List<GUIApplicationFile> guiApplicationFileListToDelete) {
        this.guiApplicationFileListToDelete = guiApplicationFileListToDelete;
    }    
    
    @Autowired
    public void setHashUtil(HashUtil hashUtil) {
        this.hashUtil = hashUtil;
    }

    @Autowired
    public void setApplicationFileUtil(ApplicationFileUtil applicationFileUtil) {
        this.applicationFileUtil = applicationFileUtil;
    }
}
