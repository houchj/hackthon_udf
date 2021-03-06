package com.sap.hackthon.framework.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSettings {
	
	private Map<String, Object> settings = new HashMap<String, Object>();
	
	public UserSettings() {
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
