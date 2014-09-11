/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.service.classifier.strategy;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pierre
 * @param <T>
 */
public class MissingPartialHashStrategy<T extends ApplicationFile> implements FilterStrategy<String, T> {

    @Override
    public Analyse<String, T> filter(Map<String, Set<T>> unFilteredValues) {
        Analyse<String, T> analyse = new Analyse<>();

        if (unFilteredValues == null) {
            return null;
        }
        for (Map.Entry<String, Set<T>> entry : unFilteredValues.entrySet()) {
            if (entry.getValue() != null){
                if(entry.getValue().size() == 1){
                    analyse.addEntryThatDoMatch(entry);
                }
                // Check that all the files belong to the same group
                if(entry.getValue().size() > 1){
                    Set<Group.VALUE> groups = new HashSet<>();
                    for(T applicationFile : entry.getValue()){
                        groups.add(applicationFile.getGroup());
                    }
                    if(groups.size() == 1){
                        analyse.addEntryThatDoMatch(entry);
                    }else{
                        analyse.addEntryThatMigthMatch(entry);
                    }
                }
            }
        }
        return analyse;
    }
}
