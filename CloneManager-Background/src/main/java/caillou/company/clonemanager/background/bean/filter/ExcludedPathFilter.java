/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.nio.file.Path;

/**
 *
 * @author pierre
 */
public class ExcludedPathFilter implements Filter<ApplicationFile>{

    private final Path excludedPath;
    
    public ExcludedPathFilter(Path excludedPath){
        this.excludedPath = excludedPath;
    }
    
    @Override
    public boolean accept(ApplicationFile myFile) {
        if(myFile == null){
            return false;
        }else if(myFile.getAbsolutePath().startsWith(excludedPath.toString())){
            return false;
        }else{
            return true;
        }
    }
    
}
