package com.sample.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

@UtilityClass
public class CollectionsUtils {
    public boolean isEmpty(Collection<?> collection){
        return collection==null||collection.isEmpty();
    }
    public boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }
    public boolean isEmpty(Map<?,?> map){
        return map==null||map.isEmpty();
    }
    public boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }
}
