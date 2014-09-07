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
    public static final String HIGTHLIGHTED_STYLE = "higthtedTableLigthed";
    public static final String DEFAULT_STYLE = "defaultTableStyle";
    public static final String ALONE_STYLE = "aloneTableStyle";

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
                String cssStyle = DEFAULT_STYLE;
                getStyleClass().remove(HIGTHLIGHTED_STYLE);
                getStyleClass().remove(DEFAULT_STYLE);
                getStyleClass().remove(ALONE_STYLE);

                if (myFileFX != null) {
                    if (myFileFX.isAlone() != null && myFileFX.isAlone()) {
                        cssStyle = ALONE_STYLE;
                    } else if (higthLigthMode) {
                        cssStyle = myFileFX.getCssColor();
                    } else {
                        cssStyle = DEFAULT_STYLE;
                    }
                }
                getStyleClass().add(cssStyle);
            }
        };

        final ContextMenu rowMenu = new ContextMenu();
        ImageView garbageImageView = new ImageView(new Image(caillou.company.clonemanager.gui.Image.GARBAGE_ICON));
        garbageImageView.setFitHeight(16);
        garbageImageView.setFitWidth(16);
        MenuItem removeItem = new MenuItem("Supprimer", garbageImageView);
        ImageView hideImageView = new ImageView(new Image(caillou.company.clonemanager.gui.Image.HIDE_ICON));
        MenuItem clearItem = new MenuItem("Effacer de la vue", hideImageView);
        ImageView copyImageView = new ImageView(new Image(caillou.company.clonemanager.gui.Image.COPY_ICON));
        MenuItem copyTo = new MenuItem("Copier vers ...", copyImageView);
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

        copyTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Sélectionner le répertoire");
                File targetDirectory = directoryChooser.showDialog(null);
                if (targetDirectory != null && targetDirectory.exists() && targetDirectory.isDirectory()) {

                    Action response = Dialogs.create()
                            .owner(MainApp.getInstance().getStage())
                            .title("Confirmation")
                            .masthead(null)
                            .message("Voulez vous copier les éléments séléctionnés vers : \"" + targetDirectory.getAbsolutePath() + "\" ?")
                            .actions(Dialog.Actions.CANCEL, Dialog.Actions.OK)
                            .showConfirm();
                    if (response == Dialog.Actions.OK) {
                        CopyToTask copyToTask = new CopyToTask();
                        copyToTask.setTargetDirectory(targetDirectory);
                        copyToTask.setFilesToCopy(resultView.getSelectionModel().getSelectedItems());
                        new Thread(copyToTask).start();
                    }
                }
            }
        });

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
