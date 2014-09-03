/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caillou.company.clonemanager.gui.predicate;

import caillou.company.clonemanager.gui.bean.applicationFileFX.contract.GUIApplicationFile;
import java.util.function.Predicate;

/**
 *
 * @author pierre
 */
public class AlonePredicate implements Predicate<GUIApplicationFile> {

    @Override
    public boolean test(GUIApplicationFile t) {
        return t == null || t.isAlone() == null || !t.isAlone();
    }

}
