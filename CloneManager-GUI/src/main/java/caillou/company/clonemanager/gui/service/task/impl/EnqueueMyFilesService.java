/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.gui.service.task.contract.EnqueueService;
import java.util.HashSet;
import java.util.Set;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Qualifier(value = "EnqueueMyFileProcessor")
public class EnqueueMyFilesService extends Service<Long> implements EnqueueService<Long> {

    private Set<ApplicationFile> filesToTreat = new HashSet<>();
    
    private BeanFactory beanFactory;
        
    @Override
    protected Task<Long> createTask() {
        return beanFactory.getBean(VisitLocationTask.class);
    }

    public Set<ApplicationFile> getMyFilesToTreat() {
        return filesToTreat;
    }

    @Override
    public void setMyFilesToTreat(Set<ApplicationFile> myFilesToTreat) {
        this.filesToTreat = myFilesToTreat;
    }

    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        this.beanFactory = bf;
    }   
}
