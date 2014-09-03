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
 * @param <U>
 */
public interface FilterStrategy<T, U extends ApplicationFile> {
    
    public Analyse<T, U> filter(Map<T, Set<U>> unFilteredValues);

}
