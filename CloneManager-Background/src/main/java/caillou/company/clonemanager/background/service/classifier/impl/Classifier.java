package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.exception.CloneManagerException;

public interface Classifier<T> {      
        public void classify(T fileToSort) throws CloneManagerException;
}
