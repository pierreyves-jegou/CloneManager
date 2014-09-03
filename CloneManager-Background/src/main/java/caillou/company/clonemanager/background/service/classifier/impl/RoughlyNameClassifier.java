package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.event.FileTreatedEvent;


public class RoughlyNameClassifier extends AbstractClassifier<String, ApplicationFile> implements Classifier<ApplicationFile> {

//    @Override
//    public void classifyAll(Set<MyFile> myFilesNotSorted, boolean useEventBus) {
//
//        for (MyFile myFile : myFilesNotSorted) {
//            String roughlyName = null;
//
//            if (myFile.getRoughlyName() != null) {
//                roughlyName = myFile.getRoughlyName();
//            } else {
//                roughlyName = NameHandler.getRoughlyName(myFile.getName());
//                myFile.setRoughlyName(roughlyName);
//            }
//
//            if (!this.getResults().containsKey(roughlyName)) {
//                this.getResults().put(roughlyName, new HashSet<MyFile>());
//            }
//
//            this.getResults().get(roughlyName).add(myFile);
//
//            // Post sur l'évent bus
////			if(useEventBus){
////				PotentialMatchEvent<String> potentialMatchEvent = new PotentialMatchEvent<>();
////				potentialMatchEvent.setKey(roughlyName);
////				potentialMatchEvent.setValues(resultMap.get(roughlyName));
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
