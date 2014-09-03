/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.excludeTree.contract;

import caillou.company.clonemanager.gui.customComponent.common.contract.DirectoryLazyCheckableTreeItem;

/**
 *
 * @author pierre
 */
public interface FactoryDirectoryProvider {
    
    public DirectoryLazyCheckableTreeItem createItem(String absolutePath);
    
}
