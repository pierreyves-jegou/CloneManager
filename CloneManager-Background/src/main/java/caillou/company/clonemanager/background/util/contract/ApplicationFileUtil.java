/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.util.contract;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.impl.Group;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pierre
 */
public interface ApplicationFileUtil {
    
    public <T extends ApplicationFile> Set<T> detectDuplicateFilesHavingACopyInOtherGroup(Map<String, List<T>> listHashProviderPerHash, Group.VALUE originalGroup);
    
    public <T extends ApplicationFile> Set<T> detectAllDuplicateFilesHavingACopy(Map<String, List<T>> listHashProviderPerHash);
}
