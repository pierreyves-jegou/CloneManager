/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.statistic;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.Set;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * @author pierre
 */
@Aspect
@Component
public class StatisticAdvice {

    private StatisticToCompute statisticToCompute;

    @AfterReturning("execution(public void caillou.company.clonemanager.gui.service.task.impl.EnqueueMyFilesService.setMyFilesToTreat(..)) && args(myFilesToTreat)")
    public void handleFileToTreat(JoinPoint jp, Set<ApplicationFile> myFilesToTreat) {
        statisticToCompute.setMyFilesToTreat(myFilesToTreat);
    }

    public StatisticToCompute getStatisticToCompute() {
        return statisticToCompute;
    }

    public void setStatisticToCompute(StatisticToCompute statisticToCompute) {
        this.statisticToCompute = statisticToCompute;
    }
}
