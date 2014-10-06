/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.service.task.impl;

import caillou.company.clonemanager.background.exception.CloneManagerIOException;
import caillou.company.clonemanager.background.log.ErrorMessage;
import java.io.IOException;
import java.nio.file.Path;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Aspect
@Component
public class CloneManagerIOExceptionAdvice {
    
    private CloneManagerIOExceptionBean CloneManagerIOExceptionBean;
    
    @AfterThrowing(pointcut="execution(* caillou.company.clonemanager.background.service.classifier.impl.Classifier.classify(..))", throwing = "e")
    public void handleFileToTreat(Exception e) {
        if(CloneManagerIOException.class.isAssignableFrom(e.getClass())){
            CloneManagerIOExceptionBean.getReadingErrors().add((CloneManagerIOException) e);
        }
    }

    
    @After("execution(* caillou.company.clonemanager.background.service.impl.FileVisitor.visitFileFailed(..)) && args(path, exception)")
    public void handleFailedToVisit(Path path, IOException exception) {
        String message = this.getErrorMessageForIOException(path);
        CloneManagerIOException cloneManagerIOException = new CloneManagerIOException(message, exception.getMessage(), path.toString());
        CloneManagerIOExceptionBean.getReadingErrors().add(cloneManagerIOException);
    }
    
//    @AfterThrowing(pointcut="execution(* caillou.company.clonemanager.background.service.impl.FileVisitor.visitFile(..)) && args(path, ..)", throwing = "exception")
//    public void handleVisitFile(Path path, Exception exception){
//        if(IOException.class.isAssignableFrom(exception.getClass())){
//            String message = this.getErrorMessageForIOException(path);
//            CloneManagerIOException cloneManagerIOException = new CloneManagerIOException(message, exception.getMessage(), path.toString());
//            CloneManagerIOExceptionBean.getReadingErrors().add(cloneManagerIOException);
//        }
//    }
    
    
    public void setCloneManagerIOExceptionBean(CloneManagerIOExceptionBean CloneManagerIOExceptionBean) {
        this.CloneManagerIOExceptionBean = CloneManagerIOExceptionBean;
    }

    private String getErrorMessageForIOException(Path path){
        String message;
       
        if(path.toFile().exists() && path.toFile().isDirectory()){
            message = ErrorMessage.VISIT_DIRECTORY_FAILED;
        }else{
            message = ErrorMessage.VISIT_FILE_FAILED;
        }
        message += path.toString();
        return message;
    }
    
    
}
