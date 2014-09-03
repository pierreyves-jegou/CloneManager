/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.common;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class StatisticsModel implements Model {

    private final LongProperty nbScannedFiles = new SimpleLongProperty();
    
    private final LongProperty nbDuplicateFiles = new SimpleLongProperty();
    private final LongProperty spaceWasted = new SimpleLongProperty();
    private final LongProperty initialSpaceWasted = new SimpleLongProperty(-1);
    private final LongProperty spaceReleased = new SimpleLongProperty(0);

    private final LongProperty nbFileToDuplicate = new SimpleLongProperty();
    private final LongProperty spaceToDuplicate = new SimpleLongProperty();
        
    public long getNbDuplicateFiles() {
        return nbDuplicateFiles.get();
    }

    public void setNbDuplicateFiles(long value) {
        nbDuplicateFiles.set(value);
    }

    public void setNbFileToDuplicate(long value){
        nbFileToDuplicate.set(value);
    }
    
    public void setSpaceToDuplicate(long value){
        this.spaceToDuplicate.set(value);
    }
    
    public LongProperty nbFileToDuplicateProperty(){
        return this.nbFileToDuplicate;
    }
    
    public LongProperty spaceToDuplicateProperty(){
        return this.spaceToDuplicate;
    }
    
    public LongProperty nbDuplicateFilesProperty() {
        return nbDuplicateFiles;
    }

    public long getNbScannedFiles() {
        return nbScannedFiles.get();
    }

    public void setNbScannedFiles(long value) {
        nbScannedFiles.set(value);
    }

    public LongProperty nbScannedFilesProperty() {
        return nbScannedFiles;
    }

    public long getSpaceWasted() {
        return spaceWasted.get();
    }

    public void setSpaceWasted(long value) {
        spaceWasted.set(value);
    }

    public LongProperty spaceWastedProperty() {
        return spaceWasted;
    }

    public LongProperty getNbFileToDuplicate() {
        return nbFileToDuplicate;
    }

    public LongProperty getSpaceToDuplicate() {
        return spaceToDuplicate;
    }
    
    public void setInitialSpaceWasted(long value) {
        initialSpaceWasted.set(value);
        if(!spaceReleased.isBound()){
            spaceReleased.bind(initialSpaceWasted.subtract(spaceWasted));
        }
    }
    
    public Long getInitialSpaceWasted(){
        return initialSpaceWasted.get();
    }
        
    public LongProperty spaceReleasedProperty(){
        return spaceReleased;
    }
}
