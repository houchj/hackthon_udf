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
		super.writeInternal(object, outputMessage);
	}
	
	

	@Override
	@SuppressWarnings("unchecked")
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		return quenchTo(objectMapper.readValue(inputMessage.getBody(), Map.class), type);
	}

	private Object quenchTo(Map<String, Object> raw, Type type) throws JsonConvertException{
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
			Object pv = getProperty(p, v);
			try {
				entity.setProperty(p, pv);
			} catch (Exception e) {/* No operation */}
		});
		return entity;
	}

	private Object quenchToObject(Map<String, Object> raw, Class<?> type){
		Object obj;
		try {
			obj = type.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new JsonConvertException("No default constructer found", e);
		} 
		raw.forEach((p, v) -> {
			Object pv = getProperty(p, v);
			try {
				PropertyUtils.setProperty(obj, p, pv);
			} catch (Exception e) {/* No operation */}
		});
		return obj;
	}


	private Object getProperty(String pro, Object bean){
		PropertyDescriptor desc = null;
		try {
			desc = PropertyUtils.getPropertyDescriptor(bean, pro);
		} catch (Exception e1) {
			return  null;
		}
		Class<?> pType = desc.getPropertyType();
		if(Collection.class.isAssignableFrom(pType)){
			return getCollectionProperty(bean);
		}
		if(Map.class.isAssignableFrom(pType) ){
			Type keyType = TypeResolver.getParameterizedType(pType, 0);
			if(keyType == null || 
					((keyType instanceof Class) && String.class.isAssignableFrom((Class<?>)keyType))){
				return getMapProperty(bean);
			}
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	private Object getCollectionProperty(Object bean){
		Collection<Object> ctn = new ArrayList<Object>();
		((Collection<Object>)bean).stream().forEach(it -> {
			Object pi = it;
			if(Map.class.isAssignableFrom(it.getClass())){
				pi = quenchTo((Map<String, Object>)pi, pi.getClass());
			} 
			ctn.add(pi);
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


}
