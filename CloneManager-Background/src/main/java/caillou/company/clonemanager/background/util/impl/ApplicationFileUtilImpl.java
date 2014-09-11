/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.util.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.background.util.contract.ApplicationFileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class ApplicationFileUtilImpl implements ApplicationFileUtil {
        
    @Override
    public <T extends ApplicationFile> Set<T> detectDuplicateFilesHavingACopyInOtherGroup(Map<String, List<T>> listHashProviderPerHash, Group.VALUE originalGroupValue){
        Set<T> results = new HashSet<>();      
        Group.VALUE groupToCheck = Group.getTheOtherGroup(originalGroupValue);
        
        for(Map.Entry<String, List<T>> entry : listHashProviderPerHash.entrySet()){
            List<T> tmp = new ArrayList<>();
            boolean copyFound = false;
            if(entry.getValue().size() > 1){
                for(T myFileFX : entry.getValue()){
                    if(myFileFX.getGroup().equals(originalGroupValue)){
                       tmp.add(myFileFX); 
                    }
                    if(myFileFX.getGroup().equals(groupToCheck)){
                        copyFound = true;
                    }
                }
                if(copyFound){
                    results.addAll(tmp);
                }
            }
        }        
        return results;
    }

    @Override
    public <T extends ApplicationFile> Set<T> detectAllDuplicateFilesHavingACopy(Map<String, List<T>> listHashProviderPerHash) {
        Set<T> results = new HashSet<>();     
        for(Map.Entry<String, List<T>> entry : listHashProviderPerHash.entrySet()){
            if(entry.getValue().size() > 1){
                T myFileToKeep = null;
                for(T myFileFX : entry.getValue()){
                    myFileToKeep = myFileFX;
                    if(myFileFX.getGroup().equals(Group.GROUP1)){
                        break;
                    }
                }
                for(T myFileFX : entry.getValue()){
                    if(!myFileFX.getAbsolutePath().equals(myFileToKeep.getAbsolutePath())){
                        results.add(myFileFX);
                    }
                }
            }
        }
        return results;
    }
    
}
