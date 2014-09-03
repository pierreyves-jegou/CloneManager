/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.excludeTree;

import caillou.company.clonemanager.gui.customComponent.common.Controller;
import caillou.company.clonemanager.gui.customComponent.common.contract.DialogWrapper;
import caillou.company.clonemanager.gui.customComponent.common.contract.DirectoryLazyCheckableTreeItem;
import caillou.company.clonemanager.gui.customComponent.excludeTree.impl.FactoryDirectoryCheckableTreeItem;
import java.io.File;
import java.io.FileFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author pierre
 */
@org.springframework.stereotype.Controller
@Scope(value = "prototype")
public class ExcludeTreeController extends Controller<ExcludeModel> implements DialogWrapper{

    @FXML
    private VBox rootId;

    private TreeItem<DirectoryLazyCheckableTreeItem> root;

    private FactoryDirectoryCheckableTreeItem factoryDirectoryCheckableTreeItem;
    
    private Dialog wrappingDialog;

    FileFilter directoryFilter = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            } else {
                return false;
            }
        }
    };


    public void buildTreeLazily(final TreeItem<DirectoryLazyCheckableTreeItem> currentItem, int nbPass) {
        if (nbPass == 0) {
            return;
        }

        boolean lazyChilds = nbPass > 2;
        DirectoryLazyCheckableTreeItem currentItemValue = currentItem.getValue();
        final ExcludeTreeController excludeControllerInstance = this;

        File currentDirectory = new File(currentItemValue.getAbsolutePath());
        if (!currentDirectory.canRead()) {
            return;
        }

        currentItemValue.setLoaded(lazyChilds);
        currentItem.getChildren().clear();
        
        for (File subFile : currentDirectory.listFiles(directoryFilter)) {
            TreeItem<DirectoryLazyCheckableTreeItem> children = new CheckBoxTreeItem<>(this.factoryDirectoryCheckableTreeItem.createItem(subFile.getAbsolutePath()));
            this.buildTreeLazily(children, nbPass - 1);
            currentItem.getChildren().add(children);
        }

        if (nbPass == 2) {
            currentItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    excludeControllerInstance.buildTreeLazily(currentItem, 3);
                    currentItem.expandedProperty().removeListener(this);
                }
            });
        }

    }
    
       public void buildTreeLazily(final CheckBoxTreeItem<DirectoryLazyCheckableTreeItem> currentItem, int nbPass) {
        if (nbPass == 0) {
            return;
        }

        boolean lazyChilds = nbPass > 2;
        DirectoryLazyCheckableTreeItem currentItemValue = currentItem.getValue();
        final ExcludeTreeController excludeControllerInstance = this;

        File currentDirectory = new File(currentItemValue.getAbsolutePath());
        if (!currentDirectory.canRead()) {
            return;
        }

        currentItemValue.setLoaded(lazyChilds);
        currentItem.getChildren().clear();
        
        for (File subFile : currentDirectory.listFiles(directoryFilter)) {
            DirectoryLazyCheckableTreeItem childrenDirectoryLazyCheckableTreeItem = this.factoryDirectoryCheckableTreeItem.createItem(subFile.getAbsolutePath());
            CheckBoxTreeItem<DirectoryLazyCheckableTreeItem> children = new CheckBoxTreeItem<>(childrenDirectoryLazyCheckableTreeItem);
            children.setSelected(!this.getModel().isExcludedByUser(subFile.toPath()));
            final ExcludeModel excludeModel = this.getModel();
            final File tmpFile = subFile;
            children.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                       excludeModel.removeExclusionByUser(tmpFile.toPath());
                    }else{
                        excludeModel.addExclusionByUser(tmpFile.toPath());
                    }
                }
            });
            
            this.buildTreeLazily(children, nbPass - 1);
            currentItem.getChildren().add(children);
        }

        if (nbPass == 2) {
            currentItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    excludeControllerInstance.buildTreeLazily(currentItem, 3);
                    currentItem.expandedProperty().removeListener(this);
                }
            });
        }

    }
    
    
    public void initialiseRootDirectory() {
        DirectoryLazyCheckableTreeItem rootValue = this.factoryDirectoryCheckableTreeItem.createItem(this.getModel().getPath());
        final CheckBoxTreeItem<DirectoryLazyCheckableTreeItem> rootItem = new CheckBoxTreeItem<>(rootValue);
        rootItem.setSelected(true);
         
        this.buildTreeLazily(rootItem, 3);
        TreeView<DirectoryLazyCheckableTreeItem> treeView = new TreeView<>(rootItem);
        
        treeView.prefWidthProperty().bind(this.rootId.widthProperty().subtract(10));
        treeView.prefHeightProperty().bind(this.rootId.heightProperty().subtract(10));
        treeView.setCellFactory(CheckBoxTreeCell.<DirectoryLazyCheckableTreeItem>forTreeView());
        this.rootId.getChildren().add(treeView);
    }

    @Autowired
    public void setFactoryDirectoryCheckableTreeItem(FactoryDirectoryCheckableTreeItem factoryDirectoryCheckableTreeItem) {
        this.factoryDirectoryCheckableTreeItem = factoryDirectoryCheckableTreeItem;
    }
    
    @FXML
    private void closeAction(ActionEvent event) {
        this.wrappingDialog.hide();
    }

    @Override
    public void setWrappingDialog(Dialog wrappingDialog) {
        this.wrappingDialog = wrappingDialog;
    }

    @Override
    public Dialog getWrappingDialog() {
        return this.wrappingDialog;
    }
}
