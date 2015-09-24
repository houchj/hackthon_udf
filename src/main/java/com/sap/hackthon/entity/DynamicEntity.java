/**
 * 
 */
package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;

import com.sap.hackthon.utils.ObjectTypeEnum;

/**
 * @author I310717
 *
 */
public class DynamicEntity {

    private Map<String, Object> propertities;

    private ObjectTypeEnum objectType;

    public DynamicEntity(ObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public DynamicEntity(ObjectTypeEnum objectType, Map<String, Object> propertities) {
        this(objectType);
        this.propertities = propertities;
    }

    public Object getProperty(String name) {
        if (propertities == null || propertities.size() == 0) {
            return null;
        }
        return propertities.get(name);
    }

    public void setProperty(String name, Object value) {
        if (propertities == null) {
            propertities = new HashMap<String, Object>();
        }
        propertities.put(name, value);
    }

    public ObjectTypeEnum getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public Map<String, Object> getPropertities() {
        return propertities;
    }

    public void setPropertities(Map<String, Object> propertities) {
        this.propertities = propertities;
    }

}
