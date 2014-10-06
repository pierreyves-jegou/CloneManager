/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.spring;

import caillou.company.clonemanager.background.bean.impl.Group;
import caillou.company.clonemanager.gui.MainApp;
import caillou.company.clonemanager.gui.Navigation;
import caillou.company.clonemanager.gui.bean.impl.LoadingMojo;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFxmlLoader {

    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Navigation.SPRING_APPLICATION_FILE);
    private static ResourceBundle resourceBundle;
    private static Locale currentLocale = Locale.ENGLISH;
    private static String lastLoadedURL = null;

    public SpringFxmlLoader() {
        resourceBundle = ResourceBundle.getBundle("bundle.Main", SpringFxmlLoader.currentLocale);
        changeLocale(currentLocale);
    }

    public static LoadingMojo load(String url) {
        return load(url, null);
    }

    public static LoadingMojo load(String url, final String controllerClassName) {

        lastLoadedURL = url;
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(url), resourceBundle);
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> clazz) {
                if (controllerClassName != null) {
                    try {
                        return applicationContext.getBean(Class.forName(controllerClassName));
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SpringFxmlLoader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return applicationContext.getBean(clazz);
            }
        });

        try {
            Parent parent = loader.load();
            LoadingMojo loadingMojo = new LoadingMojo();
            loadingMojo.setController(loader.getController());
            loadingMojo.setParent(parent);
            return loadingMojo;
        } catch (IOException ex) {
            Logger.getLogger(SpringFxmlLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception exMain){
            System.out.println("Error");
        }
        return null;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        if (name == null) {
            return applicationContext.getBean(requiredType);
        } else {
            return applicationContext.getBean(name, requiredType);
        }
    }

    public static void changeLocale(Locale locale) {
        SpringFxmlLoader.currentLocale = locale;
        Group.GROUPA.setGuiValue(resourceBundle.getString("group1"));
        Group.GROUPB.setGuiValue(resourceBundle.getString("group2"));
        resourceBundle = ResourceBundle.getBundle("bundle.Main", SpringFxmlLoader.currentLocale);
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static String getLastLoadedURL() {
        return lastLoadedURL;
    }

}
