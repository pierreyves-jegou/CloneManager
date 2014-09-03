/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.converter;

import javafx.util.StringConverter;

/**
 *
 * @author pierre
 */
public class ByteStringConverter extends StringConverter<Number> {

    @Override
    public String toString(Number number) {
        if(number.longValue() > 1000000000){
            return number.longValue() / 1000000000 + " Go";
        }
        else if(number.longValue() > 1000000){
            return number.longValue() / 1000000 + " Mo";
        }
        else if(number.longValue() > 1000){
            return number.longValue() / 1000 + " Ko";
        }else{
            return number.toString() + " O";
        }
    }

    @Override
    public Number fromString(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
