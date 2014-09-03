package caillou.company.clonemanager.background.bean.filter;



import caillou.company.clonemanager.background.bean.applicationFile.contract.ApplicationFile;
import java.util.HashSet;
import java.util.Set;


public class FilterGroup implements Filter<ApplicationFile>{

	Set<Filter> filters = new HashSet<>();
		
	@Override
	public boolean accept(ApplicationFile myFile) {
		for(Filter filter : filters){
			if( ! filter.accept(myFile))
				return false;
		}
		return true;
	}

	public void addFilter(Filter filter){
		this.filters.add(filter);
	}
	
	public void removeFilter(Filter filter){
		if(this.filters.contains(filter)){
			this.filters.remove(filter);
		}
	}
}
