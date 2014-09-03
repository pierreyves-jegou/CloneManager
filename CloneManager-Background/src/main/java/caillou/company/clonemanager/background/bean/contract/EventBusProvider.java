/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.bean.contract;

import com.google.common.eventbus.EventBus;

/**
 *
 * @author pierre
 */
public interface EventBusProvider {
    
    public EventBus getEventBus();
    
}
