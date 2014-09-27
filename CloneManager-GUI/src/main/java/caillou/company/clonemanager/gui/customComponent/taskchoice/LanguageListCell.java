/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.taskchoice;

import caillou.company.clonemanager.gui.bean.Language;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 *
 * @author pierre
 */
public class LanguageListCell extends ListCell<Language> {

    private final ImageView view = new ImageView();

    public LanguageListCell(){
        view.setFitHeight(22);
        view.setFitWidth(34);
    }
    
    @Override
    protected void updateItem(Language language, boolean empty) {
        super.updateItem(language, empty);

        if (language == null || empty) {
            setGraphic(null);
            setText(null);
        } else {
            view.setImage(language.getFlag());
            setGraphic(view);
            setText(language.getTitle());
        }
    }

}
