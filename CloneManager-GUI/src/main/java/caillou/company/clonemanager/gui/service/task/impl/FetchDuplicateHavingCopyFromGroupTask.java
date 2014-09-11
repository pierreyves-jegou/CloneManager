/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.background.exception.ArgumentException;
import caillou.company.clonemanager.background.exception.OrganizerException;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.service.task.contract.ArgumentCheckable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class FetchDuplicateHavingCopyFromGroupTask extends AbstractFetchTask implements ArgumentCheckable{

    private Group.VALUE targetGroup;
        
    @Override
    protected Set<GUIApplicationFile> call() throws Exception {
        if(this.targetGroup == null){
            throw new OrganizerException("the fied 'targetGroup' has to be set");
        }
        Map<String, List<GUIApplicationFile>> listMyFilePerHash = hashUtil.sortPerMD5(guiApplicationFileListToDelete);
        return applicationFileUtil.detectDuplicateFilesHavingACopyInOtherGroup(listMyFilePerHash, this.targetGroup);
    }

    public void setTargetGroup(Group.VALUE targetGroup) {
        this.targetGroup = targetGroup;
    }

    @Override
    public void checkArguments() throws ArgumentException {
        if(guiApplicationFileListToDelete == null || targetGroup == null){
            throw new ArgumentException();
        }
    }
    
    
}
