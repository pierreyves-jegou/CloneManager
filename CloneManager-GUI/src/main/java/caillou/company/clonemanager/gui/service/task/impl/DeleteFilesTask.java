/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class DeleteFilesTask extends Task<List<GUIApplicationFile>> {

    private List<GUIApplicationFile> filesToDelete;
    private List<GUIApplicationFile> filesDeleted = new ArrayList<>();
    private List<GUIApplicationFile> filesNotDeleted = new ArrayList<>();

    public List<GUIApplicationFile> getFilesToDelete() {
        return filesToDelete;
    }

    public void setFilesToDelete(List<GUIApplicationFile> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

    public List<GUIApplicationFile> getFilesDeleted() {
        return filesDeleted;
    }

    public void setFilesDeleted(List<GUIApplicationFile> filesDeleted) {
        this.filesDeleted = filesDeleted;
    }

    public List<GUIApplicationFile> getFilesNotDeleted() {
        return filesNotDeleted;
    }

    public void setFilesNotDeleted(List<GUIApplicationFile> filesNotDeleted) {
        this.filesNotDeleted = filesNotDeleted;
    }

    @Override
    protected List<GUIApplicationFile> call() throws Exception {
        if (filesToDelete != null) {
            for (GUIApplicationFile myFileFX : filesToDelete) {
                File currentFile = new File(myFileFX.getAbsolutePath());
                if (currentFile.exists() && currentFile.isFile()) {
                    if (currentFile.delete()) {
                        filesDeleted.add(myFileFX);
                    } else {
                        filesNotDeleted.add(myFileFX);
                    }
                } else {
                    filesNotDeleted.add(myFileFX);
                }
            }
        }
        return filesDeleted;
    }

}
