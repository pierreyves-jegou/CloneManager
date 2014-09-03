/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.util.contract;

import caillou.company.clonemanager.background.bean.applicationFile.contract.HashProvider;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pierre
 */
public interface HashUtil {
    
    public <T extends HashProvider> Map<String, List<T>> sortPerMD5(List<T> hashProviderList);
    
}
