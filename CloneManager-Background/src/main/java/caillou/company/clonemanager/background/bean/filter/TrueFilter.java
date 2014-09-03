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
public class TrueFilter implements Filter<ApplicationFile>{

    @Override
    public boolean accept(ApplicationFile myFile) {
        return true;
    }
    
}
