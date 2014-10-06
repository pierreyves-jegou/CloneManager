/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.bean.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.Set;

/**
 *
 * @author pierre
 */
public class VisitDirectoriesResult {
    
    private Long byteToTreat;
    
    private Set<ApplicationFile> filesToTreat;

    public Long getByteToTreat() {
        return byteToTreat;
    }

    public void setByteToTreat(Long byteToTreat) {
        this.byteToTreat = byteToTreat;
    }

    public Set<ApplicationFile> getFilesToTreat() {
        return filesToTreat;
    }

    public void setFilesToTreat(Set<ApplicationFile> filesToTreat) {
        this.filesToTreat = filesToTreat;
    }
}
