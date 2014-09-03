package caillou.company.clonemanager.background.bean.filter;

import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import caillou.company.clonemanager.background.exception.configuration.FilterConfigurationException;


public class SizeFilter implements Filter<ApplicationFile>{

	private Long minimumSize = null;
	private Long maximumSize = null;
	
	public SizeFilter(Long minimumSize, Long maximumSize) throws FilterConfigurationException{
		if(minimumSize != null && maximumSize != null){
			if(minimumSize.compareTo(maximumSize) > 0){
				throw new FilterConfigurationException("Parameter maximumSize has to be equals or greater than minimumSize");
			}
		}
		this.minimumSize = minimumSize;
		this.maximumSize = maximumSize;
	}
	
	@Override
	public boolean accept(ApplicationFile myFile) {
		
		// No criteria 
		if(this.minimumSize == null && this.maximumSize == null)
			return true;
			
		if(this.minimumSize != null && myFile.getSize().compareTo(this.minimumSize) < 0) {
			return false;
		}
		
		if(this.maximumSize != null && myFile.getSize().compareTo(this.maximumSize) > 0) {
			return false;
		}
		
		return true;
	}

	/**** Getters And Setters***/
	
	public Long getMinimumSize() {
		return minimumSize;
	}

	public void setMinimumSize(Long minimumSize) {
		this.minimumSize = minimumSize;
	}

	public Long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	
	

}
