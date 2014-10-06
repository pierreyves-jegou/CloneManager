package caillou.company.clonemanager.gui;

import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class MainApp extends Application {

     private Stage stage;

    private static MainApp instance;
    
    // TODO : Strange problem when using 'SpringFxmlLoader' statically here
    private SpringFxmlLoader springFxmlLoader;

    private Double posX;
    private Double posY;

    public static double LITTLE_WINDOWS_WIDTH = 640;
    public static double LITTLE_WINDOWS_HEIGTH = 480;
    public static double LARGE_WINDOWS_WIDTH = 800;
    public static double LARGE_WINDOWS_HEIGTH = 600;

    public MainApp() {
        instance = this;
        springFxmlLoader = new SpringFxmlLoader();
    }

    public static MainApp getInstance() {
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {            
//            Font.loadFont(ClassLoader.getSystemResource("fonts/Assassin.ttf").toExternalForm(), 10);
                        
            stage = primaryStage;
            stage.setTitle("Clone Manager");
            
            replaceSceneContent(Navigation.TASK_CHOICE_VIEW, WindowsPreferredDimensions.TASKCHOICE_VIEW_WIDTH, WindowsPreferredDimensions.TASKCHOICE_VIEW_HEIGHT);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            posX = (primScreenBounds.getWidth() - stage.getWidth()) / 2;
            posY = (primScreenBounds.getHeight() - stage.getHeight()) / 4;
            stage.setX(posX);
            stage.setY(posY);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Parent buildScene(Parent page, Double width, Double heigth) {
        stage.hide();

        Scene scene = null;
        if (width == null || heigth == null) {
            scene = new Scene(page);
        } else {
            scene = new Scene(page, width, heigth);
        }

        stage.setScene(scene);
        stage.getScene().setRoot(page);
        stage.sizeToScene();

        stage.show();
        return page;
    }

    public Parent replaceSceneContent(String fxml, Double width, Double heigth) {
        LoadingMojo loadingMojo = springFxmlLoader.load(fxml);
        return buildScene(loadingMojo.getParent(), width, heigth);
    }

    public Stage getStage() {
        return stage;
    }

}
