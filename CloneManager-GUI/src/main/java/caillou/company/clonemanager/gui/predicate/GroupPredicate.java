/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.predicate;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author pierre
 * @param <T>
 */
public class GroupPredicate<T extends GUIApplicationFile> implements Predicate<T>{

    private List<Predicate<T>> predicates = new ArrayList<>();
    
    @Override
    public boolean test(GUIApplicationFile t) {
        for(Predicate predicate : predicates){
            if(!predicate.test(t)){
                return false;
            }
        }
        return true;
    }
    
    public void add(Predicate<T> predicate){
        this.predicates.add(predicate);
    }
    
    public void remove(Predicate<T> predicate){
        this.predicates.remove(predicate);
    }
    
    public void clear(){
        this.predicates.clear();
    }
    
}
