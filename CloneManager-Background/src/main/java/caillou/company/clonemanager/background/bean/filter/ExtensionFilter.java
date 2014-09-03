package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.HashSet;
import java.util.Set;


public class ExtensionFilter implements Filter<ApplicationFile>{

	private Set<String> extensionsToKeep = new HashSet<>();
	private static String DOT = ".";
	
	public ExtensionFilter(String ... extensions){
		if(extensions != null){
			for (String extension : extensions) {
				extensionsToKeep.add(extension);
			}
		}
	}
	
	public ExtensionFilter(Set<String> extensionsToKeep){
		this.extensionsToKeep = extensionsToKeep;
	}

	@Override
	public boolean accept(ApplicationFile pMyFile) {
		if(pMyFile != null){
			for(String extension : extensionsToKeep){
				if(pMyFile.getBaseNamePath().toLowerCase().endsWith(DOT + extension.toLowerCase())){
					return true;
				}
			}
		}
		return false;
	}

	public Set<String> getExtensionsToKeep() {
		return extensionsToKeep;
	}

	public void setExtensionsToKeep(Set<String> pExtensionsToKeep) {
		extensionsToKeep = pExtensionsToKeep;
	}

}
