package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.HashSet;
import java.util.Set;

public class ImageExtensionFilter extends ExtensionFilter implements Filter<ApplicationFile> {
	
	public ImageExtensionFilter(){
		super();
		Set<String> extensions = new HashSet<>();
		extensions.add("jpeg");
                extensions.add("jpg");
		extensions.add("jfif");
		extensions.add("tiff");
		extensions.add("raw");
		extensions.add("gif");
		extensions.add("bmp");
		extensions.add("png");
		extensions.add("ppm");
		extensions.add("pgm");
		extensions.add("pbm");
                extensions.add("pnm");
                extensions.add("webp");
                extensions.add("svg");
                extensions.add("ico");
                extensions.add("ciff");
                extensions.add("dng");
		this.setExtensionsToKeep(extensions);
	}

}
