/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.gui.bean.error;

import caillou.company.clonemanager.gui.spring.SpringFxmlLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pierre
 */
public class Error {
    
    private final StringProperty message = new SimpleStringProperty();
    private final IntegerProperty severity = new SimpleIntegerProperty();
    public static enum SEVERITY_LEVEL { NONE, INFO, WARNING, ERROR };
    public static enum TYPE {GENERAL, UNEXISTINGFILE, ALREADYDEFINEDFILEERROR, INCLUDEDFILEERROR, NOTADIRECTORYERROR};
    
    protected String getI18NMessage(String key){
        return SpringFxmlLoader.getResourceBundle().getString(key);
    }
    
    public Error(){   
    }
    
    public Error(String message, SEVERITY_LEVEL severityLevel){
        setMessage(message);
        switch(severityLevel){
            case INFO : setSeverity(1); break;
            case WARNING : setSeverity(2); break;
            case ERROR : setSeverity(3); break;
        }
    }
    
    public void reset(){
        setMessage("");
        setSeverity(SEVERITY_LEVEL.NONE);
    }
    
    public String getMessage() {
        return message.get();
    }

    final public void setMessage(String value) {
        message.set(value);
    }

    public StringProperty messageProperty() {
        return message;
    }
    
    public int getSeverity() {
        return severity.get();
    }
    
    public SEVERITY_LEVEL getSeverityLevel() {
        switch(this.severity.intValue()){
            case 0 : return SEVERITY_LEVEL.NONE;
            case 1 : return SEVERITY_LEVEL.INFO;
            case 2 : return SEVERITY_LEVEL.WARNING;    
            case 3 : return SEVERITY_LEVEL.ERROR;        
        }
        return null;
    }
    

    final public void setSeverity(int value) {
        severity.set(value);
    }
    
    final public void setSeverity(SEVERITY_LEVEL severityLevel) {
        switch(severityLevel){
            case INFO : setSeverity(1); break;
            case WARNING : setSeverity(2); break;
            case ERROR : setSeverity(3); break;
        }
    }
   
    public IntegerProperty severityProperty() {
        return severity;
    }    
    
    public TYPE getType(){
        return TYPE.GENERAL;
    }
    
}
