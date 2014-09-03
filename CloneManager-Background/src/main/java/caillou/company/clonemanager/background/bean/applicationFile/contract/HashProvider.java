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
public interface HashProvider {
    
    public String getMD5Print();
    
    public void setMD5Print(String MD5Print);
    
    public String getPartialMD5Print();
    
    public void setPartialMD5Print(String MD5Print);
}
