package com.sap.hackthon.framework.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

import org.joda.time.DateTime;

import com.sap.hackthon.framework.enumeration.Prototype;

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
	
	public static synchronized boolean isPrototype(Class<?> type){
		try{
			toPrototype(type);
			return true;
		} catch (NoSuchElementException e){
			return false;
		}
	}
	
	public static synchronized Prototype toPrototype(Class<?> type){
		return Arrays.stream(Prototype.values()).filter(p -> p.getInnerClass() == type).findFirst().get();
	}
	
	public static synchronized Object convertToPrototype(Object raw, Prototype prototype){
		switch(prototype){
			case STRING:
				return raw.toString();
			case BOOLEAN:
				return toBoolean(raw);
			case SHORT:
				return toShort(raw);
			case INTEGER:
				return toInteger(raw);
			case LONG:
				return toLong(raw);
			case BIGINT:
				return toBigInteger(raw);
			case FLOAT:
				return toFloat(raw);
			case DOUBLE:
				return toDouble(raw);
			case CHARACTER:
				return toCharacter(raw);
			case DECIMAL:
				return toBigDecimal(raw);
			case TIMESTAMP:
				return toTimestamp(raw);
			case DATETIME:
				return toDateTime(raw);
			case DATE:
				return toDate(raw);
			default:
		}
		throw new IllegalArgumentException("Mismatched propertype type!");
	}
	
	private static synchronized Boolean toBoolean(Object val){
		if(val instanceof Boolean){
			return (Boolean)val;
		}
		if(val instanceof String){
			return Boolean.valueOf((String)val);
		}
		return null;
	}
	
	private static synchronized Short toShort(Object val){
		if(val instanceof Short){
			return (Short)val;
		}
		if(val instanceof String){
			return Short.valueOf((String)val);
		}
		if(val instanceof Number){
			return ((Number)val).shortValue();
		}
		return null;
	}
	
	private static synchronized Integer toInteger(Object val){
		if(val instanceof Integer){
			return (Integer)val;
		}
		if(val instanceof String){
			return Integer.valueOf((String)val);
		}
		if(val instanceof Number){
			return ((Number)val).intValue();
		}
		return null;
	}
	
	private static synchronized Long toLong(Object val){
		if(val instanceof Long){
			return (Long)val;
		}
		if(val instanceof String){
			return Long.valueOf((String)val);
		}
		if(val instanceof Number){
			return ((Number)val).longValue();
		}
		return null;
	}
	
	private static synchronized BigInteger toBigInteger(Object val){
		if(val instanceof BigInteger){
			return (BigInteger)val;
		}
		if(val instanceof Long){
			return BigInteger.valueOf((Long)val);
		}
		if(val instanceof String){
			return BigInteger.valueOf(Long.valueOf((String)val));
		}
		if(val instanceof BigDecimal){
			((BigDecimal)val).toBigInteger();
		}
		return null;
	}
	
	private static synchronized Float toFloat(Object val){
		if(val instanceof Float){
			return (Float)val;
		}
		if(val instanceof String){
			return Float.valueOf((String)val);
		}
		if(val instanceof Number){
			return ((Number)val).floatValue();
		}
		return null;
	}
	
	private static synchronized Double toDouble(Object val){
		if(val instanceof Double){
			return (Double)val;
		}
		if(val instanceof String){
			return Double.valueOf((String)val);
		}
		if(val instanceof Number){
			return ((Number)val).doubleValue();
		}
		return null;
	}
	
	private static synchronized Character toCharacter(Object val){
		if(val instanceof Character){
			return (Character)val;
		}
		return null;
	}
	
	private static synchronized BigDecimal toBigDecimal(Object val){
		if(val instanceof BigDecimal){
			return (BigDecimal)val;
		}
		if(val instanceof Number){
			return BigDecimal.valueOf(((Number)val).doubleValue());
		}
		if(val instanceof String){
			return new BigDecimal((String)val);
		}
		return null;
	}
	
	private static synchronized Timestamp toTimestamp(Object val){
		if(val instanceof Timestamp){
			return (Timestamp)val;
		}
		if(val instanceof String){
			return Timestamp.valueOf((String)val);
		}
		if(val instanceof LocalDateTime){
			return Timestamp.valueOf((LocalDateTime)val);
		}
		return null;
	}
	
	private static synchronized DateTime toDateTime(Object val){
		if(val instanceof DateTime){
			return (DateTime)val;
		}
		if(val instanceof String){
			return DateTime.parse((String)val);
		}
		return null;
	}
	
	private static synchronized Date toDate(Object val){
		if(val instanceof Date){
			return (Date)val;
		}
		if(val instanceof String){
			try {
				return GlobalConstants.defaultDateFormat.parse((String)val);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Invalid date format:", e);
			}
		}
		return null;
	}
}