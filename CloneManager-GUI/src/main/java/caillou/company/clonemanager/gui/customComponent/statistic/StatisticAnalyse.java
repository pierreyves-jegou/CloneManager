/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.statistic;

/**
 *
 * @author pierre
 */
public class StatisticAnalyse {
    
    private Long nbFileScanned = null;
    
    private Long wastedSpace = null;
    
    private Long numberOfDuplicateFiles = null;
    
    private Long nbFileToDuplicate = null;
    
    private Long spaceToDuplicate = null;

    public Long getNbFileScanned() {
        return nbFileScanned;
    }

    public void setNbFileScanned(Long nbFileScanned) {
        this.nbFileScanned = nbFileScanned;
    }

    public Long getWastedSpace() {
        return wastedSpace;
    }

    public void setWastedSpace(Long wastedSpace) {
        this.wastedSpace = wastedSpace;
    }

    public Long getNumberOfDuplicateFiles() {
        return numberOfDuplicateFiles;
    }

    public void setNumberOfDuplicateFiles(Long numberOfDuplicateFiles) {
        this.numberOfDuplicateFiles = numberOfDuplicateFiles;
    }

    public Long getNbFileToDuplicate() {
        return nbFileToDuplicate;
    }

    public void setNbFileToDuplicate(Long nbFileToDuplicate) {
        this.nbFileToDuplicate = nbFileToDuplicate;
    }

    public Long getSpaceToDuplicate() {
        return spaceToDuplicate;
    }

    public void setSpaceToDuplicate(Long spaceToDuplicate) {
        this.spaceToDuplicate = spaceToDuplicate;
    }
}
