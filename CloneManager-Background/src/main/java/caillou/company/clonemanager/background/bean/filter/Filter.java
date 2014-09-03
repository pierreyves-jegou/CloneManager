package caillou.company.clonemanager.background.bean.filter;

public interface Filter<T> {

	public boolean accept(T myFile);
	
}
