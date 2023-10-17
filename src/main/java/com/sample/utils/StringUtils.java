package com.sample.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public boolean isEmpty(String str){
        return str==null||"".equals(str.trim());
    }
    public boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    public String toTitleCase(String str){
        if (str == null || str.isEmpty()) {
            return str;
        }
        if(str.length()==1)
            return str.toUpperCase();
        return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
    }
}
