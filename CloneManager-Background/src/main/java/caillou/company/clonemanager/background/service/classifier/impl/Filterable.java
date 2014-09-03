/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.service.classifier.strategy.FilterStrategy;


/**
 *
 * @author pierre
 * @param <T>
 * @param <V>
 */
public interface Filterable<T, V extends ApplicationFile> {

    public void setFilterStrategy(FilterStrategy<T,V> filterStrategy);

    public FilterStrategy<T, V> getFilterStrategy();
}
