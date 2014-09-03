package caillou.company.clonemanager.background.service.classifier.impl;

import java.io.FileNotFoundException;

public interface Classifier<T> {      
        public void classify(T fileToSort) throws FileNotFoundException;
}
