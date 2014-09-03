/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.service.classifier.strategy;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.impl.Analyse;
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
            if (entry.getValue() != null && entry.getValue().size() == 1) {
                analyse.addEntryThatDoMatch(entry);
            } else {
                analyse.addEntryThatMigthMatch(entry);
            }
        }
        return analyse;
    }
}
