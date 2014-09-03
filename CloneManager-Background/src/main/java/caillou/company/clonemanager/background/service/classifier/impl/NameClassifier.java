package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileTreatedEvent;

public class NameClassifier extends AbstractClassifier<String, ApplicationFile> implements Classifier<ApplicationFile> {

//    @Override
//    public void classifyAll(Set<MyFile> myFilesNotSorted, boolean useEventBus) {
//
//        for (MyFile myFile : myFilesNotSorted) {
//            String name = myFile.getName();
//
//            if (!this.getResults().containsKey(name)) {
//                this.getResults().put(name, new HashSet<MyFile>());
//            }
//
//            this.getResults().get(name).add(myFile);
//
//            // Post sur l'évent bus
////			if(useEventBus){
////				PotentialMatchEvent<String> potentialMatchEvent = new PotentialMatchEvent<>();
////				potentialMatchEvent.setKey(name);
////				potentialMatchEvent.setValues(resultMap.get(name));
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
