/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.critere;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class Format {
    
    private final BooleanProperty all = new SimpleBooleanProperty(true);
    private final BooleanProperty video = new SimpleBooleanProperty(false);
    private final BooleanProperty audio = new SimpleBooleanProperty(false);
    private final BooleanProperty image = new SimpleBooleanProperty(false);
    private final BooleanProperty archive = new SimpleBooleanProperty();
     
    
    public Format(){
    }
    
    public boolean isAll() {
        return all.get();
    }

    public void setAll(boolean value) {
        all.set(value);
    }

    public BooleanProperty allProperty() {
        return all;
    }
    
    public boolean isVideo() {
        return video.get();
    }

    public void setVideo(boolean value) {
        video.set(value);
    }

    public BooleanProperty videoProperty() {
        return video;
    }
    
    public boolean isAudio() {
        return audio.get();
    }

    public void setAudio(boolean value) {
        audio.set(value);
    }

    public BooleanProperty audioProperty() {
        return audio;
    }
           
    public boolean isImage() {
        return image.get();
    }

    public void setImage(boolean value) {
        image.set(value);
    }

    public BooleanProperty imageProperty() {
        return image;
    }
    
    public boolean isArchive() {
        return archive.get();
    }

    public void setArchive(boolean value) {
        archive.set(value);
    }

    public BooleanProperty archiveProperty() {
        return archive;
    }
}
