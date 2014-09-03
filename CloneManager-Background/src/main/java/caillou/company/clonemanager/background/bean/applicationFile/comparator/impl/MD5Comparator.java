/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.bean.applicationFile.comparator.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.Comparator;

/**
 *
 * @author pierre
 */
public class MD5Comparator implements Comparator<ApplicationFile>{

    @Override
    public int compare(ApplicationFile o1, ApplicationFile o2) {
        if(o1.getMD5Print() == null && o2.getMD5Print() == null){
            return 0;
        }if(o1.getMD5Print() != null && o2.getMD5Print() == null){
            return -1;
        }if(o1.getMD5Print() == null && o2.getMD5Print() != null){
            return 1;
        }else{
            return o1.getMD5Print().compareTo(o2.getMD5Print());
        }
    }
    
}
