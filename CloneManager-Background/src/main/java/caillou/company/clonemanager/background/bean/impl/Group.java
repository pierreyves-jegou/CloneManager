/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.bean.impl;

public class Group {
        public static String GROUP1 = "Groupe 1";
        public static String GROUP2 = "Groupe 2";
        
        public static String getTheOtherGroup(String group){
            if(group.equals(GROUP1)){
                return GROUP2;
            }
            if(group.equals(GROUP2)){
                return GROUP1;
            }
            return null;
        }
}
