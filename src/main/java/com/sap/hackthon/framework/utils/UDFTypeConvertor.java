/**
 * 
 */
package com.sap.hackthon.framework.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.util.NumberUtils;

import com.sap.hackthon.framework.beans.PropertyMeta;
import com.sap.hackthon.framework.enumeration.UDFType;

/**
 * @author I310717
 *
 */
public final class UDFTypeConvertor {

    public static synchronized Object convert(PropertyMeta proMeta, Object value) {
        if (proMeta == null || value == null) {
            return null;
        }
        UDFType typeEnum = proMeta.getType();
        switch (typeEnum) {
        case DECIMAL:
            return decimalValue(value);
        case TIMESTAMP:
            return timestampValue(value);
        default:
            return value;
        }
    }

    private static synchronized BigDecimal decimalValue(Object value) {
        String numStr = value.toString();
        BigDecimal decimal = null;
        try {
            NumberUtils.parseNumber(numStr, BigDecimal.class);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return decimal;
    }

    private static synchronized Timestamp timestampValue(Object value) {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(value.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return ts;
    }
}
