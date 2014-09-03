/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.customComponent.results.ResultController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author pierre
 */
public class CopyToTask extends Task<List<GUIApplicationFile>> {

    private final ListProperty<GUIApplicationFile> filesToCopy = new SimpleListProperty<>();
    private final List<GUIApplicationFile> copiedFiles = new ArrayList<>();
    private final List<GUIApplicationFile> notCopiedFiles = new ArrayList<>();
    private File targetDirectory = null;

    @Override
    protected List<GUIApplicationFile> call() throws Exception {
        if (targetDirectory != null && targetDirectory.exists() && targetDirectory.isDirectory()) {
            for (GUIApplicationFile myFileFX : filesToCopy) {
                File fileToCopy = new File(myFileFX.getAbsolutePath());
                File targetFile = new File(targetDirectory, fileToCopy.getName());
                if (fileToCopy.exists()) {
                    try {
                        while(targetFile.exists()){
                            targetFile = new File(targetFile.getParent(), targetFile.getName() + "_copy");
                        }
                        Files.copy(fileToCopy.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
                        notCopiedFiles.add(myFileFX);
                    }
                    copiedFiles.add(myFileFX);
                }
            }
        }
        return copiedFiles;
    }


   public List<GUIApplicationFile> getCopiedFiles() {
        return copiedFiles;
    }

    public List<GUIApplicationFile> getNotCopiedFiles() {
        return notCopiedFiles;
    }
    
    public ObservableList<GUIApplicationFile> getFilesToCopy() {
        return filesToCopy.get();
    }

    public void setFilesToCopy(ObservableList<GUIApplicationFile> value) {
        filesToCopy.set(value);
    }

    public ListProperty<GUIApplicationFile> filesToCopyProperty() {
        return filesToCopy;
    }

    public File getTargetDirectory() {
        return targetDirectory;
    }

    public void setTargetDirectory(File targetDirectory) {
        this.targetDirectory = targetDirectory;
    }
}
