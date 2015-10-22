package com.sap.hackthon.framework.converter;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.metamodel.EntityType;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.JavaType;
import com.sap.hackthon.framework.beans.BasicEntity;
import com.sap.hackthon.framework.exception.JsonConvertException;
import com.sap.hackthon.framework.mata.MetaInfoRetriever;
import com.sap.hackthon.framework.utils.GlobalConstants;
import com.sap.hackthon.framework.utils.TypeResolver;

public class MappingJson2DynamicEntityConverter extends MappingJackson2HttpMessageConverter {

	@Autowired
	private MetaInfoRetriever metaInfo;

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		return read(clazz, null, inputMessage);
	}
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		super.writeInternal(mapFrom(object), outputMessage);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		return quenchTo(objectMapper.readValue(inputMessage.getBody(), Map.class), type);
	}

	protected Object quenchTo(Map<String, Object> raw, Type type) throws JsonConvertException{
		if(type == null || raw == null){
			return null;
		}
		JavaType javaType = getJavaType(type, null);
		Class<?> rawCls = javaType.getRawClass();
		if(BasicEntity.class.isAssignableFrom(rawCls)){
			return quenchToBasic(raw);
		}
		return quenchToObject(raw, rawCls);
	}
	
	protected Map<String, Object> mapFrom(Object obj){
		if(obj == null){
			return null;
		}
		if(obj instanceof BasicEntity){
			return mapFromBasic(obj);
		}
		return mapFromObject(obj);
	}

	private Object quenchToBasic(Map<String, Object> raw) throws JsonConvertException{
		Object objectType = raw.get(GlobalConstants.OBJECT_TYPE);
		if(objectType == null){
			throw new JsonConvertException("Not a valid basic entity type!");
		}
		EntityType<? extends BasicEntity> entityType = metaInfo.retrieveEntityType(objectType.toString());
		Class<? extends BasicEntity> eCls = entityType.getJavaType();
		BasicEntity entity;
		try {
			entity = eCls.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new JsonConvertException("No default constructer found", e);
		} 
		raw.forEach((p, v) -> {
			PropertyDescriptor desc = null;
			try {
				desc = PropertyUtils.getPropertyDescriptor(entity, p);
			} catch (Exception e1) {
				return;
			}
			Class<?> pType = desc.getPropertyType();
			Object pv = getProperty(v, pType);
			try {
				entity.setProperty(p, pv);
			} catch (Exception e) {/* No operation */}
		});
		return entity;
	}
	
	private Map<String, Object> mapFromBasic(Object obj){
		BasicEntity entity = (BasicEntity) obj;
		Map<String, Object> properties = entity.getProperties();
		return properties.entrySet().stream().collect(Collectors.toMap( s -> s.getKey(), u -> {
			Object v = u.getValue();
			return v;
		}));
	}

	private Object quenchToObject(Map<String, Object> raw, Class<?> type){
		Object obj;
		try {
			obj = type.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new JsonConvertException("No default constructer found", e);
		} 
		raw.forEach((p, v) -> {
			PropertyDescriptor desc = null;
			try {
				desc = PropertyUtils.getPropertyDescriptor(obj, p);
			} catch (Exception e1) {
				return;
			}
			Class<?> pType = desc.getPropertyType();
			Object pv = getProperty(v, pType);
			try {
				PropertyUtils.setProperty(obj, p, pv);
			} catch (Exception e) {/* No operation */}
		});
		return obj;
	}
	
	private Map<String, Object> mapFromObject(Object obj){
		return null;
	}


	@SuppressWarnings("unchecked")
	private Object getProperty(Object bean, Class<?> type){
		
		if(Collection.class.isAssignableFrom(type)){
			return getCollectionProperty(bean);
		}
		if(Map.class.isAssignableFrom(type) ){
			Type keyType = TypeResolver.getParameterizedType(type, 0);
			if(keyType == null || 
					((keyType instanceof Class) && String.class.isAssignableFrom((Class<?>)keyType))){
				return quenchTo((Map<String, Object>)bean, type);
			}
		}
		return bean;
	}
	
	private Object mapProperty(Object obj){
		if(obj instanceof Collection){
			return mapCollectionProperty(obj);
		}
		if(obj instanceof Map){
			return mapMappedProperty(obj);
		}
		return mapFrom(obj);
	}

	@SuppressWarnings("unchecked")
	private Object getCollectionProperty(Object bean){
		Collection<Object> ctn = new ArrayList<Object>();
		((Collection<Object>)bean).stream().forEach(it -> {
			if(it == null){
				return;
			}
			ctn.add(getProperty(it, it.getClass()));
		});
		return ctn;
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Map<String, Object>> mapCollectionProperty(Object obj){
		Collection<Map<String, Object>> ctn = new ArrayList<Map<String, Object>>();
		((Collection<Map<String, Object>>)obj).stream().forEach(it -> {
			ctn.add(mapFrom(it));
		});
		return ctn;
	}

	@SuppressWarnings("unchecked")
	private Object getMapProperty(Object bean){
		return ((Map<String, Object>)bean).entrySet().stream().collect(Collectors.toMap(s->s.getKey(), u -> {
			Object pi = u.getValue();
			if(pi == null){
				return null;
			}
			Class<?> pType = pi.getClass();
			if(Map.class.isAssignableFrom(pType)){
				pi = getMapProperty(pi);
			} else if(Collection.class.isAssignableFrom(pType)){
				pi = getCollectionProperty(pi);
			}
			return pi;
		}));
	}

	@SuppressWarnings("unchecked")
	private Object mapMappedProperty(Object obj){
		return ((Map<String, Object>)obj).entrySet().stream().collect(Collectors.toMap(s->s.getKey(), u -> {
			Object pi = u.getValue();
			if(pi instanceof Map){
				pi = mapMappedProperty(pi);
			} else if(pi instanceof Collection){
				pi = mapCollectionProperty(pi);
			}
			return pi;
		}));
	}

}
