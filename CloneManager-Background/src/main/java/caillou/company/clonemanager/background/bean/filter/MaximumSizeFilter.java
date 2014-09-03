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
public class MaximumSizeFilter implements Filter<ApplicationFile> {

    private Long maximumSize = null;

    public MaximumSizeFilter(Long maximumSize) {
        this.maximumSize = maximumSize;
    }

    @Override
    public boolean accept(ApplicationFile myFile) {
        
        if(this.maximumSize == null)
            return true;
        
        return (this.maximumSize != null && myFile.getSize().compareTo(this.maximumSize) < 0); 
    }
}
