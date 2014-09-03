/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.contract;

import caillou.company.clonemanager.gui.customComponent.location.LocationModel;
import java.util.List;

/**
 *
 * @author pierre
 */
public interface LocationsValidationService {
    
    /**
     * Check the consistency between the locations filled in
     * Firstly add an {@code UnexistingFileError} to the {@code locationModel} whenever the location doesn't suit an existing directory
     * Secondly add an {@code AlreadyDefinedFileError} when one location has already been defined before
     * Thirdly add an {@code IncludedFileError} when one location is included into an other one
     * Note that empty location are just skipped
     * @param locationModels
     */
    public void checkErrors(List<LocationModel> locationModels);
    
    /**
     * Return true when no serious error exists in the list {@code locationModels} and : 
     * <li>
     * <ul>- if the user uses groups {@code enableGrouping}, both GROUP1 and GROUP2 are present</ul>
     * <ul>- if {@code detectsIdentiqueFilesWithinALocation} is true at least one location is present and is valid, two otherwise</ul>
     * </li>
     * @param locationModels the list of {@code LocationModel}
     * @param enableGrouping true if the user uses some groups, false otherwise
     * @param detectsIdentiqueFilesWithinALocation true if the user want to track identic files within a location, false otherwise
     * @return 
     */
    public boolean validLocations(List<LocationModel> locationModels, boolean enableGrouping, boolean detectsIdentiqueFilesWithinALocation);
}
