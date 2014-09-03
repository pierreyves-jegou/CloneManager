/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.excludeTree;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.filter.ExcludedPathFilter;
import caillou.company.clonemanager.background.bean.filter.Filter;
import caillou.company.clonemanager.background.bean.filter.FilterGroup;
import caillou.company.clonemanager.background.bean.filter.TrueFilter;
import caillou.company.clonemanager.gui.customComponent.common.Model;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class ExcludeModel implements Model {

    private final StringProperty path = new SimpleStringProperty();
    private final Set<Path> excludedDirectoriesByUser = new HashSet<>();
    private Set<Path> excludedDirectoriesByApplication = new HashSet<>();

    @PostConstruct
    private void postConstruct() {
        final ExcludeModel excludeModel = this;
        this.pathProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                excludeModel.updateModel(newValue);
            }

        });
    }

    public boolean isExcludedByUser(Path path) {
        return excludedDirectoriesByUser.contains(path);
    }

    public void addExclusionByUser(Path path) {
        this.excludedDirectoriesByUser.add(path);
    }

    public void removeExclusionByUser(Path path) {
        this.excludedDirectoriesByUser.remove(path);
    }

    public void clearExclusionByApplication() {
        this.excludedDirectoriesByApplication.clear();
    }

    public void addExclusionByApplication(Path pathToExclude) {
        this.excludedDirectoriesByApplication.add(pathToExclude);
    }

    public Set<Path> getExclusionByApplication() {
        return excludedDirectoriesByApplication;
    }

    public void setExclusionByApplication(Set<Path> exludedPathFromApplication) {
        this.excludedDirectoriesByApplication = exludedPathFromApplication;
    }

    public Filter<ApplicationFile> getFilters(){
        FilterGroup filterGroup = new FilterGroup();
        filterGroup.addFilter(new TrueFilter());
        for(Path path : excludedDirectoriesByApplication){
            filterGroup.addFilter(new ExcludedPathFilter(path));
        }
        for(Path path : excludedDirectoriesByUser){
            filterGroup.addFilter(new ExcludedPathFilter(path));
        }
        return filterGroup;
    }
    
    private void updateModel(String path) {
        this.excludedDirectoriesByUser.clear();
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String value) {
        path.set(value);
    }

    public StringProperty pathProperty() {
        return path;
    }
}
