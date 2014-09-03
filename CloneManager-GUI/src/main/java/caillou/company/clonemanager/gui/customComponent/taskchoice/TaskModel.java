/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.taskchoice;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Component
@Scope(value = "prototype")
public class TaskModel {

    public static enum TASK { DETECT_DOUBLONS, DETECT_MISSING, CONNECT_SLAVE };
    
    private TASK currentTask;

    public TASK getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(TASK currentTask) {
        this.currentTask = currentTask;
    }    
}
