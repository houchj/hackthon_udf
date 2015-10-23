/**
 * 
 */
package com.sap.hackthon.framework.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author I310717
 *
 */
public final class GlobalConstants {

    public static final String TENANT = "tenant_id";
    public static final String UDF = "UDF";
    public static final String USERNAME = "username";
    
    public static final String OBJECT_TYPE = "objectType";
    public static final String VO_TYPE_PROPERTY_META = "PropertyMeta";
    
    public static final DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
    
}
