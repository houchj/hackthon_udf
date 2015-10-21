package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(WebApplicationContext.SCOPE_GLOBAL_SESSION)
public class GlobalSettings {
	
	private Map<String, Object> settings = new HashMap<String, Object>();
	
	public GlobalSettings() {
		super();
	}

	public void setVariable(String name, Object value){
		settings.put(name, value);
	}

	public Object getVariable(String name){
		return settings.get(name);
	}
	
	public Stream<Entry<String, Object>> getVariables(){
		return settings.entrySet().parallelStream();
	}
}
