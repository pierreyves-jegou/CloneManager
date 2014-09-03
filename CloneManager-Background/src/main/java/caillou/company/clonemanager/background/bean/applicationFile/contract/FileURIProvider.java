/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.bean.applicationFile.contract;

/**
 *
 * @author pierre
 */
public interface FileURIProvider {
    
    public String getAbsolutePath();
    
    public String getBaseNamePath();
}
