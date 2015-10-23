package com.sap.hackthon.framework.converter;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import com.sap.hackthon.framework.utils.CommonUtils;
import com.sap.hackthon.framework.utils.GlobalConstants;

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
		super.writeInternal(depositeFrom(object), outputMessage);
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(type, contextClass);
		Class<?> rawCls = javaType.getRawClass();
		rawCls = Collection.class.isAssignableFrom(rawCls) ? Collection.class : Map.class;
		return quenchTo(objectMapper.readValue(inputMessage.getBody(), rawCls), javaType);
	}

	@SuppressWarnings("unchecked")
	protected Object quenchTo(Object raw, JavaType javaType) throws JsonConvertException{
		if(raw == null){
			return null;
		}
		if(javaType == null){
			javaType = getJavaType(Map.class, null);
		}
		if(javaType.isArrayType()){
			return quenchToArray((Map<String, Object>[])raw, javaType);
		}
		if(javaType.isCollectionLikeType()){
			return quenchToCollection((Collection<Map<String, Object>>)raw, javaType);
		}
		if(javaType.isMapLikeType()){
			return quenchToMap((Map<String, Object>)raw, javaType);
		}
		if(javaType.isEnumType()){
			return quenchToEnum((String)raw, javaType);
		}
		if(javaType.isPrimitive()){
			return raw;
		}
		Class<?> rawCls = javaType.getRawClass();
		if(BasicEntity.class.isAssignableFrom(rawCls)){
			return quenchToBasicEntity((Map<String, Object>)raw, javaType);
		}
		return quenchToObject((Map<String, Object>)raw, javaType);
	}
	
	@SuppressWarnings("unchecked")
	protected Object depositeFrom(Object pole){
		if(pole == null){
			return null;
		}
		Class<?> pCls = pole.getClass();
		JavaType javaType = getJavaType(pCls, null);
		if(javaType == null){
			javaType = getJavaType(Map.class, null);
		}
		if(javaType.isArrayType()){
			return depositeFromArray((Object[]) pole);
		}
		if(javaType.isCollectionLikeType()){
			return depositeFromCollection((Collection<Object>) pole);
		}
		if(javaType.isMapLikeType()){
			return depositeFromMap((Map<String, Object>) pole);
		}
		if(javaType.isEnumType()){
			return pole;
		}
		if(javaType.isPrimitive()){
			return pole;
		}
		Class<?> rawCls = javaType.getRawClass();
		if(BasicEntity.class.isAssignableFrom(rawCls)){
			return depositeFromBasicEntity((BasicEntity) pole);
		}
		return depositeFromObject(pole);
	}

	private Object quenchToCollection(Collection<Map<String, Object>> raw, JavaType javaType){
		Collection<Object> container = newCollection(javaType);
		JavaType contentType = javaType.getContentType();
		raw.forEach(m -> container.add(quenchTo(m, contentType)));
		return container;
	}

	@SuppressWarnings("unchecked")
	private Collection<Object> newCollection(JavaType javaType){
		Class<? extends Collection<?>> rawCls = (Class<? extends Collection<?>>) javaType.getRawClass(); 
		if(javaType.isConcrete()){
			try {
				return (Collection<Object>) rawCls.newInstance();
			} catch (Exception e) {
				return CommonUtils.getCollectionInstance(rawCls);
			} 
		} 
		return CommonUtils.getCollectionInstance(rawCls);
	}

	private Object quenchToArray(Map<String, Object>[] raw, JavaType javaType){
		JavaType contentType = javaType.getContentType();
		Class<?> contentCls = Map.class;
		if(contentType != null){
			contentCls = contentType.getRawClass();
		}
		Object container = Array.newInstance(contentCls, raw.length);
		for(int i = 0 ; i < raw.length ; i++ ){
			Array.set(container, i, quenchTo(raw[i], contentType));
		}
		return container;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object quenchToEnum(String raw, JavaType javaType){
		return Enum.valueOf((Class<Enum>) javaType.getRawClass(), raw);
	}

	private Object quenchToMap(Map<String, Object> raw, JavaType javaType){
		Map<String, Object> container = newMap(javaType);
		JavaType contentType = javaType.getContentType();
		raw.forEach((k, v) -> {
			container.put(k, quenchTo(v, contentType));
		});
		return container;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> newMap(JavaType javaType){
		Class<? extends Collection<?>> rawCls = (Class<? extends Collection<?>>) javaType.getRawClass(); 
		if(javaType.isConcrete()){
			try {
				return (Map<String, Object>) rawCls.newInstance();
			} catch (Exception e) {
				return new LinkedHashMap<String, Object>();
			} 
		} 
		return new LinkedHashMap<String, Object>();
	}

	private Object quenchToObject(Map<String, Object> raw, JavaType javaType){
		Object obj;
		try {
			obj = javaType.getRawClass().getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new JsonConvertException("No default constructer found", e);
		} 
		raw.forEach((p, v) -> {
			PropertyDescriptor desc = null;
			try {
				desc = PropertyUtils.getPropertyDescriptor(obj, p);
				JavaType pType = getJavaType(desc.getPropertyType(), null);
				PropertyUtils.setProperty(obj, p, quenchTo(v, pType));
			} catch (Exception e1) {
				return;
			}
		});
		return obj;
	}

	private Object quenchToBasicEntity(Map<String, Object> raw, JavaType javaType){
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
				JavaType pType = getJavaType(desc.getPropertyType(), null);
				entity.setProperty(p, quenchTo(v, pType));
			} catch (Exception e1) {
				return;
			}
		});
		return entity;
	}
	
	private List<Object> depositeFromArray(Object[] pole){
		List<Object> wrapper = new LinkedList<Object>();
		for(Object item : pole){
			wrapper.add(depositeFrom(item));
		}
		return wrapper;
	}
	
	private List<Object> depositeFromCollection(Collection<Object> pole){
		return pole.stream().map(s -> depositeFrom(s)).collect(Collectors.toList());
	}
	
	private Map<String, Object> depositeFromMap(Map<String, Object> pole){
		return pole.entrySet().stream().collect(Collectors.toMap(m -> m.getKey(), m -> depositeFrom(m.getValue())));
	}
	
	private Map<String, Object> depositeFromBasicEntity(BasicEntity pole){
		Map<String, Object> properties = pole.getProperties();
		return properties.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), r -> depositeFrom(r.getValue())));
	}
	
	private Map<String, Object> depositeFromObject(Object pole){
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(pole);
		Map<String, Object> wrapper = new LinkedHashMap<String, Object>();
		for(PropertyDescriptor descriptor: descriptors){
			String pro = descriptor.getName();
			try {
				wrapper.put(pro, depositeFrom(PropertyUtils.getProperty(pole, pro)));
			} catch (Exception e) {
				/* Never reach here*/
			} 
		}
		return wrapper;
	}
}
