/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author pierre
 */
public class AudioExtensionFilter extends ExtensionFilter implements Filter<ApplicationFile> {
    	public AudioExtensionFilter(){
		super();
		Set<String> extensions = new HashSet<>();
		extensions.add("3gp");
		extensions.add("act");
		extensions.add("AIFF");
		extensions.add("aac");
		extensions.add("ALAC");
		extensions.add("amr");
		extensions.add("atrac");
		extensions.add("Au");
		extensions.add("awb");
		extensions.add("dct");
                extensions.add("dss");
                extensions.add("dvf");
                extensions.add("flac");
                extensions.add("gsm");
                extensions.add("iklax");
                extensions.add("IVS");
                extensions.add("m4a");
                extensions.add("m4p");
                extensions.add("mmf");
                extensions.add("mp3");
                extensions.add("mpc");
                extensions.add("msv");
                extensions.add("ogg");
                extensions.add("Opus");
                extensions.add("raw");
                extensions.add("TTA");
                extensions.add("vox");
                extensions.add("wav");
                extensions.add("wma");
                extensions.add("wavpack");
		this.setExtensionsToKeep(extensions);
	}
}
