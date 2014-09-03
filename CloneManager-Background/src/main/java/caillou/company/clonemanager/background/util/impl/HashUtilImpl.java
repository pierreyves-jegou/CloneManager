/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.util.impl;

import caillou.company.clonemanager.background.bean.applicationFile.contract.HashProvider;
import caillou.company.clonemanager.background.util.contract.HashUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class HashUtilImpl implements HashUtil{

    @Override
    public <T extends HashProvider> Map<String, List<T>> sortPerMD5(List<T> hashProviderList) {
        Map<String, List<T>> listHashProviderPerHash = new HashMap<>();
        for(T hashProvider : hashProviderList){
            if(!listHashProviderPerHash.containsKey(hashProvider.getMD5Print())){
                listHashProviderPerHash.put(hashProvider.getMD5Print(), new ArrayList<T>());
            }
            listHashProviderPerHash.get(hashProvider.getMD5Print()).add(hashProvider);
        }
        return listHashProviderPerHash;
    }
    
}
