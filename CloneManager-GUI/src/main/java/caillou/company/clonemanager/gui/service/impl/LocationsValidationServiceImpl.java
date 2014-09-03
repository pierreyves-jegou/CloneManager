/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.service.impl;

import caillou.company.clonemanager.gui.bean.error.AlreadyDefinedFileError;
import caillou.company.clonemanager.gui.bean.error.IncludedFileError;
import caillou.company.clonemanager.gui.bean.error.NotADirectoryError;
import caillou.company.clonemanager.gui.bean.error.UnexistingFileError;
import caillou.company.clonemanager.gui.customComponent.location.LocationModel;
import caillou.company.clonemanager.gui.service.contract.LocationsValidationService;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author pierre
 */
@Service
public class LocationsValidationServiceImpl implements LocationsValidationService{
    
    private static Comparator<LocationModel> locationPathLengthComparator = new Comparator<LocationModel>() {

        @Override
        public int compare(LocationModel o1, LocationModel o2) {
            if (o1.getPath() == null) {
                return 1;
            }
            if (o2.getPath() == null) {
                return -1;
            }
            Integer length1 = o1.getPath().length();
            Integer length2 = o2.getPath().length();
            return -length1.compareTo(length2);
        }
    };
    
    private boolean containsError(LocationModel locationModel, caillou.company.clonemanager.gui.bean.error.Error.TYPE type) {
        for(caillou.company.clonemanager.gui.bean.error.Error error : locationModel.getErrors()){
            if(error.getType().equals(type)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void checkErrors(List<LocationModel> locationModels){
        List<LocationModel> copy = new ArrayList<>(locationModels);
        for(LocationModel locationModel : copy){
            locationModel.getExcludeModel().clearExclusionByApplication();
            if (locationModel.getPath() != null && !locationModel.getPath().isEmpty()) {
                File a = new File(locationModel.getPath());
                if (!a.exists() && !containsError(locationModel, caillou.company.clonemanager.gui.bean.error.Error.TYPE.UNEXISTINGFILE)) {
                    locationModel.addError(new UnexistingFileError());
                } else if (!a.isDirectory() && !containsError(locationModel, caillou.company.clonemanager.gui.bean.error.Error.TYPE.NOTADIRECTORYERROR)) {
                    locationModel.addError(new NotADirectoryError());
                }
            }
        }

        Collections.sort(copy, locationPathLengthComparator);
        for (int i = 0; i < copy.size() - 1; i++) {
            for (int j = i + 1; j < copy.size(); j++) {
                if (copy.get(i).getPath() != null && copy.get(j).getPath() != null && !copy.get(i).getPath().isEmpty() && !copy.get(j).getPath().isEmpty()) {
                    File a = new File(copy.get(i).getPath());
                    File b = new File(copy.get(j).getPath());

                    if (!a.exists() || !a.isDirectory() || !b.exists() || !b.isDirectory()) {
                        continue;
                    }

                    if (a.getAbsolutePath().equals(b.getAbsolutePath())) {
                        boolean errorAlreadySet = false;
                        if (containsError(copy.get(i), caillou.company.clonemanager.gui.bean.error.Error.TYPE.ALREADYDEFINEDFILEERROR)) {
                            errorAlreadySet = true;
                        }
                        if (containsError(copy.get(j), caillou.company.clonemanager.gui.bean.error.Error.TYPE.ALREADYDEFINEDFILEERROR)) {
                            errorAlreadySet = true;
                        }

                        if (!errorAlreadySet) {
                            copy.get(i).addError(new AlreadyDefinedFileError());
                        }
                    } else if (a.getAbsolutePath().startsWith(b.getAbsolutePath())) {
                        boolean errorAlreadySet = false;
                        copy.get(j).getExcludeModel().addExclusionByApplication(a.toPath());
                        if (containsError(copy.get(i), caillou.company.clonemanager.gui.bean.error.Error.TYPE.INCLUDEDFILEERROR)) {
                            errorAlreadySet = true;
                        }
                        if (!errorAlreadySet) {
                            copy.get(i).addError(new IncludedFileError());
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean validLocations(List<LocationModel> locationModels, boolean enableGrouping, boolean detectsIdentiqueFilesWithinALocation){
        boolean valid = true;
        int nbLocation = 0;
        Set<String> groupsPresent = new HashSet<>();

        for (LocationModel locationModel : locationModels) {

            if (locationModel.getPath() != null && !locationModel.getPath().isEmpty()) {

                // Check the groups
                if (enableGrouping) {
                    if (locationModel.getGroup() == null) {
                        valid = false;
                    } else {
                        groupsPresent.add(locationModel.getGroup());
                    }
                }

                // Check errors on the path
                for (caillou.company.clonemanager.gui.bean.error.Error error : locationModel.getErrors()) {
                    if (error.getSeverityLevel().equals(caillou.company.clonemanager.gui.bean.error.Error.SEVERITY_LEVEL.ERROR)) {
                        valid = false;
                    }
                }

                nbLocation++;
            }

        }
        if (enableGrouping) {
            if (groupsPresent.size() != 2) {
                valid = false;
            }
        }

        if (detectsIdentiqueFilesWithinALocation) {
            if (nbLocation < 1) {
                valid = false;
            }
        } else {
            if (nbLocation < 2) {
                valid = false;
            }
        }
        
        return valid;
    }
    
}
