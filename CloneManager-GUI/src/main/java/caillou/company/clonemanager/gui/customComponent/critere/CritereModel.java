/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.critere;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.filter.ArchiveExtensionFilter;
import caillou.company.clonemanager.background.bean.filter.AudioExtensionFilter;
import caillou.company.clonemanager.background.bean.filter.Filter;
import caillou.company.clonemanager.background.bean.filter.FilterGroup;
import caillou.company.clonemanager.background.bean.filter.ImageExtensionFilter;
import caillou.company.clonemanager.background.bean.filter.MaximumSizeFilter;
import caillou.company.clonemanager.background.bean.filter.MinimumSizeFilter;
import caillou.company.clonemanager.background.bean.filter.TrueFilter;
import caillou.company.clonemanager.background.bean.filter.VideoExtensionFilter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class CritereModel{
   
    private Format format;
    
    private Size size;
        
    private final BooleanProperty valid
            = new SimpleBooleanProperty(false);
    
    public CritereModel(){
    }
        
    
    public Format getFormat() {
        return format;
    }

    @Autowired
    public void setFormat(Format format) {
        this.format = format;
    }
    
    public Size getSize() {
        return size;
    }

    @Autowired
    public void setSize(Size size) {
        this.size = size;
    }
    
    public Filter<ApplicationFile> getFilters(){
        FilterGroup filter = new FilterGroup();
        filter.addFilter(new TrueFilter());

        if (this.getFormat() != null) {
            if (this.getFormat().isVideo()) {
                filter.addFilter(new VideoExtensionFilter());
            }
            if (this.getFormat().isAudio()) {
                filter.addFilter(new AudioExtensionFilter());
            }
           if(this.getFormat().isImage()){
               filter.addFilter(new ImageExtensionFilter());
           }
           if(this.getFormat().isArchive()){
               filter.addFilter(new ArchiveExtensionFilter());
           }
        }
        
        if(this.getSize().getMinimalSize() != null && this.getSize().getMinimalSize() > 0){
           filter.addFilter(new MinimumSizeFilter(this.getSize().getMinimalSize()));
        }
                
        if(this.getSize().getMaximalSize() != null && this.getSize().getMaximalSize() > 0){
           filter.addFilter(new MaximumSizeFilter(this.getSize().getMaximalSize()));
        }

        return filter;
    }
    
    public boolean isValid() {
        return valid.get();
    }

    public void setValid(boolean value) {
        valid.set(value);
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    @PostConstruct
    public void initialize() {
        valid.bind(Bindings.or(this.getFormat().allProperty(), Bindings.or(this.getFormat().audioProperty(), this.getFormat().imageProperty()).or(this.getFormat().videoProperty())).or(this.getFormat().archiveProperty()));
    }
    
}
