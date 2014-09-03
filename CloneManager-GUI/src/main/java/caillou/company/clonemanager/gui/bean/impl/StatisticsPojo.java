/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.bean.impl;

/**
 *
 * @author pierre
 */
public class StatisticsPojo {

    private Long nbScannedFiles;

    private Long bytesToTreat;

    public Long getNbScannedFiles() {
        return nbScannedFiles;
    }

    public void setNbScannedFiles(Long nbScannedFiles) {
        this.nbScannedFiles = nbScannedFiles;
    }

    public Long getBytesToTreat() {
        return bytesToTreat;
    }

    public void setBytesToTreat(Long bytesToTreat) {
        this.bytesToTreat = bytesToTreat;
    }

}
