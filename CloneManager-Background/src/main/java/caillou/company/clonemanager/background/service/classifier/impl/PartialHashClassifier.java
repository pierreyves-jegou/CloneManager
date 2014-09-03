package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FilePartiallyHashedEvent;
import caillou.company.clonemanager.background.service.impl.HashHandler;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartialHashClassifier extends AbstractClassifier<String, ApplicationFile> implements Classifier<ApplicationFile> {

    public int byteToRead = 8000;

    public int getByteToRead() {
        return byteToRead;
    }

    public void setByteToRead(int byteToRead) {
        this.byteToRead = byteToRead;
    }

    @Override
    public void classify(ApplicationFile fileToSort) throws FileNotFoundException{
        String MD5Print;

        if (fileToSort.getPartialMD5Print() != null) {
            MD5Print = fileToSort.getPartialMD5Print();
        } else {
            MD5Print = HashHandler.getHash(fileToSort, byteToRead);
            fileToSort.setPartialMD5Print(MD5Print);
        }
        
        if (!this.getResults().containsKey(MD5Print)) {
            this.getResults().put(MD5Print, new HashSet<ApplicationFile>());
        }

        ((Map<String, Set<ApplicationFile>>)this.getResults()).get(MD5Print).add(fileToSort);
    }    

    @Override
    public FilePartiallyHashedEvent getFileTreatedEvent() {
       return new FilePartiallyHashedEvent();
    }
}
