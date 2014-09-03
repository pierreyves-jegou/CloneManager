/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pierre
 */
public class MyFileFX implements GUIApplicationFile{
    
    private final StringProperty md5Print = new SimpleStringProperty();
    private final StringProperty absolutePath = new SimpleStringProperty();
    private final StringProperty group = new SimpleStringProperty();
    private final BooleanProperty higthLigth = new SimpleBooleanProperty(true);
    private final StringProperty cssColor = new SimpleStringProperty();
    private final ApplicationFile myFile;
    private Boolean isAlone = null;
    
    public MyFileFX(ApplicationFile myFile){
        this.md5Print.set(myFile.getMD5Print());
        this.absolutePath.set(myFile.getAbsolutePath());
        this.group.set(myFile.getGroup());
        this.myFile = myFile;
    }
    
    @Override
    public String getMD5Print() {
        return md5Print.get();
    }

    public void setMd5Print(String value) {
        md5Print.set(value);
    }

    public StringProperty md5PrintProperty() {
        return md5Print;
    }
    
    @Override
    public String getAbsolutePath() {
        return absolutePath.get();
    }

    public void setAbsolutePath(String value) {
        absolutePath.set(value);
    }

    public StringProperty absolutePathProperty() {
        return absolutePath;
    }
    
    @Override    
    public String getGroup() {
        return group.get();
    }

    public void setGroup(String value) {
        group.set(value);
    }

    public StringProperty groupProperty() {
        return group;
    }
    
    public boolean isHigthLigth() {
        return higthLigth.get();
    }

    public void setHigthLigth(boolean value) {
        higthLigth.set(value);
    }

    public BooleanProperty higthLigthProperty() {
        return higthLigth;
    }
    
    public String getCssColor() {
        return cssColor.get();
    }

    public void setCssColor(String value) {
        cssColor.set(value);
    }

    public StringProperty cssColorProperty() {
        return cssColor;
    }
   
    public ApplicationFile getMyFile() {
        return myFile;
    }

    @Override
    public void setMD5Print(String MD5Print) {
        this.myFile.setMD5Print(MD5Print);
    }

    @Override
    public String getPartialMD5Print() {
        return this.myFile.getPartialMD5Print();
    }

    @Override
    public void setPartialMD5Print(String MD5Print) {
        this.myFile.setPartialMD5Print(MD5Print);
    }

    @Override
    public String getBaseNamePath() {
        return this.myFile.getBaseNamePath();
    }

    @Override
    public Long getSize() {
        return this.myFile.getSize();
    }

    @Override
    public Boolean isAlone() {
        return isAlone;
    }

    @Override
    public void setAlone(Boolean isAlone) {
        this.isAlone = isAlone;
    }
}
