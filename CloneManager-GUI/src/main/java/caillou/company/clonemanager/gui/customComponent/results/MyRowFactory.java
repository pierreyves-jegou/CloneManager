/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.bean.contract.EventBusProvider;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.event.MouseEnteredRowEvent;
import caillou.company.clonemanager.gui.handler.CopyFilesHandler;
import caillou.company.clonemanager.gui.handler.PreSuppressionEventHandler;
import caillou.company.clonemanager.gui.service.task.impl.CopyToTask;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import com.google.common.eventbus.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author pierre
 */
public class MyRowFactory implements Callback<TableView<GUIApplicationFile>, TableRow<GUIApplicationFile>>, EventBusProvider {

    private boolean higthLigthMode = false;
    public static final String FIRST_POSITION_STYLE = "firstPosition";
    public static final String MIDDLE_POSITION_STYLE = "middlePosition";
    public static final String LAST_POSITION_STYLE = "lastPosition";
    public static final String ALONE_POSITION_STYLE = "alonePosition";

    private TableView<GUIApplicationFile> resultView;
    private final ObservableList<GUIApplicationFile> guiApplicationFileList;
    protected EventBus eventBus = new EventBus("ROW_EVENT_BUS");

    public TableView<GUIApplicationFile> getResultView() {
        return resultView;
    }

    public void setResultView(TableView<GUIApplicationFile> resultView) {
        this.resultView = resultView;
    }

    public MyRowFactory(TableView<GUIApplicationFile> tableView, ObservableList<GUIApplicationFile> guiApplicationFileList) {
        resultView = tableView;
        higthLigthMode = true;
        this.guiApplicationFileList = guiApplicationFileList;
    }

    @Override
    public TableRow<GUIApplicationFile> call(TableView<GUIApplicationFile> tableView) {

        final TableRow<GUIApplicationFile> row = new TableRow<GUIApplicationFile>() {
            @Override
            protected void updateItem(final GUIApplicationFile myFileFX, boolean empty) {
                super.updateItem(myFileFX, empty);
                String cssStyle = null;
                getStyleClass().remove(FIRST_POSITION_STYLE);
                getStyleClass().remove(MIDDLE_POSITION_STYLE);
                getStyleClass().remove(LAST_POSITION_STYLE);
                getStyleClass().remove(ALONE_POSITION_STYLE);

                if (myFileFX != null && higthLigthMode) {
                    switch (myFileFX.getCurrentPostion()) {
                        case FIRST:
                            cssStyle = FIRST_POSITION_STYLE;
                            break;
                        case MIDDLE:
                            cssStyle = MIDDLE_POSITION_STYLE;
                            break;
                        case LAST:
                            cssStyle = LAST_POSITION_STYLE;
                            break;
                        case ALONE:
                            cssStyle = ALONE_POSITION_STYLE;
                            break;
                    }
                }
                getStyleClass().add(cssStyle);
            }
        };

        final ContextMenu rowMenu = new ContextMenu();
        ImageView garbageImageView = new ImageView(new Image(caillou.company.clonemanager.gui.Image.GARBAGE_ICON));
        garbageImageView.setFitHeight(16);
        garbageImageView.setFitWidth(16);
        MenuItem removeItem = new MenuItem(SpringFxmlLoader.getResourceBundle().getString("contextual.text.suppress"), garbageImageView);
        ImageView hideImageView = new ImageView(new Image(caillou.company.clonemanager.gui.Image.HIDE_ICON));
        MenuItem clearItem = new MenuItem(SpringFxmlLoader.getResourceBundle().getString("contextual.text.removeFromView"), hideImageView);
        ImageView copyImageView = new ImageView(new Image(caillou.company.clonemanager.gui.Image.COPY_ICON));
        MenuItem copyTo = new MenuItem(SpringFxmlLoader.getResourceBundle().getString("contextual.text.copyTo"), copyImageView);
        removeItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PreSuppressionEventHandler preSuppressionEventHandler = SpringFxmlLoader.getBean(PreSuppressionEventHandler.class);
                preSuppressionEventHandler.setTableView(resultView);
                preSuppressionEventHandler.setSuppressOnlyOnSelected(true);
                preSuppressionEventHandler.setGuiApplicationFileList(guiApplicationFileList);
                preSuppressionEventHandler.handleWithoutThread(resultView.getSelectionModel().getSelectedItems());
            }
        });

        CopyFilesHandler copyFilesHandler = SpringFxmlLoader.getBean(CopyFilesHandler.class);
        copyFilesHandler.setFileToShow(resultView.getSelectionModel().getSelectedItems());
        copyFilesHandler.setGuiApplicationFileList(guiApplicationFileList);
        copyFilesHandler.setTableView(tableView);
        copyTo.setOnAction(copyFilesHandler);

        clearItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<GUIApplicationFile> filesToRemoveFromView = new ArrayList<>();
                for (GUIApplicationFile myFileFX : resultView.getSelectionModel().getSelectedItems()) {
                    filesToRemoveFromView.add(myFileFX);
                }
                for (GUIApplicationFile myFileFX : filesToRemoveFromView) {
                    guiApplicationFileList.remove(myFileFX);
                    GUIApplicationFile myFileAlone = GUIApplicationFileUtil.isolateSingleFile(resultView.getItems(), myFileFX);
                    if (myFileAlone != null) {
                        guiApplicationFileList.remove(myFileAlone);
                        guiApplicationFileList.add(myFileAlone);
                    }
                }
            }
        });

        rowMenu.getItems().addAll(copyTo, removeItem, clearItem);
        row.contextMenuProperty().bind(
                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                .then(rowMenu)
                .otherwise((ContextMenu) null));

        row.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                eventBus.post(new MouseEnteredRowEvent(row));
            }

        });

        return row;
    }

    public boolean isHigthLigthMode() {
        return higthLigthMode;
    }

    public void setHigthLigthMode(boolean higthLigthMode) {
        this.higthLigthMode = higthLigthMode;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }
}
