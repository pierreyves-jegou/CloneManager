/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.customComponent.results;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pierre
 */
public class GUIApplicationFileUtil {

    public static GUIApplicationFile isolateSingleFile(List<GUIApplicationFile> filesWithinView, GUIApplicationFile removedFromView) {
        if (removedFromView == null || removedFromView.getMD5Print() == null) {
            return null;
        }
        List<GUIApplicationFile> sameMD5Files = new ArrayList<>();
        for(GUIApplicationFile fileWithinView : filesWithinView){
            if(!removedFromView.getAbsolutePath().equals(fileWithinView.getAbsolutePath()) && fileWithinView.getMD5Print() != null){
                 if(fileWithinView.getMD5Print().equals(removedFromView.getMD5Print())){
                     sameMD5Files.add(fileWithinView);
                 }              
            }
        }
        if(sameMD5Files.size() == 1){
            sameMD5Files.get(0).setAlone(Boolean.TRUE);
            return sameMD5Files.get(0);
        }
        return null;
    }

}
