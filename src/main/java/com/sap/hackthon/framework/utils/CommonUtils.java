package com.sap.hackthon.framework.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public final class CommonUtils {

	public static String uniqueString(int length, boolean... asciiConvert) {
        UUID uuid = UUID.randomUUID();
        String ustr = uuid.toString().substring(0, length);
        if (asciiConvert.length > 0 && asciiConvert[0]) {
            Pattern pattern = Pattern.compile("\\d");
            char[] conv = ustr.toCharArray();
            for (int i = 0; i < ustr.length(); i++) {
                Character c = conv[i];
                if (pattern.matcher(c.toString()).matches())
                    conv[i] = (char) (65 + Integer.parseInt(c.toString()));
            }
            return new String(conv);
        }
        return ustr;
    }
	
	public static Collection<Object> getCollectionInstance(Class<? extends Collection<?>> cls){
		if(Set.class.isAssignableFrom(cls)){
			return new LinkedHashSet<Object>();
		}
		if(Queue.class.isAssignableFrom(cls)){
			return new LinkedList<Object>();
		}
		return new ArrayList<Object>();
	}

}
