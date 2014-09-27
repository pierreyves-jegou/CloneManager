/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.bean;

import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

/**
 *
 * @author pierre
 */
public class Language {
    
    private final String title;
    
    private final Image flag;
    
    private final Locale locale;
    
    public Language(Locale locale){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle.Main", locale);
        this.title = resourceBundle.getString("language");
        this.flag = new Image(resourceBundle.getString("flag"));
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public Image getFlag() {
        return flag;
    }

    public Locale getLocale() {
        return locale;
    }
   
}
