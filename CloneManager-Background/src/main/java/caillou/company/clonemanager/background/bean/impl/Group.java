/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.background.bean.impl;

public class Group {

    public static Group GROUP1 = new Group(VALUE.GROUP1);
    public static Group GROUP2 = new Group(VALUE.GROUP2);

    public enum VALUE {
        GROUP1, GROUP2
    };

    private String guiValue;
    private VALUE value;

    public Group() {
    }

    public Group(VALUE value) {
        this.value = value;
    }

    public Group(VALUE value, String guiValue) {
        this.value = value;
        this.guiValue = guiValue;
    }

    public void setGuiValue(String guiValue) {
        this.guiValue = guiValue;
    }

    public String getGuiValue() {
        return guiValue;
    }

    public VALUE getValue() {
        return value;
    }
    
    @Override
    public String toString(){
        return this.getGuiValue();
    }
    
    public static Group.VALUE getTheOtherGroup(Group.VALUE value) {
        if (value.equals(Group.VALUE.GROUP1)) {
            return Group.VALUE.GROUP2;
        }
        if (value.equals(Group.VALUE.GROUP2)) {
            return Group.VALUE.GROUP1;
        }
        return null;
    }
}
