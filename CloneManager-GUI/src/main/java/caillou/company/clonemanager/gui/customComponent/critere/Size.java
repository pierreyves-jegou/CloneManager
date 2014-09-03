/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.critere;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class Size {
    private final LongProperty minimalSize = new SimpleLongProperty();
    private final LongProperty maximalSize = new SimpleLongProperty();

    public Long getMaximalSize() {
        return maximalSize.get();
    }

    public void setMaximalSize(Long value) {
        maximalSize.set(value);
    }

    public LongProperty maximalSizeProperty() {
        return maximalSize;
    }

    public Long getMinimalSize() {
        return minimalSize.get();
    }

    public void setMinimalSize(Long value) {
        minimalSize.set(value);
    }

    public LongProperty minimalSizeProperty() {
        return minimalSize;
    }
}
