/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.customComponent.excludeTree.impl;

import caillou.company.clonemanager.gui.customComponent.common.contract.DirectoryLazyCheckableTreeItem;
import caillou.company.clonemanager.gui.customComponent.common.impl.DirectoryTreeItem;
import caillou.company.clonemanager.gui.customComponent.excludeTree.contract.FactoryDirectoryProvider;
import org.springframework.stereotype.Component;
/**
 *
 * @author pierre
 */
@Component
public class FactoryDirectoryCheckableTreeItem implements FactoryDirectoryProvider{

    @Override
    public DirectoryLazyCheckableTreeItem createItem(String absolutePath) {
        DirectoryLazyCheckableTreeItem directoryLazyCheckableTreeItem = new DirectoryTreeItem(absolutePath);
        return directoryLazyCheckableTreeItem;
    }
    
}
