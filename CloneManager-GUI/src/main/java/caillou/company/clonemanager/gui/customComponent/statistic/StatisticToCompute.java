/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.statistic;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.gui.service.contract.MyFileProcessor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class StatisticToCompute implements MyFileProcessor{
    
    private Set<ApplicationFile> filesToTreat = new HashSet<>();
            
    @Override
    public void setMyFilesToTreat(Set<ApplicationFile> myFilesToTreat) {
         this.filesToTreat = myFilesToTreat;
    }
    
    public Set<ApplicationFile> getMyFilesToTreat(){
        return filesToTreat;
    }
}
