package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.springframework.context.annotation.Scope;

@Scope("global session")
public class GlobalSettings {
	
	private Map<String, Object> settings = new HashMap<String, Object>();
	
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
