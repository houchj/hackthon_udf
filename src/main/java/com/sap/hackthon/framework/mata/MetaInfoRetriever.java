package com.sap.hackthon.framework.mata;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.framework.beans.BasicEntity;
import com.sap.hackthon.framework.beans.VersionObserver;
import com.sap.hackthon.framework.utils.GlobalConstants;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MetaInfoRetriever {

	@Autowired
	private EntityManager entityManager;

	private VersionObserver metaVersion;
	
	private Map<String, EntityType<?>> eCache;

	private Map<String, PropertyMeta> pCache;

	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		if(eCache == null){
			eCache = entityManager.getEntityManagerFactory().getMetamodel().getEntities().stream()
					.filter(s -> BasicEntity.class.isAssignableFrom(s.getJavaType()))
					.collect(Collectors.toConcurrentMap(t -> t.getName(), u -> u));
		}
		return (EntityType<T>) eCache.get(objectType);
	}

	public <T extends BasicEntity> String retrieveTableName(String objectType){
		EntityType<T> entityType = retrieveEntityType(objectType);
		Class<T> eCls = entityType.getJavaType();
		Table table = eCls.getAnnotation(Table.class);
		if(table == null || table.name().isEmpty()){
			return eCls.getSimpleName();
		}
		return table.name();
	}

	public List<PropertyMeta> retrievePropertyMetas(){
		VersionObserver metaVersionInDB = versionOfPropertyMeta();
		if(metaVersion == null || pCache == null || 
				(!metaVersionInDB.getVersion().equals(metaVersion.getVersion()))){
			refreshMetaCache();
		}
		metaVersion = metaVersionInDB;
		List<PropertyMeta> clone = new Vector<PropertyMeta>();
		clone.addAll(pCache.values());
		return clone;
	}
	
	public PropertyMeta retrievePropertyMeta(String name){
		return pCache.get(name);
	}
	
	private void refreshMetaCache(){
		String jp = "SELECT pm FROM " + PropertyMeta.class.getName() + " pm";
		List<PropertyMeta> propertyMetas = entityManager.createQuery(jp, PropertyMeta.class).getResultList();
		if(propertyMetas == null){
			pCache = new ConcurrentHashMap<String, PropertyMeta>();
			return;
		}
		pCache = propertyMetas.stream().collect(Collectors.toConcurrentMap(p -> p.getDisplayName(), p -> p));
	}

	public VersionObserver versionOfPropertyMeta(){
		String jp = "SELECT vo FROM " + 
				VersionObserver.class.getName() + 
				" vo WHERE vo.observerType = :oType";
		TypedQuery<VersionObserver> query =  entityManager.createQuery(jp, VersionObserver.class).
				setParameter("oType", GlobalConstants.VO_TYPE_PROPERTY_META).setMaxResults(1);
		return query.getSingleResult();
	}
}
