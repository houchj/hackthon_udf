package com.sap.hackthon.framework.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class TypeResolver {

	public static synchronized Class<?> type2Class(Type type){
		if(ParameterizedType.class.isAssignableFrom(type.getClass())){
			return toGenericType((ParameterizedType)type);
		}
		if(TypeVariable.class.isAssignableFrom(type.getClass())){
			return type2Class(((TypeVariable<?>)type).getBounds()[0]);
		}
		return (Class<?>)type;
	}

	public static synchronized Type getParameterizedType(Type type, int index){
		if(ParameterizedType.class.isAssignableFrom(type.getClass())){
			return null;
		}
		Type[] acTypes = ((ParameterizedType)type).getActualTypeArguments();
		if(acTypes.length <= index){
			return null;
		}
		return acTypes[index];
	}
	
	private static synchronized Class<?> toGenericType(ParameterizedType parameterizedType){
		Object genericClass = parameterizedType.getActualTypeArguments()[0];     
		if (genericClass instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) genericClass).getRawType();     
		} else if (genericClass instanceof GenericArrayType) {
			return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();     
		} else if (genericClass instanceof TypeVariable) {     
			return (Class<?>) type2Class(((TypeVariable<?>) genericClass).getBounds()[0]);     
		}      
		return (Class<?>) genericClass;     
	}
}