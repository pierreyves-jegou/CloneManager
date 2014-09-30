/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template targetDirectory, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.WindowsPreferredDimensions;
import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import caillou.company.clonemanager.gui.bean.impl.IntegerField;
import caillou.company.clonemanager.gui.customComponent.common.MainModel;
import caillou.company.clonemanager.gui.customComponent.statistic.StatisticHelper;
import caillou.company.clonemanager.gui.customComponent.taskchoice.TaskModel;
import caillou.company.clonemanager.gui.event.MouseEnteredRowEvent;
import caillou.company.clonemanager.gui.handler.PreSuppressionEventHandler;
import caillou.company.clonemanager.gui.predicate.AlonePredicate;
import caillou.company.clonemanager.gui.predicate.GroupPredicate;
import caillou.company.clonemanager.gui.predicate.PathPredicate;
import caillou.company.clonemanager.gui.service.task.impl.AbstractFetchTask;
import caillou.company.clonemanager.gui.service.task.impl.FetchAllDuplicateHavingACopyTask;
import caillou.company.clonemanager.gui.service.task.impl.FetchDuplicateHavingCopyFromGroupTask;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import com.google.common.eventbus.Subscribe;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
public class ResultController implements Initializable {

    @FXML
    private HBox mainHbox;

    @FXML
    private TableColumn<GUIApplicationFile, String> columnFile;

    @FXML
    private TableColumn<GUIApplicationFile, String> columnGroup;

    @FXML
    private TableColumn<GUIApplicationFile, String> md5PrintId;
    
    @FXML
    private TableColumn<GUIApplicationFile, Long> columnSize;

    @FXML
    private TableColumn<GUIApplicationFile, String> columnHashPosition;
    
    @FXML
    private TableView<GUIApplicationFile> resultViewId;
    
    @FXML
    private CheckBox hideSingleFileId;

    @FXML
    private SplitPane splittedPanelId;

    @FXML
    private Accordion accordionPaneId;

    @FXML
    private VBox staticContainer;

    @FXML
    private TitledPane informationPaneId;

    @FXML
    private HBox hideSingleFileContainerId;
    
    @FXML
    private VBox deleteDoublonsActionBoxId;
    
    @FXML
    private VBox deleteDoublonsActionFromGroupId;

    @FXML
    private ComboBox<Group> groupId;

    @FXML
    private TextField filterTextFieldId;

    @FXML
    private ComboBox<String> pathFilterTypeId;

    @FXML
    private String startWithValueId;

    @FXML
    private String containsValueId;

    @FXML
    private String endWithValueId;

    @FXML
    private VBox mainContainerPanelID;
   
    private final ListProperty<GUIApplicationFile> guiApplicationFileList = new SimpleListProperty<>();

    private FilteredList<GUIApplicationFile> guiApplicationFileListFiltered;

    private MyRowFactory myRowFactory = null;

    private MainModel mainModel;

    private PopOver popOver;

    private final GroupPredicate mainPredicate = new GroupPredicate();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.guiApplicationFileListFiltered = new FilteredList<>(guiApplicationFileList);
        this.initializePhaseAutomaticResizing();
        this.initializeContextDependant();

        SortedList<GUIApplicationFile> sortedList = new SortedList<>(guiApplicationFileListFiltered);
        // Bind the SortedList comparator to the TableView comparator.
        sortedList.comparatorProperty().bind(resultViewId.comparatorProperty());

        resultViewId.setItems(sortedList);
        resultViewId.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        this.filterList(guiApplicationFileListFiltered);
        this.initializeFilter();
        this.initializeRowFactory();

        this.initializeStatistic();

        accordionPaneId.setExpandedPane(informationPaneId);

        groupId.getItems().add(Group.GROUP1);
        groupId.getItems().add(Group.GROUP2);
        groupId.setValue(Group.GROUP1);
        
        /**
         * Due to the bug
         * "https://bitbucket.org/controlsfx/controlsfx/issue/185/nullpointerexception-when-using-popover"
         */
        MainApp.getInstance().getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (popOver != null) {
                    popOver.hide(Duration.millis(0));
                }
            }
        });
        /**
         * End *
         */
    }

    private void initializeRowFactory() {
        myRowFactory = new MyRowFactory(resultViewId, guiApplicationFileList);
        myRowFactory.getEventBus().register(this);
        myRowFactory.setHigthLigthMode(this.mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS));
        resultViewId.setRowFactory(myRowFactory);
    }

    private void initializeStatistic(){
        int i=0;
        List<Node> children = StatisticHelper.createStaticList();
        for (Node child : children) {
            staticContainer.getChildren().add(child);
        }
    }
   
    
    private void initializeFilter() {
        if (mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            hideSingleFileId.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    filterList(guiApplicationFileListFiltered);
                }
            });
        }
        filterTextFieldId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterList(guiApplicationFileListFiltered);
            }
        });

        pathFilterTypeId.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterList(guiApplicationFileListFiltered);
            }
        });

    }

    private void filterList(FilteredList<GUIApplicationFile> fileListFiltered) {
        this.mainPredicate.clear();
        if (mainModel.getTaskModel().getCurrentTask().equals(TaskModel.TASK.DETECT_DOUBLONS)) {
            if (hideSingleFileId.selectedProperty().get()) {
                AlonePredicate alonePredicate = new AlonePredicate();
                this.mainPredicate.add(alonePredicate);
            }
        }
        if (filterTextFieldId.textProperty().get() != null && !filterTextFieldId.textProperty().get().equals("")) {
            PathPredicate pathPredicate = new PathPredicate();
            pathPredicate.setPath(filterTextFieldId.textProperty().get());
            if (pathFilterTypeId.valueProperty().get().equals(startWithValueId)) {
                pathPredicate.setCurrentType(PathPredicate.TYPE.STARTWITH);
            }
            if (pathFilterTypeId.valueProperty().get().equals(containsValueId)) {
                pathPredicate.setCurrentType(PathPredicate.TYPE.CONTAINS);
            }
            if (pathFilterTypeId.valueProperty().get().equals(endWithValueId)) {
                pathPredicate.setCurrentType(PathPredicate.TYPE.ENDWITH);
            }
            this.mainPredicate.add(pathPredicate);
        }
        fileListFiltered.setPredicate(fileListFiltered.getPredicate().and(mainPredicate));
    }

    private void initializeContextDependant() {
        TaskModel.TASK currentTask = mainModel.getTaskModel().getCurrentTask();
        switch (currentTask) {
            case DETECT_DOUBLONS:
                handleRowColor();
                break;
            case DETECT_MISSING:
                this.deleteDoublonsActionBoxId.setVisible(false);
                this.deleteDoublonsActionBoxId.setManaged(false);
                this.deleteDoublonsActionFromGroupId.setVisible(false);
                this.deleteDoublonsActionFromGroupId.setManaged(false);
                this.hideSingleFileContainerId.setManaged(false);
                this.hideSingleFileContainerId.setVisible(false);
                break;
        }
    }

    private void initializePhaseAutomaticResizing() {     
        splittedPanelId.prefHeightProperty().bind(mainContainerPanelID.heightProperty());
        splittedPanelId.prefWidthProperty().bind(mainContainerPanelID.widthProperty());
        resultViewId.prefHeightProperty().bind(splittedPanelId.heightProperty().subtract(2));

        resultViewId.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Double columnHashPositionWidth = 45.0;
                Double columnFileWidth = (resultViewId.getWidth() * 0.4) - 1;
                Double columnSizeWidth = (resultViewId.getWidth() * 0.2) - 23 - 1;
                Double columnGroupWidth = resultViewId.getWidth() * 0.15 - 22;
                Double md5PrintIdWidth = (resultViewId.getWidth() * 0.25) - 1;

                columnFile.setPrefWidth(columnFileWidth);
                columnSize.setPrefWidth(columnSizeWidth);
                columnGroup.setPrefWidth(columnGroupWidth);
                md5PrintId.setPrefWidth(md5PrintIdWidth);
                columnHashPosition.setPrefWidth(columnHashPositionWidth);
            }
        });

        SplitPane.setResizableWithParent(accordionPaneId, Boolean.FALSE);
    }

    private void handleRowColor() {
        resultViewId.getSortOrder().addListener(new ListChangeListener<TableColumn<GUIApplicationFile, ?>>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends TableColumn<GUIApplicationFile, ?>> c) {
                if (resultViewId.getSortOrder().size() > 0) {
                    TableColumn<GUIApplicationFile, ?> tableColumn = resultViewId.getSortOrder().get(0);
                    if (tableColumn.getId() != null && tableColumn.getId().equals("md5PrintId")) {
                        myRowFactory.setHigthLigthMode(true);
                    } else {
                        myRowFactory.setHigthLigthMode(false);
                    }
                }
            }
        });
    }

    @FXML
    private void backAction(ActionEvent event) throws Exception {
        /**
         * Due to the bug
         * "https://bitbucket.org/controlsfx/controlsfx/issue/185/nullpointerexception-when-using-popover"
         */
        if (popOver != null) {
            popOver.hide(Duration.millis(0));
        }
        /**
         * End *
         */
        MainApp app = MainApp.getInstance();
        mainModel.resetLocationsModel();
        mainModel.resetCritereModel();
        app.replaceSceneContent(Navigation.SEARCH_VIEW, WindowsPreferredDimensions.SEARCH_VIEW_WIDTH, WindowsPreferredDimensions.SEARCH_VIEW_HEIGHT);
    }

    @FXML
    private void deleteAllDoublonsAction(ActionEvent event) throws Exception {
        FetchAllDuplicateHavingACopyTask fetchAllDuplicateHavingACopyTask = SpringFxmlLoader.getBean(FetchAllDuplicateHavingACopyTask.class);
        fetchAllDuplicateHavingACopyTask.setGuiApplicationFileListToDelete(guiApplicationFileList);
        fetchAllDuplicateHavingACopyTask.checkArguments();
        this.delete(fetchAllDuplicateHavingACopyTask);
    }

    @FXML
    private void deleteDoublonsFromGroupAction(ActionEvent event) throws Exception {
        FetchDuplicateHavingCopyFromGroupTask fetchDuplicateHavingCopyFromGroupTask = SpringFxmlLoader.getBean(FetchDuplicateHavingCopyFromGroupTask.class);
        fetchDuplicateHavingCopyFromGroupTask.setGuiApplicationFileListToDelete(guiApplicationFileList);
        fetchDuplicateHavingCopyFromGroupTask.setTargetGroup(this.groupId.getValue().getValue());
        fetchDuplicateHavingCopyFromGroupTask.checkArguments();
        this.delete(fetchDuplicateHavingCopyFromGroupTask);
    }

    private void delete(AbstractFetchTask fetchTask) {
        PreSuppressionEventHandler preSuppressionEventHandler = SpringFxmlLoader.getBean(PreSuppressionEventHandler.class);
        preSuppressionEventHandler.setTableView(resultViewId);
        preSuppressionEventHandler.setSuppressOnlyOnSelected(false);
        preSuppressionEventHandler.setGuiApplicationFileList(guiApplicationFileList);
        fetchTask.setOnSucceeded(preSuppressionEventHandler);
        new Thread(fetchTask).start();

    }

    @Subscribe
    public void handleMouseOverRow(MouseEnteredRowEvent mouseEnteredRowEvent) {

        if (popOver != null) {
            popOver.hide();
        }
        if (mouseEnteredRowEvent.getRow().getItem() == null) {
            return;
        }
        ApplicationFile myFileFX = mouseEnteredRowEvent.getRow().getItem();
        String mimeType = null;
        try {
            mimeType = Files.probeContentType(new File(myFileFX.getAbsolutePath()).toPath());
        } catch (IOException ex) {
            Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mimeType != null && mimeType.startsWith("image/")) {
            try {
                ImageView imageView = new ImageView(new File(myFileFX.getAbsolutePath()).toURI().toURL().toString());
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                popOver = new PopOver();
                popOver.setContentNode(imageView);
                popOver.show(mouseEnteredRowEvent.getRow());
            } catch (MalformedURLException ex) {
                Logger.getLogger(ResultController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ObservableList getValues() {
        return guiApplicationFileList.get();
    }

    public void setValues(ObservableList value) {
        guiApplicationFileList.set(value);
    }

    public ListProperty valuesProperty() {
        return guiApplicationFileList;
    }

    public FilteredList<GUIApplicationFile> getValuesFiltered() {
        return guiApplicationFileListFiltered;
    }

    public void setValuesFiltered(FilteredList<GUIApplicationFile> valuesFiltered) {
        this.guiApplicationFileListFiltered = valuesFiltered;
    }

    @Autowired
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public TableView<GUIApplicationFile> getResultViewId() {
        return resultViewId;
    }

    public void setResultViewId(TableView<GUIApplicationFile> resultViewId) {
        this.resultViewId = resultViewId;
    }

}
