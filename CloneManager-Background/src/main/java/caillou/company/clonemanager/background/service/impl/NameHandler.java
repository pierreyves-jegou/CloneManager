package caillou.company.clonemanager.background.service.impl;

public class NameHandler {

	public static String getRoughlyName(String name){
		return name.replaceAll("[\\W[_-]]", " ").replaceAll(" +", " ");
	}

}
