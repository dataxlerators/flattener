package com.dataxlerators.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class JSONFlattener {

public static Map<String,Object> flattenMap(Map<String,Object> baseMap, String parentKey, Map<String,Object> finalMap) {
        baseMap.forEach((String t,Object u) -> {
            if(u instanceof Map) {
                flattenMap((Map<String, Object>) u, concatKey(parentKey, t), finalMap);
            }else if(u instanceof ArrayList) {
             int index = 0;
                for (Object o : (ArrayList) u) {
                         if (o instanceof Map) {
                        String value = t.concat("[").concat(index + "").concat("]");
                       flattenMap((Map<String, Object>) o,concatKey(parentKey,value),finalMap);
                        index++;
                       }
                }
            } else {
                 finalMap.put(concatKey(parentKey,t),convertToNativeType(u));
           }
        });
        return finalMap;
    }

    private static String concatKey(String parentKey,String value) {
if (StringUtils.isNotEmpty(parentKey)) {
return parentKey + "." + value;
}
return value;
    }


    private static String convertToNativeType(Object str1) {
        String str = str1 != null ? str1.toString() : null;
        try{
            BigDecimal d =new BigDecimal(str);
            BigDecimal fractionPart = d.remainder(BigDecimal.ONE);
            String val = fractionPart.intValueExact() != 0 ? d.toString() : d.toBigInteger().toString();
            return val;
            } catch(Exception e) {
    return str;
}
    }
}
