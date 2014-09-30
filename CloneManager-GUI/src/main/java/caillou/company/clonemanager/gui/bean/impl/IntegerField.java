/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.bean.impl;

// helper text field subclass which restricts text input to a given range of natural int numbers
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// and exposes the current numeric int value of the edit box as a value property.
public class IntegerField extends TextField {

    private IntegerProperty value = new SimpleIntegerProperty();
    ;
    private Integer minValue = null;
    private Integer maxValue = null;
    private Integer initialValue = null;

    public IntegerField() {
        this.intializeHandlers();
    }

    public IntegerField(int minValue, int maxValue, int initialValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException(
                    "IntField min value " + minValue + " greater than max value " + maxValue
            );
        }
        if (maxValue < minValue) {
            throw new IllegalArgumentException(
                    "IntField max value " + minValue + " less than min value " + maxValue
            );
        }
        if (!((minValue <= initialValue) && (initialValue <= maxValue))) {
            throw new IllegalArgumentException(
                    "IntField initialValue " + initialValue + " not between " + minValue + " and " + maxValue
            );
        }

        // initialize the field values.
        this.minValue = minValue;
        this.maxValue = maxValue;
        value = new SimpleIntegerProperty(initialValue);
        setText(initialValue + "");
        this.intializeHandlers();
    }

    private void intializeHandlers(){
        final IntegerField intField = this;
        if(value == null){
            value = new SimpleIntegerProperty(0);
        }
        if(this.minValue == null){
            this.minValue = Integer.MIN_VALUE;
        }
        if(this.maxValue == null){
            this.maxValue = Integer.MAX_VALUE;
        }
        
        // make sure the value property is clamped to the required range
        // and update the field's text to be in sync with the value.
        value.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    intField.setText("");
                } else {
                    if (intField.minValue != null && newValue.intValue() < intField.minValue) {
                        value.setValue(intField.minValue);
                        return;
                    }
                    if (intField.maxValue != null && newValue.intValue() > intField.maxValue) {
                        value.setValue(intField.maxValue);
                        return;
                    }

                    if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
                        // no action required, text property is already blank, we don't need to set it to 0.
                    } else {
                        intField.setText(newValue.toString());
                    }
                }
            }
        });

        // restrict key input to numerals.
        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        });

        // ensure any entered values lie inside the required range.
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue == null || "".equals(newValue)) {
                    value.setValue(0);
                    return;
                }

                final int intValue = Integer.parseInt(newValue);

                if(intField.minValue != null && intField.minValue > intValue){
                    textProperty().setValue(oldValue);
                }
                
                if (intField.maxValue != null && intValue > intField.maxValue) {
                    textProperty().setValue(oldValue);
                }

                value.set(Integer.parseInt(textProperty().get()));
            }
        });
    }
    
    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Integer initialValue) {
        this.initialValue = initialValue;
    }

    // expose an integer value property for the text field.
    public int getValue() {
        return value.getValue();
    }

    public void setValue(int newValue) {
        value.setValue(newValue);
    }

    public IntegerProperty valueProperty() {
        return value;
    }
}
