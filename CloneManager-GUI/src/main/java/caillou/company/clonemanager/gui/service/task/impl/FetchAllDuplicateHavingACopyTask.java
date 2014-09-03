/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.exception.ArgumentException;
import caillou.company.clonemanager.background.util.contract.ApplicationFileUtil;
import caillou.company.clonemanager.background.util.contract.HashUtil;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class FetchAllDuplicateHavingACopyTask extends AbstractFetchTask implements ArgumentCheckable{
    
    @Override
    protected Set<GUIApplicationFile> call() throws Exception {
        Map<String, List<GUIApplicationFile>> listMyFilePerHash = hashUtil.sortPerMD5(guiApplicationFileListToDelete);
        return applicationFileUtil.detectAllDuplicateFilesHavingACopy(listMyFilePerHash);
    }

    @Override
    public void checkArguments() throws ArgumentException {
        if(guiApplicationFileListToDelete == null){
            throw new ArgumentException();
        }
    }    
}
