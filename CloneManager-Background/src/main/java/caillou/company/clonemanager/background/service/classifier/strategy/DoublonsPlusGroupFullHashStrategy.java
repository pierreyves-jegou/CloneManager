/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.service.classifier.strategy;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pierre
 */
public class DoublonsPlusGroupFullHashStrategy<T extends ApplicationFile> implements FilterStrategy<String, T>{
   

    @Override
    public Analyse<String, T> filter(Map<String, Set<T>> unFilteredValues) {
        Analyse<String, T> analyse = new Analyse<>();

        if (unFilteredValues == null) {
            return null;
        }
        for (Map.Entry<String, Set<T>> entry : unFilteredValues.entrySet()) {
            boolean group1Found = false;
            boolean group2Found = false;
            for(ApplicationFile myFile : entry.getValue()){
                if(myFile.getGroup().equals(Group.GROUP1)){
                    group1Found = true;
                }else if(myFile.getGroup().equals(Group.GROUP2)){
                    group2Found = true;
                }
            }
            
            
            if (entry.getValue() != null && entry.getValue().size() > 1 && group1Found &&  group2Found) {
                analyse.addEntryThatDoMatch(entry);
            } else {
                analyse.addEntryThatDoNotMatch(entry);
            }
        }
        return analyse;
    }
}

