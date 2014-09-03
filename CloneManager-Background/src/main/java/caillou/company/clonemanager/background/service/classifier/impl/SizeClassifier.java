package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileTreatedEvent;

public class SizeClassifier extends AbstractClassifier<Long, ApplicationFile> implements Classifier<ApplicationFile> {

//    @Override
//    public void classifyAll(Set<MyFile> myFilesNotSorted, boolean useEventBus) {
//
//        for (MyFile myFile : myFilesNotSorted) {
//            Long fileLength = null;
//
//            // Apparemment la méthode .length peut être longue
//            if (myFile.getSize() != null) {
//                fileLength = myFile.getSize();
//            } else {
//                fileLength = myFile.length();
//                myFile.setSize(fileLength);
//            }
//
//            if (!this.getResults().containsKey(fileLength)) {
//                this.getResults().put(new Long(fileLength), new HashSet<MyFile>());
//            }
//
//            this.getResults().get(fileLength).add(myFile);
//
//            // Post sur l'évent bus
////			if(useEventBus){
////				PotentialMatchEvent<Long> potentialMatchEvent = new PotentialMatchEvent<>();
////				potentialMatchEvent.setKey(new Long(fileLength));
////				potentialMatchEvent.setValues(resultMap.get(fileLength));
////				eventBus.post(potentialMatchEvent);	
////			}
//        }
//    }

    @Override
    public void classify(ApplicationFile fileToSort) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileTreatedEvent getFileTreatedEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
