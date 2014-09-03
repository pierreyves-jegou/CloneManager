package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileFullyHashedEvent;
import caillou.company.clonemanager.background.service.impl.HashHandler;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class HashClassifier<T extends ApplicationFile> extends AbstractClassifier<String, T> implements Classifier<T> {

    @Override
    public void classify(T fileToSort) throws FileNotFoundException{
        String MD5Print;

        if (fileToSort.getMD5Print() != null) {
            MD5Print = fileToSort.getMD5Print();
        } else {
            MD5Print = HashHandler.getHash(fileToSort, null);
            fileToSort.setMD5Print(MD5Print);
        }

        if (!this.getResults().containsKey(MD5Print)) {
            this.getResults().put(MD5Print, new HashSet<T>());
        }

        this.getResults().get(MD5Print).add(fileToSort);
    }

    @Override
    public FileFullyHashedEvent getFileTreatedEvent() {
        return new FileFullyHashedEvent();
    }
}
