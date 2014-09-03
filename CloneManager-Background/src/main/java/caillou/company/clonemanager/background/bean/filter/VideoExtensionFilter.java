package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.HashSet;
import java.util.Set;

public class VideoExtensionFilter extends ExtensionFilter implements Filter<ApplicationFile> {
	
	public VideoExtensionFilter(){
		super();
		Set<String> extensions = new HashSet<>();
		extensions.add("mkv");
		extensions.add("wrap");
		extensions.add("mov");
		extensions.add("mpeg");
		extensions.add("mpg");
		extensions.add("mpe");
		extensions.add("avi");
		extensions.add("divx");
		extensions.add("vob");
		extensions.add("wmv");
		this.setExtensionsToKeep(extensions);
	}

}
