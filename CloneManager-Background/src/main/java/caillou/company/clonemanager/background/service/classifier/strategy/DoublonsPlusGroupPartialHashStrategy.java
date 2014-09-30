/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.service.classifier.strategy;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pierre
 * @param <T>
 */
public class DoublonsPlusGroupPartialHashStrategy<T extends ApplicationFile> implements FilterStrategy<String, T> {

    @Override
    public Analyse<String, T> filter(Map<String, Set<T>> unFilteredValues) {
        Analyse<String, T> analyse = new Analyse<>();

        if (unFilteredValues == null) {
            return null;
        }
        for (Map.Entry<String, Set<T>> entry : unFilteredValues.entrySet()) {
            Set<Object> groups = new HashSet<>();
            for(ApplicationFile myFile : entry.getValue()){
                if(myFile.getGroup() != null){
                    groups.add(myFile.getGroup());
                }
            }
                        
            if (entry.getValue() != null && entry.getValue().size() > 1 && groups.size() == 2) {
                analyse.addEntryThatMigthMatch(entry);
            } else {
                analyse.addEntryThatDoNotMatch(entry);
            }
        }
        return analyse;
    }
}
