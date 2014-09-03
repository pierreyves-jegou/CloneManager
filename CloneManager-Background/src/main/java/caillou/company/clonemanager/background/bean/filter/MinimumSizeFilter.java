/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;

/**
 *
 * @author pierre
 */
public class MinimumSizeFilter implements Filter<ApplicationFile> {

    private Long minimumSize = null;

    public MinimumSizeFilter(Long minimumSize) {
        this.minimumSize = minimumSize;
    }

    @Override
    public boolean accept(ApplicationFile myFile) {
        
        if(this.minimumSize == null)
            return true;
               
        return (this.minimumSize != null && myFile.getSize().compareTo(this.minimumSize) > 0); 
    }
}
