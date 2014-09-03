/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.service.classifier.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pierre
 * @param <T>
 * @param <U>
 */
public class Analyse<T,U extends ApplicationFile> {

    private Map<T, Set<U>> filesThatDoMatch = new HashMap<>();
    
    private Map<T, Set<U>> filesThatMigthMatch = new HashMap<>();
    
    private Map<T, Set<U>> filesThatDoNotMatch = new HashMap<>();
    
    private Set<U> filesNotTreated = new HashSet<>();
    
    public Map<T, Set<U>> getFilesThatDoMatch() {
        return filesThatDoMatch;
    }

    public void addEntryThatDoMatch(Map.Entry<T, Set<U>> entry){
        if(!filesThatDoMatch.containsKey(entry.getKey())){
            filesThatDoMatch.put(entry.getKey(), new HashSet<U>());
        }
        filesThatDoMatch.get(entry.getKey()).addAll(entry.getValue());
    }
    
    public void addEntryThatMigthMatch(Map.Entry<T, Set<U>> entry){
        if(!filesThatMigthMatch.containsKey(entry.getKey())){
            filesThatMigthMatch.put(entry.getKey(), new HashSet<U>());
        }
        filesThatMigthMatch.get(entry.getKey()).addAll(entry.getValue());
    }
    
    public void addEntryThatDoNotMatch(Map.Entry<T, Set<U>> entry){
        if(!filesThatDoNotMatch.containsKey(entry.getKey())){
            filesThatDoNotMatch.put(entry.getKey(), new HashSet<U>());
        }
        filesThatDoNotMatch.get(entry.getKey()).addAll(entry.getValue());
    }
    
    public void addAllEntryThatDoMatch(Map<T, Set<U>> map){
        filesThatDoMatch.putAll(map);
    }
    
    public void addAllEntryThatMigthMatch(Map<T, Set<U>> map){
        filesThatMigthMatch.putAll(map);
    }
    
    public void addAllEntryThatDoNotMatch(Map<T, Set<U>> map){
        filesThatDoNotMatch.putAll(map);
    }
    
    public void setFilesThatDoMatch(Map<T, Set<U>> filesThatDoMatch) {
        this.filesThatDoMatch = filesThatDoMatch;
    }

    public Map<T, Set<U>> getFilesThatMigthMatch() {
        return filesThatMigthMatch;
    }

    public void setFilesThatMigthMatch(Map<T, Set<U>> filesThatMigthMatch) {
        this.filesThatMigthMatch = filesThatMigthMatch;
    }

    public Map<T, Set<U>> getFilesThatDoNotMatch() {
        return filesThatDoNotMatch;
    }

    public void setFilesThatDoNotMatch(Map<T, Set<U>> filesThatDoNotMatch) {
        this.filesThatDoNotMatch = filesThatDoNotMatch;
    }

    public Set<U> getFilesNotTreated() {
        return filesNotTreated;
    }

    public void setFilesNotTreated(Set<U> filesNotTreated) {
        this.filesNotTreated = filesNotTreated;
    }

    
}
